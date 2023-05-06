package org.goriachev.homework.activities.task01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.task01.TelevisionGridAdapter;
import org.goriachev.homework.models.task01.Television;
import org.goriachev.homework.models.task01.TelevisionList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TelevisionsMainActivity extends AppCompatActivity {

    // список телевизоров
    TelevisionList televisionList;

    // путь к файлу
    public static final String saveFileName = "television_list.bin";

    // элемент для вывода записей
    GridView gdvTelevisions;

    // адаптер для GridView с телевизорам
    TelevisionGridAdapter televisionGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_televisions_main);


        try {

            if (!load()) {
                televisionList = new TelevisionList();
                televisionList.initialization();
                save();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        gdvTelevisions = findViewById(R.id.gdv_televisions);

        televisionGridAdapter = new TelevisionGridAdapter(
                this,
                R.layout.view_grid_item_television,
                televisionList.getItems()
        );

        gdvTelevisions.setAdapter(televisionGridAdapter);
    }

    // получить объект файла сохранения для работы
    private File getSaveFile() {
        return new File(getFilesDir().getPath(), saveFileName);
    }

    // переход на активность с упорядочиванием элементов по убыванию цены
    private void showTelevisionsOrderByPriceDesc() {
        var intent = new Intent(this, TelevisionsListViewActivity.class);
        intent.putExtra("title", "Сортировка по убыванию цены");
        intent.putParcelableArrayListExtra(
                "items", (ArrayList<Television>) televisionList.sortByPriceDesc()
        );

        startActivity(intent);
    }

    // переход на активность с упорядочиванием элементов по возрастанию диагонали
    private void showTelevisionsOrderByDiagonalAsc() {
        var intent = new Intent(this, TelevisionsListViewActivity.class);
        intent.putExtra("title", "Сортировка по возрастанию диагонали");
        intent.putParcelableArrayListExtra(
                "items", (ArrayList<Television>) televisionList.sortByDiagonalAsc()
        );

        startActivity(intent);
    }

    // загрузка данных
    private boolean load() {
        var file = getSaveFile();

        if (!file.exists()) {
            return false;
        }

        try (FileInputStream stream = new FileInputStream(file)) {
            televisionList = TelevisionList.deserialize(stream);

            return true;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // сохранение данных
    private void save() {
        try (FileOutputStream stream = new FileOutputStream(getSaveFile())) {
            televisionList.serialize(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // работа с главным меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.televisions_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_televisions_order_by_price_descending:
                showTelevisionsOrderByPriceDesc();
                break;
            case R.id.mni_televisions_order_by_diagonal_ascending:
                showTelevisionsOrderByDiagonalAsc();
                break;
            case R.id.mni_exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}