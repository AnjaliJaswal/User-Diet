package com.example.userdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText name,age,height,weight,phone_no,user_id, pass;
    Button reg;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=findViewById(R.id.name);
        age=findViewById(R.id.Age);
        height=findViewById(R.id.rheight);
        weight=findViewById(R.id.rweight);
        phone_no=findViewById(R.id.rphone);
        user_id = findViewById(R.id.mail);
        pass = findViewById(R.id.password);
        reg = findViewById(R.id.register);
        radioGroup = findViewById(R.id.radioGroup);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (TextUtils.isEmpty(user_id.getText().toString())) {
                    Toast.makeText(Registration.this, "Please Enter Email.address", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(user_id.getText().toString()).matches()) {
                    Toast.makeText(Registration.this, "Please Enter Valid Email.address",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass.getText().toString())) {
                    Toast.makeText(Registration.this, "Please Enter Password",
                            Toast.LENGTH_SHORT).show();
                } else if (pass.getText().toString().length() < 8) {
                    Toast.makeText(Registration.this, "Please Enter 8 & more then 8 Digit Password",
                            Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(Registration.this, "Please Enter name  :)",
                            Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(height.getText().toString())) {
                    Toast.makeText(Registration.this, "Please Enter height in cms ^_^",
                            Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(weight.getText().toString())) {
                    Toast.makeText(Registration.this, "Please Enter Weight",
                            Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone_no.getText().toString())) {
                    Toast.makeText(Registration.this, "Please Enter  Your phone no ;)",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    registeration();
                }
            }
        });


    }
    public void click(View v)
    {
           int radiobuttonid=radioGroup.getCheckedRadioButtonId();
           radioButton=findViewById(radiobuttonid);
           msg= (String) radioButton.getText();


    }
    private void registeration() {
        // here bmi calculated
        double w=Double.parseDouble(weight.getText().toString());
        double h=Double.parseDouble(height.getText().toString());
       // final double bmi=( w/h/h )*10000;
        final double bmi=((w)/(h*h));
        DecimalFormat df = new DecimalFormat("#.##");
        final String bmiValue = df.format(bmi);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user_id.getText
                ().toString(), pass.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                         Map<String,Object> map=new HashMap<>();
                         map.put("User_name",name.getText().toString());
                         map.put("Age",age.getText().toString());
                         map.put("Height",height.getText().toString());
                         map.put("weight",weight.getText().toString());
                         map.put("Phone_no",phone_no.getText().toString());
                         map.put("User_Mail",user_id.getText().toString());
                         map.put("password",pass.getText().toString());
                         map.put("BMI",bmiValue);
                     map.put("Gender", msg);

                        FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Registration.this, "registration successful",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(Registration.this,MainActivity.class);
                                        startActivity(intent);

                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registration.this, "Failed"+e.getMessage() ,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
