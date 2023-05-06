
package org.goriachev.homework.fragments.tables;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.polyclinic.PatientAdapter;
import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.PatientsRepository;

import java.io.Serializable;
import java.util.List;


public class PatientListFragment extends Fragment implements Serializable {

    private RecyclerView rcvPatients;

    // репозиторий
    PatientsRepository repository;


    public PatientListFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_patient_list, container, false);

        var context = getContext();

        repository = new PatientsRepository(context);

        rcvPatients = view.findViewById(R.id.rcv_patients);

        // запуск обработки запроса в отдельном потоке исполнения
        run(context);

        return view;
    }

    // обработка запроса
    private void run(Context context) {

        var task = new Task<Void, Void, List<Patient>>(
                null,
                (v) -> repository.getAll(),
                (v) -> rcvPatients.setAdapter(new PatientAdapter(context, v))
        );

        task.execute();
    }
}