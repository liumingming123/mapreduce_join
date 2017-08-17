package com.test.join;

/**
 * Created by 小新很忙 on 2017/8/16.
 */
public class CustomerBean {
    private int customID;
    private String name;
    private String address;
    private String telephone;

    public CustomerBean() {
    }

    public CustomerBean(int customID, String name, String address, String telephone) {
        this.customID = customID;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public int getCustomID() {
        return customID;
    }

    public void setCustomID(int customID) {
        this.customID = customID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
