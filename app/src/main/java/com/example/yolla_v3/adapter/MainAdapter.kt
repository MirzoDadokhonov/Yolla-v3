package com.example.yolla_v3.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yolla_v3.R
import com.example.yolla_v3.repostiory.ItemData

class MainAdapter(val myDataSet: ArrayList<ItemData>, val onClickListener: View.OnClickListener) :
    RecyclerView.Adapter<MainAdapter.MyVieWHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyVieWHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.list_item, p0, false)
        return MyVieWHolder(view)
    }

    override fun onBindViewHolder(p0: MyVieWHolder, p1: Int) {
        val itemData = myDataSet[p1]
        p0.textViewName.text = itemData.name
        p0.textViewPhoneNum.text = itemData.phoneNum
        if (itemData.status == null) {
            p0.iconStatus.visibility = View.GONE
        } else {
            p0.iconStatus.visibility = View.VISIBLE
            p0.iconStatus.setImageResource(
                if (itemData.status == true) {
                    R.drawable.ic_baseline_error_24
                } else if (itemData.status == false) {
                    R.drawable.ic_baseline_check_circle_24
                } else {
                    0
                }
            )
        }
        p0.itemView.setOnClickListener(onClickListener)
        p0.itemView.tag = p1
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }


    class MyVieWHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.textViewName)
        var textViewPhoneNum: TextView = view.findViewById(R.id.textViewPhoneNum)
        var iconStatus: ImageView = view.findViewById(R.id.imageView)

    }

}