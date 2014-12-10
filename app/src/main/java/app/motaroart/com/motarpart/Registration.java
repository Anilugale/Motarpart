package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class Registration extends Activity {


    EditText company_name,pin_number,vat_number,address,zip_code,po_code;
    CheckBox company_detail,postal_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        company_detail = (CheckBox) findViewById(R.id.company_detail);
        postal_address = (CheckBox) findViewById(R.id.postal_address);
        company_detail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    company_name.setVisibility(View.VISIBLE);
                    pin_number.setVisibility(View.VISIBLE);
                    vat_number.setVisibility(View.VISIBLE);
                }else {
                    company_name.setVisibility(View.GONE);
                    pin_number.setVisibility(View.GONE);
                    vat_number.setVisibility(View.GONE);
                }
            }
        });
        postal_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    address.setVisibility(View.VISIBLE);
                    zip_code.setVisibility(View.VISIBLE);
                    po_code.setVisibility(View.VISIBLE);
                }else {
                    address.setVisibility(View.GONE);
                    zip_code.setVisibility(View.GONE);
                    po_code.setVisibility(View.GONE);
                }
            }
        });

    }

    private void init() {

        company_name=(EditText)findViewById(R.id.company_name);
        pin_number=(EditText)findViewById(R.id.pin_number);
        vat_number=(EditText)findViewById(R.id.vat_number);
        address=(EditText)findViewById(R.id.address);
        zip_code=(EditText)findViewById(R.id.zip_code);
        po_code=(EditText)findViewById(R.id.po_code);


    }


    public void register(View view) {

       boolean company,address;

        if(company_detail.isChecked())
        {
            company=  validateCompanyDetail();
        }
        if (postal_address.isChecked()) {

            address= validateAddress();
        }
    }

    boolean validateAddress()
    {

        if (address.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Address!",Toast.LENGTH_SHORT ).show();
            return false;

        } else if (zip_code.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Zip Code!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if (po_code.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the PO BOX!",Toast.LENGTH_SHORT ).show();
            return false;
        }


        return true;
    }
    boolean validateCompanyDetail()
    {
        if(company_name.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Company name!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if(pin_number.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Pin number!",Toast.LENGTH_SHORT ).show();
            return false;
        } else if(vat_number.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the VAT number!",Toast.LENGTH_SHORT ).show();
            return false;
        }


        return true;
    }
}
