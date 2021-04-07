package yu.proj.fish.memory.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**  
 * @ClassName: StringsText  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public class StringsTest {

    @Test
    public void test() {
        assertEquals(true, Strings.equals("a123", 1, "12123", 2, 3));
        assertEquals(false, Strings.equals("a12", 1, "12123", 2, 3));
        assertEquals(false, Strings.equals("a123", 1, "1212", 2, 3));
        assertEquals(false, Strings.equals("a123", 1, "12124", 2, 3));
    }

    @Test
    public void test2() {
        assertEquals(true, Strings.equals("123", "a123".toCharArray(), 1));
        assertEquals(false, Strings.equals("123", "a12".toCharArray(), 1));
        assertEquals(false, Strings.equals("123", "a124".toCharArray(), 1));
    }

}
