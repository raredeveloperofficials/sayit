package SayIt.SyntaxReaders;
import java.util.ArrayList;
import SayIt.basic.SIObject;
import SayIt.SIRunner;
import java.util.IllegalFormatException;
import java.util.Arrays;

public class ExpressionReader
{
    public static ExpressionResult read(char[] s,int from){
        //first letter inside that expression
        ArrayList<SIObject> vals = new ArrayList<>();
        ArrayList<Integer> operation = new ArrayList<>();
        int to = from;
        boolean op = false;
        while(to<s.length){
            char c = s[to];
            if(!Character.isWhitespace(c)){
                if(op){
                    if(c == '+'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.ADD_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.ADD);
                        }
                    }else
                    if(c == '-'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.SUB_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.SUB);
                        }
                    }else
                    if(c == '*'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.MUL_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.MUL);
                        }
                    }else
                    if(c == '/'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.DIV_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.DIV);
                        }
                    }else
                    if(c == '%'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.MOD_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.MOD);
                        }
                    }else
                    if(c == '='){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.EQUAL);
                            to++;
                        }else{
                            throw new IllegalStateException("can't assign inside expression");
                        }
                    }else
                    if(c == '!'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.NOT_EQUAL);
                            to++;
                        }else{
                            throw new IllegalStateException("can't assign inside expression");
                        }
                    }else
                    if(c=='>'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.GREATER_OR_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.GREATER);
                        }
                    }else
                    if(c=='<'){
                        if(to+1<s.length&&s[to+1] =='='){
                            operation.add(SIRunner.LESS_OR_EQUAL);
                            to++;
                        }else{
                            operation.add(SIRunner.LESS);
                        }
                    }else{
                        throw new IllegalStateException("can't identify use of "+c+",no operations defined to work with it");
                    }
                }else{
                    if(Character.isAlphabetic(c)||Character.isDigit(c)||c=='"'||c=='('||(c=='-'&&to==0)){
                        ValueResult val = ValueReader.read(s,to);
                        to = val.nextInd;
                        vals.add(val.value);
                    }else{
                        throw new IllegalStateException("its illegal to place "+c+" here expression or value may come");
                    }
                    
                }
                op = !op;
            }
            to++;
        }
        return new ExpressionResult(vals.toArray(new SIObject[0]),operation.toArray(new Integer[0]),to);
    }
}
