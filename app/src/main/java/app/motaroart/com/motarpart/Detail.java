package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import app.motaroart.com.motarpart.pojo.Product;


public class Detail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Product product = (Product) getIntent().getSerializableExtra("Product");

        TextView product_name = (TextView) findViewById(R.id.product_name);
        TextView product_make = (TextView) findViewById(R.id.product_make);
        TextView product_model = (TextView) findViewById(R.id.product_model);
        TextView product_mrp = (TextView) findViewById(R.id.product_mrp);
        TextView product_code = (TextView) findViewById(R.id.product_code);
        TextView product_number = (TextView) findViewById(R.id.product_number);
        TextView product_oem_no = (TextView) findViewById(R.id.product_oem_no);
        TextView product_desc = (TextView) findViewById(R.id.product_desc);

        product_name.setText(product.getProductName());
        product_make.setText(product.getMakeName());
        product_model.setText(product.getModelName());
        product_mrp.setText("Rs." + product.getRetailerPrice());
        product_code.setText(product.getProductCode() + "");
        product_number.setText("Code." + product.getProductNumber());
        product_oem_no.setText(product.getOME() + "");
        product_desc.setText(product.getProductDesc() + "");


    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
    }*/
}
