package com.example.ativosceb.activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ativosceb.R;
import com.example.ativosceb.model.Piso;
import com.example.ativosceb.service.APIAtivosCEB;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ActivityNovoAtivo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnInserirAtivo;
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
        setContentView(R.layout.activity_novo_ativo);
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

        this.btnInserirAtivo = (Button) findViewById(R.id.btnInserirAtivo);
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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //não abrir teclado automaticamente

        this.ClickBotaoInserirListener();

        try {
            popularSpinnerCategorias();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            popularSpinnerFabricantes();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            popularSpinnerLocais();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            popularSpinnerPisos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ClickBotaoInserirListener(){
        btnInserirAtivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InserirAtivo inserirAtivo = new InserirAtivo();
                try{
                    inserirAtivo.execute();
                    Toast.makeText(view.getContext(), "Inserido com sucesso!", Toast.LENGTH_LONG).show();
                }
                catch (Exception ex){
                    Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class InserirAtivo extends AsyncTask<Void, Void, String>{

        public String obterPostDataString (JSONObject parametros) throws JSONException, UnsupportedEncodingException {
            StringBuilder resposta = new StringBuilder();
            Boolean first = true;
            Iterator<String> itr = parametros.keys();
            while(itr.hasNext()){
                String key = itr.next();
                Object value = parametros.get(key);

                if(first)
                    first = false;
                else
                    resposta.append("&");

                resposta.append(URLEncoder.encode(key,"UTF-8"));
                resposta.append("=");
                resposta.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }
            return  resposta.toString();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            String result = null;
            try{
                URL url = new URL(APIAtivosCEB.urlPadrao + "fabricante");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                JSONObject parametrosPost = new JSONObject();
                parametrosPost.put("item", txtItem.getText());
                parametrosPost.put("idPiso", idPisoSelecionado);
                parametrosPost.put("idLocal", idLocalSelecionado);
                parametrosPost.put("idFabricante", idFabricanteSelecionado);
                parametrosPost.put("modelo", txtModelo.getText());
                parametrosPost.put("comentarios", "");
                parametrosPost.put("dataRegistro", txtDataRegistro.getText());
                parametrosPost.put("valor", txtValor.getText());
                parametrosPost.put("condicao", "Em uso");
                parametrosPost.put("idCategoria", idCategoriaSelecionada);
                parametrosPost.put("serviceTag", txtServiceTag.getText());
                parametrosPost.put("patrimonio", txtPatrimonio.getText());
                parametrosPost.put("garantia", txtGarantia.getText());
                parametrosPost.put("numeroSerie", txtNumeroSerie.getText());

                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(obterPostDataString(parametrosPost));
                writer.flush();
                writer.close();
                outputStream.close();

                int responseCode = urlConnection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuffer stringBuffer = new StringBuffer("");
                    String linha;
                    while ((linha = bufferedReader.readLine()) != null){
                        stringBuffer.append(linha);
                        break;
                    }
                    bufferedReader.close();
                    result = stringBuffer.toString();
                }
                else {
                    result = "Erro ao inserir ativo" + responseCode;
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(httpContext, "Processando solicitação", "por favor, aguarde");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(httpContext, s, Toast.LENGTH_LONG);
        }*/
    }

    private class APIPisos extends AsyncTask<Void, Void, List<String>>{

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<Piso> lista = new ArrayList<>();
            ArrayList<String> listaGlobal = new ArrayList<String>();
            listaGlobal.add("Selecione o piso...");
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
                    listaGlobal.add(object.optString("descPiso"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listaGlobal;
        }
    }

    private void popularSpinnerCategorias() throws ExecutionException, InterruptedException {
        ActivityAtivosCategoria activityAtivosCategoria = new ActivityAtivosCategoria();
        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activityAtivosCategoria.carregarCategorias());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterSpinner);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Categoria categoria = (Categoria) adapterView.getItemAtPosition(i);
                //idCategoriaSelecionada = categoria.getIdCategoria();
                //Toast.makeText(view.getContext(), "ID: " + categoria.getIdCategoria(), Toast.LENGTH_LONG).show();
                idCategoriaSelecionada = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void popularSpinnerFabricantes() throws ExecutionException, InterruptedException {
        ActivityAtivosFabricante activityAtivosFabricante = new ActivityAtivosFabricante();
        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activityAtivosFabricante.carregarFabricantes());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFabricante.setAdapter(adapterSpinner);
        spinnerFabricante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Fabricante fabricante = (Fabricante) adapterView.getItemAtPosition(i);
                //idFabricanteSelecionado = fabricante.getIdFabricante();
                idFabricanteSelecionado = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void popularSpinnerLocais() throws ExecutionException, InterruptedException {
        ActivityAtivosLocal activityAtivosLocal = new ActivityAtivosLocal();
        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activityAtivosLocal.carregarLocais());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocal.setAdapter(adapterSpinner);
        spinnerLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Local local = (Local) adapterView.getItemAtPosition(i);
                //idLocalSelecionado = local.getIdLocal();
                idLocalSelecionado = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void popularSpinnerPisos() throws ExecutionException, InterruptedException {
        ActivityNovoAtivo.APIPisos api = new ActivityNovoAtivo.APIPisos();
        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, api.execute().get());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPiso.setAdapter(adapterSpinner);
        spinnerPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Piso piso = (Piso) adapterView.getItemAtPosition(i);
                //idPisoSelecionado = piso.getIdPiso();
                idPisoSelecionado = i;
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
        getMenuInflater().inflate(R.menu.activity_novo_ativo, menu);
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

        if (id == R.id.nav_ListaAtivos) {
            Intent intent = new Intent(ActivityNovoAtivo.this, ActivityListaAtivos.class);
            startActivity(intent);
        } else if (id == R.id.nav_NovoAtivo) {
            Intent intent = new Intent(ActivityNovoAtivo.this, ActivityNovoAtivo.class);
            startActivity(intent);
        } else if (id == R.id.nav_Localizar) {
            Intent intent = new Intent(ActivityNovoAtivo.this, ActivityBusca.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
