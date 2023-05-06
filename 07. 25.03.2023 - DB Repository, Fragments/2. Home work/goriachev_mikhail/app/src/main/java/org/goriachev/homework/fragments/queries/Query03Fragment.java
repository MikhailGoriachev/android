
package org.goriachev.homework.fragments.queries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.AppointmentAdapter;
import org.goriachev.homework.repositories.AppointmentRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;


public class Query03Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    AppointmentRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query03Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new AppointmentRepository(context);

        var view = inflater.inflate(R.layout.fragment_query03, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        var minDate = Utils.getItem(repository.getAll()).getAppointmentDate();
        var maxDate = new Date(minDate.getTime());
        maxDate.setDate(minDate.getDate() + Utils.getInt(5, 10));

        rcvItems.setAdapter(new AppointmentAdapter(context, repository.getAllByAppointmentDateRange(minDate, maxDate)));

        txvTitle.setText(String.format(Locale.UK, "Запрос 3. Период приёма от %s до %s",
                Utils.dateHtmlToFormat(minDate),
                Utils.dateHtmlToFormat(maxDate)));

        return view;
    }
}