package com.example.admindata.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.Model.DriverCapBookModel
import com.example.admindata.R

class CapDriverDataAdapter(private val dataList: ArrayList<DriverCapBookModel>) :
    RecyclerView.Adapter<CapDriverDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cap_driver_data_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.txName.text = "Name: ${data.Name}"
        holder.txPhoneNo.text = "Phone No: ${data.PhoneNo}"
        holder.txDrivingLicenseNo.text = "Driving License No: ${data.DrivingLicenseNo}"
        holder.txVehicleRc.text = "Vehicle RC: ${data.VehicleRc}"
        holder.txAadhaarCardNo.text = "Aadhaar Card No: ${data.AadhaarCardNo}"
        holder.txPanCard.text = "Pan Card: ${data.PanCard}"
        holder.txBankAccountNo.text = "Bank Account No: ${data.BankAccountNo}"
        holder.txIFSCcode.text = "IFSC Code: ${data.IFSCcode}"
        holder.txNameOfBank.text = "Name of Bank: ${data.NameOfBank}"
        holder.txArea.text = "Area: ${data.location?.area}"
        holder.txCity.text = "City: ${data.location?.city}"
        holder.txCountry.text = "Country: ${data.location?.country}"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txName: TextView = itemView.findViewById(R.id.TxName)
        val txPhoneNo: TextView = itemView.findViewById(R.id.TxPhoneNo)
        val txDrivingLicenseNo: TextView = itemView.findViewById(R.id.TxDrivingLicenseNo)
        val txVehicleRc: TextView = itemView.findViewById(R.id.TxVehicleRc)
        val txAadhaarCardNo: TextView = itemView.findViewById(R.id.TxAadhaarCardNo)
        val txPanCard: TextView = itemView.findViewById(R.id.TxPanCard)
        val txBankAccountNo: TextView = itemView.findViewById(R.id.TxBankAccountNo)
        val txIFSCcode: TextView = itemView.findViewById(R.id.TxIFSCcode)
        val txNameOfBank: TextView = itemView.findViewById(R.id.TxNameOfBank)
        val txArea: TextView = itemView.findViewById(R.id.TxArea)
        val txCity: TextView = itemView.findViewById(R.id.TxCity)
        val txCountry: TextView = itemView.findViewById(R.id.TxCountry)
    }
}
