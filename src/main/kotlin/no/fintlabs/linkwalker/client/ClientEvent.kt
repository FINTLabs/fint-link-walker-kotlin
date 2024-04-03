package no.fintlabs.linkwalker.client

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.util.StringUtils

class ClientEvent(
    val client: Client? = null,
    val orgId: String? = null,
    val operation: Operation? = null,
    val errorMessage: String? = null
) {

    fun hasError(): Boolean {
        return StringUtils.hasText(errorMessage)
    }

    @JsonIgnore
    fun getOrganisationObjectName(): String {
        return orgId!!.replace("\\.".toRegex(), "_")
    }

    enum class Operation {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

}