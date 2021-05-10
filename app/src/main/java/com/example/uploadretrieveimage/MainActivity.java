package com.example.uploadretrieveimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uploadretrieveimage.data.DatabaseHandler;
import com.example.uploadretrieveimage.data.ModelClass;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private EditText imageDetailsET;
    private ImageView objectImageView;
    Bitmap imageToStore;
    DatabaseHandler objectDatabaseHandler;

    private static  final int PICK_IMAGE_REQUEST = 100;//any number other than 0
    private Uri imageFilepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            imageDetailsET =findViewById(R.id.imageNameET);
            objectImageView =findViewById(R.id.image);

            objectDatabaseHandler = new DatabaseHandler(this);

        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {

                e.printStackTrace();

        }

    }


    public void chooseImage(View view){
        try{
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);

        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData() !=null){
                imageFilepath =data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilepath);

                objectImageView.setImageBitmap(imageToStore);
            }


        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    public void storeImage(View view){
        try{
            if(!imageDetailsET.getText().toString().isEmpty() && objectImageView.getDrawable() != null && imageToStore !=null)
            {
                objectDatabaseHandler.storeImage(new ModelClass(imageDetailsET.getText().toString(),imageToStore));
            }
            else{
                Toast.makeText(this,"Please select image name and image" , Toast.LENGTH_SHORT).show();
            }



        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void move(View view){
        Intent intent = new Intent(MainActivity.this,ShowImage.class);
        startActivity(intent);
    }



}