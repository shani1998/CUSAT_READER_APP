package pro.himanshu.cusatreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText name;
     private Button save;
     private RadioGroup rgp,rgp1;
     private RadioButton rbt,rbt1;
    // private Button next;
     //private ProgressDialog progressDialog;
    private TextView textViewUserEmail;

     DatabaseReference databaseReference;

    @Override

    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        firebaseAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        rgp = (RadioGroup) findViewById(R.id.RGroup);
        rgp1 = (RadioGroup) findViewById(R.id.R1Group);
        save = (Button) findViewById(R.id.save);
        //next = (Button) findViewById(R.id.next);
       // progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Saving...");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        textViewUserEmail = (TextView) findViewById(R.id.Email);
        //displaying logged in user name
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail.setText("Welcome" +user.getEmail());
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AddData();
            }
        } );

    }
    public void AddData(){
        String Name = name.getText().toString().trim();
        //progressDialog.show( this );
        int selectedId = rgp.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        rbt = (RadioButton) findViewById(selectedId);
        int selectedId1 = rgp1.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        rbt1 = (RadioButton) findViewById(selectedId1);
        String s1 =rbt.getText().toString();
        String s2=rbt1.getText().toString();
        String s3="shani.pathak123@gmail.com";
        FirebaseUser user = firebaseAuth.getCurrentUser();
        s3=user.getEmail();
        SaveData saveData = new SaveData(Name,s1,s2,s3);
        databaseReference.push().setValue(saveData);
        //progressDialog.dismiss();
        Toast.makeText(getApplication(),"Saved Successfully",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ProfileActivity.class));



    }
}