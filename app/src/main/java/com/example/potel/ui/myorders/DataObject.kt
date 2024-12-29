package com.example.potel.ui.myorders

data class Order (
    var orderid: Int = 0,
//    var memberid: Int = 0,
//    var roomtypeid: Int = 0,
    var roomid: Int = 0,
    var expdates: String = "",
    var expdatee: String = "",
    var dates: String = "",
    var datee: String = "",
    var amount: Int = -1,
    var refundamount: Int = -1,
//    var petid: Int = 0,
    var orderstate: Char = '0',
    var paymentstate: Char = '0',
    var refundstate: Char = '0',
    var score: Int = 0,
    var comment: String = "",
    var paydatetime: String = "",
    var refunddatetime: String = "",
    var createdate: String = "",
    var modifydate: String = "",
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
    var createdate: String = "",
    var modifydate: String = ""
)

data class Member(
    var memberid: Int = 0,
    var name: String = "",
    var cellphone: String = "",
    var address: String = "",
    var gender: Char = 'M',
    var birthday: String = "",
    var email: String = "",
    var imageid: Int = 0,
    var status: Char = '1',
    var createdate: String = "",
    var modifydate: String = ""
)

data class RoomType(
    var roomtypeid: Int = 0,
    var descpt: String = "",
    var imageid: Int = 0,
    var price: Int = -1,
    var pettype: Char = 'D',
    var weightl: Int = 0,
    var weighth: Int = 0,
    var status: Char = '1',
    var createdate: String = "",
    var modifydate: String = ""
)

data class Product(
    var prdid: Int = 0,
    var prdname: String = "",
    var price: Int = -1,
    var imageid: Int = -1,
    var prddesc: String = "",
    var status: Char = '0',
    var createdate: String = "",
    var modifydate: String = ""
)

data class PrdOrder(
    var prdorderid: Int = 0,
//    var memberid: Int = 0,
    var amount: Int = -1,
    var status: Char = '0',
    var createdate: String = "",
    var modifydate: String = "",
    var member: Member = Member(),
    var prdorditems: List<PrdOrdItem> = emptyList()
)

data class PrdOrdItem(
    var prdorditemid: Int = 0,
    var prdorderid: Int = 0,
//    var prdid: Int = 0,
    var prdcount: Int = -1,
    var createdate: String = "",
    var modifydate: String = "",
    var product: Product
)
