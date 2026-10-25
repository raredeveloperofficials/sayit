package SayIt.basic;
import java.util.HashMap;
import SayIt.SIRunner;
import SayIt.main.Compiled;
import java.util.Arrays;

public class SIFunction extends SIObject {
    private HashMap<String,String>  args = new HashMap<>();
    private javaFunction java;
    private boolean infinite;
    public String name="",rtrn="";
    public Compiled c;
    @Override
    public Integer get() {
        return args.size();
    }

    @Override
    public void set(Object... val) {
        //setting arg name and type
        if (val.length % 2 != 0) {
            throw new IllegalArgumentException(
                "Expected pairs of argument name and type,but got\n"+Arrays.toString(val)
            );
        }
        if (val.length == 2  && val[1]instanceof String && ((String)val[1]).equals("...")) {
            infinite = true;
        } else {
            infinite = false;
        }
        args.clear();
        for (int i = 0; i < (val.length / 2);i++) {
            if (!(val[i * 2]instanceof String) || !(val[(i * 2) + 1]instanceof String))throw new IllegalArgumentException("set of function must give arguments String(name),String(type),...");
            args.put((String)val[i * 2+1], (String)val[(i * 2)]);
        }
    }
    public SIObject call(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars,SIObject... vals) {
        if (!infinite) {
            if (args.size() != vals.length)throw new IllegalArgumentException("function arguments doesn't match the expectation");
        }
        if (java == null)
            return SIRunner.run(localfun,c,args.keySet().toArray(new String[0]),vals, vars.keySet().toArray(new String[0]),vars.values().toArray(new Integer[0]));
        else
            return java.run(localfun,vars,vals);
    }
    public void javaExecute(javaFunction java) {
        this.java = java;
    }
    public static interface javaFunction {
        public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars,SIObject... vals);
    }
}
