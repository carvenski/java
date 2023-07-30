from Crypto.Cipher import AES
import os
from Crypto import Random
import base64

"""
aes加密算法
padding : PKCS7
CBC
"""
class AES256Util:

    __BLOCK_SIZE_16 = BLOCK_SIZE_16 = AES.block_size

    @staticmethod
    def encryt(string, key, iv):
        """
        加密文本
        :param string: 文本
        :param key: 密钥
        :param iv: 偏移量/初始向量
        :return: 密文
        """
        cipher = AES.new(key, AES.MODE_CBC, iv)
        x = AES256Util.__BLOCK_SIZE_16 - (len(string) % AES256Util.__BLOCK_SIZE_16)
        # 如果最后一块不够16位需要用字符进行补全
        if x != 0:
            string = string + chr(x)*x
        msg = cipher.encrypt(string.encode('utf-8'))
        
        # msg = base64.urlsafe_b64encode(msg).replace('=', '')
        msg = base64.b64encode(msg)
        return msg

    @staticmethod
    def decrypt(en_str, key, iv):
        cipher = AES.new(key, AES.MODE_CBC, iv)
        # en_str += (len(en_str) % 4)*"="
        # decryptByts = base64.urlsafe_b64decode(en_str)
        en_str = base64.b64decode(en_str)

        msg = cipher.decrypt(en_str)
        padding_len = msg[len(msg)-1]
        return msg[0:-padding_len]



if __name__ == "__main__":
    # import hashlib
    # key = hashlib.md5().hexdigest()   # 随机生产一个md5值，32位
    string = "{'xss':'cssvv'}"

    import json
    string = json.dumps( {"username":"test", "sysname":"TestSys", 
    "redirect": "http://datastudio-sit.cpic.com.cn/webroot/decision/v5/design/report/dd83502efef34949acd8377a77152e17/view",
    "ext": {}} )

    key = b"uBdUx82vPHkDKb284d7NkjFoNcKWBuka"   # 32位
    iv = b"c558Gq0YQK2QUlMc"    # 16位
    res = AES256Util.encryt(string, key, iv)
    print(res)  # \xbf\xd7\xf7\xfc\x1c\x15\x93\xc1*Z\xc3\x0e\xda\x85\xdb\x9d\x9a\xb6z\xbb\xa7\xb3&\x8b\xa1\xd3\xc6\xf13;\xf3\n
    print(AES256Util.decrypt(res, key, iv))    # {"key": "123456"}
