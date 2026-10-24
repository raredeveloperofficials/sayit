package SayIt.SyntaxReaders;

import SayIt.IdentifierReader;
import SayIt.StringReturn;
import SayIt.basic.SIObject;
import SayIt.SIRunner;
import SayIt.MemberAcessResult;
import SayIt.MemberAcess;

public class AssignmentReader {
    public static AssignmentResult read(char[] s, int from) {
        //StringReturn name = IdentifierReader.read(s, from);
        MemberAcessResult name = MemberAcess.acess(s,from);
        int to = name.nextInd + 1;
        if(name.result[name.result.length-1].isFunction)return null;
        while (to < s.length && Character.isWhitespace(s[to])) {
            to++;
        }
        if (to >= s.length) {
            return null;
        }
        SIObject val = null;
        int assignment_op = SIRunner.ASSIGN;
        if (to < s.length) {
            
            if(to+1<s.length&&s[to+1]=='='){
                
                switch(s[to]){
                    case '+':
                        //System.out.println(s[to]+"----------------------");
                        assignment_op = SIRunner.ADD_EQUAL;
                        to+=2;
                        break;
                    case '-':
                        assignment_op = SIRunner.SUB_EQUAL;
                        to+=2;
                        break;
                    case '*':
                        assignment_op = SIRunner.MUL_EQUAL;
                        to+=2;
                        break;
                    case '/':
                        assignment_op = SIRunner.DIV_EQUAL;
                        to+=2;
                        break;
                    case '%':
                        assignment_op = SIRunner.MOD_EQUAL;
                        to+=2;
                        break;
                    
                    
                }
                
            }else if(s[to]!='='){
                return null;
            }else to++;
        }
        while (to < s.length && Character.isWhitespace(s[to])) {
            to++;
        }
        if(to>=s.length){
            return null;
        }
        //System.out.println("hello"+s[to]);
        ValueResult vr = ValueReader.read(s,to);
        to = vr.nextInd;
        val = vr.value;
        return new AssignmentResult(name,val,assignment_op,to);
    }
}
