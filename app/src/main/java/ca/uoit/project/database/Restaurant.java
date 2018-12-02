package ca.uoit.project.database;

import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;

public class Restaurant {

    public int id;
    public String name;
    public String location;
    public String foodType;
    public int price;
    public Atmosphere atmosphere;
    public ServingMethod servingMethod;
    public boolean favourite;

    public Restaurant(int id, String name, String location, String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod, boolean favourite) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.foodType = foodType;
        this.price = price;
        this.atmosphere = atmosphere;
        this.servingMethod = servingMethod;
        this.favourite = favourite;
    }

    public Restaurant(String name, String location, String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod) {
        this.name = name;
        this.location = location;
        this.foodType = foodType;
        this.price = price;
        this.atmosphere = atmosphere;
        this.servingMethod = servingMethod;
    }
}