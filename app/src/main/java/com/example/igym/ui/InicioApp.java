package com.example.igym.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.igym.R;
import com.example.igym.model.Actividad;
import com.example.igym.database.DBManager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class InicioApp extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> actividades;
    ArrayList<Actividad> listAct;
    DBManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        this.dbManager = new DBManager(this.getApplicationContext());

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TextView textViewListaAct = findViewById(R.id.textView2);
        textViewListaAct.setText(R.string.tv_listaActividades);

        Button btHorario = this.findViewById(R.id.btHorario);
        btHorario.setText(R.string.bt_Horarios);
        Intent intent = new Intent(InicioApp.this, Horarios.class);
        btHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InicioApp.this.startActivity(intent);
            }
        });

        Button btTarifa = this.findViewById(R.id.btTarifa);
        btTarifa.setText(R.string.bt_Tarifas);
        Intent intent4 = new Intent(InicioApp.this, Tarifas.class);
        btTarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InicioApp.this.startActivity(intent4);
            }
        });

        Button btMyAct = this.findViewById(R.id.idMyAct);
        btMyAct.setText(R.string.bt_Actividades);
        Intent intent5 = new Intent(InicioApp.this, misActividades.class);
        btMyAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent5);
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

        consultarListaActividadesSeleccionadas();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_selectable_list_item,
                this.actividades);

        ListView listaAct = findViewById(R.id.idListAct);
        listaAct.setAdapter(adapter);

        InicioApp.this.registerForContextMenu(listaAct);

        listaAct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el nombre del elemento en la posición clicada
                String nombreElemento = (String) adapter.getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(InicioApp.this);
                builder.setTitle("Descripción");
                // Usar el nombre del elemento como mensaje en el AlertDialog
                builder.setMessage(nombreElemento);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onStart();  // Considera actualizar solo el adaptador en lugar de llamar a onStart()
                    }
                });
                builder.setNegativeButton("Cancelar", null);

                // Mostrar el diálogo
                builder.show();
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.regAct) {
            Intent intent2 = new Intent(InicioApp.this, AddAct.class);
            startActivity(intent2);
            return true;
        }else if(itemId == R.id.action_mapa){
            Intent intent3 = new Intent(InicioApp.this, webView.class);
            startActivity(intent3);
            return true;
        }else if(itemId == R.id.info){
            AlertDialog.Builder builder = new AlertDialog.Builder(InicioApp.this);
            builder.setTitle("ℹ ¿Quien somos?");
            builder.setMessage("Bienvenido a iGym, la aplicación móvil definitiva para amantes del fitness y entusiastas del gimnasio. Esta aplicación innovadora está diseñada para mejorar tu experiencia en el gimnasio, permitiéndote apuntarte a actividades, consultar horarios, y mucho más, todo desde la palma de tu mano.\n" +
                    "\n" +
                    "Características Principales:\n" +
                    "Inscripción en Actividades: Con unos pocos clics, podrás apuntarte a una variedad de actividades ofrecidas por tu gimnasio. Desde clases de yoga hasta sesiones intensas de HIIT, tenemos algo para todos.\n" +
                    "\n" +
                    "Consulta de Horarios: ¿Necesitas planificar tu día? Consulta fácilmente los horarios de todas las actividades disponibles. Así podrás organizarte mejor y no perderte ninguna de tus clases favoritas.\n" +
                    "\n" +
                    "Detalles y Descripciones de Actividades: Obtén información detallada sobre cada actividad, incluyendo una descripción completa, lo que te ayudará a elegir la mejor opción según tus objetivos y preferencias de fitness.\n" +
                    "\n" +
                    "Información de Precios: Transparente y al alcance de tu mano. Consulta los precios de las diferentes actividades para gestionar tu presupuesto de forma eficiente.\n" +
                    "\n" +
                    "¿Cómo Funciona?\n" +
                    "Fácil de Usar: Nuestra interfaz intuitiva te permite navegar fácilmente entre las diferentes funciones. Inscribirse, cancelar o cambiar actividades es sencillo y rápido.\n" +
                    "\n" +
                    "Actualizaciones en Tiempo Real: Recibe notificaciones y actualizaciones en tiempo real sobre cambios en horarios, nuevas actividades o promociones especiales.\n" +
                    "\n" +
                    "Perfil Personalizado: Crea tu perfil y mantén un registro de tus actividades, progresos y clases favoritas.\n" +
                    "\n" +
                    "Seguridad y Privacidad:\n" +
                    "Tu seguridad y privacidad son nuestra máxima prioridad. Todas tus datos están protegidos y nunca se comparten sin tu consentimiento explícito.\n" +
                    "\n" +
                    "¡Únete a Nuestra Comunidad!\n" +
                    "Gym Activities es más que una app, es una comunidad. Únete a nosotros y lleva tu experiencia de fitness al siguiente nivel. ¡Descarga la app hoy y comienza tu viaje hacia un estilo de vida más saludable y activo!");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }else if(itemId == R.id.idMyAct){
            FirebaseAuth.getInstance().signOut();
            onBackPressed(); // O puedes iniciar otra actividad como una pantalla de inicio de sesión
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if ( v.getId() == R.id.idListAct)
        {
            this.getMenuInflater().inflate( R.menu.menu_contextual, menu );
            menu.setHeaderTitle( R.string.app_name );
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.eliminar) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            AlertDialog.Builder builder = new AlertDialog.Builder(InicioApp.this);
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
            Actividad actividadSeleccionada = listAct.get(position);
            Intent intent = new Intent(InicioApp.this, EditAct.class);
            intent.putExtra("nombreActividad", actividadSeleccionada.getNombreActividad());
            intent.putExtra("descripcion", actividadSeleccionada.getDescr());
            startActivity(intent);

        }

        return super.onContextItemSelected(item);
    }

    private void consultarListaActividadesSeleccionadas()
    {
        SQLiteDatabase db = dbManager.getReadableDatabase();

        listAct = new ArrayList<Actividad>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBManager.TABLA_ACTIVIDAD + " WHERE " + DBManager.SELECCIONADA + " = 1", null);

            while (cursor.moveToNext()) {
                Actividad act = new Actividad();
                act.setNombreActividad(cursor.getString(0));
                act.setDescr(cursor.getString(1));

                listAct.add(act);
            }

            obtenerLista();
    }

    public void remove(int position) {
        dbManager.remove(listAct.get(position).getNombreActividad());
        listAct.remove(position);
        adapter.notifyDataSetChanged();
    }



    private void obtenerLista()
    {
        actividades = new ArrayList<String>();

        for(int i=0; i<listAct.size();i++)
        {
            actividades.add(listAct.get(i).getNombreActividad()+"\n"+ listAct.get(i).getDescr());
        }
    }

}




