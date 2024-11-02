package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;

/**
 * 商品销售
 */
public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @Alias("序号")
    private Integer id;
    /** 客户ID */
    @Alias("客户ID")
    private Integer customerId;
    /** 商品ID */
    @Alias("商品ID")
    private Integer goodsId;
    /** 支付类型 */
    @Alias("支付类型")
    private String payType;
    /** 销售时间 */
    @Alias("销售时间")
    private String time;
    /** 操作人 */
    @Alias("操作人")
    private String user;
    /** 销售价格 */
    @Alias("销售价格")
    private Double price;
    /** 销售数量 */
    @Alias("销售数量")
    private Integer num;
    /** 商品规格 */
    @Alias("商品规格")
    private String unit;
    /** 备注 */
    @Alias("备注")
    private String comment;
    private String customerName;
    private String goodsName;
    private Double total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}