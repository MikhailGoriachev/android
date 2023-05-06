
package org.goriachev.homework.fragments.webData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.webData.PostAdapter;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.interfaces.Api;
import org.goriachev.homework.models.webData.Post;
import org.goriachev.homework.services.PostListService;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;


public class PostListFragment extends Fragment implements Serializable {

    public static final String BROADCAST_ACTION = "org.goriachev.homework.PostListFragment";

    private RecyclerView rcvItems;

    // api для работы с веб-сервисом
    private Api api;

    // объект для работы с сервисом
    private BroadcastReceiver receiver;


    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onReceiveHandler(context, intent);
            }
        };

        var view = inflater.inflate(R.layout.fragment_list, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);

        // фильтр
        var filter = new IntentFilter(BROADCAST_ACTION);

        var activity = getActivity();

        // регистрация слушателя
        activity.registerReceiver(receiver, filter);

        var intent = new Intent(getContext(), PostListService.class);

        activity.startService(intent);

        return view;
    }

    // вывод ошибки
    Consumer<Throwable> showErrorMessage = throwable ->
            Toast.makeText(
                    getContext(),
                    "Не удалось получить данные",
                    Toast.LENGTH_SHORT
            ).show();

    // обработчик получения данных из сервиса
    public void onReceiveHandler(Context context, Intent intent) {
        var serviceName = intent.getStringExtra("service_name");
        var resultCode = intent.getIntExtra("result_code", 1);

        if (serviceName.equals(PostListService.SERVICE_NAME)) {

            if (resultCode == ResultCode.RESULT_OK)
                rcvItems.setAdapter(new PostAdapter(
                        getContext(),
                        (List<Post>) intent.getSerializableExtra("items")
                ));
            else
                showErrorMessage.accept(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
        }
    }
}