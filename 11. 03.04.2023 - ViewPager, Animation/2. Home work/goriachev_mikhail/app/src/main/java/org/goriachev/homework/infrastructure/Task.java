package org.goriachev.homework.infrastructure;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;

// Класс Асинхронная таска
public class Task<T, K, V> extends AsyncTask<T, K, V> {

    // callback для вызова в onPreExecute
    private final Consumer<Void> onPreExecuteCallback;

    // callback для вызова в doInBackground
    @NonNull
    private final Function<T[], V> doInBackgroundCallback;

    // callback для вызова в onPostExecute
    private final Consumer<V> onPostExecuteCallback;


    public Task(Consumer<Void> onPreExecuteCallback,
                @NonNull Function<T[], V> doInBackgroundCallback,
                Consumer<V> onPostExecuteCallback) {
        this.onPreExecuteCallback = onPreExecuteCallback;
        this.doInBackgroundCallback = doInBackgroundCallback;
        this.onPostExecuteCallback = onPostExecuteCallback;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (onPreExecuteCallback != null)
            onPreExecuteCallback.accept(null);
    }

    @Override
    protected V doInBackground(T... ts) {
        return doInBackgroundCallback.apply(ts);
    }

    @Override
    protected void onPostExecute(V v) {
        super.onPostExecute(v);

        if (onPostExecuteCallback != null)
            onPostExecuteCallback.accept(v);
    }
}
