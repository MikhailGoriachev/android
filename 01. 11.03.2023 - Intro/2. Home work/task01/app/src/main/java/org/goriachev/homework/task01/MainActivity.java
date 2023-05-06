package org.goriachev.homework.task01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.goriachev.homework.task01.models.Triangle;
import org.goriachev.homework.task01.utils.Utils;

public class MainActivity extends AppCompatActivity {

    // треугольник
    private Triangle triangle;

    // поля для вывода сторон
    private TextView txvSideA, txvSideB, txvSideC;

    // поле для вывода результата
    private TextView txvResult;

    // кнопки
    private Button btnPerimeter, btnArea;


    // событие создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // поиск элементов представления
        txvSideA = findViewById(R.id.txv_side_a);
        txvSideB = findViewById(R.id.txv_side_b);
        txvSideC = findViewById(R.id.txv_side_c);
        txvResult = findViewById(R.id.txv_result_message);

        btnPerimeter = findViewById(R.id.btn_perimeter);
        btnArea = findViewById(R.id.btn_area);

        // изначально кнопки недоступны
        btnPerimeter.setEnabled(false);
        btnArea.setEnabled(false);
    }

    // генерация данных
    public void generation(View view) {
        triangle = Triangle.factory();

        setResultMessage("Сгенерированны новые данные");
        onUpdateTriangle();

        // перевод кнопок в активный режим
        btnPerimeter.setEnabled(true);
        btnArea.setEnabled(true);
    }

    // периметр
    public void perimeter(View view) {
        setResultMessage("Периметр: " + Utils.doubleFormat(triangle.getPerimeter()));
    }

    // площадь
    public void area(View view) {
        setResultMessage("Площадь: " + Utils.doubleFormat(triangle.getArea()));
    }

    // событие обновления данных треугольника
    public void onUpdateTriangle() {
        txvSideA.setText(Utils.doubleFormat(triangle.getA()));
        txvSideB.setText(Utils.doubleFormat(triangle.getB()));
        txvSideC.setText(Utils.doubleFormat(triangle.getC()));
    }

    // установить сообщение результата
    public void setResultMessage(String message) {
        txvResult.setText(message);
    }
}