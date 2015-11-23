package com.example.karen.proyectobiblioteca;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Gabriel Aispuro on 25/07/2015.
 */
public class ListLibrosAdapter extends BaseAdapter{

    protected List<ParseObject> result;
    private Context context;
    LayoutInflater inflater;

    public ListLibrosAdapter(Context context, List<ParseObject> result){

        this.result = result;
        this.inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.layout_list_item, parent, false);

            holder.txtAutor = (TextView) convertView.findViewById(R.id.txt_autor);
            holder.txtTitulo = (TextView) convertView.findViewById(R.id.txt_titulo);
            holder.img = (ParseImageView) convertView.findViewById(R.id.img_libro);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject libro = result.get(position);                  //Se guarda el primer ParseObject que es un libro en la base de datos con toda su informacion y junto con el pointer a la clase Imagen
        ParseObject imgParse = new ParseObject("Imagen");          //Se crea un objeto de tipo ParseObject y se le manda como parametro que contendra un objeto de la base de datos de la clase Imagen
        imgParse = libro.getParseObject("id_imagen");              //Se obtiene el objeto parse,el cual es un File que a su vez es una imagen, del anterior creado, donde contendra la imagen
        holder.txtAutor.setText(libro.getString("autor"));
        holder.txtTitulo.setText(libro.getString("titulo"));       //Se modifica el texto a mostrar en el TextView
        holder.img.setParseFile(imgParse.getParseFile("imagen"));  //Se modifica la imagen a mostrar en el ParseImageView
        holder.img.loadInBackground(new GetDataCallback(){         //Metodo por el cual cargaremos la imagen para que se muestre

                                        @Override
                                        public void done(byte[] bytes, com.parse.ParseException e) {
                                            Log.i("ParseImageView", "Data length " + bytes.length);
                                        }
                                    }
        );

        return convertView;

    }

    private class ViewHolder{
        TextView txtTitulo;
        TextView txtAutor;
        ParseImageView img;
    }

}
