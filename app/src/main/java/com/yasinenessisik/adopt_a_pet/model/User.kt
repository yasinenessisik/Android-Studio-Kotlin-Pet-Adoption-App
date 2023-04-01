package com.yasinenessisik.adopt_a_pet.model

class User {
    var mail:String?=null
    var uid:String?=null
    var nickname:String?=null
    var downloadUrl:String?=null
    constructor(){}
    constructor(email:String?,uid:String?,nickname:String?,downloadUrl:String?){
        this.mail = email
        this.uid = uid
        this.nickname = nickname
        this.downloadUrl = downloadUrl
    }
}