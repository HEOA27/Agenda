package com.cdp.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cdp.agenda.MainActivity;
import com.cdp.agenda.entidades.Contactos;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String telefono, String correo_electronico, String direccion,String sexo,String fecha_nacimiento,String grupo,String tipo,String nota,String fecha_registro) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);
            values.put("direccion", direccion);
            values.put("sexo", sexo);
            values.put("fecha_nacimiento", fecha_nacimiento);
            values.put("grupo", grupo);
            values.put("tipo", tipo);
            values.put("nota", nota);
            values.put("fecha_registro", fecha_registro);
            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreo_electornico(cursorContactos.getString(3));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setCorreo_electornico(cursorContactos.getString(3));
            contacto.setDireccion(cursorContactos.getString(4));
            contacto.setSexo(cursorContactos.getString(5));
            contacto.setFecha_nacimiento(cursorContactos.getString(6));
            contacto.setGrupo(cursorContactos.getString(7));
            contacto.setTipo(cursorContactos.getString(8));
            contacto.setNota(cursorContactos.getString(9));
            contacto.setFecha_registro(cursorContactos.getString(10));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String correo_electronico, String direccion,String sexo,String fecha_nacimiento,String grupo,String tipo,String nota,String fecha_registro) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            //db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', correo_electronico = '" + correo_electronico + "' WHERE id='" + id + "' ");
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', correo_electronico = '" + correo_electronico + "',direccion = '" + direccion + "',sexo='"+sexo+"',fecha_nacimiento='"+fecha_nacimiento+"', grupo='"+grupo+"',tipo='"+tipo+"',nota='"+nota+"',fecha_registro='"+fecha_registro+"' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public ArrayList<Contactos> mostrarContactosFiltros() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto;
        Cursor cursorContactos;


        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setSexo(cursorContactos.getString(5));
                contacto.setFecha_nacimiento(cursorContactos.getString(6));
                contacto.setGrupo(cursorContactos.getString(7));
                contacto.setTipo(cursorContactos.getString(8));

                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public boolean exportarCSV() {
        boolean bandera=false;
        File carpeta = new File(Environment.getExternalStorageDirectory() + "/ExportarSQLiteCSV");
        String archivoAgenda = carpeta.toString() + "/" + "Contactos.csv";

        boolean isCreate = false;
        if(!carpeta.exists()) {
            isCreate = carpeta.mkdir();
        }

        try {
            FileWriter fileWriter = new FileWriter(archivoAgenda);

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Cursor fila = db.rawQuery("select * from TABLE_CONTACTOS", null);
            Cursor fila=db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

            if(fila != null && fila.getCount() != 0) {
                bandera=true;
                fila.moveToFirst();
                fileWriter.append("ID");
                fileWriter.append(",");
                fileWriter.append("Nombre");
                fileWriter.append(",");
                fileWriter.append("Telefono");
                fileWriter.append(",");
                fileWriter.append("Correo electronico");
                fileWriter.append(",");
                fileWriter.append("Direccion");
                fileWriter.append(",");
                fileWriter.append("Sexo");
                fileWriter.append(",");
                fileWriter.append("Fecha nacimiento");
                fileWriter.append(",");
                fileWriter.append("Grupo");
                fileWriter.append(",");
                fileWriter.append("Tipo");
                fileWriter.append(",");
                fileWriter.append("Nota");
                fileWriter.append(",");
                fileWriter.append("Fecha registro");
                fileWriter.append("\n");
                do {
                    fileWriter.append(fila.getString(0));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(1));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(2));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(3));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(4));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(5));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(6));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(7));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(8));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(9));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(10));
                    fileWriter.append("\n");

                } while(fila.moveToNext());
            } else {
               // Toast.makeText(MainActivity.this, "No hay registros.", Toast.LENGTH_LONG).show();
                bandera=false;
            }

            db.close();
            fileWriter.close();
            //Toast.makeText(MainActivity.this, "SE CREO EL ARCHIVO CSV EXITOSAMENTE", Toast.LENGTH_LONG).show();

        } catch (Exception e) { }
        return bandera;
    }


}
