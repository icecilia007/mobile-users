package com.brzas.user.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brzas.user.R;
import com.brzas.user.models.Location;
import com.brzas.user.models.Name;
import com.brzas.user.models.User;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView titleName;
    TextView firstName;
    TextView lastName;
    TextView coutry;
    TextView state;
    TextView email;
    TextView phone;

    public MyViewHolder(@NonNull View itemView, UserRecyclerViewInterface userRecyclerViewInterface) {
        super(itemView);
        titleName = itemView.findViewById(R.id.titleName);
        firstName = itemView.findViewById(R.id.firstName);
        lastName = itemView.findViewById(R.id.lastName);
        coutry = itemView.findViewById(R.id.country);
        state = itemView.findViewById(R.id.state);
        email = itemView.findViewById(R.id.email);
        phone = itemView.findViewById(R.id.number);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        userRecyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }

    void bind(User user) {
        Name name = user.getName();
        Location location = user.getLocation();
        titleName.setText(name.getTitle());
        firstName.setText(name.getFirst());
        lastName.setText(name.getLast());
        coutry.setText(location.getCountry());
        state.setText(location.getCity());
        email.setText(user.getEmail());
        phone.setText(user.getCell());
    }
}
