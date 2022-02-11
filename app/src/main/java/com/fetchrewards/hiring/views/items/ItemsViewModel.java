package com.fetchrewards.hiring.views.items;

import static com.fetchrewards.hiring.utils.Constants.BASE_URL;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fetchrewards.hiring.models.Item;
import com.fetchrewards.hiring.network.FetchRewardsAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsViewModel extends ViewModel {
    MutableLiveData<List<Item>> items;
    FetchRewardsAPI fetchRewardsAPI;

    public ItemsViewModel(){
        items = new MutableLiveData<>();

    }

    public MutableLiveData<List<Item>> fetchItems(){
        Call<List<Item>> call = fetchRewardsAPI.fetchItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if(response.isSuccessful()){

                    assert response.body() != null;
                    Log.i("ItemsVM", response.body().toString());

                    items.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                Log.i("ItemsVM", t.getLocalizedMessage());
            }
        });
        return items;
    }

    public void setUpNetworkRequest() {
        Gson gson = new GsonBuilder().serializeNulls().create();//to be able to see null value fields

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10000, TimeUnit.MILLISECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        fetchRewardsAPI = retrofit.create(FetchRewardsAPI.class);
    }
}
