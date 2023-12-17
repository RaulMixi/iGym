package com.example.igym.ui;

import android.widget.TimePicker;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AddAct extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_act);

        DBManager dbManager = new DBManager( this.getApplicationContext() );

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        EditText nombreAct = this.findViewById(R.id.idNombre);

        EditText descr = this.findViewById(R.id.descr);

        EditText tarifa = this.findViewById(R.id.idTarifa);




        Button add = this.findViewById(R.id.editAct);
        add.setText(R.string.texto_botonadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tarifaStr = tarifa.getText().toString();
                // Obteniendo instancias de los TimePickers
                TimePicker timePickerInicio = AddAct.this.findViewById(R.id.timePickerInicio);
                TimePicker timePickerFin = AddAct.this.findViewById(R.id.timePickerFin);

                // Obteniendo la hora y minutos de los TimePickers
                int horaInicio = timePickerInicio.getHour();
                int minutoInicio = timePickerInicio.getMinute();
                int horaFin = timePickerFin.getHour();
                int minutoFin = timePickerFin.getMinute();

                // Convertir a formato de hora
                String horario = horaInicio + ":" + minutoInicio + " - " + horaFin + ":" + minutoFin;
                if (!tarifaStr.isEmpty()) {

                        double tarifaDouble = Double.parseDouble(tarifaStr);
                        dbManager.add(nombreAct.getText().toString(), descr.getText().toString(), horario, tarifaDouble, 0);
                        AddAct.this.finish();



                }
            }
        });

    }
}
