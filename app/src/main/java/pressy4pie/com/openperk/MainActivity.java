package pressy4pie.com.openperk;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView tb_runningCheck;
    public boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //this is our status box. (not really a box)
        tb_runningCheck = (TextView) findViewById(R.id.tb_RunningCheck);
        //give it some text and set it to red when the app starts.
        tb_runningCheck.setText("Not Running");
        tb_runningCheck.setTextColor(Color.RED);
    }

    private class maintask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //before the task actually starts.
            //has access to gui.

            tb_runningCheck.setTextColor(Color.GREEN);
            tb_runningCheck.setText("Starting.");
        }
        @Override
        protected void onCancelled() {
            //he's dead jim.
            running = false;
            tb_runningCheck.setTextColor(Color.RED);
            tb_runningCheck.setText("Stopped.");
        }
        @Override
        protected String doInBackground(String... params) {
            //the main hard work to be done...
            //does not have access to gui.
            int x = 0;
            running = true;
            while(running ){
                Log.d("maintask", "doing important stuff here...");
                x++;
                if (isCancelled()) break;
            }
            return null;
        }
        protected void onProgressUpdate(Void... values){

        }
    }

    public void Start(View view) {
        Log.d("Start Process", "Starting.");
        new maintask().execute("");

    }

    public void Stop(View view){
        Log.d("Start Process", "Stopping.");
        new maintask().cancel(true);
    }

}
