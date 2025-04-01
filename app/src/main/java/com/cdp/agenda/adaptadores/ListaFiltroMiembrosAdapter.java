package com.cdp.agenda.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdp.agenda.R;
import com.cdp.agenda.VerActivity;
import com.cdp.agenda.entidades.Contactos;

import java.text.ParseException;
import java.util.ArrayList;

public class ListaFiltroMiembrosAdapter extends RecyclerView.Adapter<ListaFiltroMiembrosAdapter.ContactoViewHolder>{
    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;

    boolean bandera;
    String miembroPC;

    int contador=0,cdor=0;

    public ListaFiltroMiembrosAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }
    @NonNull
    @Override
    public ListaFiltroMiembrosAdapter.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_filtro, null, false);
        return new ListaFiltroMiembrosAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFiltroMiembrosAdapter.ContactoViewHolder holder, int position) {
        holder.textViewIzq.setText(listaContactos.get(position).getNombre());
        holder.textViewDer.setText(listaContactos.get(position).getMiembro_plena_comunion());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public int filtroMiembroPlenaComunion(final int position) throws ParseException {
        contador=0;
        if (position != 13) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                miembroPC= contactos.getMiembro_plena_comunion();
                if (miembroPC.equals("Es miembro")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroNoMiembroPlenaComunion(final int position) throws ParseException {
        contador=0;
        if (position != 14) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                miembroPC= contactos.getMiembro_plena_comunion();
                if (miembroPC.equals("No es miembro")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIzq, textViewDer;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIzq = itemView.findViewById(R.id.textViewIzq);
            textViewDer = itemView.findViewById(R.id.textViewDer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent =new Intent(context, VerActivity.class);
                    intent.putExtra("ID",listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
