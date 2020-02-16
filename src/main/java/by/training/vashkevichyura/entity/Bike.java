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

    public BikeStatusEnum getBikeStatus() {
        return bikeStatus;
    }

    public void setBikeStatus(BikeStatusEnum bikeStatus) {
        this.bikeStatus = bikeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bike bike = (Bike) o;

        if (!brand.equals(bike.brand)) {
            return false;
        }
        if (!model.equals(bike.model)) {
            return false;
        }
        if (!bikeType.equals(bike.bikeType)) {
            return false;
        }
        if (!rentalPoint.equals(bike.rentalPoint)) {
            return false;
        }
        return bikeStatus == bike.bikeStatus;
    }

    @Override
    public int hashCode() {
        int result = brand.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + bikeType.hashCode();
        result = 31 * result + rentalPoint.hashCode();
        result = 31 * result + bikeStatus.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", bikeType=" + bikeType +
                ", rentalPoint=" + rentalPoint +
                ", bikeStatus=" + bikeStatus +
                '}';
    }
}
