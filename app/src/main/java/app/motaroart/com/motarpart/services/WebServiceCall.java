package app.motaroart.com.motarpart.services;
/**
 * Created by Anil   Ugale on 20-12-2014.
 */

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class WebServiceCall {


    private static final String NAMESPACE = "http://tempuri.org/";

    public static String BASE_URL="http://autolampskenya.cloudapp.net/";
    public static String URL=BASE_URL+"mobileservice.asmx";



    public static String userLogin(String username, String password)
    {
        SoapObject request = new SoapObject(NAMESPACE, "ValidateUser");
        try {
        PropertyInfo usernameParam=new PropertyInfo();
        usernameParam.name="userId";
        usernameParam.type= PropertyInfo.STRING_CLASS;
        usernameParam.setValue(username);
        PropertyInfo passwordParam=new PropertyInfo();
        passwordParam.name="pass";
        String base64 = Base64.encodeToString(password.getBytes("US-ASCII"), Base64.DEFAULT);
        passwordParam.setValue(base64);
        passwordParam.type= PropertyInfo.STRING_CLASS;
        request.addProperty(usernameParam);
        request.addProperty(passwordParam);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet=true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


           androidHttpTransport.call(NAMESPACE+"ValidateUser", envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            return resultsRequestSOAP.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();

            return  null;
        }
        return  null;
    }
    private static final String METHOD_NAME_MAKE = "GetMake";
    private static final String SOAP_ACTION_MAKE =  "http://tempuri.org/GetMake";
    public static String getMakeJson()
    {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_MAKE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION_MAKE, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private static final String METHOD_NAME_MODEL = "GetModel";
    private static final String SOAP_ACTION_MODEL =  "http://tempuri.org/GetModel";
    public static String getModelJson(String MakeID)
    {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_MODEL);

        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="pMakeId";
        ProductId.type= PropertyInfo.STRING_CLASS;
        ProductId.setValue(MakeID);
        request.addProperty(ProductId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet=true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION_MODEL, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private static final String METHOD_NAME_CATEGORY = "GetCategory";
    private static final String SOAP_ACTION_CATEGORY =  "http://tempuri.org/GetCategory";
    public static String getCategoryJson()
    {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_CATEGORY);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION_CATEGORY, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
    private static final String METHOD_NAME_Product= "GetProduct";
    private static final String ACTION_PRODUCT= "http://tempuri.org/GetProduct";

    public static String getProduct(String modelID,String catID) {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Product);

        PropertyInfo usernameParam=new PropertyInfo();
        usernameParam.name="pModelId";
        usernameParam.type= PropertyInfo.STRING_CLASS;
        usernameParam.setValue(modelID);

        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="pCategoryId";
        ProductId.type= PropertyInfo.STRING_CLASS;
        ProductId.setValue(catID);

        request.addProperty(usernameParam);
        request.addProperty(ProductId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(ACTION_PRODUCT, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            System.out.println(resultsRequestSOAP.toString());
            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }
    }

    private static final String METHOD_NAME_SEARCH= "SearchProduct";
    private static final String ACTION_SEARCH= "http://tempuri.org/SearchProduct";
    public static String SearchProduct(String str) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_SEARCH);



        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="pSearchText";
        ProductId.type= PropertyInfo.STRING_CLASS;
        ProductId.setValue(str);


        request.addProperty(ProductId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(ACTION_SEARCH, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            System.out.println(resultsRequestSOAP.toString());
            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }
    }

    private static final String METHOD_NAME_WISH_UPDATE= "SetWishList";
    private static final String ACTION_WISH_UPDATE= "http://tempuri.org/SetWishList";
    public static String SetWishList(String str) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_WISH_UPDATE);



        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="pAccountWishList";
        ProductId.type= PropertyInfo.STRING_CLASS;
        ProductId.setValue(str);


        request.addProperty(ProductId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(ACTION_WISH_UPDATE, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }
    }

    private static final String METHOD_NAME_WISH_PRODUCT= "GetWishList";
    private static final String ACTION_WISH_PRODUCT= "http://tempuri.org/GetWishList";
    public static String getWishListProduct(String userid) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_WISH_PRODUCT);



        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="pAccountId";
        ProductId.type= PropertyInfo.STRING_CLASS;
        ProductId.setValue(userid);


        request.addProperty(ProductId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(ACTION_WISH_PRODUCT, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }

    }

    private static final String ACTION_WISH_REMOVE= "http://tempuri.org/RemoveWishListItem";
    private static final String REMOVE_WISH= "RemoveWishListItem";
    public static String removeWishListItem(String accountID,String productID) {

        SoapObject request = new SoapObject(NAMESPACE, REMOVE_WISH);



        PropertyInfo AccountId=new PropertyInfo();
        AccountId.name="pAccountId";
        AccountId.type= PropertyInfo.STRING_CLASS;
        AccountId.setValue(accountID);

        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="pProductId";
        ProductId.type= PropertyInfo.STRING_CLASS;
       ProductId.setValue(productID);

        request.addProperty(AccountId);
        request.addProperty(ProductId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(ACTION_WISH_REMOVE, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }

    }

    public static String getRegistration(String json) {
        SoapObject request = new SoapObject(NAMESPACE, "CreateCustomer");

        System.out.println(json);

        PropertyInfo AccountId=new PropertyInfo();
        AccountId.name="jsonAccount";
        AccountId.type= PropertyInfo.STRING_CLASS;
        AccountId.setValue(json);
        request.addProperty(AccountId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(NAMESPACE+"CreateCustomer", envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }

    }

    public static String createOrder(String json) {
        SoapObject request = new SoapObject(NAMESPACE, "GenerateOrder");

        System.out.println(json);

        PropertyInfo AccountId=new PropertyInfo();
        AccountId.name="jsonOrder";
        AccountId.type= PropertyInfo.STRING_CLASS;
        AccountId.setValue(json);
        request.addProperty(AccountId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(NAMESPACE+"GenerateOrder", envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }

    }


    public static String getSetting(SharedPreferences con) {
        SoapObject request = new SoapObject(NAMESPACE, "GetSettings");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(NAMESPACE+"GetSettings", envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();


            Log.d("SEt",resultsRequestSOAP.toString());
            return resultsRequestSOAP.toString();
        } catch (XmlPullParserException e) {

            return null;
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();

            return null;

        } catch (HttpResponseException e) {
            e.printStackTrace();

            return null;

        } catch (IOException e) {
            e.printStackTrace();

            return null;

        }

    }

    public static String createCardOrder(String json,String json1) {

        SoapObject request = new SoapObject(NAMESPACE, "ChargeCreditCard");

        System.out.println(json);

        PropertyInfo AccountId=new PropertyInfo();
        AccountId.name="jsonOrder";
        AccountId.type= PropertyInfo.STRING_CLASS;
        AccountId.setValue(json);
        request.addProperty(AccountId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        try {
            androidHttpTransport.call(NAMESPACE+"ChargeCreditCard", envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }
    }
}
