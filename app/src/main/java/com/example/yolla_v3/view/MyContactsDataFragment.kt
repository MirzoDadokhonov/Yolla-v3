package com.example.yolla_v3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.yolla_v3.R
import com.example.yolla_v3.repostiory.ItemData

class MyContactsDataFragment : Fragment() {

    lateinit var itemData : ItemData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_contacts_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.textViewName).text = itemData.name
        view.findViewById<TextView>(R.id.textViewPhoneNum).text = itemData.phoneNum

        val iconStatus : ImageView = view.findViewById(R.id.imageView)
        if (itemData.status == null) {
            iconStatus.visibility = View.GONE
        } else {
            iconStatus.visibility = View.VISIBLE
            iconStatus.setImageResource(
                if (itemData.status == true) {
                    R.drawable.ic_baseline_error_24
                } else {
                    R.drawable.ic_baseline_check_circle_24
                }
            )
        }
    }

    companion object {

        fun newInstance(itemData: ItemData) : MyContactsDataFragment {
            val fragment = MyContactsDataFragment()
            fragment.itemData = itemData
            return fragment
        }
    }
}