package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Secound extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);
        ListView listView=(ListView)findViewById(R.id.secund_list);

        List<String> list2=new ArrayList<String>();
        list2.add("Mahindra Verito");
        list2.add("Mahindra QUANTO");
        list2.add("Mahindra Bolero");
        list2.add("Mahindra Thar");
        list2.add("Mahindra Xylo");
        list2.add("Mahindra XUV 500");
        list2.add("Scorpio");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,list2);
        listView.setAdapter(adapter2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
