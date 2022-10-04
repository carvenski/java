# coding=utf-8
import rsa
import base64

def create_keys():  # 生成公钥和私钥
    (pubkey, privkey) = rsa.newkeys(1024)
    pub = pubkey.save_pkcs1()   # python的RSA库使用的是pkcs1格式的公私钥
    with open('public.pem', 'wb+')as f: # java的RSA默认使用的是pkcs8格式的
        f.write(pub)                    # 这2种公私钥格式需要转换一下才能互解
    pri = privkey.save_pkcs1()          # openssl rsa -in pkcs8.pem -out pkcs1.pem
    with open('private_pkcs1.pem', 'wb+')as f:
        f.write(pri)

def encrypt(content):
    with open('public.pem', 'rb') as publickfile:
        p = publickfile.read()
    print("公钥:", p)
    pubkey = rsa.PublicKey.load_pkcs1(p)  # pkcs1格式
    original_text = str(content).encode("utf8")
    #加密是加的bytes类型
    crypt_text = rsa.encrypt(original_text, pubkey) 
    print("加密后的密文类型:", type(crypt_text)) # bytes
    print("加密后的密文:", crypt_text)
    return crypt_text  # 返回的是字节


def decrypt(crypt_text):
    with open('private_pkcs1.pem', 'rb') as privatefile:
        p = privatefile.read()
    print("私钥:", p)
    privkey = rsa.PrivateKey.load_pkcs1(p)
    # 注意这里结果是bytes类型，需要进行decode()转化为str
    lase_text = rsa.decrypt(crypt_text, privkey).decode("utf8")  
    print("解密后的明文类型:", type(lase_text))
    print("解密后的明文:", lase_text)
    return lase_text


if __name__ == '__main__':
    # TODO　生成　.pem 证书
    create_keys()
    # TODO　加密和解密 返回字节
    content = 'hello'    
    print("需要加密的明文:", content)
    encrypt_str = encrypt(content)
    encrypt_str_b64 = base64.b64encode(encrypt_str)
    print('经过base64加密后:\n', encrypt_str_b64)

    # TODO　解密
    encrypt_str = base64.b64decode(encrypt_str_b64)
    content = decrypt(encrypt_str)
    print(content)


