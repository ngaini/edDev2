package edu.scu.mapproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.KeyEvent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;


public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button newUser;
    private TextView clickLink;
    private String userStatement;
    String uname;
    String lat, lng;
    String pass;
    private int uRole;
    Firebase mref, userRef, userRef1;
    Resources res;
    UserSessionManager session;
    Query qref;
    private String userID;
    Users userData;
    String sessionUserID;
    String sessionUserName;

    private Double uLat, uLong;

    Query queryRef;

    private static final String preferName = "AndriodSession";
    SharedPreferences pref; // 0 - for private mode
    SharedPreferences.Editor editor;
    public static final String key_userid = "name";
    public static final String key_email = "email";

    private static Firebase loggedInUserRef;
    private static Firebase loggedInUserRef1;
    private static Query loggedInUserQueryRef;
    private static Query loggedInUserQueryRef1;
    private String TUTOR_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Tutor";
    private String STUDENT_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Student";

    public static final String key_role = "userRole";
    public static final String key_userID = "userID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
        res = getResources();
        session = new UserSessionManager(getApplicationContext());
        username = (EditText) findViewById(R.id.login_emailID);
        password = (EditText) findViewById(R.id.login_Password);
        login = (Button) findViewById(R.id.main_Login);
        pref = getApplicationContext().getSharedPreferences(preferName, 0); // 0 - for private mode
        editor = pref.edit();
        newUser = (Button) findViewById(R.id.btn_NewUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSignUp = new Intent(Login.this, RoleChoice.class);
                startActivity(iSignUp);
            }
        });

        userRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null)
                {
//                    checkForClient();
//                    sessionUserName = pref.getString(key_email, null);
//                    userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
//                    queryRef = userRef1.orderByChild("emailID").equalTo(uname);
//                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            //Toast.makeText(getApplicationContext(), "INSIDE MAIN", Toast.LENGTH_SHORT).show();
//                            if (dataSnapshot.getChildrenCount() != 0) {
//                                Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
//                                startActivity(mainPage);
//                            }
//                            else {
//                                Intent mainPage = new Intent(Login.this, TutorsListActivity.class);
//                                startActivity(mainPage);
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    });
////                    sessionUserName = pref.getString(key_email, null);
                    sessionUserName = pref.getString(key_email, null);
//                    session.createUserLoginSession("session stored", uname);
                    userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
                    final String clientName= uname;
                    queryRef = userRef1.orderByChild("emailID").equalTo(uname);
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Toast.makeText(getApplicationContext(), "INSIDE MAIN", Toast.LENGTH_SHORT).show();
                            if (dataSnapshot.getChildrenCount() != 0) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Users userData = postSnapshot.getValue(Users.class);
                                    //Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();
//                                            uRole = userData.getRole();
//                                            userID = userData.getUserID();
//                                            session.createUserLoginSession("session stored", uname);
//                                            Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
                                    if(clientName == userData.getEmailID())
                                    {
                                        Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
                                        startActivity(mainPage);
                                    }

                                }
                            }else
                            {
                                userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                                final String clientName1= uname;
                                queryRef = userRef1.orderByChild("emailID").equalTo(uname);
                                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            Users userData = postSnapshot.getValue(Users.class);
//                                                    uRole = userData.getRole();
//                                                    userID = userData.getUserID();
//                                                    session.createUserLoginSession("session stored", uname);
                                            //Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
                                            if(clientName1 == userData.getEmailID())
                                            {
                                                Intent mainPage = new Intent(Login.this, TutorsListActivity.class);
                                                startActivity(mainPage);
                                            }
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

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = username.getText().toString();
                pass = password.getText().toString();
                if (uname.isEmpty() || pass.isEmpty()) {
                    username.setError("Invalid Input");
                } else {
                    mref.authWithPassword(uname, pass, new Firebase.AuthResultHandler() {
                        @Override

                        public void onAuthenticated(AuthData authData)
                        {
//                            checkForClient();
                            session.createUserLoginSession("session stored", uname);
                            userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
                            queryRef = userRef1.orderByChild("emailID").equalTo(uname);
                            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Toast.makeText(getApplicationContext(), "INSIDE MAIN", Toast.LENGTH_SHORT).show();
                                    if (dataSnapshot.getChildrenCount() != 0) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            Users userData = postSnapshot.getValue(Users.class);
                                            //Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();
//                                            uRole = userData.getRole();
//                                            userID = userData.getUserID();
//                                            session.createUserLoginSession("session stored", uname);
//                                            Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
                                            Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
                                            startActivity(mainPage);

                                        }
                                    }else
                                    {
                                        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                                        queryRef = userRef1.orderByChild("emailID").equalTo(uname);
                                        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    Users userData = postSnapshot.getValue(Users.class);
//                                                    uRole = userData.getRole();
//                                                    userID = userData.getUserID();
//                                                    session.createUserLoginSession("session stored", uname);
                                                    //Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
                                                    //Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
                                                    //Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
                                                    Intent mainPage = new Intent(Login.this, TutorsListActivity.class);
                                                    startActivity(mainPage);

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

//                            session.createUserLoginSession("session stored", uname);
//                            Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
//                            startActivity(mainPage);


                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            switch (firebaseError.getCode()){
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    username.setError("username does not exist");
                                    break;
                                case FirebaseError.INVALID_PASSWORD:
                                    password.setError("password is not correct");
                                    break;

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }


    public void checkForClient()
    {
        session.createUserLoginSession("session stored", uname);
        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
        queryRef = userRef1.orderByChild("emailID").equalTo(uname);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(), "INSIDE MAIN", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Users userData = postSnapshot.getValue(Users.class);
                        //Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();
//                                            uRole = userData.getRole();
//                                            userID = userData.getUserID();
//                                            session.createUserLoginSession("session stored", uname);
//                                            Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
                        Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
                        startActivity(mainPage);

                    }
                }else
                {
                    userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                    queryRef = userRef1.orderByChild("emailID").equalTo(uname);
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Users userData = postSnapshot.getValue(Users.class);
//                                                    uRole = userData.getRole();
//                                                    userID = userData.getUserID();
//                                                    session.createUserLoginSession("session stored", uname);
                                //Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
                                Intent mainPage = new Intent(Login.this, TutorsListActivity.class);
                                startActivity(mainPage);

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

//                            session.createUserLoginSession("session stored", uname);
//                            Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
//                            startActivity(mainPage);

    }
}

