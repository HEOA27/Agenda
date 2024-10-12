package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cdp.agenda.adaptadores.ListaFiltroGruposAdapter;
import com.cdp.agenda.adaptadores.ListaFiltroTiposAdapter;
import com.cdp.agenda.adaptadores.ListaFiltroCumpleanosAdapter;
import com.cdp.agenda.db.DbContactos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FiltroActivity extends AppCompatActivity {
    TextView viewFecha,viewSeleccionado;
    Spinner cbxFiltro;
    private RecyclerView listaFiltro;
    int count=0;
   ListaFiltroCumpleanosAdapter adapter;
   ListaFiltroTiposAdapter adapterTipo;
   ListaFiltroGruposAdapter adapterGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        cbxFiltro=findViewById(R.id.cbxFiltro);
        viewFecha=findViewById(R.id.viewFecha);
        viewSeleccionado=findViewById(R.id.viewSeleccionado);
        listaFiltro=findViewById(R.id.listaFiltro);
        Date todayDate = new Date();
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        String fDia = dia.format(todayDate);
        String fMes = mes.format(todayDate);
        String fAno = ano.format(todayDate);
        viewFecha.setText(fMes +"/"+fAno);

        Locale idioma = Locale.getDefault();
        String nombreMes = (new SimpleDateFormat("MMMM", idioma)).format(todayDate);

        listaFiltro.setLayoutManager(new LinearLayoutManager(FiltroActivity.this));
        DbContactos dbContactos = new DbContactos(FiltroActivity.this);
        adapter = new ListaFiltroCumpleanosAdapter(dbContactos.mostrarContactosFiltros());
        adapterTipo = new ListaFiltroTiposAdapter(dbContactos.mostrarContactosFiltros());
        adapterGrupo = new ListaFiltroGruposAdapter(dbContactos.mostrarContactosFiltros());
        try {
            adapter.filtroCumpleanos(0);
            listaFiltro.setAdapter(adapter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        cbxFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) cbxFiltro.getItemAtPosition(position);
                //viewSeleccionado.setText(selected + position);

                switch (position) {
                    case 0:
                        //Cumpleaños
                        viewSeleccionado.setText(nombreMes);
                        try {

                            adapter.filtroCumpleanos(position);
                            listaFiltro.setAdapter(adapter);

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case 1:
                        //Bautizados
                        try {
                           // count=0;
                            count=adapterTipo.filtroBautizado(position);
                            listaFiltro.setAdapter(adapterTipo);
                            viewSeleccionado.setText("Total: " +count);
                            ///viewSeleccionado.setText("Total: "+ adapterTipo.obtenerContador());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        //No Bautizados
                        try {
                            //count=0;
                            count=adapterTipo.filtroNoBautizado(position);
                            listaFiltro.setAdapter(adapterTipo);
                            ///viewSeleccionado.setText("Total: "+ adapterTipo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 3:
                        //Jóvenes

                        try {
                          //  count=0;
                            count=adapterGrupo.filtroJovenes(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 4:
                        //Mujeres(Grupo)
                        try {
                           // count=0;
                            count=adapterGrupo.filtroMujeres(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 5:
                        //Varones(Grupo)
                        try {
                           // count=0;
                            count= adapterGrupo.filtroVarones(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            viewSeleccionado.setText("Total: " +count);
                           ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 6:
                        //Visitantes
                        try {
                            //count=0;
                            count= adapterTipo.filtroVisitante(position);
                            listaFiltro.setAdapter(adapterTipo);
                           /// viewSeleccionado.setText("Total: "+ adapterTipo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 7:
                        //Niños
                        try {
                            count= adapterGrupo.filtroNiños(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 8:
                        //Adolescentes
                        try {
                            count= adapterGrupo.filtroAdolescentes(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 9:
                        //Adultos
                        try {
                            count= adapterGrupo.filtroAdultos(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 10:
                        //Asistencia Frecuente
                        try {
                            count=0;
                            count= adapterTipo.filtroAsistenciaF(position);
                            listaFiltro.setAdapter(adapterTipo);
                            //viewSeleccionado.setText("Total: "+ adapterTipo.obtenerContador());
                            viewSeleccionado.setText("Total: "+count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 11:
                        //Cant.Mujeres
                        try {
                            count= adapterGrupo.filtroMujer(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 12:
                        //Cant.Hombres
                        try {
                            count= adapterGrupo.filtroHombre(position);
                            listaFiltro.setAdapter(adapterGrupo);
                            ///viewSeleccionado.setText("Total: "+ adapterGrupo.obtenerContador());
                            viewSeleccionado.setText("Total: " +count);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }

                //  Do Something
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}