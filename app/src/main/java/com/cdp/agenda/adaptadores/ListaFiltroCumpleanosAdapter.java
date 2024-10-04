package com.cdp.agenda.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdp.agenda.R;
import com.cdp.agenda.entidades.Contactos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListaFiltroCumpleanosAdapter extends RecyclerView.Adapter<ListaFiltroCumpleanosAdapter.ContactoViewHolder>{
    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;
    int mes,bandera;
    public String[] mColors = {"#3F51B5","#FF9800","#009688","#673AB7"};



    public ListaFiltroCumpleanosAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }
    @NonNull
    @Override
    public ListaFiltroCumpleanosAdapter.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_filtro, null, false);

        return new ListaFiltroCumpleanosAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFiltroCumpleanosAdapter.ContactoViewHolder holder, int position) {
        holder.textViewIzq.setText(listaContactos.get(position).getNombre());
        if(bandera==1){
            holder.textViewDer.setBackgroundColor(Color.parseColor(mColors[position % 4]));
        }
        holder.textViewDer.setText(listaContactos.get(position).getFecha_nacimiento());
    }

    public void filtroCumpleanos(final int position) throws ParseException {
        bandera=0;
        if (position != 0) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }  else {
            listaContactos.clear();
            Date todayDate = new Date();
            SimpleDateFormat fmes = new SimpleDateFormat("MM");
            String fMes = fmes.format(todayDate);
            int mesActual=Integer.parseInt(fMes);
            String fecha,fechames;
            int idx1,idx2;
                for (Contactos contactos : listaOriginal) {
                    fecha= contactos.getFecha_nacimiento();
                    idx1= fecha.indexOf("/");
                    idx2=fecha.indexOf("/",idx1+1);
                    fechames=fecha.substring(idx1+1,idx2);
                    mes=Integer.parseInt(fechames);

                    if (mesActual==mes) {
                        listaContactos.add(contactos);
                        bandera=1;
                    }

                }
            }

        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIzq, textViewDer;


        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIzq = itemView.findViewById(R.id.textViewIzq);
            textViewDer = itemView.findViewById(R.id.textViewDer);
        }
    }


}
