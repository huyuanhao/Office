package com.yt.simpleframe.http.bean;

import java.util.List;

public class WaterInfoBean extends BaseBean {
        private List<WaterInfo> DATA;

        public List<WaterInfo> getDATA() {
            return DATA;
        }

        public void setDATA(List<WaterInfo> DATA) {
            this.DATA = DATA;
        }
}
