
package org.goriachev.homework.fragments.queries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.Query06Adapter;
import org.goriachev.homework.repositories.AppointmentRepository;

import java.io.Serializable;


public class Query06Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    AppointmentRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query06Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new AppointmentRepository(context);

        var view = inflater.inflate(R.layout.fragment_query06, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        rcvItems.setAdapter(new Query06Adapter(context, repository.getAllGroupByAppointmentDate()));

        txvTitle.setText("Запрос 6. Статистика по дате приёма");

        return view;
    }
}