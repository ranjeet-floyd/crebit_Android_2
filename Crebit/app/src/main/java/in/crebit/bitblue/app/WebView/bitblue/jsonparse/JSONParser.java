package in.crebit.bitblue.app.WebView.bitblue.jsonparse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JSONParser {
    static InputStream is;
    static JSONArray jsonArray;
    static JSONObject jsonObject;
    static String json = "";

    public JSONParser() {
    }


    public JSONArray makeHttpPostRequest(String url, List<NameValuePair> nameValuePairs) {
        StringBuilder sb = new StringBuilder();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpresponse = httpclient.execute(httppost);
            StatusLine statusLine = httpresponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200 || statusCode == 201) {
                HttpEntity httpentity = httpresponse.getEntity();
                is = httpentity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } else {
                return null;
            }
            is.close();
            json = sb.toString();
            jsonArray = new JSONArray(json);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject makeHttpPostRequestforJsonObject(String url, List<NameValuePair> nameValuePairs) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();
            is = httpentity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject makeHttpGetRequestforJsonObject(String url, List<NameValuePair> nameValuePairs) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();
            is = httpentity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getMargin(String address) {
        StringBuilder sb = new StringBuilder();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb = new StringBuilder("Error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getBalance(String address) {
        StringBuilder sb = new StringBuilder();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb = new StringBuilder("Error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
