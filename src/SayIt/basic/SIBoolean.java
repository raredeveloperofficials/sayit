package SayIt.basic;

public class SIBoolean extends SIObject
{
    private boolean i = false;
    public SIBoolean(){

        name = "boolean";
    }
    @Override
    public Integer get() {
        return (i)?1:0;
    }

    @Override
    public  void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIBoolean need exactly 1 argument ");
        if(!(val[0]instanceof Integer)&&!(val[0] instanceof Boolean))throw new IllegalArgumentException("given argument is not an boolean or integer for SIBoolean");
        if(val[0]instanceof Integer)i =(((Integer) val[0])>0); else
        if(val[0] instanceof Boolean)i =(Boolean)val[0];
    }
}
