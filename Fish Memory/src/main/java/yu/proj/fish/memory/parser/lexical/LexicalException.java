package yu.proj.fish.memory.parser.lexical;

/**  
 * @ClassName: LexicalException  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
public class LexicalException extends RuntimeException {

    private static final long serialVersionUID = -3622716700412035784L;

    public LexicalException(String errMesg, int line, int indexOfLine) {
        super(toExceptionMesg(errMesg, line, indexOfLine));
    }

    private static String toExceptionMesg(String errMesg, int line,
        int indexOfLine) {

        return errMesg + ", 位置" + line + ":" + indexOfLine;
    }

}
