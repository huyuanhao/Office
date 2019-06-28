package com.powerrich.office.oa.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;


public class RegisterBean implements IPickerViewData {


   private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public RegisterBean setName(String name) {
        this.name = name;
        return  this;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
