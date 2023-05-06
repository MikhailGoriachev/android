package org.goriachev.homework.task02.models;


// Класс Товар
public class Goods {

    // наименование
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.isEmpty())
            throw new Exception("Название товара не может быть пустым");

        this.name = name;
    }

    // количество
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) throws Exception {
        if (amount <= 0)
            throw new Exception("Количество товара должно быть больше 0");

        this.amount = amount;
    }

    // цена
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) throws Exception {
        if (price <= 0)
            throw new Exception("Цена товара должна быть больше 0");

        this.price = price;
    }


    // конструктор по умолчанию
    public Goods() {
    }

    // конструктор инициализирующий
    public Goods(String name, int amount, int price) throws Exception {
        this.setName(name);
        this.setAmount(amount);
        this.setPrice(price);
    }


    // стоимость
    public int getCost() {
        return amount * price;
    }
}
