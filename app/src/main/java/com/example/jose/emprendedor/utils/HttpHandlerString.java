package com.example.jose.emprendedor.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 10/10/2014.
 */
public class HttpHandlerString {
    public String post(String posturl,String username,String pass){
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(posturl);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ausuario",username));
            params.add(new BasicNameValuePair("acontrasena",pass));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse resp= httpClient.execute(httpPost);
            HttpEntity ent = resp.getEntity();

            String text = EntityUtils.toString(ent);

            return text;
        }catch (Exception e){
            return e.toString();
        }
    }
}
