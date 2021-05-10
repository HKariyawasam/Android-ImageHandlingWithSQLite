package com.example.uploadretrieveimage;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uploadretrieveimage.data.DatabaseHandler;
import com.example.uploadretrieveimage.data.ModelClass;

public class ShowImage extends AppCompatActivity {

    TextView imageViewTV;
    ImageView imageView;
    ModelClass objectModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        imageViewTV = findViewById(R.id.imageNameTV);
        imageView =findViewById(R.id.imageView);

        getImage();

        imageViewTV.setText(objectModelClass.getImageName());
        imageView.setImageBitmap(objectModelClass.getImage());
    }

    public void getImage(){
        DatabaseHandler db = new DatabaseHandler(this);
        objectModelClass = db.getObject();
    }

}