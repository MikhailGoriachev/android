package org.goriachev.homework.models;


import java.util.Date;


// Класс Запись результата запроса 6
public class Query06 {

    // дата приёма
    private Date appointmentDate;

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    // минимальная стоимость приёма
    private int minPrice;

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    // средняя стоимость приёма
    private double avgPrice;

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    // максимальная стоимость приёма
    private int maxPrice;

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    // количество
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    // конструктор по умолчанию
    public Query06() {
    }

    // конструктор инициализирующий
    public Query06(Date appointmentDate, int minPrice, double avgPrice, int maxPrice, int count) {
        this.setAppointmentDate(appointmentDate);
        this.setMinPrice(minPrice);
        this.setAvgPrice(avgPrice);
        this.setMaxPrice(maxPrice);
        this.setCount(count);
    }
}
