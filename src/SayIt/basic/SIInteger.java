package SayIt.basic;

public class SIInteger extends SIBaseNumber
{
    private int i = 0;
    public SIInteger(){

        name = "int";
    }
    @Override
    public Integer get() {
        return i;
    }

    @Override
    public  void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIInteger need exactly 1 argument ");
        if(!(val[0]instanceof Integer))throw new IllegalArgumentException("given argument is not an integer for SIInteger");
        i =(Integer) val[0];
    }
    
    
}
