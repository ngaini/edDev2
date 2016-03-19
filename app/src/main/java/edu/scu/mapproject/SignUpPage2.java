package edu.scu.mapproject;

import android.content.ContentValues;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class SignUpPage2 extends AppCompatActivity{

    private RadioButton male;
    private RadioButton female;
    private RadioGroup genderGroup;
    private Spinner degreeList;
    private Spinner expertiseList;
    private EditText description;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText pincode;
    private TextView areaOfInterest;
    private int uRole;
    private String uFullName;
    private String uAge;
    private String uPhoneNumber;
    private String uPassword;
    private String uEmailID;
    private Button imageButton;
    private Button back;
    private Button confirm;
    private String uCity;
    private String uState;
    private String uGender;
    private String uDegreeList;
    private String uExpertiseList;
    private String uDescription;
    private String uAddress;
    private ImageView image;
    private String uPincode;
    private String userID;
    boolean flagCapButton = false;
    File imageFile;
    static String imagePath;
    String imageName;
    public static final int IMAGE_CAPTURE_IDENTIFIER = 1;
    Bitmap currentImage;
    ImageView imgView;
    Intent curPhotoIntent;
    File externalPictureDirectory;
    Uri imageUri;
    String imageCaption;
    ContentValues cv;
    Firebase mref;
    Bitmap userImage;
    String uImage;
    String getImageString;
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private String userStatement;
    Firebase newUserRef;
    private static String uCountry;
    Geocoder geocoder;
    Address address1;
    double longitude, latitude;
    String constructedAddress;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        male = (RadioButton) findViewById(R.id.rd_Male);
        female = (RadioButton) findViewById(R.id.rd_Female);
        genderGroup = (RadioGroup) findViewById(R.id.rg_gender);
        degreeList = (Spinner) findViewById(R.id.spinner_Education);
        expertiseList = (Spinner) findViewById(R.id.spinner_Interest);
        back = (Button) findViewById(R.id.back);
        confirm = (Button) findViewById(R.id.confirm);
        address = (EditText) findViewById(R.id.txt_Address);
        pincode = (EditText) findViewById(R.id.txt_PinCode);
        city = (EditText) findViewById(R.id.txt_City);
        state = (EditText) findViewById(R.id.txt_State);
        areaOfInterest = (TextView) findViewById(R.id.txt_Expertise);
        geocoder = new Geocoder(this, Locale.getDefault());
        Bundle extras = getIntent().getExtras();
        uRole = extras.getInt("userRole");
        uFullName = extras.getString("uFullName");
        uPhoneNumber = extras.getString("uPhoneNumber");
        uPassword = extras.getString("uPassword");
        uAge = extras.getString("uAge");
        uEmailID = extras.getString("uEmailID");
        userID = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if(uRole == 1)
        {
            areaOfInterest.setText("I need Tutor for ");
            userStatement = "Student";
        }else{
            areaOfInterest.setText("Expertise ");
            userStatement = "Tutor";
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpPage2.this, "button clicked!!", Toast.LENGTH_LONG).show();
                if (genderGroup.getCheckedRadioButtonId() == male.getId()) {
                    uGender = "male";
                } else {
                    uGender = "female";
                }
                uDegreeList = degreeList.getSelectedItem().toString();
                uExpertiseList = expertiseList.getSelectedItem().toString();
                uAddress = address.getText().toString().trim();
                uPincode = pincode.getText().toString().trim();
                uCity = city.getText().toString().trim();
                uState = state.getText().toString().trim();
                if (uAddress.isEmpty()) {
                    address.setError("Invalid Input");
                } else if (uPincode.isEmpty()) {
                    pincode.setError("Invalid Input");
                } else if (uCity.isEmpty()) {
                    city.setError("Invalid Input");
                } else if (uState.isEmpty()) {
                    state.setError("Invalid Input");
                } else {
                    constructedAddress = uAddress + " " + uCity + " " + uState + " " + uPincode;
                    try {
                        addresses = geocoder.getFromLocationName(constructedAddress,1);
                        address1 = addresses.get(0);
                        longitude = address1.getLongitude();
                        latitude = address1.getLatitude();
                        Toast.makeText(SignUpPage2.this, "latitude is - "+latitude, Toast.LENGTH_LONG).show();
                        Toast.makeText(SignUpPage2.this, "longitude is - "+longitude, Toast.LENGTH_LONG).show();
                        Intent mainPage = new Intent(SignUpPage2.this, SignUpPage3.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("userRole", uRole);
                        bundle.putString("uAge", uAge);
                        bundle.putString("uPhoneNumber", uPhoneNumber);
                        bundle.putString("uFullName", uFullName);
                        bundle.putString("uPassword", uPassword);
                        bundle.putString("uEmailID", uEmailID);
                        bundle.putString("uDegreeList", uDegreeList);
                        bundle.putString("uExpertiseList", uExpertiseList);
                        bundle.putDouble("uLat", latitude);
                        bundle.putDouble("uLng", longitude);
                        bundle.putString("userID", userID);
                        bundle.putString("uGender", uGender);
                        mainPage.putExtras(bundle);
                        startActivity(mainPage);
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstPage = new Intent(SignUpPage2.this, MainActivity.class);
                startActivity(firstPage);
            }
        });
    }
}
