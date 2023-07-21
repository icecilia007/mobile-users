package com.brzas.user.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.brzas.user.R;
import com.brzas.user.models.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private User user;

    private ImageView pictureImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView nationalityTextView;
    private TextView userNameTextView;
    private ImageView genderIconImageView;
    private TextView dateTextView;
    private TextView idadeTextView;
    private TextView streetTextView;
    private TextView numberTextView;
    private TextView cityTextView;
    private TextView stateTextView;
    private TextView countryTextView;
    private TextView timeZoneTextView;
    private TextView emailTextView;
    private TextView cellTextView;
    private TextView phoneTextView;
    private TextView dateRegisteredTextView;
    private TextView yearRegisteredTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
        }
        layoutView();
        setUserProfile(user);
    }

    private void layoutView() {
        pictureImageView = findViewById(R.id.picture);
        firstNameTextView = findViewById(R.id.firstName);
        lastNameTextView = findViewById(R.id.lastName);
        nationalityTextView = findViewById(R.id.nationality);
        userNameTextView = findViewById(R.id.userName);
        genderIconImageView = findViewById(R.id.gender_icon);
        dateTextView = findViewById(R.id.date);
        idadeTextView = findViewById(R.id.idade);
        streetTextView = findViewById(R.id.street);
        numberTextView = findViewById(R.id.number);
        cityTextView = findViewById(R.id.city);
        stateTextView = findViewById(R.id.state);
        countryTextView = findViewById(R.id.country);
        timeZoneTextView = findViewById(R.id.time_zone);
        emailTextView = findViewById(R.id.email);
        cellTextView = findViewById(R.id.cell);
        phoneTextView = findViewById(R.id.phone);
        dateRegisteredTextView = findViewById(R.id.date_registered);
        yearRegisteredTextView = findViewById(R.id.year_registered);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUserProfile(User user) {

        String imageUrl=user.getPicture().getLarge();

        Glide.with(UserProfileActivity.this)
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(pictureImageView);

        if (user.getGender().equals("male")) {
            genderIconImageView.setImageResource(R.drawable.gender_male);
        } else if(user.getGender().equals("female")){
            genderIconImageView.setImageResource(R.drawable.gender_female);
        }else{
            genderIconImageView.setImageResource(R.drawable.gender_non_binary);
        }

        // Preencha as informações do nome
        firstNameTextView.setText(user.getName().getFirst());
        lastNameTextView.setText(user.getName().getLast());

        // Preencha as informações de nacionalidade
        nationalityTextView.setText(user.getNat());

        // Preencha as informações do nome de usuário
        userNameTextView.setText(user.getLogin().getUsername());

        // Preencha as informações de data de nascimento e idade
        dateTextView.setText(convertDateFormat(user.getDob().getDate()));
        idadeTextView.setText(String.valueOf(user.getDob().getAge()));

        // Preencha as informações de endereço
        streetTextView.setText(user.getLocation().getStreet().getName());
        numberTextView.setText(String.valueOf(user.getLocation().getStreet().getNumber()));
        cityTextView.setText(user.getLocation().getCity());
        stateTextView.setText(user.getLocation().getState());
        countryTextView.setText(user.getLocation().getCountry());
        timeZoneTextView.setText(user.getLocation().getTimezone().getOffset());

        // Preencha as informações de contato
        emailTextView.setText(user.getEmail());
        cellTextView.setText(user.getCell());
        phoneTextView.setText(user.getPhone());

        // Preencha as informações de registro
        dateRegisteredTextView.setText(convertDateFormat(user.getRegistered().getDate()));
        yearRegisteredTextView.setText(String.valueOf(user.getRegistered().getAge()));
    }
    private String convertDateFormat(String inputDateStr) {
        String outputDateStr = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Convertendo a data de entrada para um objeto Date
            Date inputDate = inputFormat.parse(inputDateStr);
            // Convertendo o objeto Date para a data formatada no formato de saída desejado
            outputDateStr = outputFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }
}
