package laboratory_problems.problems_03;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Ad implements Comparable<Ad> {
    private String id;
    private String category;
    private double bidValue;
    private double ctr;
    private String content;

    public Ad(String id, String category, double bidValue, double ctr, String content) {
        this.id = id;
        this.category = category;
        this.bidValue = bidValue;
        this.ctr = ctr;
        this.content = content;
    }

    public String getId() { return id; }
    public String getCategory() { return category; }
    public double getBidValue() { return bidValue; }
    public double getCtr() { return ctr; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s %s (bid=%.2f, ctr=%.2f%%) %s",
                id, category, bidValue, ctr * 100.0, content);
    }

    @Override
    public int compareTo(Ad other) {
        int cmp = Double.compare(other.bidValue, this.bidValue);
        if (cmp != 0) return cmp;
        return this.id.compareTo(other.id);
    }
}

class AdRequest {
    private String id;
    private String category;
    private double floorBid;
    private String keywords;

    public AdRequest(String id, String category, double floorBid, String keywords) {
        this.id = id;
        this.category = category;
        this.floorBid = floorBid;
        this.keywords = (keywords == null ? "" : keywords);
    }

    public String getId() { return id; }
    public String getCategory() { return category; }
    public double getFloorBid() { return floorBid; }
    public String getKeywords() { return keywords; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s [%s] (floor=%.1f): %s",
                id, category, floorBid, keywords);
    }
}

class AdNetwork {
    ArrayList<Ad> ads = new ArrayList<>();
    private String pendingRequestLine = null;

    private int relevanceScore(Ad ad, AdRequest req) {
        int score = 0;
        if (ad.getCategory().equalsIgnoreCase(req.getCategory())) score += 10;
        String[] adWords = ad.getContent().toLowerCase().split("\\s+");
        String[] keywords = req.getKeywords().toLowerCase().split("\\s+");
        for (String kw : keywords) {
            if (kw.isEmpty()) continue;
            for (String aw : adWords) {
                if (kw.equals(aw)) score++;
            }
        }
        return score;
    }

    public void readAds(BufferedReader in) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) break;
            if (line.startsWith("AR")) {
                pendingRequestLine = line;
                break;
            }
            String[] parts = line.split("\\s+", 5);
            if (parts.length < 4) continue;
            String id = parts[0];
            String category = parts[1];
            double bidValue = Double.parseDouble(parts[2]);
            double ctr = Double.parseDouble(parts[3]);
            String content = "";
            if (parts.length == 5) content = parts[4];
            Ad ad = new Ad(id, category, bidValue, ctr, content);
            ads.add(ad);
        }
    }

    public List<Ad> placeAds(BufferedReader inputReader, int k, PrintWriter outputWriter) throws IOException {
        String reqLine;
        if (pendingRequestLine != null) {
            reqLine = pendingRequestLine;
            pendingRequestLine = null;
        } else {
            while ((reqLine = inputReader.readLine()) != null) {
                if (!reqLine.trim().isEmpty()) break;
            }
        }

        if (reqLine == null) return Collections.emptyList();

        String[] parts = reqLine.trim().split("\\s+", 4);
        String reqId = parts[0];
        String reqCategory = (parts.length > 1) ? parts[1] : "";
        double floorBid = 0.0;

        if (parts.length > 2) {
            try {
                floorBid = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                floorBid = 0.0;
            }
        }

        String keywords = (parts.length > 3) ? parts[3] : "";

        AdRequest request = new AdRequest(reqId, reqCategory, floorBid, keywords);

        List<Ad> eligible = ads.stream()
                .filter(a -> a.getBidValue() >= request.getFloorBid())
                .collect(Collectors.toList());

        final double x = 5.0;
        final double y = 100.0;

        Map<Ad, Double> scoreMap = new HashMap<>();
        for (Ad a : eligible) {
            double totalScore = relevanceScore(a, request) + x * a.getBidValue() + y * a.getCtr();
            scoreMap.put(a, totalScore);
        }

        List<Ad> byScore = new ArrayList<>(eligible);
        byScore.sort((a, b) -> {
            double sa = scoreMap.get(a);
            double sb = scoreMap.get(b);
            int cmp = Double.compare(sb, sa);
            if (cmp != 0) return cmp;
            return a.compareTo(b);
        });

        List<Ad> topK = byScore.stream().limit(k).collect(Collectors.toList());
        Collections.sort(topK);

        outputWriter.println("Top ads for request " + request.getId() + ":");
        for (Ad ad : topK) outputWriter.println(ad.toString());

        return topK;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        AdNetwork network = new AdNetwork();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out), true);

        String firstLine = br.readLine();
        if (firstLine == null) return;

        firstLine = firstLine.trim();
        int k = Integer.parseInt(firstLine);

        if (k == 0) {
            network.readAds(br);
            network.placeAds(br, 1, pw);
        } else if (k == 1) {
            network.readAds(br);
            network.placeAds(br, 3, pw);
        } else {
            network.readAds(br);
            network.placeAds(br, 8, pw);
        }

        pw.flush();
    }
}