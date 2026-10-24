package SayIt;

public class FloatReader
{
    public static FloatReturn read(char[] s,int from){
        //from must be first number and return must give lasg number index
        float gathered = 0;
        int ind = from;
        boolean neg = false;
        boolean decimal = false;
        float desp = 10;
        if(s[ind]=='-'){
            neg = true;
            ind++;
        }
        while(true){
            char c = s[ind];
            
            if(!decimal){//non decimal
                if(Character.isDigit(c)){
                    int digit = c - '0';
                    if (gathered > (Float.MAX_VALUE - digit) / 10) {
                        gathered = -Float.MAX_VALUE;
                    } else {
                        gathered = gathered * 10 + digit;
                    }
                }else if(c == '.'){
                    decimal = true;
                }else break;
            }else{//after decimal
                if(Character.isDigit(c)){
                    float digit = c - '0';
                    
                    gathered = gathered + digit/desp;
                    desp*=10;
                }else break;
            }
            ind++;
            if(s.length<=ind)break;
        }
        ind--;
        return new FloatReturn(ind,(neg)?-gathered:gathered);
    }
}
