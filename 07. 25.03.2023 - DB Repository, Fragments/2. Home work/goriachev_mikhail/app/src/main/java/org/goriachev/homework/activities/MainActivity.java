package org.goriachev.homework.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import org.goriachev.homework.R;
import org.goriachev.homework.fragments.InfoFragment;
import org.goriachev.homework.fragments.MainMenuFragment;
import org.goriachev.homework.fragments.queries.Query01Fragment;
import org.goriachev.homework.fragments.queries.Query02Fragment;
import org.goriachev.homework.fragments.queries.Query03Fragment;
import org.goriachev.homework.fragments.queries.Query04Fragment;
import org.goriachev.homework.fragments.queries.Query05Fragment;
import org.goriachev.homework.fragments.queries.Query06Fragment;
import org.goriachev.homework.fragments.queries.Query07Fragment;
import org.goriachev.homework.fragments.tables.AppointmentListFragment;
import org.goriachev.homework.fragments.tables.DoctorListFragment;
import org.goriachev.homework.fragments.tables.PatientListFragment;
import org.goriachev.homework.fragments.tables.PersonListFragment;
import org.goriachev.homework.fragments.tables.SpecialityListFragment;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Type;

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

        if (currentFragment != null && fragmentType == currentFragment.getClass() && isHorizontal)
            return;

        if (AppointmentListFragment.class.equals(fragmentType)) {
            currentFragment = new AppointmentListFragment();
        } else if (DoctorListFragment.class.equals(fragmentType)) {
            currentFragment = new DoctorListFragment();
        } else if (PatientListFragment.class.equals(fragmentType)) {
            currentFragment = new PatientListFragment();
        } else if (PersonListFragment.class.equals(fragmentType)) {
            currentFragment = new PersonListFragment();
        } else if (SpecialityListFragment.class.equals(fragmentType)) {
            currentFragment = new SpecialityListFragment();
        } else if (InfoFragment.class.equals(fragmentType)) {
            currentFragment = new InfoFragment();
        } else if (Query01Fragment.class.equals(fragmentType)) {
            currentFragment = new Query01Fragment();
        } else if (Query02Fragment.class.equals(fragmentType)) {
            currentFragment = new Query02Fragment();
        } else if (Query03Fragment.class.equals(fragmentType)) {
            currentFragment = new Query03Fragment();
        } else if (Query04Fragment.class.equals(fragmentType)) {
            currentFragment = new Query04Fragment();
        } else if (Query05Fragment.class.equals(fragmentType)) {
            currentFragment = new Query05Fragment();
        } else if (Query06Fragment.class.equals(fragmentType)) {
            currentFragment = new Query06Fragment();
        } else if (Query07Fragment.class.equals(fragmentType)) {
            currentFragment = new Query07Fragment();
        } else return;

        // отображение в зависимости от ориентации экрана
        if (isHorizontal) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_body, currentFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, FragmentViewActivity.class);
            intent.putExtra("fragment", (Serializable) currentFragment);
            startActivity(intent);
        }
    }

    // переход на активность Приёмы
    public void showAppointmentList(View view) {
        showFragment(AppointmentListFragment.class);
    }

    // переход на активность Доктора
    public void showDoctorList(View view) {
        showFragment(DoctorListFragment.class);
    }

    // переход на активность Пациенты
    public void showPatientList(View view) {
        showFragment(PatientListFragment.class);
    }

    // переход на активность Персоны
    public void showPersonList(View view) {
        showFragment(PersonListFragment.class);
    }

    // переход на активность Специальности
    public void showSpecialityList(View view) {
        showFragment(SpecialityListFragment.class);
    }

    // переход на активность с информацией о разработчике
    public void showInfo(View view) {
        showFragment(InfoFragment.class);
    }

    // переход на активность запроса 1
    public void showQuery01(View view) {
        showFragment(Query01Fragment.class);
    }

    // переход на активность запроса 2
    public void showQuery02(View view) {
        showFragment(Query02Fragment.class);
    }

    // переход на активность запроса 3
    public void showQuery03(View view) {
        showFragment(Query03Fragment.class);
    }

    // переход на активность запроса 4
    public void showQuery04(View view) {
        showFragment(Query04Fragment.class);
    }

    // переход на активность запроса 5
    public void showQuery05(View view) {
        showFragment(Query05Fragment.class);
    }

    // переход на активность запроса 6
    public void showQuery06(View view) {
        showFragment(Query06Fragment.class);
    }

    // переход на активность запроса 7
    public void showQuery07(View view) {
        showFragment(Query07Fragment.class);
    }

    // завершение активности
    public void finish(View view) {
        finish();
    }

    // обработчик меню фрагмента
    @Override
    public void onMainMenuItemSelected(int option) {
        switch (option) {
            case MainMenuFragment.MainMenuOption.APPOINTMENT_LIST:
                showAppointmentList(null);
                break;
            case MainMenuFragment.MainMenuOption.DOCTOR_LIST:
                showDoctorList(null);
                break;
            case MainMenuFragment.MainMenuOption.PATIENT_LIST:
                showPatientList(null);
                break;
            case MainMenuFragment.MainMenuOption.PERSON_LIST:
                showPersonList(null);
                break;
            case MainMenuFragment.MainMenuOption.SPECIALITY_LIST:
                showSpecialityList(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_01:
                showQuery01(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_02:
                showQuery02(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_03:
                showQuery03(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_04:
                showQuery04(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_05:
                showQuery05(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_06:
                showQuery06(null);
                break;
            case MainMenuFragment.MainMenuOption.QUERY_07:
                showQuery07(null);
                break;
            case MainMenuFragment.MainMenuOption.INFO:
                showInfo(null);
                break;
            case MainMenuFragment.MainMenuOption.EXIT:
                finish(null);
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
                showInfo(null);
                break;
            case R.id.mni_exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putSerializable("currentFragment", (Serializable) currentFragment);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        currentFragment = (Fragment) savedInstanceState.getSerializable("currentFragment");

        if (currentFragment != null
                && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_body, currentFragment)
                    .commit();
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}