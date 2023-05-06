
package org.goriachev.homework.fragments.periodicals;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.EditionAdapter;
import org.goriachev.homework.dialogues.EditionDialog;
import org.goriachev.homework.entities.Edition;
import org.goriachev.homework.repositories.EditionsRepository;

import java.io.Serializable;
import java.util.List;


public class EditionListFragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // активность
    private Activity activity;

    // записи
    private List<Edition> items;

    EditionAdapter adapter;


    public EditionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var view = inflater.inflate(R.layout.fragment_edition_list, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);

        activity = getActivity();

        items = EditionsRepository.getAll(activity.getContentResolver());

        adapter = new EditionAdapter(
                getContext(),
                items,
                this::editEdition);

        rcvItems.setAdapter(adapter);

        view.findViewById(R.id.btn_add).setOnClickListener(v -> addEdition());

        return view;
    }

    // обновление списка
    private void updateItems() {
        items.clear();
        items.addAll(EditionsRepository.getAll(activity.getContentResolver()));

        adapter.notifyDataSetChanged();
    }


    // добавление записи
    private void addEdition() {
        var dialog = new EditionDialog(
                e -> {
                    EditionsRepository.create(e, activity.getContentResolver());
                    updateItems();
                }
        );

        dialog.show(getChildFragmentManager(), "add_form");
    }

    // изменение записи
    private void editEdition(Edition edition) {

        // поиск записи по id в базе данных
        var item = EditionsRepository.getById(edition.getId(), activity.getContentResolver());

        var dialog = new EditionDialog(
                e -> {
                    EditionsRepository.update(e, activity.getContentResolver());
                    updateItems();
                },
                e -> {
                    EditionsRepository.deleteById(e.getId(), activity.getContentResolver());
                    updateItems();
                },
                item
        );

        dialog.show(getChildFragmentManager(), "edit_form");
    }
}