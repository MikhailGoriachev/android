package org.goriachev.homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.goriachev.homework.activities.InfoActivity;
import org.goriachev.homework.activities.task01.TelevisionsMainActivity;
import org.goriachev.homework.activities.task02.AuthorsActivity;
import org.goriachev.homework.activities.task02.AuthorsStatisticActivity;
import org.goriachev.homework.activities.task02.CategoriesActivity;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // переход на активность со списком суден
    public void showTelevisionsList(View view) {
        Intent intent = new Intent(this, TelevisionsMainActivity.class);
        startActivity(intent);
    }

    // переход на активность со списком авторов
    public void showAuthorsList(View view) {
        Intent intent = new Intent(this, AuthorsActivity.class);
        startActivity(intent);
    }

    // переход на активность со списком категорий
    public void showCategoriesList(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    // переход на активность со списком статистики авторов
    public void showAuthorsStatisticList(View view) {
        Intent intent = new Intent(this, AuthorsStatisticActivity.class);
        startActivity(intent);
    }

    // переход на активность с информацией о разработчике
    public void showInfo(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    // завершение активности
    public void finish(View view) {
        finish();
    }

    // обработчик события получения результата из активности
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != ResultCode.RESULT_OK)
            Utils.showSnakeBar(null, "Ошибка с кодом: " + resultCode);
    }

    // работа с главным меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_televisions:
                showTelevisionsList(null);
                break;
            case R.id.mni_authors:
                showAuthorsList(null);
                break;
            case R.id.mni_categories:
                showCategoriesList(null);
                break;
            case R.id.mni_authors_statistic:
                showAuthorsStatisticList(null);
                break;
            case R.id.mni_info:
                showInfo(null);
                break;
            case R.id.mni_exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}