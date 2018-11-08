package gatewayintegration.com.gatewayintegration

import android.content.Context
import android.util.Log
import android.widget.Toast

object Constants {

    fun logD(message : String){
        Log.d("TAG", message)
    }

    fun toast(context: Context,message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    //this is the URL of the paytm folder that we added in the server
    //make sure you are using your ip else it will not work
    var BASE_URL = "http://www.alchemyeducation.org/paytm/"

    val M_ID = "Alchem89983996599310" //Paytm Merchand Id we got it in paytm credentials
    val CHANNEL_ID = "WAP" //Paytm Channel Id, got it in paytm credentials
    val INDUSTRY_TYPE_ID = "Retail" //Paytm industry type got it in paytm credential

    val WEBSITE = "APPSTAGING"
    val CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
}