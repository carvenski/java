
public class AES256Util {
    // 加密算法
    private static final String AES = "AES";
    // 密钥, 256位32个字节
    public static final String DEFAULT_SECRET_KEY = "uBdUx82vPHkDKb284d7NkjFoNcKWBuka";
    // 初始向量IV, 初始向量IV的长度规定为128位16个字节
    private static final byte[] KEY_VI = "c558Gq0YQK2QUlMc".getBytes();        
    // 加密解密算法/加密模式/填充方式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    
    private static java.util.Base64.Encoder base64Encoder = java.util.Base64.getEncoder();
    private static java.util.Base64.Decoder base64Decoder = java.util.Base64.getDecoder();

    static {
        java.security.Security.setProperty("crypto.policy", "unlimited");
    }

     // AES加密
    public static String encode(String content) {
        try {
            javax.crypto.SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(DEFAULT_SECRET_KEY.getBytes(), AES);
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(KEY_VI));

            // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            // AES加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            // 将加密后的数据转换为base64字符串
            return base64Encoder.encodeToString(byteAES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // AES解密
    public static String decode(String content) {
        try {
            javax.crypto.SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(DEFAULT_SECRET_KEY.getBytes(), AES);
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(KEY_VI));

            // 将base64编码后的内容解码成字节数组
            byte[] byteContent = base64Decoder.decode(content);
            // AES解密
            byte[] byteDecode = cipher.doFinal(byteContent);
            // bytes转string, UTF8
            return new String(byteDecode, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Long t0 = System.currentTimeMillis();
        String dbPassword = "{'xss':'cssvv'}";
        String encryptDbPwd = AES256Util.encode(dbPassword);
        System.out.println("encrypt: " + encryptDbPwd);

        String decrypt = AES256Util.decode(encryptDbPwd);
        System.out.println("decrypt:" + decrypt);
        System.out.println("AES Time=" + (System.currentTimeMillis()-t0));
    }

}