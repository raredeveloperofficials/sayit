package SayIt;

public class IdentifierReader
{
    public static StringReturn read(char[] s,int from){
        //from must be first letter and return last index
        int to = from+1;
        StringBuilder build = new StringBuilder();
        build.append(s[from]);
        while(to<s.length){
            
            char c = s[to];
            if(Character.isAlphabetic(c)||Character.isDigit(c)||c=='_'){
                build.append(c);
            }else {to--;break;}
            to++;
            if(to>=s.length){to--;break;}
        }
        return new StringReturn(to,build.toString());
    }
}
