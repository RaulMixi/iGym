package com.example.igym.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.database.sqlite.SQLiteDatabaseKt;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.igym.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText idMailReEditText = findViewById(R.id.idMailRe);
        String hintTextoMailRe = getResources().getString(R.string.texto_campoemail);
        idMailReEditText.setHint(hintTextoMailRe);

        EditText idPassReEditText = findViewById(R.id.idPassRe);
        String hintTextoPassRe = getResources().getString(R.string.texto_campocontra);
        idPassReEditText.setHint(hintTextoPassRe);

        Button btInicioSesion = findViewById(R.id.btRegistroUs);
        btInicioSesion.setText(R.string.texto_boton_inicsesion);

        TextView textViewEncimaRegistro = findViewById(R.id.textView7);
        textViewEncimaRegistro.setText(R.string.tv_registrarse);

        Button btRegistro = findViewById(R.id.btRegistro);
        btRegistro.setText(R.string.texto_boton_registrarse);

        FirebaseAnalytics analiticas = FirebaseAnalytics.getInstance(this);
        Bundle bun = new Bundle();
        bun.putString("mensaje", "Integracion de la base de dtos");
        analiticas.logEvent("Init", bun);

        email = this.findViewById(R.id.idMailRe);
        password = findViewById(R.id.idPassRe);


        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this, Registro.class);
                startActivity(registro);
                Toast.makeText(MainActivity.this, "Vamos a registrarnos", Toast.LENGTH_LONG).show();
            }
        });

        btInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (!mail.isEmpty() && !pass.isEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Iniciado correctamente", Toast.LENGTH_LONG).show();
                                        Intent registro = new Intent(MainActivity.this, InicioApp.class);
                                        startActivity(registro);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Correo electrónico y contraseña incorrectos", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "Correo electrónico y contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void  onStart() {
        super.onStart();
    }
}

