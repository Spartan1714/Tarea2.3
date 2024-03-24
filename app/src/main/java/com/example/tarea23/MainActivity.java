package com.example.tarea23;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import Conexion.SQLiteConexion;

public class MainActivity extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private ImageView imageView;
    private Button btnSaveImage, btnChooseImage;
    private byte[] imageByteArray;
    private SQLiteConexion sqLiteConexion;
    private Button btnGoToRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteConexion = new SQLiteConexion(this);

        imageView = findViewById(R.id.imageView);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSaveImage = findViewById(R.id.btnSaveImage);
        btnGoToRegistro = findViewById(R.id.btnGoToRegistro);

        btnGoToRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Registro
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la galería para elegir una imagen
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para guardar la imagen aquí
                if (imageByteArray != null) {
                    saveImageToDatabase(imageByteArray); // Aquí se intenta guardar la imagen en la base de datos
                }
            }
        });

    }

    private void saveImageToDatabase(byte[] imageBytes) {
        try {
            // Llamar al método insertImage(byte[] imageBytes) de la clase SQLConexion
            sqLiteConexion.insertImage(imageBytes);
            // Mostrar un mensaje de éxito
            showToast("Imagen guardada correctamente en la base de datos");
        } catch (SQLException e) {
            Log.e("MainActivity", "Error al guardar la imagen en la base de datos: " + e.getMessage());
            e.printStackTrace();
            // Mostrar un mensaje de error
            showToast("Error al guardar la imagen en la base de datos");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la imagen seleccionada de la galería
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageView.setImageBitmap(bitmap);

                // Convertir la imagen a un arreglo de bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageByteArray = stream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
