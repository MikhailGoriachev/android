package org.goriachev.homework.models;


// Класс Запись результата запроса 7
public class Query07 {

    // специальность
    private String specialityName;

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    // минимальный процент
    private double minPercent;

    public double getMinPercent() {
        return minPercent;
    }

    public void setMinPercent(double minPercent) {
        this.minPercent = minPercent;
    }

    // средний процент
    private double avgPercent;

    public double getAvgPercent() {
        return avgPercent;
    }

    public void setAvgPercent(double avgPercent) {
        this.avgPercent = avgPercent;
    }

    // максимальный процент
    private double maxPercent;

    public double getMaxPercent() {
        return maxPercent;
    }

    public void setMaxPercent(double maxPercent) {
        this.maxPercent = maxPercent;
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
    public Query07() {
    }

    // конструктор инициализирующий
    public Query07(String specialityName, double minPercent, double avgPercent, double maxPercent, int count) {
        this.setSpecialityName(specialityName);
        this.setMinPercent(minPercent);
        this.setAvgPercent(avgPercent);
        this.setMaxPercent(maxPercent);
        this.setCount(count);
    }
}
