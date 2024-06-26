package com.example.hotel.data.models.apartaments

import android.os.Parcelable
import androidx.resourceinspection.annotation.Attribute.IntMap
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RoomsDTO(
    val status: String,
    val code: Int,
    val message: String,
    val data: PreData
)


data class PreData(
    val page:Int,
    @SerializedName("perpage")
    val perPage:Int,
    @SerializedName("total_count")
    val totalCount:Int,
    val records:List<RoomItem>,
    @SerializedName("total_pages")
    val totalPages:Int

)

@Parcelize
data class RoomItem(
    val id: Int,
    val active: Boolean,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable

data class RoomInfoItem(
    val status: String,
    val code: Int,
    val message: String,
    val data: List<RoomImages>
)

data class RoomImages(
    val image: String
)


data class BookingRequest(
    @SerializedName("apartment_id")
    val apartmentId: Int,
    @SerializedName("date_from")
    val dateFrom: String,
    @SerializedName("date_to")
    val dateTo: String
)

data class BookingResponse(
    val status: String,
    val code: Int,
    val message: String
)