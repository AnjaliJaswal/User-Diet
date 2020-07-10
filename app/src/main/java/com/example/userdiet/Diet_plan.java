package com.example.userdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static java.lang.String.*;

public class Diet_plan extends AppCompatActivity {
    TextView fast,bmi,status,fast2,lunch,lunch2,snacks,dinner,dinner2,dinner3;
    private DatabaseReference  databaseReference;
    FirebaseUser user;
    String uid;
    String  bmi_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);
        fast =findViewById(R.id.fast);
        bmi=findViewById(R.id.bmi);
        status=findViewById(R.id.status);
        fast2 =findViewById(R.id.fast2);
        lunch=findViewById(R.id.lunch);
        lunch2=findViewById(R.id.lunch2);
        snacks=findViewById(R.id.snacks);
        dinner=findViewById(R.id.dinner);
        dinner2=findViewById(R.id.dinner2);
        dinner3=findViewById(R.id.dinner3);



        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener()
        {@Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                bmi_status   = (String) dataSnapshot.child(uid).child("BMI").getValue();
                bmi.setText(bmi_status);

                double xValue= Double.parseDouble(bmi_status);
                if(xValue<18.5)
                {
                    status.setText("Under Weight");
                    isUnderWeight();
                }
                else if(xValue<=18.5 ||xValue<=24.9)
                {
                    status.setText("Healthy");
                    isHealthy();
                }
                else
                {
                    status.setText("Over Weight");
                    isOverWeight();
                }
             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                }
        });
    }




    private void isUnderWeight() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int pvalue=0;
                for(DataSnapshot ds :dataSnapshot.getChildren())
                {
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();
                    Object value=map.get("Food_name");
                    Object x=map.get("Consumed_By");
                    Object y=map.get("Consumed_For");
                    String tempString = (String) value;
                    String consumed_by = (String) x;
                    String consumed_for = (String) y;
                     //Breakfast
                    if(consumed_by.equals("Under Weight,Healthy"))
                    {
                        if((consumed_for.equals("BreakFast"))){
                            fast.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy"))
                    {
                        if((consumed_for.equals("BreakFast,Dinner"))){
                            fast2.setText(tempString);
                        }
                    }

                    //Lunch
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Lunch"))){
                            lunch.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy"))
                    {
                        if((consumed_for.equals("Lunch"))){
                            lunch2.setText(tempString);
                        }
                    }

                    //snacks
                    if(consumed_by.equals("Under Weight,Healthy"))
                    {
                        if((consumed_for.equals("Evening snacks"))){
                            snacks.setText(tempString);
                        }
                    }

                // Dinner
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Dinner"))){
                            dinner.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Lunch,Dinner"))){
                            dinner2.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Dinner3"))){
                            dinner3.setText(tempString);
                        }
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isHealthy() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int pvalue=0;
                for(DataSnapshot ds :dataSnapshot.getChildren())
                {
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();
                    Object value=map.get("Food_name");
                    Object x=map.get("Consumed_By");
                    Object y=map.get("Consumed_For");
                    String tempString = (String) value;
                    String consumed_by = (String) x;
                    String consumed_for = (String) y;
                    //Breakfast
                    if(consumed_by.equals("Under Weight,Healthy"))
                    {
                        if((consumed_for.equals("BreakFast"))){
                            fast.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("BreakFast"))){
                            fast2.setText(tempString);
                        }
                    }

                    //Lunch
                    if(consumed_by.equals("Healthy"))
                    {
                        if((consumed_for.equals("Lunch"))){
                            lunch.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Lunch,Dinner"))){
                            lunch2.setText(tempString);
                        }
                    }

                    //snacks
                    if(consumed_by.equals("Under Weight,Healthy"))
                    {
                        if((consumed_for.equals("Evening snacks,Dinner"))){
                            snacks.setText(tempString);
                        }
                    }

                    // Dinner
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Dinner"))){
                            dinner.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Healthy"))
                    {
                        if((consumed_for.equals("Dinner"))){
                            dinner2.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Dinner3"))){
                            dinner3.setText(tempString);
                        }
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void isOverWeight() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int pvalue=0;
                for(DataSnapshot ds :dataSnapshot.getChildren())
                {
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();
                    Object value=map.get("Food_name");
                    Object x=map.get("Consumed_By");
                    Object y=map.get("Consumed_For");
                    String tempString = (String) value;
                    String consumed_by = (String) x;
                    String consumed_for = (String) y;
                    //Breakfast
                    if(consumed_by.equals("Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("BreakFast"))){
                            fast.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Over Weight"))
                    {
                        if((consumed_for.equals("BreakFast,Dinner"))){
                            fast2.setText(tempString);
                        }
                    }

                    //Lunch
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Lunch"))){
                            lunch.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Lunch"))){
                            lunch2.setText(tempString);
                        }
                    }

                    //snacks
                    if(consumed_by.equals("Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Evening snacks"))){
                            snacks.setText(tempString);
                        }
                    }

                    // Dinner
                    if(consumed_by.equals("Over Weight"))
                    {
                        if((consumed_for.equals("Dinner"))){
                            dinner.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Dinner"))){
                            dinner2.setText(tempString);
                        }
                    }
                    if(consumed_by.equals("Under Weight,Healthy,Over Weight"))
                    {
                        if((consumed_for.equals("Dinner3"))){
                            dinner3.setText(tempString);
                        }
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
