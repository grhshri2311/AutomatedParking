package com.luckylad.automatedParking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    AlertDialog alert;
    ImageView icon;
    TextView signin, welcome;
    Button login;
    Button go;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout mobile;
    TextInputLayout name;
    static TextInputLayout roleL;

    String email1, password1, phone1, name1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(register.this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("connecting...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        mobile = findViewById(R.id.mobile);
        name = findViewById(R.id.fname);
        login = findViewById(R.id.login);
        icon = findViewById(R.id.icon1);
        signin = findViewById(R.id.signin1);
        welcome = findViewById(R.id.welcome1);
        go = findViewById(R.id.go1);
        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);

        if (ContextCompat.checkSelfPermission(register.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(register.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(icon, "logo_image");
                pairs[1] = new Pair<View, String>(welcome, "logo_text");
                pairs[2] = new Pair<View, String>(signin, "logo_text1");
                pairs[3] = new Pair<View, String>(email, "email");
                pairs[4] = new Pair<View, String>(password, "pass");
                pairs[5] = new Pair<View, String>(go, "go");
                pairs[6] = new Pair<View, String>(login, "switch");

                //wrap the call in API level 21 or higher
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(register.this, pairs);
                    startActivity(intent, options.toBundle());
                }
                finish();
            }
        });

        
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(name, email, mobile, password)) {
                    name1 = name.getEditText().getText().toString();
                    email1 = email.getEditText().getText().toString();
                    phone1 = mobile.getEditText().getText().toString();
                    password1 = password.getEditText().getText().toString();
                    progressDialog.show();
                    verifyemail();
                }
            }
        });

    }


    public boolean validate(TextInputLayout name1, TextInputLayout email1, final TextInputLayout phone1, TextInputLayout password1) {
        String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPatter = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";


        if (name1.getEditText().getText().toString().isEmpty()) {
            name1.setError("\nName cannot be empty\n");
            return false;
        } else {
            name1.setError("");
            name1.setErrorEnabled(false);
        }


        if (!email1.getEditText().getText().toString().matches(emailPatter) && !email1.getEditText().getText().toString().isEmpty()) {
            email1.setError("\nEnter a valid email\n");
            return false;
        } else {
            email1.setError("");
            email1.setErrorEnabled(false);
        }

        if (password1.getEditText().getText().toString().isEmpty()) {
            password1.setError("\nPassword cannot be empty\n");
            return false;
        } else if (!password1.getEditText().getText().toString().matches(passwordPatter)) {
            password1.setError("\nPassword Must contain atleast\nOne Uppercase ,\nOne Lowercase ,\nOne Number ,\nOne Special character and \nBetween 8 to 16 letter length\n");
            return false;
        } else {
            password1.setError("");
            password1.setErrorEnabled(false);
        }
        if (phone1.getEditText().getText().toString().isEmpty()) {
            phone1.setError("\nMobile cannot be empty\n");
            return false;
        } else {
            phone1.setError("");
            phone1.setErrorEnabled(false);
        }


        return true;
    }


    void checkUser() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Users").child(phone1);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserRegistrationHelper helper = dataSnapshot.getValue(UserRegistrationHelper.class);
                if (helper != null) {
                    progressDialog.hide();
                    mobile.setError("Mobile number aldready exists");

                } else {

                    Bundle bundle = new Bundle();
                    bundle.putString("name", name1);
                    bundle.putString("phone", phone1);
                    bundle.putString("email", email1);
                    bundle.putString("pass", password1);


                    BottomSheetDialogFragment f = new Bottomsheetregisterfragment();
                    f.setArguments(bundle);
                    f.show(getSupportFragmentManager(), "Dialog");
                    progressDialog.hide();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.hide();
                Toast.makeText(register.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    void verifyemail() {

       
            checkUser();
    }


   
    @Override
    public void onBackPressed() {
        Exit1();
    }

    public void Exit1() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
