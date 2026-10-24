package SayIt.Runners;
import SayIt.SIRunner;
import SayIt.basic.SIDouble;
import SayIt.basic.SIFloat;
import SayIt.basic.SIInteger;
import SayIt.basic.SILong;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import SayIt.basic.SIBlock;
import SayIt.basic.SIExpression;
import SayIt.basic.SIFullExpression;
import SayIt.basic.SIFunction;

public class CALL_Runner
{
    public static CALL_Return run(Compiled compiled,int i,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables){
        Integer[] compcode = compiled.code;
        
        SIObject rtrn = null;
        for(int in = 0;in<names.size();in++){
           variables.put(names.get(in),extravalues.get(in));
        }
        if(i+3<compcode.length&&compcode[i+1]==SIRunner.VAR&&compcode[i+3]==SIRunner.ARGS_START){
            i+=2;
            String name = compiled.pool[compcode[i]];
            
            i+=2;
            ArrayList<SIObject> vals = new ArrayList<>();
            int val = -1;
            
            while(i<compcode.length&&(compcode[i]!=SIRunner.ARGS_END||val!=-1)){
                if(val==-1){
                    switch(compcode[i]){
                        case SIRunner.POOL:
                        case SIRunner.VALUE:
                        case SIRunner.VAR:
                        case SIRunner.CALL:
                        case SIRunner.EXPRESSION:
                        case SIRunner.BLOCK:
                            val = compcode[i];
                            break;
                    }
                }else{
                    
                    switch(val){
                        
                        case SIRunner.POOL:
                            SIString s = new SIString();
                            s.set(compiled.pool[compcode[i]]);
                            //System.out.println(s.get());
                            vals.add(s);
                            break;
                        case SIRunner.VALUE:
                            Number num = compiled.numpool[compcode[i]];
                            if(num instanceof Integer){
                                SIInteger n = new SIInteger();
                                n.set(num);
                                vals.add(n);
                            }else if(num instanceof Long){
                                SILong n = new SILong();
                                n.set(num);
                                vals.add(n);
                            }else if(num instanceof Float){
                                SIFloat n = new SIFloat();
                                n.set(num);
                                vals.add(n);
                            }else{
                                SIDouble n = new SIDouble();
                                n.set(num);
                                vals.add(n);
                            }
                            
                            break;
                        case SIRunner.VAR:
                            String nme = compiled.pool[compcode[i]];
                            
                            if (names.contains(nme)) {
                                int nmeindex = names.indexOf(nme);

                                if (nmeindex < extravalues.size()) {
                                    SIObject var = SIRunner.getVariable(extravalues.get(nmeindex));

                                    if (var != null) {
                                        vals.add(var);
                                    } else {
                                        SIRunner.PushError(nme + " variable storage is missing.");
                                    }
                                }
                            } else if (variables.containsKey(nme)) {
                                SIObject var = SIRunner.getVariable(variables.get(nme));

                                if (var != null) {
                                    vals.add(var);
                                } else {
                                    SIRunner.PushError(nme + " variable storage is missing.");
                                }
                            } else {
                                SIRunner.PushError(nme + " variable is not in scope.");
                            }
                        break;
                        case SIRunner.CALL:
                            
                            CALL_Return rt =run(compiled,i-1,names,extravalues,variables);
                            
                            i = rt.next;
                            //if(rt.returnval!= null)System.out.println(rt.returnval.get());
                            vals.add(rt.returnval);
                            break;
                        case SIRunner.EXPRESSION:
                            //System.out.println("Evaluating expression index: " + compcode[i]);
                            //System.out.println(Arrays.toString(compiled.expressions[compcode[i]]));
                           // System.out.println(Arrays.toString(compiled.expressions[compcode[i]]));
                            EXPRESSION_Result exres = EXPRESSION_Runner.run(compiled,compcode[i],names,extravalues,variables);
                            if(name.equals("while")){
                                SIFullExpression expno = new SIFullExpression();
                                expno.set(compiled);
                                expno.expno = compcode[i];
                                vals.add(expno);
                            }else
                            vals.add(exres.returnval);
                            
                            break;
                        case SIRunner.BLOCK:
                            int blockno = compcode[i];
                            SIBlock b = new SIBlock();
                            b.set(compiled.block[blockno]);
                            vals.add(b);
                            break;
                    }
                    val = -1;
                }
                i++;
                
            }
            for(SIObject o:vals){
                SIRunner.addVariable(o.name,o);
            }
            rtrn = SIRunner.functions.get(name).call(new HashMap<String,Integer>(variables),vals.toArray(new SIObject[0]));
            
        }
        //if(rtrn !=null)//System.out.println(rtrn.get());
        return new CALL_Return(i,rtrn);
    }
}
