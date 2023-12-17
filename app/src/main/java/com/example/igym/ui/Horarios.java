package com.example.igym.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Horarios extends AppCompatActivity{

    ArrayAdapter<String> adapter;
    ArrayList<String> horarios;
    ArrayList<Actividad> listAct;
    DBManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horarios);


        this.dbManager = new DBManager(this.getApplicationContext());

        ListView lisHorarios = this.findViewById(R.id.idLisHorarios);

        consultarListaActividades();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_selectable_list_item,
                this.horarios);
        lisHorarios.setAdapter(adapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void obtenerLista()
    {
        horarios = new ArrayList<String>();

        for(int i=0; i<listAct.size();i++)
        {
            horarios.add(listAct.get(i).getNombreActividad()+"\n"+ listAct.get(i).getHorario());
        }
    }

    private void consultarListaActividades()
    {
        SQLiteDatabase db = dbManager.getReadableDatabase();

        listAct = new ArrayList<Actividad>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBManager.TABLA_ACTIVIDAD + " WHERE " + DBManager.SELECCIONADA + " = 1", null);
        while (cursor.moveToNext()){
            Actividad act = new Actividad();
            act.setNombreActividad(cursor.getString(0));
            act.setHorario(cursor.getString(2));
            listAct.add(act);
        }

        obtenerLista();
    }
}

