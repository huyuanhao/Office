package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * @author AlienChao
 * @date 2019/06/13 15:38
 */
public class QRQuery {

    /**
     * DefaultMessage :
     * Code : 0
     * Message : {}
     * MessageToString :
     * Body : {"Language":0,"RelativeMobilePhones":["13907017128"],"ContactMobilePhone":"13907017128","Gift":{"GiftStatus":0,"GiftToMobile":null,"GiftToRealName":null},"uuid":"8fb4bc22-301f-4667-8641-0c7047d8c22b","Retroactive":{"RetroactiveMoney":0,"RequiredRetroactive":false,"IsRetroactiveSuccess":false,"RetroactiveOrderID":null,"RetroactiveTime":null},"RetroactiveMoney":0,"EventDateFrom":"2019-07-18T09:00:00+08:00","EventDateTo":"2019-07-19T12:00:00+08:00","_Retroactive":{"RetroactiveMoney":0,"RequiredRetroactive":false,"IsRetroactiveSuccess":false,"RetroactiveOrderID":null,"RetroactiveTime":null},"PayType":0,"OrderPayType":-1,"JoinId":1660074053,"IsAssigned":true,"CountryEx":null,"CityEx":null,"TicketPrice":0,"TicketTitle":"观众","JoinCateName":"注册通道","ScheduleIds":null,"ChannelID":1655847708,"ChannelName":null,"ProvinceEx":null,"ScrmCompanyId":1655505386,"MemberNumber":null,"WeiXinOpenId":null,"WeiXinAppId":null,"EventTitle":"2019江西国际移动物联网博览会观众注册","IsPrePare":false,"Address":null,"EventId":1655847707,"UserId":0,"JoinCateId":1655847777,"TicketId":1655847778,"RealName":"费尚恒","EventUserJoinImage":"/Uploads/Files/2019/06/04/6ddfdb8ddff548128d8c3231732fa1cd.jpg","EventJoinOriginalImage":"/Uploads/Files/2019/06/04/9fc8f2e0145441649389c5074e7f7c6b.jpg","AllowExchangeNameCard":false,"Email":null,"ConfirmTime":"2019/6/4 15:59:12","CardImagePath":null,"Company":null,"PosStatusEx":null,"Mobile":"13907017128","PNOpenId":null,"WeiXin":null,"IsSignIn":false,"SignInTime":null,"SignInCode":"70074053","IsPaid":true,"PaidString":"已支付","PayTime":"2019-06-04T15:59:12.797+08:00","AddIP":null,"UpdateIP":null,"FromSite":0,"IsImported":false,"UserGroup":null,"IsSubstitued":false,"ExtraFields":{"Field1657693863":"鹰潭市","Field1657527904":"交通运输","SexEx":"男","Field1655550492":"鹰潭市交通运输局","Field1655550493":"党委书记、局长","Field1655552467":"中华人民共和国居民身份证","Field1655550497":"360602196710161015","BakEventUserJoinImage":"/Uploads/Files/2019/06/04/6ddfdb8ddff548128d8c3231732fa1cd.jpg"},"ExtraFieldsForES":{},"Remarks":null,"AddTime":"2019-06-04T15:59:12.578+08:00","TotalAwardMoney":0,"LastAwardTime":null,"AwardLotteryIds":null,"ScanSpotStatisticList":null,"JoinContactId":1660074049,"OrderIdList":[1660074048],"ExistOrder":true,"TicketOrderID":1660074048,"RelativeSpotIds":[],"TrackingCode":null,"RecordVersion":0,"DiscountCode":[],"SiteDiscountCode":null,"Status":1,"SendCodeTimes":0,"SendCodeTimesFix":0,"AllowSendCodeTimes":0,"AllowSendSMSTimes":0,"SendSMSTimes":0,"UseReplySMS":false,"IdCardNo":null,"IsEmailOpened":false,"IdCardType":null,"PrintCount":0,"DepartmentEx":null,"PrintBG":null,"LastPrintTime":null,"FullPY":"FEISHANGHENG,FSH,FSHH 费尚恒","SignInFromName":null,"TicketInfoList":[{"TicketId":1655847778,"TicketName":"观众","TicketPrice":0,"CateId":1655847777,"CateName":"注册通道","SpotId":0,"SignInTime":null,"IsSignIn":false,"EventId":1655847707,"VerifyCode":""}],"RoleIds":null,"RoleName":null,"LevelId":0,"LevelName":null,"LeaveReason":null,"RejectReason":null,"ApprovalLeaveStatue":0,"ApprovalLeaveStatuestr":"","ApprovalTime":null,"AlterJoinFieldStatue":0,"AlterJoinFieldContent":null,"JoinNumber":21,"OperatingUserName":"管理员","OperatingUserType":0,"GlobalRoaming":null,"BelongsUserId":0,"NickName":null,"IsSignInProductPackageDeduction":false,"MemberType":null,"MemberTypeId":null,"MemberGroup":null,"MemberGroupIds":null,"ContactGroup":null,"ContactGroupIds":null,"RealPayMoney":null,"RealPayRemark":null,"IsAnonymousFill":false,"FaceStatus":1,"FaceMessage":"","PersonId":null,"NotCheck":false,"SignImagePath":null,"ArrivedFlightNo":null,"ArrivedFlightDate":"0001-01-01T00:00:00Z","ActualArrivedTime":null,"ArrivedAirport":null,"ArrivedTerminal":null,"LeaveFlightNo":null,"LeaveFlightDate":"0001-01-01T00:00:00Z","ActualLeaveTime":null,"LeaveAirport":null,"LeaveTerminal":null,"FaceCutImagePath":null,"FaceFeatureBase64String":null,"JoinRight":null,"IsBoothNumAllot":false,"IsConfirmBoothUp":false,"IsConfirmBoothSend":false,"IsConfirmBoothDown":false,"IsAddFixedCost":false,"ConfirmBoothUrl":null,"BoothNum":null,"BoothArea":null,"ExhibitorId":0,"ExhibitorNameZhengJian":null,"LimitCertificatesCount":0,"CreateTime":"2019-06-04T15:59:12.547+08:00","UpdateTime":"2019-06-11T14:54:40.447+08:00","NeedChangeFieldNames":null,"DbBackupHistoryLogId":0,"NotNeedRecordDbOperatingHistoryLog":false,"_id":"5cf624d0634199c824dedc08","__v":3,"IsDeleted":false}
     * ElapsedMilliseconds : 0
     */

    private String DefaultMessage;
    private int Code;
    private MessageBean Message;
    private String MessageToString;
    private BodyBean Body;
    private int ElapsedMilliseconds;

    public String getDefaultMessage() {
        return DefaultMessage;
    }

    public void setDefaultMessage(String DefaultMessage) {
        this.DefaultMessage = DefaultMessage;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean Message) {
        this.Message = Message;
    }

    public String getMessageToString() {
        return MessageToString;
    }

    public void setMessageToString(String MessageToString) {
        this.MessageToString = MessageToString;
    }

    public BodyBean getBody() {
        return Body;
    }

    public void setBody(BodyBean Body) {
        this.Body = Body;
    }

    public int getElapsedMilliseconds() {
        return ElapsedMilliseconds;
    }

    public void setElapsedMilliseconds(int ElapsedMilliseconds) {
        this.ElapsedMilliseconds = ElapsedMilliseconds;
    }

    public static class MessageBean {
    }

    public static class BodyBean {
        /**
         * Language : 0
         * RelativeMobilePhones : ["13907017128"]
         * ContactMobilePhone : 13907017128
         * Gift : {"GiftStatus":0,"GiftToMobile":null,"GiftToRealName":null}
         * uuid : 8fb4bc22-301f-4667-8641-0c7047d8c22b
         * Retroactive : {"RetroactiveMoney":0,"RequiredRetroactive":false,"IsRetroactiveSuccess":false,"RetroactiveOrderID":null,"RetroactiveTime":null}
         * RetroactiveMoney : 0
         * EventDateFrom : 2019-07-18T09:00:00+08:00
         * EventDateTo : 2019-07-19T12:00:00+08:00
         * _Retroactive : {"RetroactiveMoney":0,"RequiredRetroactive":false,"IsRetroactiveSuccess":false,"RetroactiveOrderID":null,"RetroactiveTime":null}
         * PayType : 0
         * OrderPayType : -1
         * JoinId : 1660074053
         * IsAssigned : true
         * CountryEx : null
         * CityEx : null
         * TicketPrice : 0
         * TicketTitle : 观众
         * JoinCateName : 注册通道
         * ScheduleIds : null
         * ChannelID : 1655847708
         * ChannelName : null
         * ProvinceEx : null
         * ScrmCompanyId : 1655505386
         * MemberNumber : null
         * WeiXinOpenId : null
         * WeiXinAppId : null
         * EventTitle : 2019江西国际移动物联网博览会观众注册
         * IsPrePare : false
         * Address : null
         * EventId : 1655847707
         * UserId : 0
         * JoinCateId : 1655847777
         * TicketId : 1655847778
         * RealName : 费尚恒
         * EventUserJoinImage : /Uploads/Files/2019/06/04/6ddfdb8ddff548128d8c3231732fa1cd.jpg
         * EventJoinOriginalImage : /Uploads/Files/2019/06/04/9fc8f2e0145441649389c5074e7f7c6b.jpg
         * AllowExchangeNameCard : false
         * Email : null
         * ConfirmTime : 2019/6/4 15:59:12
         * CardImagePath : null
         * Company : null
         * PosStatusEx : null
         * Mobile : 13907017128
         * PNOpenId : null
         * WeiXin : null
         * IsSignIn : false
         * SignInTime : null
         * SignInCode : 70074053
         * IsPaid : true
         * PaidString : 已支付
         * PayTime : 2019-06-04T15:59:12.797+08:00
         * AddIP : null
         * UpdateIP : null
         * FromSite : 0
         * IsImported : false
         * UserGroup : null
         * IsSubstitued : false
         * ExtraFields : {"Field1657693863":"鹰潭市","Field1657527904":"交通运输","SexEx":"男","Field1655550492":"鹰潭市交通运输局","Field1655550493":"党委书记、局长","Field1655552467":"中华人民共和国居民身份证","Field1655550497":"360602196710161015","BakEventUserJoinImage":"/Uploads/Files/2019/06/04/6ddfdb8ddff548128d8c3231732fa1cd.jpg"}
         * ExtraFieldsForES : {}
         * Remarks : null
         * AddTime : 2019-06-04T15:59:12.578+08:00
         * TotalAwardMoney : 0
         * LastAwardTime : null
         * AwardLotteryIds : null
         * ScanSpotStatisticList : null
         * JoinContactId : 1660074049
         * OrderIdList : [1660074048]
         * ExistOrder : true
         * TicketOrderID : 1660074048
         * RelativeSpotIds : []
         * TrackingCode : null
         * RecordVersion : 0
         * DiscountCode : []
         * SiteDiscountCode : null
         * Status : 1
         * SendCodeTimes : 0
         * SendCodeTimesFix : 0
         * AllowSendCodeTimes : 0
         * AllowSendSMSTimes : 0
         * SendSMSTimes : 0
         * UseReplySMS : false
         * IdCardNo : null
         * IsEmailOpened : false
         * IdCardType : null
         * PrintCount : 0
         * DepartmentEx : null
         * PrintBG : null
         * LastPrintTime : null
         * FullPY : FEISHANGHENG,FSH,FSHH 费尚恒
         * SignInFromName : null
         * TicketInfoList : [{"TicketId":1655847778,"TicketName":"观众","TicketPrice":0,"CateId":1655847777,"CateName":"注册通道","SpotId":0,"SignInTime":null,"IsSignIn":false,"EventId":1655847707,"VerifyCode":""}]
         * RoleIds : null
         * RoleName : null
         * LevelId : 0
         * LevelName : null
         * LeaveReason : null
         * RejectReason : null
         * ApprovalLeaveStatue : 0
         * ApprovalLeaveStatuestr :
         * ApprovalTime : null
         * AlterJoinFieldStatue : 0
         * AlterJoinFieldContent : null
         * JoinNumber : 21
         * OperatingUserName : 管理员
         * OperatingUserType : 0
         * GlobalRoaming : null
         * BelongsUserId : 0
         * NickName : null
         * IsSignInProductPackageDeduction : false
         * MemberType : null
         * MemberTypeId : null
         * MemberGroup : null
         * MemberGroupIds : null
         * ContactGroup : null
         * ContactGroupIds : null
         * RealPayMoney : null
         * RealPayRemark : null
         * IsAnonymousFill : false
         * FaceStatus : 1
         * FaceMessage :
         * PersonId : null
         * NotCheck : false
         * SignImagePath : null
         * ArrivedFlightNo : null
         * ArrivedFlightDate : 0001-01-01T00:00:00Z
         * ActualArrivedTime : null
         * ArrivedAirport : null
         * ArrivedTerminal : null
         * LeaveFlightNo : null
         * LeaveFlightDate : 0001-01-01T00:00:00Z
         * ActualLeaveTime : null
         * LeaveAirport : null
         * LeaveTerminal : null
         * FaceCutImagePath : null
         * FaceFeatureBase64String : null
         * JoinRight : null
         * IsBoothNumAllot : false
         * IsConfirmBoothUp : false
         * IsConfirmBoothSend : false
         * IsConfirmBoothDown : false
         * IsAddFixedCost : false
         * ConfirmBoothUrl : null
         * BoothNum : null
         * BoothArea : null
         * ExhibitorId : 0
         * ExhibitorNameZhengJian : null
         * LimitCertificatesCount : 0
         * CreateTime : 2019-06-04T15:59:12.547+08:00
         * UpdateTime : 2019-06-11T14:54:40.447+08:00
         * NeedChangeFieldNames : null
         * DbBackupHistoryLogId : 0
         * NotNeedRecordDbOperatingHistoryLog : false
         * _id : 5cf624d0634199c824dedc08
         * __v : 3
         * IsDeleted : false
         */

        private int Language;
        private String ContactMobilePhone;
        private GiftBean Gift;
        private String uuid;
        private RetroactiveBean Retroactive;
        private int RetroactiveMoney;
        private String EventDateFrom;
        private String EventDateTo;
        private RetroactiveBeanX _Retroactive;
        private int PayType;
        private int OrderPayType;
        private int JoinId;
        private boolean IsAssigned;
        private Object CountryEx;
        private Object CityEx;
        private int TicketPrice;
        private String TicketTitle;
        private String JoinCateName;
        private Object ScheduleIds;
        private int ChannelID;
        private Object ChannelName;
        private Object ProvinceEx;
        private int ScrmCompanyId;
        private Object MemberNumber;
        private Object WeiXinOpenId;
        private Object WeiXinAppId;
        private String EventTitle;
        private boolean IsPrePare;
        private Object Address;
        private int EventId;
        private int UserId;
        private int JoinCateId;
        private int TicketId;
        private String RealName;
        private String EventUserJoinImage;
        private String EventJoinOriginalImage;
        private boolean AllowExchangeNameCard;
        private Object Email;
        private String ConfirmTime;
        private Object CardImagePath;
        private Object Company;
        private Object PosStatusEx;
        private String Mobile;
        private Object PNOpenId;
        private Object WeiXin;
        private boolean IsSignIn;
        private Object SignInTime;
        private String SignInCode="";
        private boolean IsPaid;
        private String PaidString;
        private String PayTime;
        private Object AddIP;
        private Object UpdateIP;
        private int FromSite;
        private boolean IsImported;
        private Object UserGroup;
        private boolean IsSubstitued;
        private ExtraFieldsBean ExtraFields;
        private ExtraFieldsForESBean ExtraFieldsForES;
        private Object Remarks;
        private String AddTime;
        private int TotalAwardMoney;
        private Object LastAwardTime;
        private Object AwardLotteryIds;
        private Object ScanSpotStatisticList;
        private int JoinContactId;
        private boolean ExistOrder;
        private int TicketOrderID;
        private Object TrackingCode;
        private int RecordVersion;
        private Object SiteDiscountCode;
        private int Status;
        private int SendCodeTimes;
        private int SendCodeTimesFix;
        private int AllowSendCodeTimes;
        private int AllowSendSMSTimes;
        private int SendSMSTimes;
        private boolean UseReplySMS;
        private Object IdCardNo;
        private boolean IsEmailOpened;
        private Object IdCardType;
        private int PrintCount;
        private Object DepartmentEx;
        private Object PrintBG;
        private Object LastPrintTime;
        private String FullPY;
        private Object SignInFromName;
        private Object RoleIds;
        private Object RoleName;
        private int LevelId;
        private Object LevelName;
        private Object LeaveReason;
        private Object RejectReason;
        private int ApprovalLeaveStatue;
        private String ApprovalLeaveStatuestr;
        private Object ApprovalTime;
        private int AlterJoinFieldStatue;
        private Object AlterJoinFieldContent;
        private int JoinNumber;
        private String OperatingUserName;
        private int OperatingUserType;
        private Object GlobalRoaming;
        private int BelongsUserId;
        private Object NickName;
        private boolean IsSignInProductPackageDeduction;
        private Object MemberType;
        private Object MemberTypeId;
        private Object MemberGroup;
        private Object MemberGroupIds;
        private Object ContactGroup;
        private Object ContactGroupIds;
        private Object RealPayMoney;
        private Object RealPayRemark;
        private boolean IsAnonymousFill;
        private int FaceStatus;
        private String FaceMessage;
        private Object PersonId;
        private boolean NotCheck;
        private Object SignImagePath;
        private Object ArrivedFlightNo;
        private String ArrivedFlightDate;
        private Object ActualArrivedTime;
        private Object ArrivedAirport;
        private Object ArrivedTerminal;
        private Object LeaveFlightNo;
        private String LeaveFlightDate;
        private Object ActualLeaveTime;
        private Object LeaveAirport;
        private Object LeaveTerminal;
        private Object FaceCutImagePath;
        private Object FaceFeatureBase64String;
        private Object JoinRight;
        private boolean IsBoothNumAllot;
        private boolean IsConfirmBoothUp;
        private boolean IsConfirmBoothSend;
        private boolean IsConfirmBoothDown;
        private boolean IsAddFixedCost;
        private Object ConfirmBoothUrl;
        private Object BoothNum;
        private Object BoothArea;
        private int ExhibitorId;
        private Object ExhibitorNameZhengJian;
        private int LimitCertificatesCount;
        private String CreateTime;
        private String UpdateTime;
        private Object NeedChangeFieldNames;
        private int DbBackupHistoryLogId;
        private boolean NotNeedRecordDbOperatingHistoryLog;
        private String _id;
        private int __v;
        private boolean IsDeleted;
        private List<String> RelativeMobilePhones;
        private List<Integer> OrderIdList;
        private List<?> RelativeSpotIds;
        private List<?> DiscountCode;
        private List<TicketInfoListBean> TicketInfoList;

        public int getLanguage() {
            return Language;
        }

        public void setLanguage(int Language) {
            this.Language = Language;
        }

        public String getContactMobilePhone() {
            return ContactMobilePhone;
        }

        public void setContactMobilePhone(String ContactMobilePhone) {
            this.ContactMobilePhone = ContactMobilePhone;
        }

        public GiftBean getGift() {
            return Gift;
        }

        public void setGift(GiftBean Gift) {
            this.Gift = Gift;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public RetroactiveBean getRetroactive() {
            return Retroactive;
        }

        public void setRetroactive(RetroactiveBean Retroactive) {
            this.Retroactive = Retroactive;
        }

        public int getRetroactiveMoney() {
            return RetroactiveMoney;
        }

        public void setRetroactiveMoney(int RetroactiveMoney) {
            this.RetroactiveMoney = RetroactiveMoney;
        }

        public String getEventDateFrom() {
            return EventDateFrom;
        }

        public void setEventDateFrom(String EventDateFrom) {
            this.EventDateFrom = EventDateFrom;
        }

        public String getEventDateTo() {
            return EventDateTo;
        }

        public void setEventDateTo(String EventDateTo) {
            this.EventDateTo = EventDateTo;
        }

        public RetroactiveBeanX get_Retroactive() {
            return _Retroactive;
        }

        public void set_Retroactive(RetroactiveBeanX _Retroactive) {
            this._Retroactive = _Retroactive;
        }

        public int getPayType() {
            return PayType;
        }

        public void setPayType(int PayType) {
            this.PayType = PayType;
        }

        public int getOrderPayType() {
            return OrderPayType;
        }

        public void setOrderPayType(int OrderPayType) {
            this.OrderPayType = OrderPayType;
        }

        public int getJoinId() {
            return JoinId;
        }

        public void setJoinId(int JoinId) {
            this.JoinId = JoinId;
        }

        public boolean isIsAssigned() {
            return IsAssigned;
        }

        public void setIsAssigned(boolean IsAssigned) {
            this.IsAssigned = IsAssigned;
        }

        public Object getCountryEx() {
            return CountryEx;
        }

        public void setCountryEx(Object CountryEx) {
            this.CountryEx = CountryEx;
        }

        public Object getCityEx() {
            return CityEx;
        }

        public void setCityEx(Object CityEx) {
            this.CityEx = CityEx;
        }

        public int getTicketPrice() {
            return TicketPrice;
        }

        public void setTicketPrice(int TicketPrice) {
            this.TicketPrice = TicketPrice;
        }

        public String getTicketTitle() {
            return TicketTitle;
        }

        public void setTicketTitle(String TicketTitle) {
            this.TicketTitle = TicketTitle;
        }

        public String getJoinCateName() {
            return JoinCateName;
        }

        public void setJoinCateName(String JoinCateName) {
            this.JoinCateName = JoinCateName;
        }

        public Object getScheduleIds() {
            return ScheduleIds;
        }

        public void setScheduleIds(Object ScheduleIds) {
            this.ScheduleIds = ScheduleIds;
        }

        public int getChannelID() {
            return ChannelID;
        }

        public void setChannelID(int ChannelID) {
            this.ChannelID = ChannelID;
        }

        public Object getChannelName() {
            return ChannelName;
        }

        public void setChannelName(Object ChannelName) {
            this.ChannelName = ChannelName;
        }

        public Object getProvinceEx() {
            return ProvinceEx;
        }

        public void setProvinceEx(Object ProvinceEx) {
            this.ProvinceEx = ProvinceEx;
        }

        public int getScrmCompanyId() {
            return ScrmCompanyId;
        }

        public void setScrmCompanyId(int ScrmCompanyId) {
            this.ScrmCompanyId = ScrmCompanyId;
        }

        public Object getMemberNumber() {
            return MemberNumber;
        }

        public void setMemberNumber(Object MemberNumber) {
            this.MemberNumber = MemberNumber;
        }

        public Object getWeiXinOpenId() {
            return WeiXinOpenId;
        }

        public void setWeiXinOpenId(Object WeiXinOpenId) {
            this.WeiXinOpenId = WeiXinOpenId;
        }

        public Object getWeiXinAppId() {
            return WeiXinAppId;
        }

        public void setWeiXinAppId(Object WeiXinAppId) {
            this.WeiXinAppId = WeiXinAppId;
        }

        public String getEventTitle() {
            return EventTitle;
        }

        public void setEventTitle(String EventTitle) {
            this.EventTitle = EventTitle;
        }

        public boolean isIsPrePare() {
            return IsPrePare;
        }

        public void setIsPrePare(boolean IsPrePare) {
            this.IsPrePare = IsPrePare;
        }

        public Object getAddress() {
            return Address;
        }

        public void setAddress(Object Address) {
            this.Address = Address;
        }

        public int getEventId() {
            return EventId;
        }

        public void setEventId(int EventId) {
            this.EventId = EventId;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public int getJoinCateId() {
            return JoinCateId;
        }

        public void setJoinCateId(int JoinCateId) {
            this.JoinCateId = JoinCateId;
        }

        public int getTicketId() {
            return TicketId;
        }

        public void setTicketId(int TicketId) {
            this.TicketId = TicketId;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getEventUserJoinImage() {
            return EventUserJoinImage;
        }

        public void setEventUserJoinImage(String EventUserJoinImage) {
            this.EventUserJoinImage = EventUserJoinImage;
        }

        public String getEventJoinOriginalImage() {
            return EventJoinOriginalImage;
        }

        public void setEventJoinOriginalImage(String EventJoinOriginalImage) {
            this.EventJoinOriginalImage = EventJoinOriginalImage;
        }

        public boolean isAllowExchangeNameCard() {
            return AllowExchangeNameCard;
        }

        public void setAllowExchangeNameCard(boolean AllowExchangeNameCard) {
            this.AllowExchangeNameCard = AllowExchangeNameCard;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public String getConfirmTime() {
            return ConfirmTime;
        }

        public void setConfirmTime(String ConfirmTime) {
            this.ConfirmTime = ConfirmTime;
        }

        public Object getCardImagePath() {
            return CardImagePath;
        }

        public void setCardImagePath(Object CardImagePath) {
            this.CardImagePath = CardImagePath;
        }

        public Object getCompany() {
            return Company;
        }

        public void setCompany(Object Company) {
            this.Company = Company;
        }

        public Object getPosStatusEx() {
            return PosStatusEx;
        }

        public void setPosStatusEx(Object PosStatusEx) {
            this.PosStatusEx = PosStatusEx;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public Object getPNOpenId() {
            return PNOpenId;
        }

        public void setPNOpenId(Object PNOpenId) {
            this.PNOpenId = PNOpenId;
        }

        public Object getWeiXin() {
            return WeiXin;
        }

        public void setWeiXin(Object WeiXin) {
            this.WeiXin = WeiXin;
        }

        public boolean isIsSignIn() {
            return IsSignIn;
        }

        public void setIsSignIn(boolean IsSignIn) {
            this.IsSignIn = IsSignIn;
        }

        public Object getSignInTime() {
            return SignInTime;
        }

        public void setSignInTime(Object SignInTime) {
            this.SignInTime = SignInTime;
        }

        public String getSignInCode() {
            return SignInCode;
        }

        public void setSignInCode(String SignInCode) {
            this.SignInCode = SignInCode;
        }

        public boolean isIsPaid() {
            return IsPaid;
        }

        public void setIsPaid(boolean IsPaid) {
            this.IsPaid = IsPaid;
        }

        public String getPaidString() {
            return PaidString;
        }

        public void setPaidString(String PaidString) {
            this.PaidString = PaidString;
        }

        public String getPayTime() {
            return PayTime;
        }

        public void setPayTime(String PayTime) {
            this.PayTime = PayTime;
        }

        public Object getAddIP() {
            return AddIP;
        }

        public void setAddIP(Object AddIP) {
            this.AddIP = AddIP;
        }

        public Object getUpdateIP() {
            return UpdateIP;
        }

        public void setUpdateIP(Object UpdateIP) {
            this.UpdateIP = UpdateIP;
        }

        public int getFromSite() {
            return FromSite;
        }

        public void setFromSite(int FromSite) {
            this.FromSite = FromSite;
        }

        public boolean isIsImported() {
            return IsImported;
        }

        public void setIsImported(boolean IsImported) {
            this.IsImported = IsImported;
        }

        public Object getUserGroup() {
            return UserGroup;
        }

        public void setUserGroup(Object UserGroup) {
            this.UserGroup = UserGroup;
        }

        public boolean isIsSubstitued() {
            return IsSubstitued;
        }

        public void setIsSubstitued(boolean IsSubstitued) {
            this.IsSubstitued = IsSubstitued;
        }

        public ExtraFieldsBean getExtraFields() {
            return ExtraFields;
        }

        public void setExtraFields(ExtraFieldsBean ExtraFields) {
            this.ExtraFields = ExtraFields;
        }

        public ExtraFieldsForESBean getExtraFieldsForES() {
            return ExtraFieldsForES;
        }

        public void setExtraFieldsForES(ExtraFieldsForESBean ExtraFieldsForES) {
            this.ExtraFieldsForES = ExtraFieldsForES;
        }

        public Object getRemarks() {
            return Remarks;
        }

        public void setRemarks(Object Remarks) {
            this.Remarks = Remarks;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public int getTotalAwardMoney() {
            return TotalAwardMoney;
        }

        public void setTotalAwardMoney(int TotalAwardMoney) {
            this.TotalAwardMoney = TotalAwardMoney;
        }

        public Object getLastAwardTime() {
            return LastAwardTime;
        }

        public void setLastAwardTime(Object LastAwardTime) {
            this.LastAwardTime = LastAwardTime;
        }

        public Object getAwardLotteryIds() {
            return AwardLotteryIds;
        }

        public void setAwardLotteryIds(Object AwardLotteryIds) {
            this.AwardLotteryIds = AwardLotteryIds;
        }

        public Object getScanSpotStatisticList() {
            return ScanSpotStatisticList;
        }

        public void setScanSpotStatisticList(Object ScanSpotStatisticList) {
            this.ScanSpotStatisticList = ScanSpotStatisticList;
        }

        public int getJoinContactId() {
            return JoinContactId;
        }

        public void setJoinContactId(int JoinContactId) {
            this.JoinContactId = JoinContactId;
        }

        public boolean isExistOrder() {
            return ExistOrder;
        }

        public void setExistOrder(boolean ExistOrder) {
            this.ExistOrder = ExistOrder;
        }

        public int getTicketOrderID() {
            return TicketOrderID;
        }

        public void setTicketOrderID(int TicketOrderID) {
            this.TicketOrderID = TicketOrderID;
        }

        public Object getTrackingCode() {
            return TrackingCode;
        }

        public void setTrackingCode(Object TrackingCode) {
            this.TrackingCode = TrackingCode;
        }

        public int getRecordVersion() {
            return RecordVersion;
        }

        public void setRecordVersion(int RecordVersion) {
            this.RecordVersion = RecordVersion;
        }

        public Object getSiteDiscountCode() {
            return SiteDiscountCode;
        }

        public void setSiteDiscountCode(Object SiteDiscountCode) {
            this.SiteDiscountCode = SiteDiscountCode;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getSendCodeTimes() {
            return SendCodeTimes;
        }

        public void setSendCodeTimes(int SendCodeTimes) {
            this.SendCodeTimes = SendCodeTimes;
        }

        public int getSendCodeTimesFix() {
            return SendCodeTimesFix;
        }

        public void setSendCodeTimesFix(int SendCodeTimesFix) {
            this.SendCodeTimesFix = SendCodeTimesFix;
        }

        public int getAllowSendCodeTimes() {
            return AllowSendCodeTimes;
        }

        public void setAllowSendCodeTimes(int AllowSendCodeTimes) {
            this.AllowSendCodeTimes = AllowSendCodeTimes;
        }

        public int getAllowSendSMSTimes() {
            return AllowSendSMSTimes;
        }

        public void setAllowSendSMSTimes(int AllowSendSMSTimes) {
            this.AllowSendSMSTimes = AllowSendSMSTimes;
        }

        public int getSendSMSTimes() {
            return SendSMSTimes;
        }

        public void setSendSMSTimes(int SendSMSTimes) {
            this.SendSMSTimes = SendSMSTimes;
        }

        public boolean isUseReplySMS() {
            return UseReplySMS;
        }

        public void setUseReplySMS(boolean UseReplySMS) {
            this.UseReplySMS = UseReplySMS;
        }

        public Object getIdCardNo() {
            return IdCardNo;
        }

        public void setIdCardNo(Object IdCardNo) {
            this.IdCardNo = IdCardNo;
        }

        public boolean isIsEmailOpened() {
            return IsEmailOpened;
        }

        public void setIsEmailOpened(boolean IsEmailOpened) {
            this.IsEmailOpened = IsEmailOpened;
        }

        public Object getIdCardType() {
            return IdCardType;
        }

        public void setIdCardType(Object IdCardType) {
            this.IdCardType = IdCardType;
        }

        public int getPrintCount() {
            return PrintCount;
        }

        public void setPrintCount(int PrintCount) {
            this.PrintCount = PrintCount;
        }

        public Object getDepartmentEx() {
            return DepartmentEx;
        }

        public void setDepartmentEx(Object DepartmentEx) {
            this.DepartmentEx = DepartmentEx;
        }

        public Object getPrintBG() {
            return PrintBG;
        }

        public void setPrintBG(Object PrintBG) {
            this.PrintBG = PrintBG;
        }

        public Object getLastPrintTime() {
            return LastPrintTime;
        }

        public void setLastPrintTime(Object LastPrintTime) {
            this.LastPrintTime = LastPrintTime;
        }

        public String getFullPY() {
            return FullPY;
        }

        public void setFullPY(String FullPY) {
            this.FullPY = FullPY;
        }

        public Object getSignInFromName() {
            return SignInFromName;
        }

        public void setSignInFromName(Object SignInFromName) {
            this.SignInFromName = SignInFromName;
        }

        public Object getRoleIds() {
            return RoleIds;
        }

        public void setRoleIds(Object RoleIds) {
            this.RoleIds = RoleIds;
        }

        public Object getRoleName() {
            return RoleName;
        }

        public void setRoleName(Object RoleName) {
            this.RoleName = RoleName;
        }

        public int getLevelId() {
            return LevelId;
        }

        public void setLevelId(int LevelId) {
            this.LevelId = LevelId;
        }

        public Object getLevelName() {
            return LevelName;
        }

        public void setLevelName(Object LevelName) {
            this.LevelName = LevelName;
        }

        public Object getLeaveReason() {
            return LeaveReason;
        }

        public void setLeaveReason(Object LeaveReason) {
            this.LeaveReason = LeaveReason;
        }

        public Object getRejectReason() {
            return RejectReason;
        }

        public void setRejectReason(Object RejectReason) {
            this.RejectReason = RejectReason;
        }

        public int getApprovalLeaveStatue() {
            return ApprovalLeaveStatue;
        }

        public void setApprovalLeaveStatue(int ApprovalLeaveStatue) {
            this.ApprovalLeaveStatue = ApprovalLeaveStatue;
        }

        public String getApprovalLeaveStatuestr() {
            return ApprovalLeaveStatuestr;
        }

        public void setApprovalLeaveStatuestr(String ApprovalLeaveStatuestr) {
            this.ApprovalLeaveStatuestr = ApprovalLeaveStatuestr;
        }

        public Object getApprovalTime() {
            return ApprovalTime;
        }

        public void setApprovalTime(Object ApprovalTime) {
            this.ApprovalTime = ApprovalTime;
        }

        public int getAlterJoinFieldStatue() {
            return AlterJoinFieldStatue;
        }

        public void setAlterJoinFieldStatue(int AlterJoinFieldStatue) {
            this.AlterJoinFieldStatue = AlterJoinFieldStatue;
        }

        public Object getAlterJoinFieldContent() {
            return AlterJoinFieldContent;
        }

        public void setAlterJoinFieldContent(Object AlterJoinFieldContent) {
            this.AlterJoinFieldContent = AlterJoinFieldContent;
        }

        public int getJoinNumber() {
            return JoinNumber;
        }

        public void setJoinNumber(int JoinNumber) {
            this.JoinNumber = JoinNumber;
        }

        public String getOperatingUserName() {
            return OperatingUserName;
        }

        public void setOperatingUserName(String OperatingUserName) {
            this.OperatingUserName = OperatingUserName;
        }

        public int getOperatingUserType() {
            return OperatingUserType;
        }

        public void setOperatingUserType(int OperatingUserType) {
            this.OperatingUserType = OperatingUserType;
        }

        public Object getGlobalRoaming() {
            return GlobalRoaming;
        }

        public void setGlobalRoaming(Object GlobalRoaming) {
            this.GlobalRoaming = GlobalRoaming;
        }

        public int getBelongsUserId() {
            return BelongsUserId;
        }

        public void setBelongsUserId(int BelongsUserId) {
            this.BelongsUserId = BelongsUserId;
        }

        public Object getNickName() {
            return NickName;
        }

        public void setNickName(Object NickName) {
            this.NickName = NickName;
        }

        public boolean isIsSignInProductPackageDeduction() {
            return IsSignInProductPackageDeduction;
        }

        public void setIsSignInProductPackageDeduction(boolean IsSignInProductPackageDeduction) {
            this.IsSignInProductPackageDeduction = IsSignInProductPackageDeduction;
        }

        public Object getMemberType() {
            return MemberType;
        }

        public void setMemberType(Object MemberType) {
            this.MemberType = MemberType;
        }

        public Object getMemberTypeId() {
            return MemberTypeId;
        }

        public void setMemberTypeId(Object MemberTypeId) {
            this.MemberTypeId = MemberTypeId;
        }

        public Object getMemberGroup() {
            return MemberGroup;
        }

        public void setMemberGroup(Object MemberGroup) {
            this.MemberGroup = MemberGroup;
        }

        public Object getMemberGroupIds() {
            return MemberGroupIds;
        }

        public void setMemberGroupIds(Object MemberGroupIds) {
            this.MemberGroupIds = MemberGroupIds;
        }

        public Object getContactGroup() {
            return ContactGroup;
        }

        public void setContactGroup(Object ContactGroup) {
            this.ContactGroup = ContactGroup;
        }

        public Object getContactGroupIds() {
            return ContactGroupIds;
        }

        public void setContactGroupIds(Object ContactGroupIds) {
            this.ContactGroupIds = ContactGroupIds;
        }

        public Object getRealPayMoney() {
            return RealPayMoney;
        }

        public void setRealPayMoney(Object RealPayMoney) {
            this.RealPayMoney = RealPayMoney;
        }

        public Object getRealPayRemark() {
            return RealPayRemark;
        }

        public void setRealPayRemark(Object RealPayRemark) {
            this.RealPayRemark = RealPayRemark;
        }

        public boolean isIsAnonymousFill() {
            return IsAnonymousFill;
        }

        public void setIsAnonymousFill(boolean IsAnonymousFill) {
            this.IsAnonymousFill = IsAnonymousFill;
        }

        public int getFaceStatus() {
            return FaceStatus;
        }

        public void setFaceStatus(int FaceStatus) {
            this.FaceStatus = FaceStatus;
        }

        public String getFaceMessage() {
            return FaceMessage;
        }

        public void setFaceMessage(String FaceMessage) {
            this.FaceMessage = FaceMessage;
        }

        public Object getPersonId() {
            return PersonId;
        }

        public void setPersonId(Object PersonId) {
            this.PersonId = PersonId;
        }

        public boolean isNotCheck() {
            return NotCheck;
        }

        public void setNotCheck(boolean NotCheck) {
            this.NotCheck = NotCheck;
        }

        public Object getSignImagePath() {
            return SignImagePath;
        }

        public void setSignImagePath(Object SignImagePath) {
            this.SignImagePath = SignImagePath;
        }

        public Object getArrivedFlightNo() {
            return ArrivedFlightNo;
        }

        public void setArrivedFlightNo(Object ArrivedFlightNo) {
            this.ArrivedFlightNo = ArrivedFlightNo;
        }

        public String getArrivedFlightDate() {
            return ArrivedFlightDate;
        }

        public void setArrivedFlightDate(String ArrivedFlightDate) {
            this.ArrivedFlightDate = ArrivedFlightDate;
        }

        public Object getActualArrivedTime() {
            return ActualArrivedTime;
        }

        public void setActualArrivedTime(Object ActualArrivedTime) {
            this.ActualArrivedTime = ActualArrivedTime;
        }

        public Object getArrivedAirport() {
            return ArrivedAirport;
        }

        public void setArrivedAirport(Object ArrivedAirport) {
            this.ArrivedAirport = ArrivedAirport;
        }

        public Object getArrivedTerminal() {
            return ArrivedTerminal;
        }

        public void setArrivedTerminal(Object ArrivedTerminal) {
            this.ArrivedTerminal = ArrivedTerminal;
        }

        public Object getLeaveFlightNo() {
            return LeaveFlightNo;
        }

        public void setLeaveFlightNo(Object LeaveFlightNo) {
            this.LeaveFlightNo = LeaveFlightNo;
        }

        public String getLeaveFlightDate() {
            return LeaveFlightDate;
        }

        public void setLeaveFlightDate(String LeaveFlightDate) {
            this.LeaveFlightDate = LeaveFlightDate;
        }

        public Object getActualLeaveTime() {
            return ActualLeaveTime;
        }

        public void setActualLeaveTime(Object ActualLeaveTime) {
            this.ActualLeaveTime = ActualLeaveTime;
        }

        public Object getLeaveAirport() {
            return LeaveAirport;
        }

        public void setLeaveAirport(Object LeaveAirport) {
            this.LeaveAirport = LeaveAirport;
        }

        public Object getLeaveTerminal() {
            return LeaveTerminal;
        }

        public void setLeaveTerminal(Object LeaveTerminal) {
            this.LeaveTerminal = LeaveTerminal;
        }

        public Object getFaceCutImagePath() {
            return FaceCutImagePath;
        }

        public void setFaceCutImagePath(Object FaceCutImagePath) {
            this.FaceCutImagePath = FaceCutImagePath;
        }

        public Object getFaceFeatureBase64String() {
            return FaceFeatureBase64String;
        }

        public void setFaceFeatureBase64String(Object FaceFeatureBase64String) {
            this.FaceFeatureBase64String = FaceFeatureBase64String;
        }

        public Object getJoinRight() {
            return JoinRight;
        }

        public void setJoinRight(Object JoinRight) {
            this.JoinRight = JoinRight;
        }

        public boolean isIsBoothNumAllot() {
            return IsBoothNumAllot;
        }

        public void setIsBoothNumAllot(boolean IsBoothNumAllot) {
            this.IsBoothNumAllot = IsBoothNumAllot;
        }

        public boolean isIsConfirmBoothUp() {
            return IsConfirmBoothUp;
        }

        public void setIsConfirmBoothUp(boolean IsConfirmBoothUp) {
            this.IsConfirmBoothUp = IsConfirmBoothUp;
        }

        public boolean isIsConfirmBoothSend() {
            return IsConfirmBoothSend;
        }

        public void setIsConfirmBoothSend(boolean IsConfirmBoothSend) {
            this.IsConfirmBoothSend = IsConfirmBoothSend;
        }

        public boolean isIsConfirmBoothDown() {
            return IsConfirmBoothDown;
        }

        public void setIsConfirmBoothDown(boolean IsConfirmBoothDown) {
            this.IsConfirmBoothDown = IsConfirmBoothDown;
        }

        public boolean isIsAddFixedCost() {
            return IsAddFixedCost;
        }

        public void setIsAddFixedCost(boolean IsAddFixedCost) {
            this.IsAddFixedCost = IsAddFixedCost;
        }

        public Object getConfirmBoothUrl() {
            return ConfirmBoothUrl;
        }

        public void setConfirmBoothUrl(Object ConfirmBoothUrl) {
            this.ConfirmBoothUrl = ConfirmBoothUrl;
        }

        public Object getBoothNum() {
            return BoothNum;
        }

        public void setBoothNum(Object BoothNum) {
            this.BoothNum = BoothNum;
        }

        public Object getBoothArea() {
            return BoothArea;
        }

        public void setBoothArea(Object BoothArea) {
            this.BoothArea = BoothArea;
        }

        public int getExhibitorId() {
            return ExhibitorId;
        }

        public void setExhibitorId(int ExhibitorId) {
            this.ExhibitorId = ExhibitorId;
        }

        public Object getExhibitorNameZhengJian() {
            return ExhibitorNameZhengJian;
        }

        public void setExhibitorNameZhengJian(Object ExhibitorNameZhengJian) {
            this.ExhibitorNameZhengJian = ExhibitorNameZhengJian;
        }

        public int getLimitCertificatesCount() {
            return LimitCertificatesCount;
        }

        public void setLimitCertificatesCount(int LimitCertificatesCount) {
            this.LimitCertificatesCount = LimitCertificatesCount;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public Object getNeedChangeFieldNames() {
            return NeedChangeFieldNames;
        }

        public void setNeedChangeFieldNames(Object NeedChangeFieldNames) {
            this.NeedChangeFieldNames = NeedChangeFieldNames;
        }

        public int getDbBackupHistoryLogId() {
            return DbBackupHistoryLogId;
        }

        public void setDbBackupHistoryLogId(int DbBackupHistoryLogId) {
            this.DbBackupHistoryLogId = DbBackupHistoryLogId;
        }

        public boolean isNotNeedRecordDbOperatingHistoryLog() {
            return NotNeedRecordDbOperatingHistoryLog;
        }

        public void setNotNeedRecordDbOperatingHistoryLog(boolean NotNeedRecordDbOperatingHistoryLog) {
            this.NotNeedRecordDbOperatingHistoryLog = NotNeedRecordDbOperatingHistoryLog;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public List<String> getRelativeMobilePhones() {
            return RelativeMobilePhones;
        }

        public void setRelativeMobilePhones(List<String> RelativeMobilePhones) {
            this.RelativeMobilePhones = RelativeMobilePhones;
        }

        public List<Integer> getOrderIdList() {
            return OrderIdList;
        }

        public void setOrderIdList(List<Integer> OrderIdList) {
            this.OrderIdList = OrderIdList;
        }

        public List<?> getRelativeSpotIds() {
            return RelativeSpotIds;
        }

        public void setRelativeSpotIds(List<?> RelativeSpotIds) {
            this.RelativeSpotIds = RelativeSpotIds;
        }

        public List<?> getDiscountCode() {
            return DiscountCode;
        }

        public void setDiscountCode(List<?> DiscountCode) {
            this.DiscountCode = DiscountCode;
        }

        public List<TicketInfoListBean> getTicketInfoList() {
            return TicketInfoList;
        }

        public void setTicketInfoList(List<TicketInfoListBean> TicketInfoList) {
            this.TicketInfoList = TicketInfoList;
        }

        public static class GiftBean {
            /**
             * GiftStatus : 0
             * GiftToMobile : null
             * GiftToRealName : null
             */

            private int GiftStatus;
            private Object GiftToMobile;
            private Object GiftToRealName;

            public int getGiftStatus() {
                return GiftStatus;
            }

            public void setGiftStatus(int GiftStatus) {
                this.GiftStatus = GiftStatus;
            }

            public Object getGiftToMobile() {
                return GiftToMobile;
            }

            public void setGiftToMobile(Object GiftToMobile) {
                this.GiftToMobile = GiftToMobile;
            }

            public Object getGiftToRealName() {
                return GiftToRealName;
            }

            public void setGiftToRealName(Object GiftToRealName) {
                this.GiftToRealName = GiftToRealName;
            }
        }

        public static class RetroactiveBean {
            /**
             * RetroactiveMoney : 0
             * RequiredRetroactive : false
             * IsRetroactiveSuccess : false
             * RetroactiveOrderID : null
             * RetroactiveTime : null
             */

            private int RetroactiveMoney;
            private boolean RequiredRetroactive;
            private boolean IsRetroactiveSuccess;
            private Object RetroactiveOrderID;
            private Object RetroactiveTime;

            public int getRetroactiveMoney() {
                return RetroactiveMoney;
            }

            public void setRetroactiveMoney(int RetroactiveMoney) {
                this.RetroactiveMoney = RetroactiveMoney;
            }

            public boolean isRequiredRetroactive() {
                return RequiredRetroactive;
            }

            public void setRequiredRetroactive(boolean RequiredRetroactive) {
                this.RequiredRetroactive = RequiredRetroactive;
            }

            public boolean isIsRetroactiveSuccess() {
                return IsRetroactiveSuccess;
            }

            public void setIsRetroactiveSuccess(boolean IsRetroactiveSuccess) {
                this.IsRetroactiveSuccess = IsRetroactiveSuccess;
            }

            public Object getRetroactiveOrderID() {
                return RetroactiveOrderID;
            }

            public void setRetroactiveOrderID(Object RetroactiveOrderID) {
                this.RetroactiveOrderID = RetroactiveOrderID;
            }

            public Object getRetroactiveTime() {
                return RetroactiveTime;
            }

            public void setRetroactiveTime(Object RetroactiveTime) {
                this.RetroactiveTime = RetroactiveTime;
            }
        }

        public static class RetroactiveBeanX {
            /**
             * RetroactiveMoney : 0
             * RequiredRetroactive : false
             * IsRetroactiveSuccess : false
             * RetroactiveOrderID : null
             * RetroactiveTime : null
             */

            private int RetroactiveMoney;
            private boolean RequiredRetroactive;
            private boolean IsRetroactiveSuccess;
            private Object RetroactiveOrderID;
            private Object RetroactiveTime;

            public int getRetroactiveMoney() {
                return RetroactiveMoney;
            }

            public void setRetroactiveMoney(int RetroactiveMoney) {
                this.RetroactiveMoney = RetroactiveMoney;
            }

            public boolean isRequiredRetroactive() {
                return RequiredRetroactive;
            }

            public void setRequiredRetroactive(boolean RequiredRetroactive) {
                this.RequiredRetroactive = RequiredRetroactive;
            }

            public boolean isIsRetroactiveSuccess() {
                return IsRetroactiveSuccess;
            }

            public void setIsRetroactiveSuccess(boolean IsRetroactiveSuccess) {
                this.IsRetroactiveSuccess = IsRetroactiveSuccess;
            }

            public Object getRetroactiveOrderID() {
                return RetroactiveOrderID;
            }

            public void setRetroactiveOrderID(Object RetroactiveOrderID) {
                this.RetroactiveOrderID = RetroactiveOrderID;
            }

            public Object getRetroactiveTime() {
                return RetroactiveTime;
            }

            public void setRetroactiveTime(Object RetroactiveTime) {
                this.RetroactiveTime = RetroactiveTime;
            }
        }

        public static class ExtraFieldsBean {
        }

        public static class ExtraFieldsForESBean {
        }

        public static class TicketInfoListBean {
            /**
             * TicketId : 1655847778
             * TicketName : 观众
             * TicketPrice : 0
             * CateId : 1655847777
             * CateName : 注册通道
             * SpotId : 0
             * SignInTime : null
             * IsSignIn : false
             * EventId : 1655847707
             * VerifyCode :
             */

            private int TicketId;
            private String TicketName;
            private int TicketPrice;
            private int CateId;
            private String CateName;
            private int SpotId;
            private Object SignInTime;
            private boolean IsSignIn;
            private int EventId;
            private String VerifyCode;

            public int getTicketId() {
                return TicketId;
            }

            public void setTicketId(int TicketId) {
                this.TicketId = TicketId;
            }

            public String getTicketName() {
                return TicketName;
            }

            public void setTicketName(String TicketName) {
                this.TicketName = TicketName;
            }

            public int getTicketPrice() {
                return TicketPrice;
            }

            public void setTicketPrice(int TicketPrice) {
                this.TicketPrice = TicketPrice;
            }

            public int getCateId() {
                return CateId;
            }

            public void setCateId(int CateId) {
                this.CateId = CateId;
            }

            public String getCateName() {
                return CateName;
            }

            public void setCateName(String CateName) {
                this.CateName = CateName;
            }

            public int getSpotId() {
                return SpotId;
            }

            public void setSpotId(int SpotId) {
                this.SpotId = SpotId;
            }

            public Object getSignInTime() {
                return SignInTime;
            }

            public void setSignInTime(Object SignInTime) {
                this.SignInTime = SignInTime;
            }

            public boolean isIsSignIn() {
                return IsSignIn;
            }

            public void setIsSignIn(boolean IsSignIn) {
                this.IsSignIn = IsSignIn;
            }

            public int getEventId() {
                return EventId;
            }

            public void setEventId(int EventId) {
                this.EventId = EventId;
            }

            public String getVerifyCode() {
                return VerifyCode;
            }

            public void setVerifyCode(String VerifyCode) {
                this.VerifyCode = VerifyCode;
            }
        }
    }
}
