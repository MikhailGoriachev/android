package org.goriachev.homework.entities;


import androidx.annotation.NonNull;

import java.util.Date;

// Периодическое издание
public class Edition implements Cloneable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // индекс издания по каталогу (строка из цифр и букв)
    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) throws Exception {
        if (index.isEmpty())
            throw new Exception("Поле индекса должно быть заполнено");

        this.index = index;
    }

    // вид издания (газета, журнал, альманах, каталог, …)
    private String publicationType;

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) throws Exception {
        if (publicationType.isEmpty())
            throw new Exception("Поле наименования должно быть заполнено");

        this.publicationType = publicationType;
    }

    // наименование издания (название газеты, журнала, …)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.isEmpty())
            throw new Exception("Поле наименования должно быть заполнено");

        this.name = name;
    }

    // цена 1 экземпляра (в руб.)
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) throws Exception {
        if (price < 0)
            throw new Exception("Цена должен быть больше или равна 0");

        this.price = price;
    }

    // дата начала подписки
    private Date subscribeDate = new Date();

    public Date getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(Date subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    // длительность подписки (количество месяцев)
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) throws Exception {
        if (duration < 0)
            throw new Exception("Длительность подписки должна быть больше 0");

        this.duration = duration;
    }


    public Edition() {
    }

    public Edition(long id, String index, String publicationType, String name, int price, Date subscribeDate, int duration) throws Exception {
        this.setId(id);
        this.setIndex(index);
        this.setPublicationType(publicationType);
        this.setName(name);
        this.setPrice(price);
        this.setSubscribeDate(subscribeDate);
        this.setDuration(duration);
    }

    @NonNull
    @Override
    public Edition clone() {
        try {
            return (Edition) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
