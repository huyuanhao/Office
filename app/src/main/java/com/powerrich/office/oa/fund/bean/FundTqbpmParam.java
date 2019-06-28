package com.powerrich.office.oa.fund.bean;

/**
 * @author PC
 * @date 2019/04/20 16:37
 */
public class FundTqbpmParam {

    /**
     * applyUser : 1
     * bllx : 2
     * businessKey : 3
     * channgel : 4
     * description : 5
     * jyfs : 1
     * processKey : jcr_ltxhtq_sp
     * sdfs : 1
     * sfsp : 0
     * sftj : 0
     * source : 提取业务
     * sptg : 0
     * taskDefinitionKey : sp
     * tenantId : 6
     * tqjebz : 1
     * tqjehj : 26
     * tqlxbm : 27
     * userId : 0
     */

    private String applyUser = FundBean.grzh;
    private String bllx = "2";
    private String businessKey;//流程id
    private String channgel = "app";
    private String description;//姓名+证件号码+退休提取+提取金额
    private String jyfs = "1";
    private String processKey = "jcr_ltxhtq_sp";
    private String sdfs = "1";
    private String sfsp = "0";
    private String sftj = "0";
    private String source = "提取业务";
    private String sptg = "0";
    private String taskDefinitionKey = "sp";
    private String tenantId = "0101";
    private String tqjebz = "1";
    private String tqjehj = (Double.parseDouble(FundBean.grzhye) +Double.parseDouble(FundBean.zjlx))+"" ;;
    private String tqlxbm = "  ";
    private String userId = "0";

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public void setBllx(String bllx) {
        this.bllx = bllx;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public void setChanngel(String channgel) {
        this.channgel = channgel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJyfs(String jyfs) {
        this.jyfs = jyfs;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public void setSdfs(String sdfs) {
        this.sdfs = sdfs;
    }

    public void setSfsp(String sfsp) {
        this.sfsp = sfsp;
    }

    public void setSftj(String sftj) {
        this.sftj = sftj;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSptg(String sptg) {
        this.sptg = sptg;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setTqjebz(String tqjebz) {
        this.tqjebz = tqjebz;
    }

    public void setTqjehj(String tqjehj) {
        this.tqjehj = tqjehj;
    }

    public void setTqlxbm(String tqlxbm) {
        this.tqlxbm = tqlxbm;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyUser() {
        return applyUser == null ? "" : applyUser;
    }

    public String getBllx() {
        return bllx == null ? "" : bllx;
    }

    public String getBusinessKey() {
        return businessKey == null ? "" : businessKey;
    }

    public String getChanngel() {
        return channgel == null ? "" : channgel;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public String getJyfs() {
        return jyfs == null ? "" : jyfs;
    }

    public String getProcessKey() {
        return processKey == null ? "" : processKey;
    }

    public String getSdfs() {
        return sdfs == null ? "" : sdfs;
    }

    public String getSfsp() {
        return sfsp == null ? "" : sfsp;
    }

    public String getSftj() {
        return sftj == null ? "" : sftj;
    }

    public String getSource() {
        return source == null ? "" : source;
    }

    public String getSptg() {
        return sptg == null ? "" : sptg;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey == null ? "" : taskDefinitionKey;
    }

    public String getTenantId() {
        return tenantId == null ? "" : tenantId;
    }

    public String getTqjebz() {
        return tqjebz == null ? "" : tqjebz;
    }

    public String getTqjehj() {
        return tqjehj == null ? "" : tqjehj;
    }

    public String getTqlxbm() {
        return tqlxbm == null ? "" : tqlxbm;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }
}
