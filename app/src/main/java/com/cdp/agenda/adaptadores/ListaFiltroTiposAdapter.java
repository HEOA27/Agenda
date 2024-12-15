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

public class ListaFiltroTiposAdapter extends RecyclerView.Adapter<ListaFiltroTiposAdapter.ContactoViewHolder>{
    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;
    String tipo;

    int contador=0,cdor=0;
    //int cdor=0;

    public ListaFiltroTiposAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }
    @NonNull
    @Override
    public ListaFiltroTiposAdapter.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_filtro, null, false);

        return new ListaFiltroTiposAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFiltroTiposAdapter.ContactoViewHolder holder, int position) {
        holder.textViewIzq.setText(listaContactos.get(position).getNombre());
        holder.textViewDer.setText(listaContactos.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public int filtroBautizado(final int position) throws ParseException {
        contador=0;
        if (position != 1) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                tipo= contactos.getTipo();
                if (tipo.equals("Bautizado")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }


    public int filtroNoBautizado(final int position) throws ParseException {
        contador=0;
        if (position != 2) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                tipo= contactos.getTipo();
                if (tipo.equals("No Bautizado")||tipo.equals("Visitante")||tipo.equals("Asistencia frecuente")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }

    public int filtroVisitante(final int position) throws ParseException {
        contador=0;
        if (position != 6) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                tipo= contactos.getTipo();
                if (tipo.equals("Visitante")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroAsistenciaF(final int position) throws ParseException {
        contador=0;
        if (position != 10) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                tipo= contactos.getTipo();
                if (tipo.equals("Asistencia frecuente")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder{
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
