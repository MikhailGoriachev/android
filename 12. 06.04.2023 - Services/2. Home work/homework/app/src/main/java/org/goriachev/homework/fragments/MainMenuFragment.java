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

        initializeMenuOption(R.id.btn_web_data, MainMenuOption.WEB_DATA);

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