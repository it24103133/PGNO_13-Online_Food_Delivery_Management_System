package com.fooddelivery.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    private String id;
    private String menuItemId;
    private String name;
    private double price;
    private int quantity;
    private String specialInstructions;

    public OrderItem(String menuItemId, String name, double price, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}