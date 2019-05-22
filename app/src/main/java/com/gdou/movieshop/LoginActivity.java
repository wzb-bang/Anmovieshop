package com.gdou.movieshop;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

     EditText et_userid;
     EditText et_psd;
     ImageView iv_clear;
     TextView register;
     Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        ImageView iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        et_userid = (EditText) findViewById(R.id.et_userid);
        et_psd = (EditText) findViewById(R.id.et_psd);

        register=findViewById(R.id.register);
        register.setOnClickListener(this);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);

        et_userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(et_userid.getText().toString().trim())) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
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
            case R.id.iv_clear:
                et_userid.setText("");
                et_psd.setText("");
                break;
            case R.id.register:
                Intent intent=new Intent(LoginActivity.this,registerActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_login:
                //-------------Volley链接------------
                //创建请求队列
                RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
                //创建请求
                //设置参数
                Map<String,String> params=new HashMap<>();
                params.put("user_id",et_userid.getText().toString());
                params.put("user_password",et_psd.getText().toString());
                JSONObject paramsJsonObject=new JSONObject(params);

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                        "http://203.195.219.146:8080/movieshop/User/loginServlet.action",paramsJsonObject,
                        new Response.Listener<JSONObject >() {      //volley监听器
                            @Override
                            public void onResponse(JSONObject  response) {  //onResponse获取到服务器响应的值
                                try {
                                    //打印信息
                                    Toast.makeText(getApplicationContext(),response.getString("msg"), Toast.LENGTH_LONG).show();
                                    //登录成功
                                    if(response.get("status").equals(200)){
                                        //创建sharedPreferences对象,并保存用户登录状态、用户名、用户id
                                        SharedPreferences sharedPreferences =LoginActivity.this.getSharedPreferences("Login",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user_name",(String)response.get("user_name"));
                                        editor.putString("user_id",(String)response.get("user_id"));
                                        editor.putInt("status",(int)response.get("status"));
                                        editor.apply();

                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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
                break;
        }
    }

}
