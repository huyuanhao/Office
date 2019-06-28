package com.powerrich.office.oa.bean;

/**
 * @author AlienChao
 * @date 2019/06/13 16:51
 */
public class QRCommitResult {


    /**
     * DefaultMessage : 参会人不能为空
     * Code : 2
     * Message : {"defaultMessage":"参会人不能为空"}
     * MessageToString : 参会人不能为空
     * Body : null
     * ElapsedMilliseconds : 0
     */

    private String DefaultMessage;
    private int Code;
    private MessageBean Message;
    private String MessageToString;
    private Object Body;
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

    public Object getBody() {
        return Body;
    }

    public void setBody(Object Body) {
        this.Body = Body;
    }

    public int getElapsedMilliseconds() {
        return ElapsedMilliseconds;
    }

    public void setElapsedMilliseconds(int ElapsedMilliseconds) {
        this.ElapsedMilliseconds = ElapsedMilliseconds;
    }

    public static class MessageBean {
        /**
         * defaultMessage : 参会人不能为空
         */

        private String defaultMessage;

        public String getDefaultMessage() {
            return defaultMessage;
        }

        public void setDefaultMessage(String defaultMessage) {
            this.defaultMessage = defaultMessage;
        }
    }
}
