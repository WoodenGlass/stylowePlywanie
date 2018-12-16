package dobrowol.styloweplywanie.login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import dobrowol.styloweplywanie.R;

public class SigninActivity  extends AsyncTask {
    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;

    //flag 0 means get and 1 means post.(By default it is get.)
    public SigninActivity(Context context,TextView statusField,TextView roleField,int flag) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
        byGetOrPost = flag;
    }


    protected void onPreExecute(){
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                String username = (String)objects[0];
                String password = (String)objects[1];
                String link = "http://77.55.238.79/login.php?username="+username+"& password="+password;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        } else{
            try{
                String username = (String)objects[0];
                String password = (String)objects[1];

                String link="https://77.55.238.79/index2.php?username="+username+"& password="+password;

                String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);

                KeyStore keyStore = KeyStore.getInstance("BKS");
                InputStream in = context.getResources().openRawResource(R.raw.emm_truststore); //name of your keystore file here
                try {
                    // Initialize the keystore with the provided trusted certificates
                    // Provide the password of the keystore
                    keyStore.load(in, "wp0^W$1155s".toCharArray());
                }finally {
                    in.close();
                }
                String algorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
                tmf.init(keyStore);

                SSLContext context = SSLContext.getInstance("SSL");
                context.init(null, tmf.getTrustManagers(), null);
                SSLContext.setDefault(context);

                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("77.55.238.79", 443));
                /*Authenticator.setDefault(new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("<user>", "<password>".toCharArray());
                    }
                });*/
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(proxy);

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                Log.d("AAAALAMA", line);
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onPostExecute(Object result){
        String stringResult = (String)result;
        Log.d("AAAALAMA", stringResult);
        this.statusField.setText("Login Successful");
        this.roleField.setText(stringResult);
    }
}
