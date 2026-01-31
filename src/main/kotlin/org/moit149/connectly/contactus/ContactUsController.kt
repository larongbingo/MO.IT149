package org.moit149.connectly.contactus

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/contactus")
class ContactUsController(private val contactRepository: ContactRepository) {
    @PostMapping
    fun addContact(@RequestBody addContactDto: AddContactDto): ResponseEntity<AddContactResponse>? {
        val contact = addContactDto.toContactModel()

        try {
            contactRepository.save(contact)
        }
        catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        return ResponseEntity.ok(AddContactResponse())
    }
}

data class AddContactDto(val email: String) {
    fun toContactModel() = Contact(null, email, LocalDateTime.now())
}
data class AddContactResponse(val receivedAt: LocalDateTime = LocalDateTime.now())