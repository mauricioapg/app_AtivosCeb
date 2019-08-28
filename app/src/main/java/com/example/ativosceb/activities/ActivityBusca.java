package com.example.ativosceb.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ativosceb.R;
import com.example.ativosceb.model.Ativo;
import com.example.ativosceb.service.APIAtivosCEB;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ActivityBusca extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnBuscar;
    public static EditText txtBusca;
    private CheckBox checkBoxPatrimonio;
    private CheckBox checkBoxNumeroSerie;
    private CheckBox checkBoxServiceTag;
    public static String urlBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
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

        this.btnBuscar = (Button) findViewById(R.id.btnBuscar);
        this.txtBusca = (EditText) findViewById(R.id.txtBuscaID);
        this.checkBoxNumeroSerie = (CheckBox) findViewById(R.id.checkBoxNumeroSerie);
        this.checkBoxPatrimonio = (CheckBox) findViewById(R.id.checkBoxPatrimonio);
        this.checkBoxServiceTag = (CheckBox) findViewById(R.id.checkBoxServiceTag);

        this.txtBusca.setVisibility(View.INVISIBLE);
        this.btnBuscar.setVisibility(View.INVISIBLE);

        this.ClickBotaoBuscarListener();
        this.ClickCheckBoxPatrimonio();
        this.ClickCheckBoxNumeroSerie();
        this.ClickCheckBoxServiceTag();

        /*try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                carregarAtivo(566);
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERRO: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }*/

    }

    private void ClickCheckBoxPatrimonio(){
        checkBoxPatrimonio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBoxPatrimonio.isChecked()){
                    checkBoxNumeroSerie.setEnabled(false);
                    checkBoxServiceTag.setEnabled(false);
                    txtBusca.setVisibility(View.VISIBLE);
                    btnBuscar.setVisibility(View.VISIBLE);
                    txtBusca.setHint("Digite o patrimônio");
                    urlBusca = APIAtivosCEB.urlPadrao + "ativo?patrimonio=";
                }
                else {
                    checkBoxNumeroSerie.setEnabled(true);
                    checkBoxServiceTag.setEnabled(true);
                    txtBusca.setVisibility(View.INVISIBLE);
                    btnBuscar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void ClickCheckBoxNumeroSerie(){
        checkBoxNumeroSerie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBoxNumeroSerie.isChecked()){
                    checkBoxPatrimonio.setEnabled(false);
                    checkBoxServiceTag.setEnabled(false);
                    txtBusca.setVisibility(View.VISIBLE);
                    btnBuscar.setVisibility(View.VISIBLE);
                    txtBusca.setHint("Digite o número de série");
                    urlBusca = APIAtivosCEB.urlPadrao + "ativo?numeroSerie=";
                }
                else {
                    checkBoxPatrimonio.setEnabled(true);
                    checkBoxServiceTag.setEnabled(true);
                    txtBusca.setVisibility(View.INVISIBLE);
                    btnBuscar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void ClickCheckBoxServiceTag(){
        checkBoxServiceTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBoxServiceTag.isChecked()){
                    checkBoxNumeroSerie.setEnabled(false);
                    checkBoxPatrimonio.setEnabled(false);
                    txtBusca.setVisibility(View.VISIBLE);
                    btnBuscar.setVisibility(View.VISIBLE);
                    txtBusca.setHint("Digite a service tag");
                    urlBusca = APIAtivosCEB.urlPadrao + "ativo?serviceTag=";
                }
                else {
                    checkBoxNumeroSerie.setEnabled(true);
                    checkBoxPatrimonio.setEnabled(true);
                    txtBusca.setVisibility(View.INVISIBLE);
                    btnBuscar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void ClickBotaoBuscarListener() {
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityDetalhesAtivos.telaOrigem = "telaBusca";
                Intent intent = new Intent(ActivityBusca.this, ActivityDetalhesAtivos.class);
                startActivity(intent);
            }
        });
    }

    private class API extends AsyncTask<Void, Void, Ativo>{

        @Override
        protected Ativo doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            StringBuilder resposta = new StringBuilder();
            try {
                URL url = new URL(ActivityBusca.urlBusca + String.valueOf(ActivityBusca.txtBusca.getText()));
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

    public Ativo carregarAtivo() throws ExecutionException, InterruptedException {
        API api = new API();
        Ativo ativo = api.execute().get();
        return ativo;
    }

    /*public Ativo carregarAtivo() throws ExecutionException, InterruptedException {
        API api = new API();
        Ativo ativo = api.execute().get();
        /*this.labelID.setVisibility(View.VISIBLE);
        this.labelItem.setVisibility(View.VISIBLE);
        this.labelID.setText("ID: " + ativo.getIdAtivo());
        this.labelItem.setText(ativo.getItem());
        Intent intent = new Intent(ActivityBusca.this, ActivityDetalhesAtivos.class);
        startActivity(intent);
        return ativo;
    }*/

    public void onRequestPermissionResult(int requestCode, String permissions[], int[] granResults) throws JSONException, IOException, ExecutionException, InterruptedException {
        switch (requestCode) {
            case 1: {
                if (granResults.length > 0 && granResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //carregarAtivo();
                } else {
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
        getMenuInflater().inflate(R.menu.activity_busca, menu);
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
            Intent intent = new Intent(ActivityBusca.this, ActivityNovoAtivo.class);
            startActivity(intent);
        } else if (id == R.id.nav_ListaAtivos) {
            Intent intent = new Intent(ActivityBusca.this, ActivityListaAtivos.class);
            startActivity(intent);
        } else if (id == R.id.nav_Localizar) {
            Intent intent = new Intent(ActivityBusca.this, ActivityBusca.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
