package com.example.familysafety

import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    suspend fun insertContacts(contacts: List<ContactModel>) = contactDao.insertContacts(contacts)

    suspend fun insertContact(contact: ContactModel) = contactDao.insertContact(contact)

    fun getAllContactsFlow(): Flow<List<ContactModel>> = contactDao.getAllContactsFlow()

    suspend fun getAllContacts(): List<ContactModel> = contactDao.getAllContacts()

    suspend fun deleteContact(contact: ContactModel) = contactDao.deleteContact(contact)

    suspend fun updateContact(contact: ContactModel) = contactDao.updateContact(contact)
}
