package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.motaroart.com.motarpart.pojo.Order;


public class Payment extends Activity {

    Order order;
    RadioButton m_paisa,debit_card;
    EditText mpaisa_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        order= (Order) getIntent().getSerializableExtra("order");
        init();

        System.out.println(order.getOrderAmount()+"amount");
        //TODO
   /*     "TransactionNumber":"",
            "TransactionMode":"MPESA/CARD",
            "Remark":"MPESA TEXT DATA",*/

    }

    private void init() {
        TextView total=(TextView)findViewById(R.id.total);
        total.setText("KES."+order.getTotalAmount());
        debit_card= (RadioButton) findViewById(R.id.debit_card);
        m_paisa= (RadioButton) findViewById(R.id.m_paisa);
        mpaisa_edit=(EditText)findViewById(R.id.mpaisa_edit);
        RadioGroup group=(RadioGroup) findViewById(R.id.pay_method);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
              switch (group.getCheckedRadioButtonId())
              {
                  case R.id.m_paisa:
                      order.setTransactionMode("MPESA");
                      mpaisa_edit.setVisibility(View.VISIBLE);
                      Toast.makeText(Payment.this,"m-pasa",Toast.LENGTH_SHORT).show();
                      break;
                  case R.id.debit_card:
                      order.setTransactionMode("CARD");
                      mpaisa_edit.setVisibility(View.GONE);
                      Toast.makeText(Payment.this,"Bank",Toast.LENGTH_SHORT).show();
                      break;
              }
            }
        });
    }


}
