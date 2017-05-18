package com.hemant.bountyapp.models;

/**
 * Created by hemant on 5/15/2017.
 */

public class ExpenditureModel {

    String id;
    String amount;
    String date;
    String category;
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

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ExpenditureModel{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
