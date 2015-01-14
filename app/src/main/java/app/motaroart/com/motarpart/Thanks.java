package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class Thanks extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        TextView textView=(TextView)findViewById(R.id.order_number);
        textView.setText(getIntent().getStringExtra("order_no"));

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {

               finish();
            }
        }, 2000);

    }


}
