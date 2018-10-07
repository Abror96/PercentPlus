package com.example.kringle.percentplus.fragments.searchFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.activities.MainActivity;
import com.example.kringle.percentplus.activities.ObjectsActivity;
import com.example.kringle.percentplus.retrofit.RetrofitClient;
import com.example.kringle.percentplus.retrofit.interfaces.ISearch;
import com.example.kringle.percentplus.retrofit.models.PointOfSale;
import com.example.kringle.percentplus.retrofit.models.SearchRequest;
import com.example.kringle.percentplus.retrofit.models.SearchResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ByCodeFragment extends Fragment {

    private ISearch iSearch;
    private Retrofit retrofit;

    private EditText et_object_code;
    private String object_code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_by_code, null);

        // init api
        retrofit = RetrofitClient.getInstance();

        et_object_code = view.findViewById(R.id.et_object_code);
        Button search_by_code = view.findViewById(R.id.search_by_code);

        search_by_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object_code = et_object_code.getText().toString().trim().toLowerCase();
                if (!et_object_code.getText().toString().equals("")) {
                    getData();
                } else
                    MainActivity.prefConfig.displayToast("Введите код для поиска");
            }
        });


        return view;
    }

    private void getData() {
        iSearch = retrofit.create(ISearch.class);

        List<SearchRequest.SearchParam> list = new ArrayList<>();
        list.add(new SearchRequest.SearchParam("id", object_code));

        Call<SearchResponse> searchResponseCall = iSearch.getSearchResult(
                MainActivity.prefConfig.readToken(),
                new SearchRequest(list)
        );

        searchResponseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                int statusCode = response.code();
                Log.d("LOGGER Search", "statusCode: " + statusCode);

                List<PointOfSale> searchList = new ArrayList<>();
                searchList.addAll(response.body().getPointOfSales());

                if (statusCode == 200) {
                    Intent objectsIntent = new Intent(getContext(), ObjectsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("search_result",(Serializable) searchList);
                    objectsIntent.putExtra("searchList", bundle);
                    objectsIntent.putExtra("search_by", "коду: "+object_code);
                    startActivity(objectsIntent);
                } else {
                    Log.d("LOGGER Search", "onResponse: произошла ошибка при получении данных");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                MainActivity.prefConfig.displayToast("Ошибка при воспроизведении поиска");
            }
        });
    }

}
