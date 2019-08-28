package com.example.ativosceb.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ativosceb.R;
import com.example.ativosceb.adapters.AdapterListAtivos;
import com.example.ativosceb.model.Ativo;
import com.example.ativosceb.model.Categoria;
import com.example.ativosceb.service.APIAtivosCEB;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

public class ActivityAtivosCategoria extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Categoria> listaCategorias = new ArrayList<>();;
    private Spinner spinnerCategorias;
    private ListView listViewAtivosCategoria;
    private List<Ativo> listaAtivosCategoria = new ArrayList<>();
    private AdapterListAtivos adapterListaAtivosCategoria;
    private Integer idCategoriaSelecionada;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos_categoria);
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

        this.spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        this.btnBuscar = (Button) findViewById(R.id.btnBuscarAtivosCategoria);

        ClickBotaoBuscarAtivos();

        try {
            popularSpinner();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class APIAtivosPorCategoria extends AsyncTask<Void, Void, List<Ativo>>{

        @Override
        protected List<Ativo> doInBackground(Void... voids) {
            List<Ativo> lista = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "ativo?idCategoria=" + idCategoriaSelecionada);
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
                    lista.add(ativo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }
    }

    private class APICategoria extends AsyncTask<Void, Void, List<Categoria>>{

        @Override
        protected List<Categoria> doInBackground(Void... voids) {
            List<Categoria> lista = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "categoria");
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
                    Categoria categoria = new Categoria(object.optInt("idCategoria"), object.optString("descCategoria"));
                    lista.add(categoria);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }
    }

    private void ClickBotaoBuscarAtivos(){
        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    carregarAtivosPorCategoria();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void carregarAtivosPorCategoria() throws JSONException, IOException, ExecutionException, InterruptedException {
        APIAtivosPorCategoria api = new APIAtivosPorCategoria();
        this.listaAtivosCategoria = api.execute().get();
        this.listViewAtivosCategoria = (ListView) findViewById(R.id.ListViewAtivosCategoria);
        this.adapterListaAtivosCategoria = new AdapterListAtivos(ActivityAtivosCategoria.this, this.listaAtivosCategoria);
        this.listViewAtivosCategoria.setAdapter(adapterListaAtivosCategoria);
        this.listViewAtivosCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView labelID = (TextView) view.findViewById(R.id.labelID);
                ActivityDetalhesAtivos.idAtivoClicadoLista = Integer.parseInt(labelID.getText().toString());
                ActivityDetalhesAtivos.telaOrigem = "telaLista";
                Intent intent = new Intent(ActivityAtivosCategoria.this, ActivityDetalhesAtivos.class);
                startActivity(intent);
            }
        });
    }

    public List<Categoria> carregarCategorias() throws ExecutionException, InterruptedException {
        APICategoria api = new APICategoria();
        listaCategorias = api.execute().get();
        return listaCategorias;
    }

    private void popularSpinner() throws ExecutionException, InterruptedException {
        final ArrayAdapter<Categoria> adapterSpinner = new ArrayAdapter<Categoria>(ActivityAtivosCategoria.this, android.R.layout.simple_spinner_item, carregarCategorias());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapterSpinner);
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Categoria categoria = (Categoria) adapterView.getItemAtPosition(i);
                idCategoriaSelecionada = categoria.getIdCategoria();
                //Toast.makeText(view.getContext(), "ID: " + categoria.getIdCategoria(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        getMenuInflater().inflate(R.menu.activity_ativos_categoria, menu);
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
            Intent intent = new Intent(ActivityAtivosCategoria.this, ActivityNovoAtivo.class);
            startActivity(intent);
        } else if (id == R.id.nav_ListaAtivos) {
            Intent intent = new Intent(ActivityAtivosCategoria.this, ActivityListaAtivos.class);
            startActivity(intent);
        } else if (id == R.id.nav_Localizar) {
            Intent intent = new Intent(ActivityAtivosCategoria.this, ActivityBusca.class);
            startActivity(intent);

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
