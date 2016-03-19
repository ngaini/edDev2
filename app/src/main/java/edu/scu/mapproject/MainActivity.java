package edu.scu.mapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText age;
    private EditText phoneNumber;
    private EditText emailID;
    private EditText password;
    private EditText rePassword;
    private TextView userRole;
    private Button back;
    private Button next;
    private boolean flag = true;
    private String uFullName;
    private int uRole;
    private String uAge;
    private String uPhoneNumber;
    private String uEmailID;
    private String uPassword;
    Firebase mref;
    private String tempPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //Firebase.setAndroidContext(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        fullName = (EditText) findViewById(R.id.main_FullName);
        age = (EditText) findViewById(R.id.main_Age);
        phoneNumber = (EditText) findViewById(R.id.main_PhoneNumber);
        emailID = (EditText) findViewById(R.id.main_EmailAddress);
        password = (EditText) findViewById(R.id.main_Password);
        rePassword = (EditText) findViewById(R.id.main_ConfirmPassword);
        back = (Button) findViewById(R.id.main_Back);
        next = (Button) findViewById(R.id.main_Next);
        userRole = (TextView) findViewById(R.id.txt_userrole);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        tempPassword = "1QASW#$%^&()#################";
        Bundle extras = getIntent().getExtras();
        uRole = extras.getInt("userRole");

        if(uRole == 0)
        {
            userRole.setText("Tutor");
        }
        else
        {
            userRole.setText("Student");
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uFullName = fullName.getText().toString();
                uAge = age.getText().toString();
                uPhoneNumber = phoneNumber.getText().toString();
                uEmailID = emailID.getText().toString();
                uPassword = password.getText().toString();
                if (uFullName.trim().isEmpty())
                {
                    fullName.setError("Invalid Input");
                }
                else if (uAge.trim().isEmpty())
                {
                    age.setError("Invalid Input");
                }
                else if (uPhoneNumber.trim().isEmpty())
                {
                    phoneNumber.setError("Invalid Input");
                }
                else if (uEmailID.trim().isEmpty())
                {
                    emailID.setError("Invalid Input");
                }
                else if (uPassword.trim().isEmpty())
                {
                    password.setError("Invalid Input");
                }
                else if(rePassword.getText().toString().trim().isEmpty())
                {
                    rePassword.setError("Invalid Input");
                }
                else if (!password.getText().toString().trim().equals(rePassword.getText().toString().trim()))
                {
                    password.setError("Password does not match");
                    rePassword.setError("Password does not match");
                }
                else {
                    mref.authWithPassword(uEmailID,tempPassword, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            emailID.setError("Already registered User");
                            Toast.makeText(getApplicationContext(), "Already Registered User", Toast.LENGTH_SHORT).show();
                            mref.unauth();
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            //Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();
                            switch (firebaseError.getCode()){

                                case FirebaseError.INVALID_PASSWORD: {
                                    Toast.makeText(getApplicationContext(), "Already registered User", Toast.LENGTH_SHORT).show();
                                    emailID.setError("Already registered User");
                                    break;
                                }

                                case FirebaseError.USER_DOES_NOT_EXIST: {
                                    Toast.makeText(getApplicationContext(), "SORT MKC!", Toast.LENGTH_SHORT).show();
                                    Intent mainPage = new Intent(MainActivity.this, SignUpPage2.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("userRole", uRole);
                                    bundle.putString("uAge", uAge);
                                    bundle.putString("uPhoneNumber", uPhoneNumber);
                                    bundle.putString("uFullName", uFullName);
                                    bundle.putString("uPassword", uPassword);
                                    bundle.putString("uEmailID", uEmailID);
                                    mainPage.putExtras(bundle);
                                    startActivity(mainPage);
                                    break;
                                }
                            }
                        }
                    });

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RoleChoicePage = new Intent(MainActivity.this, RoleChoice.class);
                startActivity(RoleChoicePage);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void moveToDetailsActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//        startActivity(intent);
//    }
//
//    public void moveToLoginActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, Login.class);
//        startActivity(intent);
//    }
}
