package com.example.admindata.Adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.admindata.CapRegistrationData
import com.example.admindata.HomeActivity
import com.example.admindata.Model.CategoryModel
import com.example.admindata.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CategoryAdapter(private val context: Context, private val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryModel = list[position]
        holder.txCategoryName.text = categoryModel.categoryName
        Picasso.get()
            .load(categoryModel.categoryImage)
            .placeholder(R.drawable.car)
            .into(holder.imgCategory)

        holder.itemView.setOnClickListener {

            val selectedItemText = list[position].categoryName
            if (selectedItemText == "cab book") {

                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra("selectedCategoryName", selectedItemText)
                context.startActivity(intent)
            } else {

                Toast.makeText(context, "Selected category is not 'cap book'", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: ArrayList<CategoryModel>) {

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgCategory: CircleImageView = itemView.findViewById(R.id.ImgCategory)
        var txCategoryName: TextView = itemView.findViewById(R.id.TxCategoryName)
    }
}
