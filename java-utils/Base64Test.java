import java.util.Base64;

public class Base64Test {

public static void main(String[] args) {
	try{
		// Base64编码,字节到string是按照utf8的
		String a = Base64.getEncoder().encodeToString("abcde".getBytes("utf-8"));
		System.out.println(a);

 		//这个toString()没有指定utf8来解码字节,是错的
		String z = Base64.getDecoder().decode(a).toString();
		System.out.println(z);

		// 下面这是指定了utf8来解码字节的，是对的
		String zz = new String(Base64.getDecoder().decode(a), "UTF-8");
		System.out.println(zz);
	}
	catch (Exception e){

	}

}
			

}
