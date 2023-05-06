package org.goriachev.homework.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.goriachev.homework.utils.Utils;


public class Animal implements Parcelable, Cloneable {

    // порода
    private String breed;

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) throws Exception {
        if (breed.isEmpty())
            throw new Exception("Поле породы должно быть заполнено");

        this.breed = breed;
    }

    // кличка
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.isEmpty())
            throw new Exception("Поле клички должно быть заполнено");

        this.name = name;
    }

    // возраст
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws Exception {
        if (age < 0)
            throw new Exception("Возраст должен быть больше или равен 0");

        this.age = age;
    }

    // вес
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) throws Exception {
        if (weight < 0.1)
            throw new Exception("Вес должен быть больше или равен 0,1 кг");

        this.weight = weight;
    }

    // фамилия и инициалы владельца
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) throws Exception {
        if (owner.isEmpty())
            throw new Exception("Поле владельца должно быть заполнено");

        this.owner = owner;
    }

    // имя файла с изображением
    private String imageFile;

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    // признаки: специальная диета
    private boolean isSpecialDiet;

    public boolean isSpecialDiet() {
        return isSpecialDiet;
    }

    public void setSpecialDiet(boolean specialDiet) {
        isSpecialDiet = specialDiet;
    }

    // полу-вольное содержание
    private boolean isFreeKeeping;

    public boolean isFreeKeeping() {
        return isFreeKeeping;
    }

    public void setFreeKeeping(boolean freeKeeping) {
        isFreeKeeping = freeKeeping;
    }


    // конструктор по умолчанию
    public Animal() {
    }

    // конструктор инициализирующий
    public Animal(String breed, String name, int age, double weight, String owner, String imageFile,
                  boolean isSpecialDiet, boolean isFreeKeeping) throws Exception {
        this.setBreed(breed);
        this.setName(name);
        this.setAge(age);
        this.setWeight(weight);
        this.setOwner(owner);
        this.setImageFile(imageFile);
        this.setSpecialDiet(isSpecialDiet);
        this.setFreeKeeping(isFreeKeeping);
    }


    // фабричный метод
    public static Animal factory() throws Exception {
        String[] breed = Utils.getItem(Utils.breeds);
        String[] personArr = Utils.getItem(Utils.people);
        String person = String.format("%s %s. %s", personArr[0], personArr[1].charAt(0), personArr[2].charAt(0));

        return new Animal(
                breed[0],
                Utils.getItem(Utils.animalNames),
                Utils.getInt(1, 5),
                Utils.getDouble(2d, 5d),
                person,
                breed[1],
                Utils.getBoolean(),
                Utils.getBoolean()
        );
    }


    protected Animal(Parcel in) {
        breed = in.readString();
        name = in.readString();
        age = in.readInt();
        weight = in.readDouble();
        owner = in.readString();
        imageFile = in.readString();
        isSpecialDiet = in.readByte() != 0;
        isFreeKeeping = in.readByte() != 0;
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(breed);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeDouble(weight);
        parcel.writeString(owner);
        parcel.writeString(imageFile);
        parcel.writeByte((byte) (isSpecialDiet ? 1 : 0));
        parcel.writeByte((byte) (isFreeKeeping ? 1 : 0));
    }

    @Override
    public Animal clone() {
        try {
            Animal clone = (Animal) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
