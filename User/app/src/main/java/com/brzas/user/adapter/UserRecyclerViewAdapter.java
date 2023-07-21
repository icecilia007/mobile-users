package com.brzas.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brzas.user.R;
import com.brzas.user.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final UserRecyclerViewInterface userRecyclerViewInterface;
    private final Context context;
    private final List<User> userList = new ArrayList<>();

    public UserRecyclerViewAdapter(Context context, UserRecyclerViewInterface userRecyclerViewInterface) {
        this.context = context;
        this.userRecyclerViewInterface = userRecyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_content, parent, false);
        return new MyViewHolder(view, userRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Método para adicionar um único usuário ao RecyclerView
    public void adicionarUsuario(User user) {
        userList.add(user);
        notifyItemInserted(userList.size() - 1);
    }

    // Método para adicionar uma lista de usuários ao RecyclerView
    public void adicionarListaDeUsuarios(List<User> listaDeUsuarios) {
        int startPosition = userList.size();
        userList.addAll(listaDeUsuarios);
        notifyItemRangeInserted(startPosition, listaDeUsuarios.size());
    }
}
