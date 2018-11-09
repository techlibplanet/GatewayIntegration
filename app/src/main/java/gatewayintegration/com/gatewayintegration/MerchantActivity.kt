package gatewayintegration.com.gatewayintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPGService



class MerchantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paytm)
    }

    fun onStartTransaction(view: View) {
        val Service = PaytmPGService.getProductionService()



        val paramMap = HashMap<String, String>()

        // these are mandatory parameters

        paramMap["CALLBACK_URL"] = "https://securegw.paytm.in/theia/paytmCallback"
        paramMap["CHANNEL_ID"] = "WAP"
        paramMap["CHECKSUMHASH"] = "yDZks+XoWcQ4YZJ+Iod+f/b/7mi5tcGqqQELPhSLQYjGdUfcUnsegcjlsdW797gnsvy4YrHNV8HSIJmdFN0NgWbNTle8wgKFAnSH14crB3A="
        paramMap["CUST_ID"] = "test111"
        paramMap["INDUSTRY_TYPE_ID"] = "Retail"
        paramMap["MID"] = "TECHOP10964184510936"
        paramMap["ORDER_ID"] = "1523342168787"
        paramMap["TXN_AMOUNT"] = "1"
        paramMap["WEBSITE"] = "TECHweb"

        /*
        paramMap.put("MID" , "WorldP64425807474247");
        paramMap.put("ORDER_ID" , "210lkldfka2a27");
        paramMap.put("CUST_ID" , "mkjNYC1227");
        paramMap.put("INDUSTRY_TYPE_ID" , "Retail");
        paramMap.put("CHANNEL_ID" , "WAP");
        paramMap.put("TXN_AMOUNT" , "1");
        paramMap.put("WEBSITE" , "worldpressplg");
        paramMap.put("CALLBACK_URL" , "https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/


        val Order = PaytmOrder(paramMap)

        /*PaytmMerchant Merchant = new PaytmMerchant(
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/

        Service.initialize(Order, null)

        Service.startPaymentTransaction(this, true, true,
                object : PaytmPaymentTransactionCallback {
                    override fun someUIErrorOccurred(inErrorMessage: String) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

                    /*@Override
					public void onTransactionSuccess(Bundle inResponse) {
						// After successful transaction this method gets called.
						// // Response bundle contains the merchant response
						// parameters.
						Log.d("LOG", "Payment Transaction is successful " + inResponse);
						Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
					}
					@Override
					public void onTransactionFailure(String inErrorMessage,
							Bundle inResponse) {
						// This method gets called if transaction failed. //
						// Here in this case transaction is completed, but with
						// a failure. // Error Message describes the reason for
						// failure. // Response bundle contains the merchant
						// response parameters.
						Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
						Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
					}*/

                    override fun onTransactionResponse(inResponse: Bundle) {
                        Log.d("LOG", "Payment Transaction is successful $inResponse")
                        Toast.makeText(applicationContext, "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                    }

                    override fun clientAuthenticationFailed(inErrorMessage: String) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    override fun onErrorLoadingWebPage(iniErrorCode: Int,
                                                       inErrorMessage: String, inFailingUrl: String) {

                    }

                    // had to be added: NOTE
                    override fun onBackPressedCancelTransaction() {
                        Toast.makeText(this@MerchantActivity, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show()
                    }

                    override fun onTransactionCancel(inErrorMessage: String, inResponse: Bundle) {
                        Log.d("LOG", "Payment Transaction Failed $inErrorMessage")
                        Toast.makeText(baseContext, "Payment Transaction Failed ", Toast.LENGTH_LONG).show()
                    }

                })
    }
}
