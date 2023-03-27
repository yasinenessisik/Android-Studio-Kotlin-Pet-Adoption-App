package com.yasinenessisik.adopt_a_pet.model

class Message {
    var message:String?= null
    var senderId:String?=null


    constructor(){

    }
    constructor(message: String?,senderId:String?){
        this.message = message
        this.senderId = senderId
    }
}