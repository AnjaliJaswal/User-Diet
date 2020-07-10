package com.example.userdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;
    TextView value;
    Button btn,btn2,btn3,btn4,btn5;
    String bmi_status,user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.plan);
        btn2=findViewById(R.id.bmi);
        btn3=findViewById(R.id.profile);
        btn4=findViewById(R.id.log_out);
        btn5=findViewById(R.id.feedbck);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Diet_plan.class);
                startActivity(intent);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
                View mView=getLayoutInflater().inflate(R.layout.my_dialog,null);
                mBuilder.setView(mView);
                value=mView.findViewById(R.id.value);
                final Button btnOk=mView.findViewById(R.id.btnclose);
                 final TextView txt=mView.findViewById(R.id.status);
                //Read the bmi value
                user= FirebaseAuth.getInstance().getCurrentUser();
                uid=user.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bmi_status   = (String) dataSnapshot.child(uid).child("BMI").getValue();
                        value.setText(bmi_status);

                        double xValue= Double.parseDouble(bmi_status);
                        if(xValue<18.5)
                        {
                            txt.setText("You are Under Weight");
                        }
                        else if(xValue<=18.5 ||xValue<=24.9)
                        {
                            txt.setText("You are Healthy");
                        }
                         else
                        {
                            txt.setText("You are Over Weight");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                final AlertDialog alertDialog = mBuilder.create();

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();


            }
        });
        btn3.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
            }
        } );


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                View mview=getLayoutInflater().inflate(R.layout.user_feedback,null);
                alertDialog.setView(mview);
                Button dismiss=mview.findViewById(R.id.dismiss);
                Button send=mview.findViewById(R.id.send);
                final EditText txt=mview.findViewById(R.id.msg);
                final AlertDialog dialog= alertDialog.create();

                user= FirebaseAuth.getInstance().getCurrentUser();
                uid=user.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user_name= (String) dataSnapshot.child(uid).child("User_name").getValue();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("Msg",txt.getText().toString());
                        map.put("User_name",user_name);

                        FirebaseDatabase.getInstance().getReference()
                                .child("Feedback").push()
                                .setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this, "Send..", Toast.LENGTH_LONG).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, ""+e.toString(), Toast.LENGTH_LONG).show();
                                        
                                    }
                                });
                                

                    }
                });



                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }) ;
                dialog.show();
            }
        });


    }


}
