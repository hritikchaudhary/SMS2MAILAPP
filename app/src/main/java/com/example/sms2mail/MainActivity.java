package com.example.sms2mail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int RECEIVE_SMS_PERMSSION_CODE = 100;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String msg;

    SmsReceiver receiver = new SmsReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            msg = strMessage;
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if(wifiManager.isWifiEnabled()) {
                sendMail();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SMS_RECEIVED));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

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

    private void sendMail() {

       Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hritikshiwach7@gmail.com"));
       //emailIntent.setType("message/rfc822");
       emailIntent.putExtra(Intent.EXTRA_SUBJECT, "auto gen");
       emailIntent.putExtra(Intent.EXTRA_TEXT,msg);
       startActivity(emailIntent);

//       try {
//           startActivity(Intent.createChooser(emailIntent, "gmail"));
//           finish();
//           Log.i("Finished sending email...","");
//       }catch (android.content.ActivityNotFoundException ex)
//       {
//           Toast.makeText(MainActivity.this, "There is no email client..",Toast.LENGTH_SHORT).show();
//       }


    }




}



