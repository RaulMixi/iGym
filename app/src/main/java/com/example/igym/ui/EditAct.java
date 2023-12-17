package com.example.igym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditAct extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_act);

        DBManager dbManager = new DBManager( this.getApplicationContext() );

        Intent intent = getIntent();
        String nombreActividad = intent.getStringExtra("nombreActividad");
        String descripcion = intent.getStringExtra("descripcion");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        EditText nombreAct = this.findViewById(R.id.idNombre);
        TextView textViewnombreAct = findViewById(R.id.textView9);
        textViewnombreAct.setText(R.string.tv_nombre);
        nombreAct.setText(nombreActividad);

        EditText descr = this.findViewById(R.id.descr);
        TextView textViewdescr = findViewById(R.id.textView10);
        textViewdescr.setText(R.string.tv_descripcion);
        descr.setText(descripcion);

        Button btnEditar = findViewById(R.id.editAct);
        btnEditar.setText(R.string.txtEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoNombre = nombreAct.getText().toString();
                String nuevaDescr = descr.getText().toString();
                dbManager.edit(nuevoNombre, nuevaDescr);
                finish();
            }
        });

    }


}
