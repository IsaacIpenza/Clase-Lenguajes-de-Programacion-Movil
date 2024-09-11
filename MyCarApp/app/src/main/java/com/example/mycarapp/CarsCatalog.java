package com.example.mycarapp;

public class CarsCatalog {

    private String CarID;
    private String Brand;
    private String Name;
    private String Model;

    public CarsCatalog(String carID, String brand, String name, String model) {
        CarID = carID;
        Brand = brand;
        Name = name;
        Model = model;
    }

    public String getCarID() {
        return CarID;
    }

    public String getBrand() {
        return Brand;
    }

    public String getName() {
        return Name;
    }

    public String getModel() {
        return Model;
    }
}
