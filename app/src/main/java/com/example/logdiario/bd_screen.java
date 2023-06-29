package com.example.logdiario;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.List;

public class bd_screen extends AppCompatActivity {
    ListView users_list;
    EditText txt_name, txt_email, txt_pwd;
    Button btn_update, btn_remove;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd_screen);

        txt_name    =  findViewById(R.id.txt_name);
        txt_email   =  findViewById(R.id.txt_email);
        txt_pwd    =  findViewById(R.id.txt_pwd);
        btn_update    =  findViewById(R.id.btn_update);
        btn_remove    =  findViewById(R.id.btn_remove);
        users_list  =  findViewById(R.id.users_list);

        // Inicia a conexão com o banco de dados
        AcessDB database = new AcessDB(bd_screen.this);


        // Criação de uma lista da tabela com todos os usuários
        List<User> all_users = database.getUserList();

        // Conversão da lista para array, que é necessária para colocar a lista de usuários no componente xml
        ArrayAdapter usuarioArrayAdapter = new ArrayAdapter<User>(bd_screen.this,
                android.R.layout.simple_list_item_1, all_users);
        users_list.setAdapter(usuarioArrayAdapter);

        users_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Pega a posição (id) do usuário que foi clicado
                User clicked_user = (User)parent.getItemAtPosition(position);
                //Seta os campos de nome, email e senha para corresponderem ao do usuário clicado
                txt_name.setText(clicked_user.getNameUser());
                txt_email.setText(clicked_user.getemailUser());
                txt_pwd.setText(clicked_user.getpwdUser());

                // Ação do botão remover
                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove o usuário clickado e atualiza a lista
                        database.removeUser(clicked_user);
                        updateListView(database);

                        cleanUp();
                    }
                });

                // Ação do botão alterar
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cria um objeto User, com o id do usuário clicado, que irá pegar os campos
                        // com as novas informações e alterar o usuário correspondente
                        User user_update = new User(clicked_user.getIdUser(),
                                txt_name.getText().toString(),
                                txt_email.getText().toString(),
                                txt_pwd.getText().toString());

                        cleanUp();
                        database.updateUser(user_update); // Chamada do método update que vai setar
                        // o novo usuário alterado no lugar do que foi clicado
                        updateListView(database);
                    }
                });
            }
        });
    }
    // Método para atualizar a ListView
    private void updateListView(AcessDB database) {
        ArrayAdapter userArray = new ArrayAdapter<User>(getApplicationContext(),
                android.R.layout.simple_list_item_1, database.getUserList());//Dentro de <> está o tipo de objeto que será adicionado na lista
        users_list.setAdapter(userArray);
    }

    // Método para limpar os campos
    public void cleanUp(){
        txt_name.setText("");
        txt_email.setText("");
        txt_pwd.setText("");
    }
}