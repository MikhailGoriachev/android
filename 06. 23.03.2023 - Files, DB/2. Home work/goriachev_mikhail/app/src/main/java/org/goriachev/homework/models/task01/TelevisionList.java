package org.goriachev.homework.models.task01;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Класс Список телевизоров
public class TelevisionList implements Serializable, Parcelable {

    // список телевизоров
    private List<Television> items;

    public List<Television> getItems() {
        return items;
    }

    public void setItems(List<Television> items) {
        this.items = items;
    }


    // конструктор инициализирующий
    public TelevisionList() {
    }

    // конструктор инициализирующий
    public TelevisionList(List<Television> items) {
        this.setItems(items);
    }


    // инициализация
    public void initialization() throws Exception {
        items = new ArrayList<>(List.of(
                new Television("Xiaomi Mi TV Q1E", "tv1.jpg", 55.0, 2160, 3840, 100000),
                new Television("Samsung QE75QN900", "tv2.jpg", 65.0, 2160, 3840, 150000),
                new Television("Samsung QE55Q60", "tv3.jpg", 55.0, 2160, 3840, 120000),
                new Television("Vivax 65Q10C", "tv4.jpg", 50.0, 2160, 3840, 80000),
                new Television("Hisense 43E7HQ", "tv5.jpg", 55.0, 2160, 3840, 90000),
                new Television("Sharp 55EQ3EA", "tv6.jpg", 55.0, 2160, 3840, 110000),
                new Television("TCL 43C725", "tv7.jpg", 55.0, 2160, 3840, 130000),
                new Television("Skyworth 55Q3B", "tv8.jpg", 55.0, 1440, 2560, 90000),
                new Television("TCL 55C825", "tv9.jpg", 77.0, 2160, 3840, 400000),
                new Television("Samsung QE65QN", "tv10.jpg", 65.0, 2160, 3840, 140000),
                new Television("TCL 50C725", "tv11.jpg", 55.0, 2160, 3840, 70000),
                new Television("Nokia 6500D", "tv12.jpg", 65.0, 2160, 3840, 100000)
        ));
    }

    // сортировка по убыванию цены
    public List<Television> sortByPriceDesc() {
        return getItems().stream()
                .sorted(Comparator.comparingInt(Television::getPrice).reversed())
                .collect(Collectors.toList());
    }

    // сортировка по возрастанию диагонали
    public List<Television> sortByDiagonalAsc() {
        return getItems().stream()
                .sorted(Comparator.comparingDouble(Television::getDiagonal))
                .collect(Collectors.toList());
    }

    // сериализация
    public void serialize(OutputStream outputStream) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        stream.writeObject(this);
        outputStream.close();
    }

    // десериализация
    public static TelevisionList deserialize(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(inputStream);
        return (TelevisionList) stream.readObject();
    }


    protected TelevisionList(Parcel in) {
        items = in.createTypedArrayList(Television.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TelevisionList> CREATOR = new Creator<TelevisionList>() {
        @Override
        public TelevisionList createFromParcel(Parcel in) {
            return new TelevisionList(in);
        }

        @Override
        public TelevisionList[] newArray(int size) {
            return new TelevisionList[size];
        }
    };
}
