package SayIt.Runners;
import SayIt.SIRunner;
import SayIt.basic.SIBaseNumber;
import SayIt.basic.SIBlock;
import SayIt.basic.SIObject;
import SayIt.basic.SIOverloadFunction;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import java.util.HashMap;
import java.util.List;

public class RunnerFunctions
{
    public static SIString getPool(Compiled c,int ind){
        SIString s = new SIString();
        s.set(c.pool[c.code[ind]]);
        return s;
    }
    public static SIBlock getBlock(Compiled c,int ind){
        int blockno = c.code[ind];
        SIBlock b = new SIBlock();
        
        b.set(c.block[blockno]);
        return b;
    }
    public static SIBaseNumber getValue(Compiled c,int ind){
        SIBaseNumber n;
        Number num = c.numpool[c.code[ind]];
        n = EXPRESSION_Runner. getNumber(num);
        return n;
    }
    public static SIObject getExpressionResult(Compiled c,int ind,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables,HashMap<String,SIOverloadFunction> localfun){
        SIObject o = EXPRESSION_Runner.run(c,c.code[ind],names,extravalues,variables,localfun).returnval;
        return o;
    }
    public static CALL_Return getFunctionCall(Compiled c,int ind,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables,HashMap<String,SIOverloadFunction> localfun){
        Compiled c2 = new Compiled(c.pool,c.numpool,c.code,c.expressions,c.block);
        CALL_Return r = CALL_Runner.run(c2,ind,names,extravalues,variables,localfun);
        return new CALL_Return(r.next,r.returnval);
    }
    public static CALL_Return getVariable(Compiled c,int ind,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables){
        String nme = c.pool[c.code[ind]];
        SIObject var = null;

        int nmeindex = names.indexOf(nme);

        if (nmeindex >= 0 && nmeindex < extravalues.size()) {
            var = SIRunner.getVariable(extravalues.get(nmeindex));
        }

        // Fall back to the current variable map if lookup failed.
        if (var == null && variables.containsKey(nme)) {
            var = SIRunner.getVariable(variables.get(nme));
        }

        if (var == null) {
            SIRunner.PushError(nme + " variable is not in scope.");
        }
   
        ind++;
        return new CALL_Return(ind,var);
    }
    public static CALL_Return calcValue(Compiled c,int ind,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables,HashMap<String,SIOverloadFunction> localfun){
        SIObject val = null;
        switch(c.code[ind]){
            case SIRunner.VALUE:
                ind++;
                val = getValue(c,ind);
                break;
                
            case SIRunner.POOL:
                ind++;
                val = getPool(c,ind);
                break;
            
            case SIRunner.VAR:
                ind++;
                CALL_Return cr = getVariable(c,ind,names,extravalues,variables);
                ind++;
                
                
                val = cr.returnval;
                break;
            case SIRunner.EXPRESSION:
                ind++;
                val = getExpressionResult(c,ind,names,extravalues,variables,localfun);
                break;
            case SIRunner.CALL:
                
                CALL_Return cal= getFunctionCall(c,ind,names,extravalues,variables,localfun);
                ind = cal.next;
                val = cal.returnval;
                break;
            case SIRunner.BLOCK:
                val = getBlock(c,ind);
                break;
        }
        return new CALL_Return(ind,val);
    }
    public static CALL_Return calcExpressionVal(Compiled c,int exp_no,int ind,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables,HashMap<String,SIOverloadFunction> localfun){
        Compiled c2 = new Compiled(c.pool,c.numpool,c.expressions[exp_no],c.expressions,c.block);
        return calcValue(c2,ind,names,extravalues,variables,localfun);
    }
}
