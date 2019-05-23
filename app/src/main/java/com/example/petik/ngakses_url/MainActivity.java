import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputTextView = (TextView) findViewById(R.id.output);
        Async a = new Async();
        a.execute("");

    }
    //  AsyncTask untuk  dapat melakukan koneksi dengan website dan juga berfungsi untuk multithreading
    public class Async extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = "";
            try {
                data = downloadUrl("http://papaside.com/data.php

                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            outputTextView.setText(result);

        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // membuat http connection untuk berkomunikasi url
            urlConnection = (HttpURLConnection) url.openConnection();

            // mengkoneksikan dengan url
            urlConnection.connect();

            // Membaca data dari url
            // Data yang sudah terbaca merupakan sequance data /  byte streams
            iStream = urlConnection.getInputStream();

            //byte stream yang sudah didapat didecode menjadi characters stream menggunakan InputStreamReader
            //BufferedReader akan membaca text dari character-input stream, dan melakukan buffering characters sehenggiga dapat dengan efesien membaca character, array dan perbaris
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            //StringBuffer digunkan untuk memodifikasi character-input stream menjadi String
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) { // jika saat baris dibaca berisi null maka tidak akan diinput ke sb
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
}