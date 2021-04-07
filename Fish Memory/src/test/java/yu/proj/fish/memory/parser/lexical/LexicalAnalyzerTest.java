package yu.proj.fish.memory.parser.lexical;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**  
 * @ClassName: LexicalAnalyzerTest  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月7日  
 *  
 */
public class LexicalAnalyzerTest {

    @Test
    public void test() {
        String text = "过程组 快速排序{}";

        List<Token> tokens = builder().token(TokenType.PROCESS_GROUP)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS_GROUP)
            .token(TokenType.LEFT_BRACE).token(TokenType.RIGHT_BRACE).tokens;

        assertListEquals(tokens, analyze(text));
    }

    @Test
    public void testProcess() {
        String text = "过程组 快速排序{\n" + "过程 快速排序(){}\n" + "}";

        List<Token> tokens = builder().token(TokenType.PROCESS_GROUP)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS_GROUP)
            .token(TokenType.LEFT_BRACE).token(TokenType.PROCESS)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS)
            .token(TokenType.LEFT_PARENTHESIS)
            .token(TokenType.RIGHT_PARENTHESIS).token(TokenType.LEFT_BRACE)
            .token(TokenType.RIGHT_BRACE).token(TokenType.RIGHT_BRACE).tokens;

        assertListEquals(tokens, analyze(text));
    }

    @Test
    public void testPara() {
        String text = "过程组 快速排序{\n" + "过程 快速排序(int[] 数组){}\n" + "}";

        List<Token> tokens = builder().token(TokenType.PROCESS_GROUP)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS_GROUP)
            .token(TokenType.LEFT_BRACE).token(TokenType.PROCESS)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS)
            .token(TokenType.LEFT_PARENTHESIS)
            .token(TokenType.IDENTIFIER, "int[]", IdentifierType.CLASS)
            .token(TokenType.IDENTIFIER, "数组", IdentifierType.VARIABLE)
            .token(TokenType.RIGHT_PARENTHESIS).token(TokenType.LEFT_BRACE)
            .token(TokenType.RIGHT_BRACE).token(TokenType.RIGHT_BRACE).tokens;

        assertListEquals(tokens, analyze(text));
    }

    @Test
    public void testPara2() {
        String text =
        // @formatter:off
            "过程组 快速排序{\n" + 
                "过程 快速排序(int[] 数组, int 左端点, int 右端点){\n"
                    + "[如果](左端点>=右端点)，那么就没有必要继续下去了{\r\n" + "            "
                        + "[退出].\r\n"
                + "    }.\n" + 
                 "}\n" + 
             "}";
        //@@formatter:on

        List<Token> tokens = builder().token(TokenType.PROCESS_GROUP)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS_GROUP)
            .token(TokenType.LEFT_BRACE).token(TokenType.PROCESS)
            .token(TokenType.IDENTIFIER, "快速排序", IdentifierType.PROCESS)
            .token(TokenType.LEFT_PARENTHESIS)

            .token(TokenType.IDENTIFIER, "int[]", IdentifierType.CLASS)
            .token(TokenType.IDENTIFIER, "数组", IdentifierType.VARIABLE)

            .token(TokenType.COMMA)

            .token(TokenType.IDENTIFIER, "int", IdentifierType.CLASS)
            .token(TokenType.IDENTIFIER, "左端点", IdentifierType.VARIABLE)

            .token(TokenType.COMMA)

            .token(TokenType.IDENTIFIER, "int", IdentifierType.CLASS)
            .token(TokenType.IDENTIFIER, "右端点", IdentifierType.VARIABLE)

            .token(TokenType.RIGHT_PARENTHESIS).token(TokenType.LEFT_BRACE)

            .token(TokenType.LEFT_SQUARE_BRACKET)

            .token(TokenType.IDENTIFIER, "如果", IdentifierType.PROCESS_OR_METHOD)

            .token(TokenType.RIGHT_SQUARE_BRACKET)

            .token(TokenType.LEFT_PARENTHESIS)

            .token(TokenType.IDENTIFIER, "左端点", IdentifierType.VARIABLE)

            .token(TokenType.GREAT_OR_EQUAL)

            .token(TokenType.IDENTIFIER, "右端点", IdentifierType.VARIABLE)

            .token(TokenType.RIGHT_PARENTHESIS)

            .token(TokenType.LEFT_BRACE)

            .token(TokenType.LEFT_SQUARE_BRACKET)

            .token(TokenType.IDENTIFIER, "退出", IdentifierType.PROCESS_OR_METHOD)

            .token(TokenType.RIGHT_SQUARE_BRACKET)

            .token(TokenType.RIGHT_BRACE)

            .token(TokenType.RIGHT_BRACE).token(TokenType.RIGHT_BRACE).tokens;

        assertListEquals(tokens, analyze(text));
    }

    public static <T> void assertListEquals(List<T> expected, List<T> actual) {
        if (expected.size() != actual.size()) {
            throw new AssertionError("List 长度不一致，期望长度为：" + expected.size()
                + " 实际长度为：" + actual.size());
        }
        for (int i = 0; i < expected.size(); ++i) {
            if (!expected.get(i).equals(actual.get(i))) {
                throw new AssertionError("索引" + i + "处不一致，期望为："
                    + expected.get(i) + " 实际为：" + actual.get(i));
            }
        }
    }

    public static List<Token> analyze(String text) {
        return new LexicalAnalyzer(text).getTokens();
    }

    public static Token token(TokenType type, String value) {
        return token(type, value, null);
    }

    public static Token token(TokenType type, String value,
        IdentifierType idType) {
        return new Token(type, 0, 0, 0, value, idType);
    }

    public static TokenTestListBuilder builder() {
        return new TokenTestListBuilder();
    }

    static class TokenTestListBuilder {

        List<Token> tokens = new ArrayList<>();

        TokenTestListBuilder token(TokenType type) {
            return token(type, null);
        }

        TokenTestListBuilder token(TokenType type, String value) {
            tokens.add(LexicalAnalyzerTest.token(type, value));
            return this;
        }

        TokenTestListBuilder token(TokenType type, String value,
            IdentifierType idType) {
            tokens.add(LexicalAnalyzerTest.token(type, value, idType));
            return this;
        }

    }


}
