package gatewayintegration.com.gatewayintegration

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded



interface PaytmApi {

    @FormUrlEncoded
    @POST("generate.php")
    fun getChecksum(
            @Field("MID") mId: String,
            @Field("ORDER_ID") orderId: String,
            @Field("CUST_ID") custId: String,
            @Field("CHANNEL_ID") channelId: String,
            @Field("TXN_AMOUNT") txnAmount: String,
            @Field("WEBSITE") website: String,
            @Field("CALLBACK_URL") callbackUrl: String,
            @Field("INDUSTRY_TYPE_ID") industryTypeId: String,
            @Field("MOBILE_NUMBER") mobileNumber: String,
            @Field("EMAIL") email: String
    ): Call<Checksum>
}