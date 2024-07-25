package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cdp.agenda.db.DbContactos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreoElectronico, txtDireccion,txtFechaNacimiento,txtNota;
    Spinner cbxGrupo,cbxTipo;
    RadioButton rbFemenino, rbMasculino;
    TextView txtViewEdad,txtFechaRegistro;
    Button btnGuarda;
    ImageButton btnCalendario;
    private int dia,mes,ano,edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtFechaNacimiento=findViewById(R.id.txtFechaNacimiento);
        txtFechaRegistro=findViewById(R.id.txtFechaRegistro);
        txtViewEdad=findViewById(R.id.txtViewEdad);
        cbxGrupo=findViewById(R.id.cbxGrupo);
        cbxTipo=findViewById(R.id.cbxTipo);
        rbFemenino=findViewById(R.id.rbFemenino);
        rbMasculino=findViewById(R.id.rbMasculino);
        txtNota=findViewById(R.id.txtNota);


        btnGuarda = findViewById(R.id.btnGuarda);
        btnCalendario=findViewById(R.id.btnCalendario);



        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {

                    DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                    long id = dbContactos.insertarContacto(txtNombre.getText().toString(), txtTelefono.getText().toString(), txtCorreoElectronico.getText().toString(),txtDireccion.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
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
                txtFechaRegistro.setText("Fecha de registro: "+ dia + "/"+(mes+1)+"/"+ano);
                    DatePickerDialog datePickerDialog=new DatePickerDialog(NuevoActivity.this,new DatePickerDialog.OnDateSetListener(){
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
    private void limpiar() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
        txtDireccion.setText("");
    }
}