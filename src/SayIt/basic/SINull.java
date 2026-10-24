package SayIt.basic;

public class SINull extends SIObject
{
    public SINull(){

        name = "null";
    }
    @Override
    public Object get() {
        return null;
    }

    @Override
    public void set(Object[] val) {
    }
    
}
