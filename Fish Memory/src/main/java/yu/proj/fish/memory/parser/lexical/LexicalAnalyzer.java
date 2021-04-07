package yu.proj.fish.memory.parser.lexical;

import static yu.proj.fish.memory.parser.lexical.TokenType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import yu.proj.fish.memory.util.Chars;
import yu.proj.fish.memory.util.Strings;

/**  
 * @ClassName: LexicalAnalyzer  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public class LexicalAnalyzer {

    int index = 0;

    int line = 0;

    int indexOfLine = 0;

    char[] text;

    List<Token> tokenList;

    LexicalException e;

    public LexicalAnalyzer(String text) {
        super();
        this.text = text.toCharArray();
    }

    public List<Token> getTokens() throws LexicalException {
        if (tokenList == null && e == null) {
            try {
                tokenList = new ArrayList<>();
                analyze();
            } catch (LexicalException e) {
                this.e = e;
            }
        }
        if (e != null) {
            throw e;
        }
        return tokenList;
    }

    void analyze() {
        processGroup();
    }

    boolean processGroup() {
        if (!isProcessGroup()) {
            return false;
        }

        acceptToken("过程组", PROCESS_GROUP);

        acceptIdentifierMandatory(IdentifierType.PROCESS_GROUP);

        acceptTokenMandatory("{", LEFT_BRACE);

        while (isProcess()) {
            acceptProcess();
        }

        acceptTokenMandatory("}", RIGHT_BRACE);

        return true;
    }

    boolean isProcess() {
        return isWord("过程");
    }

    void acceptProcess() {
        acceptToken("过程", PROCESS);

        acceptIdentifierMandatory(IdentifierType.PROCESS);

        acceptTokenMandatory('(', LEFT_PARENTHESIS);

        acceptParameters();

        acceptTokenMandatory(')', RIGHT_PARENTHESIS);

        acceptTokenMandatory('{', LEFT_BRACE);

        acceptStatements();

        acceptTokenMandatory('}', RIGHT_BRACE);
    }

    private void acceptStatements() {
        while (isStatement()) {
            acceptStatement();
        }
    }

    private void acceptParameters() {
        while (isParameter()) {
            acceptParameter();
        }
    }

    boolean isParameter() {
        return !is(')');
    }

    void acceptParameter() {
        int len = getLenIf(Chars::isTypeCh);
        String typeWord = String.valueOf(text, index, len);
        if (!isType(typeWord)) {
            throw typeExcpetion(typeWord);
        }
        acceptToken(typeWord, IDENTIFIER, typeWord, IdentifierType.CLASS);
        acceptIdentifierMandatory(IdentifierType.VARIABLE);

        acceptParameterDescription();

        acceptTokenIfExists(',', COMMA);
    }

    boolean isType(String type) {
        if (type.endsWith("[]")) {
            type = type.substring(0, type.length() - 2);
        }
        if (type.length() == 0) {
            return false;
        }
        char firstChar = type.charAt(0);
        if ('0' <= firstChar && firstChar <= '9') {
            return false;
        }
        return Strings.checkAllChars(type, Chars::isNumberOrLetterOrChineseCh);
    }

    boolean isStatement() {
        return !is('}');
    }

    void acceptStatement() {
        outer:
        while (true) {
            ignoreUntil('[', '(', '{', '.');
            if (noMoreText()) {
                throw wordExcpetion(".");
            }
            switch (chNow()) {
                case '[':
                    acceptTokenMandatory('[', LEFT_SQUARE_BRACKET);
                    acceptIdentifierMandatory(IdentifierType.PROCESS_OR_METHOD);
                    acceptTokenMandatory(']', RIGHT_SQUARE_BRACKET);
                    break;
                case '{':
                    acceptTokenMandatory('{', LEFT_BRACE);
                    acceptStatements();
                    acceptTokenMandatory('}', RIGHT_BRACE);
                    break;
                case '(':
                    acceptTokenMandatory('(', LEFT_PARENTHESIS);
                    acceptParameterWhenMethodCall();
                    acceptTokenMandatory(')', RIGHT_PARENTHESIS);
                    break;
                case '.':
                    acceptTokenMandatory('.', PERIOD);
                    break outer;
            }
        }
    }

    void acceptParameterWhenMethodCall() {
        acceptExpression();
        acceptParameterDescription();
    }

    void acceptExpression() {
        // TODO
    }

    void acceptParameterDescription() {
        // TODO
    }

    void ignoreUntil(char... chs) {
        outer:
        while (true) {
            if (noMoreText()) {
                break;
            }
            for (char ch : chs) {
                if (chNow() == ch) {
                    break outer;
                }
            }
            ++index;
        }
    }

    private char chNow() {
        return text[index];
    }

    private boolean noMoreText() {
        return index == text.length;
    }

    void acceptTokenMandatory(String wordInText, TokenType type) {
        if (!isWord(wordInText)) {
            throw wordExcpetion(wordInText);
        }
        acceptToken(wordInText, type);
    }

    void acceptTokenMandatory(char charInText, TokenType type) {
        if (!is(charInText)) {
            throw wordExcpetion(String.valueOf(charInText));
        }
        acceptToken(String.valueOf(charInText), type);
    }

    void acceptTokenIfExists(char charInText, TokenType type) {
        if (is(charInText)) {
            acceptToken(String.valueOf(charInText), type);
        }
    }

    void acceptIdentifierMandatory(IdentifierType type) {
        if (!isIdentifier()) {
            throw identifierExcpetion();
        }

        acceptIdentifier(type);
    }

    boolean isProcessGroup() {
        return isWord("过程组");
    }

    boolean isWord(String word) {
        acceptWhitespace();
        if (!Strings.equals(word, text, index)) {
            return false;
        }
        int nextIndex = index + word.length();
        return nextIndex >= text.length
            || !Chars.isNumberOrLetterOrChineseCh(text[nextIndex]);
    }

    boolean is(char ch) {
        acceptWhitespace();
        return index < text.length && chNow() == ch;
    }

    boolean isIdentifier() {
        acceptWhitespace();
        return index < text.length && Chars.isLetterOrChineseCh(chNow());
    }

    void acceptIdentifier(IdentifierType type) {
        int len = getLenIf(Chars::isNumberOrLetterOrChineseCh);
        String id = String.valueOf(text, index, len);
        acceptToken(id, IDENTIFIER, id, type);
    }

    int getLenIf(Predicate<Character> predicate) {
        int len = 0;
        while (predicate.test(text[index + len])) {
            ++len;
        }
        return len;
    }

    void acceptToken(String wordInText, TokenType type) {
        acceptToken(wordInText, type, null, null);
    }

    void acceptToken(String wordInText, TokenType type, String value,
        IdentifierType idType) {
        Token token = new Token(type, line, indexOfLine, wordInText.length(),
            value, idType);
        tokenList.add(token);
        accept(wordInText);
    }

    void acceptWhitespace() {
        while (index < text.length && Character.isWhitespace(chNow())) {
            accept(chNow());
        }
    }

    void accept(char ch) {
        ++index;
        ++indexOfLine;
        if (ch == '\n') {
            ++line;
            indexOfLine = 0;
        }
    }

    void accept(String word) {
        index += word.length();
        indexOfLine += word.length();
    }

    LexicalException identifierExcpetion() {
        return new LexicalException("标识符缺失错误:", line, indexOfLine);
    }

    LexicalException typeExcpetion(String type) {
        return new LexicalException("类型命名错误:" + type, line, indexOfLine);
    }

    LexicalException typeExcpetion() {
        return new LexicalException("类型缺失错误:", line, indexOfLine);
    }

    LexicalException wordExcpetion(String word) {
        return new LexicalException("缺少" + word, line, indexOfLine);
    }

}
