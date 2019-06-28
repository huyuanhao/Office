package com.powerrich.office.oa.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.powerrich.office.oa.network.http.HttpRequest;
import com.powerrich.office.oa.tools.Logger;


public class ApiRequest extends HttpRequest {

    private static final String TAG = "ApiRequest";

    private String message = "";

    /**
     * 请求的模块名
     */
    private String actionName = "";

    public String XmlRequestKey = "?wsdl";
    public String XmlRequestValue = "";


    /**
     * 请求的服务器类型（默认1）：1:OA服务器请求，2：门户服务器请求
     */
    private int serviceUrlType = 1;
    /**
     * 文档写的是requestXml，但实际又是xmlrequest，真尼玛坑爹啊；
     */
    public String requestXmlKey = "xmlrequest";
    public String requestXmlValue = "";


    public ApiRequest(String actionName) {
        //每个请求接口的请求地址
        //		setUrl(SystemContext.getServicesURL()+actionName);
        //加 token 什么都可以在默认添加
        //		addHttpHeader("token", "x");

        this.actionName = actionName;
    }

    public String getRequestMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public void send() {
        /*List<Object> files = new ArrayList<Object>();

		if(serviceUrlType==2){
//			requestXmlValue = DESUtil.enctryXml(this.getRequestXml(files));
			requestXmlValue = getRequestXml(files);
		}else{
//			XmlRequestValue = DESUtil.enctryXml(this.getRequestJson(files));
			XmlRequestValue = getRequestJson(files);
		}

		Logger.e(TAG, "XML密文：" + XmlRequestValue);

		//拼接完毕后，需要将参数请求清除
		this.paramNames.clear();
		this.paramValues.clear();

		//追加加密后的参数
		if(serviceUrlType==2){
			addParam(requestXmlKey, requestXmlValue);
		}else{
			addParam(XmlRequestKey, XmlRequestValue);
		}

		addParam("SystemId","1");//标识Android调用
		for (int i = 0; i < files.size(); i++) {
			addParam("file", files.get(i));
		}*/


        //设置请求的地址
    /*	if(serviceUrlType==2){
            //示例： Http://xxx.xxx.xxx.xxx/platform-sso-server/app/smsLogin?requestXml=xxxx
			//其中配置的地址为：“Http://xxx.xxx.xxx.xxx/platform-sso-server/app”
//			this.setUrl(SystemContext.getPortalServicesURL()+"/"+actionName);
			this.setUrl("http://222.184.59.8:8099/ipgs_ha/w"+"/"+actionName);
			
			this.setEncode("UTF-8");	//门户设置UTF-8，否则是乱码返回
			Logger.e(TAG, "请求的门户地址：" + this.getUrl());

		}else{
//			this.setUrl(SystemContext.getServicesURL());
			this.setUrl("http://222.184.59.8:8099/ipgs_ha/w");
			this.setEncode("GBK");	//OA设置GBK，否则是乱码返回
			Logger.e(TAG, "请求的OA地址：" + this.getUrl());
		}*/
//    	if(serviceUrlType == 1){
//    		this.setUrl(Constants.BASE_URL + "/" + actionName);
//
//    		this.setEncode("UTF-8"); // 门户设置UTF-8，否则是乱码返回
//    		Logger.e(TAG, "请求的门户地址：" + this.getUrl());
//    	}

        super.send();
    }


    // 组装XML请求数据
    public String getRequestXml(List<Object> files) {
        //在请求发出之前拼接xml请求字符串
        StringBuilder xmlDatas = new StringBuilder();
        if (files == null) {
            files = new ArrayList<Object>();
        }

        try {

            xmlDatas.append("<Root>");
            xmlDatas.append("<Action>" + actionName + "</Action>");
            xmlDatas.append("<Parameter>");

            if (hasParams()) {
                int length = getParamNames().size();
                for (int i = 0; i < length; i++) {
                    String key = getParamNames().get(i);
                    Object value = getParamValues().get(i);
                    //添加附件参数
                    if (value instanceof File) {
                        files.add(value);
                        continue;
                    } else if (key.startsWith("<")) {
                        xmlDatas.append(key);
                    } else {
                        xmlDatas.append(String.format("<%1$s>%2$s</%1$s>", key, (value == null ? "" : value.toString())));
                    }
                }
            }

            xmlDatas.append("</Parameter>");
            xmlDatas.append("</Root>");

            Logger.e(TAG, "XML明文：" + xmlDatas.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlDatas.toString();
    }

    // 组装XML请求数据
    public String getRequestJson(List<Object> files) {
        StringBuilder jsonDatas = new StringBuilder(String.format("%s", actionName));
        if (files == null) {
            files = new ArrayList<Object>();
        }
        try {
            if (hasParams()) {
                int length = getParamNames().size();
                jsonDatas.append(",").append("[");
                for (int i = 0; i < length; i++) {
                    String key = getParamNames().get(i);
                    Object value = getParamValues().get(i);
                    jsonDatas.append(String.format("%s", key)).append(",");
                    jsonDatas.append(String.format("%s", (value == null ? "" : value.toString())));
                }
                jsonDatas.append("]");
            }
            Logger.e(TAG, "XML明文：" + jsonDatas.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonDatas.toString();
    }

    /**
     * 1:OA服务器请求，2：门户服务器请求
     *
     * @return
     */
    public int getServiceUrlType() {
        return serviceUrlType;
    }

    /**
     * 1:OA服务器请求，2：门户服务器请求
     *
     * @param serviceUrlType
     */
    public void setServiceUrlType(int serviceUrlType) {
        this.serviceUrlType = serviceUrlType;
    }

}
