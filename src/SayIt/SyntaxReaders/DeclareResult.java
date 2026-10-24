package SayIt.SyntaxReaders;
import SayIt.basic.SIObject;

public class DeclareResult
{
    public String type,name;
    public SIObject value;
    public boolean hasValue;
    public int nextInd;
    public DeclareResult(String type,String name,boolean hasValue,SIObject value,int nextInd){
        this.type = type;
        this.name = name;
        this.hasValue = hasValue;
        this.value = value;
        this.nextInd = nextInd;
    }
}
