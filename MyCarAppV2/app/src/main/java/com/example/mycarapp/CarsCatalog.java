package com.example.mycarapp;

public class CarsCatalog {

    private String CarID;
    private String Brand;
    private String Name;
    private String Model;
    private int NoC;
    private String Price;
    private int Image;

    public CarsCatalog(String carID, String brand, String name, String model, int noC, String price, int image) {
        CarID = carID;
        Brand = brand;
        Name = name;
        Model = model;
        NoC = noC;
        Price = price;
        Image = image;
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

    public int getNoC() {
        return NoC;
    }

    public String getPrice() {
        return Price;
    }

    public int getImage() {
        return Image;
    }
}
