package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

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


        System.out.println(new Gson().toJson(orderDetails));
    }

}
