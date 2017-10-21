package com.example.myloginapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.io.InputStream;

public class UserProfile extends AppCompatActivity {

    TextView nameAndSurname;
    ImageView profileImage;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        nameAndSurname = (TextView) findViewById(R.id.nameAndSurname);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent i = new Intent(UserProfile.this, LoginActivity.class);
                startActivity(i);

            }
        });


         Intent intent = getIntent();
         String firstname = intent.getExtras().getString("firstname");
         String lastname =  intent.getExtras().getString("lastname");
         String userimage = intent.getExtras().getString("userimage");
         nameAndSurname.setText(firstname +" " +lastname);

         Uri uri = Uri.parse(intent.getExtras().getString("userimage"));
        new UserProfile.DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(userimage);
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView bmImage;
        public DownloadImage(ImageView bmImage){
          this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try
            {
                InputStream in= new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;

        }
        protected void onPostExecute(Bitmap result){

            bmImage.setImageBitmap(result);
        }
    }


}

