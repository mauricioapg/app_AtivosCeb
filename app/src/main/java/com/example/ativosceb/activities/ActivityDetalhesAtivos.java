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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ativosceb.R;
import com.example.ativosceb.model.Ativo;
import com.example.ativosceb.model.Categoria;
import com.example.ativosceb.model.Fabricante;
import com.example.ativosceb.model.Local;
import com.example.ativosceb.model.Piso;
import com.example.ativosceb.service.APIAtivosCEB;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ActivityDetalhesAtivos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Integer idAtivo;
    private EditText txtItem;
    private Spinner spinnerLocal;
    private Spinner spinnerFabricante;
    private Spinner spinnerCategoria;
    private Spinner spinnerPiso;
    private EditText txtComentarios;
    private EditText txtDataRetirada;
    private EditText txtDataRegistro;
    private TextView labelCondicao;
    private EditText txtValor;
    private EditText txtGarantia;
    private EditText txtNumeroSerie;
    private EditText txtServiceTag;
    private EditText txtPatrimonio;
    private EditText txtModelo;
    private EditText txtNotaFiscal;
    private Integer idCategoriaSelecionada;
    private Integer idFabricanteSelecionado;
    private Integer idLocalSelecionado;
    private Integer idPisoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_ativos);
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

        this.labelCondicao = (TextView) findViewById(R.id.labelCondicao);
        this.txtDataRegistro = (EditText) findViewById(R.id.txtDataRegistro);
        this.txtDataRetirada = (EditText) findViewById(R.id.txtDataRetirada);
        this.txtItem = (EditText) findViewById(R.id.txtItem);
        this.txtValor = (EditText) findViewById(R.id.txtValor);
        this.txtGarantia = (EditText) findViewById(R.id.txtGarantia);
        this.txtNumeroSerie = (EditText) findViewById(R.id.txtNumeroSerie);
        this.txtServiceTag = (EditText) findViewById(R.id.txtServiceTag);
        this.txtPatrimonio = (EditText) findViewById(R.id.txtPatrimonio);
        this.txtModelo = (EditText) findViewById(R.id.txtModelo);
        this.txtNotaFiscal = (EditText) findViewById(R.id.txtNotaFiscal);
        this.spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
        this.spinnerFabricante = (Spinner) findViewById(R.id.spinnerFabricante);
        this.spinnerLocal = (Spinner) findViewById(R.id.spinnerLocal);
        this.spinnerPiso = (Spinner) findViewById(R.id.spinnerPiso);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        try {
            this.carregarAtivo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class APIPisos extends AsyncTask<Void, Void, List<Piso>>{

        @Override
        protected List<Piso> doInBackground(Void... voids) {
            List<Piso> lista = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "piso");
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
                    Piso piso = new Piso(object.optInt("idPiso"), object.optString("descPiso"));
                    lista.add(piso);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }
    }

    private void carregarAtivo() throws ExecutionException, InterruptedException {
        ActivityBusca activityBusca = new ActivityBusca();
        Ativo ativo = activityBusca.carregarAtivo();
        idAtivo = ativo.getIdAtivo();
        this.labelCondicao.setText(ativo.getCondicao());
        this.txtItem.setText(ativo.getItem());
        this.txtDataRetirada.setText(ativo.getDataRetirada());
        this.txtDataRegistro.setText(ativo.getDataRegistro());
        this.txtModelo.setText(ativo.getModelo());
        this.txtValor.setText(String.valueOf(ativo.getValor()));
        this.txtGarantia.setText(ativo.getGarantia());
        this.txtNumeroSerie.setText(ativo.getNumeroSerie());
        this.txtServiceTag.setText(ativo.getServiceTag());
        //this.txtPatrimonio.setText(ativo.getPatrimonio());
        this.txtNotaFiscal.setText(ativo.getNotaFiscal());
        this.txtItem.setText(ativo.getItem());
        popularSpinnerCategorias();
        popularSpinnerFabricantes();
        popularSpinnerLocais();
        popularSpinnerPisos();
    }

    private void popularSpinnerCategorias() throws ExecutionException, InterruptedException {
        ActivityAtivosCategoria activityAtivosCategoria = new ActivityAtivosCategoria();
        final ArrayAdapter<Categoria> adapterSpinner = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, activityAtivosCategoria.carregarCategorias());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterSpinner);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Categoria categoria = (Categoria) adapterView.getItemAtPosition(i);
                idCategoriaSelecionada = categoria.getIdCategoria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void popularSpinnerFabricantes() throws ExecutionException, InterruptedException {
        ActivityAtivosFabricante activityAtivosFabricante = new ActivityAtivosFabricante();
        final ArrayAdapter<Fabricante> adapterSpinner = new ArrayAdapter<Fabricante>(this, android.R.layout.simple_spinner_item, activityAtivosFabricante.carregarFabricantes());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFabricante.setAdapter(adapterSpinner);
        spinnerFabricante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Fabricante fabricante = (Fabricante) adapterView.getItemAtPosition(i);
                idFabricanteSelecionado = fabricante.getIdFabricante();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void popularSpinnerLocais() throws ExecutionException, InterruptedException {
        ActivityAtivosLocal activityAtivosLocal = new ActivityAtivosLocal();
        final ArrayAdapter<Local> adapterSpinner = new ArrayAdapter<Local>(this, android.R.layout.simple_spinner_item, activityAtivosLocal.carregarLocais());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocal.setAdapter(adapterSpinner);
        spinnerLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Local local = (Local) adapterView.getItemAtPosition(i);
                idLocalSelecionado = local.getIdLocal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void popularSpinnerPisos() throws ExecutionException, InterruptedException {
        APIPisos api = new APIPisos();
        final ArrayAdapter<Piso> adapterSpinner = new ArrayAdapter<Piso>(this, android.R.layout.simple_spinner_item, api.execute().get());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPiso.setAdapter(adapterSpinner);
        spinnerPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Piso piso = (Piso) adapterView.getItemAtPosition(i);
                idPisoSelecionado = piso.getIdPiso();
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
        getMenuInflater().inflate(R.menu.activity_detalhes_ativos, menu);
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
            Intent intent = new Intent(ActivityDetalhesAtivos.this, ActivityNovoAtivo.class);
            startActivity(intent);
        } else if (id == R.id.nav_ListaAtivos) {
            Intent intent = new Intent(ActivityDetalhesAtivos.this, ActivityListaAtivos.class);
            startActivity(intent);
        } else if (id == R.id.nav_Localizar) {
            Intent intent = new Intent(ActivityDetalhesAtivos.this, ActivityBusca.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
