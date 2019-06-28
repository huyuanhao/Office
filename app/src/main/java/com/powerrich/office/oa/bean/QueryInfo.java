package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * @author dir_wang
 * @title 办件查询类
 * @date 2016-12-23下午13:56:56
 */
public class QueryInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5260988631330065985L;

    private String itemName;
    private String formUrl;
    private String desDate;
    private String userName;
    private String processState;
    private String prokeyId;
    private String content;
    private String key;
    private String value;
    private String fileNo;
    private String fileName;
    private String filePath;
    private String fileSize;


    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getDesDate() {
        return desDate;
    }

    public void setDesDate(String desDate) {
        this.desDate = desDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getProkeyId() {
        return prokeyId;
    }

    public void setProkeyId(String prokeyId) {
        this.prokeyId = prokeyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
