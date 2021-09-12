package com.woyo.woyofood.model.food;

import javax.persistence.*;

@Entity
@Table(name = "tab_food")
public class FoodModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int foodId;

    @Column(name = "resto_id")
    private int restoId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "price")
    private int price;

    @Column(name = "rate")
    private double rate;

    @Column(name = "details")
    private String details;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getRestoId() {
        return restoId;
    }

    public void setRestoId(int restoId) {
        this.restoId = restoId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
