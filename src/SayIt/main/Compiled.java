package SayIt.main;
import SayIt.basic.SIFunction;

public class Compiled {
    public String[] pool;
    public Number[] numpool;
    public Integer[] code;
    public Integer[][] expressions;
    public Compiled[] block;
    
    public Compiled(String[] pool,Number[] numpool,Integer[] code,Integer[][] expressions,Compiled[] block){
        this.pool = pool;
        this.numpool = numpool;
        this.expressions = expressions;
        this.code = code;
        this.block = block;
    }
}
