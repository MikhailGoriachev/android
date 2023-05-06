package org.goriachev.homework.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.goriachev.homework.R;

import java.io.Serializable;


public class MainMenuFragment extends Fragment implements Serializable {

    public interface MainMenuItemSelected {
        void onMainMenuItemSelected(int pointName);
    }

    // перечисление пунктов меню
    public interface MainMenuOption {
        int TABLES = 0;
        int QUERY_01 = 5;
        int QUERY_02 = 6;
        int QUERY_03 = 7;
        int QUERY_04 = 8;
        int QUERY_05 = 9;
        int QUERY_06 = 10;
        int QUERY_07 = 11;
        int SAVE_DATA = 12;
        int LOAD_DATA = 13;
        int WEB_DATA = 14;
        int ALBUM_BY_ID = 15;
        int INFO = 16;
        int EXIT = 17;
    }

    // активность обработчик
    private MainMenuItemSelected activityHandler;

    // представление фрагмента
    private View view;


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        initializeMenuOption(R.id.btn_query01_option, MainMenuOption.QUERY_01);
        initializeMenuOption(R.id.btn_query02_option, MainMenuOption.QUERY_02);
        initializeMenuOption(R.id.btn_query03_option, MainMenuOption.QUERY_03);
        initializeMenuOption(R.id.btn_query04_option, MainMenuOption.QUERY_04);
        initializeMenuOption(R.id.btn_query05_option, MainMenuOption.QUERY_05);
        initializeMenuOption(R.id.btn_query06_option, MainMenuOption.QUERY_06);
        initializeMenuOption(R.id.btn_query07_option, MainMenuOption.QUERY_07);

        initializeMenuOption(R.id.btn_tables, MainMenuOption.TABLES);

        initializeMenuOption(R.id.btn_save_appointment_list, MainMenuOption.SAVE_DATA);
        initializeMenuOption(R.id.btn_load_appointment_list, MainMenuOption.LOAD_DATA);

        initializeMenuOption(R.id.btn_web_data, MainMenuOption.WEB_DATA);
        initializeMenuOption(R.id.btn_album_by_id, MainMenuOption.ALBUM_BY_ID);

        initializeMenuOption(R.id.btn_info_option, MainMenuOption.INFO);
        initializeMenuOption(R.id.btn_exit_option, MainMenuOption.EXIT);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activityHandler = (MainMenuItemSelected) context;
    }

    // инициализация пункта меню
    private void initializeMenuOption(int id, int tag) {
        var option = view.findViewById(id);
        option.setTag(tag);
        option.setOnClickListener(this::onSelectMenuItem);
    }

    // обработчик
    public void onSelectMenuItem(View view) {
        activityHandler.onMainMenuItemSelected((int) view.getTag());
    }
}