package com.example.igym.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import java.util.ArrayList;

public class SelecAct extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> nombresActividades;
    DBManager dbManager;
    ListView listaAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selec_act);

        dbManager = new DBManager(this);
        listaAct = findViewById(R.id.idListAct);

        cargarActividades();

        Button btGuardar = findViewById(R.id.btGuardar);
        btGuardar.setText(R.string.texto_boton_guardar);
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSeleccion();
            }
        });
    }

    private void cargarActividades() {
        nombresActividades = dbManager.obtenerNombresActividades();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, nombresActividades);
        listaAct.setAdapter(adapter);

        // Marcando las actividades seleccionadas
        for (int i = 0; i < nombresActividades.size(); i++) {
            if (dbManager.estaSeleccionada(nombresActividades.get(i))) {
                listaAct.setItemChecked(i, true);
            }
        }
    }

    private void guardarSeleccion() {
        for (int i = 0; i < listaAct.getCount(); i++) {
            String nombreActividad = (String) listaAct.getItemAtPosition(i);
            boolean seleccionada = listaAct.isItemChecked(i);
            dbManager.toggleSeleccionada(nombreActividad, seleccionada);
        }
        Toast.makeText(SelecAct.this, "Selecciones actualizadas", Toast.LENGTH_LONG).show();
        finish();
    }
}





