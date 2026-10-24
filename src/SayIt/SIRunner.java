package SayIt;
import SayIt.Runners.CALL_Return;
import SayIt.Runners.CALL_Runner;
import SayIt.Runners.VAR_Reader;
import SayIt.basic.SIFunction;
import SayIt.basic.SIObject;
import SayIt.main.Compiled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import SayIt.basic.SINull;

public class SIRunner
{
    public static final int DECLARE = 0,TYPE = 1,ASSIGN = 2,VAR = 3,EXPRESSION = 4,VALUE=5,POOL=6,CALL = 7
    ,BYCALL=8,OBJECT = 9 ,ADDRESS = 10,ARGS_START = 11,ARGS_END = 12,ADD = 13,SUB=14,MUL = 15,DIV=16,MOD = 17,
    GREATER=18,LESS=19,GREATER_OR_EQUAL=20,LESS_OR_EQUAL=21,EQUAL=22,ADD_EQUAL = 23,SUB_EQUAL=24,MUL_EQUAL=25,DIV_EQUAL=26,MOD_EQUAL=27,
    NOT_EQUAL=28,BLOCK=29;
    public static HashMap<String,SIFunction> functions = new HashMap<>();
    private static HashMap<Integer,Variable> variable_storage = new HashMap<>();
    private static Stack<Integer> variable_removed_index = new Stack<>();
    private static int count = 0;
   static class Variable{
        public SIObject val;
        public String type;
        public Variable(SIObject val,
       String type){
           this.val=val;
           this .type = type;
       }
       
    }
    public static int addVariable(String type,SIObject obj){
        if(variable_removed_index.empty()){
           
            variable_storage.put(count,new Variable(obj,type));
            return count++ ;
        }
        int i = variable_removed_index.pop();
        variable_storage.put(i,new Variable(obj,type));
        return i;
    }
    public static boolean removeVariable(int i) {
        Variable removed = variable_storage.remove(i);

        if (removed == null) {
            return false;
        }

        variable_removed_index.push(i);
        return true;
    }
    
    public static boolean hasVariable(int i){
        return variable_storage.getOrDefault(i,null)!=null;
    }
    public static int addressOf(SIObject obj) {
        for (Map.Entry<Integer, Variable> entry : variable_storage.entrySet()) {
            if (entry.getValue().val == obj) {//System.out.println("hahaha");
                return entry.getKey();
            }
        }
        return -1; // Not found
    }
    public static SIObject getVariable(int i){
        return (variable_storage.getOrDefault(i,null)==null)?null:variable_storage.get(i).val;
    }
    public static boolean setValue(int i,SIObject val){
        Variable v = variable_storage.getOrDefault(i,null);
        if(v!=null){
            if(v.type.equals(val.name)&&!v.val.name.equals("null")){
                v.val.set(val.get());//System.out.println("hehehehe");
                variable_storage.put(i,v);
            }else if(v.val instanceof SINull){
                v.val = val;
                variable_storage.put(i,v);
            }else PushError("can't assign type ("+val.name+") to ("+v.type+")");
        }return false;
        
    }
    public static void PushError(String Runtime_Error){
        System.out.println("\n=========Say It Runtime Error=========\n"+Runtime_Error+"\n==================================\n");
    }
    
    
    
    public static SIObject run(Compiled compiled,String[] namet,SIObject[] myvals,String[] names,Integer... extravalues){
        int i = 0;
        List<String> name = new ArrayList<String>(Arrays.asList(names));

       
        List<Integer> values = new ArrayList<Integer>(Arrays.asList(extravalues));
        for(int in = 0; in<namet.length;in++){
           // System.out.println("heheheh");
            name.add(namet[in]);
            values.add(SIRunner.addressOf(myvals[in]));
        }
        
        HashMap<String,Integer> variables = new HashMap<>();
        Integer[] code = compiled.code;
        SIObject rtrn = null;
        while(i<compiled.code.length){
            if(code[i]==CALL){
                
                CALL_Return cr = CALL_Runner.run(compiled,i,name,values,variables);
                rtrn = cr.returnval;
                
                i = cr.next+1;
            }else
            if(code[i] == VAR){
                i = VAR_Reader.read(compiled,i,name,values,variables)+1;
            }
        }
        return rtrn;
    }
    
}
