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


    private static final String NAMESPACE = "http://hello_webservice/";
    private static String URL="http://192.168.1.68:7001/HelloWebService/HelloWSService?WSDL";
    private static final String METHOD_NAME = "hello";
    private static final String SOAP_ACTION =  "http://hello_webservice/hello";


    public String CallMethod(String methodName,String username,String password)
    {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        PropertyInfo usernameParam=new PropertyInfo();
        usernameParam.name="username";
        usernameParam.type= PropertyInfo.STRING_CLASS;

        PropertyInfo passwordParam=new PropertyInfo();
        passwordParam.name="username";
        passwordParam.type= PropertyInfo.STRING_CLASS;

        request.addProperty(usernameParam, username);
        request.addProperty(passwordParam, password);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();


            return resultsRequestSOAP.toString();


        } catch (Exception e) {

            return  null;
        }
    }

}
