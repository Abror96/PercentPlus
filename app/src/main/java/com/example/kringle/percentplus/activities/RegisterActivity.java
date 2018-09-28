package com.example.kringle.percentplus.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kringle.percentplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.next_register)
    Button btn_next_register;
    @BindView(R.id.auth_btn_register)
    TextView auth_btn_register;
    @BindView(R.id.register_back_btn)
    ImageView register_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // underlining text views
        auth_btn_register.setPaintFlags(auth_btn_register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        btn_next_register.setOnClickListener(this);
        auth_btn_register.setOnClickListener(this);
        register_back_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_register:
                Intent intent = new Intent(RegisterActivity.this, ConfirmEmailActivity.class);
                startActivity(intent);
                break;
            case R.id.auth_btn_register:
                finish();
                break;
            case R.id.register_back_btn:
                onBackPressed();
                break;
        }
    }
}
