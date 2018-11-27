package gatewayintegration.com.gatewayintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import kotlinx.android.synthetic.main.activity_google_pay.*
import android.net.Uri


class GooglePayActivity : AppCompatActivity() {


    private val TEZ_REQUEST_CODE = 123

    private val GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_pay)

        googlepay.setOnClickListener{
            val uri = Uri.Builder()
                    .scheme("upi")
                    .authority("pay")
                    .appendQueryParameter("pa", "test@axisbank")
                    .appendQueryParameter("pn", "Test Merchant")
                    .appendQueryParameter("mc", "1234")
                    .appendQueryParameter("tr", "123456789")
                    .appendQueryParameter("tn", "test transaction note")
                    .appendQueryParameter("am", "10.01")
                    .appendQueryParameter("cu", "INR")
                    .appendQueryParameter("url", "https://test.merchant.website")
                    .build()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME)
            startActivityForResult(intent, TEZ_REQUEST_CODE)
        }
    }

}
