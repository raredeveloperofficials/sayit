
package SayIt;

public class BetweenContent {
    public static BetweenContentResult parentheses(char[] content, int ind) {
        return get("(", ")", content, ind);
    }
    public static BetweenContentResult get(
        String from, String to, char[] content, int ind
    ) {
        if (from.isEmpty() || to.isEmpty()) {
            return new BetweenContentResult("", ind);
        }

        // Match the opening delimiter at ind.
        if (ind + from.length() > content.length) {
            return new BetweenContentResult("", ind);
        }

        for (int i = 0; i < from.length(); i++) {
            if (content[ind + i] != from.charAt(i)) {
                return new BetweenContentResult("", ind);
            }
        }

        int pos = ind + from.length();
        int depth = 1;
        StringBuilder b = new StringBuilder();
        
        while (pos < content.length) {
            if (content[pos] == '"') {
                
                StringReturn sr = StringReader.read(content,pos);
                b.append('"').append(sr.gathered).append('"');
                pos=sr.nextInd+1;
                continue;
                
            }
            // Nested opening delimiter
            
                if (pos + from.length() <= content.length
                    && matches(from, content, pos)) {
                    depth++;
                    b.append(from);
                    pos += from.length();
                } else if (pos + to.length() <= content.length
                           && matches(to, content, pos)) {
                    depth--;
                    if (depth == 0) {
                        return new BetweenContentResult(
                            b.toString(), pos + to.length() - 1
                        );
                    }
                    b.append(to);
                    pos += to.length();
                } else {
                    b.append(content[pos]);
                    pos++;
                }
            }
        

        // Closing delimiter was never found.
        return new BetweenContentResult("", pos);
    }

    private static boolean matches(
        String delimiter, char[] content, int pos
    ) {
        for (int i = 0; i < delimiter.length(); i++) {
            if (content[pos + i] != delimiter.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}



