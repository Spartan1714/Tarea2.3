package Conexion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Transacciones {
    public static final String TablePersonas = "personas";
    public static final String id = "id";

    public static final String nota = "nota";
    public static final String imagen = "imagen";

    private static SQLiteConexion dbHelper;

    //private android.content.Context Context;


    public Transacciones(Context context) {
        dbHelper = new SQLiteConexion(context);
    }

    public boolean insertarContacto(String nombre, String telefono, String nota, String imagen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("imagen", imagen);

        try {
            long result = db.insertOrThrow("personas", null, values);
            db.close();
            return result != -1;
        } catch (SQLException e) {
            // Captura la excepción y muestra el mensaje de error
            Log.e("Transacciones", "Error al insertar contacto: " + e.getMessage());
            return false;
        }
    }

    public static final String CreateTablePersonas =
            "CREATE TABLE " + TablePersonas + " (" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    nota + " TEXT, " +
                    "imagen TEXT)";
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + TablePersonas;


    public static Cursor obtenerTodosLosContactos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM personas", null);
    }


    public static void eliminarContacto(String nombreContacto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TablePersonas, id + " = ?", new String[]{nombreContacto});
        db.close();
    }


    public boolean actualizarContacto(String nombreAnterior, String nuevoNombre, String nuevoTelefono, String nuevaNota, String rutaImagen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nota", nuevaNota);
        values.put("imagen", rutaImagen);

        // Actualizar el registro en la base de datos
        int filasActualizadas = db.update("personas", values, "nombre = ?", new String[]{nombreAnterior});
        db.close();

        // Verificar si se actualizó al menos una fila
        return filasActualizadas > 0;
    }
}










