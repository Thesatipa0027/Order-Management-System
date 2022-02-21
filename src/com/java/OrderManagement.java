package com.java;

import java.util.List;

public interface OrderManagement{
    void addOrder(List<Order> list);
    List<Order> viewOrder();
    void viewOrder(List<Order> list, String orderId);
    void sorOrder(List<Order> list);
    void deleteById(String orderId);
    void markDelivered(List<Order> list);
    void generateReport();
}
