package com.example.karen.proyectobiblioteca;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowLibrosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowLibrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowLibrosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private static List<ParseObject> result;
    private static int posicionLibro;
    private TextView titulo;
    private TextView autor;
    private TextView genero;
    private TextView pag;
    private TextView sinopsis;
    private ParseImageView parseImg;

    private OnFragmentInteractionListener mListener;


    public static ShowLibrosFragment newInstance(int param1, int posicion, List<ParseObject> list) {
        ShowLibrosFragment fragment = new ShowLibrosFragment();
        result = list;
        posicionLibro = posicion;
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowLibrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_libros, container, false);

        titulo = (TextView) view.findViewById(R.id.txtView_titulo);
        autor = (TextView) view.findViewById(R.id.txtView_autor_contenido);
        genero = (TextView) view.findViewById(R.id.txtView_genero_contenido);
        pag = (TextView) view.findViewById(R.id.txtView_pag_contenido);
        sinopsis = (TextView) view.findViewById(R.id.txtView_sinopsis_contenido);
        parseImg = (ParseImageView) view.findViewById(R.id.parseImageView_img_libro);

        ParseObject libro = result.get(posicionLibro);
        ParseObject img = new ParseObject("Imagen");
        img = libro.getParseObject("id_imagen");

        titulo.setText(libro.getString("titulo"));
        autor.setText(libro.getString("autor"));
        genero.setText(libro.getString("genero"));
        pag.setText(libro.getInt("num_pag")+ "");
        sinopsis.setText(libro.getString("sinopsis"));
        parseImg.setParseFile(img.getParseFile("imagen"));
        parseImg.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, com.parse.ParseException e) {
                Log.i("ParseImageView", "Data length " + bytes.length);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_PARAM1));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
