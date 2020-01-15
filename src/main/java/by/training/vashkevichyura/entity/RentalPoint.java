package by.training.vashkevichyura.entity;

public class RentalPoint extends Entity {
    private static final long serialVersionUID = 8788349593812835802L;
    private String name;
    private String adress;
    private String tel;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "RentalPoint{" +
                "name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
