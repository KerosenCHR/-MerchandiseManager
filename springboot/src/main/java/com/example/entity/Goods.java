package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;

/**
 * 商品表
 */
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Alias("ID")
    private Integer id;
    /**
     * 商品名称
     */
    @Alias("商品名称")
    private String name;
    /**
     * 供应商ID
     */
    @Alias("供应商ID")
    private Integer supplierId;
    /**
     * 商品产地
     */
    @Alias("商品产地")
    private String producer;
    /**
     * 商品描述
     */
    @Alias("商品描述")
    private String descr;
    /**
     * 销售价格
     */
    @Alias("销售价格")
    private Double price;
    /**
     * 数量
     */
    @Alias("数量")
    private Integer num;
    /**
     * 商品图片
     */
    @Alias("商品图片")
    private String img;
    /**
     * 商品规格
     */
    @Alias("商品规格")
    private String unit;
    /**
     * 包装单位
     */
    @Alias("包装单位")
    private String pack;
    /**
     * 生产批号
     */
    @Alias("生产批号")
    private String productNo;
    /**
     * 批准文号
     */
    @Alias("批准文号")
    private String approveNo;

    @Alias("状态")
    private String status;

    @Alias("供应商名称")
    private String supplierName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSupplierId() { return supplierId; }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}