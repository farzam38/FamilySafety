package com.example.familysafety

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.familysafety.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize ViewBinding
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Set user details
        setUserDetails()

        // Logout button click listener
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

        return binding.root
    }

    private fun setUserDetails() {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        if (currentUser != null) {
            // Set name, email, and phone number from FirebaseUser
            binding.tvName.text = currentUser.displayName ?: "Name not available"
            binding.tvEmail.text = currentUser.email ?: "Email not available"
            binding.tvPhone.text = currentUser.phoneNumber ?: "XXXXXXX878"
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logoutUser() {
        // Sign out from Firebase
        SharedPref.putBoolean(PrefConstants.IS_USER_LOGGED_IN,false)

        firebaseAuth.signOut()

        // Navigate back to LoginActivity
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }

    companion object {
        fun newInstance(): Fragment {
            return ProfileFragment()

        }
    }
}
