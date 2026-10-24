package SayIt;

public class DoubleReader
{
    public static DoubleReturn read(char[] s,int from){
        //from must be first number and return must give lasg number index
        double gathered = 0;
        int ind = from;
        boolean neg = false;
        boolean decimal = false;
        double desp = 10;
        if(s[ind]=='-'){
            neg = true;
            ind++;
        }
        while(true){
            char c = s[ind];

            if(!decimal){//non decimal
                if(Character.isDigit(c)){
                    int digit = c - '0';
                    if (gathered > (Double.MAX_VALUE - digit) / 10) {
                        gathered = -Double.MAX_VALUE;
                    } else {
                        gathered = gathered * 10 + digit;
                    }
                }else if(c == '.'){
                    decimal = true;
                }else break;
            }else{//after decimal
                if(Character.isDigit(c)){
                    double digit = c - '0';

                    gathered = gathered + digit/desp;
                    desp*=10;
                }else break;
            }
            ind++;
            if(s.length<=ind)break;
        }
        ind--;
        return new DoubleReturn(ind,(neg)?-gathered:gathered);
    }
}
