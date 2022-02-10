package com.perpetua.eazytopup.apis

import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AirtimeTopupApi {

    @POST("api/resp/self")
    fun buyAirtimeForSelf(@Body airtimeForSelf: AirtimeForSelf): Call<Response<Unit>>

    @POST("api/resp/other")
    fun buyAirtimeForOther(@Body airtimeForOther: AirtimeForOther): Call<Response<Unit>>
}