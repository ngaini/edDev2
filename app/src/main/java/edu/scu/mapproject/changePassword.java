package edu.scu.mapproject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import static edu.scu.mapproject.R.id.toolbar;

public class changePassword extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button confirm;
    private Button back;
    private String uEmailID, uFullName, userID;
    private String uPassword;
    private String userEmailID;
    private String userOldPassword, userStatement;
    private String userNewPassword;
    private String userConfirmPassword;
    private int uRole;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private Firebase ref2;
    Query qref;
    Firebase userRef, userRef1;
    private static final String preferName = "AndriodSession";
    private String sessionUserName, sessionUserID;
    public static final String key_userid = "name";
    public static final String key_email = "email";
    private android.support.v7.app.ActionBarDrawerToggle drawerListner;
    private CustomAdapter myCustomAdapter;
    private String[] navOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawer_test);
        setContentView(R.layout.activity_change_password);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Firebase.setAndroidContext(this);
        pref = getApplicationContext().getSharedPreferences(preferName, 0);
        editor = pref.edit();
        sessionUserName = pref.getString(key_email, null);
        Toast.makeText(changePassword.this, "Session user name is - " +sessionUserName, Toast.LENGTH_SHORT).show();
        sessionUserID = pref.getString(key_userid, null);
        oldPassword = (EditText) findViewById(R.id.change_oldPassword);
        newPassword = (EditText) findViewById(R.id.change_NewPassword);
        confirmPassword = (EditText) findViewById(R.id.change_ConfirmPassword);
        confirm = (Button) findViewById(R.id.change_Confirm);
        back = (Button) findViewById(R.id.change_Back);
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

        //fetching the parent of emailID
        userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
        qref = userRef.orderByChild("emailID").equalTo(sessionUserName);
        qref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Users userData = postSnapshot.getValue(Users.class);
                        uRole = userData.getRole();
                        userID = userData.getUserID();
                    }
                } else {
                    userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                    qref = userRef.orderByChild("emailID").equalTo(sessionUserName);
                    qref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Users userData = postSnapshot.getValue(Users.class);
                                uRole = userData.getRole();
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
        if(uRole == 1)
        {
            userStatement = "Student";
        }else
        {
            userStatement = "Tutor";
        }

        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userStatement+"/"+userID);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOldPassword = oldPassword.getText().toString().trim();
                userConfirmPassword = confirmPassword.getText().toString().trim();
                userNewPassword = newPassword.getText().toString().trim();

                if (userNewPassword.equals(userConfirmPassword)) {
                    mref.changePassword(sessionUserName, userOldPassword, userConfirmPassword, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(changePassword.this, "Password Changed!", Toast.LENGTH_SHORT).show();
                            //userRef1.child("password").setValue(userConfirmPassword);
                            editor.clear();
                            editor.commit();
                            mref.unauth();
                            Intent login = new Intent(changePassword.this, Login.class);
                            startActivity(login);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(changePassword.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    newPassword.setError("Password doesn't match!");
                    confirmPassword.setError("Password doesn't match!");
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
        selectTitle(navOptions[position]);

        if(position == 0)
        {
            Intent editProfilePage = new Intent(changePassword.this, StudentsListActivity.class);
            startActivity(editProfilePage);
        }


        if(position == 1)
        {
            Intent editProfilePage = new Intent(changePassword.this, EditProfile.class);
            startActivity(editProfilePage);
        }

        if(position == 2)
        {
            Intent discoverySettingsPage = new Intent(changePassword.this, DiscoverySettingsPage.class);
            startActivity(discoverySettingsPage);
        }

        if(position == 5)
        {
            Intent changePasswordPage = new Intent(changePassword.this, changePassword.class);
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
                            Intent login = new Intent(changePassword.this, Login.class);
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

    private void selectTitle(String navOption) {
        //to change the nave bar name
        getSupportActionBar().setTitle(navOption);
    }
}
