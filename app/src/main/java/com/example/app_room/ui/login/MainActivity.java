package com.example.app_room.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_room.R;
import com.example.app_room.data.database.AppDatabase;
import com.example.app_room.data.model.User;
import com.example.app_room.ui.cadastro.CadastroActivity;
import com.example.app_room.ui.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView btnCadastro;
    private EditText edtEmail, edtSenha;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnEntrar);
        edtEmail = findViewById(R.id.editTextEmail);
        edtSenha = findViewById(R.id.editTextSenha);
        btnCadastro = findViewById(R.id.textCriarConta);

        db = AppDatabase.getInstance(this);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Executa a busca no Room em background
            AppDatabase.databaseWriteExecutor.execute(() -> {
                User user = db.userDao().login(email, senha);

                runOnUiThread(() -> {
                    if (user != null) {
                        Toast.makeText(MainActivity.this, "Bem-vindo, " + user.getName() + "!", Toast.LENGTH_SHORT).show();
                        // Chama o método passando o nome retornado do banco
                        navegarParaHome(user.getName());
                    } else {
                        Toast.makeText(MainActivity.this, "Login inválido!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        btnCadastro.setOnClickListener(v -> {
            navegarParaCadastro();
        });
    }

    // Método correto declarado fora do onCreate
    private void navegarParaHome(String nomeDoUsuario) {
        Intent telaMenu = new Intent(MainActivity.this, MenuActivity.class);
        telaMenu.putExtra("USER_NAME", nomeDoUsuario);
        startActivity(telaMenu);
    }

    private void navegarParaCadastro() {
        Intent telaCadastro = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(telaCadastro);
    }
}