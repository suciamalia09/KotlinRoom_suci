package com.example.kotlinroom_suci.room

import androidx.room.*

@Dao
interface StorybookDao {

    @Insert
    suspend fun addStorybook(storybook: Storybook)

    @Update
    suspend fun updateStorybook(storybook: Storybook)

    @Delete
    suspend fun deleteStorybook(storybook: Storybook)

    @Query ("SELECT * FROM storybook")
    suspend fun getStorybooks():List<Storybook>

    @Query ("SELECT*FROM storybook WHERE id=:storybook_id")
    suspend fun getStorybook(storybook_id:Int):List<Storybook>
}