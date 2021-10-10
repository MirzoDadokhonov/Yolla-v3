package com.example.yolla_v3.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.yolla_v3.R
import com.example.yolla_v3.adapter.MainAdapter
import com.example.yolla_v3.repostiory.ItemData
import com.example.yolla_v3.view_model.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyContactsListFragment : Fragment(), View.OnClickListener {

    private val PICK_CONTACT_CODE = 191
    lateinit var recyclerView : RecyclerView
    lateinit var viewModel: MainViewModel
    lateinit var adapter : MainAdapter
    private lateinit var progressBar: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProvider(this).get(MainViewModel :: class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_contacts_list, container, false)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener(this)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        progressBar

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel._isLoading.observe(viewLifecycleOwner, {
            if (it) {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        })
        viewModel.getMyDataSet().observe(viewLifecycleOwner, {
            adapter = MainAdapter(it, this)
            recyclerView.adapter = adapter
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_CONTACT_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val cursor = requireActivity().contentResolver.query(
                data.data ?: return,
                arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
                null, null, null)
            if (cursor?.moveToFirst() == true) {
                val name = cursor.getString(0)
                val phoneNum = cursor.getString(1)
                val itemData = ItemData(name, phoneNum, false)
                viewModel.checkAndAddContact(itemData)
                adapter.notifyDataSetChanged()

            }
            cursor?.close()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingActionButton -> {
                val i = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                startActivityForResult(i, PICK_CONTACT_CODE)
            }
            R.id.recyclerView -> {
                val position = v.tag as Int
                viewModel.getContactByPosition(position)?.let {
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, MyContactsDataFragment.newInstance(it)).addToBackStack(MyContactsDataFragment.toString()).commit()
                }
            }
        }
    }
}