package com.outfit.interview.detector.qahelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.outfit.interview.detector.qahelper.adapters.ImageAdapter;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Bitmap> bitmapList;
    private static int GALLERY_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void readImages(View view){

        Intent imagePicker = new Intent(Intent.ACTION_GET_CONTENT);

        imagePicker.setType("image/*");
        startActivityForResult(imagePicker, GALLERY_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == GALLERY_PICK && resultCode == RESULT_OK
                    && null != data && null != data.getData()) {

                Uri URI = data.getData();
                dialogSendImg();

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void dialogSendImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Send image?");

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "dejan47@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Android test.");
                email.putExtra(Intent.EXTRA_TEXT, deviceData());

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //nada
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String deviceData() {
        String result = "";

        result += "Manufacturer: " + Build.MANUFACTURER +
                "\nDevice: " + Build.MODEL;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);

        result += "Pixel width: " + dm.widthPixels;
        result += "Pixel height: " + dm.heightPixels;
        result += "Dp width: " + dm.widthPixels/dm.density + " " + dm.xdpi;
        result += "Dp height: " + dm.heightPixels/dm.density + " " + dm.ydpi;
        result += "Inches width: " + x;
        result += "Inches height: " + y;
        result += "Inches: " + Math.sqrt(x+y);
        result += "Density: " + dm.density;

        return result;
    }


}
