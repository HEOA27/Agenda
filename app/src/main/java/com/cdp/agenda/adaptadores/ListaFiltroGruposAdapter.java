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

public class ListaFiltroGruposAdapter extends RecyclerView.Adapter<ListaFiltroGruposAdapter.ContactoViewHolder>{
    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;
    int contador=0;
    int cdor=0;
    String grupo, sexo;
    
    public ListaFiltroGruposAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }
    @NonNull
    @Override
    public ListaFiltroGruposAdapter.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_filtro, null, false);
        return new ListaFiltroGruposAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFiltroGruposAdapter.ContactoViewHolder holder, int position) {
        holder.textViewIzq.setText(listaContactos.get(position).getNombre());
        holder.textViewDer.setText(listaContactos.get(position).getGrupo());
    }
    @Override
    public int getItemCount() {
        return listaContactos.size();
    }
    public int filtroJovenes(final int position) throws ParseException {
        contador=0;
        if (position != 3) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                grupo= contactos.getGrupo();
                if (grupo.equals("Jóvenes")) {
                    listaContactos.add(contactos);
                    contador++;
                    //recibirContador(contador);
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroMujeres(final int position) throws ParseException {
        contador=0;
        if (position != 4) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                grupo= contactos.getGrupo();
                if (grupo.equals("Mujeres")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroVarones(final int position) throws ParseException {
        contador=0;
        if (position != 5) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                grupo= contactos.getGrupo();
                if (grupo.equals("Varones")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroNiños(final int position) throws ParseException {
        contador=0;
        if (position != 7) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                grupo= contactos.getGrupo();
                if (grupo.equals("Iglesia Infantil")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroAdolescentes(final int position) throws ParseException {
        contador=0;
        if (position != 8) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                grupo= contactos.getGrupo();
                if (grupo.equals("Adolescentes")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }
    public int filtroAdultos(final int position) throws ParseException {
        contador=0;
        if (position != 9) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                grupo= contactos.getGrupo();//debe ser global
                if (grupo.equals("Mujeres")||grupo.equals("Varones")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }

    public int filtroMujer(final int position) throws ParseException {
        contador=0;
        if (position != 11) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                sexo= contactos.getSexo();
                if (sexo.equals("Femenino")) {
                    listaContactos.add(contactos);
                    contador++;
                }
            }
        }
        notifyDataSetChanged();
        return contador;
    }

    public int filtroHombre(final int position) throws ParseException {
        contador=0;
        if (position != 12) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();

            for (Contactos contactos : listaOriginal) {
                sexo= contactos.getSexo();
                if (sexo.equals("Masculino")) {
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
        public ContactoViewHolder(View itemView) {
            super(itemView);
            textViewIzq = itemView.findViewById(R.id.textViewIzq);
            textViewDer = itemView.findViewById(R.id.textViewDer);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent=new Intent(context, VerActivity.class);
                    intent.putExtra("ID",listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
