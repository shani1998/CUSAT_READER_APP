package pro.himanshu.cusatreader;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

     public class Fupload extends AppCompatActivity implements View.OnClickListener {

         private static final int PICK_IMAGE_REQUEST = 234;
         private Button buttonChoose, buttonUpload;
         private ImageView imageView;


         private Uri filePath;
         private StorageReference storageReference;

         public Fupload() {
              }

        @Override
        protected void onCreate ( Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                storageReference = FirebaseStorage.getInstance().getReference();
                imageView = (ImageView) findViewById(R.id.imageView);
                buttonChoose = (Button) findViewById(R.id.buttonChoose);
                buttonUpload = (Button) findViewById(R.id.buttonUpload);

                buttonChoose.setOnClickListener(this);
                buttonUpload.setOnClickListener(this);
            }




        private void showfileChooser() {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select an pdf"), PICK_IMAGE_REQUEST);
        }

        private void uploadFile() {

            if (filePath != null) {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference riversRef = storageReference.child("documents/doc.pdf");

                riversRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "File uploaded", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage(((int) progress) + "% uploading...");
                            }
                        })
                ;
            }
            else
                {
                //display a error toast
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        @Override
        public void onClick(View view) {

            if (view == buttonChoose) {
                showfileChooser();
            }
            else if (view == buttonUpload) {
                uploadFile();

            }
        }
}