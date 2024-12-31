import bg.sofia.uni.fmi.mjt.sentimentanalyzer.AnalyzerInput;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.ParallelSentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.SentimentScore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String[] inputs = {
            "I love programming, it's so fun. But sometimes I hate when the code doesn't work.",
            "Today was a good day. I felt happy and accomplished, though I had some challenges.",
            "I feel so sad today. Everything seems bad and nothing is going right.",
            "I love working on new projects. However, I hate the pressure of deadlines.",
            "Life is good. I am happy with my work and personal life.",
            "The weather is nice today, which makes me feel good. I love sunny days.",
            "I feel bad about the mistakes I made yesterday. It's tough to fix things.",
            "Hate is such a strong word. It's better to focus on good things.",
            "Good things come to those who wait. I am confident about the future.",
            "Sad to see my friends leave, but I know they will be successful in their new journey."
        };

        Set<String> stopwords;
        Map<String, SentimentScore> sentimentLexicon;
        try (
                FileReader stopwordsReader = new FileReader("stopwords.txt");
                FileReader sentimentLexiconReader = new FileReader("AFINN-111.txt");
        ) {
            BufferedReader bufferedReader1 = new BufferedReader(stopwordsReader);
            BufferedReader bufferedReader2 = new BufferedReader(sentimentLexiconReader);

            stopwords = bufferedReader1.lines().collect(Collectors.toSet());
            sentimentLexicon = bufferedReader2.lines()
                    .map(string -> string
                            .split("\\s+")
                    )
                    .collect(
                            Collectors.toMap(
                                arr -> {
                                    if (arr.length == 2) {
                                        return arr[0];
                                    } else {
                                        return arr[0].concat(" ").concat(arr[1]);
                                    }
                                },
                                arr -> SentimentScore.fromScore(Integer.parseInt(arr[arr.length - 1])),
                                (sc1, _) -> sc1
                            )
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("stopwords: \n" + stopwords);
        System.out.println("\n lexicon: " + sentimentLexicon);

        ParallelSentimentAnalyzer parallelSentimentAnalyzer = new ParallelSentimentAnalyzer(
                2, stopwords, sentimentLexicon
        );

        AnalyzerInput[] analyzerInputs = new AnalyzerInput[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            analyzerInputs[i] = new AnalyzerInput("ID-" + i, new StringReader(inputs[i]));
        }

        Map<String, SentimentScore> results = parallelSentimentAnalyzer.analyze(analyzerInputs);
        System.out.println(results);
    }
}
