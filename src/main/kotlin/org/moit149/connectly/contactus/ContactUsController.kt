package org.moit149.connectly.contactus

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/contactus")
class ContactUsController(private val contactRepository: ContactRepository) {
    @PostMapping
    fun addContact(@RequestBody addContactDto: AddContactDto): AddContactResponse? {
        val contact = addContactDto.toContactModel()

        try {
            contactRepository.save(contact)
        }
        catch (e: Exception) {
            return null
        }

        return AddContactResponse(contact.receivedAt)
    }
}

data class AddContactDto(val email: String) {
    fun toContactModel() = Contact(null, email, LocalDateTime.now())
}
data class AddContactResponse(val receivedAt: LocalDateTime = LocalDateTime.now())