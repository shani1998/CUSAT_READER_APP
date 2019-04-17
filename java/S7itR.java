package pro.himanshu.cusatreader;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class S7itR extends AppCompatActivity {


    //the listview
    ListView listView;

    //database reference to get uploads data
    DatabaseReference mDatabaseReference;
    private Button bt,bt1;
    //list to store uploads data
    List<Upload> uploadList;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7it_r);



            uploadList = new ArrayList<>();
            listView = (ListView) findViewById(R.id.listView);


            //adding a clicklistener on listview
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //getting the upload
                    Upload upload = uploadList.get(i);

                    //Opening the upload file in browser using the upload url
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(upload.getUrl()));
                    startActivity(intent);
                }
            });

            bt1=(Button) findViewById(R.id.profile);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                }
            });


            //getting the database reference
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(ConstantS7it.DATABASE_PATH_UPLOADS);

            //retrieving upload data from firebase database
            ValueEventListener valueEventListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Upload upload = postSnapshot.getValue(Upload.class);
                        uploadList.add(upload);
                    }

                    String[] S7it = new String[uploadList.size()];

                    for (int i = 0; i < S7it.length; i++) {
                        S7it[i] = uploadList.get(i).getName();
                    }

                    //displaying it to list
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, S7it){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            TextView textView = (TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                            textView.setTextColor(Color.BLUE);

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            bt=(Button) findViewById(R.id.contribute);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),S7it.class));

                }
            });
        }
    }

