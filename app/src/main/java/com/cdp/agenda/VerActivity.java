package com.cdp.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cdp.agenda.db.DbContactos;
import com.cdp.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo, txtDireccion,txtFechaNacimiento,txtNota;
    Spinner cbxGrupo,cbxTipo;
    RadioButton rbFemenino, rbMasculino;
    TextView txtViewEdad,txtFechaRegistro;
    Button btnGuarda;
    ImageButton btnCalendario;
    int dia,mes,ano,edad;
    private String sexo,fecha,fechadia,fechames,fechaano;
    FloatingActionButton fabEditar, fabEliminar;

    Contactos contacto;
    int id = 0;

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

        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);
        btnCalendario=findViewById(R.id.btnCalendario);
        btnCalendario.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);
        obtenerDatos();


        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(dbContactos.eliminarContacto(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void obtenerDatos() {

        if(contacto != null){
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
        txtViewEdad.setText(" " + calcularEdad(ano,mes,dia));}
        //txtViewEdad.setText(fechaano);



        String []opcionesGrupo={contacto.getGrupo()};

        // lista de entrada con duplicados
        //List<String> listWithDuplicates = new ArrayList<>(Arrays.asList(opcionesGrupo));

        // construye un conjunto a partir de elementos de la lista
        //Set<String> set = new LinkedHashSet<>(listWithDuplicates);

        // construir una nueva lista a partir de un conjunto e imprimirla
        //List<String> listWithoutDuplicates = new ArrayList<>(set);

        ArrayAdapter<String> adapterGrupo = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opcionesGrupo);
        cbxGrupo.setAdapter(adapterGrupo);

        String []opcionesTipo={contacto.getTipo()};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opcionesTipo);
        cbxTipo.setAdapter(adapterTipo);

        txtNota.setText(contacto.getNota());
        txtFechaRegistro.setText(contacto.getFecha_registro());




        txtNombre.setInputType(InputType.TYPE_NULL);
        txtTelefono.setInputType(InputType.TYPE_NULL);
        txtCorreo.setInputType(InputType.TYPE_NULL);
        txtDireccion.setInputType(InputType.TYPE_NULL);
        /*rbMasculino.setInputType(InputType.TYPE_NULL);
        rbFemenino.setInputType(InputType.TYPE_NULL);*/
        rbMasculino.setEnabled(false);
        rbFemenino.setEnabled(false);
        txtFechaNacimiento.setInputType(InputType.TYPE_NULL);
        txtNota.setInputType(InputType.TYPE_NULL);
        cbxGrupo.setEnabled(false);
        cbxTipo.setEnabled(false);
        }
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private int calcularEdad( int anoNacimiento, int mesNacimiento, int diaNacimiento){
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

}