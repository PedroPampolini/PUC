import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RSA {
  private final static BigInteger ONE = new BigInteger("1");
  private BigInteger privateKey;
  private BigInteger publicKey;
  private BigInteger n;

  // Gera chaves RSA
  RSA() throws NoSuchAlgorithmException {
    SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
    byte[] b = "randomSeed".getBytes();
    rnd.setSeed(b);
    BigInteger p = new BigInteger(1024 / 2, 100, rnd);
    BigInteger q = new BigInteger(1024 / 2, 100, rnd);
    BigInteger z = (p.subtract(ONE)).multiply(q.subtract(ONE));

    n = p.multiply(q); // valor de n
    publicKey = new BigInteger("65537");  // valor do d = 2^16 + 1, primo em relação a z
    privateKey = publicKey.modInverse(z); // valor do e = d*e mod z = 1
  }

  // Encripta com chava públic
  private BigInteger encrypt(BigInteger message) {
    return message.modPow(publicKey, n);
  }

  //Desencripta com chave privada
  private BigInteger decrypt(BigInteger encrypted) {
    return encrypted.modPow(privateKey, n);
  }

  public String EncrypText(String string) {
    BigInteger encrypted = encrypt(new BigInteger(string.getBytes(StandardCharsets.UTF_8)));
    return encrypted.toString();
  }

  public String DecryptText(String encryptedString) {
    BigInteger encrypted = new BigInteger(encryptedString);
    return new String(decrypt(encrypted).toByteArray(), StandardCharsets.UTF_8);
  }
}