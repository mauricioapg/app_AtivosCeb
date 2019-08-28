package com.example.ativosceb.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ativosceb.R;
import com.example.ativosceb.model.Ativo;

import java.util.List;

public class AdapterListAtivos extends BaseAdapter {

    private Context context;
    private List<Ativo> listaAtivos;

    public AdapterListAtivos(Context context, List<Ativo> listaAtivos) {
        this.context = context;
        this.listaAtivos = listaAtivos;
    }

    @Override
    public int getCount() {
        return this.listaAtivos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.listaAtivos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = View.inflate(this.context, R.layout.layout_ativos, null);
        TextView id = (TextView) view.findViewById(R.id.labelID);
        TextView item = (TextView) view.findViewById(R.id.labelItem);
        id.setText(String.valueOf(listaAtivos.get(i).getIdAtivo()));
        item.setText(listaAtivos.get(i).getItem());

        return view;
    }
}
