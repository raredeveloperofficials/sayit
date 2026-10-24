package SayIt.basic;

public abstract class SIObject
{
    public String name = "Base Object";
    public abstract Object get();
    public abstract void set(Object... val);
    public String getString(){
        return get().toString();
    }
}
