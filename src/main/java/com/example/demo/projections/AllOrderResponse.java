package com.example.demo.projections;

import java.util.List;

public class AllOrderResponse {
    private List<OrderProjection> orders;
    private int totalPages;

    public AllOrderResponse(List<OrderProjection> orders, int totalPages) {
        this.orders = orders;
        this.totalPages = totalPages;
    }

    public List<OrderProjection> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderProjection> orders) {
        this.orders = orders;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
