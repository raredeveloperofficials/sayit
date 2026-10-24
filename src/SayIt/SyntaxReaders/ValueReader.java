package SayIt.SyntaxReaders;

import SayIt.BetweenContent;
import SayIt.BetweenContentResult;
import SayIt.MemberAcess;
import SayIt.MemberAcessResult;
import SayIt.NumberReader;
import SayIt.NumberResult;
import SayIt.SIRunner;
import SayIt.StringReader;
import SayIt.StringReturn;
import SayIt.basic.SIExpression;
import SayIt.basic.SIMemberAcess;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import SayIt.main.Compiler;
import SayIt.basic.SIBlock;
public class ValueReader 
{
    public static ValueResult read(char[] s,int to ){
        //first letter of read value is to
        char c = s[to];
        SIObject val = null;
        if(Character.isDigit(c)||c=='-'){
            NumberResult nr = NumberReader.read(s,to);
            to = nr.nextInd;
            val = nr.gathered;
        }else
        if(c == '"'){
            StringReturn sr = StringReader.read(s,to);
            to = sr.nextInd;
            SIString str = new SIString();
            str.set(sr.gathered);
            val = str;
        }else
        if(Character.isAlphabetic(c)){
            MemberAcessResult mar = MemberAcess.acess(s,to);
            to = mar.nextInd;
            SIMemberAcess memacess = new SIMemberAcess();
            memacess.set(mar);
            val = memacess;
            
        }else if(c == '('){
            BetweenContentResult bcr = BetweenContent.parentheses(s,to);
            ExpressionResult er = ExpressionReader.read(bcr.content.toCharArray(),0);
            SIExpression expo = new SIExpression();
            expo.set(er);
            to = bcr.nextInd;
            val = expo;
        }else if(c =='{'){
            
            BetweenContentResult res = BetweenContent.get("{","}",s,to);
            Compiled compile = Compiler.compile(res.content.toCharArray());
           SIBlock b = new SIBlock();
            b.set(compile);
            to = res.nextInd;
            val = b;
        }
        return new ValueResult(val,to);
    }
}
