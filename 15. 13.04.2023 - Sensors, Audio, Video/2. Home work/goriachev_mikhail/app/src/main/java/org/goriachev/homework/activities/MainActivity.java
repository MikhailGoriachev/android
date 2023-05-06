package org.goriachev.homework.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.preference.PreferenceManager;

import org.goriachev.homework.R;
import org.goriachev.homework.fragments.ExecuteFragment;
import org.goriachev.homework.fragments.InfoFragment;
import org.goriachev.homework.fragments.ResultsFragment;
import org.goriachev.homework.fragments.SettingsFragment;
import org.goriachev.homework.fragments.ViewPagerFragment;
import org.goriachev.homework.infrastructure.Action;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.infrastructure.Settings;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // контейнер для фрагмента
    FragmentContainerView fragmentContainer;


    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_body);

        showTabs();
    }

    @Override
    public void onResume() {
        super.onResume();
        Settings.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    // показать данные веб-сервиса
    public void showTabs() {
        var params = new Bundle();

        var fragment = new ViewPagerFragment();

        List<AbstractMap.SimpleEntry<String, Action<Fragment>>> fragments = List.of(
                new AbstractMap.SimpleEntry<>("Данные", ExecuteFragment::new),
                new AbstractMap.SimpleEntry<>("Результаты", ResultsFragment::new),
                new AbstractMap.SimpleEntry<>("Настройки", SettingsFragment::new),
                new AbstractMap.SimpleEntry<>("О приложении", InfoFragment::new)
        );

        params.putSerializable("fragments", (Serializable) fragments);

        fragment.setArguments(params);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_body, fragment)
                .commit();
    }


    // переход на активность с информацией о разработчике
    public void showInfo() {
        Intent intent = new Intent(this, FragmentViewActivity.class);

        intent.putExtra("fragment", (Serializable) new InfoFragment());
        startActivity(intent);
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
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_info:
                showInfo();
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
    protected void onDestroy() {
        super.onDestroy();
    }

}