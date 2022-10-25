//package com.bwmx.tool;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.Field;
//import java.util.Arrays;
//
//public class AddClassLoader {
//    private static boolean SetDexElements(ClassLoader mLoader, Object[] dexElementsResut, int conunt) {
//        try {
//            Field pathListField = mLoader.getClass().getSuperclass().getDeclaredField("pathList");
//            if (pathListField != null) {
//                pathListField.setAccessible(true);
//                Object dexPathList = pathListField.get(mLoader);
//                Field dexElementsField = dexPathList.getClass().getDeclaredField("dexElements");
//                if (dexElementsField != null) {
//                    dexElementsField.setAccessible(true);
//                    //先 重新设置一次
//                    dexElementsField.set(dexPathList,dexElementsResut);
//                    //重新 get 用
//                    Object[] dexElements = (Object[]) dexElementsField.get(dexPathList);
//                    if(dexElements.length==conunt&& Arrays.hashCode(dexElements) == Arrays.hashCode(dexElementsResut)){
//                         FileUnits.writelog("[萌块]AddClassLoader 替换 以后的 长度 是 "+dexElements.length);
//                        return true;
//                    }else {
//                         FileUnits.writelog("[萌块]AddClassLoader 合成   长度  "+dexElements.length+"传入 数组 长度   "+conunt);
//
//                         FileUnits.writelog("[萌块]AddClassLoader    dexElements hashCode "+Arrays.hashCode(dexElements)+"  "+Arrays.hashCode(dexElementsResut));
//
//                        return false;
//                    }
//                }else {
//                     FileUnits.writelog("[萌块]AddClassLoader SetDexElements  获取 dexElements == null");
//                }
//            }else {
//                 FileUnits.writelog("[萌块]AddClassLoader SetDexElements  获取 pathList == null");
//            }
//        } catch (NoSuchFieldException e) {
//             FileUnits.writelog("[萌块]AddClassLoader SetDexElements  NoSuchFieldException   "+e.getMessage());
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//             FileUnits.writelog("[萌块]AddClassLoader SetDexElements  IllegalAccessException   "+e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public static void add(ClassLoader classLoader1, ClassLoader classLoader2) {
//        //先拿到 第一个classloader
//        Object[] MyDexClassloader = getClassLoaderElements(classLoader1);
//        //拿到第二个 classloader
//        Object[] otherClassloader = getClassLoaderElements(classLoader2);
//        if (otherClassloader != null && MyDexClassloader != null) {
//            //把两个 数组 DexElements合并 把第一个 dex放在前面
//            // 这样就可以 在需要的时候 先拿到 我们第一个classloader
//            // 首先开辟一个 新的 数组 大小 是前里个大小的 和
//            Object[] combined = (Object[]) Array.newInstance(otherClassloader.getClass().getComponentType(),
//                    MyDexClassloader.length + otherClassloader.length);
//            //将第一个classloader 数组的内容 放到 前面位置
//            System.arraycopy(MyDexClassloader, 0, combined, 0, MyDexClassloader.length);
//            //把 第二个 进行 拼接
//            System.arraycopy(otherClassloader, 0, combined, MyDexClassloader.length, otherClassloader.length);
//            //判断 是否合并 成功
//            if ((MyDexClassloader.length + otherClassloader.length) != combined.length) {
//                FileUnits.writelog("[萌块]AddClassLoader 合并 elements数组 失败  null");
//            }
//            //将 生成的 classloader进行 set回第二个 element数组
//            if (SetDexElements(classLoader2, combined, MyDexClassloader.length + otherClassloader.length)) {
//                FileUnits.writelog("[萌块]AddClassLoader 替换成功");
//            } else {
//                FileUnits.writelog("[萌块]AddClassLoader 替换失败 ");
//            }
//        } else {
//            FileUnits.writelog("[萌块]AddClassLoader 没有 拿到 classloader");
//        }
//    }
//
//    private static Object[] getClassLoaderElements(ClassLoader classLoader){
//            try {
//                Field pathListField = classLoader.getClass().getSuperclass().getDeclaredField("pathList");
//                if (pathListField != null) {
//                    pathListField.setAccessible(true);
//                    Object dexPathList = pathListField.get(classLoader);
//                    Field dexElementsField = dexPathList.getClass().getDeclaredField("dexElements");
//                    if (dexElementsField != null) {
//                        dexElementsField.setAccessible(true);
//                        Object[] dexElements = (Object[]) dexElementsField.get(dexPathList);
//                        if (dexElements != null) {
//                            return dexElements;
//                        } else {
//                            FileUnits.writelog("[萌块]AddClassLoader AddElements  获取 dexElements == null");
//                        }
//                        //ArrayUtils.addAll(first, second);
//                    } else {
//                        FileUnits.writelog("[萌块]AddClassLoader AddElements  获取 dexElements == null");
//                    }
//                } else {
//                    FileUnits.writelog("[萌块]AddClassLoader AddElements  获取 pathList == null");
//                }
//
//
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return null;
//    }
//}