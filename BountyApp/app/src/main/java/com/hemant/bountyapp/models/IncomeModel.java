package com.hemant.bountyapp.models;

/**
 * Created by hemant on 5/15/2017.
 */

public class IncomeModel {

    String id;
    String amount;
    String date;
    String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amout) {
        this.amount = amout;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "IncomeModel{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
