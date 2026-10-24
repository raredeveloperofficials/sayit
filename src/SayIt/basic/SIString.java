package SayIt.basic;

public class SIString extends SIObject
{
    private String s = "";
    public SIString(){

        name = "string";
    }
    @Override
    public String get() {
        return s;
    }

    @Override
    public void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIString need exactly 1 argument ");
        if(!(val[0]instanceof String))throw new IllegalArgumentException("given argument is not an String for SIString");
        s =(String) val[0];
    }
    
}
