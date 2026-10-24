package SayIt.basic;

public abstract class SIBaseNumber extends SIObject
{
    
    @Override
    public abstract Number get() ;

    @Override
    public abstract void set(Object... val);
    
}
