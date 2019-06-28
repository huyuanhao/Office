package com.yt.simpleframe.http.bean.entity;

import com.google.gson.Gson;

/**
 * //逾期未还款明细查询
 * @author AlienChao
 * @date 2019/04/20 14:28
 */
public class ReFundYQCXInfo {


    /**
     * yhny : 201903
     * yhqs : 147
     * yhrq : 2019-03-01
     * yqbj : 433.33
     * yqbjhj : 648.12
     * yqfx : 3
     * yqfxhj : 4.86
     * yqlx : 181.91
     * yqlxhj : 181.91
     */

    private String yhny;
    private String yhqs;
    private String yhrq;
    private String yqbj;
    private String yqbjhj;
    private String yqfx;
    private String yqfxhj;
    private String yqlx;
    private String yqlxhj;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getYhny() {
        return yhny;
    }

    public void setYhny(String yhny) {
        this.yhny = yhny;
    }

    public String getYhqs() {
        return yhqs;
    }

    public void setYhqs(String yhqs) {
        this.yhqs = yhqs;
    }

    public String getYhrq() {
        return yhrq;
    }

    public void setYhrq(String yhrq) {
        this.yhrq = yhrq;
    }

    public String getYqbj() {
        return yqbj;
    }

    public void setYqbj(String yqbj) {
        this.yqbj = yqbj;
    }

    public String getYqbjhj() {
        return yqbjhj;
    }

    public void setYqbjhj(String yqbjhj) {
        this.yqbjhj = yqbjhj;
    }

    public String getYqfx() {
        return yqfx;
    }

    public void setYqfx(String yqfx) {
        this.yqfx = yqfx;
    }

    public String getYqfxhj() {
        return yqfxhj;
    }

    public void setYqfxhj(String yqfxhj) {
        this.yqfxhj = yqfxhj;
    }

    public String getYqlx() {
        return yqlx;
    }

    public void setYqlx(String yqlx) {
        this.yqlx = yqlx;
    }

    public String getYqlxhj() {
        return yqlxhj;
    }

    public void setYqlxhj(String yqlxhj) {
        this.yqlxhj = yqlxhj;
    }
}
