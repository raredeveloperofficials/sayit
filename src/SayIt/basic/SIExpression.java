package SayIt.basic;

import SayIt.SyntaxReaders.ExpressionResult;

public class SIExpression extends SIObject 
{
    private ExpressionResult mar;
    @Override
    public ExpressionResult get() {
        return mar;
    }

    @Override
    public void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIExpression need exactly 1 argument ");
        if(!(val[0]instanceof ExpressionResult))throw new IllegalArgumentException("given argument is not an ExpressionResult for SIExpression");
        mar =(ExpressionResult) val[0];
    }
    

}
