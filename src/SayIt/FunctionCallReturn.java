package SayIt;

public class FunctionCallReturn
{
    public int nextInd;
    public boolean isFunction;
    public String FunctionName;
    public String callContent;
    public FunctionCallReturn(int nextInd,boolean isFunction,String FunctionName,String callContent){
        this.nextInd = nextInd;
        this.isFunction = isFunction;
        this.FunctionName = FunctionName;
        this.callContent = callContent;
    }
}
