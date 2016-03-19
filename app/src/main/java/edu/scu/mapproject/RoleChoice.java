package edu.scu.mapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class RoleChoice extends AppCompatActivity {

    private Button student;
    private Button tutor;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        student = (Button) findViewById(R.id.btn_Student);
        tutor = (Button) findViewById(R.id.btn_tutor);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 1;
                Intent signUpPage = new Intent(RoleChoice.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userRole", role);
                signUpPage.putExtras(bundle);
                startActivity(signUpPage);
            }
        });

        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpPage = new Intent(getApplicationContext(), MainActivity.class);
                role = 0;
                signUpPage.putExtra("userRole", role);
                startActivity(signUpPage);
            }
        });
    }

}
