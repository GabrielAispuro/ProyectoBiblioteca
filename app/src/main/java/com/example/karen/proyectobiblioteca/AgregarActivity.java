package com.example.karen.proyectobiblioteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class AgregarActivity extends ActionBarActivity {

    private EditText titulo;
    private EditText autor;
    private ImageView camara;
    private Button pedido;
    private Bitmap img;
    private ParseFile imagen;
    private static final int CAPTURE_IMAGE_CAPTURE_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        getSupportActionBar().setTitle("Pedido");

        titulo = (EditText) findViewById(R.id.editTxt_titulo);
        autor = (EditText) findViewById(R.id.editTxt_autor);
        camara = (ImageView) findViewById(R.id.camera_button);
        pedido = (Button) findViewById(R.id.button_agregar_pedido);

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there is a camera.
                Context context = AgregarActivity.this;
                PackageManager packageManager = context.getPackageManager();
                if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
                    Toast.makeText(context, "This device does not have a camera.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);
                // Setting Dialog Title
                alertDialog.setTitle("Escoge una opción");
                // Setting Dialog Message
                alertDialog.setMessage("¿De dónde vendrá la imagen?");
                alertDialog.setPositiveButton("Camara",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Camera exists? Then proceed...
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CAPTURE_IMAGE_CAPTURE_CODE);
                            }
                        });
                alertDialog.setNegativeButton("Galería",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event
                                //flag = false;
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
            }
        });

        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloLibro = titulo.getText().toString();
                String autorLibro = autor.getText().toString();
                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG, 100, blob);
                byte[] imgArray = blob.toByteArray();
                imagen = new ParseFile("pedido.jpeg", imgArray);

                ParseObject pedidoLibro = new ParseObject("Pedido_libros");
                pedidoLibro.put("titulo", tituloLibro);
                pedidoLibro.put("autor", autorLibro);
                pedidoLibro.put("imagen", imagen);

                pedidoLibro.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            Toast.makeText(AgregarActivity.this, "Has guardado tu reporte con exito. ¡Gracias por tu contribución!",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        } else{

                            Toast.makeText(AgregarActivity.this, "Error al intentar guardar",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 400);
        params.gravity = Gravity.CENTER;

        if (requestCode == CAPTURE_IMAGE_CAPTURE_CODE ) {

            if (resultCode == Activity.RESULT_OK) {
                img = (Bitmap) data.getExtras().get("data");
                camara.setLayoutParams(params);
                camara.setImageBitmap(img);
                Toast.makeText(AgregarActivity.this, "¡Imagen Capturada!", Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(AgregarActivity.this, "¡Has cancelado!", Toast.LENGTH_LONG).show();
            }

        } else if(requestCode == PICK_IMAGE_REQUEST){

            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = AgregarActivity.this.getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();


                img = BitmapFactory.decodeFile(filePath);
                camara.setLayoutParams(params);
                camara.setImageBitmap(img);
                Toast.makeText(AgregarActivity.this, "¡Imagen Capturada!", Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(AgregarActivity.this, "¡Has cancelado!", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
