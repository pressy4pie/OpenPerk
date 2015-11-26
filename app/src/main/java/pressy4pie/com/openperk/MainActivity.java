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
    //text box for status.
    public TextView tb_runningCheck;
    //task boolean to enable cancellation (stopping) of task.
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


    /*
    This is our main workhorse. Network stuff can be done here.
     */
    private class maintask extends AsyncTask<Void, Integer, Boolean> {
        //For our Try/Catch part.
        Exception error;
        @Override
        protected void onPreExecute() {
            //before the task actually starts.
            //has access to gui.

            //tell the user we are starting the task.
            tb_runningCheck.setTextColor(Color.GREEN);
            tb_runningCheck.setText("Starting.");
            running = false;
        }
        @Override
        protected void onCancelled() {
            //if the stop button is hit.
            //has access to gui.

            //he's dead jim.
            running = false;
            tb_runningCheck.setTextColor(Color.RED);
            tb_runningCheck.setText("Stopped.");
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            //the main hard work to be done...
            //does not have access to gui.
            publishProgress(Integer.valueOf(1));
            int x = 0;
            running = true;
            try {
                while (running) {
                    if (isCancelled()) break;
                    /*
                    This is just dummy work. the real work goes down here but i don't know what
                    the real work is...
                    .
                    . "if (isCancelled()) break;" needs to be placed throughout the work
                    . to be able to properly cancel.
                    .
                    .
                    .
                    .
                     */
                    if(((x%100) == 0)){
                        Thread.sleep(1000);
                        Log.d("maintask",String.valueOf(x) + " doing important stuff here...");
                    }
                    x++;
                    if (isCancelled()) break;
                }
                return true;
            } catch (Exception e) {
                //Can't do anything with the error other than log cat it here.
                //Put it in the on post execute.
                error = e;
                return false;
            }
        }
        protected void onProgressUpdate(Integer... values){
            //needs to be updated.
            //has access to gui.

            Log.d("maintask", String.valueOf(values[0]) + " Means go.");
            tb_runningCheck.setTextColor(Color.GREEN);
            tb_runningCheck.setText("Running.");
        }
        protected void onPostExecute(Boolean result){
            //after work is done.
            //has access to gui.
            if (result) {
                //executed properly.
                //this can be used for cleanup.
                //But this block may never be executed depending on the actual work done.
            }
            else {
                //make sure there are no null pointers thrown.
                if (error != null){
                    tb_runningCheck.setTextColor(Color.RED);
                    tb_runningCheck.setText("Failed.");
                    //put it in the logcat for good measure.
                    Log.d("maintask", String.valueOf(error.getMessage()));
                }
            }
        }
    }


    /*
    These are the button functions. The are executed on click etc.
     */

    //Start Button
    public void Start(View view) {
        Log.d("Start Process", "Starting.");
        new maintask().execute();

    }

    //Stop Button
    public void Stop(View view){
        Log.d("Start Process", "Stopping.");
        new maintask().cancel(true);
    }

}
