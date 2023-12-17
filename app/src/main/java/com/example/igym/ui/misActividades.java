package com.example.igym.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class misActividades extends AppCompatActivity {

    protected static final int CODIGO_EDICION_ITEM = 100;
    ArrayList<String> actividades;
    ArrayAdapter<String> adapter;
    ArrayList<Actividad> listaActividades;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misactividades);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Button btSelect = findViewById(R.id.idSelecAct);
        btSelect.setText(R.string.texto_boton_actividades);
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(misActividades.this, SelecAct.class);
                startActivityForResult(intent, CODIGO_EDICION_ITEM);
            }
        });

        EditText textoFiltrar = this.findViewById(R.id.idFiltrar);
        textoFiltrar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No hace falta que haga nada
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No hace falta que haga nada
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        dbManager = new DBManager(this);
        ListView listActSel = findViewById(R.id.lisActSel);
        actividades = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, actividades);
        listActSel.setAdapter(adapter);

        // Registering the ListView for context menu
        registerForContextMenu(listActSel);

        cargarActividades();

    }

    private void cargarActividades() {
        listaActividades = dbManager.obtenerTodasLasActividades();
        for (Actividad act : listaActividades) {
            String detalleActividad = act.getNombreActividad() +
                    " - Horarios: " + act.getHorario() + " - Tarifa: " + act.getPrecio();
            actividades.add(detalleActividad);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Aquí puedes manejar el resultado de SelecAct si es necesario
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.eliminar) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            AlertDialog.Builder builder = new AlertDialog.Builder(misActividades.this);
            builder.setTitle("¿Desea eliminar?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    remove(position);
                    onStart();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }else if(itemId == R.id.editar){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            Actividad actividadSeleccionada = listaActividades.get(position);
            Intent intent = new Intent(misActividades.this, EditAct.class);
            intent.putExtra("nombreActividad", actividadSeleccionada.getNombreActividad());
            intent.putExtra("descripcion", actividadSeleccionada.getDescr());
            startActivity(intent);

        }

        return super.onContextItemSelected(item);
    }

    public void remove(int position) {
        dbManager.remove(listaActividades.get(position).getNombreActividad());
        listaActividades.remove(position);
        adapter.notifyDataSetChanged();
    }

}
