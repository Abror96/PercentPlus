package com.procentplus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.procentplus.R;
import com.procentplus.activities.AuthActivity;
import com.procentplus.activities.MainActivity;
import com.procentplus.adapter.SearchPagerAdapter;
import com.procentplus.retrofit.RetrofitClient;
import com.procentplus.retrofit.interfaces.ILogout;
import com.procentplus.retrofit.models.CategoriesResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    @BindView(R.id.searchViewPager)
    ViewPager searchViewPager;
    @BindView(R.id.searchTabLayout)
    TabLayout searchTabLayout;
    SearchPagerAdapter searchPagerAdapter;
    ImageView logout_btn;

    private Retrofit retrofit;
    private ILogout iLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);

        // init api
        retrofit = RetrofitClient.getInstance();

        searchViewPager.bringToFront();
        searchPagerAdapter = new SearchPagerAdapter(getChildFragmentManager());
        searchTabLayout.setupWithViewPager(searchViewPager);
        searchViewPager.setAdapter(searchPagerAdapter);

        logout_btn = view.findViewById(R.id.logout);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        return view;
    }

    private void logout() {
        iLogout = retrofit.create(ILogout.class);

        Call<CategoriesResponse> call = iLogout.logOut(MainActivity.prefConfig.readToken());

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                int statusCode = response.code();
                Log.d("LOGGER Logout", "statusCode: " + statusCode);
                if (statusCode == 200 || statusCode == 204) {
                    MainActivity.prefConfig.writeLoginStatus(false);
                    Intent intent = new Intent(getContext(), AuthActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                MainActivity.prefConfig.displayToast("Произошла ошибка при попытке выхода из профиля");
            }
        });
    }
}
