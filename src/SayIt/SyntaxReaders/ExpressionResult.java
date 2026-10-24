package SayIt.SyntaxReaders;
import SayIt.basic.SIObject;

public class ExpressionResult
{
    public Integer[] do_operation;
    public SIObject[] expressions;
    public int nextInd;
    public ExpressionResult(SIObject[] expressions,Integer[] do_operation,int nextInd){
        this.expressions =  expressions;
        this.do_operation = do_operation;
        this.nextInd = nextInd;
    }
}
