package com.mervedemir.akademi3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AkisRecyclerAdapter extends RecyclerView.Adapter<AkisRecyclerAdapter.UserHolder> {

    private ArrayList<String > userEmailList;
    private ArrayList<String > userNameList;
    //private ArrayList<String > userSeviyeList;
    private ArrayList<String > userAlanList;
    private ArrayList<String > userImageList;
    private ArrayList<String > userOzgecmisList; //bunlar benim sınıfımın alacakları propertyler

    public AkisRecyclerAdapter(ArrayList<String> userEmailList, ArrayList<String> userNameList, ArrayList<String> userAlanList, ArrayList<String> userImageList, ArrayList<String> userOzgecmisList) {
        this.userEmailList = userEmailList;
        this.userNameList = userNameList;
        //this.userSeviyeList = userSeviyeList;
        this.userAlanList = userAlanList;
        this.userImageList = userImageList;
        this.userOzgecmisList = userOzgecmisList;
    }

    @NonNull
    @Override //
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //viewholder oluşturunca ne yapacağız

         //biz bir xml oluşturup onu kodla bağlarken inflater kullanırız. Burada da layout inflater kullanacağız

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recyler_row,parent,false); //burada false roota bağlama işlemi

        return new UserHolder(view); //bu return bizim oluşturduğumuz row ile bağlama işlemini yapacak
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) { //viewholdera bağlanınca ne yapacağız

        holder.userEmailText.setText(userEmailList.get(position)); //biz hangi pozisyondaysak yani ilk rowdaysak mesela 0 verir
        holder.userNameText.setText(userNameList.get(position));
        //holder.userSeviyeText.setText(userSeviyeList.get(position));
        holder.userAlanText.setText(userAlanList.get(position));
        holder.userOzgecmisText.setText(userOzgecmisList.get(position));
        Picasso.get().load(userImageList.get(position)).into(holder.imageView);


    }

    @Override
    public int getItemCount() {  //bizim recylerview da kaç tane row olacak onu yaz
        return userEmailList.size(); //1 tanesini vermen yeter
    }

    class UserHolder extends RecyclerView.ViewHolder{ // row da tanımladığın görünümleri buraya tanımlarsın

        ImageView imageView;
        TextView userEmailText;
        TextView userNameText;
        //TextView userSeviyeText;
        TextView userAlanText;
        TextView userOzgecmisText;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.recylerview_row_imageview);
            userEmailText=itemView.findViewById(R.id.recylerview_row_userEmail_text);
            userNameText=itemView.findViewById(R.id.recylerview_row_username_text);
            //userSeviyeText=itemView.findViewById(R.id.recylerview_row_userseviye_text);
            userAlanText=itemView.findViewById(R.id.recylerview_row_useralan_text);
            userOzgecmisText=itemView.findViewById(R.id.recylerview_row_userOzgecmis_text);

        }
    }
}
