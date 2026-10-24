package SayIt;
import java.util.ArrayList;

public class MemberAcess {
    public static MemberAcessResult acess(char[] s, int from) {
        //from must be first letter
        ArrayList<FunctionCallReturn> ret = new ArrayList<>();
        FunctionCallReturn fr = FunctionCallReader.read(s, from);
        int to = fr.nextInd + 1;
        boolean waiting = false;
        ret.add(fr);
        while (to < s.length) {
            char c = s[to];
            if (c == '.') {
                waiting = true;
            } else if (!Character.isWhitespace(c)) {
                if (waiting) {
                    if (Character.isAlphabetic(c)) {
                        FunctionCallReturn fr2 = FunctionCallReader.read(s, to);
                        to = fr2.nextInd;
                        ret.add(fr2);
                        waiting = false;
                    }else{
                        to--; break;
                    }
                   
                } else {
                    to--;
                    break;
                }

            }
            to++;
        }
        return new MemberAcessResult(ret, to);
    }
}
