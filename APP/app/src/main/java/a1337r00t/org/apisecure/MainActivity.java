package a1337r00t.org.apisecure;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends Activity {
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);}
    public void sendPostRequest(View View){new TestAPI(this).execute();}
    private class TestAPI extends AsyncTask<String, Void, Void> {
        private final Context context;
        public TestAPI(Context c){this.context = c;}
        protected void onPreExecute(){progress=new ProgressDialog(this.context);progress.setMessage("Testing !...");progress.show();}
        @Override
        protected Void doInBackground(String... params) {
            try{
                final TextView checkComplate = findViewById(R.id.checkComplate); // I Love camelCase <3
                String Parameters = "{\"timestamp\":\""+System.currentTimeMillis() / 1000L+"\"}"; // Parameters
                //////////////////////////////////
                // HMAC algorithm with Base64
                SecretKeySpec key = new SecretKeySpec(("ThisIsMyKey123456PleaseMakeITStrong").getBytes("UTF-8"), "HmacSHA1"); // Set Key
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(key);
                String radata = Base64.encodeToString(mac.doFinal(Parameters.getBytes("UTF-8")), Base64.DEFAULT); // HMAC-over-Base64
                //////////////////////////////////
                // Send GET request
                URL url = new URL("http://1337r00t.me/api/");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET"); // Method Request
                connection.setRequestProperty("Params", Parameters); // Param Request Header
                connection.setRequestProperty("Signed", radata); // Param Request Header
                connection.getInputStream();
                //////////////////////////////////
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        checkComplate.setText("Check Your Packet :)");
                        progress.dismiss();
                    }
                });
            }catch(MalformedURLException e){e.printStackTrace();}catch(IOException e){e.printStackTrace();} catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) { e.printStackTrace(); }
            return null;
        }
    }
}
