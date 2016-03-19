package edu.scu.mapproject;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class TutorDetailActivity extends Activity {

    private String FIREBASE_URL = "https://scorching-inferno-7039.firebaseio.com";
    private String TUTOR_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Tutor";
    private String STUDENT_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Student";
    private Firebase ref;
    private Firebase ref1;



    static int listRole;
    private final String API_KEY = "DEV56E7BC3BA4A1F6DF11D0CAB6148";
    Button interestedButtonId,contactButtonId;
    //is the id value of the user whose details are being displayed
    static String detailIdValue;


    /**
     * delete this when useridvalue is fetched dynamically
     */
    //is the id value of the logged in user
//    static String userIdValue ="20160311_183546";
    static String clientIdValue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyApplication app =(MyApplication)getApplication();
        setContentView(R.layout.activity_tutor_detail);
        interestedButtonId =(Button)findViewById(R.id.TDA_interested_button);
        contactButtonId =(Button)findViewById(R.id.TDA_contact_button);
        Bundle extra = getIntent().getExtras();
        final String name = extra.getString("name");
        listRole = extra.getInt("listRole");
        this.setListRole(extra.getInt("listRole"));
        clientIdValue = extra.getString("userID");
        Log.e("CHECK THIS", "ello::listROle: "+this.getListRole());
        final TextView tutorName_id = (TextView)this.findViewById(R.id.TDA_tutorName_textView);
        final TextView tutorExpertize_id = (TextView)this.findViewById(R.id.TDA_tutorExpertise_textView);
        final TextView tutorAge_id = (TextView)this.findViewById(R.id.TDA_tutorAge_textView);
        final TextView tutorDescription_id = (TextView)this.findViewById(R.id.TDA_tutorDescription_textView);
        final TextView tutorGender_id = (TextView)this.findViewById(R.id.TDA_tutorGender_textView);
        final TextView tutorEducation_id = (TextView)this.findViewById(R.id.TDA_tutorEducation_textView);



        if(!name.isEmpty())
        {
            if(listRole == 0)
            {
                ref = new Firebase(TUTOR_TABLE_URL);
                ref1= new Firebase(STUDENT_TABLE_URL);
            }
            else if(listRole==1)
            {
                ref= new Firebase(STUDENT_TABLE_URL);
                ref1 = new Firebase(TUTOR_TABLE_URL);
            }

            final int detailRole =listRole;
            enableInterestedButton();
            Query queryRef = ref.orderByChild("fullName").equalTo(name);
            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Users user = dataSnapshot.getValue(Users.class);
                    tutorName_id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                    tutorName_id.setText(name);
                    if(detailRole ==1)
                    {
                        tutorExpertize_id.setText("Interest: " + user.getInterests());
                    }
                    else if( detailRole ==0)
                    {
                        tutorExpertize_id.setText("Expertise: " + user.getInterests());
                    }
                    tutorAge_id.setText(user.getAge() + " yrs");
                    tutorEducation_id.setText("Highest Education:  " + user.getEducation());
                    tutorGender_id.setText("Gender: " + user.getGender());
                    tutorDescription_id.setText("Description:\n" + user.getDescription());
                    Log.e("CHECK THIS", "ello::before the detailIdvalue ");
                    detailIdValue = user.getUserID();

                    //enable or disable button
                    //if user has already clicked interested for a tutor the button will stay disabled
                    disableContactButton();
                    Log.e("CHECK THIS", "ello::detailsvalue: " + user.getUserID() + " ::" + detailIdValue);
                    Query q1 =ref.child(detailIdValue).child("tempList").orderByChild("idVal").equalTo(clientIdValue);
                    q1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.getValue()!=null)
                            {
                                Log.e("CHECK THIS", "inside query data change ");
                                disableInterestedButton();
                                Query q2 =ref.child(clientIdValue).child("tempList").orderByChild("idVal").equalTo(detailIdValue);
                                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.e("CHECK THIS", "in students tree, childnode contains listed tutor  ");
                                        enableContactButton();
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
//                            enableInterestedButton();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
//                    detailIdValue ="20160311_183546";
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
//




        }


        //incase the tutor happens to delete his account
        else if(name.isEmpty())
        {
            Intent tutorUnAvailableIntent = new Intent(TutorDetailActivity.this, StudentsListActivity.class);
            startActivity(tutorUnAvailableIntent);
        }
//

    }




    public void informTutor(View view)
    {

        listRole = this.getListRole();
//        ((Button)view.findViewById(R.id.TDA_interested_button)).setEnabled(false);
        disableInterestedButton();
        Log.e("CHECK THIS", "ello::: onclick:"+listRole);
        Firebase informTutor_ref=new Firebase(TUTOR_TABLE_URL) ;
        //get the tutors name
        TextView name = (TextView)view.findViewById(R.id.TDA_tutorName_textView);

        if(listRole==0)
        {
            //add student id value for in tutors temp list
            informTutor_ref = new Firebase(TUTOR_TABLE_URL).child(detailIdValue).child("tempList").push();
        }
        else if(listRole==1)
        {
            informTutor_ref = new Firebase(STUDENT_TABLE_URL).child(detailIdValue).child("tempList").push();
        }
        Map<String, String> tempListVar = new HashMap<String, String>();

        tempListVar.put("idVal",clientIdValue);
        informTutor_ref.setValue(tempListVar);
        Toast.makeText(TutorDetailActivity.this, "value added to tutor temp list :"+clientIdValue, Toast.LENGTH_SHORT).show();



        //create new child called student list


        //if child exists then add to the child


        //disable button on click to avoid extra entries
    }

    public void disableInterestedButton()
    {
        interestedButtonId.setEnabled(false);
    }



    public void enableInterestedButton()
    {
        interestedButtonId.setEnabled(true);
    }


    public static void setListRole(int listRole) {
        TutorDetailActivity.listRole = listRole;
    }

    public static int getListRole() {
        return listRole;
    }

    public void enableContactButton()
    {
        contactButtonId.setEnabled(true);
    }

    public void disableContactButton()
    {
        contactButtonId.setEnabled(false);
    }

}
