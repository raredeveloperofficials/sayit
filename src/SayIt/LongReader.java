package SayIt;

public class LongReader
{
    public static LongReturn read(char[] s,int from){
        //from is from first number , return id of last taken

        int to = from;
        long ans = 0;
        boolean neg = false;
        if(s[from]=='-'){
            neg = true;
            to++;
        }
        while(true){
            char c = s[to];
            if(Character.isDigit(c)){

                int digit = c - '0';

                if (ans > (Long.MAX_VALUE - digit) / 10) {
                    ans = -Long.MAX_VALUE;
                } else {
                    ans = ans * 10 + digit;
                }
            }else{
                //to make it comeback to last valid digit
                to--;
                break;
            } 
            if(s.length<=to+1)break;
            to++;
        }
        return new LongReturn(to,(neg)?-ans:ans);
    }
}
