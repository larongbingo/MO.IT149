package org.moit149.connectly.contactus

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "contacts")
data class Contact(
    @Id val id: String?,
    val email: String,
    val receivedAt: LocalDateTime
)
