package com.example.igym.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Tarifas extends AppCompatActivity{

    ArrayAdapter<String> adapter;
    ArrayList<String> tarifas;
    ArrayList<Actividad> listAct;
    DBManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarifas);

        this.dbManager = new DBManager(this.getApplicationContext());

        ListView lisTarifas = this.findViewById(R.id.idLisTarifas);

        consultarListaActividades();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_selectable_list_item,
                this.tarifas);
        lisTarifas.setAdapter(adapter);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void obtenerLista()
    {
        tarifas = new ArrayList<String>();

        for(int i=0; i<listAct.size();i++)
        {
            tarifas.add(listAct.get(i).getNombreActividad()+"\n"+ listAct.get(i).getPrecio());
        }
    }

    private void consultarListaActividades()
    {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        double precioTotal = 0;
        listAct = new ArrayList<Actividad>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBManager.TABLA_ACTIVIDAD + " WHERE " + DBManager.SELECCIONADA + " = 1", null);
        while (cursor.moveToNext()){
            Actividad act = new Actividad();
            act.setNombreActividad(cursor.getString(0));
            act.setPrecio(Double.parseDouble(cursor.getString(3)));
            precioTotal += Double.parseDouble(cursor.getString(3));
            listAct.add(act);
        }

        // Actualizar el TextView con el precio total
        TextView precioTextView = this.findViewById(R.id.idPrecioDouble);
        precioTextView.setText("Total: " + precioTotal);



        obtenerLista();
    }
    }

