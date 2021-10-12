package com.example.headsupprep

class Celeb {
    var data: List<Details>? = null
    data class Details(
        val name: String? = null,
        val taboo1: String? = null,
        val taboo2: String? = null,
        val taboo3: String? = null,
        val pk: Int? = null
    )
}