
package org.goriachev.homework.fragments.queries;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.polyclinic.Query07Adapter;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.models.Query07;
import org.goriachev.homework.repositories.AppointmentsRepository;

import java.io.Serializable;
import java.util.List;


public class Query07Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    AppointmentsRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query07Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new AppointmentsRepository(context);

        var view = inflater.inflate(R.layout.fragment_query07, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        // запуск обработки запроса в отдельном потоке исполнения
        run(context);

        return view;
    }

    // обработка запроса
    private void run(Context context) {

        var task = new Task<Void, Void, List<Query07>>(
                null,
                (v) -> repository.getAllGroupBySpeciality(),
                (v) -> {
                    rcvItems.setAdapter(new Query07Adapter(context, v));
                    txvTitle.setText("Запрос 7. Статистика по специальности");
                }
        );

        task.execute();
    }
}