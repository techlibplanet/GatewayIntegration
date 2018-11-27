package gatewayintegration.com.gatewayintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_razorpay_integration.*
import net.rmitsolutions.libcam.Constants.logD
import org.json.JSONObject
import android.util.Log
import android.widget.EditText
import com.razorpay.Checkout





class RazorpayIntegrationActivity : AppCompatActivity(), PaymentResultListener {


    private lateinit var inputMobileNumber : EditText
    private lateinit var inputAmount : EditText
    private lateinit var inputEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razorpay_integration)

        inputAmount = findViewById(R.id.amount)
        inputMobileNumber = findViewById(R.id.mobileNumber)
        inputEmail = findViewById(R.id.email)

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
            options.put("name", "Mayank Sharma")

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

            var amount = inputAmount.text.toString().toInt()
            amount += amount.times(100)

            val email = inputEmail.text.toString().trim()
            val mobileNumber = inputMobileNumber.text.toString().trim()
            options.put("amount", amount)
            val preFill = JSONObject()
            preFill.put("email", email)
            preFill.put("contact", mobileNumber)

            options.put("prefill", preFill)

            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("Error", "Error in starting Razorpay Checkout", e)
        }

    }
}
