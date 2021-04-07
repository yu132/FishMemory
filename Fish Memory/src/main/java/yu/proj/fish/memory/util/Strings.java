package yu.proj.fish.memory.util;

import java.util.function.Predicate;

/**  
 * @ClassName: Strings  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public class Strings {

    public static boolean equals(String s1, int i1, String s2, int i2,
        int len) {
        if (s1.length() - i1 < len || s2.length() - i2 < len) {
            return false;
        }
        for (int i = 0; i < len; ++i) {
            if (s1.charAt(i1 + i) != s2.charAt(i2 + i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(String str, char[] chStream, int streamIndex) {
        if (chStream.length - streamIndex < str.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) != chStream[streamIndex + i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkAllChars(String str,
        Predicate<Character> checker) {
        for (char ch : str.toCharArray()) {
            if (!checker.test(ch)) {
                return false;
            }
        }
        return true;
    }

}
