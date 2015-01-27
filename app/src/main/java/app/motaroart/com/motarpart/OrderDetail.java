package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import app.motaroart.com.motarpart.adapter.ProductHistoryAdapter;
import app.motaroart.com.motarpart.pojo.OrderDetails;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class OrderDetail extends Activity {

    OrderDetails orderDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        String  orderNumber=getIntent().getStringExtra("orderNumber");
        if(InternetState.getState(this))
        {
            new DataDownload().execute(orderNumber);
        }
    }

    class DataDownload extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected String doInBackground(String... voids) {
            return WebServiceCall.getOrderDetail(voids[0]);
        }
        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(OrderDetail.this,getString(R.string.app_name),"Loading..",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                Gson gson=new Gson();
                Type type=new TypeToken<OrderDetails>(){}.getType();
                orderDetails=gson.fromJson(s,type);
                init();
            }
            pd.dismiss();
            super.onPostExecute(s);
        }
    }

    private void init() {
        TextView order_number=(TextView)findViewById(R.id.order_number);
        order_number.setText(orderDetails.getOrder().get(0).getOrderNumber());
        TextView no_of_item=(TextView)findViewById(R.id.no_of_item);
        no_of_item.setText(orderDetails.getOrder().get(0).getOrderDate())  ;

        TextView subtotal=(TextView)findViewById(R.id.subtotal);
        subtotal.setText(orderDetails.getOrder().get(0).getOrderAmount());

        TextView vat_price=(TextView)findViewById(R.id.vat_price);
        vat_price.setText(orderDetails.getOrder().get(0).getVATAmount());


        TextView grand_total=(TextView)findViewById(R.id.grand_total);
        grand_total.setText(orderDetails.getOrder().get(0).getTotalAmount());

        TextView vat_pre=(TextView)findViewById(R.id.vat_per1);
        vat_pre.setText("VAT ("+orderDetails.getOrder().get(0).getVATPercent()+")");

        ListView orderHistory=(ListView)findViewById(R.id.order_history);
        ProductHistoryAdapter adapter=new ProductHistoryAdapter(this,orderDetails.getOrderDetails());
        orderHistory.setAdapter(adapter);
    }

}
