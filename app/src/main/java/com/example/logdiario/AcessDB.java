package com.example.logdiario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AcessDB extends SQLiteOpenHelper{

    protected static final String tbUser = "tbUser";
    protected static final String idUser = "idUser";
    protected static final String nameUser = "nameUser";
    protected static final String emailUser = "emailUser";
    protected static final String pwdUser = "pwdUser";

    //Gerado automaticamente, quando a classe herda o SQLiteOpenHelper
    public AcessDB(@Nullable Context context) {
        super(context, "userDB", null, 1);
    }

    // Comandos SQL para criação da tabela
    @Override
    public void onCreate(SQLiteDatabase db) {
        String statement = "CREATE TABLE " + tbUser + " (" +
        idUser + " integer primary key autoincrement, " +
        nameUser + " varchar(100), " +
        emailUser + " varchar(100), " +
        pwdUser + " varchar(100))";
        db.execSQL(statement);
    }

    //Método para adicionar usuário
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        //Pegando os dados do objeto User criado
        ContentValues contentValues = new ContentValues();
        contentValues.put(nameUser, user.getNameUser());
        contentValues.put(emailUser, user.getemailUser());
        contentValues.put(pwdUser, user.getpwdUser());

        long insertSucceded = db.insert(tbUser, null, contentValues);
        db.close();

        //-1 indica que nenhuma linha foi inserida
        // Então se o retorno for diferente de 1, a linha foi inserida e deu certo, retornando True
        return insertSucceded != -1;
    }

    //Método para remover usuário a partir de seu id
    public boolean removeUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString =
                "DELETE FROM " + tbUser + " WHERE " + idUser + " = " + user.getIdUser();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase(); //getWritableDatabase() permite a gravação em um banco de dados

        ContentValues contentValues = new ContentValues();
        contentValues.put(nameUser, user.getNameUser());
        contentValues.put(emailUser, user.getemailUser());
        contentValues.put(pwdUser, user.getpwdUser());
        //Por quê não tem o ID aqui? Porque o ID é tem incremento automático (AUTOINCREMENT). Veja a linha "CREATE TABLE..." dentro do método onCreate.

        long update = db.update(tbUser,
                contentValues,
                idUser + "=" + user.getIdUser(),
                null);

        db.close();

        //-1 indica que nenhuma linha foi inserida na referida tabela
        // Então se o retorno for diferente de 1, a linha foi inserida e deu certo, retornando True
        return update != -1;
    }

    // Lista com todos os elementos da tabela
    public List<User> getUserList(){
        List<User> userList = new ArrayList<>();

        String queryStatement = "SELECT * FROM "+ tbUser;

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(queryStatement, null)){
            if (cursor.moveToFirst()) {
                do {
                    int userId = cursor.getInt(0);
                    String nameUser = cursor.getString(1);
                    String emailUser = cursor.getString(2);
                    String pwdUser = cursor.getString(3);

                    User user = new User(userId, nameUser, emailUser, pwdUser);
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return userList;
    }
    //Gerado automaticamente, quando a classe herda o SQLiteOpenHelper
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
