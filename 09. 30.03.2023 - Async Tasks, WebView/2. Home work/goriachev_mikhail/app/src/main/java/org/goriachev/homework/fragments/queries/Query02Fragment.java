
package org.goriachev.homework.fragments.queries;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.DoctorAdapter;
import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.DoctorsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;


public class Query02Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    DoctorsRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query02Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new DoctorsRepository(context);

        var view = inflater.inflate(R.layout.fragment_query02, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        // запуск обработки запроса в отдельном потоке исполнения
        run(context);

        return view;
    }

    // обработка запроса
    private void run(Context context) {

        var task = new Task<Void, Void, Pair<List<Doctor>, Double>>(
                null,
                (v) -> {
                    var percent = Utils.getDouble(2, 6);

                    return new Pair<>(repository.getAllByPercentOver(percent), percent);
                },
                (v) -> {
                    rcvItems.setAdapter(new DoctorAdapter(context, v.first));
                    txvTitle.setText(String.format(Locale.UK, "Запрос 2. Процент больше %.2f%%", v.second));
                }
        );

        task.execute();
    }
}