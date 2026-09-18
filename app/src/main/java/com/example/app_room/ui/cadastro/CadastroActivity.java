package com.example.app_room.ui.cadastro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_room.R;
import com.example.app_room.data.database.AppDatabase;
import com.example.app_room.data.model.User;

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private TextView btnLogin;
    private EditText edtNome, edtEmail, edtSenha;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCadastrar = findViewById(R.id.btnSignUp);
        edtNome = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmailRegister);
        edtSenha = findViewById(R.id.editTextPasswordRegister);
        btnLogin = findViewById(R.id.textGoToLogin);

        db = AppDatabase.getInstance(this);

        btnCadastrar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Executa a verificação e inserção no Room em background
            AppDatabase.databaseWriteExecutor.execute(() -> {
                User existingUser = db.userDao().getUserByEmail(email);

                if (existingUser != null) {
                    runOnUiThread(() ->
                            Toast.makeText(CadastroActivity.this, "E-mail já cadastrado!", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    User newUser = new User(nome, email, senha);
                    db.userDao().registerUser(newUser);

                    runOnUiThread(() -> {
                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        navegarParaLogin();
                    });
                }
            });
        });

        btnLogin.setOnClickListener(v -> {
            navegarParaLogin();
        });
    }

    private void navegarParaLogin() {
        finish();
    }
}