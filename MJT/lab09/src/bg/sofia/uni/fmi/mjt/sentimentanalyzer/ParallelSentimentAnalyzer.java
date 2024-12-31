package bg.sofia.uni.fmi.mjt.sentimentanalyzer;

import bg.sofia.uni.fmi.mjt.sentimentanalyzer.threads.ConsumerThread;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.threads.ProducerThread;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.wrapper.MySynchronizedMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelSentimentAnalyzer implements SentimentAnalyzerAPI {
    private int workersCount;
    private Set<String> stopWords;
    private Map<String, SentimentScore> sentimentLexicon;

    /**
     * @param workersCount number of consumer workers
     * @param stopWords set containing stop words
     * @param sentimentLexicon map containing the sentiment lexicon, where the key is
     *                         the word and the value is the sentiment score
     */
    public ParallelSentimentAnalyzer(int workersCount, Set<String> stopWords,
                                     Map<String, SentimentScore> sentimentLexicon) {
        this.workersCount = workersCount;
        this.stopWords = stopWords;
        this.sentimentLexicon = sentimentLexicon;
    }

    @Override
    public Map<String, SentimentScore> analyze(AnalyzerInput... input) {
        Map<String, SentimentScore> resultMap = new HashMap<>();
        AtomicInteger tasksTakenCount = new AtomicInteger(0);

        MySynchronizedMap inputPool = new MySynchronizedMap();
        ProducerThread[] producers = new ProducerThread[input.length];
        for (int i = 0; i < input.length; i++) {
            producers[i] = new ProducerThread(input[i], inputPool);
            System.out.println("Producer" + i + "got an input");
            producers[i].start();
        }

        ConsumerThread[] consumers = new ConsumerThread[workersCount];
        for (int i = 0; i < workersCount; i++) {
            consumers[i] = new ConsumerThread(inputPool, resultMap, stopWords, sentimentLexicon, tasksTakenCount);
            consumers[i].start();
        }

        synchronized (resultMap) {
            while (tasksTakenCount.get() < input.length) {
                System.out.println("Not all tasks are done");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultMap;
    }
}