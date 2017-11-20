package com.outfit.interview.detector.qahelper;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.outfit.interview.detector.qahelper.adapters.ImageAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Bitmap> bitmapList;
    private static int GALLERY_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        GridView gridView = (GridView)findViewById(R.id.gridGallery);
        ImageAdapter imageAdapter = new ImageAdapter(this, bitmapList);
        gridView.setAdapter(imageAdapter);*/

    }

    public void readImages(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == GALLERY_PICK && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { android.provider.MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String img = cursor.getString(columnIndex);
                cursor.close();

                ImageView imgViewLoad = (ImageView) findViewById(R.id.imageView);
                imgViewLoad.setImageBitmap(BitmapFactory
                        .decodeFile(img));

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
