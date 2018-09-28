package com.example.kringle.percentplus.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kringle.percentplus.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_email_auth)
    EditText et_email_auth;
    @BindView(R.id.et_password_auth)
    EditText et_password_auth;
    @BindView(R.id.forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.sign_in_auth)
    Button btn_sign_in_auth;
    @BindView(R.id.register_btn_auth)
    TextView register_btn_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        // underlining the textviews
        tv_forgot_password.setPaintFlags(tv_forgot_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register_btn_auth.setPaintFlags(register_btn_auth.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        tv_forgot_password.setOnClickListener(this);
        btn_sign_in_auth.setOnClickListener(this);
        register_btn_auth.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.forgot_password:
                intent = new Intent(AuthActivity.this, RestorePasswordActivity.class);
                break;
            case R.id.sign_in_auth:
                intent = new Intent(AuthActivity.this, MainActivity.class);
                intent.putExtra("tab_id", 0);
                break;
            case R.id.register_btn_auth:
                intent = new Intent(AuthActivity.this, RegisterActivity.class);
                break;

        }
        startActivity(intent);
    }
}
