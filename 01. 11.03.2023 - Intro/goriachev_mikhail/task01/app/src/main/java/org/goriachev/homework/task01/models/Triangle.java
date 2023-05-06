package org.goriachev.homework.task01.models;

import android.annotation.SuppressLint;

import org.goriachev.homework.task01.utils.Utils;

import java.security.InvalidParameterException;


// Класс Треугольник
public class Triangle {

    // сторона a
    private double a = 1d;

    public double getA() {
        return a;
    }

    // сторона b
    private double b = 1d;

    public double getB() {
        return b;
    }

    // сторона c
    private double c = 1d;

    public double getC() {
        return c;
    }

    public void setSides(double a, double b, double c) throws InvalidParameterException {

        if (!isTriangle(a, b, c)) {
            @SuppressLint("DefaultLocale")
            String message = String.format(
                    "Triangle: стороны невалидны: a: %.5f; b: %.5f; c: %.5f",
                    a, b, c
            );

            throw new InvalidParameterException(message);
        }

        // корректные значения
        this.a = a;
        this.b = b;
        this.c = c;
    }


    // конструктор по умолчанию
    public Triangle() {
    }

    // конструктор инициализирующий
    public Triangle(double a, double b, double c) throws InvalidParameterException {
        setSides(a, b, c);
    }


    // проверка правильность треугольника
    private boolean isTriangle(double a, double b, double c) {
        return a + b > c && a + c > b && b + c > a;
    }

    // вычисление периметра
    public double getPerimeter() {
        return a + b + c;
    }

    // вычисление площади
    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    // фабричный метод
    public static Triangle factory() {
        return new Triangle(
                Utils.getDouble(20d, 25d),
                Utils.getDouble(20d, 25d),
                Utils.getDouble(20d, 25d)
        );
    }
}
