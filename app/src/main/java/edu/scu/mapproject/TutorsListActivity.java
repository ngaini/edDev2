package edu.scu.mapproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import static edu.scu.mapproject.R.id.tutorToolbar;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class TutorsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActionBarDrawerToggle drawerListner;
    private CustomAdapter myCustomAdapter;
    private String[] navOptions;

    private static Firebase ref1;
    private static Firebase ref;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String preferName = "AndriodSession";
    public static final String key_email = "email";
    private String sessionUserName;
    private static Firebase userRef1;
    private static Query userQueryRef;
    int appUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_list);
//        getSupportActionBar().setTitle("tutor");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tutorToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Tutor");


        //sessionUserName ="t@t.com";
        pref = getApplicationContext().getSharedPreferences(preferName, 0);
        editor = pref.edit();
        sessionUserName = pref.getString(key_email, null);
        //for fetching the global variables
//        MyApplication app =(MyApplication)getApplication();
        Toast.makeText(TutorsListActivity.this,"email- "+sessionUserName, Toast.LENGTH_LONG).show();




        // drawer layout code
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.tutorDrawerLayout);
        ListView list = (ListView)findViewById(R.id.tutorDrawerList);
        myCustomAdapter = new CustomAdapter(this);
        list.setAdapter(myCustomAdapter);
        navOptions =getResources().getStringArray(R.array.navOptions);
        list.setOnItemClickListener(this);


//

        drawerListner = new ActionBarDrawerToggle(this,drawerLayout,myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                getActionBar().setTitle(R.string.title_activity_drawer_test);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle("Menu");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerListner);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        Bundle extra = getIntent().getExtras();
//
////        String appUserName = extra.getString("");
//        String appUserID = extra.getString("uID");
//        int appUserRole =extra.getInt("role");
//        double appUserLat= extra.getDouble("lat");
//        double appUserLng = extra.getDouble("lng");
//        String interest = extra.getString("interest");
//        String shownInterest = extra.getString("interestedPeople");


        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
        userQueryRef = userRef1.orderByChild("emailID").equalTo(sessionUserName);
        userQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            String appUserName, appUserID,appUserInterest;
            double appUserLat,appUserLng;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Users userData = postSnapshot.getValue(Users.class);

                    appUserName = userData.getFullName();
                    appUserRole = userData.getRole();
                    appUserID= userData.getUserID();
                    appUserLat =userData.getLat();
                    appUserLng=userData.getLng();
                    appUserInterest=userData.getInterests();
//                                Query q1 = userRef1.child(appUserID).child("tempList").orderByChild("idVal");
                    Query q1 = userRef1.child(appUserID).child("tempList");
                    q1.addListenerForSingleValueEvent(new ValueEventListener() {
                        String interestStringArray = null;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

//                                        if(dataSnapshot.getChildrenCount()!=0)
//                                        {
                            Log.e("interesded string", "ello::before for");
                            for (DataSnapshot interestSnapshot : dataSnapshot.getChildren()) {
                                Log.e("interesded string", "ello::inside for");
                                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                                if (interestStringArray == null) {

                                    interestStringArray = String.valueOf(interestSnapshot.getValue());
                                } else {


                                    interestStringArray = interestStringArray + "," + String.valueOf(interestSnapshot.getValue());

                                }
                            }

//                                        }
                            Log.e("interesded string2", "ello::" + interestStringArray);
                            Log.e("LOGVAL Tutor", "ello::" + appUserName + "::" + appUserRole + "::" + appUserID + "::" + appUserLat);
                            populateList(appUserID, appUserName, appUserRole, appUserLat, appUserLng, appUserInterest,interestStringArray);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
//                                processDataForLoggedInUser(appUserID,appUserName,appUserRole,appUserLat,appUserLng,appUserInterest);

                    Log.e("LOGVAL STUDENT", "ello::" + appUserName + "::" + appUserRole + "::" + appUserID + "::" + appUserLat);
//

//
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        Log.e("TutList STUDENT", "ello::" + appUserLng + "::" + appUserRole + "::" + appUserID + "::" + interest+"::"+shownInterest);
//        Toast.makeText(TutorsListActivity.this, "ello::" + appUserLng + "::" + appUserRole + "::" + appUserID + "::" + appUserLat, Toast.LENGTH_SHORT).show();
//        populateList(appUserID,"somename",appUserRole,appUserLat,appUserLng,interest,shownInterest);
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
        ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
        selectTitle(navOptions[position]);

        if(position == 0)
        {
            Intent editProfilePage = new Intent(TutorsListActivity.this, TutorsListActivity.class);
            startActivity(editProfilePage);
        }

        if(position == 1)
        {
            Intent editProfilePage = new Intent(TutorsListActivity.this, EditProfile.class);
            startActivity(editProfilePage);
        }

        if(position == 4)
        {
            Intent changePasswordPage = new Intent(TutorsListActivity.this, changePassword.class);
            startActivity(changePasswordPage);
        }

        if(position == 5) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            ref1.unauth();
                            editor.clear();
                            editor.commit();
                            Intent login = new Intent(TutorsListActivity.this, Login.class);
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


    public void populateList(final String clientId, String clientName, int clientRole, double clientLat, double clientLng,String clientInterest, final String interestedInClient)
    {
//        interestedInClient  = interestedInClient.replace("idVal", "");
//        interestedInClient  = interestedInClient.replace("=", "");
//        interestedInClient  = interestedInClient.replace("{", "");
//        interestedInClient = interestedInClient.replace("}", "");
//        final String[] interestedInClientArray = interestedInClient.split(",");
//        Log.e("Interest without braces", "ello:: "+interestedInClient);

        final ListView studentList_id = (ListView)findViewById(R.id.tutorActivity_studentList_listView);
        final Location locationA = new Location("point A");
        final Location locationB = new Location("point B");
        final int listRole = 1;
        final String clientUserID=clientId;
        locationA.setLatitude(clientLat);
        locationA.setLongitude(clientLng);

        Log.e("LATLONG values", "ello ::" + clientLat + "::" + clientLng);
        ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
        ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");




//        Query queryTutorsTempList = ref1.orderByChild("interests").equalTo(clientInterest);
//
        Toast.makeText(TutorsListActivity.this, clientInterest, Toast.LENGTH_LONG).show();
        Query queryRef1 = ref.orderByChild("interests").equalTo(clientInterest);
        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, queryRef1)
        {

            @Override
            protected void populateView(View view, Users user, int i)
            {

                TextView text1_id =(TextView) view.findViewById(android.R.id.text1);
                TextView text2_id =(TextView) view.findViewById(android.R.id.text2);
                text1_id.setPaddingRelative(30,5,10,5);
                text2_id.setPaddingRelative(30, 5, 10, 20);
                String dFname= user.getFullName();
                double dLat = user.getLat();
                double dLng = user.getLng();
                locationB.setLatitude(dLat);
                locationB.setLongitude(dLng);
                float distanceInMeters =0;
                distanceInMeters = locationA.distanceTo(locationB);
                Log.e("TESTING", "ello::  dist :"+distanceInMeters);
                DecimalFormat df = new DecimalFormat("####0.0");
                String dist_in_mile= df.format(getMiles(distanceInMeters));
                Boolean flag=false;
                if(interestedInClient!=null)
                {
                    String copyOfIIC= interestedInClient;
                    copyOfIIC  = copyOfIIC.replace("idVal", "");
                    copyOfIIC  = copyOfIIC.replace("=", "");
                    copyOfIIC  = copyOfIIC.replace("{", "");
                    copyOfIIC = copyOfIIC.replace("}", "");
                    String[] interestedInClientArray= copyOfIIC.split(",");

                    for(i=0; i<interestedInClientArray.length; i++)
                    {

                        if(interestedInClientArray[i].equals(user.getUserID()))
                        {
//                        text1_id.setTextColor(Color.BLUE);
//                        text2_id.setTextColor(Color.GREEN);
                            Log.e("TESTING", "ello:: dist :"+interestedInClientArray[i]+":1010:"+ user.getUserID());
                            flag = true;
                        }
                    }
                }
//                for(i=0; i<interestedInClientArray.length; i++)
//                {
//                    Log.e("TESTING", "ello:: dist :"+interestedInClientArray[i]+":1010:"+ user.getUserID());
//                    if(interestedInClientArray[i].equals(user.getUserID()))
//                    {
//                        text1_id.setTextColor(Color.BLUE);
//                        text2_id.setTextColor(Color.GREEN);
//                        flag = true;
//                    }
//                }

                if(flag == true)
                {
                    text1_id.setTextAppearance(view.getContext(), android.R.style.TextAppearance_Large);
//                if(distanceInMeters<10)
//                {
                    text1_id.setTextColor(Color.parseColor("#FFC2260E"));
                    text2_id.setTextColor(Color.GREEN);
                    text1_id.setText(dFname);
                    text2_id.setText(user.getInterests()+"  ("+dist_in_mile+ " mi. )");
                }
                else
                {
                    text1_id.setTextAppearance(view.getContext(), android.R.style.TextAppearance_Large);
//                if(distanceInMeters<10)
//                {
//                    text1_id.setTextColor(Color.BLUE);
//                    text2_id.setTextColor(Color.GREEN);
                    text1_id.setText(dFname);
                    text2_id.setText(user.getInterests()+"  ("+dist_in_mile+ " mi. )");
                }
//                }
            }
        };
        //Bind the list adapter to  listView
        studentList_id.setAdapter(adapter);

        // item click action
        studentList_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
                String interest = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();
                Log.e("TESTING", " name "+name+" is interested in "+interest);
                Toast.makeText(TutorsListActivity.this," name "+name+" is interested in "+interest, Toast.LENGTH_SHORT).show();

                Intent tutorDetailIntent = new Intent(TutorsListActivity.this, TutorDetailActivity.class);
                // creating bundle
                Bundle extra = new Bundle();
                extra.putString("name", name);
                extra.putInt("listRole", 1);
                extra.putString("userID",clientUserID);
                tutorDetailIntent.putExtras(extra);
                startActivity(tutorDetailIntent);
            }
        });
    }

    private double getMiles(float distanceVal)
    {
        return distanceVal/1609.344;
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

//    public void getStudentsWithSameInterest(String interest, Firebase ref)
//    {
//        Query queryRef1 = ref.orderByChild("interests").equalTo(interest);
//        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, ref)
//        {
//
//            @Override
//            protected void populateView(View view, Users user, int i)
//            {
//                TextView text1_id =(TextView) view.findViewById(android.R.id.text1);
//                TextView text2_id =(TextView) view.findViewById(android.R.id.text2);
//                text1_id.setPaddingRelative(30,5,10,5);
//                text2_id.setPaddingRelative(30, 5, 10, 20);
//                String dFname= user.getFullName();
//                double dLat = user.getLat();
//                double dLng = user.getLng();
//                locationB.setLatitude(dLat);
//                locationB.setLongitude(dLng);
//                float distanceInMeters =0;
//                distanceInMeters = locationA.distanceTo(locationB);
//                Log.e("TESTING", " dist :"+distanceInMeters);
//
//                text1_id.setTextAppearance(view.getContext(), android.R.style.TextAppearance_Large);
////                if(distanceInMeters<10)
////                {
//                DecimalFormat df = new DecimalFormat("####0.0");
//
//                text1_id.setText(dFname);
//                text2_id.setText(user.getInterests()+"  ("+df.format(getMiles(distanceInMeters))+ " mi. )");
////                }
//            }
//        };
//        //Bind the list adapter to  listView
//        studentList_id.setAdapter(adapter);
//
//        // item click action
//        studentList_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String name = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
//                String interest = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();
//                Log.e("TESTING", " name "+name+" is interested in "+interest);
//                Toast.makeText(TutorsListActivity.this," name "+name+" is interested in "+interest, Toast.LENGTH_SHORT).show();
//
//                Intent tutorDetailIntent = new Intent(TutorsListActivity.this, TutorDetailActivity.class);
//                // creating bundle
//                Bundle extra = new Bundle();
//                extra.putString("name", name);
//                extra.putInt("listRole", listRole);
//                extra.putString("userID",clientUserID);
//                tutorDetailIntent.putExtras(extra);
//                startActivity(tutorDetailIntent);
//            }
//        });
//
//
//    }
}
