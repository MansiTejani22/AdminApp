package com.example.admindata.Model

class UserDataModel {
    var address: String? = null
    var birthday: String? = null
    var city: String? = null
    var emergencyContact: String? = null
    var firstName: String? = null
    var gender: String? = null
    var pincode: String? = null
    var userEmail: String? = null
    var userPhoneNumber: String? = null

    constructor()

    constructor(
        address: String?,
        birthday: String?,
        city: String?,
        emergencyContact: String?,
        firstName: String?,
        gender: String?,
        pincode: String?,
        userEmail: String?,
        userPhoneNumber: String?
    ) {
        this.address = address
        this.birthday = birthday
        this.city = city
        this.emergencyContact = emergencyContact
        this.firstName = firstName
        this.gender = gender
        this.pincode = pincode
        this.userEmail = userEmail
        this.userPhoneNumber = userPhoneNumber
    }
}
