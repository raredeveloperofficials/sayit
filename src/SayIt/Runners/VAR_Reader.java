package SayIt.Runners;
import SayIt.SIRunner;
import SayIt.basic.SIBaseNumber;
import SayIt.basic.SINull;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import java.util.HashMap;
import java.util.List;

public class VAR_Reader
{
    public static int read(Compiled comp,int ind,List<String> names,List<Integer> extravals,HashMap<String,Integer> variables){
        Integer[] code = comp.code;
        if(code[ind] != SIRunner.VAR)return ind;
        ind++;
        String name = comp.pool[code[ind]];
        ind++;
        switch(code[ind]){
            case SIRunner.TYPE:
                ind++;
                String type = comp.pool[code[ind]];
                if(code[ind+1]==SIRunner.ASSIGN){
                    ind+=2;
                    if(!variables.containsKey(name)){
                    CALL_Return c = getVariable(comp,ind,names,extravals,variables);
                    variables.put(name,SIRunner.addVariable(type,c.returnval));
                    return c.next;
                    }else{
                        
                        SIRunner.PushError("cant declare variable that alredy exist("+name+")");
                        
                        return ind+1;
                    }
                }else{
                    if(!variables.containsKey(name)){
                    variables.put(name,SIRunner.addVariable(type,new SINull()));
                    return ind;
                    }
                    else{
                        SIRunner.PushError("cant declare variable that alredy exist("+name+")");
                        
                        return ind;
                    }
                }
            case SIRunner.OBJECT:
                ind++;
                int lhs = -1;
                if(names.contains(name)){
                    lhs = extravals.get(names.indexOf(name));
                }else if(variables.containsKey(name)){
                    lhs = variables.get(name);
                }
                int assop = code[ind];
                ind++;
                CALL_Return c = getVariable(comp,ind,names,extravals,variables);
                SIObject rhs = c.returnval;
                ind =c.next;
                if(assop==SIRunner.ASSIGN){
                    SIRunner.setValue(lhs,rhs);
                }else{
                    SIObject sio = SIRunner.getVariable(lhs);
                    int op = -1;
                    if(assop == SIRunner.ADD_EQUAL)op = SIRunner.ADD;
                    if(assop == SIRunner.SUB_EQUAL)op = SIRunner.SUB;
                    if(assop == SIRunner.MUL_EQUAL)op = SIRunner.MUL;
                    if(assop == SIRunner.DIV_EQUAL)op = SIRunner.DIV;
                    if(assop == SIRunner.MOD_EQUAL)op = SIRunner.MOD;
                    SIObject o =EXPRESSION_Runner.getResult(sio,op,rhs);
                    if(o!=null)SIRunner.setValue(lhs,o);else SIRunner.PushError("Hybrid Assignment operation cant happen between ("+sio.name+") and ("+rhs.name+")");     
                    
                }
        }
        return ind;
    }
    public static CALL_Return getVariable(Compiled c,int ind,List<String> names,List<Integer> extravalues,HashMap<String,Integer> variables){
        switch(c.code[ind]){
            case SIRunner.POOL:
                SIString s = new SIString();
                s.set(c.pool[c.code[ind+1]]);
                return new CALL_Return(ind+1,s);
            case SIRunner.VALUE:
                SIBaseNumber n = EXPRESSION_Runner.getNumber(c.numpool[c.code[ind+1]]);
                return new CALL_Return(ind+1,n);
            case SIRunner.VAR:
                String nme = c.pool[c.code[ind+1]];

                if (names.contains(nme)) {
                    int nmeindex = names.indexOf(nme);

                    if (nmeindex < extravalues.size()) {
                        SIObject var = SIRunner.getVariable(extravalues.get(nmeindex));

                        if (var != null) {
                            return new CALL_Return(ind + 2, var);
                        }
                    }
                } else if (variables.containsKey(nme)) {
                    SIObject var = SIRunner.getVariable(variables.get(nme));

                    if (var != null) {
                        return new CALL_Return(ind + 2, var);
                    }
                } else {
                    SIRunner.PushError(nme + " variable is not in scope.");
                }

                return null;
            case SIRunner.EXPRESSION:
                int expno = c.code[ind+1];
                return new CALL_Return(ind+1, EXPRESSION_Runner.run(c,expno,names,extravalues,variables).returnval);
                
                
            case SIRunner.CALL:
                Compiled c2 = new Compiled(c.pool,c.numpool,c.code,c.expressions,c.block);
                CALL_Return r = CALL_Runner.run(c2,ind,names,extravalues,variables);
                ind = r.next;

                return new CALL_Return(ind, r.returnval);
                
        }
        return null;
    }
}
