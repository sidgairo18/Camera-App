package com.example.camera;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class DisplayImages extends Activity implements OnClickListener {

    /** Called when the activity is first created. */

    int image_index = 0;
    private int MAX_IMAGE_COUNT;
    public File[] imageFiles;


    //public class variables
    public String dir_name="images";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayimages);


        // list images in the directory
        File dir =  new File(Environment.getExternalStorageDirectory(), "Camera/"+dir_name);
        imageFiles = dir.listFiles();
        Arrays.sort(imageFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        MAX_IMAGE_COUNT = imageFiles.length;

        // previous
        Button btnPrevious = (Button)findViewById(R.id.previous_btn);
        btnPrevious.setOnClickListener(this);

        // next
        Button btnNext = (Button)findViewById(R.id.next_btn);
        btnNext.setOnClickListener(this);

        // close
        Button close = (Button)findViewById(R.id.close);
        close.setOnClickListener(this);

        showImage();

    }

    private void showImage() {

        // get photoview
        PhotoView imgView = (PhotoView) findViewById(R.id.myimage);
        imgView.setImageURI(Uri.fromFile(imageFiles[image_index]));
        // set prompt text and Image view
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Image "+(image_index+1)+"/"+MAX_IMAGE_COUNT);

    }

    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.previous_btn):

                image_index--;

                if (image_index == -1) {
                    image_index = MAX_IMAGE_COUNT - 1;
                }

                showImage();

                break;

            case (R.id.next_btn):

                image_index++;

                if (image_index == MAX_IMAGE_COUNT) {
                    image_index = 0;
                }

                showImage();

                break;

            case (R.id.close):

                Intent intent = new Intent(this, Camera.class);

                //finish current activity
                finish();
                //start the second Activity
                this.startActivity(intent);
                break;
        }
    }
}