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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jose.emprendedor.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

/**
 * Created by jose on 09/10/2014.
 */
public class EmpresaFragment extends Fragment {
    String[] empresa;
    JSONParser jParser = new JSONParser();
    JSONArray empresaJson = null;
    ArrayList<HashMap<String, String>> empresaList= new ArrayList<HashMap<String, String>>();
    ArrayList<String> empresaListName = new ArrayList<String>();
    private static final String TAG_SUCCESS="success";
    private static final String TAG_EMPRESA="empresa";
    private static final String TAG_ID_USUARIOEMPRESA="id";
    private static final String TAG_NOMBRE_EMPRESA="nombre";
    public EmpresaFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selecciona_empresa, container, false);

        empresas cargarLista = new empresas();
        cargarLista.execute();
        //empresa= new String[]{"Empresa 1","Empresa 2","Empresa 3"};

        return rootView;
    }

    private class empresas extends AsyncTask<Void,Void,String>{
        JSONObject json=null;
        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("aidUser",getActivity().getIntent().getStringExtra("MyID")));
            try {
                //json=jParser.makeHttpRequest("http://10.0.2.2:1000/ventas/empresas.php","POST",par);
                json=jParser.makeHttpRequest("http://isulamotors.com.pe/ventas/empresas.php","POST",par);
                Log.d("mi json 2", json.toString());
                int success=json.getInt(TAG_SUCCESS);
                if (success==1){
                    empresaJson = json.getJSONArray(TAG_EMPRESA);
                    for (int i=0; i< empresaJson.length();i++){
                        JSONObject c = empresaJson.getJSONObject(i);

                        String idUsuarioEmpresa= c.getString(TAG_ID_USUARIOEMPRESA);
                        String nombreEmpresa = c.getString(TAG_NOMBRE_EMPRESA);

                        empresaListName.add(nombreEmpresa);
                        HashMap<String,String> map= new HashMap<String, String>();
                        map.put(TAG_ID_USUARIOEMPRESA,idUsuarioEmpresa);
                        map.put(TAG_NOMBRE_EMPRESA,nombreEmpresa);
                        empresaList.add(map);
                    }
                }

            }catch (Exception e){

            }
            return null;
        }
        protected void onPostExecute(String file_url) {


            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),R.layout.list_item_empresa,R.id.list_item_empresa_textView,empresaListName);

            ListView lista_empresas=(ListView)getActivity().findViewById(R.id.listView);
            lista_empresas.setAdapter(adaptador);
            lista_empresas.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String opcionSeleccionada = (parent.getAdapter().getItem(position)).toString();
                    Toast.makeText(getActivity().getApplicationContext(), "opcion seleccionada" + opcionSeleccionada, Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getActivity(),VentasTotales.class);
                    for (int j=0;j<empresaList.size();j++){
                        if(opcionSeleccionada.equals(empresaList.get(j).get(TAG_NOMBRE_EMPRESA))){
                            i.putExtra("idUsuarioEmpresa",empresaList.get(j).get(TAG_ID_USUARIOEMPRESA));
                            i.putExtra("nombreEmpresa", empresaList.get(j).get(TAG_NOMBRE_EMPRESA));
                        }
                    }
                    startActivity(i);
                }
            });

        }
    }

}
