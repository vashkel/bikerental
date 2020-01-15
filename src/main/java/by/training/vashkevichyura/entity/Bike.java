package by.training.vashkevichyura.entity;

public class Bike extends Entity {
    private static final long serialVersionUID = -7055397869971477475L;
    private String brand;
    private String model;
    private BikeType bikeType;
    private RentalPoint rentalPoint;
    private BikeStatusEnum bikeStatus;



    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BikeType getBikeType() {
        return bikeType;
    }

    public void setBikeType(BikeType bikeType) {
        this.bikeType = bikeType;
    }

    public RentalPoint getRentalPoint() {
        return rentalPoint;
    }

    public void setRentalPoint(RentalPoint rentalPoint) {
        this.rentalPoint = rentalPoint;
    }

    public BikeStatusEnum getBikeStatus() { return bikeStatus; }

    public void setBikeStatus(BikeStatusEnum bikeStatus) { this.bikeStatus = bikeStatus; }
}
