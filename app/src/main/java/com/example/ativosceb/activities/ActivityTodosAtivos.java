package com.example.ativosceb.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ativosceb.model.Categoria;
import com.example.ativosceb.service.APIAtivosCEB;
import com.example.ativosceb.R;
import com.example.ativosceb.adapters.AdapterListAtivos;
import com.example.ativosceb.model.Ativo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ActivityTodosAtivos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView labelIdAtivo;
    private TextView labelItem;
    private ListView listViewTodosAtivos;
    private List<Ativo> listaAtivos = new ArrayList<>();
    private AdapterListAtivos adapterListaAtivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_ativos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //GAMBIARRA TEMPORÁRIA PARA RESOLVER ACESSO À INTERNET
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            }
            else {
                carregarAtivos();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class API extends AsyncTask<Void, Void, List<Ativo>>{

        @Override
        protected List<Ativo> doInBackground(Void... voids) {
            List<Ativo> ativos = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL("http://webativos.gearhostpreview.com/api/ativo");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                Scanner scanner = new Scanner(url.openStream());
                while ((scanner.hasNext())) {
                    resposta.append(scanner.next() + " ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            try {
                JSONArray array = new JSONArray(resposta.toString());
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    Ativo ativo = new Ativo(object.optInt("idAtivo"), object.optString("item"));
                    ativos.add(ativo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ativos;
        }
    }

    private void carregarAtivos() throws JSONException, IOException, ExecutionException, InterruptedException {
        API api = new API();
        this.listaAtivos = api.execute().get();
        this.listViewTodosAtivos = (ListView) findViewById(R.id.ListViewTodosAtivos);
        this.adapterListaAtivos = new AdapterListAtivos(ActivityTodosAtivos.this, this.listaAtivos);
        this.listViewTodosAtivos.setAdapter(this.adapterListaAtivos);
    }

    public void onRequestPermissionResult(int requestCode, String permissions[], int [] granResults) throws JSONException, IOException, ExecutionException, InterruptedException {
        switch (requestCode){
            case 1:{
                if(granResults.length > 0 && granResults[0] == PackageManager.PERMISSION_GRANTED){
                    carregarAtivos();
                }
                else {
                    Toast.makeText(this, "ERRO DE PERMISSÃO DE INTERNET", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_todos_ativos, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_NovoAtivo) {
            Intent intent = new Intent(ActivityTodosAtivos.this, ActivityNovoAtivo.class);
            startActivity(intent);
        } else if (id == R.id.nav_ListaAtivos) {
            Intent intent = new Intent(ActivityTodosAtivos.this, ActivityListaAtivos.class);
            startActivity(intent);
        } else if (id == R.id.nav_Localizar) {
            Intent intent = new Intent(ActivityTodosAtivos.this, ActivityBusca.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
