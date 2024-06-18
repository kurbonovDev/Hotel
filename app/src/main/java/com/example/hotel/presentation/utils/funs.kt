package com.example.hotel.presentation.utils

import com.example.hotel.data.models.registration.RegistResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit

fun <T> parseError(response: Response<T>, retrofit: Retrofit): RegistResponse? {
    val converter: Converter<ResponseBody, RegistResponse> =
        retrofit.responseBodyConverter(RegistResponse::class.java, arrayOfNulls(0))

    return response.errorBody()?.let {
        converter.convert(it)
    }
}
