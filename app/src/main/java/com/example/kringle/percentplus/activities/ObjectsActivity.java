package com.example.kringle.percentplus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.adapter.ObjectsAdapter;
import com.example.kringle.percentplus.retrofit.RetrofitClient;
import com.example.kringle.percentplus.retrofit.interfaces.IObjects;
import com.example.kringle.percentplus.retrofit.models.Objects;
import com.example.kringle.percentplus.retrofit.models.ObjectsRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ObjectsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_object_category_name)
    TextView tv_category_name;
    @BindView(R.id.object_back_btn)
    ImageView object_back_btn;

    private IObjects iObjects;
    private Retrofit retrofit;

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Objects.Object> objectsList = new ArrayList<>();

    private Integer category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);
        ButterKnife.bind(this);

        // init api

        retrofit = RetrofitClient.getInstance();

        // init recycler
        categoryRecyclerView = findViewById(R.id.objectsRecyclerView);

        if (getIntent().getExtras() != null) {
            String category_name_str = getIntent().getExtras().getString("category_name");
            category_id = getIntent().getExtras().getInt("category_id");
            tv_category_name.setText(category_name_str);
        }


        object_back_btn.setOnClickListener(this);

        getObjects();

        setUpRecyclerView();
    }

    private void getObjects() {
        iObjects = retrofit.create(IObjects.class);

        Call<Objects> objectsCall = iObjects.getObjects(
                MainActivity.prefConfig.readToken(),
                new ObjectsRequest(category_id)
        );

        objectsCall.enqueue(new Callback<Objects>() {
            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                int statusCode = response.code();
                Log.d("LOGGER Auth", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    if (objectsList.size() > 0) objectsList.clear();

                    objectsList.addAll(response.body().getActivityType().getPartners());
                    setUpRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Objects> call, Throwable t) {

            }
        });
    }

    private void setUpRecyclerView() {
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(this.getResources().getDrawable(R.drawable.divider));

        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ObjectsAdapter(objectsList);
        categoryRecyclerView.addItemDecoration(itemDecorator);
        categoryRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.object_back_btn:
                onBackPressed();
        }
    }
}
