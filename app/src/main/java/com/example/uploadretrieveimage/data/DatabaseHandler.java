package com.example.uploadretrieveimage.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME="mydb.db";
    private static int DATABASE_VERSION =1;
    private static  String createTableQuery ="create table ImageInfo (imageName TEXT"+",image BLOB)";
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInByte;



    public DatabaseHandler( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(createTableQuery);
            Toast.makeText(context, "Table created successfully inside our database", Toast.LENGTH_SHORT).show();
            
        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void storeImage(ModelClass objectModelClass){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);

            imageInByte =objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();

            objectContentValues.put("imageName",objectModelClass.getImageName());
            objectContentValues.put("image",imageInByte);

            long checkIfQueryRuns = db.insert("imageInfo",null,objectContentValues);

            if(checkIfQueryRuns !=-1){
                Toast.makeText(context, "Data added to our table", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Data not added to our table", Toast.LENGTH_SHORT).show();
            }


        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public ModelClass getObject() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ModelClass oModelClass = new ModelClass();
            String[] selectionArgs = {"image1"};
            Cursor objectC = db.rawQuery("select * from imageInfo where imageName = ? ",selectionArgs);
            if(objectC.getCount() !=0){
                objectC.moveToFirst();

                String nameOfImage = objectC.getString(0);
                byte [] imageByte = objectC.getBlob(1);

                Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                oModelClass.setImageName(nameOfImage);
                oModelClass.setImage(objectBitmap);

                return  oModelClass;

            }else{
                Toast.makeText(context, "No values", Toast.LENGTH_SHORT).show();
                return  null;
            }

        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }


    }
}
