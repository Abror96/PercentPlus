package com.procentplus.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.procentplus.ProgressDialog.DialogConfig;
import com.procentplus.R;
import com.procentplus.retrofit.RetrofitClient;
import com.procentplus.retrofit.interfaces.IAuthorization;
import com.procentplus.retrofit.interfaces.IPartnerAuthorization;
import com.procentplus.retrofit.models.AuthPartnerResponse;
import com.procentplus.retrofit.models.AuthResponse;
import com.procentplus.retrofit.models.MobileUser;
import com.procentplus.retrofit.models.SignPartnerRequest;
import com.procentplus.retrofit.models.SignRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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
    @BindView(R.id.switch1)
    SwitchCompat partner;

    private DialogConfig progressDialog;

    private IAuthorization iAuthorization;
    private IPartnerAuthorization iPartnerAuthorization;
    private Retrofit retrofit;

    private String email;
    private String password;
    private boolean isPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);


        // init api
        retrofit = RetrofitClient.getInstance();

        // init dialog
        progressDialog = new DialogConfig(this, "Идет загрузка");

        // underlining the textview
        tv_forgot_password.setPaintFlags(tv_forgot_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register_btn_auth.setPaintFlags(register_btn_auth.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        tv_forgot_password.setOnClickListener(this);
        btn_sign_in_auth.setOnClickListener(this);
        register_btn_auth.setOnClickListener(this);
        partner.setOnCheckedChangeListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.forgot_password:
                intent = new Intent(AuthActivity.this, RestorePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_in_auth:
                email = et_email_auth.getText().toString().trim();
                password = et_password_auth.getText().toString().trim();
                Log.d("AuthActivity", "onClick: email: " + email + ", password: " + password + ", isPartner: " + isPartner);
                if (!email.isEmpty() && !password.isEmpty()) {
                    if (isPartner) {
                        getAuthPartnerResponse();
                    } else {
                        getAuthResponse();
                    }

                    // show dialog
                    progressDialog.showDialog();

                } else MainActivity.prefConfig.displayToast("Заполните все поля!");

                break;
            case R.id.register_btn_auth:
                intent = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void getAuthResponse() {
        Log.d("AuthActivity", "getAuthResponse: ");
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

                    // hide dialog
                    progressDialog.dismissDialog();

                    String token = response.headers().get("Authorization");
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeEmail(email);
                    MainActivity.prefConfig.writePassword(password);
                    MainActivity.prefConfig.writeToken(token);
                    MainActivity.prefConfig.writeId(response.body().getId());
                    MainActivity.prefConfig.writeIsPartner(isPartner);
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    intent.putExtra("tab_id", 0);
                    startActivity(intent);
                    finish();
                } else {
                    // hide dialog
                    progressDialog.dismissDialog();
                    MainActivity.prefConfig.displayToast("Email или пароль были введены неверно!");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // hide dialog
                progressDialog.dismissDialog();
                MainActivity.prefConfig.displayToast("Произошла ошибка при попытке авторизации, попытайтесь снова.");
            }
        });
    }

    private void getAuthPartnerResponse() {
        Log.d("AuthActivity", "getAuthPartnerResponse: ");
        iPartnerAuthorization = retrofit.create(IPartnerAuthorization.class);

        Call<AuthPartnerResponse> authPartnerResponseCall = iPartnerAuthorization.getAccountData(
                new SignPartnerRequest(
                        new MobileUser(email, password))
        );

        authPartnerResponseCall.enqueue(new Callback<AuthPartnerResponse>() {
            @Override
            public void onResponse(Call<AuthPartnerResponse> call, Response<AuthPartnerResponse> response) {
                int statusCode = response.code();
                Log.d("LOGGER AuthPartner", "statusCode: " + statusCode);
                if (statusCode == 200) {

                    // hide dialog
                    progressDialog.dismissDialog();

                    String token = response.headers().get("Authorization");
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeEmail(email);
                    MainActivity.prefConfig.writePassword(password);
                    MainActivity.prefConfig.writeToken(token);
                    MainActivity.prefConfig.writeId(response.body().getUser().getId());
                    MainActivity.prefConfig.writeIsPartner(isPartner);
                    MainActivity.prefConfig.writePartnerId(response.body().getPartner().getId());


                    Intent intent = new Intent(AuthActivity.this, PartnerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // hide dialog
                    progressDialog.dismissDialog();
                    MainActivity.prefConfig.displayToast("Email или пароль были введены неверно!");
                }
            }

            @Override
            public void onFailure(Call<AuthPartnerResponse> call, Throwable t) {
                // hide dialog
                progressDialog.dismissDialog();
                MainActivity.prefConfig.displayToast("Произошла ошибка при попытке авторизации, попытайтесь снова.");
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (isPartner) {
            isPartner = false;
        } else {
            isPartner = true;
        }
        Log.d("AuthActivity", "onCheckedChanged: switch checked, isPartner = " + isPartner);


    }
}
