
package org.goriachev.homework.fragments.queries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.Query07Adapter;
import org.goriachev.homework.repositories.AppointmentRepository;

import java.io.Serializable;


public class Query07Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    AppointmentRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query07Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new AppointmentRepository(context);

        var view = inflater.inflate(R.layout.fragment_query07, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        rcvItems.setAdapter(new Query07Adapter(context, repository.getAllGroupBySpeciality()));

        txvTitle.setText("Запрос 7. Статистика по специальности");

        return view;
    }
}