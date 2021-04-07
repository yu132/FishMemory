package yu.proj.fish.memory.parser.lexical;

/**  
 * @ClassName: TokenType  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public enum TokenType {

    PROCESS_GROUP, //
    IDENTIFIER, //

    PROCESS, //

    LEFT_BRACE, // '{'
    RIGHT_BRACE, // '}'

    LEFT_PARENTHESIS, // '('
    RIGHT_PARENTHESIS, // ')'

    LEFT_SQUARE_BRACKET, // '['
    RIGHT_SQUARE_BRACKET, // ']'

    COMMA, // ','
    PERIOD, // '.'

    GREAT, // '>'
    GREAT_OR_EQUAL, // '>='

    LITTLE, // '<'
    LITTLE_OR_EQUAL, // '<='

    EQUAL, // '='
    NOT_EQUAL,// '!='

}
