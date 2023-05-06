package org.goriachev.homework.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import org.goriachev.homework.R;
import org.goriachev.homework.fragments.InfoFragment;
import org.goriachev.homework.fragments.MainMenuFragment;
import org.goriachev.homework.fragments.ViewPagerFragment;
import org.goriachev.homework.fragments.webData.AlbumListFragment;
import org.goriachev.homework.fragments.webData.CommentListFragment;
import org.goriachev.homework.fragments.webData.PostListFragment;
import org.goriachev.homework.fragments.webData.UserListFragment;
import org.goriachev.homework.infrastructure.Action;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.MainMenuItemSelected {

    // контейнер для фрагмента
    FragmentContainerView fragmentContainer;

    // текущий фрагмент
    Fragment currentFragment;

    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_body);
    }


    // отображение фрагмента
    public void showFragment(Type fragmentType) {
        var isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (InfoFragment.class.equals(fragmentType)) {
            currentFragment = new InfoFragment();
        } else if (AlbumListFragment.class.equals(fragmentType)) {
            currentFragment = new AlbumListFragment();
        } else if (ViewPagerFragment.class.equals(fragmentType)) {
            currentFragment = new ViewPagerFragment();
        } else return;

        var params = getIntent().getBundleExtra("params");

        // отображение в зависимости от ориентации экрана
        if (isHorizontal) {
            currentFragment.setArguments(params);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_body, this.currentFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, FragmentViewActivity.class);
            intent.putExtra("fragment", (Serializable) currentFragment);
            intent.putExtra("params", params);
            startActivity(intent);
        }
    }

    // переход на активность с информацией о разработчике
    public void showInfo() {
        showFragment(InfoFragment.class);
    }

    // показать данные веб-сервиса
    public void showWebData() {
        var params = new Bundle();

        List<AbstractMap.SimpleEntry<String, Action<Fragment>>> fragments = List.of(
                new AbstractMap.SimpleEntry<>("Альбомы", AlbumListFragment::new),
                new AbstractMap.SimpleEntry<>("Комментарии", CommentListFragment::new),
                new AbstractMap.SimpleEntry<>("Посты", PostListFragment::new),
                new AbstractMap.SimpleEntry<>("Пользователи", UserListFragment::new)
        );

        params.putSerializable("fragments", (Serializable) fragments);

        getIntent().putExtra("params", params);

        showFragment(ViewPagerFragment.class);
    }


    // обработчик меню фрагмента
    @Override
    public void onMainMenuItemSelected(int option) {
        switch (option) {
            case MainMenuFragment.MainMenuOption.WEB_DATA:
                showWebData();
                break;
            case MainMenuFragment.MainMenuOption.INFO:
                showInfo();
                break;
            case MainMenuFragment.MainMenuOption.EXIT:
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
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

        outState.putSerializable("currentFragment", (Serializable) currentFragment);
        var params = getIntent().getBundleExtra("params");
        if (params != null)
            outState.putBundle("params", params);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        var isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isHorizontal) {
            currentFragment = (Fragment) savedInstanceState.getSerializable("currentFragment");
            getIntent().putExtra("params", savedInstanceState.getBundle("params"));

            var params = savedInstanceState.getBundle("params");

            if (currentFragment != null) {

                if (params != null) {
                    getIntent().putExtra("params", params);
                    currentFragment.setArguments(params);
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_body, currentFragment)
                        .commit();

            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}