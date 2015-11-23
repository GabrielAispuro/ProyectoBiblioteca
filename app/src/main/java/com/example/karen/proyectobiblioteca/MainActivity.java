package com.example.karen.proyectobiblioteca;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.MediaController;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.parse.Parse;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Parse.initialize(this, "9cP5O7VWJLvtP1pNZfB2oiGSloV3KsKbYLbMe2og", "jGra9Hzc9Wj4jfVU28DSxcTnoiyBJkMQtokq1K5E");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //bloquea la orientacion de la pantalla

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch (position){
            case 0:
                //me permite remplazar e fragmento actual por otra
                fragment = PlaceholderFragment.newInstance(position + 1);
                break;
            case 1:

                fragment = LibrosFragment.newInstance(position + 1, "Arte");
                break;
            case 2:
                fragment = LibrosFragment.newInstance(position + 1, "CAE");
                break;
            case 3:
                fragment = LibrosFragment.newInstance(position + 1, "Negocios");
                break;
            case 4:
                fragment = LibrosFragment.newInstance(position + 1, "Historia");
                break;
            case 5:
                fragment = LibrosFragment.newInstance(position + 1, "Poesia");
                break;
            case 6:
                fragment = LibrosFragment.newInstance(position + 1, "Literatura");
                break;

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_inicio);
                break;
            case 2:
                mTitle = getString(R.string.title_arte);
                break;
            case 3:
                mTitle = getString(R.string.title_cae);
                break;
            case 4:
                mTitle = getString(R.string.title_negocios);
                break;
            case 5:
                mTitle = getString(R.string.title_historia);
                break;
            case 6:
                mTitle = getString(R.string.title_poesia);
                break;
            case 7:
                mTitle = getString(R.string.title_literatura);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

        if (id == R.id.action_users) {
            String geopoint = "geo:22.257446,-97.829867?q=22.257446,-97.829867";
            Uri gmmIntentUri = Uri.parse(geopoint);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static boolean isLogged;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            Uri video = Uri.parse("android.resource://"+ getActivity().getPackageName()+"/"+R.raw.video);

            TextView txtViewMision = (TextView) rootView.findViewById(R.id.txtView_mision_contenido);
            TextView txtViewVision = (TextView) rootView.findViewById(R.id.txtView_vision_contenido);;
            TextView txtViewObjetivo = (TextView) rootView.findViewById(R.id.txtView_objetivo_contenido);;
            Button google = (Button) rootView.findViewById(R.id.button_google_map);
            google.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String geopoint = "geo:22.257446,-97.829867?q=22.257446,-97.829867";
                    Uri gmmIntentUri = Uri.parse(geopoint);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });

            Button agregar = (Button) rootView.findViewById(R.id.button_agregar);
            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(),AgregarActivity.class);
                    startActivity(intent);

                }
            });

            final VideoView videoViewInicio = (VideoView) rootView.findViewById(R.id.video_view_inicio);
            videoViewInicio.setVideoURI(video);

            MediaController media = new MediaController(getActivity().getApplicationContext());
            media.setAnchorView(videoViewInicio);
            videoViewInicio.setMediaController(media);
            videoViewInicio.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                   // videoViewInicio.start();
                }
            });

            txtViewMision.setText("- Apoyar y desarrollar programas nacionales e internacionales de preservación del patrimonio bibliográfico nacional.\n" +
                    "- Proporcionar servicios de consulta, lectura, información y acceso, considerando la referencia al conjunto de los recursos bibliográficos nacionales.\n" +
                    "- Divulgar el contenido de sus colecciones mediante: catálogos, bibliografías, folletos, exposiciones, conferencias, páginas web, visitas guiadas y otros medios.\n" +
                    "- Adquirir las obras sobre México editadas o producidas en el extranjero.");

            txtViewVision.setText("- Compilar la bibliografía nacional.\n" +
                    "- Facilitar el acceso a la información y a los documentos resguardados.\n" +
                    "- Resguardar la memoria bibliográfica de México.");

            txtViewObjetivo.setText("- Reunir, organizar, preservar y difundir la memoria bibliográfica y documental del país, con el fin de apoyar el desarrollo científico, educativo y cultural de México.\n" +
                    "- Concentrar, custodiar y hacer accesibles los materiales editados en diversos soportes, y que integran el patrimonio bibliográfico de la nación.");

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
