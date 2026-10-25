package SayIt;

import SayIt.Runners.EXPRESSION_Runner;
import SayIt.basic.SIBaseNumber;
import SayIt.basic.SIBlock;
import SayIt.basic.SIBoolean;
import SayIt.basic.SIDouble;
import SayIt.basic.SIFloat;
import SayIt.basic.SIFullExpression;
import SayIt.basic.SIFunction;
import SayIt.basic.SIInteger;
import SayIt.basic.SILong;
import SayIt.basic.SIObject;
import SayIt.basic.SIOverloadFunction;
import SayIt.basic.SIString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import SayIt.Runners.CALL_Return;
import SayIt.Runners.CALL_Runner;

public class SIBuiltins 
{
    private static final Scanner INPUT_SCANNER =
    new Scanner(System.in);
    public static void init(){
        SIFunction sf = new SIFunction();
        sf.set("string","...");
        sf.javaExecute(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    for(SIObject val:vals)System.out.println(val.get());
                    return null;
                }
            });
        SIRunner.functions.put("showln",sf);
        SIFunction s = new SIFunction();
        s.set("string","...");
        s.javaExecute(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    for(SIObject val:vals)if(val!=null)System.out.print(val.get()); else SIRunner.PushError("cant show");
                    if(vals.length==0)SIRunner.PushError("something wrong");
                    return null;
                }
            });
        SIRunner.functions.put("show",s);

        SIFunction add = new SIFunction();
        add.set("number","a","number","b");

        add.javaExecute(new SIFunction.javaFunction() {
                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if (vals.length != 2) {
                        return null;
                    }

                    if (!(vals[0] instanceof SIBaseNumber) ||
                        !(vals[1] instanceof SIBaseNumber)) {
                        return null;
                    }

                    SIBaseNumber a = (SIBaseNumber) vals[0];
                    SIBaseNumber b = (SIBaseNumber) vals[1];

                    Number x = a.get();
                    Number y = b.get();

                    // Promote to the widest input type.
                    if (x instanceof Double || y instanceof Double) {
                        SIDouble result = new SIDouble();
                        result.set(x.doubleValue() + y.doubleValue());
                        return result;
                    }

                    if (x instanceof Float || y instanceof Float) {
                        SIFloat result = new SIFloat();
                        result.set(x.floatValue() + y.floatValue());
                        return result;
                    }

                    if (x instanceof Long || y instanceof Long) {
                        SILong result = new SILong();
                        result.set(x.longValue() + y.longValue());
                        return result;
                    }

                    if (x instanceof Integer && y instanceof Integer) {
                        SIInteger result = new SIInteger();
                        result.set(x.intValue() + y.intValue());
                        return result;
                    }
                    SIRunner.PushError("cant add");
                    return null;
                }
            });

        SIRunner.functions.put("add",add);
        
        SIFunction sub = new SIFunction();
        sub.set("number","a","number","b");

        sub.javaExecute(new SIFunction.javaFunction() {

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if(vals.length == 2){
                    SIBaseNumber n = (SIBaseNumber) vals[1];
                    SIBaseNumber n2 = null;
                    if(n instanceof  SIInteger){n2 = new SIInteger();n2.set(-n.get().intValue()); }else
                    if(n instanceof  SILong){n2 = new SILong();n2.set(-n.get().longValue());} else
                    if(n instanceof  SIFloat){n2 = new SIFloat();n2.set(-n.get().floatValue());}
                    else if(n instanceof  SIDouble){n2 = new SIDouble();n2.set(-n.get().doubleValue());}
                    
                        return CALL_Runner.callFunction("add",localfun,vars,vals[0],n2);
                    }else {SIRunner.PushError("cant sub"); return null;}
                }
            });
        SIRunner.functions.put("sub",sub);
        SIFunction input = new SIFunction();
        input.set("string","a");
        input.javaExecute(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if(vals.length==1){
                        SIString s = (SIString) vals[0];
                       
                        System.out.print(s.get());
                        SIString s2 = new SIString();
                        s2.set(INPUT_SCANNER.nextLine());
                        return s2;
                    }
                    return null;
                }
            });
       SIRunner.functions.put("input",input);
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if(vals.length == 1&&vals[0]instanceof SIString){
                        IntegerReturn ir = IntegerReader.read(((SIString)vals[0]).get().toCharArray(),0);
                        SIInteger i = new SIInteger();
                        i.set(ir.gathered);
                        return i;
                    }
                    return null;
                }
            },"toInt","string","s");
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if(vals.length == 1&&vals[0]instanceof SIString){
                        FloatReturn ir = FloatReader.read(((SIString)vals[0]).get().toCharArray(),0);
                        SIFloat i = new SIFloat();
                        i.set(ir.gathered);
                        return i;
                    }
                    return null;
                }
            },"toFloat","string","s");
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if(vals.length == 1&&vals[0]instanceof SIString){
                        LongReturn ir = LongReader.read(((SIString)vals[0]).get().toCharArray(),0);
                        SILong i = new SILong();
                        i.set(ir.gathered);
                        return i;
                    }
                    return null;
                }
            },"toLong","string","s");
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String,Integer> vars, SIObject[] vals) {
                    if(vals.length == 1&&vals[0]instanceof SIString){
                        DoubleReturn ir = DoubleReader.read(((SIString)vals[0]).get().toCharArray(),0);
                        SIDouble i = new SIDouble();
                        i.set(ir.gathered);
                        return i;
                    }
                    return null;
                }
            },"toDouble","string","s");
        addFunction(new SIFunction.javaFunction() {

                @Override
                public SIObject run(
                    HashMap<String,SIOverloadFunction> localfun,
                    HashMap<String, Integer> vars,
                    SIObject[] vals
                ) {
                    int i = 0;

                    while (i + 1 < vals.length
                           && (vals[i] instanceof SIBaseNumber||vals[i] instanceof SIBoolean)
                           && vals[i + 1] instanceof SIBlock) {

                        
                        SIBlock b = (SIBlock) vals[i + 1];

                        if ((vals[i]instanceof SIBaseNumber && ((SIBaseNumber)vals[i]).get().doubleValue()>0)||(vals[i] instanceof SIBoolean&&((SIBoolean)vals[i]).get()>0)) {
                            SIRunner.run(localfun,
                                b.get(),new String[0],new SIObject[0],
                                vars.keySet().toArray(new String[0]),
                                vars.values().toArray(new Integer[0])
                            );

                            return null;
                        }

                        i += 2;
                    }

                    
                    if (i < vals.length && vals[i] instanceof SIBlock) {
                        SIBlock b = (SIBlock) vals[i];

                        SIRunner.run(localfun,
                            b.get(),new String[0],new SIObject[0],
                            vars.keySet().toArray(new String[0]),
                            vars.values().toArray(new Integer[0])
                        );
                    }

                    return null;
                }

            }, "if", "any", "...");
        final List<String> s1 =Arrays.asList(new String[0]);
        final List<Integer> i1 =Arrays.asList(new Integer[0]);
        addFunction(new SIFunction.javaFunction() {
                @Override
                public SIObject run(
                    HashMap<String,SIOverloadFunction> localfun,
                    HashMap<String, Integer> vars,
                    SIObject[] vals
                ) {
                    if (vals.length < 2
                        || !(vals[0] instanceof SIFullExpression)
                        || !(vals[1] instanceof SIBlock)) {
                        return null;
                    }

                    SIFullExpression condition = (SIFullExpression) vals[0];
                    SIBlock block = (SIBlock) vals[1];
                    
                    while ((((SIBaseNumber)EXPRESSION_Runner.run(condition.get(),condition.expno,s1,i1,vars,localfun).returnval).get().doubleValue() > 0)||(((SIBoolean)EXPRESSION_Runner.run(condition.get(),condition.expno,s1,i1,vars,localfun).returnval).get() > 0)) {
                        
                        SIRunner.run(localfun,
                            block.get(),new String[0],new SIObject[0],
                            vars.keySet().toArray(new String[0]),
                            vars.values().toArray(new Integer[0])
                        );

                       
                    }

                    return null;
                }
            }, "while", "any", "...");
        addFunction(new SIFunction.javaFunction() {
                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun,HashMap<String, Integer> vars, SIObject[] vals) {
                    if (vals.length != 3
                        || !(vals[0] instanceof SIObject)
                        || !(vals[1] instanceof SIInteger)
                        || !(vals[2] instanceof SIBlock)) {
                        return null;
                    }

                    SIObject vname = vals[0];
                    SIInteger inte = (SIInteger) vals[1];
                    SIBlock block = (SIBlock) vals[2];

                    SIInteger inc = new SIInteger();
                    inc.set(0);

                    int ind = SIRunner.addVariable("int", inc);

                    try {
                        for (int i = 0; i < inte.get(); i++) {
                            inc.set(i);

                            HashMap<String, Integer> currentVars =
                                new HashMap<>(vars);

                            currentVars.put(vname.getString(), ind);

                            String[] currentK =
                                currentVars.keySet().toArray(new String[0]);

                            Integer[] currentV =
                                currentVars.values().toArray(new Integer[0]);

                            SIRunner.run(localfun,block.get(),new String[0],new SIObject[0], currentK, currentV);
                        }
                    } finally {
                        SIRunner.removeVariable(ind);
                    }

                    return null;
                }
            }, "for", "string", "n", "int", "i", "block", "b");
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun, HashMap<String, Integer> vars, SIObject[] vals) {
                    if(vals.length>=3&&vals[0] instanceof SIBlock&&vals[1]instanceof SIString&&vals[2]instanceof SIString){
                        SIBlock b = (SIBlock) vals[0];
                        SIString rt = (SIString) vals[2];
                        SIString nme = (SIString) vals[1];
                       
                        ArrayList<String> s = new ArrayList<>();
                        int i = 3;
                        
                        while(vals.length>i&&vals[i] instanceof SIString){
                            s.add(vals[i].getString());
                            i++;
                        }
                        SIFunction fun = new SIFunction();
                        fun.set(s.toArray(new String[0]));
                        fun.c = b.get();
                        fun.rtrn = rt.get();
                        fun.name = nme.get();
                        //SIRunner.functions.put(fun.name,fun);
                        SIRunner.addFunction(localfun,fun);
                    }
                    return null;
                }
                
                
            },"createFunction","fn","...");
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun, HashMap<String, Integer> vars, SIObject[] vals) {
                    if(vals.length == 1){
                        SIString s = new SIString();
                        s.set(vals[0].name);
                        return s;
                        
                       
                    }
                    return null;
                }
            }, "getType", "any", "a");
        addFunction(new SIFunction.javaFunction(){

                @Override
                public SIObject run(HashMap<String,SIOverloadFunction> localfun, HashMap<String, Integer> vars, SIObject[] vals) {
                    SIBoolean b = new SIBoolean();
                    b.set(0);
                    if(vals.length == 1){
                        if(vals[0] instanceof SIBaseNumber){
                            b.set(((SIBaseNumber)vals[0]).get().intValue());
                        }else if(vals[0] instanceof SIString){
                            b.set(((SIString)vals[0]).get().isEmpty()?0:1);
                        }
                    }
                    return b;
                }
            }, "bool", "boolean", "...");
    }

    
    public static void addFunction(SIFunction.javaFunction fndo,String name,Object... params){
        SIFunction fn = new SIFunction();
        fn.set(params);
        fn.javaExecute(fndo);
        SIRunner.functions.put(name,fn);
    }
}
