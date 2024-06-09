package com.example.instagramspf.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramspf.Models.User
import com.example.instagramspf.R
import com.example.instagramspf.adapters.SearchAdapter
import com.example.instagramspf.databinding.FragmentSearchBinding
import com.example.instagramspf.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter=SearchAdapter(requireContext(),userList)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()
            for (i in it.documents) {
                if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }else{
                    var user: User = i.toObject<User>()!!
                    tempList.add(user)
                }

            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        binding.searchButton.setOnClickListener{
            var text = binding.searchButton.text.toString()

            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                userList.clear()
                if(it.isEmpty)
                {

                }
                else
                {
                    for (i in it.documents) {
                        if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                        }else{
                            var user: User = i.toObject<User>()!!
                            tempList.add(user)
                        }

                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }

            }

        }
        return binding.root
    }

    companion object {


    }
}