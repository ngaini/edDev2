package edu.scu.mapproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SignUpPage3 extends Activity {

    private String uFullName, uPhoneNumber, uPassword, uAge, uEmailID, uDegreeList, uExpertiseList, userID, uGender;
    private int uRole;
    private double lat1;
    private double lng1;
    private ImageView profilePhoto;
    private EditText description;
    private Button confirm;
    private Button back;
    private Button uploadPhoto;
    private String profilePhotoName;
    Firebase mref, newUserRef;
    private String uDescription;
    Intent cameraIntent;
    File externalDirectory;
    String imgName;
    Uri uri;
    static String imgPath;
    public static final int IMAGE_IDENTIFIER = 10;
    BitmapFactory.Options options;
    Bitmap b;
    byte[] bytes;
    String base64Image;
    String uImage;
    String userStatement;
    UserSessionManager session;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page3);

        profilePhoto = (ImageView) findViewById(R.id.imageView);
        profilePhotoName = String.valueOf(profilePhoto.getTag());
        description = (EditText) findViewById(R.id.description);
        confirm = (Button) findViewById(R.id.userConfirm);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        session = new UserSessionManager(getApplicationContext());
//        newUserRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
        back = (Button) findViewById(R.id.userBack);
        uploadPhoto = (Button) findViewById(R.id.uploadPhoto);
        Bundle extras = getIntent().getExtras();
        uRole = extras.getInt("userRole");
        uFullName = extras.getString("uFullName");
        uPhoneNumber = extras.getString("uPhoneNumber");
        uPassword = extras.getString("uPassword");
        uAge = extras.getString("uAge");
        uEmailID = extras.getString("uEmailID");
        uDegreeList = extras.getString("uDegreeList");
        uExpertiseList = extras.getString("uExpertiseList");
        uGender = extras.getString("uGender");
        userID = extras.getString("userID");
        lat1 = extras.getDouble("uLat");
        lng1 = extras.getDouble("uLng");
        Toast.makeText(this, "phototag is - "+profilePhotoName, Toast.LENGTH_LONG).show();
        if(uRole == 1)
        {
            userStatement = "Student";
            newUserRef = mref.child("users").child(userStatement).child(userID);
            Toast.makeText(SignUpPage3.this, "url is - "+newUserRef, Toast.LENGTH_LONG).show();

        }else{
            userStatement = "Tutor";
            newUserRef = mref.child("users").child(userStatement).child(userID);
            Toast.makeText(SignUpPage3.this, "url is - "+newUserRef, Toast.LENGTH_LONG).show();
        }

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    externalDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imgName = "TEST"+time;
                    File imgFile = new File(externalDirectory, imgName + ".jpg");
                    uri = Uri.fromFile(imgFile);
                    imgPath = imgFile.getAbsolutePath();
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    profilePhoto.setTag("uploaded");
                    startActivityForResult(cameraIntent, IMAGE_IDENTIFIER);
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profilePhoto.getTag().equals("default")) {
                    Toast.makeText(SignUpPage3.this, "Please Upload Your Photo", Toast.LENGTH_LONG).show();
                } else if (description.getText().toString().trim().isEmpty()) {
                    description.setError("Invalid Input");
                } else {
                    uDescription = description.getText().toString().trim();
                    uname = uEmailID.toString().trim();
                    uImage = base64Image.toString().trim();
                    mref.createUser(uEmailID, uPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {
                            Users newUser = new Users(userID, uRole, uFullName, uAge, uEmailID, uPhoneNumber, uPassword, uDegreeList, uDescription, uGender, uExpertiseList, lat1, lng1, uImage);
                            newUserRef.setValue(newUser);
                            session.createUserLoginSession("session stored", uname);
                            Toast.makeText(SignUpPage3.this, "THANK YOU for Signup. Please Login to Start"+uname, Toast.LENGTH_LONG).show();
                            Intent mainPage = new Intent(SignUpPage3.this, Login.class);
                            startActivity(mainPage);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                        }
                    });

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lastPage = new Intent(SignUpPage3.this, SignUpPage2.class);
                startActivity(lastPage);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_IDENTIFIER) {
            if (resultCode == RESULT_OK) {

                options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                b = BitmapFactory.decodeFile(imgPath, options);
                profilePhoto = (ImageView) findViewById(R.id.imageView);
                //Display the captured image inside the ImageView
                profilePhoto.setImageBitmap(b);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bytes = baos.toByteArray();
                base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                Toast.makeText(SignUpPage3.this, "DONE" ,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
