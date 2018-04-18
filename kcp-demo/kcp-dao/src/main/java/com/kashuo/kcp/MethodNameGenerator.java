package com.kashuo.kcp;

import org.mybatis.generator.api.IntrospectedTable;

public class MethodNameGenerator implements org.mybatis.generator.api.DAOMethodNameCalculator {
    @Override
    public String getInsertMethodName(IntrospectedTable introspectedTable) {
        return "insert";
    }

    @Override
    public String getInsertSelectiveMethodName(IntrospectedTable introspectedTable) {
        return "insertSelective";
    }

    @Override
    public String getUpdateByPrimaryKeyWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        return "updateById";
    }

    @Override
    public String getUpdateByPrimaryKeyWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        return "updateByIdWithBlob";
    }

    @Override
    public String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable) {
        return "update";
    }

    @Override
    public String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        return "findById";
    }

    @Override
    public String getSelectByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        return "findByExampleWithoutBlob";
    }

    @Override
    public String getSelectByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        return "findByExampleWithBlob";
    }

    @Override
    public String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        return "delete";
    }

    @Override
    public String getDeleteByExampleMethodName(IntrospectedTable introspectedTable) {
        return "deleteByExample";
    }

    @Override
    public String getCountByExampleMethodName(IntrospectedTable introspectedTable) {
        return "countByExample";
    }

    @Override
    public String getUpdateByExampleSelectiveMethodName(IntrospectedTable introspectedTable) {
        return "updateByExampleSelective";
    }

    @Override
    public String getUpdateByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        return "updateByExampleWithBlob";
    }

    @Override
    public String getUpdateByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        return "updateByExampleWithoutBlob";
    }
}
