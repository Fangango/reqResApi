package dataClasses.resources

import com.fasterxml.jackson.annotation.JsonProperty


data class Resource(
    val id: Int,
    val name: String,
    val year: Int,
    val color: String,
    @JsonProperty("pantone_value")
    val pantoneValue: String
)