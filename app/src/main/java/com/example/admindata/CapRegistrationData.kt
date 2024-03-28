package com.example.admindata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.Adapter.CapDriverDataAdapter
import com.example.admindata.Model.DriverCapBookModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CapRegistrationData : AppCompatActivity() {
    private lateinit var txTitle: TextView
    private lateinit var recyclerCapDriverData: RecyclerView
    private lateinit var list : ArrayList<DriverCapBookModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cap_registration_data)
        val selectedCategoryName = intent.getStringExtra("selectedCategoryName")

        recyclerCapDriverData = findViewById(R.id.RecyclerCapDriverData)
        txTitle = findViewById(R.id.TxTitle)
        txTitle.text = selectedCategoryName

        recyclerCapDriverData.layoutManager = LinearLayoutManager(this)
        recyclerCapDriverData.setHasFixedSize(true)

        list= arrayListOf<DriverCapBookModel>()
        getCapDriverData()


    }

    private fun  getCapDriverData()
    {
        dbRef = FirebaseDatabase.getInstance().getReference("DriverRegistration")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if(snapshot.exists())
                {
                    for(dataSnap in snapshot.children)
                    {
                        val driverData = dataSnap.getValue(DriverCapBookModel::class.java)
                        list.add(driverData!!)
                    }
                    val capAdapter = CapDriverDataAdapter(list)
                    recyclerCapDriverData.adapter=capAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}