package ma.emsi.my_quizzer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import ma.emsi.my_quizzer.Controllers.Firebase_Controller;

public class Register extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static String Login, Password, checkPassword, currentPhotoPath, imgName;
    static StorageReference firebaseStorage = FirebaseStorage.getInstance ().getReference ("Users");
    static Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.register);

        // Declarations

        Button b_Login = findViewById (R.id.Reg_B_Login),
                b_SignUp = findViewById (R.id.Reg_B_SingUp),
                b_Avatar = findViewById (R.id.bAvatar);

        EditText et_Login = findViewById (R.id.Reg_ET_Login),
                et_Password = findViewById (R.id.Reg_ET_Pswd),
                et_ChkPassword = findViewById (R.id.Reg_ET_CPswd);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions (new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        // Buttons Click-Events

        ////1.Button Login Click-Event
        b_Login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                moveToLoginPage (v);
            }
        });

        ////2.Button SignUp Click-Event
        b_SignUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getEditTextsValues (et_Login, et_Password, et_ChkPassword);

                if (checkIfEditTextAreEmpty ()) {
                    Toast.makeText (v.getContext (), "Values entered not valid!", Toast.LENGTH_SHORT).show ();

                } else {
                    if (Password.equals (checkPassword)) {
                        Firebase_Controller.RegisterToFirebase (Register.this, v, Login, Password);
                        uploadImgToFirebase ();
                        startActivity (new Intent (v.getContext (), Quizzes.class));
                    } else
                        Toast.makeText (v.getContext (), "These Passwords does not match. Try Again!", Toast.LENGTH_SHORT).show ();
                }

            }
        });

        ////3.Button Take-Photo Click-Event
        b_Avatar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getEditTextsValues (et_Login, et_Password, et_ChkPassword);
                if (et_Login.getText ().toString ().trim ().isEmpty ()) {
                    Toast.makeText (v.getContext (), "Enter Login first!", Toast.LENGTH_SHORT).show ();
                } else {
                    captureImage (et_Login);
                }

            }

        });


    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        de.hdodenhof.circleimageview.CircleImageView ivAvatar = findViewById (R.id.ivAvatar);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile (currentPhotoPath);
            ivAvatar.setImageBitmap (bitmap);
        }
    }

    private void getEditTextsValues(EditText et_Login, EditText et_Password, EditText et_checkPassword) {
        Login = et_Login.getText ().toString ();
        Password = et_Password.getText ().toString ();
        checkPassword = et_checkPassword.getText ().toString ();
    }

    private boolean checkIfEditTextAreEmpty() {
        return Login.isEmpty () || Password.isEmpty () || checkPassword.isEmpty ();
    }

    public void moveToLoginPage(View v) {
        startActivity (new Intent (v.getContext (), Login.class));
    }

    public void captureImage(EditText etLogin) {
        if (etLogin.getText ().toString ().trim ().isEmpty ()) {
            Toast.makeText (this, "Enter Login first!", Toast.LENGTH_SHORT).show ();
        } else {

            imgName = etLogin.getText ().toString ().split ("@")[0];
            Intent takePictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity (getPackageManager ()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                photoFile = createPhotoFile (imgName);
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile (this,
                                                               "com.example.android.fileprovider",
                                                               photoFile);
                    takePictureIntent.putExtra (MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult (takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    imgUri = photoURI;
                }
            }


        }


    }

    private File createPhotoFile(String name) {
        String imageName = name;
        String imageFileName = "JPEG_" + imageName + "_";
        File storageDir = getExternalFilesDir (Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile (
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException ioException) {
            ioException.printStackTrace ();
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath ();
        return image;
    }


    public void uploadImgToFirebase() {
        if (currentPhotoPath != null) {
            firebaseStorage = firebaseStorage.child (imgName);
            firebaseStorage.putFile (imgUri)
                    .addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText (Register.this, "Added Successfully!", Toast.LENGTH_SHORT).show ();
                        }
                    });
            ma.emsi.my_quizzer.Login.setLoginAndPasswordValues (Login, Password);

        } else
            Toast.makeText (Register.this, "Image not uploaded!", Toast.LENGTH_SHORT).show ();

    }


}
