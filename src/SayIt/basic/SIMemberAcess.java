package SayIt.basic;
import SayIt.MemberAcessResult;

public class SIMemberAcess extends SIObject
{
    private MemberAcessResult mar;
    @Override
    public MemberAcessResult get() {
        return mar;
    }

    @Override
    public void set(Object... val) {
        if(val.length != 1)throw new IllegalArgumentException("set Method of SIMemberAcess need exactly 1 argument ");
        if(!(val[0]instanceof MemberAcessResult))throw new IllegalArgumentException("given argument is not an MemberAcessResult for SIMemberAcess");
        mar =(MemberAcessResult) val[0];
    }
    
}
