package org.goriachev.homework.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import org.goriachev.homework.fragments.webData.PostListFragment;
import org.goriachev.homework.infrastructure.CustomCallback;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.interfaces.Api;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Сервис для получения списка альбомов
public class PostListService extends Service {

    public static final String SERVICE_NAME = "org.goriachev.homework.services.PostListService";

    private Api api;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(this::run).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void run() {
        var retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        api.postsGetAll().enqueue(
                new CustomCallback<>(
                        listResponse -> {
                            var intent = new Intent(PostListFragment.BROADCAST_ACTION);
                            intent.putExtra("service_name", SERVICE_NAME);
                            intent.putExtra("result_code", ResultCode.RESULT_OK);
                            intent.putExtra("items", (Serializable) listResponse.body());
                            sendBroadcast(intent);
                            stopSelf();
                        },
                        throwable -> {
                            var intent = new Intent(PostListFragment.BROADCAST_ACTION);
                            intent.putExtra("service_name", SERVICE_NAME);
                            intent.putExtra("result_code", ResultCode.RESULT_ERROR);
                            sendBroadcast(intent);
                            stopSelf();
                        }
                )
        );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
