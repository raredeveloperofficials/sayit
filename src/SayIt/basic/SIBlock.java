package SayIt.basic;
import SayIt.main.Compiled;

public class SIBlock extends SIObject
{
    private Compiled c;
    public SIBlock(){
        name = "block";
    }
    @Override
    public Compiled get() {
        return c;
    }

    @Override
    public void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIBlock need exactly 1 argument ");
        if(!(val[0]instanceof Compiled))throw new IllegalArgumentException("given argument is not an Compiled for SIBlock");
        c =(Compiled) val[0];
    }
    
}
