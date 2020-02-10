package by.training.vashkevichyura.entity;

public class BikeType extends Entity{
    private static final long serialVersionUID = -1784150257366390793L;

    private BikeTypeEnum type;

    public BikeTypeEnum getType() {
        return type;
    }
    public void setType(BikeTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BikeType bikeType = (BikeType) o;

        return type == bikeType.type;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "BikeType{" +
                "type=" + type +
                '}';
    }
}
