package com.mervedemir.akademi3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ProfilActivity extends AppCompatActivity {

    Bitmap selectedImage;
    TextView textView4,textView5,textView6,textView7,textView8;
    ImageView imageView;
    EditText editTextUserName,editTextUserSeviye,editTextUserAlan,editTextOzgecmis;
    Uri imageData;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        textView6=findViewById(R.id.textView6);
        //textView7=findViewById(R.id.textView7);
        textView8=findViewById(R.id.textView8);
        imageView=findViewById(R.id.imageView);
        editTextUserName=findViewById(R.id.editTextUserName);
        //editTextUserSeviye=findViewById(R.id.editTextUserSeviye);
        editTextUserAlan=findViewById(R.id.editTextUserAlan);
        editTextOzgecmis=findViewById(R.id.editTextOzgecmis);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    public void foto_yukle(View view){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }

        /*
        Intent intent=new Intent(ProfilActivity.this,UploadActivity.class);
        startActivity(intent);
        */
    }


    public void kaydet(View view){



       /* if(editTextUserName==null || editTextUserAlan==null || editTextOzgecmis==null) {

            Toast.makeText(ProfilActivity.this,"Lütfen Boş Alanları Doldurun!".toString(),Toast.LENGTH_LONG).show();
            break;
        }*/

        if(editTextUserName != null && editTextUserAlan!= null && imageData!=null && editTextOzgecmis!= null ){

                //universel unique id UUId  kendine has bir uydurma id çıkaracak
                UUID uuid=UUID.randomUUID();
                final String imagename="images/" + uuid+ ".jpg";  //dosya konumu vs

                //child klasör içine referans verir
                storageReference.child(imagename).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //başarılı kaydedilirse download url yi alacağız burada
                        Toast.makeText(ProfilActivity.this,"Lütfen Bekleyin...",Toast.LENGTH_SHORT).show();
                        StorageReference newReferance=FirebaseStorage.getInstance().getReference(imagename); //benim upload ettiğim image in yerini bul
                        newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl=uri.toString();
                                //System.out.println(downloadUrl); logda url çıkar

                                //şimdi emaili falan alacağım ama önce kullanıcıyı alalım

                                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

                                String userEmail=firebaseUser.getEmail();
                                String username=editTextUserName.getText().toString();
                                //String userseviye=editTextUserSeviye.getText().toString();
                                String useralan=editTextUserAlan.getText().toString();
                                String userozgecmis=editTextOzgecmis.getText().toString();

                                HashMap<String,Object> usersData=new HashMap<>();  //firebase e ekliyor bu kısım
                                usersData.put("username",username);
                                usersData.put("userEmail",userEmail);
                                //usersData.put("userseviye",userseviye);
                                usersData.put("useralan",useralan);
                                usersData.put("date", FieldValue.serverTimestamp()); //güncel tarihi alır serverda
                                usersData.put("downloadUrl",downloadUrl);
                                usersData.put("userozgecmis",userozgecmis);


                                firebaseFirestore.collection("Users").add(usersData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Intent intent=new Intent(ProfilActivity.this,AkisActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //açık tüm aktiviteleri kapat dedik
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfilActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfilActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

            }
            else {
                Toast.makeText(ProfilActivity.this,"Lütfen Boş Alanları Doldurunuz!".toString(),Toast.LENGTH_LONG).show();
            }
        }/*
        else {
            Toast.makeText(ProfilActivity.this,"Lütfen Boş Alanları Doldurunuz!".toString(),Toast.LENGTH_LONG).show();
        }*/



    @Override  //istenilen izinlerin sonucu ne oldu
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //1 defaya mahsus çağırılır

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override //baslatılan aktivitenin sonucu
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==2 && resultCode==RESULT_OK && data != null){

            imageData=data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28){
                    ImageDecoder.Source source=ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage=ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                }

                selectedImage=MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                imageView.setImageBitmap(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}