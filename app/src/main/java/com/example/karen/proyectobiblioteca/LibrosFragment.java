package com.example.karen.proyectobiblioteca;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class LibrosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private static int mParam1;
    private String genero;
    private View view;
    private ListView listViewLibros;
    private ImageView imgViewHeader;

    private OnFragmentInteractionListener mListener;

    public static LibrosFragment newInstance(int param1, String genero) {
        LibrosFragment fragment = new LibrosFragment();
        mParam1 = param1;
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString("genero", genero);
        fragment.setArguments(args);
        return fragment;
    }

    public LibrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            genero = getArguments().getString("genero");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_libros, container, false);
        listViewLibros = (ListView) view.findViewById(R.id.list_libros);
        imgViewHeader = (ImageView) view.findViewById(R.id.imageView_header);

        switch (genero){

            case "Arte":
                imgViewHeader.setImageDrawable(getResources().getDrawable(R.drawable.arte_logo));
                break;

            case "CAE":
                imgViewHeader.setImageDrawable(getResources().getDrawable(R.drawable.logocae));
                break;

            case "Negocios":
                imgViewHeader.setImageDrawable(getResources().getDrawable(R.drawable.logo_negocios));
                break;

            case "Historia":
                imgViewHeader.setImageDrawable(getResources().getDrawable(R.drawable.logo_historia));
                break;

            case "Poesia":
                imgViewHeader.setImageDrawable(getResources().getDrawable(R.drawable.logo_poesia));
                break;

            case "Literatura":
                imgViewHeader.setImageDrawable(getResources().getDrawable(R.drawable.logo_literatura));
                break;

        }

        //Se obtiene la informacion de la plataforma parse.com
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Libros");
        query.whereEqualTo("genero", genero);
        query.include("id_imagen");

        //Se guarda la consulta en una lista de objetos de tipo Parse
        List<ParseObject> result = null;
        try {

            result = query.find();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Se crea el adaptador para la ListView
        ListLibrosAdapter adapter = new ListLibrosAdapter(getActivity(), result);
        listViewLibros.setAdapter(adapter);

        final List<ParseObject> finalResult = result;
        listViewLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShowLibrosFragment fragment = ShowLibrosFragment.newInstance(mParam1, position, finalResult);
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container,fragment);
                ft.addToBackStack("replacingLibrosFragment");
                ft.commit();

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
