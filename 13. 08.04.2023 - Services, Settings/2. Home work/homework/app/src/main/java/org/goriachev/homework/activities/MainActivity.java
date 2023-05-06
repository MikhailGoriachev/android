package org.goriachev.homework.activities;

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
import org.goriachev.homework.fragments.InfoFragment;
import org.goriachev.homework.fragments.SettingsFragment;
import org.goriachev.homework.fragments.messenger.MessageListFragment;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.infrastructure.Settings;
import org.goriachev.homework.services.MessengerService;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    // контейнер для фрагмента
    FragmentContainerView fragmentContainer;

    // текущий фрагмент
    Fragment currentFragment;


    // intent сервиса
    Intent serviceIntent;

    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_body);

        serviceIntent = new Intent(this, MessengerService.class);

        // запуск сервиса сообщений
        startMessengerService();

        showMessageList();
    }

    @Override
    public void onResume() {
        super.onResume();

        Settings.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    // запуск сервиса сообщений
    public void startMessengerService() {
        startService(serviceIntent);
    }

    // остановка сервиса сообщений
    public void stopMessengerService() {
        stopService(serviceIntent);
    }


    // переход на активность с информацией о разработчике
    public void showMessageList() {

        var fragment = new MessageListFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_body, fragment)
                .commit();
    }

    // переход на активность с информацией о разработчике
    public void showInfo() {
        Intent intent = new Intent(this, FragmentViewActivity.class);

        intent.putExtra("fragment", (Serializable) new InfoFragment());
        startActivity(intent);
    }

    // переход на активность с настройками
    public void showSettings() {
        Intent intent = new Intent(this, FragmentViewActivity.class);

        intent.putExtra("fragment", (Serializable) new SettingsFragment());
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_start_service:
                startMessengerService();
                break;
            case R.id.mni_stop_service:
                stopMessengerService();
                break;
            case R.id.mni_info:
                showInfo();
                break;
            case R.id.mni_settings:
                showSettings();
                break;
            case R.id.mni_exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putSerializable("currentFragment", (Serializable) currentFragment);
        var params = getIntent().getBundleExtra("params");
        if (params != null)
            outState.putBundle("params", params);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // остановка сервиса сообщений
        stopMessengerService();
    }
}