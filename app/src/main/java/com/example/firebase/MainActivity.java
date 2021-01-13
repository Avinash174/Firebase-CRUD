package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText first_name, last_name, phone_no,delete;
    Button btn,del;
    ScrollView scrollView;
    int maxid = 0;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first_name = findViewById(R.id.fn);
        last_name = findViewById(R.id.ln);
        phone_no = findViewById(R.id.pn);
        delete=findViewById(R.id.delete);
        del=findViewById(R.id.del);
        btn = findViewById(R.id.send);
        scrollView = findViewById(R.id.scrollView);
        member = new Member();
        databaseReference = firebaseDatabase.getInstance().getReference().child("user");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = (int) snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setFist_name(first_name.getText().toString());
                member.setLast_name(last_name.getText().toString());
                member.setPhone_no(phone_no.getText().toString());
                databaseReference.child(String.valueOf(maxid + 1)).setValue(member);

            }

        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteID(maxid);
            }
        });

    }

    private void deleteID(int maxid) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("user").child(String.valueOf(maxid));
        databaseReference.removeValue();

    }
}