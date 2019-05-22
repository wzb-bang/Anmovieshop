package com.gdou.movieshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
                String id = et_userid.getText().toString();
                String psd = et_psd.getText().toString();
                String anginpsd = et_againpsd.getText().toString();
                String username = et_username.getText().toString();
                String phone = et_phone.getText().toString();

                //如果密码与重复密码不一致,提醒用户
                if (!psd.equals(anginpsd)) {
                    Toast.makeText(getApplicationContext(), "密码不一致,请重新输入", Toast.LENGTH_LONG).show();
                }
                else {
                    //-------------Volley链接------------
                    //创建请求队列
                    RequestQueue mQueue = Volley.newRequestQueue(registerActivity.this);
                    //创建请求
                    //设置参数
                    Map<String,String> params=new HashMap<>();
                    params.put("user_id",id);
                    params.put("user_name",username);
                    params.put("user_phone",phone);
                    params.put("user_password",psd);
                    JSONObject paramsJsonObject=new JSONObject(params);

                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                            "http://203.195.219.146:8080/movieshop/User/registerServlet.action",paramsJsonObject,
                            new Response.Listener<JSONObject >() {      //volley监听器
                                @Override
                                public void onResponse(JSONObject  response) {  //onResponse获取到服务器响应的值
                                    try {
                                        //打印消息
                                        Toast.makeText(getApplicationContext(),(String)response.get("msg"), Toast.LENGTH_LONG).show();
                                        //注册成功,跳转回登录界面
                                        if(response.get("status").equals(200)){
                                            Intent intent = new Intent(registerActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {   //接受错误信息
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.e("TAG", volleyError.getMessage(), volleyError);
                                }
                            }){
                        @Override
                        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                            JSONObject  jsonObject;
                            try {
                                jsonObject = new JSONObject(new String(response.data,"UTF-8"));
                                return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                            } catch (UnsupportedEncodingException e) {
                                return Response.error(new ParseError(e));
                            } catch (Exception je) {
                                Log.e("TAG",je.toString());
                                return Response.error(new ParseError(je));
                            }
                        }
                    };
                    //将创建的请求添加到请求队列中
                    mQueue.add(jsonObjectRequest);
                }
                break;
        }
    }

}
