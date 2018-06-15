package com.redson.plugin;


import com.redson.annotations.RedJsonAbsent;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.RemappingAnnotationAdapter;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linhh on 17/6/8.
 */

public class RedsonClassVisitor extends ClassVisitor {
    public List<RedsonFieldBean> mRedsonBeans = new ArrayList<>();
    public int mClazzType;
    public String mClazzName;

    public RedsonClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    public boolean isJSON(){
        return mClazzType == RedsonVar.TYPE_OBJECT
                || mClazzType == RedsonVar.TYPE_ARRAY;
    }

    public boolean isObject(){
        return mClazzType == RedsonVar.TYPE_OBJECT;
    }

    public boolean isArray(){
        return mClazzType == RedsonVar.TYPE_ARRAY;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClazzName = name;
        if(interfaces != null){
            for(String item : interfaces){
                if(item.equals(RedsonVar.I_JSON_OBJECT_CLAZZ)){
                    mClazzType = RedsonVar.TYPE_OBJECT;
                }else if(item.equals(RedsonVar.I_JSON_ARRAY_CLAZZ)){
                    mClazzType = RedsonVar.TYPE_ARRAY;
                }
            }
        }
    }


    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        //如果是json，记录当前类的变量
        if(isJSON() && ((access) & Opcodes.ACC_STATIC) == 0 && ((access) & Opcodes.ACC_FINAL) == 0) {
            //非静态，非final类型
            RedsonFieldBean redsonFieldBean = new RedsonFieldBean();
            redsonFieldBean.setFieldName(name);
            redsonFieldBean.setDesc(desc);
            redsonFieldBean.setSignature(signature);
            mRedsonBeans.add(redsonFieldBean);
            FieldVisitor fieldVisitor = cv.visitField(access, name, desc, signature, value);
            fieldVisitor = new FieldVisitor(Opcodes.ASM5, fieldVisitor) {
                @Override
                public AnnotationVisitor visitAnnotation(String s, boolean b) {
                    if(Type.getDescriptor(RedJsonAbsent.class).equals(s)){
                        //需要移除该参数
                        for(int i = 0; i < mRedsonBeans.size(); i ++){
                            if(mRedsonBeans.get(i).getFieldName().equals(name)) {
                                mRedsonBeans.remove(i);
                                break;
                            }
                        }
                    }
//                    System.out.print(s + ":" + name);
                    return super.visitAnnotation(s, b);
                }

                @Override
                public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String s, boolean b) {
                    return super.visitTypeAnnotation(i, typePath, s, b);
                }
            };
            return fieldVisitor;
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {
            @Override
            public void visitCode() {

                if(!isJSON()){
                    super.visitCode();
                    return;
                }
                if(isObject() && name.equals("toJSONString") && desc.equals("()Ljava/lang/String;")){
//                    mv.visitCode();
                    mv.visitTypeInsn(NEW, "com/redson/JSONStringer");
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "com/redson/JSONStringer", "<init>", "()V", false);
                    mv.visitVarInsn(ASTORE, 1);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, mClazzName, "toJSONString", "(Lcom/redson/JSONStringer;)V", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "toString", "()Ljava/lang/String;", false);
                    mv.visitInsn(ARETURN);
                    mv.visitMaxs(2, 2);
                    mv.visitEnd();
                    return;
                }

                super.visitCode();
                if(isObject() && name.equals("fromJSON") && desc.equals("(Ljava/lang/String;)V")){
//                    mv.visitCode();
                    mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "instance", "()Lcom/redson/Redson;", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/Redson", "parseJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "parseJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", false);
                    mv.visitVarInsn(ASTORE, 2);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 2);
                    mv.visitMethodInsn(INVOKEVIRTUAL, mClazzName, "fromJSON", "(Lcom/redson/JSON;)V", false);
                    mv.visitInsn(RETURN);
                    mv.visitMaxs(2, 3);
                    mv.visitEnd();
                    return;
                }
//                if(isObject() && name.equals("toJSON") && desc.equals("()Lcom/redson/JSON;")){
//                    mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "makeNewJSON", "()Lcom/redson/JSON;", false);
//                    mv.visitVarInsn(ASTORE, 1);
//                    final int needreturnjson = 1;
//                    int index = 1;
//                    for(RedsonFieldBean bean : mRedsonBeans){
//                        String methodName = RedsonUtils.getMethodName(bean.getDesc());
//
//                        if(methodName.equals("optJSON")){
//                            //对象
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            Label l0 = new Label();
//                            mv.visitJumpInsn(IFNULL, l0);
//                            mv.visitVarInsn(ALOAD, needreturnjson);
//                            mv.visitLdcInsn(bean.getFieldName());
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            mv.visitTypeInsn(CHECKCAST, "com/redson/IJSONObject");
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IJSONObject", "toJSON", "()Lcom/redson/JSON;", true);
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "put", "(Ljava/lang/String;Ljava/lang/Object;)V", true);
//                            mv.visitLabel(l0);
//                            mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"com/redson/JSON"}, 0, null);
//                        }else if(methodName.equals("optJSONArray")){
//                            //int[]数组
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            Label l0 = new Label();
//                            mv.visitJumpInsn(IFNULL, l0);
//                            mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "makeNewJSONArray", "()Lcom/redson/JSONArray;", false);
//                            mv.visitVarInsn(ASTORE, index + 1);
//                            mv.visitInsn(ICONST_0);
//                            mv.visitVarInsn(ISTORE, index + 2);
//                            Label l1 = new Label();
//                            mv.visitLabel(l1);
//                            mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"com/redson/JSON", "com/redson/JSONArray", Opcodes.INTEGER}, 0, null);
//                            mv.visitVarInsn(ILOAD, index + 2);
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            mv.visitInsn(ARRAYLENGTH);
//                            Label l2 = new Label();
//                            mv.visitJumpInsn(IF_ICMPGE, l2);
//                            //------
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            mv.visitVarInsn(ILOAD, index + 2);
//                            RedsonUtils.loada(mv,bean.getDesc());
////                            mv.visitInsn(IALOAD);
//                            RedsonUtils.store(mv, bean.getDesc(), index + 3);
////                            mv.visitVarInsn(ISTORE, index + 3);
//                            mv.visitVarInsn(ALOAD, index + 1);
//                            mv.visitVarInsn(ILOAD, index + 2);
//                            RedsonUtils.load(mv, bean.getDesc(), index + 3);
////                            mv.visitVarInsn(ILOAD, index + 3);
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "put", RedsonUtils.getArrayDescTOJSON(bean.getDesc()) + "V", true);
//                            //=======
//                            mv.visitIincInsn(index + 2, 1);
//                            mv.visitJumpInsn(GOTO, l1);
//                            mv.visitLabel(l2);
//                            mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
//                            mv.visitVarInsn(ALOAD, needreturnjson);
//                            mv.visitLdcInsn(bean.getFieldName());
//                            mv.visitVarInsn(ALOAD, index + 1);
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "put", "(Ljava/lang/String;Ljava/lang/Object;)V", true);
//                            mv.visitLabel(l0);
//                            mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
////                            mv.visitInsn(ACONST_NULL);
//                        }else if(methodName.equals("optDefiniteMap")){
//                            //map
//                            String mapType = RedsonUtils.getBaseMap(bean.getSignature());
//                            if(mapType != null){
//                                String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
//
//                                //基础类型
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                Label l0 = new Label();
//                                mv.visitJumpInsn(IFNULL, l0);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "entrySet", "()Ljava/util/Set;", false);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
//                                mv.visitVarInsn(ASTORE, index + 1);
//                                mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "makeNewJSON", "()Lcom/redson/JSON;", false);
//                                mv.visitVarInsn(ASTORE, index + 2);
//                                Label l1 = new Label();
//                                mv.visitLabel(l1);
//                                mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"com/redson/JSON", "java/util/Iterator", "com/redson/JSON"}, 0, null);
//                                mv.visitVarInsn(ALOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
//                                Label l2 = new Label();
//                                mv.visitJumpInsn(IFEQ, l2);
//                                mv.visitVarInsn(ALOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
//                                mv.visitTypeInsn(CHECKCAST, "java/util/Map$Entry");
//                                mv.visitVarInsn(ASTORE, index + 3);
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map$Entry", "getKey", "()Ljava/lang/Object;", true);
//                                mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map$Entry", "getValue", "()Ljava/lang/Object;", true);
//                                mv.visitTypeInsn(CHECKCAST, RedsonUtils.getBaseMap(bean.getSignature()));
//                                RedsonUtils.covert(mv,bean.getSignature());
////                                mv.visitMethodInsn(INVOKEVIRTUAL, RedsonUtils.getBaseMap(bean.getSignature()), RedsonUtils.covert(mv,bean.getSignature()), "()Z", false);
////                                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "put", "(Ljava/lang/String;Ljava/lang/Object;)V", true);
//                                mv.visitJumpInsn(GOTO, l1);
//                                mv.visitLabel(l2);
//                                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                mv.visitVarInsn(ALOAD, needreturnjson);
//                                mv.visitLdcInsn(bean.getFieldName());
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "put", "(Ljava/lang/String;Ljava/lang/Object;)V", true);
//                                mv.visitLabel(l0);
//                                mv.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
//                            }else{
//                                //对象map
//
//                            }
//                        }else if(methodName.equals("optDefiniteList")) {
//                            //list
//                            String listType = RedsonUtils.getBaseList(bean.getSignature());
//                            if(listType != null){
//                                //基础类型list
//                                String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                Label l3 = new Label();
//                                mv.visitJumpInsn(IFNULL, l3);
//                                mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "makeNewJSONArray", "()Lcom/redson/JSONArray;", false);
//                                mv.visitVarInsn(ASTORE, index + 1);
//                                mv.visitInsn(ICONST_0);
//                                mv.visitVarInsn(ISTORE, index + 2);
//                                Label l4 = new Label();
//                                mv.visitLabel(l4);
//                                mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"com/redson/JSONArray", Opcodes.INTEGER}, 0, null);
//                                mv.visitVarInsn(ILOAD, index + 2);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "size", "()I", false);
//                                Label l5 = new Label();
//                                mv.visitJumpInsn(IF_ICMPGE, l5);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitVarInsn(ILOAD, index + 2);
//                                mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "get", "(I)Ljava/lang/Object;", false);
//                                mv.visitTypeInsn(CHECKCAST, RedsonUtils.getBaseList(bean.getSignature()));
//                                mv.visitVarInsn(ASTORE, index + 3);
//                                mv.visitVarInsn(ALOAD, index + 1);
//                                mv.visitVarInsn(ILOAD, index + 2);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "put", "(ILjava/lang/Object;)V", true);
//                                mv.visitIincInsn(index + 2, 1);
//                                mv.visitJumpInsn(GOTO, l4);
//                                mv.visitLabel(l5);
//                                mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
//                                mv.visitVarInsn(ALOAD, 1);
//                                mv.visitLdcInsn(bean.getFieldName());
//                                mv.visitVarInsn(ALOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "put", "(Ljava/lang/String;Ljava/lang/Object;)V", true);
//                                mv.visitLabel(l3);
//                                mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
//                            }
//                        }else if(methodName != null) {
//                            //基础类型
//                            mv.visitVarInsn(ALOAD, needreturnjson);
//                            mv.visitLdcInsn(bean.getFieldName());
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "put", "(Ljava/lang/String;" + RedsonUtils.getObject(bean.getDesc()) + ")V", true);
//                        }
//                    }
////                    mv.visitInsn(ACONST_NULL);
//                    mv.visitVarInsn(ALOAD, needreturnjson);
//                    mv.visitInsn(ARETURN);
//                    mv.visitMaxs(4 , 8);
//                    mv.visitEnd();
//                    return;
//                }



                boolean open  = true;
                if(isObject() && open && name.equals("toJSONString") && desc.equals("(Lcom/redson/JSONStringer;)V")){
//                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "object", "()V", false);
                    //如果是List
                    int i = 0;
                    int canshu = 1;
                    for(RedsonFieldBean bean : mRedsonBeans){
                        String methodName = RedsonUtils.getMethodName(bean.getDesc());
                        if(i != 0){
                            //需要dot
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "dot", "()V", false);
                        }
                        i++;
                        if(methodName.equals("optDefiniteMap")){
                            //map


                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitInsn(ICONST_0);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "key", "(Ljava/lang/String;I)Lcom/redson/JSONStringer;", false);
                            mv.visitInsn(POP);
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToMap", "(Ljava/util/Map;)V", false);
                        }else if(methodName.equals("optDefiniteList")){
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitInsn(ICONST_0);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "key", "(Ljava/lang/String;I)Lcom/redson/JSONStringer;", false);
                            mv.visitInsn(POP);
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToList", "(Ljava/util/List;)V", false);
                        }else if(methodName.equals("optInt")){
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToObject", "(Ljava/lang/String;I)V", false);
                        }else if(methodName.equals("optBoolean")){
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToObject", "(Ljava/lang/String;Z)V", false);
                        }else if(methodName.equals("optDouble")){
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToObject", "(Ljava/lang/String;D)V", false);
                        }else if(methodName.equals("optLong")){
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToObject", "(Ljava/lang/String;J)V", false);
                        }else if(methodName.equals("optFloat")){
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToObject", "(Ljava/lang/String;F)V", false);
                        }else{
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "writeToObject", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
                        }
                    }
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/JSONStringer", "endObject", "()V", false);
                    mv.visitInsn(RETURN);
                    mv.visitMaxs(3, 3);
                    mv.visitEnd();
                    return;
                }

                if(isObject() && name.equals("fromJSON") && desc.equals("(Lcom/redson/JSON;)V")){
                    //fromJSON方法填充
//                    mv.visitCode();
                    //取得入参的JSONString
                    /**
                     * mv.visitVarInsn(ALOAD, 1);
                     //调用parseJSON
                     mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "parseJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", false);
                     mv.visitVarInsn(ASTORE, 2);
                     int index = 2;
                     */
                    int index = 1;
                    int canshu = 1;
                    //调用结果保存
                    for(RedsonFieldBean bean : mRedsonBeans){
                        String methodName = RedsonUtils.getMethodName(bean.getDesc());

                        if(methodName.equals("optJSON")){
                            //将保存index加一
                            index ++;
                            String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", true);
                            mv.visitVarInsn(ASTORE, index);
                            mv.visitVarInsn(ALOAD, index);
                            Label l0 = new Label();
                            mv.visitJumpInsn(IFNULL, l0);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            Label l1 = new Label();
                            mv.visitJumpInsn(IFNONNULL, l1);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitTypeInsn(NEW, methodDescReal);
                            mv.visitInsn(DUP);
                            mv.visitMethodInsn(INVOKESPECIAL, methodDescReal, "<init>", "()V", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitLabel(l1);
                            mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"com/redson/JSON"}, 0, null);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitVarInsn(ALOAD, index);
                            mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "fromJSON", "(Lcom/redson/JSON;)V", false);
                            mv.visitLabel(l0);
                            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                            mv.visitLdcInsn(Type.getType("Lcom/redson/IJSONObject;"));
//                            mv.visitLdcInsn(Type.getType(bean.getDesc()));
//                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "isAssignableFrom", "(Ljava/lang/Class;)Z", false);
//                            Label l0 = new Label();
//                            mv.visitJumpInsn(IFEQ, l0);
//                            mv.visitVarInsn(ALOAD, canshu);
//                            mv.visitLdcInsn(bean.getFieldName());
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", true);
//                            mv.visitVarInsn(ASTORE, index);
//                            mv.visitVarInsn(ALOAD, index);
//                            mv.visitJumpInsn(IFNULL, l0);
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            Label l1 = new Label();
//                            mv.visitJumpInsn(IFNONNULL, l1);
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitTypeInsn(NEW, methodDescReal);
//                            mv.visitInsn(DUP);
//                            mv.visitMethodInsn(INVOKESPECIAL, methodDescReal, "<init>", "()V", false);
//                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            mv.visitLabel(l1);
//                            mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"com/redson/JSON", "com/redson/JSON"}, 0, null);
//                            mv.visitVarInsn(ALOAD, 0);
//                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                            mv.visitVarInsn(ALOAD, index);
//                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IJSONObject", "fromJSON", "(Lcom/redson/JSON;)V", true);
//                            mv.visitLabel(l0);
//                            mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
                        }else if(methodName.equals("Ljava/lang/Integer;")){
                            //整形
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            index ++;
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optInt", "(Ljava/lang/String;)I", true);
                            mv.visitVarInsn(ISTORE, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitVarInsn(ILOAD, index);
                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), "Ljava/lang/Integer;");
                        }else if(methodName.equals("Ljava/lang/Boolean;")){
                            //整形
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            index ++;
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optBoolean", "(Ljava/lang/String;)Z", true);
                            mv.visitVarInsn(ISTORE, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitVarInsn(ILOAD, index);
                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                        }else if(methodName.equals("Ljava/lang/Double;")){
                            //整形
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            index ++;
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optDouble", "(Ljava/lang/String;)D", true);
                            mv.visitVarInsn(ISTORE, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitVarInsn(ILOAD, index);
                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                        }else if(methodName.equals("Ljava/lang/Long;")){
                            //整形
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            index ++;
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optLong", "(Ljava/lang/String;)J", true);
                            mv.visitVarInsn(ISTORE, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitVarInsn(ILOAD, index);
                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                        }else if(methodName.equals("Ljava/lang/Float;")){
                            //整形
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            index ++;
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optFloat", "(Ljava/lang/String;)F", true);
                            mv.visitVarInsn(ISTORE, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitVarInsn(ILOAD, index);
                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                        }else if(methodName.equals("optJSONArray")){
                            //数组类型分为两种，基础数组和复杂的对象数组
                            //思路，先判断是否基础类型数组，如果是则
//                            String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
                            if(RedsonUtils.isBaseArray(bean.getDesc())){

                                //int[] 等基础类型的数组，判断是否为空，为空判断对应jsonarray大小，直接创建对应大小的array
                                mv.visitVarInsn(ALOAD, canshu);
                                mv.visitLdcInsn(bean.getFieldName());
                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSONArray", "(Ljava/lang/String;)Lcom/redson/JSONArray;", true);
                                index ++;
                                mv.visitVarInsn(ASTORE, index);
                                mv.visitVarInsn(ALOAD, index);
                                Label l0 = new Label();
                                mv.visitJumpInsn(IFNULL, l0);
                                mv.visitVarInsn(ALOAD, 0);
                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                                Label l1 = new Label();
                                mv.visitJumpInsn(IFNONNULL, l1);
                                mv.visitVarInsn(ALOAD, 0);
                                mv.visitVarInsn(ALOAD, index);
                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "length", "()I", true);
                                RedsonUtils.newArray(mv, bean.getDesc());
                                mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                                mv.visitLabel(l1);
                                mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"com/redson/JSON", "com/redson/JSONArray"}, 0, null);
                                mv.visitInsn(ICONST_0);
//                                index++;
                                mv.visitVarInsn(ISTORE, index + 1);
                                Label l2 = new Label();
                                mv.visitLabel(l2);
                                mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
                                mv.visitVarInsn(ILOAD, index + 1);
                                mv.visitVarInsn(ALOAD, 0);
                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                                mv.visitInsn(ARRAYLENGTH);
                                mv.visitJumpInsn(IF_ICMPGE, l0);
                                mv.visitVarInsn(ILOAD, index + 1);
                                mv.visitVarInsn(ALOAD, index);
                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "length", "()I", true);
                                mv.visitJumpInsn(IF_ICMPGE, l0);
                                //错误
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitVarInsn(ILOAD, index + 1);
                                //测试
//                                if(bean.getDesc().equals("[I")) {
//                                    mv.visitInsn(ICONST_5);
//                                }else{
//                                    mv.visitLdcInsn("test");
//                                }
                                //----错误线
                                mv.visitVarInsn(ALOAD, index);
                                mv.visitVarInsn(ILOAD, index + 1);
                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", RedsonUtils.getArrayMethod(bean.getDesc()), RedsonUtils.getArrayDesc(bean.getDesc()), true);
                                RedsonUtils.store(mv, bean.getDesc(), index + 2);
                                mv.visitVarInsn(ALOAD, 0);
                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                                mv.visitVarInsn(ILOAD, index + 1);
                                RedsonUtils.load(mv, bean.getDesc(), index + 2);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                RedsonUtils.load(mv, bean.getDesc(), index + 2);
//
//                                //---
                                RedsonUtils.visitType(mv,bean.getDesc());
                                //for循环累加
                                mv.visitIincInsn(index + 1, 1);
                                mv.visitJumpInsn(GOTO, l2);
                                mv.visitLabel(l0);
                                mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
                            }
//                            else {
                                //其他对象
//                                String methodDescReal = bean.getDesc().substring(2, bean.getDesc().length() - 1);
//                                String clazzRead = bean.getDesc().substring(1, bean.getDesc().length());
//
//                                mv.visitVarInsn(ALOAD, 2);
//                                mv.visitLdcInsn(bean.getFieldName());
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSONArray", "(Ljava/lang/String;)Lcom/redson/JSONArray;", true);
//                                index ++ ;
//                                mv.visitVarInsn(ASTORE, index);
//                                mv.visitVarInsn(ALOAD, index);
//                                Label l0 = new Label();
//                                mv.visitJumpInsn(IFNULL, l0);
//                                mv.visitLdcInsn(Type.getType("Lcom/redson/IJSONObject;"));
//                                mv.visitLdcInsn(Type.getType(clazzRead));
//                                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "isAssignableFrom", "(Ljava/lang/Class;)Z", false);
//                                Label l1 = new Label();
//                                mv.visitJumpInsn(IFEQ, l1);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                Label l2 = new Label();
//                                mv.visitJumpInsn(IFNONNULL, l2);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "length", "()I", true);
//                                mv.visitTypeInsn(ANEWARRAY, methodDescReal);
//                                mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitLabel(l2);
//                                mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"com/redson/JSON", "com/redson/JSONArray"}, 0, null);
//                                mv.visitInsn(ICONST_0);
//                                mv.visitVarInsn(ISTORE, index + 1);
//                                Label l3 = new Label();
//                                mv.visitLabel(l3);
//                                mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
//                                mv.visitVarInsn(ILOAD, index  + 1 );
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitInsn(ARRAYLENGTH);
//                                Label l4 = new Label();
//                                mv.visitJumpInsn(IF_ICMPGE, l4);
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "length", "()I", true);
//                                mv.visitJumpInsn(IF_ICMPGE, l4);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "optJSON", "(I)Lcom/redson/JSON;", true);
//                                mv.visitVarInsn(ASTORE, index + 2);
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                Label l5 = new Label();
//                                mv.visitJumpInsn(IFNULL, l5);
//                                mv.visitTypeInsn(NEW, methodDescReal);
//                                mv.visitInsn(DUP);
//                                mv.visitMethodInsn(INVOKESPECIAL, methodDescReal, "<init>", "()V", false);
//                                mv.visitVarInsn(ASTORE, index + 3);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitTypeInsn(CHECKCAST, "com/redson/IJSONObject");
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IJSONObject", "fromJSON", "(Lcom/redson/JSON;)V", true);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitInsn(AASTORE);
//                                mv.visitLabel(l5);
//                                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                mv.visitIincInsn(4, 1);
//                                mv.visitJumpInsn(GOTO, l3);
//                                mv.visitLabel(l4);
//                                mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
//                                mv.visitJumpInsn(GOTO, l0);
//                                mv.visitLabel(l1);
//                                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                mv.visitLdcInsn(Type.getType("Lcom/redson/IJSONArray;"));
//                                mv.visitLdcInsn(Type.getType(clazzRead));
//                                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "isAssignableFrom", "(Ljava/lang/Class;)Z", false);
//                                mv.visitJumpInsn(IFEQ, l0);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                Label l6 = new Label();
//                                mv.visitJumpInsn(IFNONNULL, l6);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "length", "()I", true);
//                                mv.visitTypeInsn(ANEWARRAY, methodDescReal);
//                                mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitLabel(l6);
//                                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                mv.visitInsn(ICONST_0);
//                                mv.visitVarInsn(ISTORE, index + 1);
//                                Label l7 = new Label();
//                                mv.visitLabel(l7);
//                                mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitInsn(ARRAYLENGTH);
//                                mv.visitJumpInsn(IF_ICMPGE, l0);
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "length", "()I", true);
//                                mv.visitJumpInsn(IF_ICMPGE, l0);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSONArray", "optJSONArray", "(I)Lcom/redson/JSONArray;", true);
//                                mv.visitVarInsn(ASTORE, index + 2);
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                Label l8 = new Label();
//                                mv.visitJumpInsn(IFNULL, l8);
//                                mv.visitTypeInsn(NEW, methodDescReal);
//                                mv.visitInsn(DUP);
//                                mv.visitMethodInsn(INVOKESPECIAL, methodDescReal, "<init>", "()V", false);
//                                mv.visitVarInsn(ASTORE, index + 3);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitTypeInsn(CHECKCAST, "com/redson/IJSONArray");
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IJSONArray", "fromJSON", "(Lcom/redson/JSONArray;)V", true);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitVarInsn(ILOAD, index + 1);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitInsn(AASTORE);
//                                mv.visitLabel(l8);
//                                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                mv.visitIincInsn(4, 1);
//                                mv.visitJumpInsn(GOTO, l7);
//                                mv.visitLabel(l0);
//                                mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
//                            }
                        }else if(methodName.equals("optDefiniteList")){
                            //明确的list类型
                            //判断是否基础类型的list
                            //todo
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSONArray", "(Ljava/lang/String;)Lcom/redson/JSONArray;", true);
                            index ++;
                            //index = 2;
                            mv.visitVarInsn(ASTORE, index);
                            mv.visitVarInsn(ALOAD, index);
                            Label l0 = new Label();
                            mv.visitJumpInsn(IFNULL, l0);
                            final int sindex = index;

                            SignatureReader signatureReader = new SignatureReader(bean.getSignature());
                            signatureReader.acceptType(new SignatureVisitor(Opcodes.ASM5) {
                                String lastType = null;
                                int type_index = sindex;
                                int i = -1;
                                @Override
                                public void visitClassType(String name) {
                                    super.visitClassType(name);
                                    i++;
                                    if(i == 0){
                                        return;
                                    }
                                    if(lastType == null){
                                        //第一次
                                        lastType = "";
                                        mv.visitTypeInsn(NEW, "com/redson/NormalBean");
                                        mv.visitInsn(DUP);
                                        mv.visitMethodInsn(INVOKESPECIAL, "com/redson/NormalBean", "<init>", "()V", false);
                                        type_index ++ ;
                                        mv.visitVarInsn(ASTORE, type_index);
                                        mv.visitVarInsn(ALOAD, type_index);
                                        mv.visitLdcInsn(Type.getType("L" + name + ";"));
                                        mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IBean", "setClass", "(Ljava/lang/Class;)V", true);
                                        return;
                                    }
                                    if(!(lastType.startsWith("java/util/") && lastType.endsWith("Map"))){
                                        //非map就开始处理
                                        mv.visitTypeInsn(NEW, "com/redson/NormalBean");
                                        mv.visitInsn(DUP);
                                        mv.visitMethodInsn(INVOKESPECIAL, "com/redson/NormalBean", "<init>", "()V", false);
                                        type_index ++ ;
                                        mv.visitVarInsn(ASTORE, type_index);
                                        mv.visitVarInsn(ALOAD, type_index);
                                        mv.visitLdcInsn(Type.getType("L" + name + ";"));
                                        mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IBean", "setClass", "(Ljava/lang/Class;)V", true);
                                        mv.visitVarInsn(ALOAD, type_index - 1);
                                        mv.visitVarInsn(ALOAD, type_index);
                                        mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IBean", "setNextBean", "(Lcom/redson/IBean;)V", true);

                                    }

                                    lastType = name;//设置上一次的type
                                }

                            });

                            String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
                            String newMethodDesc = methodDescReal;
                            if(bean.getDesc().equals("Ljava/util/List;")){
                                newMethodDesc = "java/util/LinkedList";
                            }
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            Label l1 = new Label();
                            mv.visitJumpInsn(IFNONNULL, l1);
                            //新增
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitTypeInsn(NEW, newMethodDesc);
                            mv.visitInsn(DUP);
                            mv.visitMethodInsn(INVOKESPECIAL, newMethodDesc, "<init>", "()V", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            Label l2 = new Label();
                            mv.visitJumpInsn(GOTO, l2);
                            mv.visitLabel(l1);
                            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, newMethodDesc, "clear", "()V", false);
                            mv.visitLabel(l2);
                            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

                            //结果
//                            mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "instance", "()Lcom/redson/Redson;", false);
                            mv.visitVarInsn(ALOAD, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitVarInsn(ALOAD, index + 1);//取第一个
//                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/Redson", "fromJson", "(Lcom/redson/JSONArray;Ljava/util/List;Lcom/redson/IBean;)V", false);
                            mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "fromJson", "(Lcom/redson/JSONArray;Ljava/util/List;Lcom/redson/IBean;)V", false);
                            mv.visitLabel(l0);
                            mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"com/redson/JSONArray"}, 0, null);

                        }else if(methodName.equals("optDefiniteMap")){
                            //明确的map类型
                            //todo
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", true);
                            index ++;
                            //index = 2;
                            mv.visitVarInsn(ASTORE, index);
                            mv.visitVarInsn(ALOAD, index);
                            Label l0 = new Label();
                            mv.visitJumpInsn(IFNULL, l0);
                            final int sindex = index;

                            SignatureReader signatureReader = new SignatureReader(bean.getSignature());
                            signatureReader.acceptType(new SignatureVisitor(Opcodes.ASM5) {
                                String lastType = null;
                                int type_index = sindex;
                                int i = -1;
                                @Override
                                public void visitClassType(String name) {
                                    super.visitClassType(name);
                                    i++;
                                    if(i <=1){
                                        return;
                                    }
                                    if(lastType == null){
                                        //第一次
                                        lastType = "";
                                        mv.visitTypeInsn(NEW, "com/redson/NormalBean");
                                        mv.visitInsn(DUP);
                                        mv.visitMethodInsn(INVOKESPECIAL, "com/redson/NormalBean", "<init>", "()V", false);
                                        type_index ++ ;
                                        mv.visitVarInsn(ASTORE, type_index);
                                        mv.visitVarInsn(ALOAD, type_index);
                                        mv.visitLdcInsn(Type.getType("L" + name + ";"));
                                        mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IBean", "setClass", "(Ljava/lang/Class;)V", true);
                                        return;
                                    }
                                    if(!(lastType.startsWith("java/util/") && lastType.endsWith("Map"))){
                                        //非map就开始处理
                                        mv.visitTypeInsn(NEW, "com/redson/NormalBean");
                                        mv.visitInsn(DUP);
                                        mv.visitMethodInsn(INVOKESPECIAL, "com/redson/NormalBean", "<init>", "()V", false);
                                        type_index ++ ;
                                        mv.visitVarInsn(ASTORE, type_index);
                                        mv.visitVarInsn(ALOAD, type_index);
                                        mv.visitLdcInsn(Type.getType("L" + name + ";"));
                                        mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IBean", "setClass", "(Ljava/lang/Class;)V", true);
                                        mv.visitVarInsn(ALOAD, type_index - 1);
                                        mv.visitVarInsn(ALOAD, type_index);
                                        mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IBean", "setNextBean", "(Lcom/redson/IBean;)V", true);

                                    }

                                    lastType = name;//设置上一次的type
                                }

                            });

                            String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
                            String newMethodDesc = methodDescReal;
                            if(bean.getDesc().equals("Ljava/util/Map;")){
                                newMethodDesc = "java/util/LinkedHashMap";
                            }
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            Label l1 = new Label();
                            mv.visitJumpInsn(IFNONNULL, l1);
                            //新增
                            mv.visitVarInsn(ALOAD, 0);

                            mv.visitTypeInsn(NEW, newMethodDesc);
                            mv.visitInsn(DUP);
                            mv.visitMethodInsn(INVOKESPECIAL, newMethodDesc, "<init>", "()V", false);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            Label l2 = new Label();
                            mv.visitJumpInsn(GOTO, l2);
                            mv.visitLabel(l1);
                            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitMethodInsn(INVOKEVIRTUAL, newMethodDesc, "clear", "()V", false);
                            mv.visitLabel(l2);
                            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

                            //结果
//                            mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "instance", "()Lcom/redson/Redson;", false);
                            mv.visitVarInsn(ALOAD, index);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                            mv.visitVarInsn(ALOAD, index + 1);//取第一个
//                            mv.visitMethodInsn(INVOKEVIRTUAL, "com/redson/Redson", "fromJson", "(Lcom/redson/JSON;Ljava/util/Map;Lcom/redson/IBean;)V", false);
                            mv.visitMethodInsn(INVOKESTATIC, "com/redson/Redson", "fromJson", "(Lcom/redson/JSON;Ljava/util/Map;Lcom/redson/IBean;)V", false);
                            mv.visitLabel(l0);
                            mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"com/redson/JSON"}, 0, null);

//                            String mapType = RedsonUtils.getBaseMap(bean.getSignature());
//                            if(mapType != null){
//                                //基础map
//                                String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
//                                mv.visitVarInsn(ALOAD, canshu);
//                                mv.visitLdcInsn(bean.getFieldName());
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", true);
//                                index ++;
//                                mv.visitVarInsn(ASTORE, index);
//                                mv.visitVarInsn(ALOAD, index);
//                                Label l0 = new Label();
//                                mv.visitJumpInsn(IFNULL, l0);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                Label l1 = new Label();
//                                mv.visitJumpInsn(IFNONNULL, l1);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitTypeInsn(NEW, methodDescReal);
//                                mv.visitInsn(DUP);
//                                mv.visitMethodInsn(INVOKESPECIAL, methodDescReal, "<init>", "()V", false);
//                                mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                Label l2 = new Label();
//                                mv.visitJumpInsn(GOTO, l2);
//                                mv.visitLabel(l1);
//                                mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"com/redson/JSON", "com/redson/JSON"}, 0, null);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "clear", "()V", false);
//                                mv.visitLabel(l2);
//                                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "keys", "()Ljava/util/Iterator;", true);
//                                mv.visitVarInsn(ASTORE, index + 1);
//                                Label l3 = new Label();
//                                mv.visitLabel(l3);
//                                mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"java/util/Iterator"}, 0, null);
//                                mv.visitVarInsn(ALOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
//                                mv.visitJumpInsn(IFEQ, l0);
//                                mv.visitVarInsn(ALOAD, index + 1);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
//                                mv.visitTypeInsn(CHECKCAST, "java/lang/String");
//                                mv.visitVarInsn(ASTORE, index + 2);
//                                mv.visitVarInsn(ALOAD, index);
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", RedsonUtils.getBaseMapMethod(bean.getSignature()), "(Ljava/lang/String;)"+RedsonUtils.getBaseMapMethodReturn(bean.getSignature()), true);
//                                mv.visitMethodInsn(INVOKESTATIC, RedsonUtils.getBaseMap(bean.getSignature()), "valueOf", "("+RedsonUtils.getBaseMapMethodParams(bean.getSignature())+")L"+RedsonUtils.getBaseMap(bean.getSignature())+";", false);
//                                mv.visitVarInsn(ASTORE, index + 3);
//                                mv.visitVarInsn(ALOAD, 0);
//                                mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                mv.visitVarInsn(ALOAD, index + 2);
//                                mv.visitVarInsn(ALOAD, index + 3);
//                                mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
//                                mv.visitInsn(POP);
//                                mv.visitJumpInsn(GOTO, l3);
//                                mv.visitLabel(l0);
//                                mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
//                            }else{
//                                //非基础类型的嵌套map，需要额外处理对策
//                                //MainActivity
//                                String mapItemType = RedsonUtils.getMapItemType(bean.getSignature());
//                                if(mapItemType != null) {
//                                    String realmapItemType = mapItemType.substring(1, mapItemType.length() - 1);
//                                    String methodDescReal = bean.getDesc().substring(1, bean.getDesc().length() - 1);
//                                    mv.visitVarInsn(ALOAD, canshu);
//                                    mv.visitLdcInsn(bean.getFieldName());
//                                    mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", true);
//                                    index++;
//                                    mv.visitVarInsn(ASTORE, index);
//                                    mv.visitVarInsn(ALOAD, index);
//                                    Label l0 = new Label();
//                                    mv.visitJumpInsn(IFNULL, l0);
//                                    mv.visitLdcInsn(Type.getType("Lcom/redson/IJSONObject;"));
//                                    mv.visitLdcInsn(Type.getType(mapItemType));
//                                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "isAssignableFrom", "(Ljava/lang/Class;)Z", false);
//                                    mv.visitJumpInsn(IFEQ, l0);
//                                    mv.visitVarInsn(ALOAD, 0);
//                                    mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                    Label l1 = new Label();
//                                    mv.visitJumpInsn(IFNONNULL, l1);
//                                    mv.visitVarInsn(ALOAD, 0);
//                                    mv.visitTypeInsn(NEW, methodDescReal);
//                                    mv.visitInsn(DUP);
//                                    mv.visitMethodInsn(INVOKESPECIAL, methodDescReal, "<init>", "()V", false);
//                                    mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                    Label l2 = new Label();
//                                    mv.visitJumpInsn(GOTO, l2);
//                                    mv.visitLabel(l1);
//                                    mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"com/redson/JSON", "com/redson/JSON"}, 0, null);
//                                    mv.visitVarInsn(ALOAD, 0);
//                                    mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                    mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "clear", "()V", false);
//                                    mv.visitLabel(l2);
//                                    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                                    mv.visitVarInsn(ALOAD, index);
//                                    mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "keys", "()Ljava/util/Iterator;", true);
//                                    mv.visitVarInsn(ASTORE, index + 1);
//                                    Label l3 = new Label();
//                                    mv.visitLabel(l3);
//                                    mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"java/util/Iterator"}, 0, null);
//                                    mv.visitVarInsn(ALOAD, index + 1);
//                                    mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
//                                    mv.visitJumpInsn(IFEQ, l0);
//                                    mv.visitVarInsn(ALOAD, index + 1);
//                                    mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
//                                    mv.visitTypeInsn(CHECKCAST, "java/lang/String");
//                                    mv.visitVarInsn(ASTORE, index + 2);
//                                    mv.visitVarInsn(ALOAD, index);
//                                    mv.visitVarInsn(ALOAD, index + 2);
//                                    mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", "optJSON", "(Ljava/lang/String;)Lcom/redson/JSON;", true);
//                                    mv.visitVarInsn(ASTORE, index + 3);
//                                    mv.visitInsn(ACONST_NULL);
//                                    mv.visitVarInsn(ASTORE, index + 4);
//                                    mv.visitVarInsn(ALOAD, index + 3);
//                                    Label l4 = new Label();
//                                    mv.visitJumpInsn(IFNULL, l4);
//                                    mv.visitTypeInsn(NEW, realmapItemType);
//                                    mv.visitInsn(DUP);
//                                    mv.visitMethodInsn(INVOKESPECIAL, realmapItemType, "<init>", "()V", false);
//                                    mv.visitVarInsn(ASTORE, index + 4);
//                                    mv.visitVarInsn(ALOAD, index + 4);
//                                    mv.visitTypeInsn(CHECKCAST, "com/redson/IJSONObject");
//                                    mv.visitVarInsn(ALOAD, index + 3);
//                                    mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/IJSONObject", "fromJSON", "(Lcom/redson/JSON;)V", true);
//                                    mv.visitLabel(l4);
//                                    mv.visitFrame(Opcodes.F_APPEND, 3, new Object[]{"java/lang/String", "com/redson/JSON", realmapItemType}, 0, null);
//                                    mv.visitVarInsn(ALOAD, 0);
//                                    mv.visitFieldInsn(GETFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
//                                    mv.visitVarInsn(ALOAD, index + 2);
//                                    mv.visitVarInsn(ALOAD, index + 4);
//                                    mv.visitMethodInsn(INVOKEVIRTUAL, methodDescReal, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
//                                    mv.visitInsn(POP);
//                                    mv.visitJumpInsn(GOTO, l3);
//                                    mv.visitLabel(l0);
//                                    mv.visitFrame(Opcodes.F_FULL, 4, new Object[]{mClazzName, "java/lang/String", "com/redson/JSON", "com/redson/JSON"}, 0, new Object[]{});
//                                }
//                            }

                        }else if(methodName != null) {
                            //基本类型直接赋值
                            String methodDesc = RedsonUtils.getMethodDesc(bean.getDesc());
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitVarInsn(ALOAD, canshu);
                            mv.visitLdcInsn(bean.getFieldName());
                            mv.visitMethodInsn(INVOKEINTERFACE, "com/redson/JSON", methodName, methodDesc, true);
                            mv.visitFieldInsn(PUTFIELD, mClazzName, bean.getFieldName(), bean.getDesc());
                        }
                    }
                    mv.visitInsn(RETURN);
                    mv.visitMaxs(4 , 8);
                    mv.visitEnd();
                }
            }
        };
        return methodVisitor;

    }
}
