package com.example.igym.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.igym.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.Provider;

public class Registro extends AppCompatActivity{

    private EditText email;
    private EditText password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        email = findViewById(R.id.idMailRe);
        password = findViewById(R.id.idPassRe);

        EditText idMailReEditText = findViewById(R.id.idMailRe);
        String hintTextoMailRe = getResources().getString(R.string.texto_campoemail);
        idMailReEditText.setHint(hintTextoMailRe);

        EditText idPassReEditText = findViewById(R.id.idPassRe);
        String hintTextoPassRe = getResources().getString(R.string.texto_campocontra);
        idPassReEditText.setHint(hintTextoPassRe);

        Button btRegistro = findViewById(R.id.btRegistroUs);
        btRegistro.setText(R.string.texto_boton_registrarse);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                Provider provider = null;
                if (!mail.isEmpty() && !pass.isEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registro.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Registro.this,InicioApp.class).putExtra("email", mail).putExtra("provider", provider.getName());
                                startActivity(intent);
                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(Registro.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Registro.this, "Correo electrónico y contraseña son obligatorios", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
