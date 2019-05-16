package com.procentplus.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.procentplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmEmailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.confirm_back_btn)
    ImageView confirm_back_btn;
    @BindView(R.id.btn_send_again)
    TextView btn_send_again;
    @BindView(R.id.next_confirm)
    Button btn_next_confirm;
    @BindView(R.id.auth_btn_confirm)
    TextView auth_btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);
        ButterKnife.bind(this);

        // underlining text views
        btn_send_again.setPaintFlags(btn_send_again.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        auth_btn_confirm.setPaintFlags(auth_btn_confirm.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        confirm_back_btn.setOnClickListener(this);
        btn_send_again.setOnClickListener(this);
        btn_next_confirm.setOnClickListener(this);
        auth_btn_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_back_btn:
                onBackPressed();
                break;
            case R.id.btn_send_again:

                break;
            case R.id.next_confirm:
                Intent intent = new Intent(ConfirmEmailActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.auth_btn_confirm:
                Intent intent1 = new Intent(ConfirmEmailActivity.this, AuthActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
