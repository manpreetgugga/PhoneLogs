package com.mobile.phonelogs.domain.entities

data class Message(
    var id: String? = "",
    var address: String? = "",
    var messsage: String? = "",
    var readState: String? = "", //"0" for have not read sms and "1" for have read sms
    var time: String? = "",
    var folderName: String? = "",
)