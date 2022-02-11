package com.perpetua.eazytopup.viewmodels

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import com.perpetua.eazytopup.repositories.AirtimeRepository
import com.perpetua.eazytopup.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.awaitResponse

class AirtimeViewModel(private val airtimeRepository: AirtimeRepository) : ViewModel() {

    val airtimeForSelfData: MutableLiveData<Resource<Unit>> = MutableLiveData()
    init{
        d("AirtimeViewModel", "View model started")
    }
    fun buyAirtimeForSelf(airtimeForSelf: AirtimeForSelf) = viewModelScope.launch(Dispatchers.IO) {
        airtimeForSelfData.postValue(Resource.Loading())
        if(airtimeForSelf.number.isEmpty()){
            airtimeForSelfData.postValue(Resource.PhoneNumberError("Phone Number missing"))
        }
        if(airtimeForSelf.pesa.isEmpty()){
            airtimeForSelfData.postValue(Resource.AmountError("Amount missing"))
        }
        val response = airtimeRepository.buyAirtimeForSelf(airtimeForSelf)?.awaitResponse()
        handleAirtimeResponse(response?.body())
       }


    private fun handleAirtimeResponse(response: Response<Unit>?) : Resource<String>{
        if (response != null) {
            if(response.isSuccessful
            ){
                response.body()
                return Resource.Success("Successful, Wait for Mpesa Pin prompt")
            }else{
                return  Resource.Error("Error, Request failed")
            }
        }

        return  Resource.Error("Error, Request failed")
    }
}