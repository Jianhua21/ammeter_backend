package com.kashuo.kcp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射工具类
 */
@SuppressWarnings("all")
public class ReflectHelper {

    private static Logger logger = LoggerFactory.getLogger(ReflectHelper.class);

    /**
     * 获取类属性字段
     * @param clazz
     * @param includeSuperClass  是否包括父类，true，包括父类；false，不包括父类
     * @return
     */
    public static List<String> fetchFields(Class clazz, boolean includeSuperClass) {
        if (!includeSuperClass) {
            return fetchFields(clazz);
        }
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field.getName());
        }
        Class superClazz = clazz.getSuperclass();
        if (null != superClazz) {
            fields.addAll(fetchFields(superClazz, includeSuperClass));
        }
        return fields;
    }

    /**
     * 获取类属性字段
     */
    public static List<String> fetchFields(Class clazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field.getName());
        }
        return fields;
    }

    /**
     * 通过类对象的 getter 方法获取 属性名及属性值
     */
    public static Map<String, Object> fetchFields(Object object) {

        Map<String, Object> fields = new HashMap<>();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = object.getClass().getMethod(methodName);
                Object value = method.invoke(object);
                fields.put(name, value);
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return fields;
    }

    public static List<String> fetchFields2List(Object object){
        List<String> fields = new ArrayList<>();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = object.getClass().getMethod(methodName);
                Object value = method.invoke(object);
                fields.add(String.valueOf(value));
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return fields;
    }

    /**
     * 通过类对象的 getter 方法获取 属性名及属性值
     */
    public static Object fetchFieldValue(Object object, String name) {
        if (object != null && name != null && name.trim().length() > 0) {
            try {
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = object.getClass().getMethod(methodName);
                return method.invoke(object);
            } catch (Exception e) {
                logger.error("{}", e);
            }
        }
        return null;
    }


    /**
     * 通过 setter 设置类对象 指定属性值
     *
     * @param object 类对象
     * @param name   属性名
     * @param value  属性值
     */
    public static void settingField(Object object, String name, Object value) {
        if (object != null && name != null && name.trim().length() > 0) {
            try {
//                Field field = object.getClass().getDeclaredField(name);
                String settingName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                if (null != value) {
                    Method settingMethod = object.getClass().getMethod(settingName, value.getClass());
                    settingMethod.invoke(object, value);
                }
            } catch (Exception e) {
                logger.error("{}", e);
            }
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (Exception e) {
                // 没有父类时，打印异常堆栈信息
                if (null == clazz.getSuperclass()) {
                    logger.error("{}", e);
                }
            }
        }
        return method;
    }


    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @return 父类中方法的执行结果
     */

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        try {
            if (null != method) {
                //抑制Java对方法进行检查,主要是针对私有方法而言
                method.setAccessible(true);
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 没有父类时，打印异常堆栈信息
                if (null == clazz.getSuperclass()) {
                    logger.error("{}", e);
                }
            }
        }
        return field;
    }


    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        try {
            //抑制Java对其的检查
            field.setAccessible(true);
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */

    public static Object getFieldValue(Object object, String fieldName) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        try {
            //抑制Java对其的检查
            field.setAccessible(true);
            //获取 object 中 field 所代表的属性值
            return field.get(object);

        } catch (Exception e) {
            logger.error("{}", e);
        }
        return null;
    }
}
