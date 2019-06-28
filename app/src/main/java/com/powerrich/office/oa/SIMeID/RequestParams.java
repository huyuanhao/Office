package com.powerrich.office.oa.SIMeID;

import java.io.Serializable;

public class RequestParams implements Serializable {

    public String envTag = "";
    public String url = "";

    public RequestParams(String envTag, String url) {

        this.envTag = envTag;
        this.url = url;

    }

}
