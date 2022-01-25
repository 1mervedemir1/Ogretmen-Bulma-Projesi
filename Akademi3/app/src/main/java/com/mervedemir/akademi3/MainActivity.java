package com.mervedemir.akademi3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textView;
    Button btnGiris, btnKaydol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        textView = findViewById(R.id.textView);
        btnGiris = findViewById(R.id.btnGiris);
        btnKaydol = findViewById(R.id.btnKaydol);

        FirebaseUser firebaseUser=auth.getCurrentUser();
        if(firebaseUser!=null){
            Intent intent=new Intent(MainActivity.this,AkisActivity.class);
            startActivity(intent);
            finish();
        }

        btnKaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent1);
            }
        });

    }

    public void giris_yap(View view){
        Intent intent=new Intent(MainActivity.this,SigninActivity.class);
        startActivity(intent);
    }
}