package com.example.app_room.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_room.R;
import com.example.app_room.ui.login.MainActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView textWelcome;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textWelcome = findViewById(R.id.textWelcomeMessage);
        btnLogout = findViewById(R.id.btnLogout);

        // 1. Pega o Intent que abriu esta tela
        Intent intent = getIntent();

        // 2. Extrai o nome guardado com a chave "USER_NAME"
        String nomeDoUsuario = intent.getStringExtra("USER_NAME");

        // 3. Atualiza o texto na tela
        if (nomeDoUsuario != null && !nomeDoUsuario.isEmpty()) {
            textWelcome.setText("Olá, " + nomeDoUsuario + "!");
        }

        // 4. Configura o botão de logout
        btnLogout.setOnClickListener(v -> {
            // Fecha a tela de menu e volta para a tela de login
            finish();
        });
    }
}