package edu.scu.mapproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DiscoverySettingsPage extends Activity {

    private RadioButton male;
    private RadioButton female;
    private RadioGroup rg;
    private CheckBox both;
    private SeekBar age;
    private SeekBar location;
    private TextView ageValue;
    private TextView locationValue;
    private int age_value;
    private int location_value;
    private Button back;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_settings_page);
        male = (RadioButton) findViewById(R.id.genderMale);
        female = (RadioButton) findViewById(R.id.genderFemale);
        rg = (RadioGroup) findViewById(R.id.genderGroup);
        both = (CheckBox) findViewById(R.id.genderBoth);
        age = (SeekBar) findViewById(R.id.ageValue);
        location = (SeekBar) findViewById(R.id.locationValue);
        ageValue = (TextView) findViewById(R.id.txt_ageValue);
        locationValue = (TextView) findViewById(R.id.txt_locationValue);
        back = (Button) findViewById(R.id.filterButtonBack);
        confirm = (Button) findViewById(R.id.filterButtonConfirm);

        both.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (both.isChecked()) {
                    rg.clearCheck();
                } else
                {
                    male.setChecked(true);
                    both.setChecked(false);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(DiscoverySettingsPage.this, StudentsListActivity.class);
                startActivity(mainPage);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age_value = progress / 1;
                ageValue.setText(String.valueOf(age_value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        location.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                location_value = progress / 1;
                locationValue.setText(String.valueOf(location_value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
