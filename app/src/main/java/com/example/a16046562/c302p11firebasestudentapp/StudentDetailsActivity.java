package com.example.a16046562.c302p11firebasestudentapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class StudentDetailsActivity extends AppCompatActivity {

    private static final String TAG = "StudentDetailsActivity";
    private EditText etName, etAge;
    private Button btnUpdate, btnDelete;
    private Student student;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studentListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        etName = (EditText)findViewById(R.id.editTextName);
        etAge = (EditText)findViewById(R.id.editTextAge);

        firebaseDatabase = FirebaseDatabase.getInstance();
        studentListRef = firebaseDatabase.getReference("/studentList");

        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("Student");

        //Display Student details as retrieved from the intent
        etName.setText(student.getName());
        etAge.setText(String.valueOf(student.getAge()));

        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Student record based on input given
                String id = student.getId();
                String name = etName.getText().toString();
                int age = Integer.parseInt(etAge.getText().toString());

                student.setId(null);
                student.setAge(age);
                student.setName(name);

                studentListRef.child(id).setValue(student);
                Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete = (Button)findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete Student record based on student id
                studentListRef.child(student.getId()).removeValue();

                Toast.makeText(getApplicationContext(), " Student record deleted successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
