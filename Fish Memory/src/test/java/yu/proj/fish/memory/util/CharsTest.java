package yu.proj.fish.memory.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**  
 * @ClassName: CharsTest  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public class CharsTest {

    @Test
    public void test() {
        assertEquals(true, Chars.isNumberOrLetterOrChineseCh('1'));
        assertEquals(true, Chars.isNumberOrLetterOrChineseCh('a'));
        assertEquals(true, Chars.isNumberOrLetterOrChineseCh('A'));
        assertEquals(true, Chars.isNumberOrLetterOrChineseCh('的'));
        assertEquals(false, Chars.isNumberOrLetterOrChineseCh('，'));
        assertEquals(false, Chars.isNumberOrLetterOrChineseCh('【'));
        assertEquals(false, Chars.isNumberOrLetterOrChineseCh('（'));
        assertEquals(false, Chars.isNumberOrLetterOrChineseCh(','));
        assertEquals(false, Chars.isNumberOrLetterOrChineseCh('['));
        assertEquals(false, Chars.isNumberOrLetterOrChineseCh(' '));
    }

}
