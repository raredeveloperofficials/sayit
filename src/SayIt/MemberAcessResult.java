package SayIt;
import java.util.ArrayList;

public class MemberAcessResult
{
    public FunctionCallReturn[] result;
    public int nextInd;
    public MemberAcessResult(ArrayList<FunctionCallReturn> fun,int nextInd){
        this.nextInd = nextInd;
        result =
            fun.toArray(new FunctionCallReturn[0]);
    }
}
