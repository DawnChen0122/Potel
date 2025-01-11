package com.example.potel.ui.booking

data class Order(
    var orderid: Int = 0,
    var roomid: Int = 0,
    var expdates: String = "",
    var expdatee: String = "",
    var dates: String = "",
    var datee: String = "",
    var amount: Int = -1,
    var refundamount: Int = -1,
    var orderstate: Char = '0',
    var paymentstate: Char = '0',
    var refundstate: Char = '0',
    var score: Int = 0,
    var comment: String = "",
    var paydatetime: String = "",
    var refunddatetime: String = "",
    var createdate: String = "",
    var modifydate: String? = null,
    var roomtype: RoomType = RoomType(),
    var member: Member = Member(),
    var pet: Pet = Pet()
)

data class Pet(
    var petid: Int = 0,
    var pettype: Char = 'D',
    var nickname: String = "",
    var weight: Double = 0.0,
    var breed: String = "",
    var imageid: Int = 0,
    var status: Char = '1',
    var createdate: String? = null,
    var modifydate: String? = null
)

data class Member(
    var memberid: Int = 0,
    var name: String = "",
    var cellphone: String = "",
    var address: String = "",
    var gender: Char = 'M',
    var birthday: String? = null,
    var email: String = "",
    var imageid: Int = 0,
    var status: Char = '1',
    var createdate: String? = null,
    var modifydate: String? = null
)

data class RoomType(
    var roomtypeid: Int = 0,
    var descpt: String = "",
    var imageid: Int = 28,
    val imageurl: String="",
    var price: Int = -1,
    var pettype: Char = 'D',
    var weightl: Int = 0,
    var weighth: Int = 0,
    var status: Char = '1',
    var createdate: String? = null,
    var modifydate: String? = null
)


