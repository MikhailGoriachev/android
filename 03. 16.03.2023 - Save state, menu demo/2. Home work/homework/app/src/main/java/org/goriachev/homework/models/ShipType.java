package org.goriachev.homework.models;

import android.os.Parcel;
import android.os.Parcelable;


import org.goriachev.homework.utils.Utils;


public class ShipType implements Parcelable, Cloneable {

    // минимальная грузоподъёмность для текущего типа судна (тонна)
    private int minCapacity;

    public int getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(int minCapacity) {
        this.minCapacity = minCapacity;
    }

    // максимальная грузоподъёмность для текущего типа судна (тонна)
    private int maxCapacity;

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // название
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // имя файла
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    // конструктор по умолчанию
    public ShipType() {
    }

    // конструктор инициализирующий
    public ShipType(String name, String fileName, int minCapacity, int maxCapacity) {
        this.setName(name);
        this.setFileName(fileName);
        this.setMinCapacity(minCapacity);
        this.setMaxCapacity(maxCapacity);
    }

    // фабричный метод
    public static ShipType factory() {
        Object[] type = Utils.getItem(Utils.shipTypes);
        return new ShipType((String) type[0], (String) type[1], (int) type[2], (int) type[3]);
    }

    protected ShipType(Parcel in) {
        name = in.readString();
        fileName = in.readString();
    }

    public static final Creator<ShipType> CREATOR = new Creator<ShipType>() {
        @Override
        public ShipType createFromParcel(Parcel in) {
            return new ShipType(in);
        }

        @Override
        public ShipType[] newArray(int size) {
            return new ShipType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fileName);
    }

    @Override
    public ShipType clone() {
        try {
            ShipType clone = (ShipType) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
