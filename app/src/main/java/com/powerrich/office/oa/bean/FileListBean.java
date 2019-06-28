package com.powerrich.office.oa.bean;

import com.powerrich.office.oa.tools.BeanUtils;

/**
 * 文 件 名：FileListBean
 * 描   述：附件列表实体类
 * 作   者：Wangzheng
 * 时   间：2018/2/7
 * 版   权：v1.0
 */
public class FileListBean {
    private String compFileName;
    private String fileId;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String hdfsFileName;
    private String prokeyId;
    private String fileState;

    public String getCompFileName() {
        return compFileName;
    }

    public void setCompFileName(String compFileName) {
        this.compFileName = compFileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public Long getByteSize() {
        if (!BeanUtils.isEmptyStr(fileSize)) {
            if (fileSize.endsWith("M")) {
                return ((long) Float.parseFloat(fileSize.split("M")[0])) * 1024 * 1024;
            }
            if (fileSize.endsWith("m")) {
                return ((long) Float.parseFloat(fileSize.split("m")[0])) * 1024 * 1024;
            }
            if (fileSize.endsWith("KB") || fileSize.endsWith("K")) {
                return ((long) Float.parseFloat(fileSize.split("K")[0])) * 1024;
            }
            if (fileSize.endsWith("kb") || fileSize.endsWith("k")) {
                return ((long) Float.parseFloat(fileSize.split("k")[0])) * 1024;
            }
        }
        return BeanUtils.isEmptyStr(fileSize) ? -1 : ((long) Float.parseFloat(fileSize));
    }

    public void setByteSize(String fileSize) {
        this.fileSize = fileSize;
    }


    public String getHdfsFileName() {
        return hdfsFileName;
    }

    public void setHdfsFileName(String hdfsFileName) {
        this.hdfsFileName = hdfsFileName;
    }

    public String getProkeyId() {
        return prokeyId;
    }

    public void setProkeyId(String prokeyId) {
        this.prokeyId = prokeyId;
    }

    public String getFileState() {
        return fileState;
    }

    public void setFileState(String fileState) {
        this.fileState = fileState;
    }
}
