package com.example.instagramspf.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramspf.Post.PostActivity
import com.example.instagramspf.Post.ReelsActivity
import com.example.instagramspf.R
import com.example.instagramspf.databinding.FragmentPostBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater,container,false)
        binding.post.setOnClickListener {
            activity?.startActivity (Intent(requireContext(), PostActivity::class.java))
            activity?.finish()
        }
        binding.reel.setOnClickListener {
            activity?.startActivity (Intent(requireContext(), ReelsActivity::class.java))
        }


        return binding.root
    }

    companion object {


    }
}