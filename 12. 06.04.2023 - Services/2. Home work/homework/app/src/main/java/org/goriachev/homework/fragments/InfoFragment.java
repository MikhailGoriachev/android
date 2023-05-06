package org.goriachev.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import org.goriachev.homework.R;
import org.goriachev.homework.utils.Utils;

import java.io.IOException;
import java.io.Serializable;


public class InfoFragment extends Fragment implements Serializable {

    final static String INFO_FILE = "info.html";

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_info, container, false);

        WebView wbvInfo = view.findViewById(R.id.wbv_info);

        try (var file = getContext().getAssets().open(INFO_FILE)) {
            var content = Utils.getTextFromFile(file);

            wbvInfo.loadData(content, "text/html", "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return view;
    }
}