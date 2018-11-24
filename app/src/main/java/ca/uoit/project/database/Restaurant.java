package ca.uoit.project.database;

import java.util.ArrayList;
import java.util.List;

import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;

public class Restaurant {

    public int id;
    public String name;
    public String address;
    public String foodType;
    public int price;
    public Atmosphere atmosphere;
    public ServingMethod servingMethod;

    public Restaurant(int id, String name, String address, String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.foodType = foodType;
        this.price = price;
        this.atmosphere = atmosphere;
        this.servingMethod = servingMethod;
    }

    public Restaurant(String name, String address, String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod) {
        this.name = name;
        this.address = address;
        this.foodType = foodType;
        this.price = price;
        this.atmosphere = atmosphere;
        this.servingMethod = servingMethod;
    }
}