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
}
