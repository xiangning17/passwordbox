package component

import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by ningxiang on 17-8-29.
 */
object EncryptTool {

    val TAG = "EncryptTool"

    private val CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding"      //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private val AES = "AES"                                     //AES 加密

    /**
     * 加密且将结果转换成BASE64编码字串
     *
     * @param key 密钥，为空时相当于不加密
     * @param clear 待加密的ByteArray内容
     *
     * @return AES加密后的ByteArray并使用BASE64编码后的字符串
     */
    fun encryptToBase64(key: String?, clear: ByteArray) =
            Base64.encodeToString(encrypt(key, clear), Base64.DEFAULT)

    /**
     * 将经BASE64编码的字串解码并使用key对其进行AES解密
     *
     * @param key 密钥，为空时相当于不加密
     * @param encrypted 待解密的BASE64字串内容
     *
     * @return  解密出来的原始ByteArray
     */
    fun decryptFromBase64(key: String?, encrypted: String) =
            String(decrypt(key, Base64.decode(encrypted, Base64.DEFAULT)))

    /**
     * 加密
     */
    private fun encrypt(key: String?, clear: ByteArray): ByteArray? {
        if (key.isNullOrEmpty())
            return clear   //无密码，直接返回原始内容

        return try {
            val skeySpec = SecretKeySpec(normalKey(key), AES)
            val cipher = Cipher.getInstance(CBC_PKCS5_PADDING)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
            cipher.doFinal(clear)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "encrpt error: ${e.message}")
            null
        }

    }

    /**
     * 解密
     */
    private fun decrypt(key: String?, encrypted: ByteArray): ByteArray {
        if (key.isNullOrEmpty())
            return encrypted   //无密码，直接返回原始内容

        return try {
            val skeySpec = SecretKeySpec(normalKey(key), AES)
            val cipher = Cipher.getInstance(CBC_PKCS5_PADDING)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
            cipher.doFinal(encrypted)
        } catch (e: Exception) {
            e.printStackTrace()
//            Log.e(TAG, "decrypt error: ${e.message}")
            throw RuntimeException("decrypt error: ${e.message}")
        }
    }

    /**
     * 对原始密钥进行散列处理，返回256位(32byte)密钥
     */
    private fun normalKey(key: String?) = shaEncrypt(key!!)

    /**
     * SHA加密
     *
     * @param strSrc 明文String
     * @return 散列后的ByteArray密文
     */
    private fun shaEncrypt(strSrc: String) : ByteArray {
        val md = MessageDigest.getInstance("SHA-256")// 将此换成SHA-1、SHA-512、SHA-384等参数
        md!!.update(strSrc.toByteArray())
        return md.digest()
    }
}