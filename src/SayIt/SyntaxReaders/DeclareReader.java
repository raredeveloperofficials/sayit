package SayIt.SyntaxReaders;
import SayIt.StringReturn;
import SayIt.StringReader;
import SayIt.IdentifierReader;
import SayIt.basic.SIObject;
import SayIt.NumberReader;
import SayIt.NumberResult;
import SayIt.basic.SIString;
import SayIt.MemberAcessResult;
import SayIt.MemberAcess;

public class DeclareReader
{
    public static DeclareResult read(char[] s, int from) {
        StringReturn name = IdentifierReader.read(s, from);
        int to = name.nextInd + 1;

        while (to < s.length && Character.isWhitespace(s[to])) {
            to++;
        }
        if(to>=s.length){
            return null;
        }
        if (to + 2 >= s.length ||
            s[to] != 'a' ||
            s[to + 1] != 's' ||
            !Character.isWhitespace(s[to + 2])) {
            return null;
        }

        to += 2;

        while (to < s.length && Character.isWhitespace(s[to])) {
            to++;
        }
        if(to>=s.length){
            return null;
        }

        StringReturn type = IdentifierReader.read(s, to);
        to = type.nextInd + 1;

        while (to < s.length && Character.isWhitespace(s[to])) {
            to++;
        }
        if(to>=s.length){
            return null;
        }
        boolean hasValue = false;
        SIObject val = null;
        if (to < s.length && s[to] == '=') {
            hasValue = true;
            to++;
            while (to < s.length && Character.isWhitespace(s[to])) {
                to++;
            }
            if(to>=s.length){
                return new DeclareResult(type.gathered,name.gathered,false,val,to-1);
            }
            ValueResult vr = ValueReader.read(s,to);
            to = vr.nextInd;
            val = vr.value;
            
            to++;//because it give last consumed character but return belive itll get next character 
        }
        
        
        return new DeclareResult(type.gathered,name.gathered,hasValue,val,to-1);
    }
}
