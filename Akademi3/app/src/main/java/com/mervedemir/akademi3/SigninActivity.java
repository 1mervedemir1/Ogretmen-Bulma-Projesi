package com.mervedemir.akademi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    TextView textView2;
    Button button_Giris;
    EditText editText_Mail,editText_Sifre,editText_kullaniciadi;
    FirebaseAuth auth;
    ImageView imageView2;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        auth=FirebaseAuth.getInstance();
        textView2=findViewById(R.id.textView2);
        button_Giris=findViewById(R.id.button_Giris);
        editText_Mail=findViewById(R.id.editText_Mail);
        editText_Sifre=findViewById(R.id.editText_Sifre);
        editText_kullaniciadi=findViewById(R.id.editText_kullaniciadi);
        imageView2=findViewById(R.id.imageView2);
        checkBox=findViewById(R.id.checkBox);


    }


    public void giris(View view){
        final String email=editText_Mail.getText().toString();
        final String password=editText_Sifre.getText().toString();
        final String kullaniciadi=editText_kullaniciadi.getText().toString();
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent=new Intent(SigninActivity.this,AkisActivity.class);
                //yukarı bar da adını göstereceğim

                intent.putExtra("userInputName",kullaniciadi);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(SigninActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SigninActivity.this,"Şifre alanı boş bırakılamaz!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(SigninActivity.this,"E-Mail alanı boş bırakılamaz!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}