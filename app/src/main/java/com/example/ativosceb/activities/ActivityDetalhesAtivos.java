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
import android.widget.Button;
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
    private TextView labelItem;
    private TextView labelLocal;
    private TextView labelFabricante;
    private TextView labelCategoria;
    private TextView labelPiso;
    private TextView labelComentarios;
    private TextView labelDataRetirada;
    private TextView labelDataRegistro;
    private TextView labelCondicao;
    private TextView labelValor;
    private TextView labelGarantia;
    private TextView labelNumeroSerie;
    private TextView labelServiceTag;
    private TextView labelPatrimonio;
    private TextView labelModelo;
    private TextView labelNotaFiscal;
    private MenuItem menuEditar;
    private static Integer idCategoriaSelecionada;
    private static Integer idFabricanteSelecionado;
    private static Integer idLocalSelecionado;
    private static Integer idPisoSelecionado;
    public static int idAtivoClicadoLista;
    public static String telaOrigem;
    public static String descricaoCategoria;
    public static String descricaoFabricante;
    public static String descricaoLocal;
    public static String descricaoPiso;


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

        this.setLabelCondicao((TextView) findViewById(R.id.labelCondicao));
        this.setLabelDataRegistro((TextView) findViewById(R.id.labelDataRegistro));
        this.setLabelDataRetirada((TextView) findViewById(R.id.labelDataRetirada));
        this.setLabelItem((TextView) findViewById(R.id.labelItem));
        this.setLabelValor((TextView) findViewById(R.id.labelValor));
        this.setLabelGarantia((TextView) findViewById(R.id.labelGarantia));
        this.setLabelNumeroSerie((TextView) findViewById(R.id.labelNumeroSerie));
        this.setLabelServiceTag((TextView) findViewById(R.id.labelServiceTag));
        this.setLabelPatrimonio((TextView) findViewById(R.id.labelPatrimonio));
        this.setLabelModelo((TextView) findViewById(R.id.labelModelo));
        this.setLabelNotaFiscal((TextView) findViewById(R.id.labelNotaFiscal));
        this.setLabelCategoria((TextView) findViewById(R.id.labelCategoria));
        this.setLabelFabricante((TextView) findViewById(R.id.labelFabricante));
        this.setLabelLocal((TextView) findViewById(R.id.labelLocal));
        this.setLabelPiso((TextView) findViewById(R.id.labelPiso));
        this.menuEditar = (MenuItem) findViewById(R.id.menuEditar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        try {
            if(telaOrigem == "telaBusca"){
                this.carregarAtivoBusca();
            }
            else if(telaOrigem == "telaLista"){
                this.carregarAtivoLista();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class APIDescricaoCategoria extends  AsyncTask<Void, Void, Categoria>{

        @Override
        protected Categoria doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "categoria/" + idCategoriaSelecionada);
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
            Gson gson = new Gson();
            return gson.fromJson(resposta.toString(), Categoria.class);
        }
    }

    private static class APIDescricaoFabricante extends  AsyncTask<Void, Void, Fabricante>{

        @Override
        protected Fabricante doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "fabricante/" + idFabricanteSelecionado);
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
            Gson gson = new Gson();
            return gson.fromJson(resposta.toString(), Fabricante.class);
        }
    }

    private static class APIDescricaoLocal extends  AsyncTask<Void, Void, Local>{

        @Override
        protected Local doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "local/" + idLocalSelecionado);
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
            Gson gson = new Gson();
            return gson.fromJson(resposta.toString(), Local.class);
        }
    }

    private static class APIDescricaoPiso extends  AsyncTask<Void, Void, Piso>{

        @Override
        protected Piso doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "piso/" + idPisoSelecionado);
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
            Gson gson = new Gson();
            return gson.fromJson(resposta.toString(), Piso.class);
        }
    }

    private class APIAtivoObtido extends AsyncTask<Void, Void, Ativo>{

        @Override
        protected Ativo doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(APIAtivosCEB.urlPadrao + "ativo/" + idAtivoClicadoLista);
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
            Gson gson = new Gson();
            return gson.fromJson(resposta.toString(), Ativo.class);
        }
    }

    public Ativo obterAtivo() throws ExecutionException, InterruptedException {
        Ativo ativo = null;
        if(telaOrigem == "telaLista"){
            APIAtivoObtido apiLista = new APIAtivoObtido();
            ativo = apiLista.execute().get();
        }
        else if(telaOrigem == "telaBusca"){
            ActivityBusca activityBusca = new ActivityBusca();
            ativo = activityBusca.carregarAtivo();
        }
        return ativo;
    }

    public void obterDescricoes() throws ExecutionException, InterruptedException {
        APIDescricaoCategoria apiDescCategoria = new APIDescricaoCategoria();
        Categoria categoria = apiDescCategoria.execute().get();
        this.getLabelCategoria().setText(categoria.getDescCategoria());
        descricaoCategoria = categoria.getDescCategoria();

        APIDescricaoFabricante apiDescFabricante = new APIDescricaoFabricante();
        Fabricante fabricante = apiDescFabricante.execute().get();
        this.getLabelFabricante().setText(fabricante.getDescFabricante());
        descricaoFabricante = fabricante.getDescFabricante();

        APIDescricaoLocal apiDescLocal = new APIDescricaoLocal();
        Local local = apiDescLocal.execute().get();
        this.getLabelLocal().setText(local.getDescLocal());
        descricaoLocal = local.getDescLocal();

        APIDescricaoPiso apiDescPiso = new APIDescricaoPiso();
        Piso piso = apiDescPiso.execute().get();
        this.getLabelPiso().setText(piso.getDescPiso());
        descricaoPiso = piso.getDescPiso();
    }
    
    private void carregarAtivoLista() throws ExecutionException, InterruptedException {
        APIAtivoObtido api = new APIAtivoObtido();
        Ativo ativo = api.execute().get();
        setIdAtivo(ativo.getIdAtivo());
        this.getLabelCondicao().setText(ativo.getCondicao());
        this.getLabelItem().setText(ativo.getItem());
        this.getLabelDataRetirada().setText(ativo.getDataRetirada());
        this.getLabelDataRegistro().setText(ativo.getDataRegistro());
        this.getLabelModelo().setText(ativo.getModelo());
        this.getLabelValor().setText(String.valueOf(ativo.getValor()));
        this.getLabelGarantia().setText(ativo.getGarantia());
        this.getLabelNumeroSerie().setText(ativo.getNumeroSerie());
        this.getLabelServiceTag().setText(ativo.getServiceTag());
        this.getLabelPatrimonio().setText(String.valueOf(ativo.getPatrimonio()));
        this.getLabelNotaFiscal().setText(ativo.getNotaFiscal());
        this.getLabelItem().setText(ativo.getItem());
        this.idCategoriaSelecionada = ativo.getIdCategoria();
        this.idFabricanteSelecionado = ativo.getIdFabricante();
        this.idLocalSelecionado = ativo.getIdLocal();
        this.idPisoSelecionado = ativo.getIdPiso();
        obterDescricoes();
    }

    private void carregarAtivoBusca() throws ExecutionException, InterruptedException {
        ActivityBusca activityBusca = new ActivityBusca();
        Ativo ativo = activityBusca.carregarAtivo();
        setIdAtivo(ativo.getIdAtivo());
        this.getLabelCondicao().setText(ativo.getCondicao());
        this.getLabelItem().setText(ativo.getItem());
        this.getLabelDataRetirada().setText(ativo.getDataRetirada());
        this.getLabelDataRegistro().setText(ativo.getDataRegistro());
        this.getLabelModelo().setText(ativo.getModelo());
        this.getLabelValor().setText(String.valueOf(ativo.getValor()));
        this.getLabelGarantia().setText(ativo.getGarantia());
        this.getLabelNumeroSerie().setText(ativo.getNumeroSerie());
        this.getLabelServiceTag().setText(ativo.getServiceTag());
        this.getLabelPatrimonio().setText(String.valueOf(ativo.getPatrimonio()));
        this.getLabelNotaFiscal().setText(ativo.getNotaFiscal());
        this.getLabelItem().setText(ativo.getItem());
        this.idCategoriaSelecionada = ativo.getIdCategoria();
        this.idFabricanteSelecionado = ativo.getIdFabricante();
        this.idLocalSelecionado = ativo.getIdLocal();
        this.idPisoSelecionado = ativo.getIdPiso();
        obterDescricoes();
    }

    public Integer getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(Integer idAtivo) {
        this.idAtivo = idAtivo;
    }

    public TextView getLabelItem() {
        return labelItem;
    }

    public void setLabelItem(TextView labelItem) {
        this.labelItem = labelItem;
    }

    public TextView getLabelLocal() {
        return labelLocal;
    }

    public void setLabelLocal(TextView labelLocal) {
        this.labelLocal = labelLocal;
    }

    public TextView getLabelFabricante() {
        return labelFabricante;
    }

    public void setLabelFabricante(TextView labelFabricante) {
        this.labelFabricante = labelFabricante;
    }

    public TextView getLabelCategoria() {
        return labelCategoria;
    }

    public void setLabelCategoria(TextView labelCategoria) {
        this.labelCategoria = labelCategoria;
    }

    public TextView getLabelPiso() {
        return labelPiso;
    }

    public void setLabelPiso(TextView labelPiso) {
        this.labelPiso = labelPiso;
    }

    public TextView getLabelComentarios() {
        return labelComentarios;
    }

    public void setLabelComentarios(TextView labelComentarios) {
        this.labelComentarios = labelComentarios;
    }

    public TextView getLabelDataRetirada() {
        return labelDataRetirada;
    }

    public void setLabelDataRetirada(TextView labelDataRetirada) {
        this.labelDataRetirada = labelDataRetirada;
    }

    public TextView getLabelDataRegistro() {
        return labelDataRegistro;
    }

    public void setLabelDataRegistro(TextView labelDataRegistro) {
        this.labelDataRegistro = labelDataRegistro;
    }

    public TextView getLabelCondicao() {
        return labelCondicao;
    }

    public void setLabelCondicao(TextView labelCondicao) {
        this.labelCondicao = labelCondicao;
    }

    public TextView getLabelValor() {
        return labelValor;
    }

    public void setLabelValor(TextView labelValor) {
        this.labelValor = labelValor;
    }

    public TextView getLabelGarantia() {
        return labelGarantia;
    }

    public void setLabelGarantia(TextView labelGarantia) {
        this.labelGarantia = labelGarantia;
    }

    public TextView getLabelNumeroSerie() {
        return labelNumeroSerie;
    }

    public void setLabelNumeroSerie(TextView labelNumeroSerie) {
        this.labelNumeroSerie = labelNumeroSerie;
    }

    public TextView getLabelServiceTag() {
        return labelServiceTag;
    }

    public void setLabelServiceTag(TextView labelServiceTag) {
        this.labelServiceTag = labelServiceTag;
    }

    public TextView getLabelPatrimonio() {
        return labelPatrimonio;
    }

    public void setLabelPatrimonio(TextView labelPatrimonio) {
        this.labelPatrimonio = labelPatrimonio;
    }

    public TextView getLabelModelo() {
        return labelModelo;
    }

    public void setLabelModelo(TextView labelModelo) {
        this.labelModelo = labelModelo;
    }

    public TextView getLabelNotaFiscal() {
        return labelNotaFiscal;
    }

    public void setLabelNotaFiscal(TextView labelNotaFiscal) {
        this.labelNotaFiscal = labelNotaFiscal;
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
        if (id == R.id.menuEditar) {
            Intent intent = new Intent(this, ActivityEditarAtivo.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
