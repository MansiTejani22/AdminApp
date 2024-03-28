package com.example.admindata.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.Model.UserDataModel
import com.example.admindata.R

class UserDataAdapter (
    private  val datalist : ArrayList<UserDataModel>
):RecyclerView.Adapter<UserDataAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_data_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datalist[position]
        holder.txFirstName.text = "Name: ${data.firstName}"
        holder.txPhoneNo.text = "Phone No: ${data.userPhoneNumber}"
        holder.txEmail.text = "Email: ${data.userEmail}"
        holder.txGender.text = "Gender: ${data.gender}"
        holder.txDateOfBorth.text = "DateOfBorth: ${data.birthday}"
        holder.txPinCode.text = "PinCode: ${data.pincode}"
        holder.txAddress.text = "Address: ${data.address}"
        holder.txCity.text = "City: ${data.city}"
        holder.txEmergencyContactNumber.text = "EmergencyContactNumber: ${data.emergencyContact}"
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txFirstName: TextView = itemView.findViewById(R.id.TxFirstName)
        val txPhoneNo: TextView = itemView.findViewById(R.id.TxPhoneNo)
        val txEmail: TextView = itemView.findViewById(R.id.TxEmail)
        val txGender: TextView = itemView.findViewById(R.id.TxGender)
        val txDateOfBorth: TextView = itemView.findViewById(R.id.TxDateOfBorth)
        val txPinCode: TextView = itemView.findViewById(R.id.TxPinCode)
        val txAddress: TextView = itemView.findViewById(R.id.TxAddress)
        val txCity: TextView = itemView.findViewById(R.id.TxCity)
        val txEmergencyContactNumber: TextView = itemView.findViewById(R.id.TxEmergencyContactNumber)
    }
}
