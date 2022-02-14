package com.example.frisencerroneandroiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frisencerroneandroiderestaurant.databinding.FragmentPhotoBinding
import com.squareup.picasso.Picasso

class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(URL)

        if (url?.isNotEmpty() == true) {
            Picasso.get().load(url).placeholder(R.drawable.android_pie)
                .into(binding.photo)
        }
    }

    companion object {
        fun newInstance(url: String) =
            PhotoFragment().apply { arguments = Bundle().apply { putString(URL, url) } }

        const val URL = "URL"
    }
}