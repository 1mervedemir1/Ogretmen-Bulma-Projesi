package com.mervedemir.akademi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextmail,editTextpassword,kullaniciadi;
    TextView textView3;
    Button btnkaydet;
    FirebaseAuth auth;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        editTextmail=findViewById(R.id.editTextmail);
        editTextpassword=findViewById(R.id.editTextpassword);
        kullaniciadi=findViewById(R.id.kullaniciadi);
        textView3=findViewById(R.id.textView3);
        btnkaydet=findViewById(R.id.btnkaydet);
        imageView3=findViewById(R.id.imageView3);

    }

    public void save(View view){
        String email=editTextmail.getText().toString();
        String password=editTextpassword.getText().toString();
        //Log.e("AA","Save");
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email alanı boş bırakılamaz!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Şifre alanı boş bırakılamaz!",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<6){
            Toast.makeText(this,"Şifre 6 karakterden küçük olamaz!",Toast.LENGTH_SHORT).show();
        }
        else{

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Kayıt oluşturuldu! Lütfen giriş yapınız.. ",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,SigninActivity.class);
                                startActivity(intent);
                                finish();
                                //Log.e("durum", "kayit edildi");
                            }else{
                                //Log.e("ss", task.getException().toString());
                                Toast.makeText(RegisterActivity.this,"Kayıt Hatası! ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

}