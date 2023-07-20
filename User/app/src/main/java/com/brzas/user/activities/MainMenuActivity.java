package com.brzas.user.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brzas.user.R;
import com.brzas.user.adapter.UserRecyclerViewAdapter;
import com.brzas.user.models.ApiResponse;
import com.brzas.user.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = "MainMenuActivity";
    private static String BASE_URL ="https://randomuser.me/api/";
    private static final String PARAM_KEY = "results";
    private static final String PARAM_VALUE = "2";
    private RecyclerView recyclerView;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;

    private List<User> userList=  new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        recyclerView = findViewById(R.id.recyclerUser);
        userRecyclerViewAdapter =  new UserRecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(userRecyclerViewAdapter);
        obterUsuarios();

    }
    private boolean success=false;
    private boolean obterUsuarios() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = BASE_URL + "?" + PARAM_KEY + "=" + PARAM_VALUE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Log.d(TAG,response.toString());
                        Type responseType = new TypeToken<ApiResponse>(){}.getType();
                        ApiResponse apiResponse = gson.fromJson(response.toString(), responseType);
                        userList.addAll(apiResponse.getResults());
                        Log.d(TAG,userList.toString());
                        if(!userList.isEmpty()){
                            userRecyclerViewAdapter.adicionarListaDeUsuarios(userList);
                        }
                        success=true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Lida com erros de solicitação
                        Log.e(TAG, "Error: " + error.toString());
                        success=false;
                    }
                }
        );
        requestQueue.add(request);
        return success;
    }
}

