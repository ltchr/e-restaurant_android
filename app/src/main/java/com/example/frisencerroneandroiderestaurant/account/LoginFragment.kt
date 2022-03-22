package com.example.frisencerroneandroiderestaurant.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.frisencerroneandroiderestaurant.databinding.FragmentLoginBinding
import java.security.MessageDigest

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    var delegate: UserActivityFragmentInteraction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserActivityFragmentInteraction) {
            delegate = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logButton.setOnClickListener {
            val pass_to_hash = binding.password.text.toString() + "ShieldFactory"
            val bytes = pass_to_hash.toByteArray()
            val md = MessageDigest.getInstance("SHA-512")
            val digest = md.digest(bytes)
            val digested_pass = digest.fold("") { str, it ->
                str + "%02x".format(it)
            }



            delegate?.makeRequest(
                binding.email.text.toString(),
                digested_pass.toString(),
                null,
                null,
                true
            )
        }

        binding.registerButton.setOnClickListener {
            delegate?.showRegister()
        }
    }
}