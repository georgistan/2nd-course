package bg.sofia.uni.fmi.mjt.sentimentanalyzer.threads;

import bg.sofia.uni.fmi.mjt.sentimentanalyzer.SentimentScore;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.wrapper.MySynchronizedMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerThread extends Thread {
    private static final String SPACE = " ";
    private static final String PUNCTUATION_MARKS_REGEX = "\\p{Punct}";
    private static final String WHITE_SPACES_REGEX = "\\s+";

    private MySynchronizedMap syncMap;
    private Map<String, SentimentScore> resultMap;
    private Set<String> stopwords;
    private Map<String, SentimentScore> sentimentLexicon;
    private AtomicInteger tasksTakenCount;

    public ConsumerThread(MySynchronizedMap syncMap,
                          Map<String, SentimentScore> resultMap,
                          Set<String> stopwords,
                          Map<String, SentimentScore> sentimentLexicon,
                          AtomicInteger tasksTakenCount) {
        this.resultMap = resultMap;
        this.syncMap = syncMap;
        this.stopwords = stopwords;
        this.sentimentLexicon = sentimentLexicon;
        this.tasksTakenCount = tasksTakenCount;
    }

    @Override
    public void run() {
        Map.Entry<String, String> task = syncMap.get();

        int score = Arrays.stream(task.getValue()
                        .toLowerCase()
                        .replaceAll(PUNCTUATION_MARKS_REGEX, "")
                        .replaceAll(WHITE_SPACES_REGEX, SPACE)
                        .trim()
                        .split(SPACE)
                )
                .filter(word -> !stopwords.contains(word))
                .map(word -> sentimentLexicon.get(word).getScore())
                .reduce(
                       0,
                        Integer::sum
                );

        System.out.println("Score: " + score);

        resultMap.put(
                task.getKey(),
                SentimentScore.fromScore(score)
        );

        tasksTakenCount.addAndGet(1);
        this.notifyAll();
    }
}
