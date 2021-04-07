package yu.proj.fish.memory.parser.lexical;

import lombok.AllArgsConstructor;
import lombok.Data;

/**  
 * @ClassName: Token  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月6日  
 *  
 */
@Data
@AllArgsConstructor
public class Token {

    private TokenType type;
    private int line, index, len;
    private String value;
    private IdentifierType idType;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Token other = (Token)obj;
        if (idType != other.idType)
            return false;
        if (type != other.type)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idType == null) ? 0 : idType.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

}
