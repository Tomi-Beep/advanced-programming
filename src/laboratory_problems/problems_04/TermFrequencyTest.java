package laboratory_problems.problems_04;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја"};
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}

class TermFrequency {
    private List<String> words;
    private Set<String> stopSet;

    public TermFrequency(InputStream inputStream, String[] stopWords) {
        words = new ArrayList<>();
        stopSet = new HashSet<>();

        for (String sw : stopWords) {
            stopSet.add(sw.toLowerCase());
        }

        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {
            String w = scanner.next().toLowerCase();
            w = w.replace(",", "");
            w = w.replace(".", "");
            if (w.isEmpty()) continue;
            words.add(w);
        }
        scanner.close();

    }

    public int countTotal() {
        return words.size();
    }

    public int countDistinct() {
        Set<String> distinct = new HashSet<>(words);

        return distinct.size();
    }

    public Map<String, Integer> getFrequency() {
        Map<String, Integer> frequency = new TreeMap<>();
        for (String s : words) {
            if (!stopSet.contains(s))
                frequency.put(s, frequency.getOrDefault(s, 0) + 1);
        }
        return frequency;
    }

    public List<String> mostOften(int k) {
        Map<String, Integer> frequency = getFrequency();

        List<Map.Entry<String, Integer>> entries = frequency.entrySet().stream().sorted((e1, e2) -> {
            int cmp = e2.getValue().compareTo(e1.getValue());
            if (cmp != 0) return cmp;
            return e1.getKey().compareTo(e2.getKey());
        }).toList();

        List<String> result = new ArrayList<>();
        int limit = Math.min(k, entries.size());
        for (int i = 0; i < limit; i++) result.add(entries.get(i).getKey());
        return result;
    }

    public Map<Integer, List<String>> byFrequency() {
        Map<Integer, List<String>> map = new TreeMap<>(Comparator.reverseOrder());
        Map<String, Integer> frequency = getFrequency();

        frequency.forEach((s, i) -> {
            if (map.containsKey(i)) {
                map.get(i).add(s);
            } else {
                List<String> list = new ArrayList<>();
                list.add(s);
                map.put(i, list);
            }
//            map.computeIfAbsent(i, )
        });

        map.forEach((i, s) -> s.sort(Comparator.naturalOrder()));

        return map;
    }

    public Set<String> stopWordsUsed() {
        return stopSet.stream().filter(s -> words.contains(s)).collect(Collectors.toSet());
    }

    public String longestWord() {
        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}

