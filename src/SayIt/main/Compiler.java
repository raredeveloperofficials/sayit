package SayIt.main;
import SayIt.SIRunner;
import SayIt.SyntaxReaders.DeclareReader;
import SayIt.SyntaxReaders.DeclareResult;
import SayIt.SyntaxReaders.ExpressionResult;
import SayIt.basic.SIExpression;
import SayIt.basic.SIMemberAcess;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import SayIt.FunctionCallReturn;
import SayIt.SyntaxReaders.ArgsValueResult;
import SayIt.SyntaxReaders.ArgsValueReader;
import SayIt.basic.SIBaseNumber;
import SayIt.SyntaxReaders.ValueResult;
import SayIt.SyntaxReaders.AssignmentResult;
import SayIt.SyntaxReaders.AssignmentReader;
import SayIt.FunctionCallReader;
import SayIt.MemberAcessResult;
import SayIt.MemberAcess;
import SayIt.BetweenContent;
import SayIt.BetweenContentResult;
import SayIt.basic.SIBlock;
import SayIt.IdentifierReader;
import SayIt.StringReturn;
import SayIt.basic.SIFunction;

public class Compiler {
    public static Compiled compile(char[] s) {
        String cname = "",ename = "";
        boolean staticv = true;
        SIFunction.VisibilityMode visble = SIFunction.VisibilityMode.PUBLIC;
        ArrayList<String> pool = new ArrayList<>();
        ArrayList<Integer[]> expressions = new ArrayList<>();
        ArrayList<Integer> code = new ArrayList<>();
        ArrayList<Number> numpool = new ArrayList<>();
        ArrayList<Compiled> block = new ArrayList<>();
        int i = 0;
        while (i < s.length) {
            char c = s[i];
            if (Character.isAlphabetic(c)) {
                DeclareResult dec = DeclareReader.read(s, i);
                if (dec != null) {
                    if (!pool.contains(dec.name))pool.add(dec.name);
                    if (!pool.contains(dec.type))pool.add(dec.type);
                    int nme = pool.indexOf(dec.name);
                    int tpe = pool.indexOf(dec.type);
                    if (dec.hasValue) {
                        Collections.addAll(code,
                                           SIRunner.VAR, nme,
                                           SIRunner.TYPE, tpe,
                                           SIRunner.ASSIGN
                                           );
                        getTypeBytes(block,code,dec.value,expressions,pool,numpool);
                        
                    } else {
                        Collections.addAll(code,
                                           SIRunner.VAR, nme,
                                           SIRunner.TYPE, tpe
                                           
                                           );
                    }
                    code.add(visble.i);
                    code.add((staticv)?SIRunner.STATIC:SIRunner.NON_STATIC);
                    i =dec.nextInd;
                }else{
                    AssignmentResult assres = AssignmentReader.read(s,i);
                    if(assres!=null){
                        SIMemberAcess memo = new SIMemberAcess();
                        memo.set(assres.name);
                        memberacess(block,assres.name,i,s,code,memo,expressions,pool,numpool);
                        
                        Collections.addAll(code,
                                           assres.assignment_op);
                        
                        getTypeBytes(block,code,assres.value,expressions,pool,numpool);
                        i = assres.nextInd;
                    }else{
                       MemberAcessResult memacess = MemberAcess.acess(s,i);
                        i=memacess.nextInd;
                       if(memacess!=null){
                           SIMemberAcess memo = new SIMemberAcess();
                           if(memacess!=null&&memacess.result!=null&&memacess.result.length == 1){
                               String comand = memacess.result[0].FunctionName;
                               if(comand.equals("class")){
                                  
                                   while(i+1<s.length&&Character.isWhitespace(s[i+1])){
                                       i++;
                                   }
                                   StringReturn idn = IdentifierReader.read(s,i+1);
                                   i = idn.nextInd+1;
                                   cname =idn.gathered;
                                   continue;
                               }else if(comand.equals("extend")){
                                   
                                   while(i+1<s.length&&Character.isWhitespace(s[i+1])){
                                       i++;
                                   }
                                   StringReturn idn = IdentifierReader.read(s,i+1);
                                   i = idn.nextInd+1;
                                   ename =idn.gathered;
                                   continue;
                               }else if(s.length>i&&s[i]==':'){
                                   i++;
                                   switch(comand){
                                       case "public": visble = SIFunction.VisibilityMode.PUBLIC; break;
                                       case "private": visble = SIFunction.VisibilityMode.PRIVATE; break;
                                       case "protected": visble = SIFunction.VisibilityMode.PROTECTED; break;
                                       case "static": staticv = true;break;
                                       case "nonstatic": staticv = false;break;
                                   }
                                   
                               }
                           }//else System.out.println("command is no more ");
                           memo.set(memacess);
                           getTypeBytes(block,code,memo,expressions,pool,numpool);
                           
                           while(i+1<s.length&&Character.isWhitespace(s[i+1])){
                               i++;
                           }
                           
                       }
                    }
                }
            }else{
                //symbol/*
                /*
                if(c=='{'){
                    BetweenContentResult res = BetweenContent.get("{","}",s,i);
                    Compiled compile = Compiler.compile(res.content.toCharArray());
                    int ind = block.size();
                   block.add(compile);
                   i = res.nextInd;
                   code.add(SIRunner.BLOCK);
                   code.add(ind);
                }*/
            }
            i++;
        }
        return new Compiled(pool.toArray(new String[0]),numpool.toArray(new Number[0]),code.toArray(new Integer[0]),expressions.toArray(new Integer[0][0]),block.toArray(new Compiled[0]),cname,ename);
    }
    public static int memberacess(ArrayList<Compiled> c,MemberAcessResult memacess,int i,char[] s,ArrayList<Integer> code,SIObject val,ArrayList<Integer[]> expressions,ArrayList<String> pool,ArrayList<Number> numpool){
        if(memacess!=null){
            SIMemberAcess memo = new SIMemberAcess();
            memo.set(memacess);
            getTypeBytes(c,code,memo,expressions,pool,numpool);
            i=memacess.nextInd;
            while(i+1<s.length&&Character.isWhitespace(s[i+1])){
                i++;
            }

        }
        return i;
    }
    public static Integer[] getExpressionCode(ArrayList<Compiled> c,ExpressionResult expres,ArrayList<Integer[]> expression,ArrayList<String> pool,ArrayList<Number> numpool){
        ArrayList<Integer> arr = new ArrayList<>();
        
        getTypeBytes(c,arr,expres.expressions,expres.do_operation,expression,pool,numpool);
       // System.out.println("Expression: " + Arrays.toString(arr.toArray(new Integer[0])));
        return arr.toArray(new Integer[0]);
    }
    public static void getTypeBytes(ArrayList<Compiled> c,ArrayList<Integer> arr,SIObject[] expres,Integer[] operations,ArrayList<Integer[]> expression,ArrayList<String> pool,ArrayList<Number> numpool){
        for(int i = 0;i<expres.length;i++){
            SIObject val = expres[i];
            getTypeBytes(c,arr,val,expression,pool,numpool);
            if(i+1<expres.length)arr.add(operations[i]);
        }
    }
    public static void getTypeBytes(ArrayList<Compiled> c,ArrayList<Integer> arr,SIObject val,ArrayList<Integer[]> expression,ArrayList<String> pool,ArrayList<Number> numpool){
        
        if(val instanceof SIExpression){
            Integer[] exp = getExpressionCode(c,(ExpressionResult)val.get(),expression,pool,numpool);
            int pl = expression.size();
            arr.add(SIRunner.EXPRESSION);
            arr.add(pl);
            expression.add(exp);
            
        }else if(val instanceof SIBlock){
            arr.add(SIRunner.BLOCK);
            arr.add(c.size());
            c.add(((SIBlock)val).get());
        }else if(val instanceof SIString){
            arr.add(SIRunner.POOL);
            if(!pool.contains(val.get().toString()))pool.add(val.get().toString());
            arr.add(pool.indexOf(val.get().toString()));
        }else if(val instanceof SIMemberAcess){
            SIMemberAcess mem = (SIMemberAcess) val;
            if(mem.get().result[0].isFunction)arr.add(SIRunner.CALL);
            for(int i = 0;i<mem.get().result.length;i++){
                FunctionCallReturn call = mem.get().result[i];
                arr.add(SIRunner.VAR);
                if(!pool.contains(call.FunctionName))pool.add(call.FunctionName);
                arr.add(pool.indexOf(call.FunctionName));
                
                if(call.isFunction){
                    arr.add(SIRunner.ARGS_START);
                    ArgsValueResult argsv = ArgsValueReader.read(call.callContent.toCharArray(),0);
                    for(ValueResult valres : argsv.args){
                        getTypeBytes(c,arr,valres.value,expression,pool,numpool);
                    }
                    arr.add(SIRunner.ARGS_END);
                }else{
                    arr.add(SIRunner.OBJECT);
                }
                if(i+1<mem.get().result.length){
                    arr.add(SIRunner.BYCALL);
                }
            }
        }else if(val instanceof SIBaseNumber){
            arr.add(SIRunner.VALUE);
            if(!numpool.contains((Number)val.get())){
                numpool.add(((SIBaseNumber)val).get());
            }
            arr.add(numpool.indexOf(((SIBaseNumber)val).get()));

        }
    }
     
}
