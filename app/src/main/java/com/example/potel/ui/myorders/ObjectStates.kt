package com.example.potel.ui.myorders

enum class OrderState(val state: Char, val desc: String){
    Created('0', "已建立"),
    Paid('1', "已付款"),
    CheckedIn('2', "已入住"),
    CheckedOut('3', "已退房"),
    Canceled('4', "已取消"),
    Void('5', "已作廢")
}

enum class PaymentState(val state: Char, val desc: String){
    WaitPayment('0', "待付款"),
    Paid('1', "已付款")
}


enum class RefundState(val state: Char, val desc: String){
    NoRefund('0', "無須退款"),
    WaitRefund('1', "待退款"),
    Refunded('2', "已退款")
}

enum class MemberStatus(val status: Char, val desc: String){
    Enabled('1', "正常"),
    Disabled('2', "停權")
}

enum class PetStatus(val status: Char, val desc: String){
    Enabled('1', "正常"),
    Disabled('2', "已不存在")
}

enum class ProductStatus(val status: Char, val desc: String){
    Enabled('1', "上架"),
    Disabled('2', "下架")
}

enum class PrdOrderStatus(val status: Char, val desc: String){
    Created('1', "已建立"),
    Paid('2',"已付款"),
    Canceled('3', "已取消")
}