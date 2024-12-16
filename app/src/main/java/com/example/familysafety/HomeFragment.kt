package com.example.familysafety

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private val listContact = ArrayList<ContactModel>()
    private lateinit var contactViewModel: ContactViewModel

    companion object {
        private const val PERMISSION_REQUEST_READ_CONTACTS = 1001

        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val database = ContactDatabase.getInstance(requireContext())
        val repository = ContactRepository(database.contactDao())
        val viewModelFactory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, viewModelFactory)[ContactViewModel::class.java]

        // Set up RecyclerView for members
        setupMemberRecyclerView(view)

        // Set up RecyclerView for contacts
        val inviteAdapter = InviteAdapter(listContact)
        val contactRecyclerView = view.findViewById<RecyclerView>(R.id.rv1)
        contactRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        contactRecyclerView.adapter = inviteAdapter

        // Check and request permissions before loading contacts
        checkAndRequestPermissions(inviteAdapter)
    }

    private fun setupMemberRecyclerView(view: View) {
        val memberAdapter = MemberAdapter(getDummyMembers())
        val memberRecyclerView = view.findViewById<RecyclerView>(R.id.rv)
        memberRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        memberRecyclerView.adapter = memberAdapter
    }

    private fun checkAndRequestPermissions(inviteAdapter: InviteAdapter) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                PERMISSION_REQUEST_READ_CONTACTS
            )
        } else {
            // Permission already granted, load contacts
            loadContacts(inviteAdapter)
        }
    }

    private fun loadContacts(inviteAdapter: InviteAdapter) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("LoadContacts", "Starting to load contacts...")

                listContact.clear()
                Log.d("LoadContacts", "Cleared the contact list.")

                // Load contacts from Room database
                val roomContacts = contactViewModel.allContacts.value.orEmpty()
                Log.d("LoadContacts", "Fetched ${roomContacts.size} contacts from Room database.")
                listContact.addAll(roomContacts)

                withContext(Dispatchers.Main) {
                    inviteAdapter.notifyDataSetChanged()
                    Log.d("LoadContacts", "Updated RecyclerView with Room database contacts.")
                }

                // Fetch device contacts and merge
                val deviceContacts = fetchDeviceContacts()
                Log.d("LoadContacts", "Fetched ${deviceContacts.size} contacts from the device.")
                listContact.addAll(deviceContacts)

                // Insert device contacts into Room database
                contactViewModel.insertContacts(deviceContacts)
                Log.d("LoadContacts", "Inserted ${deviceContacts.size} device contacts into Room database.")

                // Update RecyclerView on the main thread
                withContext(Dispatchers.Main) {
                    inviteAdapter.notifyDataSetChanged()
                    Log.d("LoadContacts", "Updated RecyclerView with device contacts.")
                }

                Log.d("LoadContacts", "Finished loading contacts successfully.")
            } catch (e: Exception) {
                Log.e("LoadContacts", "Error loading contacts: ${e.message}", e)
            }
        }
    }

    private fun fetchDeviceContacts(): List<ContactModel> {
        val contactList = mutableListOf<ContactModel>()
        val cr = requireContext().contentResolver
        val cursor = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhoneNumber > 0) {
                    val phoneCursor = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =?",
                        arrayOf(id),
                        null
                    )

                    phoneCursor?.use { pCur ->
                        while (pCur.moveToNext()) {
                            val phoneNumber = pCur.getString(
                                pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            ).replace("[^+\\d]".toRegex(), "")
                            contactList.add(ContactModel(name, phoneNumber))
                        }
                    }
                }
            }
        }
        return contactList
    }

    private fun getDummyMembers(): List<MemberModel> {
        return listOf(
            MemberModel("Farhan", "XYZ", "12", "90%", "223M", "WiFi"),
            MemberModel("Faizan", "XYZ", "12", "90%", "223M", "WiFi"),
            MemberModel("Kashif", "XYZ", "12", "90%", "223M", "WiFi")
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val inviteAdapter = InviteAdapter(listContact)
                loadContacts(inviteAdapter)
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
