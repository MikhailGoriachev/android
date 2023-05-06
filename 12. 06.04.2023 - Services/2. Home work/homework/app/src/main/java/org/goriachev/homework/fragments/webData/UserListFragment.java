
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
import org.goriachev.homework.activities.UserActivity;
import org.goriachev.homework.adapters.webData.UserAdapter;
import org.goriachev.homework.infrastructure.CustomCallback;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.interfaces.Api;
import org.goriachev.homework.models.webData.User;
import org.goriachev.homework.services.UserListService;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserListFragment extends Fragment implements Serializable {

    public static final String BROADCAST_ACTION = "org.goriachev.homework.UserListFragment";

    private RecyclerView rcvItems;

    // api для работы с веб-сервисом
    private Api api;

    // объект для работы с сервисом
    private BroadcastReceiver receiver;


    public UserListFragment() {
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

        var retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        // фильтр
        var filter = new IntentFilter(BROADCAST_ACTION);

        var activity = getActivity();

        // регистрация слушателя
        activity.registerReceiver(receiver, filter);

        var intent = new Intent(getContext(), UserListService.class);

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

    // вывод элемента
    Consumer<User> showUserItem = (u) ->
            api.usersGetById(u.getId()).enqueue(
                    new CustomCallback<User>(
                            (user) -> {
                                var intent = new Intent(getContext(), UserActivity.class)
                                        .putExtra("item", user.body());
                                startActivity(intent);
                            },
                            showErrorMessage
                    )
            );

    // обработчик получения данных из сервиса
    public void onReceiveHandler(Context context, Intent intent) {
        var serviceName = intent.getStringExtra("service_name");
        var resultCode = intent.getIntExtra("result_code", 1);

        if (serviceName.equals(UserListService.SERVICE_NAME)) {

            if (resultCode == ResultCode.RESULT_OK)
                rcvItems.setAdapter(new UserAdapter(
                        getContext(),
                        (List<User>) intent.getSerializableExtra("items"),
                        showUserItem
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