package com.kashuo.kcp.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * Created by CTO on 2017/8/30.  copy from kap
 */
public abstract class ValidatorUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 对象校验 失败信息返回结果
     *
     * @param obj 校验对象
     * @return 校验结果
     */
    public static <T> ValidationResult validateEntity(T obj) {
        return validateEntity(validator, obj);
    }

    /**
     * 对象校验 失败信息返回结果
     *
     * @param obj 校验对象
     * @return 校验结果
     */
    public static <T> ValidationResult validateEntity(Validator validator, T obj) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (!set.isEmpty()) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

    /**
     * 对象校验 失败抛出异常
     *
     * @param obj 校验对象
     * @param <T> 对象类型
     */
    public static <T> void validateEntityWithException(T obj) throws ConstraintViolationException {
        validateEntityWithException(validator, obj);
    }

    /**
     * 对象校验 失败抛出异常
     *
     * @param obj 校验对象
     * @param <T> 对象类型
     */
    public static <T> void validateEntityWithException(Validator validator, T obj) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, Default.class);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    /**
     * 验证对象中的部分属性
     *
     * @param obj          校验对象
     * @param propertyName 属性 (i.e. field and getter constraints)
     * @param <T>          对象类型
     */
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        return validateProperty(validator, obj, propertyName);
    }

    /**
     * 验证对象中的部分属性
     *
     * @param obj          校验对象
     * @param propertyName 属性 (i.e. field and getter constraints)
     * @param <T>          对象类型
     */
    public static <T> ValidationResult validateProperty(Validator validator, T obj, String propertyName) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (set != null && set.size() > 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

}

