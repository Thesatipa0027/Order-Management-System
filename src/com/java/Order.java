package com.java;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 5073964405131266039L;
    private String orderId;
    private String orderDescription;
    private String address;
    private String orderDate;
    private double amount;
    private String deliveryDateTime;

    public Order(String orderId, String orderDescription, String address, String orderDate, double amount,String deliveryDateTime) {
        this.orderId = orderId;
        this.orderDescription = orderDescription;
        this.address = address;
        this.orderDate = String.valueOf(orderDate);
        this.amount = amount;
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {this.orderId = orderId;}

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    @Override
    public String toString() {
        return "Order Detail:{" +
                "OrderId='" + orderId + '\'' +
                ", Order Description='" + orderDescription + '\'' +
                ", Address='" + address + '\'' +
                ", Order Date=" + orderDate +
                ", Amount=" + amount +
                ", Delivery Datetime=" + deliveryDateTime +
                '}';
    }
}
