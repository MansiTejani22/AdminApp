package com.example.admindata.Model

class DriverCapBookModel {
    var driverId: String? = null
    var Name: String? = null
    var PhoneNo: String? = null
    var DrivingLicenseNo: String? = null
    var VehicleRc: String? = null
    var AadhaarCardNo: String? = null
    var PanCard: String? = null
    var BankAccountNo: String? = null
    var IFSCcode: String? = null
    var NameOfBank: String? = null
    var location: Location? = null

    constructor()
    constructor(
        driverId: String?,
        Name: String?,
        PhoneNo: String?,
        DrivingLicenseNo: String?,
        VehicleRc: String?,
        AadhaarCardNo: String?,
        PanCard: String?,
        BankAccountNo: String?,
        IFSCcode: String?,
        NameOfBank: String?,
        location: Location?
    ) {
        this.driverId = driverId
        this.Name = Name
        this.PhoneNo = PhoneNo
        this.DrivingLicenseNo = DrivingLicenseNo
        this.VehicleRc = VehicleRc
        this.AadhaarCardNo = AadhaarCardNo
        this.PanCard = PanCard
        this.BankAccountNo = BankAccountNo
        this.IFSCcode = IFSCcode
        this.NameOfBank = NameOfBank
        this.location = location
    }

    class Location {
        var area: String? = null
        var city: String? = null
        var country: String? = null

        constructor()

        constructor(area: String?, city: String?, country: String?) {
            this.area = area
            this.city = city
            this.country = country
        }
    }
}
