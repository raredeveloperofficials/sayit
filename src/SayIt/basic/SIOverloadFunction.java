package SayIt.basic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import SayIt.SIRunner;

public class SIOverloadFunction 
{
    private ArrayList<SIFunction> fun = new ArrayList<>();
    public SIObject call(String classname,boolean isstatic,HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars,SIObject... vals){
        SIObject val = null;
        boolean b = false;
        String name = "";
        for(SIFunction func: fun){
            if(func.get()==vals.length){
                name = func.name;
                b = true;
                val = func.call(classname,isstatic,localfun,vars,vals);
            }
        }
        if(!b)SIRunner.PushError("No overloads that support "+vals.length+" parameter."+((name.isEmpty())?"":"Function Name \""+name+"\""));
        return val;
    }
    public void add(SIFunction... val) {
       Collections.addAll(fun,val);
    }
    
}
