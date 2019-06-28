package com.yt.simpleframe.enums;

/**
 * Created by wujian on 2016/12/24.
 */

public enum RefreshState {
    LS_INIT(0),
    LS_Refresh(1),
    LS_LoadMore(2);

    private int code;

    RefreshState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
