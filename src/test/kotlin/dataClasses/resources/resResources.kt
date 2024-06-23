package dataClasses.resources

import com.fasterxml.jackson.annotation.JsonProperty


data class ResResources(
    val page: Long,
    @JsonProperty("per_page")
    val perPage: Long,
    val total: Long,
    @JsonProperty("total_pages")
    val totalPages: Long,
    val data: List<Resource>?,
    val support: Support
)
