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
                /*
                 CALL_Return cr = RunnerFunctions.calcValue(compiled,i,names,extravalues,variables);
                 i =cr.next;
                 if(cr.returnval!=null)vals.add(cr.returnval);
                */
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
                            
                            vals.add(RunnerFunctions.getPool(compiled,i));
                            break;
                        case SIRunner.VALUE:
                            
                            vals.add(RunnerFunctions.getValue(compiled,i));
                            break;
                        case SIRunner.VAR:
                            CALL_Return cr = RunnerFunctions.getVariable(compiled,i,names,extravalues,variables);
                            
                            vals.add(cr.returnval);
                            i = cr.next;
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
                            
                            vals.add(RunnerFunctions.getBlock(compiled,i));
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
