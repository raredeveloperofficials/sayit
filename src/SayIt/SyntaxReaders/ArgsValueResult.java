package SayIt.SyntaxReaders;

public class ArgsValueResult
{
    public ValueResult[] args;
    public int nextInd;
    public ArgsValueResult(ValueResult[] args,int nextInd){
        this.args = args;
        this.nextInd = nextInd;
    }
}
