package com.powerrich.office.oa.fund.bean;

/**
 * @author PC
 * @date 2019/04/24 10:45
 */
public class FundTqlixi {

    /**
     * success : false
     * msg : null
     * totalcount : 0
     * results : null
     * erros : null
     * vdMapList : null
     * data : {"grbh":null,"dkqk":null,"tqrq":null,"tqjehj":0,"dkhth":null,"zjhm":null,"bm":null,"mc":null,"minsybj":0,"minhjcs":0,"maxtqbl":0,"tqzq":null,"tqcs":0,"sfxd":0,"age1":0,"age2":0,"tqsx":0,"minyhcs":0,"bm1":null,"mc1":null,"count":0,"page":0,"size":0,"userid":0,"dwzh":"030123","dwmc":null,"grzh":"000008481092","xingming":null,"grzhye":0,"grzhzt":null,"fwxxdz":null,"fwmj":0,"dkbj":0,"dkye":0,"yhbxje":0,"zjlx":87.8,"zxbm":null,"jgbm":"0101","khbh":null,"zhbh":null,"ywfl":null,"ywlb":null,"blqd":"app","ffbm":"08","sfqyxd":null,"sftqsx":null,"msg":null,"ret":0,"tqlx":null,"tqje":0,"tqjedws":0,"maxtqe":0,"dkzt":null,"dkffrq":null,"dkjqrq":null,"fwzj":0,"gfsfk":0,"lnljtqbj":0,"gfhtbh":null}
     */

    private boolean success;
    private String  msg;
    private String totalcount;
    private String results;
    private String erros;
    private String vdMapList;
    private DataBean data;

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public String getTotalcount() {
        return totalcount == null ? "" : totalcount;
    }

    public String getResults() {
        return results == null ? "" : results;
    }

    public String getErros() {
        return erros == null ? "" : erros;
    }

    public String getVdMapList() {
        return vdMapList == null ? "" : vdMapList;
    }

    public DataBean getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setErros(String erros) {
        this.erros = erros;
    }

    public void setVdMapList(String vdMapList) {
        this.vdMapList = vdMapList;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * grbh : null
         * dkqk : null
         * tqrq : null
         * tqjehj : 0.0
         * dkhth : null
         * zjhm : null
         * bm : null
         * mc : null
         * minsybj : 0.0
         * minhjcs : 0
         * maxtqbl : 0.0
         * tqzq : null
         * tqcs : 0
         * sfxd : 0
         * age1 : 0
         * age2 : 0
         * tqsx : 0
         * minyhcs : 0
         * bm1 : null
         * mc1 : null
         * count : 0
         * page : 0
         * size : 0
         * userid : 0
         * dwzh : 030123
         * dwmc : null
         * grzh : 000008481092
         * xingming : null
         * grzhye : 0.0
         * grzhzt : null
         * fwxxdz : null
         * fwmj : 0.0
         * dkbj : 0.0
         * dkye : 0.0
         * yhbxje : 0.0
         * zjlx : 87.8
         * zxbm : null
         * jgbm : 0101
         * khbh : null
         * zhbh : null
         * ywfl : null
         * ywlb : null
         * blqd : app
         * ffbm : 08
         * sfqyxd : null
         * sftqsx : null
         * msg : null
         * ret : 0
         * tqlx : null
         * tqje : 0.0
         * tqjedws : 0
         * maxtqe : 0.0
         * dkzt : null
         * dkffrq : null
         * dkjqrq : null
         * fwzj : 0.0
         * gfsfk : 0.0
         * lnljtqbj : 0.0
         * gfhtbh : null
         */

        private String grbh;
        private String dkqk;
        private String tqrq;
        private String tqjehj;
        private String dkhth;
        private String zjhm;
        private String bm;
        private String mc;
        private String minsybj;
        private String minhjcs;
        private String maxtqbl;
        private String tqzq;
        private String tqcs;
        private String sfxd;
        private String age1;
        private String age2;
        private String tqsx;
        private String minyhcs;
        private String bm1;
        private String mc1;
        private String count;
        private String page;
        private String size;
        private String userid;
        private String dwzh;
        private String dwmc;
        private String grzh;
        private String xingming;
        private String grzhye;
        private String grzhzt;
        private String fwxxdz;
        private String fwmj;
        private String dkbj;
        private String dkye;
        private String yhbxje;
        private String zjlx;
        private String zxbm;
        private String jgbm;
        private String khbh;
        private String zhbh;
        private String ywfl;
        private String ywlb;
        private String blqd;
        private String ffbm;
        private String sfqyxd;
        private String sftqsx;
        private String msg;
        private String ret;
        private String tqlx;
        private String tqje;
        private String tqjedws;
        private String maxtqe;
        private String dkzt;
        private String dkffrq;
        private String dkjqrq;
        private String fwzj;
        private String gfsfk;
        private String lnljtqbj;
        private String gfhtbh;

        public String getGrbh() {
            return grbh == null ? "" : grbh;
        }

        public String getDkqk() {
            return dkqk == null ? "" : dkqk;
        }

        public String getTqrq() {
            return tqrq == null ? "" : tqrq;
        }

        public String getTqjehj() {
            return tqjehj == null ? "" : tqjehj;
        }

        public String getDkhth() {
            return dkhth == null ? "" : dkhth;
        }

        public String getZjhm() {
            return zjhm == null ? "" : zjhm;
        }

        public String getBm() {
            return bm == null ? "" : bm;
        }

        public String getMc() {
            return mc == null ? "" : mc;
        }

        public String getMinsybj() {
            return minsybj == null ? "" : minsybj;
        }

        public String getMinhjcs() {
            return minhjcs == null ? "" : minhjcs;
        }

        public String getMaxtqbl() {
            return maxtqbl == null ? "" : maxtqbl;
        }

        public String getTqzq() {
            return tqzq == null ? "" : tqzq;
        }

        public String getTqcs() {
            return tqcs == null ? "" : tqcs;
        }

        public String getSfxd() {
            return sfxd == null ? "" : sfxd;
        }

        public String getAge1() {
            return age1 == null ? "" : age1;
        }

        public String getAge2() {
            return age2 == null ? "" : age2;
        }

        public String getTqsx() {
            return tqsx == null ? "" : tqsx;
        }

        public String getMinyhcs() {
            return minyhcs == null ? "" : minyhcs;
        }

        public String getBm1() {
            return bm1 == null ? "" : bm1;
        }

        public String getMc1() {
            return mc1 == null ? "" : mc1;
        }

        public String getCount() {
            return count == null ? "" : count;
        }

        public String getPage() {
            return page == null ? "" : page;
        }

        public String getSize() {
            return size == null ? "" : size;
        }

        public String getUserid() {
            return userid == null ? "" : userid;
        }

        public String getDwzh() {
            return dwzh == null ? "" : dwzh;
        }

        public String getDwmc() {
            return dwmc == null ? "" : dwmc;
        }

        public String getGrzh() {
            return grzh == null ? "" : grzh;
        }

        public String getXingming() {
            return xingming == null ? "" : xingming;
        }

        public String getGrzhye() {
            return grzhye == null ? "" : grzhye;
        }

        public String getGrzhzt() {
            return grzhzt == null ? "" : grzhzt;
        }

        public String getFwxxdz() {
            return fwxxdz == null ? "" : fwxxdz;
        }

        public String getFwmj() {
            return fwmj == null ? "" : fwmj;
        }

        public String getDkbj() {
            return dkbj == null ? "" : dkbj;
        }

        public String getDkye() {
            return dkye == null ? "" : dkye;
        }

        public String getYhbxje() {
            return yhbxje == null ? "" : yhbxje;
        }

        public String getZjlx() {
            return zjlx == null ? "" : zjlx;
        }

        public String getZxbm() {
            return zxbm == null ? "" : zxbm;
        }

        public String getJgbm() {
            return jgbm == null ? "" : jgbm;
        }

        public String getKhbh() {
            return khbh == null ? "" : khbh;
        }

        public String getZhbh() {
            return zhbh == null ? "" : zhbh;
        }

        public String getYwfl() {
            return ywfl == null ? "" : ywfl;
        }

        public String getYwlb() {
            return ywlb == null ? "" : ywlb;
        }

        public String getBlqd() {
            return blqd == null ? "" : blqd;
        }

        public String getFfbm() {
            return ffbm == null ? "" : ffbm;
        }

        public String getSfqyxd() {
            return sfqyxd == null ? "" : sfqyxd;
        }

        public String getSftqsx() {
            return sftqsx == null ? "" : sftqsx;
        }

        public String getMsg() {
            return msg == null ? "" : msg;
        }

        public String getRet() {
            return ret == null ? "" : ret;
        }

        public String getTqlx() {
            return tqlx == null ? "" : tqlx;
        }

        public String getTqje() {
            return tqje == null ? "" : tqje;
        }

        public String getTqjedws() {
            return tqjedws == null ? "" : tqjedws;
        }

        public String getMaxtqe() {
            return maxtqe == null ? "" : maxtqe;
        }

        public String getDkzt() {
            return dkzt == null ? "" : dkzt;
        }

        public String getDkffrq() {
            return dkffrq == null ? "" : dkffrq;
        }

        public String getDkjqrq() {
            return dkjqrq == null ? "" : dkjqrq;
        }

        public String getFwzj() {
            return fwzj == null ? "" : fwzj;
        }

        public String getGfsfk() {
            return gfsfk == null ? "" : gfsfk;
        }

        public String getLnljtqbj() {
            return lnljtqbj == null ? "" : lnljtqbj;
        }

        public String getGfhtbh() {
            return gfhtbh == null ? "" : gfhtbh;
        }

        public void setGrbh(String grbh) {
            this.grbh = grbh;
        }

        public void setDkqk(String dkqk) {
            this.dkqk = dkqk;
        }

        public void setTqrq(String tqrq) {
            this.tqrq = tqrq;
        }

        public void setTqjehj(String tqjehj) {
            this.tqjehj = tqjehj;
        }

        public void setDkhth(String dkhth) {
            this.dkhth = dkhth;
        }

        public void setZjhm(String zjhm) {
            this.zjhm = zjhm;
        }

        public void setBm(String bm) {
            this.bm = bm;
        }

        public void setMc(String mc) {
            this.mc = mc;
        }

        public void setMinsybj(String minsybj) {
            this.minsybj = minsybj;
        }

        public void setMinhjcs(String minhjcs) {
            this.minhjcs = minhjcs;
        }

        public void setMaxtqbl(String maxtqbl) {
            this.maxtqbl = maxtqbl;
        }

        public void setTqzq(String tqzq) {
            this.tqzq = tqzq;
        }

        public void setTqcs(String tqcs) {
            this.tqcs = tqcs;
        }

        public void setSfxd(String sfxd) {
            this.sfxd = sfxd;
        }

        public void setAge1(String age1) {
            this.age1 = age1;
        }

        public void setAge2(String age2) {
            this.age2 = age2;
        }

        public void setTqsx(String tqsx) {
            this.tqsx = tqsx;
        }

        public void setMinyhcs(String minyhcs) {
            this.minyhcs = minyhcs;
        }

        public void setBm1(String bm1) {
            this.bm1 = bm1;
        }

        public void setMc1(String mc1) {
            this.mc1 = mc1;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setDwzh(String dwzh) {
            this.dwzh = dwzh;
        }

        public void setDwmc(String dwmc) {
            this.dwmc = dwmc;
        }

        public void setGrzh(String grzh) {
            this.grzh = grzh;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }

        public void setGrzhye(String grzhye) {
            this.grzhye = grzhye;
        }

        public void setGrzhzt(String grzhzt) {
            this.grzhzt = grzhzt;
        }

        public void setFwxxdz(String fwxxdz) {
            this.fwxxdz = fwxxdz;
        }

        public void setFwmj(String fwmj) {
            this.fwmj = fwmj;
        }

        public void setDkbj(String dkbj) {
            this.dkbj = dkbj;
        }

        public void setDkye(String dkye) {
            this.dkye = dkye;
        }

        public void setYhbxje(String yhbxje) {
            this.yhbxje = yhbxje;
        }

        public void setZjlx(String zjlx) {
            this.zjlx = zjlx;
        }

        public void setZxbm(String zxbm) {
            this.zxbm = zxbm;
        }

        public void setJgbm(String jgbm) {
            this.jgbm = jgbm;
        }

        public void setKhbh(String khbh) {
            this.khbh = khbh;
        }

        public void setZhbh(String zhbh) {
            this.zhbh = zhbh;
        }

        public void setYwfl(String ywfl) {
            this.ywfl = ywfl;
        }

        public void setYwlb(String ywlb) {
            this.ywlb = ywlb;
        }

        public void setBlqd(String blqd) {
            this.blqd = blqd;
        }

        public void setFfbm(String ffbm) {
            this.ffbm = ffbm;
        }

        public void setSfqyxd(String sfqyxd) {
            this.sfqyxd = sfqyxd;
        }

        public void setSftqsx(String sftqsx) {
            this.sftqsx = sftqsx;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public void setTqlx(String tqlx) {
            this.tqlx = tqlx;
        }

        public void setTqje(String tqje) {
            this.tqje = tqje;
        }

        public void setTqjedws(String tqjedws) {
            this.tqjedws = tqjedws;
        }

        public void setMaxtqe(String maxtqe) {
            this.maxtqe = maxtqe;
        }

        public void setDkzt(String dkzt) {
            this.dkzt = dkzt;
        }

        public void setDkffrq(String dkffrq) {
            this.dkffrq = dkffrq;
        }

        public void setDkjqrq(String dkjqrq) {
            this.dkjqrq = dkjqrq;
        }

        public void setFwzj(String fwzj) {
            this.fwzj = fwzj;
        }

        public void setGfsfk(String gfsfk) {
            this.gfsfk = gfsfk;
        }

        public void setLnljtqbj(String lnljtqbj) {
            this.lnljtqbj = lnljtqbj;
        }

        public void setGfhtbh(String gfhtbh) {
            this.gfhtbh = gfhtbh;
        }
    }
}
