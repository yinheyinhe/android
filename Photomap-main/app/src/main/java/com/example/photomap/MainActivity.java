package com.example.photomap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    private Button register;
    private EditText username;
    private EditText password;
    private DBHelper mHelper;
    private SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Init();
        mHelper = new DBHelper(this);
        mDatabase = mHelper.getWritableDatabase();
    }
    private void Init(){
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                register();
                break;
            case R.id.login:
                login();
                break;
            default:
                break;
        }
    }
    private void register(){
        ContentValues values = new ContentValues();
        values.put(DBHelper.USERNAME, username.getText().toString());
        values.put(DBHelper.PASSAGE, password.getText().toString());
        mDatabase.insert(DBHelper.TABLE_NAME, null, values);
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }
    private void login(){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_NAME,
                new String[]{DBHelper.USERNAME, DBHelper.PASSAGE},
                DBHelper.PASSAGE + " > ?",
                new String[]{"16"},
                null,
                null,
                DBHelper.PASSAGE + " desc");// 注意空格！

        int nameIndex = cursor.getColumnIndex(DBHelper.USERNAME);
        int ageIndex = cursor.getColumnIndex(DBHelper.PASSAGE);
        while (cursor.moveToNext()) {
            String Name = cursor.getString(nameIndex);
            String Password = cursor.getString(ageIndex);
            if(Name.equals(username.getText().toString())){
                if(Password.equals(password.getText().toString())){
                    Intent intent=new Intent(MainActivity.this,main.class);
                    startActivity(intent);
                }
            }

        }
    }

}
