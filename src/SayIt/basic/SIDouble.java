package SayIt.basic;

public class SIDouble extends SIBaseNumber
{
    private double i = 0;
    public SIDouble(){

        name = "double";
    }
    @Override
    public Double get() {
        return i;
    }

    @Override
    public  void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIDouble need exactly 1 argument ");
        if(!(val[0]instanceof Double))throw new IllegalArgumentException("given argument is not an double for SIDouble");
        i =(Double) val[0];
    }
}
