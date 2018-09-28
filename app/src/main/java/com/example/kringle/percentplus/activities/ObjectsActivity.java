package com.example.kringle.percentplus.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.adapter.ObjectsAdapter;
import com.example.kringle.percentplus.model.Object;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ObjectsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_object_category_name)
    TextView tv_category_name;
    @BindView(R.id.object_back_btn)
    ImageView object_back_btn;

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> objectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);
        ButterKnife.bind(this);

        String category_name_str = getIntent().getStringExtra("category_name");
        tv_category_name.setText(category_name_str);

        object_back_btn.setOnClickListener(this);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        fillData();
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(this.getResources().getDrawable(R.drawable.divider));
        categoryRecyclerView = findViewById(R.id.objectsRecyclerView);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ObjectsAdapter(objectList);
        categoryRecyclerView.addItemDecoration(itemDecorator);
        categoryRecyclerView.setAdapter(mAdapter);
    }

    private void fillData() {
        objectList.add(new Object("Шинка"));
        objectList.add(new Object("Шиномонтаж 24"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.object_back_btn:
                onBackPressed();
        }
    }
}
