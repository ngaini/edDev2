package edu.scu.mapproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText streetAdrress;
    private EditText city;
    private EditText state;
    private EditText pinCode;
    private EditText phoneNumber;
    private EditText description;
    private Spinner education;
    private Spinner interest;
    private Spinner role;
    private Button confirm;
    private double lat, lng;
    private Button back;
    Firebase mref,href,xref, userRef;
    private String uEmailID1, uFullName;
    Query queryRef;
    private String userImage, userStreetAddress, userpinCode, userphoneNumber, userDescription, userRole, userID, userFullName, userAge, userEmailID, userPassword, userGender;
    private String uAddress, uPincode, uPhoneNumber, uEducation, uInterests, uDescription, uID, uFUllName, uAge, uEmailID, uPassword, uGender;
    private int uRole, userRoleInt;
    private String userStatement;
    private static final String preferName = "AndriodSession";
    private String sessionUserName, sessionUserID;
    public static final String key_userid = "name";
    public static final String key_email = "email";
    SharedPreferences pref;
    private Firebase ref2;
    SharedPreferences.Editor editor;
    private android.support.v7.app.ActionBarDrawerToggle drawerListner;
    private CustomAdapter myCustomAdapter;
    private String[] navOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawer_test);
        setContentView(R.layout.activity_edit_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Firebase.setAndroidContext(this);
        pref = getApplicationContext().getSharedPreferences(preferName, 0);
        editor = pref.edit();
        sessionUserName = pref.getString(key_email, null);
        Toast.makeText(getApplicationContext(), "Session User Name - "+sessionUserName, Toast.LENGTH_SHORT).show();
        streetAdrress = (EditText) findViewById(R.id.update_StreetAdress);
        pinCode = (EditText) findViewById(R.id.update_PinCode);
        phoneNumber = (EditText) findViewById(R.id.update_PhoneNumber);
        description = (EditText) findViewById(R.id.update_Description);
        education = (Spinner) findViewById(R.id.update_Education);
        interest = (Spinner) findViewById(R.id.update_Interest);
        confirm = (Button) findViewById(R.id.update_Confirm);
        back = (Button) findViewById(R.id.update_Back);
        city = (EditText) findViewById(R.id.update_City);
        state = (EditText) findViewById(R.id.update_State);
        ref2 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ListView list = (ListView)findViewById(R.id.drawerList);
        myCustomAdapter = new CustomAdapter(this);
        list.setAdapter(myCustomAdapter);
        navOptions = getResources().getStringArray(R.array.navOptions);

        //setting item lister for nav drawer item click
        list.setOnItemClickListener(this);

        //drawerListner = new ActionBarDrawerToggle(this,drawerLayout,myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerListner = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }
        };
        drawerLayout.setDrawerListener(drawerListner);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
        queryRef = userRef.orderByChild("emailID").equalTo(sessionUserName);
        Toast.makeText(getApplicationContext(), "Going inside the query"+sessionUserName, Toast.LENGTH_SHORT).show();
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Users userData = postSnapshot.getValue(Users.class);
                        uRole = userData.getRole();
                        userphoneNumber = userData.getPhoneNumber();
                        userDescription = userData.getDescription();
                        userID = userData.getUserID();
                        userFullName = userData.getFullName();
                        userAge = userData.getAge();
                        userEmailID = userData.getEmailID();
                        userPassword = userData.getPassword();
                        userGender = userData.getGender();
                        userImage = userData.getImage();
                        streetAdrress.setText("1234 Benton Street");
                        pinCode.setText("95050");
                        city.setText("Fremont");
                        state.setText("CaliforniA");
                        phoneNumber.setText(userphoneNumber);
                        description.setText(userDescription);

                    }
                }else
                {
                    userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                    queryRef = userRef.orderByChild("emailID").equalTo(sessionUserName);
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Users userData = postSnapshot.getValue(Users.class);
                                uRole = userData.getRole();
                                userphoneNumber = userData.getPhoneNumber();
                                userDescription = userData.getDescription();
                                userID = userData.getUserID();
                                userFullName = userData.getFullName();
                                userAge = userData.getAge();
                                userEmailID = userData.getEmailID();
                                userPassword = userData.getPassword();
                                userGender = userData.getGender();
                                streetAdrress.setText("1234 Benton Street");
                                pinCode.setText("95050");
                                city.setText("Fremont");
                                state.setText("CaliforniA");
                                phoneNumber.setText(userphoneNumber);
                                description.setText(userDescription);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uAddress = streetAdrress.getText().toString().trim();
                uPincode = pinCode.getText().toString().trim();
                uPhoneNumber = phoneNumber.getText().toString().trim();
                uDescription = description.getText().toString().trim();
                uID = userID.toString().trim();
                uFullName = userFullName.toString().trim();
                uAge = userAge.toString().trim();
                uEmailID = userEmailID.toString().trim();
                uPassword = userPassword.toString().trim();
                uGender = userGender.toString().trim();
                uEducation = education.getSelectedItem().toString();
                uInterests = interest.getSelectedItem().toString();
                lat = 37.3502;
                lng = -121.946224;
                if (uAddress.equals("")) {
                    streetAdrress.setError("Field cannot be Empty!");
                } else if (uPincode.equals("")) {
                    pinCode.setError("Field cannot be Empty!");
                } else if (uPhoneNumber.equals("")) {
                    phoneNumber.setError("Field cannot be Empty!");
                } else if (uDescription.equals("")) {
                    description.setError("Field cannot be Empty!");
                } else {
                    if(uRole == 1)
                    {
                        userRole = "Student";
                    }else
                    {
                        userRole = "Tutor";
                    }
                    mref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userRole);
                    Firebase newUserRef = mref.child(uID);
                    Users newUser = new Users(uID, uRole, uFullName, uAge, uEmailID, uPhoneNumber, uPassword, uEducation, uDescription, uGender, uInterests, lat, lng, userImage);
                    newUserRef.setValue(newUser);
                    Intent mainPage = new Intent(EditProfile.this, StudentsListActivity.class);
                    startActivity(mainPage);
                    Toast.makeText(getApplicationContext(), "CHECK IF UPDATED", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // this causes the drawer icon to appear
        drawerListner.syncState();
    }

    // change the navigation drawer when the configuration changes
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListner.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0)
        {
            Intent editProfilePage = new Intent(EditProfile.this, StudentsListActivity.class);
            startActivity(editProfilePage);
        }


        if(position == 1)
        {
            Intent editProfilePage = new Intent(EditProfile.this, EditProfile.class);
            startActivity(editProfilePage);
        }

        if(position == 2)
        {
            Intent discoverySettingsPage = new Intent(EditProfile.this, DiscoverySettingsPage.class);
            startActivity(discoverySettingsPage);
        }

        if(position == 5)
        {
            Intent changePasswordPage = new Intent(EditProfile.this, changePassword.class);
            startActivity(changePasswordPage);
        }

        if(position == 6) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            ref2.unauth();
                            editor.clear();
                            editor.commit();
                            Intent login = new Intent(EditProfile.this, Login.class);
                            startActivity(login);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("CONFIRM");
            builder.setMessage("Are you sure ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }


    }
}
