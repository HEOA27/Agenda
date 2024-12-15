package com.cdp.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.cdp.agenda.adaptadores.ListaContactosAdapter;
import com.cdp.agenda.db.DbContactos;
import com.cdp.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    FloatingActionButton fabNuevo;
    ListaContactosAdapter adapter;
    private String channelID="chanelID";
    private String channelName="chanelName";
    Contactos contacto;

    private int dia,mes,ano,banderaCumpleanos,diaActual,mesActual,anoActual;
    String fecha,fechadia,fechames,fechaano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtBuscar = findViewById(R.id.txtBuscar);
        listaContactos = findViewById(R.id.listaContactos);
        fabNuevo = findViewById(R.id.favNuevo);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        DbContactos dbContactos = new DbContactos(MainActivity.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
        programarNotificacion();

    }

    private void programarNotificacion(){
        banderaCumpleanos=diaCumpleaños();
        if(banderaCumpleanos>=1){
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY,9);
            calendar.set(Calendar.MINUTE,20);
            calendar.set(Calendar.SECOND,30);

            Intent intent=new Intent(getApplicationContext(), Notification_receiver.class);
            PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }

    }
    private int diaCumpleaños(){
        int contador=0;
        ArrayList<Contactos> listaContactos;
        DbContactos dbContactos = new DbContactos(MainActivity.this);
        listaContactos= dbContactos.mostrarContactosFiltros();
        for(Contactos contactos : listaContactos){
            if (contactos != null) {
                if(!contactos.getFecha_nacimiento().equals("")) {
                    fecha=contactos.getFecha_nacimiento();
                    int idx1= fecha.indexOf("/");
                    int idx2=fecha.indexOf("/",idx1+1);
                    fechadia=fecha.substring(0,idx1);
                    dia=Integer.parseInt(fechadia);
                    fechames=fecha.substring(idx1+1,idx2);
                    mes=Integer.parseInt(fechames);
                    fechaano=fecha.substring(idx2+1);
                    ano=Integer.parseInt(fechaano);

                    TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
                    final Calendar c=Calendar.getInstance(timeZone);
                    diaActual=c.get(Calendar.DAY_OF_MONTH);
                    mesActual=c.get(Calendar.MONTH);
                    mesActual+=1;
                    anoActual=c.get(Calendar.YEAR);

                    if((dia==diaActual)&&(mes==mesActual)){
                        contador++;
                    }

                }
            }
        }

        return contador;
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;
            case R.id.menuFiltro:
                filtroRegistro()  ;
                return true;
            case R.id.menuExcel:
                //exportar();
                mensajeExportar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }
    private void filtroRegistro(){
        Intent intentFiltro=new Intent(this,FiltroActivity.class);
        startActivity(intentFiltro);
    }
    private void exportar(){
        boolean bandera;
        pedirPermisos();
        DbContactos dbContactos = new DbContactos(MainActivity.this);
        bandera=dbContactos.exportarCSV();
        if(bandera){
            Toast.makeText(MainActivity.this, "SE CREO EL ARCHIVO CSV EXITOSAMENTE", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "No hay registros.", Toast.LENGTH_LONG).show();
        }

    }

    private void mensajeExportar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("¿Desea exportar a un archivo Excel?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exportar();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0
            );

        }
    }

}