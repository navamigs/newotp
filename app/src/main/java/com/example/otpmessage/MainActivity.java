package com.example.otpmessage;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity {
    int myotp=(int)(Math.random()*100000);
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"OTP:"+myotp,Toast.LENGTH_LONG).show();
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                //  TextView tv = (TextView) findViewById(R.id.txtview);
                //   tv.setText(message);
               // if ( myotp==) {
                    EditText ev = (EditText) findViewById(R.id.otp);
                    ev.setText(message);
               // }
            }
            }
           };

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}


  /*  int range = 9;  // to generate a single number with this range, by default its 0..9
    int length = 4; // by default length is 4
    Context context;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int otp=(int)(Math.random()*100000);
        Toast.makeText(MainActivity.this,"OTP:"+otp,Toast.LENGTH_LONG).show();


    }
}
       //Toast.makeText(MainActivity.this,"OTP: ",Toast.LENGTH_LONG).show();
        //   generateRandomNumber();

 /*   }
        /*button =findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp == 1357)
                Intent i= new Intent(RovrRuby.this,RegistrationActivity.class);
                startActivity(i);
            }
        });


    }
}
  public int generateRandomNumber () {
            int randomNumber;

            SecureRandom secureRandom = new SecureRandom();
            String s = "";
            for (int i = 0; i < length; i++) {
                int number = secureRandom.nextInt(range);
                if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                    i = -1;
                    continue;
                }
                s = s + number;
            }

            randomNumber = Integer.parseInt(s);
  //       Toast.makeText(MainActivity.this,"OTP: "+randomNumber,Toast.LENGTH_LONG).show();
       // Toast.makeText(getApplicationContext(),"OTP: "+randomNumber,Toast.LENGTH_LONG).show();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("OTP_PIN", randomNumber);
            editor.commit();


            return randomNumber;
        }


    }
*/