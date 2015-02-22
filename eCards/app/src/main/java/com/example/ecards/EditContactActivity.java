package com.example.ecards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lenovo-USER on 2/22/2015.
 */
public class EditContactActivity extends Activity {

    private PagerAdapter mPagerAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

    }


    public void save(View v) {
        String tmp = "";

        TextView fname = (TextView)findViewById(R.id.user_fname);
        TextView lname = (TextView)findViewById(R.id.user_lname);
        TextView phone = (TextView)findViewById(R.id.user_phonenumber);
        TextView email = (TextView)findViewById(R.id.user_email);
        TextView linkedin = (TextView)findViewById(R.id.user_linkedin);
        TextView github = (TextView)findViewById(R.id.user_github);

        tmp += fname.getText() + "," + lname.getText() + "," + phone.getText() + "," + email.getText() + "," + linkedin.getText() + "," + github.getText();

        String FILENAME = "users.txt";
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(tmp.getBytes());
            fos.close();
        } catch (IOException e) {

        }
    }

    public void chooseImage(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    private byte[] extractBytes (String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);

        Bitmap mybmp = BitmapFactory.decodeFile(imgPath.getAbsolutePath());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mybmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

}