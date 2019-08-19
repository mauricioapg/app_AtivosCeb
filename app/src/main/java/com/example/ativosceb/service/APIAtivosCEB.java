package com.example.ativosceb.service;

import android.os.AsyncTask;
import com.example.ativosceb.model.Ativo;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIAtivosCEB extends AsyncTask<Void, Void, Ativo>{

    private final int id;

    public APIAtivosCEB(int id){
        this.id  = id;
    }

    /*public Ativo buscarAtivo() throws JSONException, IOException, ExecutionException, InterruptedException {
        String resposta = this.execute().get();
        JSONObject obj = new JSONObject(resposta);
        int idAtivo = obj.getInt("idAtivo");
        int idLocal = obj.getInt("idLocal");
        int idCategoria = obj.getInt("idCategoria");
        int idFabricante = obj.getInt("idFabricante");
        int idPiso = obj.getInt("idPiso");
        int patrimonio = obj.getInt("patrimonio");
        String item = obj.getString("item");
        String notaFiscal = obj.getString("notaFiscal");
        String dataRegistro = obj.getString("dataRegistro");
        String dataRetirada = obj.getString("dataRetirada");
        String garantia = obj.getString("garantia");
        String serviceTag = obj.getString("serviceTag");
        String condicao = obj.getString("condicao");
        String modelo = obj.getString("modelo");
        String numeroSerie = obj.getString("numeroSerie");
        String comentarios = obj.getString("comentarios");
        double valor = obj.getDouble("valor");
        return new Ativo(idAtivo, item);
        //return new Ativo(idAtivo, item, idLocal, idCategoria, idPiso, idFabricante, modelo, comentarios, dataRetirada, dataRegistro, condicao, serviceTag,
        //        garantia, numeroSerie, notaFiscal, patrimonio, valor);
    }*/

    @Override
    protected Ativo doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URL("http://webativos.gearhostpreview.com/api/ativo/" + id);
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
