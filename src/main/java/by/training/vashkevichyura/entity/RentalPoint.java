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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalPoint that = (RentalPoint) o;

        if (!name.equals(that.name)) return false;
        if (!adress.equals(that.adress)) return false;
        return tel.equals(that.tel);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + adress.hashCode();
        result = 31 * result + tel.hashCode();
        return result;
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
