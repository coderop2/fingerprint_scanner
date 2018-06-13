package com.example.android.fingerprint;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.media.audiofx.BassBoost;
import android.opengl.Visibility;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.multidots.fingerprintauth.*;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private Button button;
    private ViewSwitcher mviewswitcher;
    private EditText mEditText;
    private TextView textView;
    private FingerPrintAuthHelper fpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.search_settings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(i);
            }
        });
        mviewswitcher = (ViewSwitcher) findViewById(R.id.main_switcher);
        textView=(TextView)findViewById(R.id.auth_message);
        mEditText=(EditText)findViewById(R.id.pin_edit);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("1234"))
                {
                    Toast.makeText(MainActivity.this,"authentication succesful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, fingerprintauth.class));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fpa=FingerPrintAuthHelper.getHelper(this,this);

    }

    @Override
    protected void onResume() {
        button.setVisibility(View.GONE);
        textView.setText("Pls Scan your Finger");
        fpa.startAuth();
        if(findViewById(R.id.pin_edit).isShown())
            mviewswitcher.showPrevious();
        super.onResume();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        textView.setText("No fingerPrint hardware funnd pls type the password in the blank field");
        mviewswitcher.showNext();

    }

    @Override
    public void onNoFingerPrintRegistered() {
        textView.setText("There are no finger prints registered on this device. Please register your finger from settings.");
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBelowMarshmallow() {
        textView.setText("You are running older version of android that does not support finger print authentication. Please type 1234 to authenticate.");
        mviewswitcher.showNext();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(MainActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, fingerprintauth.class));
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                textView.setText("Cannot recognize your finger print. Please try again.");
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                textView.setText("Cannot initialize finger print authentication. Please type 1234 to authenticate.");
                mviewswitcher.showNext();
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                textView.setText(errorMessage);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNum=item.getItemId();
        if(itemNum==R.id.next)
            mviewswitcher.showPrevious();
        return super.onOptionsItemSelected(item);
    }
}
