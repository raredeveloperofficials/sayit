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
import SayIt.basic.SIBoolean;

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
                    CALL_Return ret = RunnerFunctions.calcExpressionVal(compiled,expression_no,ind,names,extravalues,variables);
                    SIObject now = ret.returnval;
                    ind = ret.next;
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
                CALL_Return ret = RunnerFunctions.calcExpressionVal(compiled,expression_no,ind,names,extravalues,variables);
                first = ret.returnval;
                ind = ret.next;
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
                        SIBoolean i = new SIBoolean();
                        i.set(((SIString)o1).get().equals(((SIString)o2).get())?1:0);
                        return i;
                        
                    case SIRunner.NOT_EQUAL:
                        SIBoolean i2 = new SIBoolean();
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
                        SIBoolean i = new SIBoolean();
                        i.set(((SIString)o1).get().equals(o2.getString())?1:0);
                        return i;

                    case SIRunner.NOT_EQUAL:
                        SIBoolean i2 = new SIBoolean();
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
                        SIBoolean i = new SIBoolean();
                        i.set(o1.getString().equals(((SIString)o2).get())?1:0);
                        return i;

                    case SIRunner.NOT_EQUAL:
                        SIBoolean i2 = new SIBoolean();
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
                        SIBoolean result1 = new SIBoolean();
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
                        SIBoolean result2 = new SIBoolean();
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
                    SIBoolean bo3 = new SIBoolean();
                    bo3.set((n1.doubleValue()>n2.doubleValue())?1:0);
                    return bo3;
                case SIRunner.GREATER_OR_EQUAL:
                    SIBoolean bo4 = new SIBoolean();
                    bo4.set((n1.doubleValue()>=n2.doubleValue())?1:0);
                    return bo4;
                case SIRunner.LESS:
                    SIBoolean bo5 = new SIBoolean();
                    bo5.set((n1.doubleValue()<n2.doubleValue())?1:0);
                    return bo5;
                case SIRunner.LESS_OR_EQUAL:
                    SIBoolean bo6 = new SIBoolean();
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
