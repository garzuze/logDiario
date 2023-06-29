package com.example.logdiario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtName, txtEmail, txtPassword;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName     = findViewById(R.id.txtName);
        txtEmail    = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnStart    = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criação de um usuário nulo que será preenchido posteriormente
                User user = null ;

                // Tenta construir o objeto user
                try {
                    user = new User(-1,
                            txtName.getText().toString(),
                            txtEmail.getText().toString(),
                            txtPassword.getText().toString());
                } catch (Exception e){ // Procura por erro na criação do usuário
                    Toast.makeText(MainActivity.this, "Erro na criação do usuário!", Toast.LENGTH_LONG).show();
                    // Uma forma de registrar no banco que deu erro
                    user = new User(-1,"erro", "erro", "erro");
                }

                // Inicia a conexão com o banco de dados
                AcessDB database = new AcessDB(MainActivity.this);

                // Adiciona o objeto user criado no banco de dados. Se tudo der certo, aparece um Toast no app
                boolean success = database.addUser(user);
                Toast.makeText(MainActivity.this, "Sucesso: "+success, Toast.LENGTH_SHORT).show();

                // Intent que leva para a próxima tela
                Intent in = new Intent(MainActivity.this, bd_screen.class);
                startActivity(in);
                cleanUp();
            }
        });
    }

    // Limpar todos os campos
    public void cleanUp(){
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}