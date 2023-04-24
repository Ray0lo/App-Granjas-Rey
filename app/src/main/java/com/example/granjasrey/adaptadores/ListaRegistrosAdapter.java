package com.example.granjasrey.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.granjasrey.R;
import com.example.granjasrey.VerActivity;
import com.example.granjasrey.entidades.Registros;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaRegistrosAdapter extends RecyclerView.Adapter<ListaRegistrosAdapter.RegistroViewHolder> {

    ArrayList<Registros> listaRegistros;
    ArrayList<Registros> listaOriginal;

    public ListaRegistrosAdapter(ArrayList<Registros> listaRegistros) {
        this.listaRegistros = listaRegistros;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaRegistros);
    }

    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_registro, null, false);
        return new RegistroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
        holder.viewNombre.setText(listaRegistros.get(position).getNombre());
        holder.viewFecha.setText(listaRegistros.get(position).getFecha());
        holder.viewHora.setText(listaRegistros.get(position).getHora());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaRegistros.clear();
            listaRegistros.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Registros> collecion = listaRegistros.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaRegistros.clear();
                listaRegistros.addAll(collecion);
            } else {
                for (Registros c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaRegistros.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaRegistros.size();
    }

    public class RegistroViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewFecha, viewHora;

        public RegistroViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewFecha = itemView.findViewById(R.id.viewFecha);
            viewHora = itemView.findViewById(R.id.viewHora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaRegistros.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
