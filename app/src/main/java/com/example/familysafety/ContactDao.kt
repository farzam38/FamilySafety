package com.example.familysafety

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<ContactModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactModel)

    @Query("SELECT * FROM contactList")
    fun getAllContactsFlow(): Flow<List<ContactModel>>

    @Query("SELECT * FROM contactList")
    suspend fun getAllContacts(): List<ContactModel>

    @Delete
    suspend fun deleteContact(contact: ContactModel)

    @Update
    suspend fun updateContact(contact: ContactModel)

    @Query("DELETE FROM contactList")
    suspend fun deleteAllContacts()
}
