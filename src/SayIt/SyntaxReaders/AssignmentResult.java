package SayIt.SyntaxReaders;

import SayIt.basic.SIObject;
import SayIt.MemberAcessResult;

public class AssignmentResult 
{
    public MemberAcessResult name;
    public SIObject value;
    public int assignment_op;
    public int nextInd;
    public AssignmentResult(MemberAcessResult name,SIObject value,int assignment_op,int nextInd){
        
        this.name = name;
        this.assignment_op = assignment_op;
        this.value = value;
        this.nextInd = nextInd;
    }
}
