package com.example.demo

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.Adapter.CategoryAdapter
import com.example.admindata.Model.CategoryModel
import com.example.admindata.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var imgCategory: ImageView
    private lateinit var etCategoryName: EditText
    private lateinit var UploadCategory: Button
    private lateinit var fetchImage: View
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    private var ImageUri: Uri? = null
    private lateinit var imgAdd: ImageView
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        val list: ArrayList<CategoryModel> = ArrayList()

        // Set up RecyclerView and adapter
        recyclerView = findViewById(R.id.RecyclerAddCategory)
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        val categoryAdapter = CategoryAdapter(this, list)
        recyclerView.adapter = categoryAdapter

        // Retrieve category data from Firebase
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear() // Clear the list before adding new data
                for (dataSnapshot in snapshot.children) {
                    val model = dataSnapshot.getValue(CategoryModel::class.java)
                    model?.let { list.add(it) }
                }
                categoryAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        database.getReference().child("categories").addValueEventListener(valueEventListener)

        // Initialize dialog before accessing its views
        dialog = Dialog(this)
        dialog.setContentView(R.layout.item_add_category_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        imgCategory = dialog.findViewById(R.id.ImgCategory)
        etCategoryName = dialog.findViewById(R.id.EtCategoryName)
        UploadCategory = dialog.findViewById(R.id.BtnUpload)
        imgAdd = findViewById(R.id.ImgAdd)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading")
        progressDialog.setMessage("Please wait...")

        imgAdd.setOnClickListener {
            dialog.show()
        }

        UploadCategory.setOnClickListener {
            val name = etCategoryName.text.toString()
            if (ImageUri == null) {
                Toast.makeText(this@MainActivity, "Please Upload Image", Toast.LENGTH_SHORT).show()
            } else if (name.isEmpty()) {
                etCategoryName.error = "Enter Category Name"
            } else {
                progressDialog.show()
                uploadData()
            }
        }

        // Find the FetchImage view in the dialog
        fetchImage = dialog.findViewById(R.id.FetchImage)

        // Set click listener for image selection
        fetchImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    private fun uploadData() {
        val reference: StorageReference = storage.getReference().child("category")
            .child(Date().time.toString())

        reference.putFile(ImageUri!!).addOnSuccessListener { taskSnapshot ->
            reference.downloadUrl.addOnSuccessListener { uri ->
                val categoryModel = CategoryModel()
                categoryModel.categoryName = etCategoryName.text.toString()
                categoryModel.setRegistration = 0
                categoryModel.categoryImage = uri.toString()

                // Generate a unique key for the new category
                val categoryKey = database.reference.child("categories").push().key

                // Set the value of the category using the generated key
                categoryKey?.let {
                    database.reference.child("categories").child(it).setValue(categoryModel)
                        .addOnSuccessListener {
                            Toast.makeText(this@MainActivity, "Data Uploaded", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ImageUri = data.data
            imgCategory.setImageURI(ImageUri)
        }
    }
}
