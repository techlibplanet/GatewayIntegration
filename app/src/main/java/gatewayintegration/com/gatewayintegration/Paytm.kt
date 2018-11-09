package gatewayintegration.com.gatewayintegration

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.util.*


class Paytm(@field:SerializedName("MID")
            internal var mId: String, orderId: String, channelId: String, txnAmount: String, website: String, callBackUrl: String, industryTypeId: String, mobileNumber: String, email: String) {

    @SerializedName("ORDER_ID")
    var orderId: String
        internal set

    @SerializedName("CUST_ID")
    var custId: String
        internal set

    @SerializedName("CHANNEL_ID")
    var channelId: String
        internal set

    @SerializedName("TXN_AMOUNT")
    var txnAmount: String
        internal set

    @SerializedName("WEBSITE")
    var website: String
        internal set

    @SerializedName("CALLBACK_URL")
    var callBackUrl: String
        internal set

    @SerializedName("INDUSTRY_TYPE_ID")
    var industryTypeId: String
        internal set

    @SerializedName("MOBILE_NUMBER")
    var mobileNumber: String
        internal set

    @SerializedName("EMAIL")
    var email: String
        internal set

    init {
        this.orderId = orderId
        this.custId = generateString()
        this.channelId = channelId
        this.txnAmount = txnAmount
        this.website = website
        this.callBackUrl = callBackUrl
        this.industryTypeId = industryTypeId
        this.mobileNumber = mobileNumber
        this.email = email

    }

    fun getmId(): String {
        return mId
    }

    /*
    * The following method we are using to generate a random string everytime
    * As we need a unique customer id and order id everytime
    * For real scenario you can implement it with your own application logic
    * */
    private fun generateString(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.replace("-".toRegex(), "")
    }
}