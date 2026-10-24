package SayIt.SyntaxReaders;
import SayIt.basic.SIObject;

public class ValueResult
{
    public SIObject value;
    public int nextInd;
    public ValueResult(SIObject value,int nextInd){
        this.value = value;
        this.nextInd = nextInd;
    }
}
