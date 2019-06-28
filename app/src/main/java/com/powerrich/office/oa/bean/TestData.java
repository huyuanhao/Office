package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/9.
 */

public class TestData implements Serializable {
    public String title = "";
    public String content = "";
    public String department = "";

    @Override
    public String toString() {
        return title + "   " + content + "  " + department + "\n";
    }
}