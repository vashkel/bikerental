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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalCost that = (RentalCost) o;

        if (Double.compare(that.price, price) != 0) return false;
        return bikeType.equals(that.bikeType);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = bikeType.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "RentalCost{" +
                "bikeType=" + bikeType +
                ", price=" + price +
                '}';
    }
}
