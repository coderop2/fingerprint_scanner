package com.example.android.fingerprint;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.squareup.picasso.Picasso;

/**
 * Created by harshitkhandelwal on 19/01/18.
 */

public class fingerprintauth extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private String imageURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singletext);
        mImageView=(ImageView)findViewById(R.id.android_image);
        mTextView=(TextView)findViewById(R.id.auth_succ);

        imageURL="http://st1.vchensubeswogfpjoq.netdna-cdn.com/wp-content/uploads/2013/11/Android-ImageView-Example.jpg";
        Picasso.with(fingerprintauth.this)
                .load(imageURL)
                .resize(100,250)
                .into(mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_image,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid=item.getItemId();
        switch(itemid)
        {
            case R.id.image_fade:
                Picasso.with(fingerprintauth.this)
                        .load(imageURL)
                        .noFade()
                        .into(mImageView);
                Toast.makeText(fingerprintauth.this,"noFading successful",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_rotate:
                Picasso.with(fingerprintauth.this)
                        .load(imageURL)
                        .rotate(90f)
                        .into(mImageView);
                Toast.makeText(fingerprintauth.this,"Rotation successful",Toast.LENGTH_SHORT).show();
                break;
            default:
                mTextView.setText("No such thing exists");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
