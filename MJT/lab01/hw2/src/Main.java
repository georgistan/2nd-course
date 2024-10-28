// Тази задача определено е доста тегава, не успях да направя повече.... :(

public class Main {
    public static void main(String[] args) {
        String[] strings1 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."};
        strings1 = TextJustifier.justifyText(strings1, 11);

        printStringArray(strings1);

        String[] strings2 = {"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer."};
        strings2 = TextJustifier.justifyText(strings2, 20);

        printStringArray(strings2);
    }

    public static void printStringArray(String[] strArr) {
        for (String str : strArr) {
            System.out.println(str);
        }
    }
}