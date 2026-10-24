package SayIt;
import SayIt.basic.SIBaseNumber;

public class NumberResult{
    public SIBaseNumber gathered;
    public int nextInd;
    public NumberResult(SIBaseNumber gathered,int nextInd){
        this.gathered = gathered;
        this.nextInd = nextInd;
    }
}
