package SayIt.Runners;
import SayIt.SIRunner;
import SayIt.basic.SIBaseNumber;
import SayIt.basic.SIDouble;
import SayIt.basic.SIFloat;
import SayIt.basic.SIInteger;
import SayIt.basic.SILong;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import java.util.HashMap;
import java.util.List;

public class EXPRESSION_Runner
{
    public static EXPRESSION_Result run(Compiled compiled,int expression_no,List<String> names, List<Integer> extravalues,HashMap<String,Integer> variables){
        Integer[] expression = compiled.expressions[expression_no];
        
        int ind = 0;
        SIObject first = null;
        int op = -1;
        
        while(ind<expression.length){
            if(first!=null){
                //System.out.println("ind=" + ind + ", token=" + expression[ind] + ", first=" + (first == null ? "null" : first.getString()) + ", op=" + op);
                if(op == -1){
                    op = expression[ind];
                }else{
                    SIObject now = null;
                    switch(expression[ind]){
                        case SIRunner.VALUE:
                            Number num = compiled.numpool[expression[ind+1]];
                            now = getNumber(num);
                            ind++;
                            break;
                        case SIRunner.POOL:
                            String pool = compiled.pool[expression[ind+1]];
                            SIString s = new SIString();
                            s.set(pool);
                            now = s;
                            ind++;
                            break;
                        case SIRunner.VAR: {
                                String nme = compiled.pool[expression[ind + 1]];
                                SIObject var = null;

                                int nmeindex = names.indexOf(nme);

                                if (nmeindex >= 0 && nmeindex < extravalues.size()) {
                                    var = SIRunner.getVariable(extravalues.get(nmeindex));
                                }

                                // Fall back to the current variable map if lookup failed.
                                if (var == null && variables.containsKey(nme)) {
                                    var = SIRunner.getVariable(variables.get(nme));
                                }

                                if (var != null) {
                                    now = var;
                                } else {
                                    SIRunner.PushError(nme + " variable is not in scope.");
                                }

                                // Skip VAR's pool index and OBJECT token.
                                ind++;
                                ind++;
                                break;
                            }
                        case SIRunner.EXPRESSION:
                            int expno = expression[ind+1];
                            now = run(compiled,expno,names,extravalues,variables).returnval;
                            ind++;
                            break;
                        case SIRunner.CALL:
                            Compiled c2 = new Compiled(compiled.pool,compiled.numpool,expression,compiled.expressions,compiled.block);
                            CALL_Return r = CALL_Runner.run(c2,ind,names,extravalues,variables);
                            ind = r.next;
                            
                            now = r.returnval;
                            break;
                    }
                    /*System.out.println(
                        "BEFORE RESULT: first=" + first +
                        ", op=" + op +
                        ", now=" + now
                    );*/
                    if(now!=null){
                        //System.out.println("Comparing: " + first.getString() + " op=" + op + " now=" + now.getString());
                    first = getResult(first,op,now);
                    }
                    
                    op = -1;
                }
            }else{
                switch(expression[ind]){
                    case SIRunner.VALUE:
                        Number num = compiled.numpool[expression[ind+1]];
                        first = getNumber(num);
                        ind++;
                        break;
                    case SIRunner.POOL:
                        String pool = compiled.pool[expression[ind+1]];
                        SIString s = new SIString();
                        s.set(pool);
                        first = s;
                        ind++;
                        break;
                   case SIRunner.VAR:
                        String nme = compiled.pool[expression[ind+1]];

                        int nmeindex = names.indexOf(nme);
                        if(nmeindex >= 0 && nmeindex < extravalues.size()){
                            SIObject var = SIRunner.getVariable(extravalues.get(nmeindex));
                            if(var != null) first = var;
                            ind++;
                        }else if(variables.containsKey(nme)){
                            SIObject var = SIRunner.getVariable(variables.get(nme));
                            if(var!=null)first =var;
                            ind++;
                        }else SIRunner.PushError(nme+" variable is not in scope.");
                        ind++;
                        break;
                   case SIRunner.EXPRESSION:
                       int expno = expression[ind+1];
                       first = run(compiled,expno,names,extravalues,variables).returnval;
                       ind++;
                       break;
                   case SIRunner.CALL:
                       Compiled c2 = new Compiled(compiled.pool,compiled.numpool,expression,compiled.expressions,compiled.block);
                       CALL_Return r = CALL_Runner.run(c2,ind,names,extravalues,variables);
                       ind = r.next;
                       first = r.returnval;
                       break;
                }
            }
            ind++;
        }
        return new EXPRESSION_Result(first);
    }  
    public static SIObject getResult(SIObject o1,int op,SIObject o2){
        
        if(o1 instanceof SIString||o2 instanceof SIString){
            if(o1 instanceof SIString&&o2 instanceof SIString){
                switch(op){
                    case SIRunner.EQUAL:
                        SIInteger i = new SIInteger();
                        i.set(((SIString)o1).get().equals(((SIString)o2).get())?1:0);
                        return i;
                        
                    case SIRunner.NOT_EQUAL:
                        SIInteger i2 = new SIInteger();
                        i2.set(((SIString)o1).get().equals(((SIString)o2).get())?0:1);
                        return i2;
                    case SIRunner.ADD:
                        SIString con = new SIString();
                        con.set(((SIString)o1).get()+((SIString)o2).get());
                        return con;
                }
            }else if(o1 instanceof SIString){
                switch(op){
                    case SIRunner.EQUAL:
                        SIInteger i = new SIInteger();
                        i.set(((SIString)o1).get().equals(o2.getString())?1:0);
                        return i;

                    case SIRunner.NOT_EQUAL:
                        SIInteger i2 = new SIInteger();
                        i2.set(((SIString)o1).get().equals(o2.getString())?0:1);
                        return i2;
                    case SIRunner.ADD:
                        SIString con = new SIString();
                        con.set(((SIString)o1).get()+(o2.getString()));
                        return con;
                }
            }else if(o2 instanceof SIString){
                switch(op){
                    case SIRunner.EQUAL:
                        SIInteger i = new SIInteger();
                        i.set(o1.getString().equals(((SIString)o2).get())?1:0);
                        return i;

                    case SIRunner.NOT_EQUAL:
                        SIInteger i2 = new SIInteger();
                        i2.set(o1.getString().equals(((SIString)o2).get())?0:1);
                        return i2;
                    case SIRunner.ADD:
                        SIString con = new SIString();
                        con.set(o1.getString()+((SIString)o2).get());
                        return con;
                }
            }
        }else if(o1 instanceof SIBaseNumber&&o2 instanceof SIBaseNumber){
            SIBaseNumber b1 = (SIBaseNumber) o1;
            SIBaseNumber b2 = (SIBaseNumber) o2;

            Number n1 = b1.get();
            Number n2 = b2.get();

            int type = 0; // 0 = int, 1 = long, 2 = float, 3 = double

            if (b1 instanceof SIDouble || b2 instanceof SIDouble) {
                type = 3;
            } else if (b1 instanceof SIFloat || b2 instanceof SIFloat) {
                type = 2;
            } else if (b1 instanceof SILong || b2 instanceof SILong) {
                type = 1;
            }

            SIBaseNumber result;
            //System.out.println("getResult: " + n1 + " op=" + op + " " + n2);
            switch (type) {
                case 3:
                    result = new SIDouble();
                    break;
                case 2:
                    result = new SIFloat();
                    break;
                case 1:
                    result = new SILong();
                    break;
                default:
                    result = new SIInteger();
                    break;
            }

            switch (op) {
                case SIRunner.ADD:
                    switch (type) {
                        case 3: result.set(n1.doubleValue() + n2.doubleValue()); return result;
                        case 2: result.set(n1.floatValue() + n2.floatValue());return result;
                        case 1: result.set(n1.longValue() + n2.longValue());return result;
                        default: result.set(n1.intValue() + n2.intValue());return result;
                    }

                case SIRunner.SUB:
                    switch (type) {
                        case 3:result.set(n1.doubleValue() - n2.doubleValue());return result;
                        case 2:  result.set(n1.floatValue() - n2.floatValue());return result;
                        case 1: result.set(n1.longValue() - n2.longValue());return result;
                        default: result.set(n1.intValue() - n2.intValue());return result;
                    }

                case SIRunner.MUL:
                    switch (type) {
                        case 3: result.set(n1.doubleValue() * n2.doubleValue());return result;
                        case 2: result.set(n1.floatValue() * n2.floatValue());return result;
                        case 1: result.set(n1.longValue() * n2.longValue());return result;
                        default:result.set(n1.intValue() * n2.intValue());return result;
                    }

                case SIRunner.DIV:
                    switch (type) {
                        case 3: result.set(n1.doubleValue() / n2.doubleValue());return result;
                        case 2: result.set(n1.floatValue() / n2.floatValue());return result;
                        case 1: result.set(n1.longValue() / n2.longValue());return result;
                        default: result.set(n1.intValue() / n2.intValue());return result;
                    }

                case SIRunner.MOD:
                    switch (type) {
                        case 3: result.set(n1.doubleValue() % n2.doubleValue());return result;
                        case 2: result.set(n1.floatValue() % n2.floatValue());return result;
                        case 1: result.set(n1.longValue() % n2.longValue());return result;
                        default: result.set(n1.intValue() % n2.intValue());return result;
                    }
                case SIRunner.EQUAL: {
                        SIInteger result1 = new SIInteger();
                        boolean equal;

                        switch (type) {
                            case 3:
                                equal = n1.doubleValue() == n2.doubleValue();
                                break;
                            case 2:
                                equal = n1.floatValue() == n2.floatValue();
                                break;
                            case 1:
                                equal = n1.longValue() == n2.longValue();
                                break;
                            default:
                                equal = n1.intValue() == n2.intValue();
                                break;
                        }

                        result1.set(equal ? 1 : 0);
                        return result1;
                    }

                case SIRunner.NOT_EQUAL: {
                        SIInteger result2 = new SIInteger();
                        boolean notEqual;

                        switch (type) {
                            case 3:
                                notEqual = n1.doubleValue() != n2.doubleValue();
                                break;
                            case 2:
                                notEqual = n1.floatValue() != n2.floatValue();
                                break;
                            case 1:
                                notEqual = n1.longValue() != n2.longValue();
                                break;
                            default:
                                notEqual = n1.intValue() != n2.intValue();
                                break;
                        }

                        result2.set(notEqual ? 1 : 0);
                        return result2;
                    }
                case SIRunner.GREATER:
                    SIInteger bo3 = new SIInteger();
                    bo3.set((n1.doubleValue()>n2.doubleValue())?1:0);
                    return bo3;
                case SIRunner.GREATER_OR_EQUAL:
                    SIInteger bo4 = new SIInteger();
                    bo4.set((n1.doubleValue()>=n2.doubleValue())?1:0);
                    return bo4;
                case SIRunner.LESS:
                    SIInteger bo5 = new SIInteger();
                    bo5.set((n1.doubleValue()<n2.doubleValue())?1:0);
                    return bo5;
                case SIRunner.LESS_OR_EQUAL:
                    SIInteger bo6 = new SIInteger();
                    bo6.set((n1.doubleValue()<=n2.doubleValue())?1:0);
                    return bo6;
            }

            return null;
        }
        return null;
    }
    public static SIBaseNumber getNumber(Number num){
        if(num instanceof Integer){
            SIInteger n = new SIInteger();
            n.set(num);
            return n;
        }else if(num instanceof Long){
            SILong n = new SILong();
            n.set(num);
            return n;
        }else if(num instanceof Float){
            SIFloat n = new SIFloat();
            n.set(num);
            return n;
        }else{
            SIDouble n = new SIDouble();
            n.set(num);
            return n;
        }
    }
}
