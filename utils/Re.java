
public class Re {

    public static void main(String[] args) throws Exception {
        String s = "a value=0.4522 456";
        // 正则匹配替换
        s = s.replaceAll("value=.*\\s{1,1}", "value=100 ");
        System.out.println(s);
    }
}
