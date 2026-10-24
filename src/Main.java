import SayIt.SIBuiltins;
import SayIt.SIRunner;
import SayIt.SyntaxReaders.ExpressionResult;
import SayIt.basic.SIExpression;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import SayIt.main.Compiler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.FileNotFoundException;
public class Main {

    public static void main(String[] args) {
        
        String source = readFile("/storage/emulated/0/SayIt/main.si");
        System.out.println("Source:\n"+source+"\nOutput:");
        SIBuiltins.init();
        SIString titan = new SIString();
        titan.set("titan11200");
        int titanindex =SIRunner.addVariable("string",titan);
        //long l = System.nanoTime();
        Compiled compiled = Compiler.compile(source.toCharArray());
        
        
      //printVmBinary();
        //System.out.println(Arrays.toString(compiled.code));
        /*for(int i = 0; i < compiled.block.length;i++){
            System.out.println(Arrays.toString(compiled.block[i].code));
            System.out.println(Arrays.toString(compiled.block[i].block[0].code));
        }*/
        //System.out.println(compiled.block.length);
        long l = System.nanoTime();
        
        //for(int i = 0;i<1000;i++)
        SIRunner.run(compiled,new String[0],new SIObject[0],new String[]{"titan"},new Integer[]{titanindex});
        
        
        double delay = ((System.nanoTime()-l)/1_000_000.0);
        System.out.println("\nruntime:"+delay+"ms");
      System.out.println(SIRunner.functions.keySet());
    }
    public static String readFile(String filename) {
        StringBuilder content = new StringBuilder();

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                } catch (IOException e) {}

                return content.toString();

            }
        } catch (FileNotFoundException e) {} 
        return content.toString();
    }
    public static void read(ExpressionResult expr){
        SIObject[] s =expr.expressions;
        Integer[] in = expr.do_operation;
        System.out.println("{");
        for(int i=0;i<s.length;i++){
            
            SIObject o = s[i];
            if(o instanceof SIExpression){
                read(((SIExpression)o).get());
            }else{
                System.out.println(o.get());
            }
            if(in.length>i){
                System.out.println("op:"+in[i]);
            }
        }
        System.out.println("}");
    }

    public static void printVmBinary() {
        System.out.println("=== VM Binary ===");

        System.out.println("DECLARE: " + SIRunner.DECLARE);
        System.out.println("TYPE: " + SIRunner.TYPE);
        System.out.println("ASSIGN: " + SIRunner.ASSIGN);
        System.out.println("VAR: " + SIRunner.VAR);
        System.out.println("EXPRESSION: " + SIRunner.EXPRESSION);
        System.out.println("VALUE: " + SIRunner.VALUE);
        System.out.println("POOL: " + SIRunner.POOL);
        System.out.println("CALL: " + SIRunner.CALL);
        System.out.println("BYCALL: " + SIRunner.BYCALL);
        System.out.println("OBJECT: " + SIRunner.OBJECT);
        System.out.println("ADDRESS: " + SIRunner.ADDRESS);
        System.out.println("ARGS_START: " + SIRunner.ARGS_START);
        System.out.println("ARGS_END: " + SIRunner.ARGS_END);
        System.out.println("ADD: " + SIRunner.ADD);
        System.out.println("SUB: " + SIRunner.SUB);
        System.out.println("MUL: " + SIRunner.MUL);
        System.out.println("DIV: " + SIRunner.DIV);
        System.out.println("MOD: " + SIRunner.MOD);
        System.out.println("GREATER: " + SIRunner.GREATER);
        System.out.println("LESS: " + SIRunner.LESS);
        System.out.println("GREATER_OR_EQUAL: " + SIRunner.GREATER_OR_EQUAL);
        System.out.println("LESS_OR_EQUAL: " + SIRunner.LESS_OR_EQUAL);
        System.out.println("EQUAL: " + SIRunner.EQUAL);
        System.out.println("ADD_EQUAL: " + SIRunner.ADD_EQUAL);
        System.out.println("SUB_EQUAL: " + SIRunner.SUB_EQUAL);
        System.out.println("MUL_EQUAL: " + SIRunner.MUL_EQUAL);
        System.out.println("DIV_EQUAL: " + SIRunner.DIV_EQUAL);
        System.out.println("MOD_EQUAL: " + SIRunner.MOD_EQUAL);
        System.out.println("NOT_EQUAL: " + SIRunner.NOT_EQUAL);
    }
    
}
