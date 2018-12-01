package com.david.crisabel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference root;
    EditText stFName, stLName, stExam1, stExam2, stAve;
    ArrayList<String> keyList;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        root = db.getReference("grade");
        stFName = findViewById(R.id.sFName);
        stLName = findViewById(R.id.sLName);
        stExam1 = findViewById(R.id.sExam1);
        stExam2 = findViewById(R.id.sExam2);
        stAve = findViewById(R.id.sAve);
        keyList = new ArrayList<>();
    }

    @Override
    protected void onStart(){
        super.onStart();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ss: dataSnapshot.getChildren()){
                    keyList.add(ss.getKey());
                    //Toast.makeText(this,"aaaaaaaa",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void compAve(View v) {
        String fname = stFName.getText().toString().trim();
        String lname = stLName.getText().toString().trim();
        double exam1 = Double.parseDouble(stExam1.getText().toString().trim());
        double exam2 = Double.parseDouble(stExam2.getText().toString().trim());
        Student student = new Student(fname, lname, exam1, exam2);
        String key = root.push().getKey();
        root.child(key).setValue(student);
        keyList.add(key);


        Query query = root.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    stAve.setText(child.child("average").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });;

        Toast.makeText(this, "average computed!", Toast.LENGTH_LONG).show();
    }
}
