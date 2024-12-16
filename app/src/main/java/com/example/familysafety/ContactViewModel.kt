package com.example.familysafety

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {



    val allContacts: LiveData<List<ContactModel>> = repository.getAllContactsFlow().asLiveData()

    fun insertContacts(contacts: List<ContactModel>) {
        viewModelScope.launch { repository.insertContacts(contacts) }
    }

    suspend fun getAllContacts(): List<ContactModel> {
        return repository.getAllContacts()
    }



    // Insert multiple contacts


    // Insert a single contact
    fun insertContact(contact: ContactModel) {
        performDatabaseOperation { repository.insertContact(contact) }
    }

    // Update a contact
    fun updateContact(contact: ContactModel) {
        performDatabaseOperation { repository.updateContact(contact) }
    }

    // Delete a contact
    fun deleteContact(contact: ContactModel) {
        performDatabaseOperation { repository.deleteContact(contact) }
    }

    // Delete all contacts
//    fun deleteAllContacts() {
//        performDatabaseOperation { repository.deleteAllContacts() }
//    }

    /**
     * Utility function to perform database operations on IO thread.
     */
    private fun performDatabaseOperation(action: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { action() }
    }
}

class ContactViewModelFactory(private val repository: ContactRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
