package SayIt;
import SayIt.basic.SIBaseNumber;
import SayIt.basic.SIDouble;
import SayIt.basic.SIFloat;
import SayIt.basic.SIInteger;
import SayIt.basic.SILong;

public class NumberReader
{
    public static NumberResult read(char[] s, int from) {
        double gathered = 0;
        int ind = from;
        boolean neg = false;
        boolean decimal = false;
        double desp = 10;

        if (s[ind] == '-') {
            neg = true;
            ind++;
        }

        while (ind < s.length) {
            char c = s[ind];

            if (Character.isDigit(c)) {
                int digit = c - '0';

                if (!decimal) {
                    gathered = gathered * 10 + digit;
                } else {
                    gathered += digit / desp;
                    desp *= 10;
                }
            } else if (c == '.' && !decimal) {
                decimal = true;
            } else {
                break;
            }

            ind++;
        }

        double value = neg ? -gathered : gathered;

        // Check for an optional type suffix.
        char suffix = '\0';
        if (ind < s.length) {
            char c = s[ind];
            if (c == 'L' || c == 'l' ||
                c == 'F' || c == 'f' ||
                c == 'D' || c == 'd') {
                suffix = Character.toUpperCase(c);
                ind++;
            }
        }

        SIBaseNumber result;

        if (suffix == 'L') {
            SILong n = new SILong();
            n.set(new Object[]{(long) value});
            result = n;
        } else if (suffix == 'F') {
            SIFloat n = new SIFloat();
            n.set(new Object[]{(float) value});
            result = n;
        } else if (suffix == 'D' || decimal) {
            SIDouble n = new SIDouble();
            n.set(new Object[]{value});
            result = n;
        } else if (value >= Integer.MIN_VALUE &&
                   value <= Integer.MAX_VALUE) {
            SIInteger n = new SIInteger();
            n.set(new Object[]{(int) value});
            result = n;
        } else {
            SILong n = new SILong();
            n.set(new Object[]{(long) value});
            result = n;
        }

        return new NumberResult(result, ind - 1);
    }
    
}

