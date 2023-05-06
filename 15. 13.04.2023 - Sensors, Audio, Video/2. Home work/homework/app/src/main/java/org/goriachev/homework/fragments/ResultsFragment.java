package org.goriachev.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.ResultAdapter;
import org.goriachev.homework.entities.Result;
import org.goriachev.homework.repositories.ResultsRepository;

import java.io.Serializable;
import java.util.List;


public class ResultsFragment extends Fragment implements Serializable {

    RecyclerView rcvItems;

    // записи
    List<Result> items;

    // адаптер для записей
    ResultAdapter adapter;

    // репозиторий результатов
    ResultsRepository resultsRepository;

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_list, container, false);

        resultsRepository = new ResultsRepository(getContext());
        items = resultsRepository.getAll();

        rcvItems = view.findViewById(R.id.rcv_items);
        adapter = new ResultAdapter(getContext(), items);
        rcvItems.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        items.clear();
        items.addAll(resultsRepository.getAll());
        adapter.notifyDataSetChanged();
    }
}