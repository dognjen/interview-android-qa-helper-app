package com.outfit.interview.detector.qahelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.outfit.interview.detector.qahelper.utils.QAHelperUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Bitmap> bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // CHeck if request has relevant request code
        if (requestCode == QAHelperUtils.GALLERY_IMG_PICK && resultCode == RESULT_OK
                && null != data && null != data.getData()) {

            Uri URI = data.getData();
            dialogSendImg(URI);
        }
    }


    public void readImages(View view){

        // Create intent
        Intent imagePicker = new Intent(Intent.ACTION_GET_CONTENT);
        imagePicker.setType(QAHelperUtils.GALLERY_TYPES);

        // start activity
        startActivityForResult(imagePicker, QAHelperUtils.GALLERY_IMG_PICK);
    }


    private void dialogSendImg(final Uri URI) {

        // set dialog on activity
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Send image?");

        // create listener on yes
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ });
                email.putExtra(Intent.EXTRA_SUBJECT, "Android test.");
                email.putExtra(Intent.EXTRA_TEXT, deviceData());
                email.putExtra(Intent.EXTRA_STREAM, URI);

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client:"));

                dialog.dismiss();
            }
        });

        // create listener on no
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String deviceData() {

        StringBuilder sb = new StringBuilder();

        // Manufacturer data
        sb.append("Manufacturer: ");
        sb.append(Build.MANUFACTURER);
        sb.append('\n');
        sb.append("Device: ");
        sb.append(Build.MODEL);
        sb.append('\n');
        sb.append('\n');

        // Get display
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // calculate other metrics
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);

        // Display metrics
        sb.append("Display metrics ");
        sb.append("Pixel width: ");
        sb.append(dm.widthPixels);
        sb.append('\n');
        sb.append("Pixel height: ");
        sb.append(dm.heightPixels);
        sb.append('\n');
        sb.append("Dp width: ");
        sb.append(dm.widthPixels/dm.density + " " + dm.xdpi);
        sb.append('\n');
        sb.append("Dp height: ");
        sb.append(dm.heightPixels/dm.density + " " + dm.ydpi);
        sb.append('\n');
        sb.append("Screen inches: ");
        sb.append(Math.sqrt(x+y));
        sb.append('\n');
        sb.append("Screen density: ");
        sb.append(dm.density);

        return sb.toString();
    }


}
