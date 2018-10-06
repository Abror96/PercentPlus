package com.example.kringle.percentplus.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.retrofit.interfaces.IAuthorization;
import com.example.kringle.percentplus.retrofit.RetrofitClient;
import com.example.kringle.percentplus.retrofit.models.SignRequest;
import com.example.kringle.percentplus.retrofit.models.AuthResponse;
import com.example.kringle.percentplus.retrofit.models.MobileUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    private IAuthorization iAuthorization;
    private Retrofit retrofit;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        // init api
        retrofit = RetrofitClient.getInstance();

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
                startActivity(intent);
                break;
            case R.id.sign_in_auth:
                email = et_email_auth.getText().toString().trim();
                password = et_password_auth.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    getAuthResponse();
                } else MainActivity.prefConfig.displayToast("Заполните все поля!");

                break;
            case R.id.register_btn_auth:
                intent = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void getAuthResponse() {
        iAuthorization = retrofit.create(IAuthorization.class);

        Call<AuthResponse> authResponseCall = iAuthorization.getAccountData(
                new SignRequest(
                        new MobileUser(email, password))
        );

        authResponseCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                int statusCode = response.code();
                Log.d("LOGGER Auth", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    String token = response.headers().get("Authorization");
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeEmail(email);
                    MainActivity.prefConfig.writePassword(password);
                    MainActivity.prefConfig.writeToken(token);
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    intent.putExtra("tab_id", 0);
                    startActivity(intent);
                    finish();
                } else if (statusCode == 401) {
                    MainActivity.prefConfig.displayToast("Email или пароль были введены неверно!");
                } else {
                    MainActivity.prefConfig.displayToast("Произошла ошибка при попытке авторизации, попытайтесь снова.");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                MainActivity.prefConfig.displayToast("Произошла ошибка при попытке авторизации, попытайтесь снова.");
            }
        });
    }
}
