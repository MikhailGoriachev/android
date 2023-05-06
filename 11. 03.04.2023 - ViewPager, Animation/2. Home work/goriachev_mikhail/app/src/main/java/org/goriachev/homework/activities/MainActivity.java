package org.goriachev.homework.activities;

import android.app.DatePickerDialog;
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
import org.goriachev.homework.dialogues.webData.AlbumIdDialog;
import org.goriachev.homework.dialogues.queries.Query01Dialog;
import org.goriachev.homework.dialogues.queries.Query02Dialog;
import org.goriachev.homework.dialogues.queries.Query04Dialog;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.entities.Person;
import org.goriachev.homework.entities.Speciality;
import org.goriachev.homework.fragments.InfoFragment;
import org.goriachev.homework.fragments.MainMenuFragment;
import org.goriachev.homework.fragments.ViewPagerFragment;
import org.goriachev.homework.fragments.webData.AlbumFragment;
import org.goriachev.homework.fragments.webData.AlbumListFragment;
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
import org.goriachev.homework.fragments.webData.CommentListFragment;
import org.goriachev.homework.fragments.webData.PostListFragment;
import org.goriachev.homework.infrastructure.Action;
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
import java.util.AbstractMap;
import java.util.Calendar;
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

        if (InfoFragment.class.equals(fragmentType)) {
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
        } else if (AlbumListFragment.class.equals(fragmentType)) {
            currentFragment = new AlbumListFragment();
        } else if (AlbumFragment.class.equals(fragmentType)) {
            currentFragment = new AlbumFragment();
        } else if (ViewPagerFragment.class.equals(fragmentType)) {
            currentFragment = new ViewPagerFragment();
        } else return;

        var params = getIntent().getBundleExtra("params");

        // отображение в зависимости от ориентации экрана
        if (isHorizontal) {
            currentFragment.setArguments(params);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_body, currentFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, FragmentViewActivity.class);
            intent.putExtra("fragment", (Serializable) currentFragment);
            intent.putExtra("params", params);
            startActivity(intent);
        }
    }

    // переход на активность Приёмы
    public void showAppointmentList() {
        showFragment(AppointmentListFragment.class);
    }

    // показать все таблицы
    public void showTables() {
        var params = new Bundle();

        List<AbstractMap.SimpleEntry<String, Action<Fragment>>> fragments = List.of(
                new AbstractMap.SimpleEntry<>("Приёмы", AppointmentListFragment::new),
                new AbstractMap.SimpleEntry<>("Доктора", DoctorListFragment::new),
                new AbstractMap.SimpleEntry<>("Пациенты", PatientListFragment::new),
                new AbstractMap.SimpleEntry<>("Персоны", PersonListFragment::new),
                new AbstractMap.SimpleEntry<>("Специальности", SpecialityListFragment::new)
        );

        params.putSerializable("fragments", (Serializable) fragments);

        getIntent().putExtra("params", params);

        showFragment(ViewPagerFragment.class);
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
        var dialog = new Query01Dialog(
                // обработчик submit
                (s) -> {
                    Bundle params = new Bundle();
                    params.putString("surname", s);

                    getIntent().putExtra("params", params);

                    showFragment(Query01Fragment.class);
                });

        dialog.show(getSupportFragmentManager(), "query01Dialog");
    }

    // переход на активность запроса 2
    public void showQuery02() {
        var dialog = new Query02Dialog(
                // обработчик submit
                (p) -> {
                    Bundle params = new Bundle();
                    params.putDouble("percent", p);

                    getIntent().putExtra("params", params);

                    showFragment(Query02Fragment.class);
                });

        dialog.show(getSupportFragmentManager(), "query02Dialog");
    }

    // переход на активность запроса 3
    public void showQuery03() {

        var calenar = Calendar.getInstance();

        var minCalendar = Calendar.getInstance();
        var maxCalendar = Calendar.getInstance();

        // начальная дата
        new DatePickerDialog(
                MainActivity.this,
                (datePicker, year, month, dayOfMonth) -> {
                    minCalendar.set(Calendar.YEAR, year);
                    minCalendar.set(Calendar.MONTH, month);
                    minCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // конечная дата
                    new DatePickerDialog(
                            MainActivity.this,
                            (picker, y, m, d) -> {
                                maxCalendar.set(Calendar.YEAR, y);
                                maxCalendar.set(Calendar.MONTH, m);
                                maxCalendar.set(Calendar.DAY_OF_MONTH, d);

                                Bundle params = new Bundle();
                                params.putSerializable("minDate", minCalendar);
                                params.putSerializable("maxDate", maxCalendar);

                                getIntent().putExtra("params", params);

                                showFragment(Query03Fragment.class);
                            },
                            minCalendar.get(Calendar.YEAR),
                            minCalendar.get(Calendar.MONTH),
                            minCalendar.get(Calendar.DAY_OF_MONTH))
                            .show();
                },
                calenar.get(Calendar.YEAR),
                calenar.get(Calendar.MONTH),
                calenar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // переход на активность запроса 4
    public void showQuery04() {
        var dialog = new Query04Dialog(
                // обработчик submit
                (s) -> {
                    Bundle params = new Bundle();
                    params.putString("specialityName", s);

                    getIntent().putExtra("params", params);

                    showFragment(Query04Fragment.class);
                });

        dialog.show(getSupportFragmentManager(), "query04Dialog");
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

                    result = JsonHelper.exportToJson(SPECIALITY_TABLE_FILE, this, data,
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

    // переход на фрагмент списка альбомов
//    public void showAlbumList() {
//        showFragment(AlbumListFragment.class);
//    }

    // переход на фрагмент поиска альбома по id
    public void showAlbumById() {
        var dialog = new AlbumIdDialog(
                // обработчик submit
                (id) -> {
                    Bundle params = new Bundle();
                    params.putInt("album_id", id);

                    getIntent().putExtra("params", params);

                    showFragment(AlbumFragment.class);
                });

        dialog.show(getSupportFragmentManager(), "album_by_id");
    }

    // показать данные веб-сервиса
    public void showWebData() {
        var params = new Bundle();

        List<AbstractMap.SimpleEntry<String, Action<Fragment>>> fragments = List.of(
                new AbstractMap.SimpleEntry<>("Альбомы", AlbumListFragment::new),
                new AbstractMap.SimpleEntry<>("Комментарии", CommentListFragment::new),
                new AbstractMap.SimpleEntry<>("Посты", PostListFragment::new)
        );

        params.putSerializable("fragments", (Serializable) fragments);

        getIntent().putExtra("params", params);

        showFragment(ViewPagerFragment.class);
    }


    // обработчик меню фрагмента
    @Override
    public void onMainMenuItemSelected(int option) {
        switch (option) {
            case MainMenuFragment.MainMenuOption.TABLES:
                showTables();
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
            case MainMenuFragment.MainMenuOption.WEB_DATA:
                showWebData();
                break;
            case MainMenuFragment.MainMenuOption.ALBUM_BY_ID:
                showAlbumById();
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
                    System.out.println("PARAMS NOT NULL");
                    getIntent().putExtra("params", params);
                    currentFragment.setArguments(params);
                }

                if (isHorizontal)
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_body, currentFragment)
                            .commit();

            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}