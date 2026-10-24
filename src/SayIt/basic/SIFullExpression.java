package SayIt.basic;
import SayIt.main.Compiled;

public class SIFullExpression extends SIObject
{
    private Compiled c;
    public int expno;
    @Override
    public Compiled get() {
        return c;
    }

    @Override
    public void set(Object... val) {
        if(val.length>0)c = (Compiled) val[0];
    }
    
}
