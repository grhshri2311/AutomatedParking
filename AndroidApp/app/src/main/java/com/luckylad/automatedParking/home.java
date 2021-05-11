package com.luckylad.automatedParking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = getApplicationContext().getSharedPreferences("user", 0); // 0 - for private mode
        editor = pref.edit();

        FirebaseDatabase.getInstance().getReference().child("Users").child(pref.getString("user","as")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot!=null && snapshot.getValue()!=null){
                    UserRegistrationHelper u = snapshot.getValue(UserRegistrationHelper.class);
                    TextView t= (TextView)findViewById(R.id.confirm);
                    t.setText("Balance : "+u.getBalance());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });



    }

    public void addFace(View view) {
        Intent  intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("url","add_face");
        startActivity(intent);
    }

    public void addAcess(View view) {
        Intent  intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("url","add_access");
        startActivity(intent);
    }

    public void mySlot(View view) {
        Intent  intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("url","reserve?name="+pref.getString("user",""));
        startActivity(intent);
    }

    public void logout(View view) {


        editor.putString("user", "");
        editor.apply();

        startActivity(new Intent(home.this, login.class), ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle());
        finish();
    }

    public void history(View view) {
        Intent  intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("url","history?name="+pref.getString("user",""));
        startActivity(intent);
    }

    public void payment(View view) {
        Intent  intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("url","payment?name="+pref.getString("user",""));
        startActivity(intent);
    }
}