package com.cdp.agenda;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cdp.agenda.db.DbContactos;
import com.cdp.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo,txtDireccion,txtFechaNacimiento,txtNota;
    Spinner cbxGrupo,cbxTipo;
    RadioButton rbFemenino, rbMasculino;
    TextView txtViewEdad,txtFechaRegistro;
    ImageButton btnCalendario;
    private int dia,mes,ano,edad;
    String sexo,fecha,fechadia,fechames,fechaano;
    //String []opcionesGrupo;
    //ArrayAdapter<String> adapterGrupo;

    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Contactos contacto;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        txtDireccion=findViewById(R.id.txtDireccion);

        txtFechaNacimiento=findViewById(R.id.txtFechaNacimiento);
        txtFechaRegistro=findViewById(R.id.txtFechaRegistro);
        txtViewEdad=findViewById(R.id.txtViewEdad);
        cbxGrupo=findViewById(R.id.cbxGrupo);
        cbxTipo=findViewById(R.id.cbxTipo);
        rbFemenino=findViewById(R.id.rbFemenino);
        rbMasculino=findViewById(R.id.rbMasculino);
        txtNota=findViewById(R.id.txtNota);

        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        btnCalendario=findViewById(R.id.btnCalendario);

        //btnCalendario.setVisibility(View.INVISIBLE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        obtenerDatos();







        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {
                    if(rbFemenino.isChecked()){
                        sexo="Femenino";
                    }else {
                        sexo="Masculino";
                    }


                    correcto = dbContactos.editarContacto(id, txtNombre.getText().toString(), txtTelefono.getText().toString(), txtCorreo.getText().toString(), txtDireccion.getText().toString(),sexo,txtFechaNacimiento.getText().toString(),cbxGrupo.getSelectedItem().toString(),cbxTipo.getSelectedItem().toString(),txtNota.getText().toString(),txtFechaRegistro.getText().toString());


                    if(correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==btnCalendario){
                    TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
                    final Calendar c=Calendar.getInstance(timeZone);
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    ano=c.get(Calendar.YEAR);
                    txtFechaRegistro.setText(dia + "/"+(mes+1)+"/"+ano);
                    DatePickerDialog datePickerDialog=new DatePickerDialog(EditarActivity.this,new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            txtFechaNacimiento.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);

                            txtViewEdad.setText(" "+calcularEdad(ano,year,mes+1,monthOfYear+1,dia,dayOfMonth));

                        }
                    }
                            ,dia,mes,ano);
                    datePickerDialog.show();

                }
            }
        });



    }

    private void obtenerDatos() {
        if (contacto != null) {
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreo_electornico());
            txtDireccion.setText(contacto.getDireccion());

            if (contacto.getSexo().equals("Femenino")){
                rbFemenino.setChecked(true);
                rbMasculino.setChecked(false);
            }
            else{
                rbMasculino.setChecked(true);
                rbFemenino.setChecked(false);
            }
            if(!contacto.getFecha_nacimiento().equals("")){
                txtFechaNacimiento.setText(contacto.getFecha_nacimiento());
                fecha=contacto.getFecha_nacimiento();
                int idx1= fecha.indexOf("/");
                int idx2=fecha.indexOf("/",idx1+1);
                fechadia=fecha.substring(0,idx1);
                dia=Integer.parseInt(fechadia);
                fechames=fecha.substring(idx1+1,idx2);
                mes=Integer.parseInt(fechames);
                fechaano=fecha.substring(idx2+1);
                ano=Integer.parseInt(fechaano);
                txtViewEdad.setText(" " + calcularEdadRecuperada(ano,mes,dia));}
            //txtViewEdad.setText(fechaano);


            ArrayList<String>listaGrupo=new ArrayList<String>();
            listaGrupo.add(contacto.getGrupo());
            listaGrupo.add("Adolescentes");
           // listaGrupo.add("Adultos");
            listaGrupo.add("Iglesia Infantil");
            listaGrupo.add("Jóvenes");
            listaGrupo.add("Mujeres");
            listaGrupo.add("Varones");
            String [] arrayDeStringsG = listaGrupo.toArray(new String[listaGrupo.size()]);

            //String []opcionesGrupo={contacto.getGrupo()};

            // lista de entrada con duplicados
            List<String> listWithDuplicates = new ArrayList<>(Arrays.asList(arrayDeStringsG));

            // construye un conjunto a partir de elementos de la lista
            Set<String> set = new LinkedHashSet<>(listWithDuplicates);

            // construir una nueva lista a partir de un conjunto e imprimirla
            List<String> listWithoutDuplicates = new ArrayList<>(set);

            ArrayAdapter<String>adapterGrupo = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listWithoutDuplicates);
            cbxGrupo.setAdapter(adapterGrupo);

            /*String []opcionesTipo={contacto.getTipo()};
            List<String> listWithDuplicatesT = new ArrayList<>(Arrays.asList(opcionesTipo));
            Set<String> setT = new LinkedHashSet<>(listWithDuplicatesT);
            List<String> listWithoutDuplicatesT = new ArrayList<>(setT);
            ArrayAdapter<String> adapterTipo = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listWithoutDuplicatesT);
            adapterTipo.add(contacto.getTipo());*/
            //cbxTipo.setAdapter(adapterTipo);
            //String []opcionesTipo={contacto.getTipo(),"Asistencia frecuente","Bautizado","No Bautizado","Visitante"};
            ArrayList<String>listaTipo=new ArrayList<String>();
            listaTipo.add(contacto.getTipo());
            listaTipo.add("Asistencia frecuente");
            listaTipo.add("Bautizado");
            listaTipo.add("No Bautizado");
            listaTipo.add("Visitante");
            String [] arrayDeStrings = listaTipo.toArray(new String[listaTipo.size()]);
            List<String> listWithDuplicatesT = new ArrayList<>(Arrays.asList(arrayDeStrings));
            Set<String> setT = new LinkedHashSet<>(listWithDuplicatesT);
            List<String> listWithoutDuplicatesT = new ArrayList<>(setT);
            ArrayAdapter<String>adapterT=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listWithoutDuplicatesT);
            cbxTipo.setAdapter(adapterT);



            txtNota.setText(contacto.getNota());
            txtFechaRegistro.setText(contacto.getFecha_registro());




            /*txtNombre.setInputType(InputType.TYPE_NULL);
            txtTelefono.setInputType(InputType.TYPE_NULL);
            txtCorreo.setInputType(InputType.TYPE_NULL);
            txtDireccion.setInputType(InputType.TYPE_NULL);
            rbMasculino.setInputType(InputType.TYPE_NULL);
            rbFemenino.setInputType(InputType.TYPE_NULL);
            txtFechaNacimiento.setInputType(InputType.TYPE_NULL);
            txtNota.setInputType(InputType.TYPE_NULL);*/


        }
    }

    private int calcularEdadRecuperada( int anoNacimiento, int mesNacimiento, int diaNacimiento){
        Date todayDate = new Date();
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");

        String fDia = dia.format(todayDate);
        String fMes = mes.format(todayDate);
        String fAno = ano.format(todayDate);
        int anoActual=Integer.parseInt(fAno);
        int mesActual=Integer.parseInt(fMes);
        int diaActual=Integer.parseInt(fDia);


        // txtViewEdad.setText(fAno);
        edad=anoActual-anoNacimiento;
        if(mesNacimiento>mesActual){
            edad=edad-1;
        }else{
            if(mesNacimiento==mesActual){
                if(diaNacimiento>diaActual){
                    edad=edad-1;
                }
                //if(diaNacimiento==diaActual){
                //Felicidades el dia de hoy es su cumpleaños
                // }
            }
        }
        return edad;
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    private int calcularEdad(int anoActual, int anoNacimiento,int mesActual, int mesNacimiento, int diaActual, int diaNacimiento){
        edad=anoActual-anoNacimiento;
        if(mesNacimiento>mesActual){
            edad=edad-1;
        }else{
            if(mesNacimiento==mesActual){
                if(diaNacimiento>diaActual){
                    edad=edad-1;
                }
                //if(diaNacimiento==diaActual){
                //Felicidades el dia de hoy es su cumpleaños
                // }
            }
        }
        return edad;
    }

}