package com.procentplus.fragments.searchFragments;

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

import com.procentplus.R;
import com.procentplus.activities.MainActivity;
import com.procentplus.activities.ObjectsActivity;
import com.procentplus.retrofit.RetrofitClient;
import com.procentplus.retrofit.interfaces.ISearch;
import com.procentplus.retrofit.models.PointOfSale;
import com.procentplus.retrofit.models.SearchRequest;
import com.procentplus.retrofit.models.SearchResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ByAddressFragment extends Fragment {

    private ISearch iSearch;
    private Retrofit retrofit;

    private EditText et_city_search;
    private EditText et_address_search;
    private String city_search;
    private String address_search;
    private String search_by;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_by_address, null);

        // init api
        retrofit = RetrofitClient.getInstance();

        Button search_by_address = view.findViewById(R.id.search_by_address);
        et_city_search = view.findViewById(R.id.et_city_search);
        et_address_search = view.findViewById(R.id.et_address_search);

        search_by_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city_search = et_city_search.getText().toString().trim().toLowerCase();
                address_search = et_address_search.getText().toString().trim().toLowerCase();

                if (city_search.isEmpty() && address_search.isEmpty()) {
                    MainActivity.prefConfig.displayToast("Заполните одно поле");
                } else if (!city_search.isEmpty() && !address_search.isEmpty()) {
                    MainActivity.prefConfig.displayToast("Заполните только одно поле");
                } else if (city_search.isEmpty()) {
                    getData(address_search, "address");
                    search_by = "адресу: ";
                    // show dialog
                    MainActivity.dialogConfig.showDialog();
                } else {
                    getData(city_search, "city");
                    search_by = "городу: ";
                    // show dialog
                    MainActivity.dialogConfig.showDialog();
                }
            }
        });

        return view;
    }

    private void getData(String value, String param) {
        iSearch = retrofit.create(ISearch.class);

        List<SearchRequest.SearchParam> list = new ArrayList<>();
        list.add(new SearchRequest.SearchParam(param, value));

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

                    // hide dialog
                    MainActivity.dialogConfig.dismissDialog();

                    Intent objectsIntent = new Intent(getContext(), ObjectsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("search_result",(Serializable) searchList);
                    objectsIntent.putExtra("searchList", bundle);
                    objectsIntent.putExtra("search_by", search_by+address_search+city_search);
                    startActivity(objectsIntent);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // hide dialog
                MainActivity.dialogConfig.dismissDialog();
                MainActivity.prefConfig.displayToast("Ошибка при воспроизведении поиска");
            }
        });
    }
}
