package com.example.userdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class Profile extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;
    EditText name,age,height,weight,phone_no,user_id, pass,gender;
    String user_name,user_Age,user_Height,user_weight,user_Gender,user_Phoneno,user_Mail,user_pass;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name =findViewById(R.id.Uname);
        age =findViewById(R.id.Uage);
        height =findViewById(R.id.Uheight);
        weight =findViewById(R.id.Uweight);
        phone_no =findViewById(R.id.Uphno);
        user_id =findViewById(R.id.Umail);
        pass =findViewById(R.id.Upass);
        gender =findViewById(R.id.Ugender);
        update=findViewById(R.id.update);

        //Here read the data from database
        user= FirebaseAuth.getInstance().getCurrentUser();
         uid=user.getUid();

          databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
          databaseReference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  user_name   = (String) dataSnapshot.child(uid).child("User_name").getValue();
                  user_Age = (String) dataSnapshot.child(uid).child("Age").getValue();
                  user_Height = (String) dataSnapshot.child(uid).child("Height").getValue();
                  user_weight = (String) dataSnapshot.child(uid).child("weight").getValue();
                  user_Gender = (String) dataSnapshot.child(uid).child("Gender").getValue();
                  user_Phoneno = (String) dataSnapshot.child(uid).child("Phone_no").getValue();
                  user_Mail = (String) dataSnapshot.child(uid).child("User_Mail").getValue();
                  user_pass = (String) dataSnapshot.child(uid).child("password").getValue();

                  name.setText(user_name);
                  age.setText(user_Age);
                  height.setText(user_Height);
                  weight.setText(user_weight);
                  gender.setText(user_Gender);
                  phone_no.setText(user_Phoneno);
                  user_id.setText(user_Mail);
                  pass.setText(user_pass);

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

            // here updation is performed
       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isNameChanged()|| isPasswordChanged()|| isHeightChanged()||isWeightChanged()||isPhonenoChanged()||isAgeChanged())
               {
                   bmiChanged();
                   Toast.makeText(Profile.this, "Data has been updated", Toast.LENGTH_LONG).show();
               }
           }

           private void bmiChanged() {
               double w=Double.parseDouble(weight.getText().toString());
               double h=Double.parseDouble(height.getText().toString());
               final double bmi=((w)/(h*h));

               DecimalFormat df = new DecimalFormat("#.##");
               String bmiValue = df.format(bmi);
               databaseReference.child(uid).child("BMI").setValue(bmiValue);
           }

           private boolean isAgeChanged() {
               if(!user_Age.equals(age.getText().toString()))
               {
                   databaseReference.child(uid).child("Age").setValue(age.getText().toString());
                   return true;
               }
               else
               {
                   return false;
               }
           }

           private boolean isPhonenoChanged() {
               if(!user_Phoneno.equals(phone_no.getText().toString()))
               {
                   databaseReference.child(uid).child("Phone_no").setValue(phone_no.getText().toString());
                   return true;
               }
               else {
                   return false;
               }
           }

           private boolean isWeightChanged() {
               if(!user_weight.equals(weight.getText().toString()))
               {
                   databaseReference.child(uid).child("weight").setValue(weight.getText().toString());
                   return true;
               }
               else
               {
                   return false;
               }

           }

           private boolean isHeightChanged() {
               if(!user_Height.equals(height.getText().toString()))
               {
                   databaseReference.child(uid).child("Height").setValue(height.getText().toString());
                   return true;
               }
               else
               {
                   return false;
               }
           }

           private boolean isPasswordChanged() {
               if(!user_pass.equals(pass.getText().toString()))
               {
                   databaseReference.child(uid).child("password").setValue(pass.getText().toString());
                   return true;
               }
               else
               {
                   return false;
               }

           }

           private boolean isNameChanged() {
               if(!user_name.equals(name.getText().toString()))
               {
                   databaseReference.child(uid).child("User_name").setValue(name.getText().toString());
                   return true;
               }
               else
               {
                   return false;
               }
           }

       });






    }
}
