package SayIt.SyntaxReaders;
import java.util.ArrayList;
import SayIt.main.Compiled;

public class ArgsValueReader
{
    public static ArgsValueResult read(char[] s,int from){
        //from is first letter after (
        int to = from;
        ArrayList<ValueResult> vals = new ArrayList< >();
        while(to < s.length){
            ValueResult v = ValueReader.read(s,to);
            vals.add(v);
            to = v.nextInd+1;
            while(to<s.length&&Character.isWhitespace(s[to])){
                to++;
            }
        }
        to--;
        return new ArgsValueResult(vals.toArray(new ValueResult[0]),to);
    }
}
