package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.motaroart.com.motarpart.pojo.CardInfo;
import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class Payment extends Activity {

    Order order;
    RadioButton m_paisa,debit_card;
    EditText mpaisa_edit;
    TextView payment_type;
    RelativeLayout rl;
    Button btn_mpaesa,cod_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        order= (Order) getIntent().getSerializableExtra("order");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
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
        cod_btn=(Button)findViewById(R.id.cod_btn);

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
                        cod_btn.setVisibility(View.GONE);

                        Toast.makeText(Payment.this,"m-pasa",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.debit_card:
                        order.setTransactionMode("CARD");
                        payment_type.setText("CARD");
                        mpaisa_edit.setVisibility(View.GONE);
                        btn_mpaesa.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);
                        cod_btn.setVisibility(View.GONE);
                        Toast.makeText(Payment.this,"Bank",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cash_on_delivery:
                        order.setTransactionMode("COD");
                        cod_btn.setVisibility(View.VISIBLE);
                        mpaisa_edit.setVisibility(View.GONE);
                        btn_mpaesa.setVisibility(View.GONE);
                        rl.setVisibility(View.GONE);
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
        new UplateTran().execute();


    }

    EditText name,cardNo,mm,yy,cvv;
//

    public void cardPayment(View v) {



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
        } else if(cardNo.getText().toString().trim().length()<15 ||cardNo.getText().toString().trim().length()>16)
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
            DateFormat inputDF  = new SimpleDateFormat("MM/dd/yyyy");
            String date=inputDF.format(new Date());
            String[] dateData=date.split("/");

            int year=Integer.parseInt(yy.getText().toString());
            int currntYear=(Integer.parseInt(dateData[2]));
            int month=Integer.parseInt(mm.getText().toString());
            int currntMonth=(Integer.parseInt(dateData[0]));

            if(yy.getText().toString().trim().equals(dateData[2]))
            {


                if(month>=currntMonth && month<13)
                {

                    cardCall();

                }

                else
                    {
                        Toast.makeText(this,"Invalid Expiry Month and Year.",Toast.LENGTH_SHORT).show();
                    }

            }
            else if(year>currntYear)
            {
                if(month<13)
                cardCall();
                else
                    Toast.makeText(this,"Invalid Expiry Month and Year.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Invalid Expiry Month and Year.",Toast.LENGTH_SHORT).show();
            }


        }

    }


    void cardCall()
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

            String cardinfo= Base64.encodeToString(josnCard.getBytes("US-ASCII"), Base64.DEFAULT);
            String orderinfo=Base64.encodeToString(josnEncode.getBytes("US-ASCII"),Base64.DEFAULT);
            if(InternetState.getState(this)) {
                new Cardpaymene().execute(cardinfo,orderinfo);
            }
            else
                Toast.makeText(this, "Opps! Connection has lost", Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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

    public void cod(View view) {

        new  AlertDialog.Builder(this)
                .setMessage("Do you want go with Cash on delivery")
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        order.setTransactionNumber("");
                        order.setRemark("");
                        new UplateTran().execute();

                    }
                })
                .setTitle(getString(R.string.app_name))
                .show();


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
                startActivity(new Intent(Payment.this, OrderDetail.class).putExtra("orderNumber", s));
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


            if(s!=null) {
                try {
                    JSONArray arry = new JSONArray(s);
                    JSONObject node = arry.getJSONObject(0);
                    if (node.getString("STATUS").equals("SUCCESS")) {
                        startActivity(new Intent(Payment.this, OrderDetail.class).putExtra("orderNumber", node.getString("ORDERNUMBER")));
                        Toast.makeText(Payment.this, node.getString("DESCRIPTION"), Toast.LENGTH_LONG).show();
                        SharedPreferences mPref=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
                        mPref.edit().remove("cart").apply();
                        finish();



                    } else {
                        Toast.makeText(Payment.this, node.getString("DESCRIPTION"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Payment.this, "Opps! Communication error.", Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(Payment.this, "Opps! Communication error.", Toast.LENGTH_LONG).show();
            pd.dismiss();
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... voids) {
            return WebServiceCall.createCardOrder(voids[0],voids[1]);
        }
    }

    TextView count;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        count = new TextView(this);
        count.setTextColor(Color.BLUE);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Payment.this, Cart.class));
            }
        });
        LinearLayout.LayoutParams imgvwDimens =
                new LinearLayout.LayoutParams(100, 100);
        count.setGravity(Gravity.TOP | Gravity.RIGHT);
        count.setLayoutParams(imgvwDimens);
        count.setBackgroundResource(R.drawable.cart);
        count.setPadding(5, 5, 5, 5);
        count.setTypeface(null, Typeface.BOLD);
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr = mPrefs.getString("cart", "");
        Gson gson = new Gson();
        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
        if (list!=null) {
            count.setText(list.size() + "  ");
        } else {
            count.setText(0 + "  ");
        }

        count.setTextSize(15);
        menu.add(0, 0, 1, "count").setActionView(count).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr = mPrefs.getString("cart", "");
        Gson gson = new Gson();
        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
        if (count != null&&list!=null)
            count.setText(list.size() + "  ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id ==android.R.id.home)
        {
            Intent homeIntent = new Intent(this, MakeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        if (id == R.id.action_user) {
            startActivity(new Intent(this, Login.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}



