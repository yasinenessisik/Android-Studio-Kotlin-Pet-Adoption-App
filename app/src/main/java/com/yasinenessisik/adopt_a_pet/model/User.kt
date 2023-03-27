package com.yasinenessisik.adopt_a_pet.model

class User {
    var mail:String?=null
    var uid:String?=null

    constructor(){}
    constructor(email:String?,uid:String?){
        this.mail = email
        this.uid = uid
    }
}