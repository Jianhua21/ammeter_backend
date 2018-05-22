package com.kashuo.kcp.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CTO on 2017/8/24.
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static <T> T copy(Object obj, T x) {
        try {
            Class xClass = x.getClass();
            Field[] xFields = xClass.getDeclaredFields();
            Class objClass = obj.getClass();
            Field[] objFields = objClass.getDeclaredFields();
            for (Field xfield : xFields) {
                for (Field objField : objFields) {
                    if (xfield.getName() == objField.getName()) {
                        PropertyDescriptor pdGet = new PropertyDescriptor(objField.getName(),
                                objClass);
                        Method getMethod = pdGet.getReadMethod();
                        Object o = getMethod.invoke(obj);

                        PropertyDescriptor pdSet = new PropertyDescriptor(xfield.getName(), xClass);
                        Method setMethod = pdSet.getWriteMethod();//获得写方法
                        setMethod.invoke(x, o);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("beanUtil copy exception");
        }


        return x;
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }

}


