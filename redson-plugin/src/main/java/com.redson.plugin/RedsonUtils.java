package com.redson.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Created by Linhh on 11/02/2018.
 */

public class RedsonUtils implements Opcodes{

    public static String getMethodDesc(String typeS){
        if("Ljava/lang/String;".equals(typeS)){
            //返回string
            return "(Ljava/lang/String;)Ljava/lang/String;";
        }
        if("Z".equals(typeS)){
            return "(Ljava/lang/String;)Z";
        }
        if("I".equals(typeS)){
            return "(Ljava/lang/String;)I";
        }
        if("F".equals(typeS)){
            return "(Ljava/lang/String;)F";
        }
        if("D".equals(typeS)){
            return "(Ljava/lang/String;)D";
        }
        if("J".equals(typeS)){
            return "(Ljava/lang/String;)J";
        }
        return null;
    }

    public static String getArrayDescTOJSON(String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            return "(ILjava/lang/Object;)";
        }
        if("[Z".equals(typeS)){
            return "(IZ)";
        }
        if("[I".equals(typeS)){
            return "(II)";
        }
        if("[F".equals(typeS)){
            return "(ID)";
        }
        if("[D".equals(typeS)){
            return "(ID)";
        }
        if("[J".equals(typeS)){
            return "(IJ)";
        }
        //其他类型的
        return null;

    }


    public static String getArrayDesc(String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            return "(I)Ljava/lang/String;";
        }
        if("[Z".equals(typeS)){
            return "(I)Z";
        }
        if("[I".equals(typeS)){
            return "(I)I";
        }
        if("[F".equals(typeS)){
            return "(I)D";
        }
        if("[D".equals(typeS)){
            return "(I)D";
        }
        if("[J".equals(typeS)){
            return "(I)J";
        }
        //其他类型的
        return null;

    }

    public static String getArrayMethod(String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            return "optString";
        }
        if("[Z".equals(typeS)){
            return "optBoolean";
        }
        if("[I".equals(typeS)){
            return "optInt";
        }
        if("[F".equals(typeS)){
            return "optDouble";
        }
        if("[D".equals(typeS)){
            return "optDouble";
        }
        if("[J".equals(typeS)){
            return "optLong";
        }
        //其他类型的
        return null;
    }

    public static boolean isMap(String typeS){
        if("Ljava/util/HashMap;".equals(typeS)){
            return true;
        }
        if("Ljava/util/LinkedHashMap;".equals(typeS)){
            return true;
        }
        if("Landroid/util/ArrayMap;".equals(typeS)){
            return true;
        }
        if("Ljava/util/Map;".equals(typeS)){
            return true;
        }
        return false;
    }

    public static boolean isList(String typeS){
        if("Ljava/util/ArrayList;".equals(typeS)){
            return true;
        }
        if("Ljava/util/LinkedList;".equals(typeS)){
            return true;
        }
        if("Ljava/util/List;".equals(typeS)){
            return true;
        }
        return false;
    }

    public static boolean isBaseArray(String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            return true;
        }
        if("[Z".equals(typeS)){
            return true;
        }
        if("[I".equals(typeS)){
            return true;
        }
        if("[F".equals(typeS)){
            return true;
        }
        if("[D".equals(typeS)){
            return true;
        }
        if("[J".equals(typeS)){
            return true;
        }
        return false;
    }

    public static void load(MethodVisitor mv, String typeS, int index){
        if("[Ljava/lang/String;".equals(typeS)){
            mv.visitVarInsn(ALOAD, index);
        }
        if("[Z".equals(typeS)){
            mv.visitVarInsn(ILOAD, index);
        }
        if("[I".equals(typeS)){
            mv.visitVarInsn(ILOAD, index);
        }
        if("[F".equals(typeS)){
            mv.visitVarInsn(FLOAD, index);
        }
        if("[D".equals(typeS)){
            mv.visitVarInsn(DLOAD, index);
        }
        if("[J".equals(typeS)){
            mv.visitVarInsn(LLOAD, index);
        }
    }

    public static void loada(MethodVisitor mv, String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            mv.visitInsn(AALOAD);
        }
        if("[Z".equals(typeS)){
            mv.visitInsn(IALOAD);
        }
        if("[I".equals(typeS)){
            mv.visitInsn(IALOAD);
        }
        if("[F".equals(typeS)){
            mv.visitInsn(FALOAD);
        }
        if("[D".equals(typeS)){
            mv.visitInsn(DALOAD);
        }
        if("[J".equals(typeS)){
            mv.visitInsn(LALOAD);
        }
    }

    public static void store(MethodVisitor mv, String typeS, int index){
        if("[Ljava/lang/String;".equals(typeS)){
            mv.visitVarInsn(ASTORE, index);
        }
        if("[Z".equals(typeS)){
            mv.visitVarInsn(ISTORE, index);
        }
        if("[I".equals(typeS)){
            mv.visitVarInsn(ISTORE, index);
        }
        if("[F".equals(typeS)){
            mv.visitVarInsn(FSTORE, index);
        }
        if("[D".equals(typeS)){
            mv.visitVarInsn(DSTORE, index);
        }
        if("[J".equals(typeS)){
            mv.visitVarInsn(LSTORE, index);
        }
    }

    public static void visitType(MethodVisitor mv, String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            mv.visitInsn(AASTORE);
        }
        if("[Z".equals(typeS)){
            mv.visitInsn(IASTORE);
        }
        if("[I".equals(typeS)){
            mv.visitInsn(IASTORE);
        }
        if("[F".equals(typeS)){
            mv.visitInsn(FASTORE);
        }
        if("[D".equals(typeS)){
            mv.visitInsn(DASTORE);
        }
        if("[J".equals(typeS)){
            mv.visitInsn(LASTORE);
        }
    }

    public static void newArray(MethodVisitor mv, String typeS){
        if("[Ljava/lang/String;".equals(typeS)){
            mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
        }
        if("[Z".equals(typeS)){
            mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
        }
        if("[I".equals(typeS)){
            mv.visitIntInsn(NEWARRAY, T_INT);
        }
        if("[F".equals(typeS)){
            mv.visitIntInsn(NEWARRAY, T_FLOAT);
        }
        if("[D".equals(typeS)){
            mv.visitIntInsn(NEWARRAY, T_DOUBLE);
        }
        if("[J".equals(typeS)){
            mv.visitIntInsn(NEWARRAY, T_LONG);
        }
    }

    public static String getListItemType(String typeS){
        String[] t = typeS.split("<");
        if(t != null && t.length > 1 && t[1] != null){
            String[] ts = t[1].split(">");
            if(ts != null && ts.length > 1 && ts[0] != null){
                return ts[0];
            }
        }
        return null;
    }

    public static String getMapItemType(String typeS){
        String[] t = typeS.split(";");
        if(t != null && t.length > 1 && t[1] != null){
            return t[1] + ";";
        }
        return null;
    }

    public static String getBaseMapMethodParams(String typeS){
        if(typeS.endsWith(";Ljava/lang/String;>;")){
            //返回string
            return "Ljava/lang/Object;";
        }
        if(typeS.endsWith(";Ljava/lang/Boolean;>;")){
            return "Z";
        }
        if(typeS.endsWith(";Ljava/lang/Integer;>;")){
            return "I";
        }
        if(typeS.endsWith(";Ljava/lang/Float;>;")){
            return "D";
        }
        if(typeS.endsWith(";Ljava/lang/Double;>;")){
            return "D";
        }
        if(typeS.endsWith(";Ljava/lang/Long;>;")){
            return "J";
        }

        return null;
    }

    public static String getBaseListValueofMethodReturn(String typeS){
        if(typeS.endsWith("<Ljava/lang/String;>;")){
            //返回string
            return "Ljava/lang/Object;";
        }
        if(typeS.endsWith("<Ljava/lang/Boolean;>;")){
            return "Z";
        }
        if(typeS.endsWith("<Ljava/lang/Integer;>;")){
            return "I";
        }
        if(typeS.endsWith("<Ljava/lang/Float;>;")){
            return "D";
        }
        if(typeS.endsWith("<Ljava/lang/Double;>;")){
            return "D";
        }
        if(typeS.endsWith("<Ljava/lang/Long;>;")){
            return "J";
        }

        return null;
    }

    public static String getBaseListMethodReturn(String typeS){
        if(typeS.endsWith("<Ljava/lang/String;>;")){
            //返回string
            return "Ljava/lang/String;";
        }
        if(typeS.endsWith("<Ljava/lang/Boolean;>;")){
            return "Z";
        }
        if(typeS.endsWith("<Ljava/lang/Integer;>;")){
            return "I";
        }
        if(typeS.endsWith("<Ljava/lang/Float;>;")){
            return "D";
        }
        if(typeS.endsWith("<Ljava/lang/Double;>;")){
            return "D";
        }
        if(typeS.endsWith("<Ljava/lang/Long;>;")){
            return "J";
        }

        return null;
    }

    public static String getBaseMapMethodReturn(String typeS){
        if(typeS.endsWith(";Ljava/lang/String;>;")){
            //返回string
            return "Ljava/lang/String;";
        }
        if(typeS.endsWith(";Ljava/lang/Boolean;>;")){
            return "Z";
        }
        if(typeS.endsWith(";Ljava/lang/Integer;>;")){
            return "I";
        }
        if(typeS.endsWith(";Ljava/lang/Float;>;")){
            return "D";
        }
        if(typeS.endsWith(";Ljava/lang/Double;>;")){
            return "D";
        }
        if(typeS.endsWith(";Ljava/lang/Long;>;")){
            return "J";
        }

        return null;
    }

    public static String getBaseListMethod(String typeS){
        if(typeS.endsWith("<Ljava/lang/String;>;")){
            //返回string
            return "optString";
        }
        if(typeS.endsWith("<Ljava/lang/Boolean;>;")){
            return "optBoolean";
        }
        if(typeS.endsWith("<Ljava/lang/Integer;>;")){
            return "optInt";
        }
        if(typeS.endsWith("<Ljava/lang/Float;>;")){
            return "optDouble";
        }
        if(typeS.endsWith("<Ljava/lang/Double;>;")){
            return "optDouble";
        }
        if(typeS.endsWith("<Ljava/lang/Long;>;")){
            return "optLong";
        }

        return null;
    }

    public static String getBaseMapMethod(String typeS){
        if(typeS.endsWith(";Ljava/lang/String;>;")){
            //返回string
            return "optString";
        }
        if(typeS.endsWith(";Ljava/lang/Boolean;>;")){
            return "optBoolean";
        }
        if(typeS.endsWith(";Ljava/lang/Integer;>;")){
            return "optInt";
        }
        if(typeS.endsWith(";Ljava/lang/Float;>;")){
            return "optDouble";
        }
        if(typeS.endsWith(";Ljava/lang/Double;>;")){
            return "optDouble";
        }
        if(typeS.endsWith(";Ljava/lang/Long;>;")){
            return "optLong";
        }

        return null;
    }

    public static String getJSONObject(String typeS){
        if("Z".equals(typeS)){
            return "Z";
        }
        if("I".equals(typeS)){
            return "I";
        }
        if("F".equals(typeS)){
            return "F";
        }
        if("D".equals(typeS)){
            return "D";
        }
        if("J".equals(typeS)){
            return "J";
        }
        if("Ljava/lang/String;".equals(typeS)){
            return "Ljava/lang/String;";
        }
        if("Ljava/util/HashMap;".equals(typeS)||
                "Ljava/util/LinkedHashMap;".equals(typeS)||
                "Landroid/util/ArrayMap;".equals(typeS)){
            return "Ljava/util/Map;";
        }
        if("Ljava/util/ArrayList;".equals(typeS)||
                "Ljava/util/LinkedList;".equals(typeS)){
            return "Ljava/util/List;";
        }
        return "Lcom/redson/IJSONObject;";
    }

    public static String getObject(String typeS){
        if("Z".equals(typeS)){
            return "Z";
        }
        if("I".equals(typeS)){
            return "I";
        }
        if("F".equals(typeS)){
            return "F";
        }
        if("D".equals(typeS)){
            return "D";
        }
        if("J".equals(typeS)){
            return "J";
        }
        return "Ljava/lang/Object;";
    }

    public static String getBaseList(String typeS){
        if(typeS.endsWith("<Ljava/lang/String;>;")){
            //返回string
            return "java/lang/String";
        }
        if(typeS.endsWith("<Ljava/lang/Boolean;>;")){
            return "java/lang/Boolean";
        }
        if(typeS.endsWith("<Ljava/lang/Integer;>;")){
            return "java/lang/Integer";
        }
        if(typeS.endsWith("<Ljava/lang/Float;>;")){
            return "java/lang/Float";
        }
        if(typeS.endsWith("<Ljava/lang/Double;>;")){
            return "java/lang/Double";
        }
        if(typeS.endsWith("<Ljava/lang/Long;>;")){
            return "java/lang/Long";
        }

        return null;
    }

    public static void covert(MethodVisitor mv, String typeS){
        if(typeS.endsWith(";Ljava/lang/String;>;")){
            //返回string
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);

        }
        if(typeS.endsWith(";Ljava/lang/Boolean;>;")){
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);

        }
        if(typeS.endsWith(";Ljava/lang/Integer;>;")){
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);

        }
        if(typeS.endsWith(";Ljava/lang/Float;>;")){
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);

        }
        if(typeS.endsWith(";Ljava/lang/Double;>;")){
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);

        }
        if(typeS.endsWith(";Ljava/lang/Long;>;")){
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);

        }

    }

    public static String getBaseMap(String typeS){
        if(typeS.endsWith(";Ljava/lang/String;>;")){
            //返回string
            return "java/lang/String";
        }
        if(typeS.endsWith(";Ljava/lang/Boolean;>;")){
            return "java/lang/Boolean";
        }
        if(typeS.endsWith(";Ljava/lang/Integer;>;")){
            return "java/lang/Integer";
        }
        if(typeS.endsWith(";Ljava/lang/Float;>;")){
            return "java/lang/Float";
        }
        if(typeS.endsWith(";Ljava/lang/Double;>;")){
            return "java/lang/Double";
        }
        if(typeS.endsWith(";Ljava/lang/Long;>;")){
            return "java/lang/Long";
        }

        return null;
    }

    public static String getMethodName(String typeS){
        if("Ljava/lang/String;".equals(typeS)){
            //返回string
            return "optString";
        }
        if("Ljava/lang/Integer;".equals(typeS)){
            return "Ljava/lang/Integer;";
        }
        if("Ljava/lang/Boolean;".equals(typeS)){
            return "Ljava/lang/Boolean;";
        }
        if("Ljava/lang/Double;".equals(typeS)){
            return "Ljava/lang/Double;";
        }
        if("Ljava/lang/Float;".equals(typeS)){
            return "Ljava/lang/Float;";
        }
        if("Ljava/lang/Long;".equals(typeS)){
            return "Ljava/lang/Long;";
        }
        if("Z".equals(typeS)){
            return "optBoolean";
        }
        if("I".equals(typeS)){
            return "optInt";
        }
        if("F".equals(typeS)){
            return "optFloat";
        }
        if("D".equals(typeS)){
            return "optDouble";
        }
        if("J".equals(typeS)){
            return "optLong";
        }
        if(typeS.startsWith("[")){
            //数组
            return "optJSONArray";
        }
        if(isMap(typeS)){
            return "optDefiniteMap";
        }
        if(isList(typeS)){
            return "optDefiniteList";
        }
        return "optJSON";
    }
}
