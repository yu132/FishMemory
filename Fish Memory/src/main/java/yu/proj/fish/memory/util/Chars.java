package yu.proj.fish.memory.util;

/**  
 * @ClassName: Chars  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public class Chars {

    public static boolean isTypeCh(char ch) {
        return isNumberOrLetterOrChineseCh(ch) || ch == '[' || ch == ']';
    }

    public static boolean isNumberOrLetterOrChineseCh(char ch) {
        return Character.isLetterOrDigit(ch) || isChineseCh(ch);
    }

    public static boolean isLetterOrChineseCh(char ch) {
        return Character.isLetter(ch) || isChineseCh(ch);
    }

    private static boolean isChineseCh(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
            return true;
        }
        return false;
    }

}
