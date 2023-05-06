package org.goriachev.homework.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import org.goriachev.homework.R;

import java.io.Serializable;


public class SettingsFragment extends PreferenceFragmentCompat implements Serializable {

    final static String INFO_FILE = "info.html";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }
}