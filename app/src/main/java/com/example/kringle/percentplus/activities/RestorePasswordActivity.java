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

public class RestorePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.restore_back_btn)
    ImageView restore_back_btn;
    @BindView(R.id.send_email_restore)
    Button btn_send_email_restore;
    @BindView(R.id.register_btn_restore)
    TextView register_btn_restore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        ButterKnife.bind(this);

        // underlining text views
        register_btn_restore.setPaintFlags(register_btn_restore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        restore_back_btn.setOnClickListener(this);
        btn_send_email_restore.setOnClickListener(this);
        register_btn_restore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restore_back_btn:
                onBackPressed();
                break;
            case R.id.send_email_restore:

                break;
            case R.id.register_btn_restore:
                Intent intent = new Intent(RestorePasswordActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
