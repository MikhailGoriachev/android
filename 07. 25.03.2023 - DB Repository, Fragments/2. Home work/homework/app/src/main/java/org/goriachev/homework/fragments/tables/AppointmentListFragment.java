
package org.goriachev.homework.fragments.tables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.AppointmentAdapter;
import org.goriachev.homework.repositories.AppointmentRepository;

import java.io.Serializable;


public class AppointmentListFragment extends Fragment implements Serializable {

    private RecyclerView rcvAppointments;

    // репозиторий
    AppointmentRepository repository;


    public AppointmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_appointment_list, container, false);

        var context = getContext();

        repository = new AppointmentRepository(context);

        rcvAppointments = view.findViewById(R.id.rcv_appointments);

        rcvAppointments.setAdapter(new AppointmentAdapter(context, repository.getAll()));

        return view;
    }
}