package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.motaroart.com.motarpart.pojo.Customer;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class Registration extends Activity {


    EditText company_name,tin_number,vat_number,addresss1,addresss2, city_p,po_code,username,pass,pass1,email,mobile,name,address1,address2,state,city,pobox;
    CheckBox company_detail,postal_address,shipping_address;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        company_detail = (CheckBox) findViewById(R.id.company_detail);
        postal_address = (CheckBox) findViewById(R.id.postal_address);
        shipping_address = (CheckBox) findViewById(R.id.shipping_address);
        company_detail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    rg.setVisibility(View.VISIBLE);
                    company_name.setVisibility(View.VISIBLE);
                    tin_number.setVisibility(View.VISIBLE);
                    vat_number.setVisibility(View.VISIBLE);
                }else {
                    rg.setVisibility(View.GONE);
                    company_name.setVisibility(View.GONE);
                    tin_number.setVisibility(View.GONE);
                    vat_number.setVisibility(View.GONE);
                }
            }
        });
        postal_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    addresss1.setVisibility(View.VISIBLE);
                    addresss2.setVisibility(View.VISIBLE);
                    city_p.setVisibility(View.VISIBLE);
                    po_code.setVisibility(View.VISIBLE);
                }else {
                    addresss1.setVisibility(View.GONE);
                    addresss2.setVisibility(View.GONE);
                    city_p.setVisibility(View.GONE);
                    po_code.setVisibility(View.GONE);
                }

            }
        });
        shipping_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    address1.setVisibility(View.VISIBLE);
                    address2.setVisibility(View.VISIBLE);
                    state.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    pobox.setVisibility(View.VISIBLE);
                }else {
                    address1.setVisibility(View.GONE);
                    address2.setVisibility(View.GONE);
                    state.setVisibility(View.GONE);
                    city.setVisibility(View.GONE);
                    pobox.setVisibility(View.GONE);
                }
            }
        });

    }

    private void init() {

        rg=(RadioGroup)findViewById(R.id.user_type);
        company_name=(EditText)findViewById(R.id.company_name);
        tin_number=(EditText)findViewById(R.id.tin_number);
        vat_number=(EditText)findViewById(R.id.vat_number);
        addresss1=(EditText)findViewById(R.id.address);
        addresss2=(EditText)findViewById(R.id.addresss2);
        city_p=(EditText)findViewById(R.id.city_p);
        po_code=(EditText)findViewById(R.id.po_code);
        name=(EditText)findViewById(R.id.user_name);


        address1=(EditText)findViewById(R.id.address1);
        address2=(EditText)findViewById(R.id.address2);
        state=(EditText)findViewById(R.id.state);
        city=(EditText)findViewById(R.id.city);
        pobox=(EditText)findViewById(R.id.pobox);




        username=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password1);
        pass1=(EditText)findViewById(R.id.password2);
        email=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.mobile);



    }

    Customer customer;
    public void register(View view) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean flag=true;
        String passw1 = pass.getText().toString().trim();
        String passw2 = pass1.getText().toString().trim();

        if (name.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter the Name!", Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter the username!", Toast.LENGTH_SHORT).show();
        } else if (mobile.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter the mobile number!", Toast.LENGTH_SHORT).show();
        } else if (pass.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter the password!", Toast.LENGTH_SHORT).show();
        } else if (pass1.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter the Re password!", Toast.LENGTH_SHORT).show();
        } else if (!passw1.equals(passw2)) {
            Toast.makeText(this, "password not match!", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter the email id!", Toast.LENGTH_SHORT).show();
        } else if (!email.getText().toString().trim().matches(emailPattern)) {
            Toast.makeText(this, "invalid the email id!", Toast.LENGTH_SHORT).show();
        } else {
            customer = new Customer();
            customer.setName(name.getText().toString().trim());
            customer.setLoginId(username.getText().toString().trim());
            customer.setLoginPwd(passw1.trim());
            customer.setEmail(email.getText().toString().trim());
            customer.setMobile(mobile.getText().toString().trim());

            if (company_detail.isChecked()) {

                if (validateCompanyDetail()) {
                    customer.setCompanyName(company_name.getText().toString().trim());
                    customer.setTIN(tin_number.getText().toString().trim());
                    customer.setVATNumber(vat_number.getText().toString().trim());
                    if(rg.getCheckedRadioButtonId()==R.id.retailers)
                    customer.setAccountType("R");
                    else
                    customer.setAccountType("W");
                    flag=true;
                } else
                    flag = false;
            }

            if (postal_address.isChecked()) {

                if (validateAddress()) {
                    customer.setAddress1(this.addresss1.getText().toString().trim());
                    customer.setAddress2(this.addresss2.getText().toString().trim());
                    customer.setCity(this.city_p.getText().toString().trim());
                    customer.setPoBox(this.po_code.getText().toString().trim());
                    flag=true;
                } else {
                    flag = false;
                }
            } else if (shipping_address.isChecked()) {
                if (validateShippingAddress()) {
                    customer.setShipmentAddress1(address1.getText().toString().trim());
                    customer.setShipmentAddress2(address2.getText().toString().trim());
                    customer.setShipmentState(state.getText().toString().trim());
                    customer.setShipmentCity(city.getText().toString().trim());
                    customer.setShipmentPoBox(pobox.getText().toString().trim());
                    flag=true;
                } else {
                    flag = false;
                }

            }

                if(flag)
                {
                    new RegisterBack().execute();


                }



        }

                   /* if (postal_address.isChecked()) {

                        address = validateAddress();
                        if (address) {
                            customer.setAddress1(this.addresss1.getText().toString().trim());
                            customer.setAddress2(this.addresss2.getText().toString().trim());
                            customer.setCity(this.city_p.getText().toString().trim());
                            customer.setPoBox(this.po_code.getText().toString().trim());
                        }
                        if (validateShippingAddress()) {
                            customer.setShipmentAddress1(address1.getText().toString().trim());
                            customer.setShipmentAddress2(address2.getText().toString().trim());
                            customer.setShipmentState(state.getText().toString().trim());
                            customer.setShipmentCity(city.getText().toString().trim());
                            customer.setShipmentPoBox(pobox.getText().toString().trim());
                            new RegisterBack().execute();
                        } else {


                            new RegisterBack().execute();
                        }




                } else if (shipping_address.isChecked()) {
                    if (validateShippingAddress()) {
                        customer.setShipmentAddress1(address1.getText().toString().trim());
                        customer.setShipmentAddress2(address2.getText().toString().trim());
                        customer.setShipmentState(state.getText().toString().trim());
                        customer.setShipmentCity(city.getText().toString().trim());
                        customer.setShipmentPoBox(pobox.getText().toString().trim());
                        new RegisterBack().execute();
                    } else {
                        new RegisterBack().execute();
                    }

                }

            } else {

                if (postal_address.isChecked()) {

                    address = validateAddress();
                    if (address) {
                        customer.setAddress1(this.addresss1.getText().toString().trim());
                        customer.setAddress2(this.addresss2.getText().toString().trim());
                        customer.setCity(this.city_p.getText().toString().trim());
                        customer.setPoBox(this.po_code.getText().toString().trim());
                        new RegisterBack().execute();
                    }
                }
               else  if (shipping_address.isChecked()) {
                    if (validateShippingAddress()) {
                        customer.setShipmentAddress1(address1.getText().toString().trim());
                        customer.setShipmentAddress2(address2.getText().toString().trim());
                        customer.setShipmentState(state.getText().toString().trim());
                        customer.setShipmentCity(city.getText().toString().trim());
                        customer.setShipmentPoBox(pobox.getText().toString().trim());
                        new RegisterBack().execute();
                    } else {
                        new RegisterBack().execute();
                    }


                } else {
                    new RegisterBack().execute();
                }

            }
        }*/
    }

    boolean validateAddress()
    {

        if (addresss1.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Address1!",Toast.LENGTH_SHORT ).show();
            return false;

        } else if (addresss2.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Address2!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if (city_p.getText().toString().trim().length()==0)
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

    boolean validateShippingAddress()
    {

        if (address1.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Address1!",Toast.LENGTH_SHORT ).show();
            return false;

        } else if (address2.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"EEnter the Address1!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if (state.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the State!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if (city.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the City!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if (pobox.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Po Box!",Toast.LENGTH_SHORT ).show();
            return false;
        }


        return true;
    }


    boolean validateCompanyDetail()
    {
        if(rg.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this,"Enter the Company Type number!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if(company_name.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Company name!",Toast.LENGTH_SHORT ).show();
            return false;
        }else if(tin_number.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the tin number!",Toast.LENGTH_SHORT ).show();
            return false;
        } else if(vat_number.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the VAT number!",Toast.LENGTH_SHORT ).show();
            return false;
        }


        return true;
    }


    class RegisterBack extends AsyncTask<Void,Void,String>
    {

        ProgressDialog pd;
        @Override
        protected String doInBackground(Void... voids) {
            Gson gson=new Gson();
            String json=gson.toJson(customer);
            return  WebServiceCall.getRegistration(json);

        }

        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(Registration.this,getString(R.string.app_name),"Loading..!",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            if(s!=null)
            {
              try {
                    JSONObject jsonResult=new JSONObject(s);
                    JSONArray jsonArrayResult=jsonResult.getJSONArray("Status");
                    JSONObject status=jsonArrayResult.getJSONObject(0);
                    String errorCode= status.getString("ERROR_CODE");
                    String errorMsg= status.getString("ERROR_DESC");

                    if(errorCode.equals("1010"))
                    {
                        new AlertDialog.Builder(Registration.this)
                                .setTitle(Registration.this.getString(R.string.app_name))
                                .setMessage(errorMsg)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if(errorCode.equals("1000"))
                    {
                        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);                        mPrefs.edit().putString("user",jsonResult.getJSONArray("UserAccount").get(0).toString()).apply();
                        mPrefs.edit().putString("user",jsonResult.getJSONArray("UserAccount").get(0).toString());
                        Intent intent=new Intent(Registration.this,MakeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else
            {
             Toast.makeText(Registration.this,"Network Problem",Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
            super.onPostExecute(s);
        }
    }
}
