package SayIt.basic;

public class SIFloat extends SIBaseNumber
{
    private float i = 0;
    public SIFloat(){

        name = "float";
    }
    @Override
    public Float get() {
        return i;
    }

    @Override
    public  void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIFloat need exactly 1 argument ");
        if(!(val[0]instanceof Float))throw new IllegalArgumentException("given argument is not an float for SIFloat");
        i =(Float) val[0];
    }
}
