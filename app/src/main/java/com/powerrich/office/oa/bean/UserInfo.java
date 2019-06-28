package com.powerrich.office.oa.bean;

import com.google.gson.Gson;
import com.powerrich.office.oa.tools.BeanUtils;

import java.io.Serializable;
import java.util.List;

/** 用户信息*/
public class UserInfo implements Serializable{
	/**
	 * DATA : {"ADDR":"","APPLY_TIME":"","AUDIT_IDEA":"","AUDIT_STATE":"","AUDIT_TIME":"","AUDIT_USER":"","AUDIT_USERNAME":"",
	 * "BUSINESSLICENCE":"","CARD_CODE":"","COMPANYNAME":"","CSRQ":"","EMAIL":"","FRDB":"","FRDB_SFZHM":"","FR_PHONE_NUM":"",
	 * "GLZH_ID":"","GSDJH":"","GSZZFILE":"","GSZZFILE_NAME":"","IDCARD":"","IDCARDFILE":"","IDCARD_ADDRESS":"","IDCARD_CON":"",
	 * "IDCARD_FACE":"","IDSEXT_EMAIL":"","IDSEXT_IDCARD":"","IDSEXT_NAME":"","IDSEXT_SEX":"","IS_REAL_NAME":"","JGLX":"",
	 * "LINKMAN":"","LOCATION_NUM":"","MOBILE_NUM":"","MZ":"","OVERTIME":"","QYTXDZ":"","REALNAME":"","REGDATE":1511193600000,
	 * "SEX":"","SFSMRZ":"0","STATE":"1","TEL":"","UNIT":"","UNITADD":"","USERDUTY":"0","USERID":"201711211743293361313127506",
	 * "USERNAME":"qqq","USERPWD":"@]4y5?6m7+8y9","VALIDATE_TYPE":"","VALIDNUM":"","ZCLX":""}
	 * code : 0
	 * message : 操作成功
	 */

	private DATABean DATA;
	private String code;
	private String message;

	private String authtoken;
	private String siteNo;
	private String userType;
	/**
	 * 社保接口登录返回
	 */
	private String sessionId;
	private String sysNo;
	private String sysCode = "0";
	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}

	public String getSiteNo() {
		return siteNo;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}

	public String getAuthtoken() {
		return authtoken;
	}

	public DATABean getDATA() {
		return DATA;
	}

	public void setDATA(DATABean DATA) {
		this.DATA = DATA;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId){
		this.sessionId = sessionId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysNo() {
		return sysNo;
	}

	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}

	public static class DATABean implements Serializable{
		/**
		 * ADDR :
		 * APPEIDCODE :
		 * APPLY_TIME :
		 * AUDIT_IDEA :
		 * AUDIT_STATE :
		 * AUDIT_TIME :
		 * AUDIT_USER :
		 * AUDIT_USERNAME :
		 * BUSINESSLICENCE : 91360622MA35FWPH4W
		 * CARD_CODE :
		 * COMPANYNAME : 余江县米财工贸有限公司
		 * COMPANY_USERID :
		 * COMPANY_USERNAME :
		 * CSRQ :
		 * EIDCODE :
		 * EMAIL :
		 * FACE_RECOGNITION_NAME :
		 * FACE_RECOGNITION_PATH :
		 * FILEPATH :
		 * FRDB : 曾米财
		 * FRDB_SFZHM : 36062219811202455X
		 * FR_PHONE_NUM :
		 * GLZH_ID :
		 * GRINFOID :
		 * GR_EMAIL : 850773882@qq.com
		 * GR_REALNAME : 姜胜超
		 * GR_REALPHONE : 17762560406
		 * GSDJH :
		 * GSZZFILE :
		 * GSZZFILE_NAME :
		 * HEADPORTRAIT_DOWNPATH :
		 * HEADPORTRAIT_FILENAME :
		 * IDCARD :
		 * IDCARDFILE :
		 * IDCARD_ADDRESS :
		 * IDCARD_CON :
		 * IDCARD_CON_NAME :
		 * IDCARD_FACE :
		 * IDCARD_FACE_NAME :
		 * IDSEXT_EMAIL :
		 * IDSEXT_IDCARD :
		 * IDSEXT_NAME :
		 * IDSEXT_SEX :
		 * ISFIRST_LOGIN :
		 * ISLINKED :
		 * IS_REAL_NAME :
		 * JGLX :
		 * LASTLOGINTIME :
		 * LINKMAN :
		 * LOCATION_NUM :
		 * MOBILE_NUM :
		 * MZ :
		 * OVERTIME :
		 * QYLXDH :
		 * QYTXDZ : 江西省余江县洪湖乡财家山猪场
		 * REALNAME :
		 * REGDATE : 1542683409901
		 * REGISTERTIME : 2018-11-20 11:06:47
		 * SEX :
		 * SFSMRZ : 1
		 * SHOTFILENAME :
		 * SMRZ_CHOICE : 0
		 * STATE : 1
		 * TEL :
		 * UNIT :
		 * UNITADD :
		 * UPDATETIME :
		 * USERDUTY : 1
		 * USERID : 8a84b683672b56b101672f15a8290041
		 * USERNAME : frceshi
		 * USERPWD :
		 * VALIDATE_TYPE :
		 * VALIDNUM :
		 * XTYHLX :
		 * ZCLX :
		 */

		private String ADDR;
		private String APPEIDCODE;
		private String APPLY_TIME;
		private String AUDIT_IDEA;
		private String AUDIT_STATE;
		private String AUDIT_TIME;
		private String AUDIT_USER;
		private String AUDIT_USERNAME;
		private String BUSINESSLICENCE;
		private String CARD_CODE;
		private String COMPANYNAME;
		private String COMPANY_USERID;
		private String COMPANY_USERNAME;
		private String CSRQ;
		private String EIDCODE;
		private String EMAIL;
		private String FACE_RECOGNITION_NAME;
		private String FACE_RECOGNITION_PATH;
		private String FILEPATH;
		private String FRDB;
		private String FRDB_SFZHM;
		private String FR_PHONE_NUM;
		private String GLZH_ID;
		private String GRINFOID;
		private String GR_EMAIL;
		private String GR_REALNAME;
		private String GR_REALPHONE;
		private String GSDJH;
		private String GSZZFILE;
		private String GSZZFILE_NAME;
		private String HEADPORTRAIT_DOWNPATH;
		private String HEADPORTRAIT_FILENAME;
		private String IDCARD;
		private String IDCARDFILE;
		private String IDCARD_ADDRESS;
		private String IDCARD_CON;
		private String IDCARD_CON_NAME;
		private String IDCARD_FACE;
		private String IDCARD_FACE_NAME;
		private String IDSEXT_EMAIL;
		private String IDSEXT_IDCARD;
		private String IDSEXT_NAME;
		private String IDSEXT_SEX;
		private String ISFIRST_LOGIN;
		private String ISLINKED;
		private String IS_REAL_NAME;
		private String JGLX;
		private String LASTLOGINTIME;
		private String LINKMAN;
		private String LOCATION_NUM;
		private String MOBILE_NUM;
		private String MZ;
		private String OVERTIME;
		private String QYLXDH;
		private String QYTXDZ;
		private String REALNAME;
		private long REGDATE;
		private String REGISTERTIME;
		private String SEX;
		private String SFSMRZ;  //1 认证
		private String SHOTFILENAME;
		private String SMRZ_CHOICE;
		private String STATE;
		private String TEL;
		private String UNIT;
		private String UNITADD;
		private String UPDATETIME;
		private String USERDUTY;
		private String USERID;
		private String USERNAME;
		private String USERPWD;
		private String VALIDATE_TYPE;
		private String VALIDNUM;
		private String XTYHLX;
		private String ZCLX;

		private List<CompanyInfo> COMPANYLIST;

		public List<CompanyInfo> getCOMPANYLIST() {
			return COMPANYLIST;
		}

		public void setCOMPANYLIST(List<CompanyInfo> COMPANYLIST) {
			this.COMPANYLIST = COMPANYLIST;
		}

		public String getADDR() {
			return ADDR;
		}

		public void setADDR(String ADDR) {
			this.ADDR = ADDR;
		}

		public String getAPPEIDCODE() {
			return APPEIDCODE;
		}

		public void setAPPEIDCODE(String APPEIDCODE) {
			this.APPEIDCODE = APPEIDCODE;
		}

		public String getAPPLY_TIME() {
			return APPLY_TIME;
		}

		public void setAPPLY_TIME(String APPLY_TIME) {
			this.APPLY_TIME = APPLY_TIME;
		}

		public String getAUDIT_IDEA() {
			return AUDIT_IDEA;
		}

		public void setAUDIT_IDEA(String AUDIT_IDEA) {
			this.AUDIT_IDEA = AUDIT_IDEA;
		}

		public String getAUDIT_STATE() {
			return AUDIT_STATE;
		}

		public void setAUDIT_STATE(String AUDIT_STATE) {
			this.AUDIT_STATE = AUDIT_STATE;
		}

		public String getAUDIT_TIME() {
			return AUDIT_TIME;
		}

		public void setAUDIT_TIME(String AUDIT_TIME) {
			this.AUDIT_TIME = AUDIT_TIME;
		}

		public String getAUDIT_USER() {
			return AUDIT_USER;
		}

		public void setAUDIT_USER(String AUDIT_USER) {
			this.AUDIT_USER = AUDIT_USER;
		}

		public String getAUDIT_USERNAME() {
			return AUDIT_USERNAME;
		}

		public void setAUDIT_USERNAME(String AUDIT_USERNAME) {
			this.AUDIT_USERNAME = AUDIT_USERNAME;
		}

		public String getBUSINESSLICENCE() {
			return BUSINESSLICENCE;
		}

		public void setBUSINESSLICENCE(String BUSINESSLICENCE) {
			this.BUSINESSLICENCE = BUSINESSLICENCE;
		}

		public String getCARD_CODE() {
			return CARD_CODE;
		}

		public void setCARD_CODE(String CARD_CODE) {
			this.CARD_CODE = CARD_CODE;
		}

		public String getCOMPANYNAME() {
			return COMPANYNAME;
		}

		public void setCOMPANYNAME(String COMPANYNAME) {
			this.COMPANYNAME = COMPANYNAME;
		}

		public String getCOMPANY_USERID() {
			return COMPANY_USERID;
		}

		public void setCOMPANY_USERID(String COMPANY_USERID) {
			this.COMPANY_USERID = COMPANY_USERID;
		}

		public String getCOMPANY_USERNAME() {
			return COMPANY_USERNAME;
		}

		public void setCOMPANY_USERNAME(String COMPANY_USERNAME) {
			this.COMPANY_USERNAME = COMPANY_USERNAME;
		}

		public String getCSRQ() {
			return CSRQ;
		}

		public void setCSRQ(String CSRQ) {
			this.CSRQ = CSRQ;
		}

		public String getEIDCODE() {
			return EIDCODE;
		}

		public void setEIDCODE(String EIDCODE) {
			this.EIDCODE = EIDCODE;
		}

		public String getEMAIL() {
			return EMAIL;
		}

		public void setEMAIL(String EMAIL) {
			this.EMAIL = EMAIL;
		}

		public String getFACE_RECOGNITION_NAME() {
			return FACE_RECOGNITION_NAME;
		}

		public void setFACE_RECOGNITION_NAME(String FACE_RECOGNITION_NAME) {
			this.FACE_RECOGNITION_NAME = FACE_RECOGNITION_NAME;
		}

		public String getFACE_RECOGNITION_PATH() {
			return FACE_RECOGNITION_PATH;
		}

		public void setFACE_RECOGNITION_PATH(String FACE_RECOGNITION_PATH) {
			this.FACE_RECOGNITION_PATH = FACE_RECOGNITION_PATH;
		}

		public String getFILEPATH() {
			return FILEPATH;
		}

		public void setFILEPATH(String FILEPATH) {
			this.FILEPATH = FILEPATH;
		}

		public String getFRDB() {
			return FRDB;
		}

		public void setFRDB(String FRDB) {
			this.FRDB = FRDB;
		}

		public String getFRDB_SFZHM() {
			return FRDB_SFZHM;
		}

		public void setFRDB_SFZHM(String FRDB_SFZHM) {
			this.FRDB_SFZHM = FRDB_SFZHM;
		}

		public String getFR_PHONE_NUM() {
			return FR_PHONE_NUM;
		}

		public void setFR_PHONE_NUM(String FR_PHONE_NUM) {
			this.FR_PHONE_NUM = FR_PHONE_NUM;
		}

		public String getGLZH_ID() {
			return GLZH_ID;
		}

		public void setGLZH_ID(String GLZH_ID) {
			this.GLZH_ID = GLZH_ID;
		}

		public String getGRINFOID() {
			return GRINFOID;
		}

		public void setGRINFOID(String GRINFOID) {
			this.GRINFOID = GRINFOID;
		}

		public String getGR_EMAIL() {
			return GR_EMAIL;
		}

		public void setGR_EMAIL(String GR_EMAIL) {
			this.GR_EMAIL = GR_EMAIL;
		}

		public String getGR_REALNAME() {
			return GR_REALNAME;
		}

		public void setGR_REALNAME(String GR_REALNAME) {
			this.GR_REALNAME = GR_REALNAME;
		}

		public String getGR_REALPHONE() {
			return GR_REALPHONE;
		}

		public void setGR_REALPHONE(String GR_REALPHONE) {
			this.GR_REALPHONE = GR_REALPHONE;
		}

		public String getGSDJH() {
			return GSDJH;
		}

		public void setGSDJH(String GSDJH) {
			this.GSDJH = GSDJH;
		}

		public String getGSZZFILE() {
			return GSZZFILE;
		}

		public void setGSZZFILE(String GSZZFILE) {
			this.GSZZFILE = GSZZFILE;
		}

		public String getGSZZFILE_NAME() {
			return GSZZFILE_NAME;
		}

		public void setGSZZFILE_NAME(String GSZZFILE_NAME) {
			this.GSZZFILE_NAME = GSZZFILE_NAME;
		}

		public String getHEADPORTRAIT_DOWNPATH() {
			return HEADPORTRAIT_DOWNPATH;
		}

		public void setHEADPORTRAIT_DOWNPATH(String HEADPORTRAIT_DOWNPATH) {
			this.HEADPORTRAIT_DOWNPATH = HEADPORTRAIT_DOWNPATH;
		}

		public String getHEADPORTRAIT_FILENAME() {
			return HEADPORTRAIT_FILENAME;
		}

		public void setHEADPORTRAIT_FILENAME(String HEADPORTRAIT_FILENAME) {
			this.HEADPORTRAIT_FILENAME = HEADPORTRAIT_FILENAME;
		}

		public String getIDCARD() {
			return IDCARD;
		}

		public void setIDCARD(String IDCARD) {
			this.IDCARD = IDCARD;
		}

		public String getIDCARDFILE() {
			return IDCARDFILE;
		}

		public void setIDCARDFILE(String IDCARDFILE) {
			this.IDCARDFILE = IDCARDFILE;
		}

		public String getIDCARD_ADDRESS() {
			return IDCARD_ADDRESS;
		}

		public void setIDCARD_ADDRESS(String IDCARD_ADDRESS) {
			this.IDCARD_ADDRESS = IDCARD_ADDRESS;
		}

		public String getIDCARD_CON() {
			return IDCARD_CON;
		}

		public void setIDCARD_CON(String IDCARD_CON) {
			this.IDCARD_CON = IDCARD_CON;
		}

		public String getIDCARD_CON_NAME() {
			return IDCARD_CON_NAME;
		}

		public void setIDCARD_CON_NAME(String IDCARD_CON_NAME) {
			this.IDCARD_CON_NAME = IDCARD_CON_NAME;
		}

		public String getIDCARD_FACE() {
			return IDCARD_FACE;
		}

		public void setIDCARD_FACE(String IDCARD_FACE) {
			this.IDCARD_FACE = IDCARD_FACE;
		}

		public String getIDCARD_FACE_NAME() {
			return IDCARD_FACE_NAME;
		}

		public void setIDCARD_FACE_NAME(String IDCARD_FACE_NAME) {
			this.IDCARD_FACE_NAME = IDCARD_FACE_NAME;
		}

		public String getIDSEXT_EMAIL() {
			return IDSEXT_EMAIL;
		}

		public void setIDSEXT_EMAIL(String IDSEXT_EMAIL) {
			this.IDSEXT_EMAIL = IDSEXT_EMAIL;
		}

		public String getIDSEXT_IDCARD() {
			return IDSEXT_IDCARD;
		}

		public void setIDSEXT_IDCARD(String IDSEXT_IDCARD) {
			this.IDSEXT_IDCARD = IDSEXT_IDCARD;
		}

		public String getIDSEXT_NAME() {
			return IDSEXT_NAME;
		}

		public void setIDSEXT_NAME(String IDSEXT_NAME) {
			this.IDSEXT_NAME = IDSEXT_NAME;
		}

		public String getIDSEXT_SEX() {
			return IDSEXT_SEX;
		}

		public void setIDSEXT_SEX(String IDSEXT_SEX) {
			this.IDSEXT_SEX = IDSEXT_SEX;
		}

		public String getISFIRST_LOGIN() {
			return ISFIRST_LOGIN;
		}

		public void setISFIRST_LOGIN(String ISFIRST_LOGIN) {
			this.ISFIRST_LOGIN = ISFIRST_LOGIN;
		}

		public String getISLINKED() {
			return ISLINKED;
		}

		public void setISLINKED(String ISLINKED) {
			this.ISLINKED = ISLINKED;
		}

		public String getIS_REAL_NAME() {
			return IS_REAL_NAME;
		}

		public void setIS_REAL_NAME(String IS_REAL_NAME) {
			this.IS_REAL_NAME = IS_REAL_NAME;
		}

		public String getJGLX() {
			return JGLX;
		}

		public void setJGLX(String JGLX) {
			this.JGLX = JGLX;
		}

		public String getLASTLOGINTIME() {
			return LASTLOGINTIME;
		}

		public void setLASTLOGINTIME(String LASTLOGINTIME) {
			this.LASTLOGINTIME = LASTLOGINTIME;
		}

		public String getLINKMAN() {
			return LINKMAN;
		}

		public void setLINKMAN(String LINKMAN) {
			this.LINKMAN = LINKMAN;
		}

		public String getLOCATION_NUM() {
			return LOCATION_NUM;
		}

		public void setLOCATION_NUM(String LOCATION_NUM) {
			this.LOCATION_NUM = LOCATION_NUM;
		}

		public String getMOBILE_NUM() {
			return MOBILE_NUM;
		}

		public void setMOBILE_NUM(String MOBILE_NUM) {
			this.MOBILE_NUM = MOBILE_NUM;
		}

		public String getMZ() {
			return MZ;
		}

		public void setMZ(String MZ) {
			this.MZ = MZ;
		}

		public String getOVERTIME() {
			return OVERTIME;
		}

		public void setOVERTIME(String OVERTIME) {
			this.OVERTIME = OVERTIME;
		}

		public String getQYLXDH() {
			return QYLXDH;
		}

		public void setQYLXDH(String QYLXDH) {
			this.QYLXDH = QYLXDH;
		}

		public String getQYTXDZ() {
			return QYTXDZ;
		}

		public void setQYTXDZ(String QYTXDZ) {
			this.QYTXDZ = QYTXDZ;
		}

		public String getREALNAME() {
			return REALNAME;
		}

		public void setREALNAME(String REALNAME) {
			this.REALNAME = REALNAME;
		}

		public long getREGDATE() {
			return REGDATE;
		}

		public void setREGDATE(long REGDATE) {
			this.REGDATE = REGDATE;
		}

		public String getREGISTERTIME() {
			return REGISTERTIME;
		}

		public void setREGISTERTIME(String REGISTERTIME) {
			this.REGISTERTIME = REGISTERTIME;
		}

		public String getSEX() {
			return SEX;
		}

		public void setSEX(String SEX) {
			this.SEX = SEX;
		}

		public String getSFSMRZ() {
			if(BeanUtils.isEmptyStr(getIDCARD())){
				SFSMRZ= "0";
			}else{
				SFSMRZ="1";
			}
			return SFSMRZ;
		}

		public void setSFSMRZ(String SFSMRZ) {
			this.SFSMRZ = SFSMRZ;
		}

		public String getSHOTFILENAME() {
			return SHOTFILENAME;
		}

		public void setSHOTFILENAME(String SHOTFILENAME) {
			this.SHOTFILENAME = SHOTFILENAME;
		}

		public String getSMRZ_CHOICE() {
			return SMRZ_CHOICE;
		}

		public void setSMRZ_CHOICE(String SMRZ_CHOICE) {
			this.SMRZ_CHOICE = SMRZ_CHOICE;
		}

		public String getSTATE() {
			return STATE;
		}

		public void setSTATE(String STATE) {
			this.STATE = STATE;
		}

		public String getTEL() {
			return TEL;
		}

		public void setTEL(String TEL) {
			this.TEL = TEL;
		}

		public String getUNIT() {
			return UNIT;
		}

		public void setUNIT(String UNIT) {
			this.UNIT = UNIT;
		}

		public String getUNITADD() {
			return UNITADD;
		}

		public void setUNITADD(String UNITADD) {
			this.UNITADD = UNITADD;
		}

		public String getUPDATETIME() {
			return UPDATETIME;
		}

		public void setUPDATETIME(String UPDATETIME) {
			this.UPDATETIME = UPDATETIME;
		}

		public String getUSERDUTY() {
			return USERDUTY;
		}

		public void setUSERDUTY(String USERDUTY) {
			this.USERDUTY = USERDUTY;
		}

		public String getUSERID() {
			return USERID;
		}

		public void setUSERID(String USERID) {
			this.USERID = USERID;
		}

		public String getUSERNAME() {
			return USERNAME;
		}

		public void setUSERNAME(String USERNAME) {
			this.USERNAME = USERNAME;
		}

		public String getUSERPWD() {
			return USERPWD;
		}

		public void setUSERPWD(String USERPWD) {
			this.USERPWD = USERPWD;
		}

		public String getVALIDATE_TYPE() {
			return VALIDATE_TYPE;
		}

		public void setVALIDATE_TYPE(String VALIDATE_TYPE) {
			this.VALIDATE_TYPE = VALIDATE_TYPE;
		}

		public String getVALIDNUM() {
			return VALIDNUM;
		}

		public void setVALIDNUM(String VALIDNUM) {
			this.VALIDNUM = VALIDNUM;
		}

		public String getXTYHLX() {
			return XTYHLX;
		}

		public void setXTYHLX(String XTYHLX) {
			this.XTYHLX = XTYHLX;
		}

		public String getZCLX() {
			return ZCLX;
		}

		public void setZCLX(String ZCLX) {
			this.ZCLX = ZCLX;
		}


//
//
//		private String ADDR;
//		private String APPEIDCODE;
//		private String APPLY_TIME;
//		private String AUDIT_IDEA;
//		private String AUDIT_STATE;
//		private String AUDIT_TIME;
//		private String AUDIT_USER;
//		private String AUDIT_USERNAME;
//		private String BUSINESSLICENCE;
//		private String CARD_CODE;
//		private String COMPANYNAME;
//		private String CSRQ;
//		private String EMAIL;
//		private String FRDB;
//		private String FRDB_SFZHM;
//		private String FR_PHONE_NUM;
//		private String GLZH_ID;
//		private String GSDJH;
//		private String GSZZFILE;
//		private String GSZZFILE_NAME;
//		private String IDCARD;
//		private String IDCARDFILE;
//		private String IDCARD_ADDRESS;
//		private String IDCARD_CON;
//		private String IDCARD_FACE;
//		private String IDSEXT_EMAIL;
//		private String IDSEXT_IDCARD;
//		private String IDSEXT_NAME;
//		private String IDSEXT_SEX;
//		private String IS_REAL_NAME;
//		private String JGLX;
//		private String LINKMAN;
//		private String LOCATION_NUM;
//		private String MOBILE_NUM;
//		private String MZ;
//		private String OVERTIME;
//		private String QYTXDZ;
//		private String REALNAME;
//		private long REGDATE;
//		private String SEX;
//		private String SFSMRZ;
//		private String STATE;
//		private String TEL;
//		private String UNIT;
//		private String UNITADD;
//		//用户类型（0个人，1企业，2管理员）
//		private String USERDUTY;
//		private String USERID;
//		private String USERNAME;
//		private String USERPWD;
//		private String VALIDATE_TYPE;
//		private String VALIDNUM;
//		private String ZCLX;
//		private String HEADPORTRAIT_DOWNPATH;
//		private String HEADPORTRAIT_FILENAME;
//
//		private List<CompanyInfo>  COMPANYLIST;
//
//		public List<CompanyInfo> getCOMPANYLIST() {
//			return COMPANYLIST;
//		}
//
//		@Override
//		public String toString() {
//			return new Gson().toJson(this);
//		}
//
//		public void setCOMPANYLIST(List<CompanyInfo> COMPANYLIST) {
//			this.COMPANYLIST = COMPANYLIST;
//		}
//
//		public String getHEADPORTRAIT_DOWNPATH() {
//			return HEADPORTRAIT_DOWNPATH;
//		}
//
//		public void setHEADPORTRAIT_DOWNPATH(String HEADPORTRAIT_DOWNPATH) {
//			this.HEADPORTRAIT_DOWNPATH = HEADPORTRAIT_DOWNPATH;
//		}
//
//		public String getHEADPORTRAIT_FILENAME() {
//			return HEADPORTRAIT_FILENAME;
//		}
//
//		public void setHEADPORTRAIT_FILENAME(String HEADPORTRAIT_FILENAME) {
//			this.HEADPORTRAIT_FILENAME = HEADPORTRAIT_FILENAME;
//		}
//
//		public String getADDR() {
//			return ADDR;
//		}
//
//		public void setADDR(String ADDR) {
//			this.ADDR = ADDR;
//		}
//
//		public String getAPPEIDCODE() {
//			return APPEIDCODE;
//		}
//
//		public void setAPPEIDCODE(String APPEIDCODE) {
//			this.APPEIDCODE = APPEIDCODE;
//		}
//
//		public String getAPPLY_TIME() {
//			return APPLY_TIME;
//		}
//
//		public void setAPPLY_TIME(String APPLY_TIME) {
//			this.APPLY_TIME = APPLY_TIME;
//		}
//
//		public String getAUDIT_IDEA() {
//			return AUDIT_IDEA;
//		}
//
//		public void setAUDIT_IDEA(String AUDIT_IDEA) {
//			this.AUDIT_IDEA = AUDIT_IDEA;
//		}
//
//		public String getAUDIT_STATE() {
//			return AUDIT_STATE;
//		}
//
//		public void setAUDIT_STATE(String AUDIT_STATE) {
//			this.AUDIT_STATE = AUDIT_STATE;
//		}
//
//		public String getAUDIT_TIME() {
//			return AUDIT_TIME;
//		}
//
//		public void setAUDIT_TIME(String AUDIT_TIME) {
//			this.AUDIT_TIME = AUDIT_TIME;
//		}
//
//		public String getAUDIT_USER() {
//			return AUDIT_USER;
//		}
//
//		public void setAUDIT_USER(String AUDIT_USER) {
//			this.AUDIT_USER = AUDIT_USER;
//		}
//
//		public String getAUDIT_USERNAME() {
//			return AUDIT_USERNAME;
//		}
//
//		public void setAUDIT_USERNAME(String AUDIT_USERNAME) {
//			this.AUDIT_USERNAME = AUDIT_USERNAME;
//		}
//
//		public String getBUSINESSLICENCE() {
//			return BUSINESSLICENCE;
//		}
//
//		public void setBUSINESSLICENCE(String BUSINESSLICENCE) {
//			this.BUSINESSLICENCE = BUSINESSLICENCE;
//		}
//
//		public String getCARD_CODE() {
//			return CARD_CODE;
//		}
//
//		public void setCARD_CODE(String CARD_CODE) {
//			this.CARD_CODE = CARD_CODE;
//		}
//
//		public String getCOMPANYNAME() {
//			return COMPANYNAME;
//		}
//
//		public void setCOMPANYNAME(String COMPANYNAME) {
//			this.COMPANYNAME = COMPANYNAME;
//		}
//
//		public String getCSRQ() {
//			return CSRQ;
//		}
//
//		public void setCSRQ(String CSRQ) {
//			this.CSRQ = CSRQ;
//		}
//
//		public String getEMAIL() {
//			return EMAIL;
//		}
//
//		public void setEMAIL(String EMAIL) {
//			this.EMAIL = EMAIL;
//		}
//
//		public String getFRDB() {
//			return FRDB;
//		}
//
//		public void setFRDB(String FRDB) {
//			this.FRDB = FRDB;
//		}
//
//		public String getFRDB_SFZHM() {
//			return FRDB_SFZHM;
//		}
//
//		public void setFRDB_SFZHM(String FRDB_SFZHM) {
//			this.FRDB_SFZHM = FRDB_SFZHM;
//		}
//
//		public String getFR_PHONE_NUM() {
//			return FR_PHONE_NUM;
//		}
//
//		public void setFR_PHONE_NUM(String FR_PHONE_NUM) {
//			this.FR_PHONE_NUM = FR_PHONE_NUM;
//		}
//
//		public String getGLZH_ID() {
//			return GLZH_ID;
//		}
//
//		public void setGLZH_ID(String GLZH_ID) {
//			this.GLZH_ID = GLZH_ID;
//		}
//
//		public String getGSDJH() {
//			return GSDJH;
//		}
//
//		public void setGSDJH(String GSDJH) {
//			this.GSDJH = GSDJH;
//		}
//
//		public String getGSZZFILE() {
//			return GSZZFILE;
//		}
//
//		public void setGSZZFILE(String GSZZFILE) {
//			this.GSZZFILE = GSZZFILE;
//		}
//
//		public String getGSZZFILE_NAME() {
//			return GSZZFILE_NAME;
//		}
//
//		public void setGSZZFILE_NAME(String GSZZFILE_NAME) {
//			this.GSZZFILE_NAME = GSZZFILE_NAME;
//		}
//
//		public String getIDCARD() {
//			return IDCARD;
//		}
//
//		public void setIDCARD(String IDCARD) {
//			this.IDCARD = IDCARD;
//		}
//
//		public String getIDCARDFILE() {
//			return IDCARDFILE;
//		}
//
//		public void setIDCARDFILE(String IDCARDFILE) {
//			this.IDCARDFILE = IDCARDFILE;
//		}
//
//		public String getIDCARD_ADDRESS() {
//			return IDCARD_ADDRESS;
//		}
//
//		public void setIDCARD_ADDRESS(String IDCARD_ADDRESS) {
//			this.IDCARD_ADDRESS = IDCARD_ADDRESS;
//		}
//
//		public String getIDCARD_CON() {
//			return IDCARD_CON;
//		}
//
//		public void setIDCARD_CON(String IDCARD_CON) {
//			this.IDCARD_CON = IDCARD_CON;
//		}
//
//		public String getIDCARD_FACE() {
//			return IDCARD_FACE;
//		}
//
//		public void setIDCARD_FACE(String IDCARD_FACE) {
//			this.IDCARD_FACE = IDCARD_FACE;
//		}
//
//		public String getIDSEXT_EMAIL() {
//			return IDSEXT_EMAIL;
//		}
//
//		public void setIDSEXT_EMAIL(String IDSEXT_EMAIL) {
//			this.IDSEXT_EMAIL = IDSEXT_EMAIL;
//		}
//
//		public String getIDSEXT_IDCARD() {
//			return IDSEXT_IDCARD;
//		}
//
//		public void setIDSEXT_IDCARD(String IDSEXT_IDCARD) {
//			this.IDSEXT_IDCARD = IDSEXT_IDCARD;
//		}
//
//		public String getIDSEXT_NAME() {
//			return IDSEXT_NAME;
//		}
//
//		public void setIDSEXT_NAME(String IDSEXT_NAME) {
//			this.IDSEXT_NAME = IDSEXT_NAME;
//		}
//
//		public String getIDSEXT_SEX() {
//			return IDSEXT_SEX;
//		}
//
//		public void setIDSEXT_SEX(String IDSEXT_SEX) {
//			this.IDSEXT_SEX = IDSEXT_SEX;
//		}
//
//		public String getIS_REAL_NAME() {
//			return IS_REAL_NAME;
//		}
//
//		public void setIS_REAL_NAME(String IS_REAL_NAME) {
//			this.IS_REAL_NAME = IS_REAL_NAME;
//		}
//
//		public String getJGLX() {
//			return JGLX;
//		}
//
//		public void setJGLX(String JGLX) {
//			this.JGLX = JGLX;
//		}
//
//		public String getLINKMAN() {
//			return LINKMAN;
//		}
//
//		public void setLINKMAN(String LINKMAN) {
//			this.LINKMAN = LINKMAN;
//		}
//
//		public String getLOCATION_NUM() {
//			return LOCATION_NUM;
//		}
//
//		public void setLOCATION_NUM(String LOCATION_NUM) {
//			this.LOCATION_NUM = LOCATION_NUM;
//		}
//
//		public String getMOBILE_NUM() {
//			return MOBILE_NUM;
//		}
//
//		public void setMOBILE_NUM(String MOBILE_NUM) {
//			this.MOBILE_NUM = MOBILE_NUM;
//		}
//
//		public String getMZ() {
//			return MZ;
//		}
//
//		public void setMZ(String MZ) {
//			this.MZ = MZ;
//		}
//
//		public String getOVERTIME() {
//			return OVERTIME;
//		}
//
//		public void setOVERTIME(String OVERTIME) {
//			this.OVERTIME = OVERTIME;
//		}
//
//		public String getQYTXDZ() {
//			return QYTXDZ;
//		}
//
//		public void setQYTXDZ(String QYTXDZ) {
//			this.QYTXDZ = QYTXDZ;
//		}
//
//		public String getREALNAME() {
//			return REALNAME;
//		}
//
//		public void setREALNAME(String REALNAME) {
//			this.REALNAME = REALNAME;
//		}
//
//		public long getREGDATE() {
//			return REGDATE;
//		}
//
//		public void setREGDATE(long REGDATE) {
//			this.REGDATE = REGDATE;
//		}
//
//		public String getSEX() {
//			return SEX;
//		}
//
//		public void setSEX(String SEX) {
//			this.SEX = SEX;
//		}
//
//		public String getSFSMRZ() {
//			return SFSMRZ;
//		}
//
//		public void setSFSMRZ(String SFSMRZ) {
//			this.SFSMRZ = SFSMRZ;
//		}
//
//		public String getSTATE() {
//			return STATE;
//		}
//
//		public void setSTATE(String STATE) {
//			this.STATE = STATE;
//		}
//
//		public String getTEL() {
//			return TEL;
//		}
//
//		public void setTEL(String TEL) {
//			this.TEL = TEL;
//		}
//
//		public String getUNIT() {
//			return UNIT;
//		}
//
//		public void setUNIT(String UNIT) {
//			this.UNIT = UNIT;
//		}
//
//		public String getUNITADD() {
//			return UNITADD;
//		}
//
//		public void setUNITADD(String UNITADD) {
//			this.UNITADD = UNITADD;
//		}
//
//		public String getUSERDUTY() {
//			return USERDUTY;
//		}
//
//		public void setUSERDUTY(String USERDUTY) {
//			this.USERDUTY = USERDUTY;
//		}
//
//		public String getUSERID() {
//			return USERID;
//		}
//
//		public void setUSERID(String USERID) {
//			this.USERID = USERID;
//		}
//
//		public String getUSERNAME() {
//			if(TextUtils.isEmpty(USERNAME)){
//				return "";
//			}
//			return USERNAME;
//		}
//
//		public void setUSERNAME(String USERNAME) {
//			this.USERNAME = USERNAME;
//		}
//
//		public String getUSERPWD() {
//			return USERPWD;
//		}
//
//		public void setUSERPWD(String USERPWD) {
//			this.USERPWD = USERPWD;
//		}
//
//		public String getVALIDATE_TYPE() {
//			return VALIDATE_TYPE;
//		}
//
//		public void setVALIDATE_TYPE(String VALIDATE_TYPE) {
//			this.VALIDATE_TYPE = VALIDATE_TYPE;
//		}
//
//		public String getVALIDNUM() {
//			return VALIDNUM;
//		}
//
//		public void setVALIDNUM(String VALIDNUM) {
//			this.VALIDNUM = VALIDNUM;
//		}
//
//		public String getZCLX() {
//			return ZCLX;
//		}
//
//		public void setZCLX(String ZCLX) {
//			this.ZCLX = ZCLX;
//		}
//
//
//
	}

//	private static final long serialVersionUID = 7798217957705233498L;
//	/** 用户ID*/
//	private String personid;
//	/** 登录账户名*/
//	private String account;
//	/** 手机号*/
//	private String sjh;
//	/** 是否实名认证，0否1是*/
//	private String sfsmrz;
//	/** 实名认证类型，1个人2企业*/
//	private String yhlx;
//	/** 个人实名认证-姓名*/
//	private String realname;
//	/** 个人实名认证-身份证号码*/
//	private String sfzhm;
//	/** 企业（法人）实名认证-企业名称*/
//	private String qymc;
//	/** 企业（法人）实名认证-信用代码（或营业执照号）*/
//	private String xydm;
//	/** 企业（法人）实名认证-法人代表-姓名*/
//	private String frdb_xm;
//	/** 企业（法人）实名认证-法人代表-身份证号码*/
//	private String frdb_sfzhm;
//	/** 企业（法人）实名认证-法人代表-手机号*/
//	private String frdb_sjh;
//	/** 注册类型1个人手机号2个人邮箱3个人用户名4企业法人*/
//	private String zclx;
//	/** 邮箱*/
//	private String email;
//	/** 通讯地址*/
//	private String address;
//	/** 邮编*/
//	private String post;
//	/** 邮寄地址*/
//	private String emailAddress;
//
//	private String authtoken;
//	/** 挂点部门和职能部门是否可以切换*/
//	private boolean isSwitch;









	public static class CompanyInfo implements Serializable{
		private String ACCREDIT_TIME;
		private String BUSINESSLICENCE;
		private String COMPANYNAME;
		private String CREATETIME;
		private String ID;
		private String IDCARD;
		private String OVERTIME;
		private String QYTXDZ;
		private String REALNAME;
		private String UNITADD;
		private String USERID;
		private String USERNAME;

		@Override
		public String toString() {
			return new Gson().toString();
		}

		public String getACCREDIT_TIME() {
			return ACCREDIT_TIME;
		}

		public void setACCREDIT_TIME(String ACCREDIT_TIME) {
			this.ACCREDIT_TIME = ACCREDIT_TIME;
		}

		public String getBUSINESSLICENCE() {
			return BUSINESSLICENCE;
		}

		public void setBUSINESSLICENCE(String BUSINESSLICENCE) {
			this.BUSINESSLICENCE = BUSINESSLICENCE;
		}

		public String getCOMPANYNAME() {
			return COMPANYNAME;
		}

		public void setCOMPANYNAME(String COMPANYNAME) {
			this.COMPANYNAME = COMPANYNAME;
		}

		public String getCREATETIME() {
			return CREATETIME;
		}

		public void setCREATETIME(String CREATETIME) {
			this.CREATETIME = CREATETIME;
		}

		public String getID() {
			return ID;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public String getIDCARD() {
			return IDCARD;
		}

		public void setIDCARD(String IDCARD) {
			this.IDCARD = IDCARD;
		}

		public String getOVERTIME() {
			return OVERTIME;
		}

		public void setOVERTIME(String OVERTIME) {
			this.OVERTIME = OVERTIME;
		}

		public String getQYTXDZ() {
			return QYTXDZ;
		}

		public void setQYTXDZ(String QYTXDZ) {
			this.QYTXDZ = QYTXDZ;
		}

		public String getREALNAME() {
			return REALNAME;
		}

		public void setREALNAME(String REALNAME) {
			this.REALNAME = REALNAME;
		}

		public String getUNITADD() {
			return UNITADD;
		}

		public void setUNITADD(String UNITADD) {
			this.UNITADD = UNITADD;
		}

		public String getUSERID() {
			return USERID;
		}

		public void setUSERID(String USERID) {
			this.USERID = USERID;
		}

		public String getUSERNAME() {
			return USERNAME;
		}

		public void setUSERNAME(String USERNAME) {
			this.USERNAME = USERNAME;
		}
	}

}
