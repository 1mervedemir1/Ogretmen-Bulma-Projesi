package com.mervedemir.akademi3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AkisActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    ArrayList<String> userEmailFromFB;
    ArrayList<String> userNameFromFB;
    //ArrayList<String> userSeviyeFromFB;
    ArrayList<String> userAlanFromFB;
    ArrayList<String> userImageFromFB;
    ArrayList<String> userOzgecmisFromFB;
    AkisRecyclerAdapter akisRecyclerAdapter;

    TextView textView7,textView9;
    Button yorum_gonder;
    EditText editText_yorumlar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  //menüyü bağlamak için

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //menüde bişey seçilirse yapılacaklar için
        if(item.getItemId()==R.id.cikis){
            auth.signOut();
            Intent intent=new Intent(AkisActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.profilim){
            Intent intent=new Intent(AkisActivity.this,ProfilActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akis);

        textView7=findViewById(R.id.textView7);
        textView9=findViewById(R.id.textView9);
        editText_yorumlar=findViewById(R.id.editText_yorumlar);
        yorum_gonder=findViewById(R.id.yorum_gonder);

        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userAlanFromFB=new ArrayList<>();
        userNameFromFB=new ArrayList<>();
        userEmailFromFB=new ArrayList<>();
        userImageFromFB=new ArrayList<>();
        //userSeviyeFromFB=new ArrayList<>();
        userOzgecmisFromFB=new ArrayList<>();

        getDataFromFirestore(); //verileri burada alıyoruz

        //yukarı bar da kullanıcı adını gösteriyoruz

        Intent intent=getIntent();
        String userInputName=intent.getStringExtra("userInputName");
        textView9.setText(userInputName);



        //Recylerview
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        akisRecyclerAdapter=new AkisRecyclerAdapter(userEmailFromFB,userNameFromFB,userAlanFromFB,userImageFromFB,userOzgecmisFromFB);
        recyclerView.setAdapter(akisRecyclerAdapter);
    }

    public void getDataFromFirestore(){

        //firebase de firestore un  referansını almaya yarıyor
        CollectionReference collectionReference=firebaseFirestore.collection("Users");

        //burada where dediğimiz kod bize filtreleyip sunuyor verileri whereEqualTo() şuna denk verileri getir demek
        //collectionReference.whereEqualTo(username, Phoebe)addSnapshotListener(new EventListener<QuerySnapshot>() {     şeklinde kullanılır
        // ama biz verileri date in güncelliğine göre sıralayacağız

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //bütün veriler snapshot içinde yüklü

                if(error!=null){
                    Toast.makeText(AkisActivity.this, error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if(value!=null){

                    for(DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String,Object> data= snapshot.getData();

                        //Casting (string) yazdık yani başlarına çünkü object demiştik
                        String userEmail=(String) data.get("userEmail");
                        String useralan=(String) data.get("useralan");
                        String username=(String) data.get("username");
                        //String userseviye=(String) data.get("userseviye");
                        String downloadUrl=(String) data.get("downloadUrl");
                        String userozgecmis=(String) data.get("userozgecmis");//şimdi bu verilerin hepsini recylerview de göstereceğiz

                        //System.out.println(username); isimleri yani verileri alabildik

                        userAlanFromFB.add(useralan);
                        userNameFromFB.add(username);
                        userEmailFromFB.add(userEmail);
                        userImageFromFB.add(downloadUrl);
                        //userSeviyeFromFB.add(userseviye);
                        userOzgecmisFromFB.add(userozgecmis); //bunları recylerview de gösterelim

                        akisRecyclerAdapter.notifyDataSetChanged(); //bişeyi ekledikten sonra içeri yeni veri geldi diye adapterı uyarır

                    }
                }
            }
        });
    }
}