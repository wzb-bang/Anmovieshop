package com.gdou.movieshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_userid;
    private EditText et_psd;
    private EditText et_againpsd;
    private EditText et_username;
    private EditText et_phone;
    private Button bt_register;
    private Button bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        ImageView iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        et_userid = (EditText) findViewById(R.id.et_userid);
        et_psd = (EditText) findViewById(R.id.et_psd);
        et_againpsd = findViewById(R.id.et_againpsd);
        et_username = findViewById(R.id.et_username);
        et_phone = findViewById(R.id.et_phone);

        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_back.setOnClickListener(this);

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_phone.getText().toString().trim().length() == 11) {
                    bt_register.setEnabled(true);
                } else {
                    bt_register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.bt_back:
                Intent intent = new Intent(registerActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_register:

                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader buffer = null;
                HttpURLConnection connPOST = null;

                String id = et_userid.getText().toString();
                String psd = et_psd.getText().toString();
                String anginpsd = et_againpsd.getText().toString();
                String username = et_username.getText().toString();
                String phone = et_phone.getText().toString();

                //如果密码与重复密码不一致,提醒用户
                if (!psd.equals(anginpsd)) {
                    Toast.makeText(getApplicationContext(), "密码不一致,请重新输入", Toast.LENGTH_LONG).show();
                } else {
                    String str = "http://192.168.1.104:8080/example6_war_exploded/registerServlet";
                    String param = "user_id=" + id + "&" + "user_password=" + psd + "&" + "user_name=" + username
                            + "&" + "user_phone=" + phone; //设置参数
                    try {
                        URL url = new URL(str);
                        connPOST = (HttpURLConnection) url.openConnection();
                        connPOST.setConnectTimeout(5000);
                        connPOST.setRequestMethod("POST");
                        connPOST.setUseCaches(false);

                        // 发送POST请求必须设置如下两行
                        connPOST.setDoOutput(true);
                        connPOST.setDoInput(true);

                        //----------发送数据--------
                        PrintWriter printWriter = new PrintWriter(connPOST.getOutputStream());
                        printWriter.write(param); // 发送请求参数
                        printWriter.flush();

                        //----------接收数据--------
                        buffer = new BufferedReader(new InputStreamReader(connPOST.getInputStream()));
                        for (String s = buffer.readLine(); s != null; s = buffer.readLine()) {
                            stringBuilder.append(s);
                        }
                        Toast.makeText(getApplicationContext(), stringBuilder, Toast.LENGTH_LONG).show();
                        buffer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "response err....." + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                finish();
                break;
        }
    }

}
