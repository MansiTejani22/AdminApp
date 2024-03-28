package com.example.admindata.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.Adapter.CapDriverDataAdapter
import com.example.admindata.Adapter.UserDataAdapter
import com.example.admindata.Model.DriverCapBookModel
import com.example.admindata.Model.UserDataModel
import com.example.admindata.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserDataFragment : Fragment() {

    private lateinit var recyclerCapDriverData: RecyclerView
    private lateinit var list: ArrayList<UserDataModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var capAdapter: UserDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_data, container, false)
        recyclerCapDriverData = view.findViewById(R.id.RecyclerCapUserData)
        recyclerCapDriverData.layoutManager = LinearLayoutManager(requireContext())
        recyclerCapDriverData.setHasFixedSize(true)

        list = arrayListOf()
        capAdapter = UserDataAdapter(list)
        recyclerCapDriverData.adapter = capAdapter

        getCapDriverData()

        return view
    }
    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "User Data"
    }

    private fun getCapDriverData() {
        dbRef = FirebaseDatabase.getInstance().getReference("UserRegistration")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Clear the list
                    list.clear()

                    // Iterate through each child (user) under UserRegistration
                    for (userSnapshot in snapshot.children) {
                        // Retrieve data for each user
                        val address = userSnapshot.child("address").getValue(String::class.java)
                        val birthday = userSnapshot.child("birthday").getValue(String::class.java)
                        val city = userSnapshot.child("city").getValue(String::class.java)
                        val emergencyContact = userSnapshot.child("emergencyContact").getValue(String::class.java)
                        val firstName = userSnapshot.child("firstName").getValue(String::class.java)
                        val gender = userSnapshot.child("gender").getValue(String::class.java)
                        val pincode = userSnapshot.child("pincode").getValue(String::class.java)
                        val userEmail = userSnapshot.child("userEmail").getValue(String::class.java)
                        val userPhoneNumber = userSnapshot.child("userPhoneNumber").getValue(String::class.java)

                        // Create UserDataModel object for each user
                        val userData = UserDataModel(
                            address,
                            birthday,
                            city,
                            emergencyContact,
                            firstName,
                            gender,
                            pincode,
                            userEmail,
                            userPhoneNumber
                        )

                        // Add UserDataModel object to the list
                        list.add(userData)
                    }

                    // Notify adapter of data change
                    capAdapter.notifyDataSetChanged()
                } else {
                    Log.d("UserDataFragment", "No data available")
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("UserDataFragment", "Database Error: ${error.message}")
            }
        })
    }



}