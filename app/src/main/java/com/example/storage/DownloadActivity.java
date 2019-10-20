package com.example.storage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.storage.util.Helper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.layout.simple_list_item_1;

public class DownloadActivity extends AppCompatActivity {

    ListView mypdflistview;
    DatabaseReference databaseReference;
    List<uploadpdf> uploadpdfs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mypdflistview=(ListView)findViewById(R.id.mylistview);
        uploadpdfs=new ArrayList<>();

        viewallfiles();

        mypdflistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uploadpdf uploadpdf=uploadpdfs.get(position);
                Intent intent=new Intent();

                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadpdf.getUrl()));
                startActivity(intent);

            }
        });




    }

    private void viewallfiles() {
       databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
               {
                   uploadpdf uploadpdf=postSnapshot.getValue(com.example.storage.uploadpdf.class);
                   uploadpdfs.add(uploadpdf);

               }
               String[] uploads=new String[uploadpdfs.size()];

               for(int i=0;i<uploadpdfs.size();i++) {
                   uploads[i] = uploadpdfs.get(i).getName();
               }
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), simple_list_item_1,uploads){

                   @Override
                   public View getView(int position, View convertView, ViewGroup parent) {

                       View view=super.getView(position, convertView, parent);

                       TextView mytext=(TextView) view.findViewById(android.R.id.text1);
                        mytext.setTextColor(Color.BLACK);

                       return view;
                   }
               };
               mypdflistview.setAdapter(adapter);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

}

