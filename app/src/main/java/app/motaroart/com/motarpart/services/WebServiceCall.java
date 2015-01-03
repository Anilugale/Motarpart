package app.motaroart.com.motarpart.services;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by AnilU on 20-12-2014.
 */
//   import org.ksoap2.transport.HttpTransportSE;
public class WebServiceCall {


    private static final String NAMESPACE = "http://tempuri.org/";
    private static String URL="http://192.168.42.122/TEST/mobileservice.asmx";




    public static String userLogin(String username, String password)
    {
     //   SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

       /* PropertyInfo usernameParam=new PropertyInfo();
        usernameParam.name="username";
        usernameParam.type= PropertyInfo.STRING_CLASS;
        usernameParam.setValue(username);
        PropertyInfo passwordParam=new PropertyInfo();
        passwordParam.name="username";
        passwordParam.setValue(password);
        passwordParam.type= PropertyInfo.STRING_CLASS;
        request.addProperty(usernameParam);
        request.addProperty(passwordParam);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
          //  androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            return resultsRequestSOAP.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }*/
        return null;
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
    public static String getModelJson()
    {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_MODEL);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
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
    private static final String METHOD_NAME_Product= "GetgetProduct";
    private static final String ACTION_PRODUCT= "http://tempuri.org/GetProduct";

    public static String getProduct(String modelID,String catID) {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Product);

        PropertyInfo usernameParam=new PropertyInfo();
        usernameParam.name="pModelId";
        usernameParam.type= PropertyInfo.STRING_CLASS;
        usernameParam.setValue(modelID);

        PropertyInfo ProductId=new PropertyInfo();
        ProductId.name="strProductId";
        ProductId.type= PropertyInfo.STRING_CLASS;
        ProductId.setValue(modelID);

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
}
