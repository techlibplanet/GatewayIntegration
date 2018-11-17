package gatewayintegration.com.gatewayintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_razorpay_integration.*
import net.rmitsolutions.libcam.Constants.logD
import org.json.JSONObject
import android.util.Log
import com.razorpay.Checkout





class RazorpayIntegrationActivity : AppCompatActivity(), PaymentResultListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razorpay_integration)

        buttonPay.setOnClickListener {
            startPayment()
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        logD("Error - Code - $errorCode Response -$response")
        when(errorCode){
            Checkout.NETWORK_ERROR -> logD("Network error occourred")
            Checkout.PAYMENT_CANCELED -> logD("Payment Cancelled")
            Checkout.INVALID_OPTIONS -> logD("Invalid Options")
            Checkout.TLS_ERROR -> logD("If device doesn't have TLS 1.1 or TLS 1.2")
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        logD("Success - $razorpayPaymentID")
    }

    fun startPayment() {
        /**
         * Instantiate Checkout
         */
        val checkout = Checkout()

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.app_logo)

        /**
         * Reference to current activity
         */
        val activity = this

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            val options = JSONObject()

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", "Merchant Name")

            /**
             * Description can be anything
             * eg: Order #123123
             * Invoice Payment
             * etc.
             */
            options.put("description", "Order #123456")

            options.put("currency", "INR")

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", "100")
            val preFill = JSONObject()
            preFill.put("email", "abc@gmail.com")
            preFill.put("contact", "9893192579")

            options.put("prefill", preFill)

            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("Error", "Error in starting Razorpay Checkout", e)
        }

    }
}
