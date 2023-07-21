package com.brzas.user.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brzas.user.R;
import com.brzas.user.adapter.UserRecyclerViewAdapter;
import com.brzas.user.adapter.UserRecyclerViewInterface;
import com.brzas.user.models.ApiResponse;
import com.brzas.user.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements UserRecyclerViewInterface {
    private static final String TAG = "MainMenuActivity";
    private static final String BASE_URL = "https://randomuser.me/api/";
    private static final String PARAM_KEY = "results";
    private static String PARAM_VALUE = "2";
    private RecyclerView recyclerView;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;

    private SearchView searchView;

    private final List<User> userList = new ArrayList<>();
    private final int maxRetries = 3;
    private final int initialTimeoutMs = 5000; // 5 segundos
    private final float backoffMultiplier = 1.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        recyclerView = findViewById(R.id.recyclerUser);
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(userRecyclerViewAdapter);

        searchView = findViewById(R.id.edit_query);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    Integer i = Integer.parseInt(query);
                    PARAM_VALUE = query;
                    obterUsuarios();
                } catch (NumberFormatException ex) {
                    Toast.makeText(getApplicationContext(), "Digite um inteiro", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void obterUsuarios() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = BASE_URL + "?" + PARAM_KEY + "=" + PARAM_VALUE;
        JsonObjectRequest request = null;

        JsonObjectRequest finalRequest = request;
        request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Log.d(TAG, response.toString());
                        Type responseType = new TypeToken<ApiResponse>() {
                        }.getType();
                        ApiResponse apiResponse = gson.fromJson(response.toString(), responseType);
                        if (apiResponse != null && apiResponse.getResults() != null) {
                            userList.addAll(apiResponse.getResults());
                            Log.d(TAG, userList.toString());
                            userRecyclerViewAdapter.adicionarListaDeUsuarios(userList);
                        } else {
                            Log.d(TAG, "Resposta vazia ou inválida.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            RetryPolicy retryPolicy = new DefaultRetryPolicy(initialTimeoutMs, maxRetries, backoffMultiplier);
                            finalRequest.setRetryPolicy(retryPolicy);
                            requestQueue.add(finalRequest);
                        } else {
                            // Lida com outros erros de solicitação
                            Log.e(TAG, "Error: " + error.toString());
                        }
                    }
                }
        );

        // Definir a política de retry inicial
        RetryPolicy retryPolicy = new DefaultRetryPolicy(initialTimeoutMs, maxRetries, backoffMultiplier);
        request.setRetryPolicy(retryPolicy);

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        User user = userList.get(position);
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}

