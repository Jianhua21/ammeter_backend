package com.kashuo.kcp.dao.condition;


import com.kashuo.kcp.domain.AmmeterDepartment;

import java.util.ArrayList;
import java.util.List;

public class DepartmentTree {

    private Integer id;

    private String departmentName;

    private Byte departmentType;

    private String description;

    private Byte status;

    private String levelCode;

    private Integer depth;

    private Integer totalNum;

    private List<DepartmentTree> nodes = new ArrayList<>();

    private DepartmentTree() {
        super();
    }

    public DepartmentTree(String levelCode, String name) {
        this.levelCode = levelCode;
        this.departmentName = name;
    }

    public DepartmentTree buildTree(List<AmmeterDepartment> allList) {
        this.setNodes(queryNodeList(this.getLevelCode(), allList));
        return this;
    }

    private List<DepartmentTree> queryNodeList(String parentLevelCode, List<AmmeterDepartment> allList) {
        List<DepartmentTree> treeList = new ArrayList<>();
        for (AmmeterDepartment department : allList) {
            if (department.getLevelCode() == null) {
                continue;
            }
            DepartmentTree departmentTree = department2Tree(department);
            String levelCode = departmentTree.getLevelCode();
            if (levelCode.startsWith(parentLevelCode) && levelCode.length() - parentLevelCode.length() == 3) {
                departmentTree.setNodes(queryNodeList(department.getLevelCode(), allList));
                treeList.add(departmentTree);
            }
        }
        return treeList;
    }

    private DepartmentTree department2Tree(AmmeterDepartment department) {
        DepartmentTree node = new DepartmentTree();
        node.setId(department.getId());
        node.setDepartmentName(department.getDepartmentName());
        node.setDepartmentType(department.getDepartmentType());
        node.setDescription(department.getDescription());
        node.setLevelCode(department.getLevelCode());
        node.setStatus(department.getStatus());
        node.setDepth(department.getLevelCode().length() / 3);
        return node;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Byte getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(Byte departmentType) {
        this.departmentType = departmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public List<DepartmentTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<DepartmentTree> nodes) {
        this.nodes = nodes;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
