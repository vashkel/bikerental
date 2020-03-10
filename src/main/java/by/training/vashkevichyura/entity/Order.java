package by.training.vashkevichyura.entity;

import java.time.LocalDateTime;

public class Order extends Entity{
    private static final long serialVersionUID = -4434253064260999533L;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double sum;
    private User user;
    private Bike bike;
    private OrderStatusEnum status;


    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (Double.compare(order.sum, sum) != 0) {
            return false;
        }
        if (!startDate.equals(order.startDate)) {
            return false;
        }
        if (!endDate.equals(order.endDate)) {
            return false;
        }
        if (!user.equals(order.user)) {
            return false;
        }
        if (!bike.equals(order.bike)) {
            return false;
        }
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
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
                ", endDate=" + endDate +
                ", sum=" + sum +
                ", user=" + user +
                ", bike=" + bike +
                ", status=" + status +
                '}';
    }
}
