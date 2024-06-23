package dataClasses.users

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    val id: Int,
    val email: String,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    val avatar: String
)