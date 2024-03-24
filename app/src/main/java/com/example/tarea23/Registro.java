package com.example.tarea23;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import Conexion.SQLiteConexion;

public class Registro extends AppCompatActivity {

    private RecyclerView recyclerViewImages;
    private SQLiteConexion sqLiteConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        sqLiteConexion = new SQLiteConexion(this);

        try {
            // Obtener todas las imágenes de la base de datos
            List<byte[]> imageList = sqLiteConexion.getAllImages();

            if (imageList.isEmpty()) {
                Toast.makeText(this, "No hay imágenes en la base de datos", Toast.LENGTH_SHORT).show();
            } else {
                // Configurar RecyclerView
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerViewImages.setLayoutManager(layoutManager);

                // Crear y configurar adaptador personalizado
                ImageAdapter adapter = new ImageAdapter(this, imageList);
                recyclerViewImages.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar la actividad Registro", Toast.LENGTH_SHORT).show();
        }
    }
}
