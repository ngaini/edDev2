package edu.scu.mapproject;

import android.Manifest;
import android.content.ContentValues;
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

public class SignUpPage2 extends AppCompatActivity implements LocationListener{

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
    double lat1, lng1;


    List<Address> addresses;
    private String constructedAddress;





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
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(provider);
//
//        if (location != null) {
//            System.out.println("Provider " + provider + " has been selected.");
//            onLocationChanged(location);
//
//            pincode.setText(uPincode);
//            address.setText(uAddress);
//            city.setText(uCity);
//            state.setText(uState);
//        } else {
//            //Toast.makeText(SignUpPage2.this, "Network Issues unable to fetch Loaction", Toast.LENGTH_SHORT);
//        }

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
                uAddress = address.getText().toString();
                uPincode = pincode.getText().toString();
                uCity = city.getText().toString().trim();
                uState = state.getText().toString().trim();
                if (uAddress.trim().isEmpty()) {
                    address.setError("Invalid Input");
                } else if (uPincode.trim().isEmpty()) {
                    pincode.setError("Invalid Input");
                } else if (uCity.trim().isEmpty()) {
                    city.setError("Invalid Input");
                } else if (uState.trim().isEmpty()) {
                    state.setError("Invalid Input");
                } else {
                    lat1 = 11.11111;
                    lng1 = -11.11111;
//
//                    String full_address = uAddress + ", "+uCity+ ", " +uState+", "+uCountry+", "+uPincode;
//                    HashMap<String, Double> latLong_values = convertToLatLong(full_address);
//                    double lat =latLong_values.get("lattitude") ;
//                    double lng=latLong_values.get("longitude") ;
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
                    bundle.putDouble("uLat", lat1);
                    bundle.putDouble("uLng", lng1);
                    bundle.putString("userID", userID);
                    bundle.putString("uGender", uGender);
                    mainPage.putExtras(bundle);
                    startActivity(mainPage);
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

//    @Override
//    public void onLocationChanged(Location location) {
//        double lat = (location.getLatitude());
//        double lng = (location.getLongitude());
////        latituteField.setText(String.valueOf(lat));
////        longitudeField.setText(String.valueOf(lng));
//        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
//        try {
//            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
//
//
//            if (addresses != null) {
//                Log.e(" ADDR VALUE"," inside location changed" );
//                uState = null;
//                uCity = null;
//                uPincode = null;
//                uAddress = null;
//                uCountry = null;
//
////                longitudeField.setText(strReturnedAddress.toString());
//                uState = addresses.get(0).getAdminArea();
//                uCity = addresses.get(0).getLocality();
//                uPincode = addresses.get(0).getPostalCode();
//                uAddress = addresses.get(0).getAddressLine(0).toString();
//                uCountry = addresses.get(0).getCountryName();
////
//                Log.e(" ADDR VALUE"," "+uAddress+", "+uPincode );
//            } else {
////                longitudeField.setText("No Address returned!");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//
//    /* Request updates at startup */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(provider, 400, 1, this);
//    }
//
//    /* Remove the locationlistener updates when Activity is paused */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.removeUpdates(this);
//    }
//
//    //construct address from pin code
//    public String constructAddress(String zip, String streetAddress)
//    {
//        Geocoder coder = new Geocoder(this);
//        List<Address> addresses1;
//        double convLat;
//        double convLong;
//        final String zippy = "95050";
//        try {
//            String streetAddr = "1050 Benton Street";
//            addresses1 = coder.getFromLocationName(zippy, 1);
//            if (addresses1 != null && !addresses1.isEmpty()) {
//                Address address = addresses1.get(0);
//                // Use the address as needed
//                convLat = address.getLatitude();
//                convLong = address.getLongitude();
//                String message = String.format("Latitude: %f, Longitude: %f",
//                address.getLatitude(), address.getLongitude());
//                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//
//                //convert back to address
//                Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
//                try {
//                    List<Address> addrConv = geocoder.getFromLocation(convLat, convLong, 1);
//
//
//                    if (addrConv != null) {
//
//
//                        String city = addrConv.get(0).getLocality();
//                        String state = addrConv.get(0).getAdminArea();
//                        String country = addrConv.get(0).getCountryName();
////                        constructedAddress = streetAddr + " " + city + " " + state + " " + " "+zip+" " + country;
//                        constructedAddress = streetAddr + " " + city + " " + state + " " +country +" "+zippy;
//                        ((TextView) findViewById(R.id.location_givenAddr_textView)).setText("the given address is:" + constructedAddress);
//
//                        //getting the same address from the constructed address
//
////                        String returnedAddr =convertToLatLong(constructedAddress);
////                        ((TextView) findViewById(R.id.location_constructedAddr_textView)).setText("Trying to get the same address: \n"+returnedAddr);
//
//
//
//                    } else {
//                        longitudeField.setText("No Address returned!");
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
////            e.printStackTrace();
//                    longitudeField.setText("Canont get Address!");
//                }
//
//
//                ((TextView) findViewById(R.id.location_displayLati_textView)).setText(message);
//            } else {
//                // Display appropriate message when Geocoder services are not available
//                Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
//            }
//
//
//
//
//        } catch (IOException e) {
//            // handle exception
//        }
//
//        return null;
//    }
//    //convert address into lat long
//    public HashMap<String, Double> convertToLatLong(String completeAddr) {
//        Geocoder coder = new Geocoder(this);
//        addresses = null;
//
//        Log.e("LocationDETAILS", completeAddr);
//        HashMap<String, Double> hmap = new HashMap<String,Double>();
//        try {
//            Log.e("LocationDETAILS", completeAddr);
//            addresses = coder.getFromLocationName(completeAddr, 1);
//            Log.e("LocationDETAILS", "before if ");
//
//            if (addresses != null && !addresses.isEmpty()) {
//                Address address = addresses.get(0);
//                // Use the address as needed
//                Log.e("LocationDETAILS", "after if ");
//                if(address.getLongitude()!=0 || address.getLatitude()!=0)
//                {
//                    String message = String.format("Latitude: %f, Longitude: %f",
//                            address.getLatitude(), address.getLongitude());
//                    Log.e("LocationDETAILS", " lat long value : " + message);
//                    hmap.put("lattitude", address.getLatitude());
//                    hmap.put("longitude", address.getLongitude());
//
//                    //checking if values are put inside the hash map
//                    Log.e(" HASH MAP CHECK", hmap.get("lattitude").toString());
//
//
//
//                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//
////                addr =convToAddr(latValue, longValue);
//                }
//
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return hmap;
//    }
//
//    public String convToAddr(double lat, double lng)
//    {
//        //convert from lat long to address
//        String constructedAddressForMethod=null;
//        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
//        try {
//            List<Address> addrConv = geocoder.getFromLocation(lat, lng, 1);
//
//
//            if (addrConv != null) {
//                Address returnedAddress = addrConv.get(0);
//                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
//                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
//                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
//                }
//                longitudeField.setText(strReturnedAddress.toString());
//
////                String city = addrConv.get(0).getLocality();
////                String state = addrConv.get(0).getAdminArea();
////                String country = addrConv.get(0).getCountryName();
//                constructedAddressForMethod = strReturnedAddress.toString();
//                ((TextView) findViewById(R.id.location_constructedAddr_textView)).setText(constructedAddress);
//            } else {
//                longitudeField.setText("No Address returned!");
////                constructedAddressForMethod = "";
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
////            e.printStackTrace();
//
//        }
//
//        return constructedAddressForMethod;
//    }

}