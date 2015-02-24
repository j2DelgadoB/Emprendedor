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
import android.widget.Toast;

import com.example.jose.emprendedor.utils.AdaptadorVentas;
import com.example.jose.emprendedor.utils.JSONParser;
import com.example.jose.emprendedor.utils.VentasObj;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;
/**
 * Created by jose on 09/10/2014.
 */
public class VentasTotalesFragment extends Fragment{
    JSONParser jParser = new JSONParser();
    JSONArray ventasJson = null;
    ArrayList<HashMap<String, String>> ventasList= new ArrayList<HashMap<String, String>>();
    public VentasTotalesFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ventas_totales, container, false);

        TextView NombreEmpresa = (TextView)rootView.findViewById(R.id.textView);
        NombreEmpresa.setText(getActivity().getIntent().getStringExtra("nombreEmpresa"));

        ventasTotales ventas= new ventasTotales();
        ventas.execute();

        return rootView;
    }

    private class ventasTotales extends AsyncTask<Void,Void,String>{
        JSONObject json=null;
        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("aidUsuarioEmpresa",getActivity().getIntent().getStringExtra("idUsuarioEmpresa")));
            try {
                //json=jParser.makeHttpRequest("http://10.0.2.2:1000/ventas/ventas_totales.php","POST",par);
                json=jParser.makeHttpRequest("http://isulamotors.com.pe/ventas/ventas_totales.php","POST",par);
                Log.d("Mi json 3:", json.toString());
                int success= json.getInt("success");
                if (success==1){
                    ventasJson = json.getJSONArray("ventastotales");
                    //solo va ver un registro de totales x usuario y x empresa
                    JSONObject c = ventasJson.getJSONObject(0);
                    String vDia= c.getString("Dia");
                    String vSemana= c.getString("Semana");
                    String vMes= c.getString("MesActual");
                    String vMesAnt= c.getString("MesAnterior");
                    String vDiaD= c.getString("DiaDolar");
                    String vSemanaD= c.getString("SemanaDolar");
                    String vMesD= c.getString("MesActualDolar");
                    String vMesAntD= c.getString("MesAnteriorDolar");
                    String ProMesActual= c.getString("ProyectadoMesActual");
                    String LogMesActual= c.getString("LogroMesActual");
                    String idVenTot= c.getString("id");

                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("id",idVenTot);
                    map.put("Dia",vDia);map.put("Semana",vSemana);
                    map.put("MesActual",vMes);map.put("MesAnterior",vMesAnt);
                    map.put("DiaDolar",vDiaD);map.put("SemanaDolar",vSemanaD);
                    map.put("MesActualDolar",vMesD);map.put("MesAnteriorDolar",vMesAntD);
                    map.put("ProyectadoMesActual",ProMesActual);map.put("LogroMesActual",LogMesActual);

                    ventasList.add(map);
                }
            }catch (Exception e){

            }

            return null;
        }
        protected void onPostExecute(String file){

            ArrayList<VentasObj> ventasTotales= new ArrayList<VentasObj>();
            VentasObj v= new VentasObj("Dia",ventasList.get(0).get("Dia"),ventasList.get(0).get("DiaDolar"));
            ventasTotales.add(v);
            VentasObj v1= new VentasObj("Semana",ventasList.get(0).get("Semana"),ventasList.get(0).get("SemanaDolar"));
            ventasTotales.add(v1);
            VentasObj v2= new VentasObj("Mes",ventasList.get(0).get("MesActual"),ventasList.get(0).get("MesActualDolar"));
            ventasTotales.add(v2);
            VentasObj v3= new VentasObj("Mes Anterior",ventasList.get(0).get("MesAnterior"),ventasList.get(0).get("MesAnteriorDolar"));
            ventasTotales.add(v3);


            TextView tvProyectado,tvLogro;
            tvProyectado=(TextView)getActivity().findViewById(R.id.tvTotal);
            tvLogro=(TextView)getActivity().findViewById(R.id.tvLogro);
            String proyectado=ventasList.get(0).get("ProyectadoMesActual");
            tvProyectado.setText(proyectado);
            String logro=ventasList.get(0).get("LogroMesActual");
            DecimalFormat df=new DecimalFormat("0");
            Float porcentaje= (Float.parseFloat(logro)/Float.parseFloat(proyectado))*100;
            tvLogro.setText(df.format(porcentaje)+"%");

            AdaptadorVentas adaptador= new AdaptadorVentas(getActivity(),ventasTotales);
            ListView lstOpciones = (ListView)getActivity().findViewById(R.id.listView);
            lstOpciones.setAdapter(adaptador);

            lstOpciones.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Alternativa 1:
                    VentasObj opcionSeleccionada = (VentasObj)parent.getAdapter().getItem(position);
                    String op=opcionSeleccionada.getDescripcion();
                    TextView NombreEmpresa = (TextView)getActivity().findViewById(R.id.textView);
                    Toast.makeText(getActivity().getApplicationContext(), "opcion seleccionada " + op, Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getActivity(),VentasCategoria.class);
                    i.putExtra("idVentasTotales",ventasList.get(0).get("id"));
                    i.putExtra("fechaCategoria",op);
                    i.putExtra("nombreEmpresa",NombreEmpresa.getText().toString());
                    if(op.equals("Dia")){
                       i.putExtra("Total",ventasList.get(0).get("Dia"));
                       i.putExtra("TotalDolar",ventasList.get(0).get("DiaDolar"));
                    }else if (op.equals("Semana")){
                        i.putExtra("Total",ventasList.get(0).get("Semana"));
                        i.putExtra("TotalDolar",ventasList.get(0).get("SemanaDolar"));
                    }else if (op.equals("Mes")){
                        i.putExtra("Total",ventasList.get(0).get("MesActual"));
                        i.putExtra("TotalDolar",ventasList.get(0).get("MesActualDolar"));
                    }else if (op.equals("Mes Anterior")){
                        i.putExtra("Total",ventasList.get(0).get("MesAnterior"));
                        i.putExtra("TotalDolar",ventasList.get(0).get("MesAnteriorDolar"));
                    }
                    startActivity(i);
                }
            });

        }
    }

}
