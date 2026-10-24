package SayIt;

public class FunctionCallReader
{
    public static FunctionCallReturn read(char[] s,int from){
        //from is starting of that
        StringReturn func = IdentifierReader.read(s,from);
        BetweenContentResult bcr = null;
        boolean isFunc = false;
        int to = func.nextInd+1;
        if(to<s.length){
            while(s[to]==' '){
                if(to+1<s.length)to++;
            }
            if(s[to]=='('){
                bcr = BetweenContent.parentheses(s,to);
                isFunc=true;
                //System.out.println("itd a function");
            }else{
                //System.out.println(s[to]);
            }
        }
        if(bcr!=null){
            return new FunctionCallReturn(bcr.nextInd,isFunc,func.gathered,bcr.content);
        }else{
            return new FunctionCallReturn(func.nextInd,isFunc,func.gathered,"");
        }
    }
}
