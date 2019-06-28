package com.powerrich.office.oa.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 文 件 名：MaterialsInfo
 * 描   述：材料信息实体类
 * 作   者：Wangzheng
 * 时   间：2018-6-20 10:10:46
 * 版   权：v1.0
 */
public class MaterialsInfo implements Serializable {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private static final long serialVersionUID = 4861426028982473110L;

    private String materialId;
    private String materialName;
    private String materialSize;
    private String materialFormat;
    private String materialType;
    private String materialDescribe;
    private String materialCopies;
    private String materialForm;
    private String materialNecessity;

    public MaterialsInfo() {
        super();
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(String materialSize) {
        this.materialSize = materialSize;
    }

    public String getMaterialFormat() {
        return materialFormat;
    }

    public void setMaterialFormat(String materialFormat) {
        this.materialFormat = materialFormat;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialDescribe() {
        return materialDescribe;
    }

    public void setMaterialDescribe(String materialDescribe) {
        this.materialDescribe = materialDescribe;
    }

    public String getMaterialCopies() {
        return materialCopies;
    }

    public void setMaterialCopies(String materialCopies) {
        this.materialCopies = materialCopies;
    }

    public String getMaterialForm() {
        return materialForm;
    }

    public void setMaterialForm(String materialForm) {
        this.materialForm = materialForm;
    }

    public String getMaterialNecessity() {
        return materialNecessity;
    }

    public void setMaterialNecessity(String materialNecessity) {
        this.materialNecessity = materialNecessity;
    }
}
