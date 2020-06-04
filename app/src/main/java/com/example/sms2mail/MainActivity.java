package com.example.sms2mail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int RECEIVE_SMS_PERMSSION_CODE = 100;

    //permissions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) ==
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Receive SMS Permission is Granted!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            RequestReceiveSMSPermission();
        }
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hritikshiwach@gmail.com"));
                intent1.putExtra(Intent.EXTRA_SUBJECT, "from app".toString());
                intent1.putExtra(Intent.EXTRA_TEXT, "TO BE IMPLEENTED");
                startActivity(intent1);


            }
        });
    }
    private void RequestReceiveSMSPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Receive SMS permission is needed to work")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_PERMSSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_PERMSSION_CODE);
        }
    }
    //permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RECEIVE_SMS_PERMSSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Permisaion Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }




}


