package com.example.potel.ui.booking

data class newOrder(
    var memberId :Int =0,
    var roomId: Int = 0,
    var expdates: String = "",
    var expdatee: String = "",
    var amount: Int = -1,
    var roomTypeId: Int =0,
    var petId: Int =0
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


