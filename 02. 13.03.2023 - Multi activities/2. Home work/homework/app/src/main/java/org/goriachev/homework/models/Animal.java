package org.goriachev.homework.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.goriachev.homework.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Setter
//@Getter
//@NoArgsConstructor
public class Animal implements Parcelable {

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


    protected Animal(Parcel in) {
        breed = in.readString();
        name = in.readString();
        age = in.readInt();
        weight = in.readDouble();
        owner = in.readString();
        imageFile = in.readString();
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

    public Animal() {
    }

    // конструктор инициализирующий
    public Animal(String breed, String name, int age, double weight, String owner, String imageFile) throws Exception {
        this.setBreed(breed);
        this.setName(name);
        this.setAge(age);
        this.setWeight(weight);
        this.setOwner(owner);
        this.setImageFile(imageFile);
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
                breed[1]
        );
    }

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
    }
}
