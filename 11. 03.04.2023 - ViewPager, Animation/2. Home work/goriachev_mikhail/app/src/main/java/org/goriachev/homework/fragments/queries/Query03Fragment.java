
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
import org.goriachev.homework.adapters.polyclinic.AppointmentAdapter;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.AppointmentsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Query03Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    AppointmentsRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query03Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new AppointmentsRepository(context);

        var view = inflater.inflate(R.layout.fragment_query03, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        // запуск обработки запроса в отдельном потоке исполнения
        run(context);

        return view;
    }

    // обработка запроса
    private void run(Context context) {

        var args = getArguments();
        Date minDate = ((Calendar) args.getSerializable("minDate")).getTime();
        Date maxDate = ((Calendar) args.getSerializable("maxDate")).getTime();

        var task = new Task<Void, Void, Pair<List<Appointment>, Pair<Date, Date>>>(
                null,
                (v) -> {
                    return new Pair<>(
                            repository.getAllByAppointmentDateRange(minDate, maxDate),
                            new Pair<>(minDate, maxDate)
                            );
                },
                (v) -> {
                    rcvItems.setAdapter(new AppointmentAdapter(context, v.first));
                    txvTitle.setText(
                            String.format(Locale.UK, "Запрос 3. Период приёма от %s до %s",
                                    Utils.dateHtmlToFormat(v.second.first),
                                    Utils.dateHtmlToFormat(v.second.second))
                    );
                }
        );

        task.execute();
    }
}