package org.goriachev.homework.infrastructure;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomCallback<T> implements Callback<T> {

    private final Consumer<Response<T>> onResponseHandler;

    private final Consumer<Throwable> onFailureHandler;


    public CustomCallback(@NonNull Consumer<Response<T>> onResponseHandler, @Nullable Consumer<Throwable> onFailureHandler) {
        this.onResponseHandler = onResponseHandler;
        this.onFailureHandler = onFailureHandler;
    }


    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        onResponseHandler.accept(response);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (onFailureHandler != null)
            onFailureHandler.accept(t);
    }
}
