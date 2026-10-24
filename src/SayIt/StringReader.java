package SayIt;

public class StringReader
{
    public static String Tab = "  ";
    public static StringReturn read(char[] s,int from){
        //from is index of " return index of ending "
        int ind = from+1;
        StringBuilder finalString = new StringBuilder();
        //code
        while(true){
            char c = s[ind];
            if(c=='\\'){
                if (ind + 1 >= s.length) break;
                char c2 = s[ind+1];
                switch(c2){
                    case 'n':
                        finalString.append("\n");
                        break;
                    case 't':
                        finalString.append(Tab);
                        break;
                    default:
                        finalString.append(c2);
                }
                
                ind+=2;
                
                continue;
            }
            
            if(c=='"')break;
            finalString.append(c);
            ind++;
            if(s.length<=ind)break;
        }
        
        return new StringReturn(ind,finalString.toString());
    }
}
