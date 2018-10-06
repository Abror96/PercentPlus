package com.example.kringle.percentplus.fragments;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.activities.MainActivity;
import com.example.kringle.percentplus.adapter.CategoryAdapter;
import com.example.kringle.percentplus.model.Category;
import com.example.kringle.percentplus.retrofit.RetrofitClient;
import com.example.kringle.percentplus.retrofit.interfaces.ICategories;
import com.example.kringle.percentplus.retrofit.models.CategoriesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private ICategories iCategories;
    private Retrofit retrofit;

    private List<CategoriesResponse.ActivityType> categoriesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);

        // init api
        retrofit = RetrofitClient.getInstance();

        // init recycler
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);

        getCategories();

        TextView tv_category_city = view.findViewById(R.id.tv_category_city);
        tv_category_city.setText("Курск");
        tv_category_city.setPaintFlags(tv_category_city.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }

    private void getCategories() {
        iCategories = retrofit.create(ICategories.class);

        Call<CategoriesResponse> categoriesResponseCall = iCategories.getCategories(
                MainActivity.prefConfig.readToken());

        categoriesResponseCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                int statusCode = response.code();
                Log.d("LOGGER Auth", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    if (categoriesList.size() > 0) categoriesList.clear();

                    categoriesList.addAll(response.body().getActivityTypes());
                    Log.d("LOGGER Categories", "onResponse: " + categoriesList.toString());
                    setUpRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });

    }

    private void setUpRecyclerView() {
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getDrawable(getContext(), R.drawable.divider));

        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CategoryAdapter(categoriesList);
        categoryRecyclerView.addItemDecoration(itemDecorator);
        categoryRecyclerView.setAdapter(mAdapter);
    }
}
