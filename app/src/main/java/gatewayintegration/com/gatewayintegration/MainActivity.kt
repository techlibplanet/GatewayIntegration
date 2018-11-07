package gatewayintegration.com.gatewayintegration

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.paytm.pgsdk.PaytmPGService
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import kotlinx.android.synthetic.main.activity_main.*
import net.rmitsolutions.libcam.LibPermissions
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.paytm.pgsdk.PaytmOrder
import android.support.v4.app.NotificationCompat.getChannelId
import android.view.WindowManager
import gatewayintegration.com.gatewayintegration.Constants.logD
import gatewayintegration.com.gatewayintegration.Constants.toast


class MainActivity : AppCompatActivity(), PaytmPaymentTransactionCallback {

    private lateinit var libPermissions: LibPermissions
    private lateinit var paytmPGService: PaytmPGService
    private lateinit var textViewPrice : TextView

    private val permissions = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        libPermissions = LibPermissions(this,permissions)

        val runnable = Runnable {
            Constants.logD("All permission enabled")
        }
        libPermissions.askPermissions(runnable)

        paytmPGService = PaytmPGService.getStagingService()

        textViewPrice = findViewById(R.id.textViewPrice)

        buttonBuy.setOnClickListener{
            generateChecksum()
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

    }

    private fun generateChecksum() {
        //getting the tax amount first.
        val txnAmount = textViewPrice.text.toString().trim()

        //creating a retrofit object.
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //creating the retrofit api service
        val apiService = retrofit.create(PaytmApi::class.java)



        //creating paytm object
        //containing all the values required
        val paytm = Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        )

        //creating a call object from the apiService
        val call = apiService.getChecksum(
                paytm.getmId(),
                paytm.orderId,
                paytm.custId,
                paytm.channelId,
                paytm.txnAmount,
                paytm.website,
                paytm.callBackUrl,
                paytm.industryTypeId
        )

        //making the call to generate checksum
        call.enqueue(object : Callback<Checksum> {
            override fun onResponse(call: Call<Checksum>, response: Response<Checksum>) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                logD("Checksum hash - ${response.body().checksumHash}")
                initializePaytmPayment(response.body().checksumHash, paytm)
            }

            override fun onFailure(call: Call<Checksum>, t: Throwable) {

            }
        })
    }

    private fun initializePaytmPayment(checksumHash: String, paytm: Paytm) {
        //getting paytm service
        val Service = PaytmPGService.getStagingService()

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        val paramMap = HashMap<String, String>()
        paramMap.put("MID", Constants.M_ID)
        paramMap.put("ORDER_ID", paytm.orderId)
        paramMap.put("CUST_ID", paytm.custId)
        paramMap.put("CHANNEL_ID", paytm.channelId)
        paramMap.put("TXN_AMOUNT", paytm.txnAmount)
        paramMap.put("WEBSITE", paytm.website)
        paramMap.put("CALLBACK_URL", paytm.callBackUrl)
        paramMap.put("CHECKSUMHASH", checksumHash)
        paramMap.put("INDUSTRY_TYPE_ID", paytm.industryTypeId)


        //creating a paytm order object using the hashmap
        val order = PaytmOrder(paramMap)

        //intializing the paytm service
        Service.initialize(order, null)

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this)
    }


    override fun onTransactionResponse(inResponse: Bundle?) {
        toast(this, inResponse.toString())
    }

    override fun clientAuthenticationFailed(inErrorMessage: String?) {
        toast(this,inErrorMessage.toString())
    }

    override fun someUIErrorOccurred(inErrorMessage: String?) {
        toast(this, inErrorMessage.toString())
    }

    override fun onTransactionCancel(inErrorMessage: String?, inResponse: Bundle?) {
        toast(this, inErrorMessage.toString() + inResponse.toString())
    }

    override fun networkNotAvailable() {
        toast(this, "Network Error")
    }

    override fun onErrorLoadingWebPage(iniErrorCode: Int, inErrorMessage: String?, inFailingUrl: String?) {
        toast(this, inErrorMessage.toString())
    }

    override fun onBackPressedCancelTransaction() {
        toast(this, "Back pressed")
    }
}
