package by.training.vashkevichyura.entity;

public class RentalCost extends Entity {
    private static final long serialVersionUID = 1169377864944323330L;

    private BikeType bikeType;
    private double price;


    public BikeType getBikeType() {
        return bikeType;
    }

    public void setBikeType(BikeType bikeType) {
        this.bikeType = bikeType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
