package dataClasses.users

import com.fasterxml.jackson.annotation.JsonProperty


data class ResUsers(
    val page: Long,
    @JsonProperty("per_page")
    val perPage: Long,
    val total: Long,
    @JsonProperty("total_pages")
    val totalPages: Long,
    val data: List<User>?,
    val support: Support
)
