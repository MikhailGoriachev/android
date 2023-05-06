package org.goriachev.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.PageAdapter;
import org.goriachev.homework.infrastructure.Action;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;


// Фрагмент для отображения страниц
public class ViewPagerFragment extends Fragment implements Serializable {

    // фрагменты и заголовки
    List<AbstractMap.SimpleEntry<String, Fragment>> fragments;

    TabLayout tblHeaders;

    ViewPager2 vpgTabs;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var arguments = getArguments();

        // получение заголовков и лямбда выражений для получения фрагментов
        var items = (List<AbstractMap.SimpleEntry<String, Action<Fragment>>>) arguments.getSerializable("fragments");

        // создание фрагментов
        fragments = items.stream()
                .map(item -> new AbstractMap.SimpleEntry<>(
                        item.getKey(), item.getValue().run())
                )
                .collect(Collectors.toList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        // настройка контейнера для вкладок
        vpgTabs = view.findViewById(R.id.vpg_tabs);

        var pageAdapter = new PageAdapter(
                getActivity(),
                fragments.stream().map(AbstractMap.SimpleEntry::getValue).collect(Collectors.toList())
        );

        vpgTabs.setAdapter(pageAdapter);

        // настройка заголовков
        tblHeaders = view.findViewById(R.id.tbl_headers);

        var tabMediator = new TabLayoutMediator(
                tblHeaders,
                vpgTabs,
                (tab, position) -> {
                    tab.setCustomView(R.layout.view_item_tab_header);
                    TextView txvTabHeader = tab.getCustomView().findViewById(R.id.txv_tab_header);

                    txvTabHeader.setText(fragments.get(position).getKey());
                }
        );

        tabMediator.attach();

        return view;
    }
}