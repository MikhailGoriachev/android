package org.goriachev.homework.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.entities.Person;
import org.goriachev.homework.entities.Speciality;
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
import org.goriachev.homework.infrastructure.JsonHelper;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.jsonConverters.AppointmentJsonConverter;
import org.goriachev.homework.jsonConverters.DoctorJsonConverter;
import org.goriachev.homework.jsonConverters.PatientJsonConverter;
import org.goriachev.homework.jsonConverters.PersonJsonConverter;
import org.goriachev.homework.jsonConverters.SpecialityJsonConverter;
import org.goriachev.homework.repositories.AppointmentsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.MainMenuItemSelected {

    // контейнер для фрагмента
    FragmentContainerView fragmentContainer;

    // текущий фрагмент
    Fragment currentFragment;

    // название JSON файлов для таблиц
    private final String APPOINTMENTS_TABLE_FILE = "appointments.json";
    private final String DOCTORS_TABLE_FILE = "doctors.json";
    private final String PATIENTS_TABLE_FILE = "patients.json";
    private final String PEOPLE_TABLE_FILE = "people.json";
    private final String SPECIALITY_TABLE_FILE = "specialities.json";

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
    public void showAppointmentList() {
        showFragment(AppointmentListFragment.class);
    }

    // переход на активность Доктора
    public void showDoctorList() {
        showFragment(DoctorListFragment.class);
    }

    // переход на активность Пациенты
    public void showPatientList() {
        showFragment(PatientListFragment.class);
    }

    // переход на активность Персоны
    public void showPersonList() {
        showFragment(PersonListFragment.class);
    }

    // переход на активность Специальности
    public void showSpecialityList() {
        showFragment(SpecialityListFragment.class);
    }

    // переход на активность с информацией о разработчике
    public void showInfo() {
        showFragment(InfoFragment.class);
    }

    // переход на активность запроса 1
    public void showQuery01() {
        showFragment(Query01Fragment.class);
    }

    // переход на активность запроса 2
    public void showQuery02() {
        showFragment(Query02Fragment.class);
    }

    // переход на активность запроса 3
    public void showQuery03() {
        showFragment(Query03Fragment.class);
    }

    // переход на активность запроса 4
    public void showQuery04() {
        showFragment(Query04Fragment.class);
    }

    // переход на активность запроса 5
    public void showQuery05() {
        showFragment(Query05Fragment.class);
    }

    // переход на активность запроса 6
    public void showQuery06() {
        showFragment(Query06Fragment.class);
    }

    // переход на активность запроса 7
    public void showQuery07() {
        showFragment(Query07Fragment.class);
    }

    // сохранить данные
    public void saveData() {

        var task = new Task<Void, Void, Boolean>(
                null,
                (v) -> {
                    var data = new AppointmentsRepository(this).getAll();

                    boolean result;

                    result = JsonHelper.exportToJson(APPOINTMENTS_TABLE_FILE, this, data,
                            Appointment.class, new AppointmentJsonConverter(this));

                    result = JsonHelper.exportToJson(DOCTORS_TABLE_FILE, this, data,
                            Doctor.class, new DoctorJsonConverter(this)) && result;

                    result = JsonHelper.exportToJson(PATIENTS_TABLE_FILE, this, data,
                            Patient.class, new PatientJsonConverter(this)) && result;

                    result = JsonHelper.exportToJson(PEOPLE_TABLE_FILE, this, data,
                            Person.class, new PersonJsonConverter(this)) && result;

                    result = JsonHelper.exportToJson(PEOPLE_TABLE_FILE, this, data,
                            Speciality.class, new SpecialityJsonConverter(this)) && result;

                    return result;
                },
                (v) -> {
                    String str = v ? "Данные сохранены" : "Не удалось сохранить данные";
                    Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                }
        );

        task.execute();
    }

    // загрузить данные
    public void loadData() {

        var task = new Task<Void, Void, Boolean>(
                null,
                (v) -> {
                    List<Appointment> list = JsonHelper.importListFromJson(APPOINTMENTS_TABLE_FILE, this,
                            Appointment.class, Appointment[].class, new AppointmentJsonConverter(this));

                    var isLoad = list != null;

                    if (isLoad) {
                        var repository = new AppointmentsRepository(this);

                        repository.clear();
                        repository.store(list);
                    }

                    return isLoad;
                },
                (v) -> {

                    showAppointmentList();

                    String str = v ? "Данные восстановлены" : "Не удалось восстановить данные";
                    Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                }
        );

        task.execute();
    }

    // обработчик меню фрагмента
    @Override
    public void onMainMenuItemSelected(int option) {
        switch (option) {
            case MainMenuFragment.MainMenuOption.APPOINTMENT_LIST:
                showAppointmentList();
                break;
            case MainMenuFragment.MainMenuOption.DOCTOR_LIST:
                showDoctorList();
                break;
            case MainMenuFragment.MainMenuOption.PATIENT_LIST:
                showPatientList();
                break;
            case MainMenuFragment.MainMenuOption.PERSON_LIST:
                showPersonList();
                break;
            case MainMenuFragment.MainMenuOption.SPECIALITY_LIST:
                showSpecialityList();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_01:
                showQuery01();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_02:
                showQuery02();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_03:
                showQuery03();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_04:
                showQuery04();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_05:
                showQuery05();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_06:
                showQuery06();
                break;
            case MainMenuFragment.MainMenuOption.QUERY_07:
                showQuery07();
                break;
            case MainMenuFragment.MainMenuOption.SAVE_DATA:
                saveData();
                break;
            case MainMenuFragment.MainMenuOption.LOAD_DATA:
                loadData();
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