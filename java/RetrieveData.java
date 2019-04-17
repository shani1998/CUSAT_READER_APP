package pro.himanshu.cusatreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RetrieveData extends AppCompatActivity {
    private DatabaseReference mdb;
    //private TextView name;
    private ListView ulist;
    private ArrayList<String> A= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);
        mdb= FirebaseDatabase.getInstance().getReference();
        ulist=(ListView) findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,A);
        ulist.setAdapter(arrayAdapter);



        mdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                A.add(value);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*mdb= FirebaseDatabase.getInstance().getReference().child("User");
        name=(TextView) findViewById(R.id.name_view);
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user=dataSnapshot.getValue().toString();
                name.setText("Name:"+user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
}
