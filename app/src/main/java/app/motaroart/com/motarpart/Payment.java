package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import app.motaroart.com.motarpart.pojo.CardInfo;
import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class Payment extends Activity {

    Order order;
    RadioButton m_paisa,debit_card;
    EditText mpaisa_edit;
    TextView payment_type;
    RelativeLayout rl;
    Button btn_mpaesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        order= (Order) getIntent().getSerializableExtra("order");
        ins=this;
        init();
    }

    private void init() {
        TextView total=(TextView)findViewById(R.id.total);
        total.setText("KES "+order.getTotalAmount());
        debit_card= (RadioButton) findViewById(R.id.debit_card);
        m_paisa= (RadioButton) findViewById(R.id.m_paisa);
        mpaisa_edit=(EditText)findViewById(R.id.mpaisa_edit);
        payment_type=(TextView)findViewById(R.id.payment_type);
        RadioGroup group=(RadioGroup) findViewById(R.id.pay_method);
        rl=(RelativeLayout)findViewById(R.id.card_details);
        btn_mpaesa=(Button)findViewById(R.id.btn_mpaesa);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.m_paisa:
                        order.setTransactionMode("MPESA");
                        mpaisa_edit.setVisibility(View.VISIBLE);
                        btn_mpaesa.setVisibility(View.VISIBLE);
                        payment_type.setText("M-PESA");
                        rl.setVisibility(View.GONE);
                        Toast.makeText(Payment.this,"m-pasa",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.debit_card:
                        order.setTransactionMode("CARD");
                        payment_type.setText("CARD");
                        mpaisa_edit.setVisibility(View.GONE);
                        btn_mpaesa.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);
                        Toast.makeText(Payment.this,"Bank",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    // Payment of m-paesa

    public void mpaesa(View v)
    {

        order.setTransactionNumber(mpaisa_edit.getText().toString().trim());
        order.setRemark(mpaesaSms);
        if(mpaesaSms!=null)
            new UplateTran().execute();


    }


//

    public void cardPayment(View v) {


        EditText name,cardNo,mm,yy,cvv;
        name=(EditText)findViewById(R.id.card_name);
        cardNo=(EditText)findViewById(R.id.card_number);
        mm=(EditText)findViewById(R.id.expiry_mm);
        yy=(EditText)findViewById(R.id.expiry_yy);
        cvv=(EditText)findViewById(R.id.card_cvv);
        order.setTransactionNumber("");
        order.setRemark("");

        if(name.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Card holder name.",Toast.LENGTH_SHORT).show();
        }
        else if(cardNo.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Card number properly.",Toast.LENGTH_SHORT).show();
        } else if(cardNo.getText().toString().trim().length()<16 ||cardNo.getText().toString().trim().length()>16)
        {
            Toast.makeText(this,"Enter the Card  number properly.",Toast.LENGTH_SHORT).show();
        }else if(mm.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Card  month",Toast.LENGTH_SHORT).show();
        }else if(yy.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Card  year",Toast.LENGTH_SHORT).show();
        }else if(cvv.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Card  cvv",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CardInfo card=new CardInfo();
            card.setCreditCardCVV(cvv.getText().toString().trim());
            card.setCreditCardHolderName(name.getText().toString().trim());
            card.setCreditCardExpiry(mm.getText().toString().trim()+"/"+yy.getText().toString().trim());
            card.setOrderAmount(order.getOrderAmount());
            card.setOrderBy(order.getOrderBy());
            card.setCreditCardNumber(cardNo.getText().toString().trim());

            Gson gson=new Gson();


            try {
                String josnCard=gson.toJson(card);
                String josnEncode="{\"Order\":"+gson.toJson(order)+"}";

                String cardinfo=Base64.encodeToString(josnCard.getBytes("US-ASCII"),Base64.DEFAULT);
                String orderinfo=Base64.encodeToString(josnEncode.getBytes("US-ASCII"),Base64.DEFAULT);
                if(InternetState.getState(this)) {
                new Cardpaymene().execute(cardinfo,orderinfo);
                }
                Toast.makeText(this, "Opps! Connection has lost", Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }




            
        }

    }


    String mpaesaSms;
    // M-paesa code
    private static Payment ins;
    public static Payment  getInstace()
    {
        return ins;
    }
    public void updateTheTextView( String t) {

        mpaesaSms=t;
        t = t.replace("\"","");
        t = t.replace("\'\'","");
        final  String[] str1=t.split(" ");
        Payment.this.runOnUiThread(new Runnable() {
            public void run() {

                mpaisa_edit.setText(str1[0]);
                Button btn_mpaesa= (Button) findViewById(R.id.btn_mpaesa);
                btn_mpaesa.setEnabled(true);
            }
        });
    }


    // update trans

    class UplateTran extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(Payment.this,getString(R.string.app_name),"Loading...",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if(s!=null) {
                System.out.println(s);
                Toast.makeText(Payment.this, s, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Payment.this, Thanks.class).putExtra("order_no", s));
                SharedPreferences mPref=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
                mPref.edit().remove("cart").apply();
                finish();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Gson gson=new Gson();
            return WebServiceCall.createOrder("{\"Order\":"+gson.toJson(order)+"}");
        }
    }

    class Cardpaymene extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(Payment.this,getString(R.string.app_name),"Loading",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            pd.dismiss();
            Toast.makeText(Payment.this,s,Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... voids) {
            return WebServiceCall.createCardOrder(voids[0],voids[1]);
        }
    }

}



