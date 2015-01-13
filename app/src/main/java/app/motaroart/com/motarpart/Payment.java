package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.motaroart.com.motarpart.pojo.Order;


public class Payment extends Activity {

    Order order;
    RadioButton m_paisa,debit_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        order= (Order) getIntent().getSerializableExtra("order");
        init();

        System.out.println(order.getOrderAmount()+"amount");

    }

    private void init() {
        TextView total=(TextView)findViewById(R.id.total);
        total.setText("KES."+order.getTotalAmount());
        debit_card= (RadioButton) findViewById(R.id.debit_card);
        m_paisa= (RadioButton) findViewById(R.id.m_paisa);

        RadioGroup group=(RadioGroup) findViewById(R.id.pay_method);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
              switch (group.getCheckedRadioButtonId())
              {
                  case R.id.m_paisa:
                      Toast.makeText(Payment.this,"m-pasa",Toast.LENGTH_SHORT).show();
                      break;
                  case R.id.debit_card:
                      Toast.makeText(Payment.this,"Bank",Toast.LENGTH_SHORT).show();
                      break;
              }
            }
        });
    }


}
