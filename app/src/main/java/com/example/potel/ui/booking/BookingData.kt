package com.example.potel.ui.booking


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


