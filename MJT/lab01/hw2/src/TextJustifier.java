public class TextJustifier {
    public static String justifyLine(String[] words, int spaces) {
        int wordsCount = words.length;

        if(wordsCount == 1) {
            return words[0] + " ".repeat(spaces);
        }

        int spacesBetween = spaces / (wordsCount - 1);
        int extraSpaces = spaces % (wordsCount - 1);

        StringBuilder line = new StringBuilder();

        for (int i = 0; i < wordsCount; i++) {
            line.append(words[i]);

            if (i < wordsCount - 1) {
                if(i < extraSpaces) {
                    line.append(" ".repeat(spacesBetween + 1));
                } else {
                    line.append(" ".repeat(spacesBetween));
                }
            }
        }

        return line.toString();
    }

    public static String[] justifyText(String[] words, int maxWidth) {
        String[] justifiedText = {};
        int wordsLen = words.length;

        String[] rowWords = new String[words.length];
        int currLen = 0;
        int currWords = 0;

        for (int i = 0; i < wordsLen; i++) {
            if(currLen + words[i].length() + 1 < maxWidth) {
                currWords++;
                currLen += words[i].length();
                rowWords[i] = words[i];
            } else {
                System.out.println(maxWidth - currLen);
                break;
            }
        }

        return new String[]{justifyLine(rowWords, maxWidth - currLen)};
    }
}