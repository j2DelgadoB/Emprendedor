package com.example.jose.emprendedor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jose.emprendedor.utils.AdaptadorVentas;
import com.example.jose.emprendedor.utils.JSONParser;
import com.example.jose.emprendedor.utils.VentasObj;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jose on 09/10/2014.
 */

public class VentasCategoriaFragment extends Fragment{
    JSONParser jParser=new JSONParser();
    JSONArray jsonArray=null;
    ArrayList<HashMap<String, String>> ventasList= new ArrayList<HashMap<String, String>>();
    public VentasCategoriaFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ventas_categoria, container, false);

        TextView NombreEmpresa = (TextView)rootView.findViewById(R.id.textView);
        NombreEmpresa.setText(getActivity().getIntent().getStringExtra("nombreEmpresa"));

        TextView fechaCategoria = (TextView)rootView.findViewById(R.id.tvCategoria);
        fechaCategoria.setText(getActivity().getIntent().getStringExtra("fechaCategoria"));

        ventasCategoria ventas = new ventasCategoria();
        ventas.execute();



        return rootView;
    }

    private class ventasCategoria extends AsyncTask<Void,Void,String>{
        JSONObject json=null;
        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("aidVentasTotales",getActivity().getIntent().getStringExtra("idVentasTotales")));
            try {
                //json=jParser.makeHttpRequest("http://10.0.2.2:1000/ventas/ventas_categoria.php","POST",par);
                json=jParser.makeHttpRequest("http://isulamotors.com.pe/ventas/ventas_categoria.php","POST",par);

                Log.d("Mi json 4:", json.toString());
                int success = json.getInt("success");
                if (success == 1){
                    jsonArray= json.getJSONArray("ventascategoria");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String descripcion=c.getString("Descripcion");
                        String dia=c.getString("Dia");
                        String semana=c.getString("Semana");
                        String mes=c.getString("MesActual");
                        String mesAnt=c.getString("MesAnterior");
                        String diaDolar=c.getString("DiaDolar");
                        String semanaDolar=c.getString("SemanaDolar");
                        String mesDolar=c.getString("MesActualDolar");
                        String mesAntDolar=c.getString("MesAnteriorDolar");

                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("Descripcion",descripcion);map.put("Dia",dia);
                    map.put("Semana",semana);map.put("MesActual",mes);
                    map.put("MesAnterior",mesAnt);map.put("DiaDolar",diaDolar);
                    map.put("SemanaDolar",semanaDolar);map.put("MesActualDolar",mesDolar);
                    map.put("MesAnteriorDolar",mesAntDolar);
                    ventasList.add(map);
                    }


                }
            }catch (Exception e){

            }
            return null;
        }
        protected void onPostExecute(String file){

            TextView tvTotal=(TextView)getActivity().findViewById(R.id.tvTotal);
            TextView tvTotalDolar=(TextView)getActivity().findViewById(R.id.tvTotalDolar);
            ListView lstOpciones = (ListView)getActivity().findViewById(R.id.listView);
            ArrayList<VentasObj> ventasxcategoria = new ArrayList<VentasObj>();
            if (getActivity().getIntent().getStringExtra("fechaCategoria").equals("Dia")){
                //mi lista
                for (int i=0;i<ventasList.size();i++){
                    VentasObj v= new VentasObj(ventasList.get(i).get("Descripcion"),ventasList.get(i).get("Dia"),ventasList.get(i).get("DiaDolar"));
                    ventasxcategoria.add(v);
                }
                AdaptadorVentas adaptador= new AdaptadorVentas(getActivity(),ventasxcategoria);
                lstOpciones.setAdapter(adaptador);
                //mis textviews
                tvTotal.setText(getActivity().getIntent().getStringExtra("Total"));
                tvTotalDolar.setText(getActivity().getIntent().getStringExtra("TotalDolar"));
            } else if (getActivity().getIntent().getStringExtra("fechaCategoria").equals("Semana")){
                //mi lista
                for (int i=0;i<ventasList.size();i++){
                    VentasObj v= new VentasObj(ventasList.get(i).get("Descripcion"),ventasList.get(i).get("Semana"),ventasList.get(i).get("SemanaDolar"));
                    ventasxcategoria.add(v);
                }
                AdaptadorVentas adaptador= new AdaptadorVentas(getActivity(),ventasxcategoria);
                lstOpciones.setAdapter(adaptador);
                //mis textviews
                tvTotal.setText(getActivity().getIntent().getStringExtra("Total"));
                tvTotalDolar.setText(getActivity().getIntent().getStringExtra("TotalDolar"));
            } else if (getActivity().getIntent().getStringExtra("fechaCategoria").equals("Mes")){
                //mi lista
                for (int i=0;i<ventasList.size();i++){
                    VentasObj v= new VentasObj(ventasList.get(i).get("Descripcion"),ventasList.get(i).get("MesActual"),ventasList.get(i).get("MesActualDolar"));
                    ventasxcategoria.add(v);
                }
                AdaptadorVentas adaptador= new AdaptadorVentas(getActivity(),ventasxcategoria);
                lstOpciones.setAdapter(adaptador);
                //mis textviews
                tvTotal.setText(getActivity().getIntent().getStringExtra("Total"));
                tvTotalDolar.setText(getActivity().getIntent().getStringExtra("TotalDolar"));
            } else if (getActivity().getIntent().getStringExtra("fechaCategoria").equals("Mes Anterior")){
                //mi lista
                for (int i=0;i<ventasList.size();i++){
                    VentasObj v= new VentasObj(ventasList.get(i).get("Descripcion"),ventasList.get(i).get("MesAnterior"),ventasList.get(i).get("MesAnteriorDolar"));
                    ventasxcategoria.add(v);
                }
                AdaptadorVentas adaptador= new AdaptadorVentas(getActivity(),ventasxcategoria);
                lstOpciones.setAdapter(adaptador);
                //mis textviews
                tvTotal.setText(getActivity().getIntent().getStringExtra("Total"));
                tvTotalDolar.setText(getActivity().getIntent().getStringExtra("TotalDolar"));
            }










        }
    }

}