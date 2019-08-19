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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ativosceb.R;
import com.example.ativosceb.model.Ativo;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ActivityDetalhesAtivos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView labelIdAtivo;
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
        this.labelIdAtivo = (TextView) findViewById(R.id.labelIdAtivo);
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

        try {
            this.carregarAtivo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void carregarAtivo() throws ExecutionException, InterruptedException {
        ActivityBusca activityBusca = new ActivityBusca();
        Ativo ativo = activityBusca.carregarAtivo();
        this.labelIdAtivo.setText(String.valueOf(ativo.getIdAtivo()));
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
