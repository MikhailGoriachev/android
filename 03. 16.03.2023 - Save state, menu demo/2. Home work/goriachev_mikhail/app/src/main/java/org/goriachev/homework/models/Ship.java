package org.goriachev.homework.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.goriachev.homework.utils.Utils;

public class Ship implements Parcelable, Cloneable {

    // название типа судна
    private ShipType shipType;

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    // грузоподъемность
    private int loadCapacity;

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) throws Exception {
        if (loadCapacity < 0)
            throw new Exception("Грузоподъёмность должна быть больше или равна 0");

        this.loadCapacity = loadCapacity;
    }

    // пункт назначения
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) throws Exception {
        if (destination.isEmpty())
            throw new Exception("Поле пункта назначения должно быть заполнено");

        this.destination = destination;
    }

    // тип груза
    private String cargoType;

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) throws Exception {
        if (cargoType.isEmpty())
            throw new Exception("Поле типа груза должно быть заполнено");

        this.cargoType = cargoType;
    }

    // вес груза (тонна)
    private int cargoWeight;

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) throws Exception {
        if (cargoWeight > loadCapacity)
            throw new Exception("Вес груза не может быть больше грузоподъёмности ("+ loadCapacity +"тонн)");

        if (cargoWeight < 0)
            throw new Exception("Вес груза должен быть больше или равен 0");

        this.cargoWeight = cargoWeight;
    }

    // цена 1 тонны груза
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) throws Exception {
        if (price < 0)
            throw new Exception("Цена груза должна быть больше 0");

        this.price = price;
    }

    // требуется якорная стоянка
    private boolean isAnchorage;

    public boolean isAnchorage() {
        return isAnchorage;
    }

    public void setAnchorage(boolean anchorage) {
        isAnchorage = anchorage;
    }

    // требуется дозаправка топливом
    private boolean isRefueling;

    public boolean isRefueling() {
        return isRefueling;
    }

    public void setRefueling(boolean refueling) {
        isRefueling = refueling;
    }

    // требуется лоцман
    private boolean isPilot;

    public boolean isPilot() {
        return isPilot;
    }

    public void setPilot(boolean pilot) {
        isPilot = pilot;
    }


    // конструктор по умолчанию
    public Ship() {
    }

    // конструктор инициализирующий
    public Ship(ShipType shipType, int loadCapacity, String destination, String cargoType,
                int cargoWeight, int price, boolean isAnchorage, boolean isRefueling,
                boolean isPilot) throws Exception {
        this.setShipType(shipType);
        this.setLoadCapacity(loadCapacity);
        this.setDestination(destination);
        this.setCargoType(cargoType);
        this.setCargoWeight(cargoWeight);
        this.setPrice(price);
        this.setAnchorage(isAnchorage);
        this.setRefueling(isRefueling);
        this.setPilot(isPilot);
    }


    // получить стоимость груза
    public long getCost() {
        return (long) price * cargoWeight;
    }

    // фабричный метод
    public static Ship factory() throws Exception {
        ShipType shipType = ShipType.factory();
        int capacity = Utils.getInt(shipType.getMinCapacity(), shipType.getMaxCapacity());
        return new Ship(
                shipType,
                capacity,
                Utils.getItem(Utils.destinations),
                Utils.getItem(Utils.cargoTypes),
                Utils.getInt(shipType.getMinCapacity(), capacity),
                Utils.getInt(5, 10) * 1000,
                Utils.getBoolean(),
                Utils.getBoolean(),
                Utils.getBoolean()
        );
    }


    protected Ship(Parcel in) {
        shipType = in.readParcelable(ShipType.class.getClassLoader());
        loadCapacity = in.readInt();
        destination = in.readString();
        cargoType = in.readString();
        cargoWeight = in.readInt();
        price = in.readInt();
        isAnchorage = in.readByte() != 0;
        isRefueling = in.readByte() != 0;
        isPilot = in.readByte() != 0;
    }

    public static final Creator<Ship> CREATOR = new Creator<Ship>() {
        @Override
        public Ship createFromParcel(Parcel in) {
            return new Ship(in);
        }

        @Override
        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(shipType, i);
        parcel.writeInt(loadCapacity);
        parcel.writeString(destination);
        parcel.writeString(cargoType);
        parcel.writeInt(cargoWeight);
        parcel.writeInt(price);
        parcel.writeByte((byte) (isAnchorage ? 1 : 0));
        parcel.writeByte((byte) (isRefueling ? 1 : 0));
        parcel.writeByte((byte) (isPilot ? 1 : 0));
    }

    @Override
    public Ship clone() {
        try {
            Ship clone = (Ship) super.clone();
            clone.shipType = this.shipType.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
