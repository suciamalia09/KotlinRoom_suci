package com.example.kotlinroom_suci.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Storybook (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
        )
