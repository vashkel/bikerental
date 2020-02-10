package by.training.vashkevichyura.entity;

import java.util.Date;

public class Order extends Entity{
    private static final long serialVersionUID = -4434253064260999533L;
    private Date startDate;
    private Date end_Date;
    private double sum;
    private User user;
    private Bike bike;
    private OrderStatusEnum status;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.sum, sum) != 0) return false;
        if (!startDate.equals(order.startDate)) return false;
        if (!end_Date.equals(order.end_Date)) return false;
        if (!user.equals(order.user)) return false;
        if (!bike.equals(order.bike)) return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = startDate.hashCode();
        result = 31 * result + end_Date.hashCode();
        temp = Double.doubleToLongBits(sum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + user.hashCode();
        result = 31 * result + bike.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "startDate=" + startDate +
                ", end_Date=" + end_Date +
                ", sum=" + sum +
                ", user=" + user +
                ", bike=" + bike +
                ", status=" + status +
                '}';
    }
}
