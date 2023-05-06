package org.goriachev.homework.models.task01;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

// Класс Телевизор
public class Television implements Serializable, Parcelable {

    // производителя и типа телевизора – это одно поле (например, Samsung OLED55DT2020)
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) throws Exception {
        if (model.isEmpty())
            throw new Exception("Поле модели не может быть пустым");

        this.model = model;
    }

    // имя файла с фотографией телевизора в папке assets
    private String imageFile;

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) throws Exception {
        if (imageFile.isEmpty())
            throw new Exception("Поле имени файла не может быть пустым");

        this.imageFile = imageFile;
    }

    // диагонали экрана в дюймах
    private double diagonal;

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) throws Exception {
        if (diagonal <= 0)
            throw new Exception("Диагональ экрана должна быть больше 0");

        this.diagonal = diagonal;
    }

    // разрешения по вертикали в пикселях
    private int verticalResolution;

    public int getVerticalResolution() {
        return verticalResolution;
    }

    public void setVerticalResolution(int verticalResolution) throws Exception {
        if (verticalResolution <= 0)
            throw new Exception("Разрешение по вертикали должно быть больше 0");

        this.verticalResolution = verticalResolution;
    }

    // разрешения по горизонтали в пикселях
    private int horizontalResolution;

    public int getHorizontalResolution() {
        return horizontalResolution;
    }

    public void setHorizontalResolution(int horizontalResolution) throws Exception {
        if (horizontalResolution <= 0)
            throw new Exception("Разрешение по горизонтали должно быть больше 0");

        this.horizontalResolution = horizontalResolution;
    }

    // цена в рублях
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) throws Exception {
        if (price <= 0)
            throw new Exception("Цена должна быть больше 0");

        this.price = price;
    }


    // конструктор по умолчанию
    public Television() {
    }

    // конструктор инициализирующий
    public Television(String model, String imageFile, double diagonal, int verticalResolution, int horizontalResolution, int price) throws Exception {
        this.setModel(model);
        this.setImageFile(imageFile);
        this.setDiagonal(diagonal);
        this.setVerticalResolution(verticalResolution);
        this.setHorizontalResolution(horizontalResolution);
        this.setPrice(price);
    }

    
    protected Television(Parcel in) {
        model = in.readString();
        imageFile = in.readString();
        diagonal = in.readDouble();
        verticalResolution = in.readInt();
        horizontalResolution = in.readInt();
        price = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeString(imageFile);
        dest.writeDouble(diagonal);
        dest.writeInt(verticalResolution);
        dest.writeInt(horizontalResolution);
        dest.writeInt(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Television> CREATOR = new Creator<Television>() {
        @Override
        public Television createFromParcel(Parcel in) {
            return new Television(in);
        }

        @Override
        public Television[] newArray(int size) {
            return new Television[size];
        }
    };
}
