
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import java.util.Base64



fun main() {
    val iv = ByteArray(8) {0x00}
    val key = genKey()
    println("Введите сообщения для шифрования:")
    val i = "${readLine()}"
    val crypt = encryption(i, key, iv)
    println("Crypt $crypt")
    val uncrypt = decryption(crypt, key, iv)
    println("Uncrypt $uncrypt")

}


/*
*   Функция для шифрования которая возвращает строку
*
*   В переменную cipher мы создаём экземпляр класса Cipher
*   и при помощи метода getInstance указываем какой метод шифрования использовать
*
*   следующим шагом инициализируем методом init передавая в него режим шифрования и ключ
*
*
*/


fun encryption(data: String, key: SecretKey, iv: ByteArray ): String {
    val cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding")

    cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
    val encrypt = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
    return Base64.getEncoder().encodeToString(encrypt)
}

fun decryption(data: String, key: SecretKey, iv: ByteArray): String {
    val cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
    val decodedBytes = Base64.getDecoder().decode(data)
    return String(cipher.doFinal(decodedBytes), Charsets.UTF_8)
}

fun genKey(): SecretKey {
    val keyGen = KeyGenerator.getInstance("Blowfish")
    keyGen.init(448)
    return keyGen.generateKey()
}


