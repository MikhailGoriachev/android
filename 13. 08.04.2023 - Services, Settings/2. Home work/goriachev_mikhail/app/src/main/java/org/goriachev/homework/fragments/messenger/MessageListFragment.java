
package org.goriachev.homework.fragments.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.MessageAdapter;
import org.goriachev.homework.dialogues.MessageDialog;
import org.goriachev.homework.models.Message;
import org.goriachev.homework.services.MessengerService;

import java.io.Serializable;
import java.util.List;


public class MessageListFragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // индикатор соединения с сервисом
    private boolean bound = false;

    // соединение с сервисом
    private ServiceConnection connection;

    // intent сервиса
    private Intent serviceIntent;

    // сервис
    private MessengerService service;

    // активность
    private Activity activity;

    // записи
    private List<Message> items;

    MessageAdapter adapter;


    public MessageListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var view = inflater.inflate(R.layout.fragment_message_list, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);

        activity = getActivity();

        view.findViewById(R.id.btn_add).setOnClickListener(v -> addMessage());

        // intent сервиса
        serviceIntent = new Intent(activity, MessengerService.class);

        // обработчик соединения
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                service = ((MessengerService.ServiceBinder) iBinder).getService();

                items = service.getMessageList();

                adapter = new MessageAdapter(
                        getContext(),
                        items,
                        m -> editMessage(m));

                rcvItems.post(() -> rcvItems.setAdapter(adapter));
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                bound = false;
            }
        };

        return view;
    }

    // обновление списка
    private void updateItems() {
        items.clear();
        service.getMessageList();
        items.addAll(service.getMessageList());

        adapter.notifyDataSetChanged();
    }

    // добавление сообщения
    private void addMessage() {

        if (bound) {

            var dialog = new MessageDialog(
                    m -> {
                        service.sendMessage(m);
                        updateItems();
                    }
            );

            dialog.show(getChildFragmentManager(), "add_form_message");
        }
    }

    // изменение сообщения
    private void editMessage(Message message) {

        if (bound) {
            var dialog = new MessageDialog(
                    m -> {
                        service.editMessage(message.getId(), m);
                        updateItems();
                    },
                    m -> {
                        service.deleteMessage(m.getId());
                        updateItems();
                    },
                    message
            );

            dialog.show(getChildFragmentManager(), "edit_form_message");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!bound) return;

        activity.unbindService(connection);
        bound = false;
    }
}