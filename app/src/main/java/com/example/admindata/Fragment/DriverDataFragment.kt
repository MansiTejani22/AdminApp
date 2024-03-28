package com.example.admindata.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.Adapter.CapDriverDataAdapter
import com.example.admindata.Model.DriverCapBookModel
import com.example.admindata.R
import com.google.firebase.database.*

class DriverDataFragment : Fragment() {

    private lateinit var recyclerCapDriverData: RecyclerView
    private lateinit var list: ArrayList<DriverCapBookModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_driver_data, container, false)
        recyclerCapDriverData = view.findViewById(R.id.RecyclerCapDriverData)
        recyclerCapDriverData.layoutManager = LinearLayoutManager(requireContext())
        recyclerCapDriverData.setHasFixedSize(true)

        list = arrayListOf()
        getCapDriverData()

        return view
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Driver Data"
    }

    private fun getCapDriverData() {
        dbRef = FirebaseDatabase.getInstance().getReference("DriverRegistration")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (dataSnap in snapshot.children) {
                        val driverData = dataSnap.getValue(DriverCapBookModel::class.java)
                        driverData?.let {
                            list.add(it)
                        }
                    }
                    val capAdapter = CapDriverDataAdapter(list)
                    recyclerCapDriverData.adapter = capAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled event
            }
        })
    }
}
