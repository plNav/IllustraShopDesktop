package utils

import java.security.MessageDigest
/** Get String and returns it cyphered */
fun getSHA256(passw : String) : String {
    try{
        val bytes = passw.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }

    }catch (e: Exception){
        throw e
    }
}
