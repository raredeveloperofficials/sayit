package SayIt.basic;

public class SILong extends SIBaseNumber
{
    private long i = 0;
    public SILong(){
        
        name = "long";
    }
    @Override
    public Long get() {
        return i;
    }

    @Override
    public  void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SILong need exactly 1 argument ");
        if(!(val[0]instanceof Long))throw new IllegalArgumentException("given argument is not an long for SILong");
        i =(Long) val[0];
    }
}
