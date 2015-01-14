package app.motaroart.com.motarpart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

/**
 * Created by AnilU on 14-01-2015.
 */
public  class IncomingSms extends BroadcastReceiver {

    public IncomingSms() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();



                    if("AD-WAYSMS".equals(senderNum))
                    {


                        try {
                            Payment.getInstace().updateTheTextView(message);

                        } catch (Exception e) {

                        }


                    }





                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
            e.printStackTrace();
        }
    }
}