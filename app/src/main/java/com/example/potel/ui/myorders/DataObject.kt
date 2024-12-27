package com.example.potel.ui.myorders

data class Order (
    val orderid: Int,
    val memberid: Int,
    val roomtypeid: Int,
    val roomid: Int,
    val expdates: String,
    val expdatee: String,
    val dates: String,
    val datee: String,
    val amount: Int,
    val refundamount: Int,
    val petid: Int,
    val orderstate: Char,
    val paymentstate: Char,
    val refundstate: Char,
    val score: Int,
    val comment: String,
    val paydatetime: String,
    val refunddatetime: String,
    val createdate: String,
    val modifydate: String
)

data class Pet(
    val petid: Int,
    val pettype: Char,
    val nickname: String,
    val weight: Double,
    val breed: String,
    val imageid: Int,
    val status: Char,
    val createdate: String,
    val modifydate: String
)

data class Member(
    val memberid: Int,
    val name: String,
    val cellphone: String,
    val address: String,
    val gender: Char,
    val birthday: String,
    val email: String,
    val status: Char,
    val createdate: String,
    val modifydate: String
)

data class RoomType(
    val roomtypeid: Int,
    val descpt: String,
    val imageid: Int,
    val price: Int,
    val pettype: Char,
    val weightl: Int,
    val weighth: Int,
    val status: Char,
    val createdate: String,
    val modifydate: String
)

data class Product(
    val prdid: Int,
    val prdname: Int,
    val price: Int,
    val imageid: Int,
    val prddesc: String,
    val status: Char,
    val createdate: String,
    val modifydate: String
)

data class PrdOrder(
    val prdorderid: Int,
    val memberid: Int,
    val amount: Int,
    val status: Char,
    val createdate: String,
    val modifydate: String
)

data class PrdOrdItem(
    val prdorditemid: Int,
    val prdorderid: Int,
    val prdid: Int,
    val prdcount: Int,
    val createdate: String,
    val modifydate: String
)



