import java.io.*;
import java.util.*;

public class Task4 {
    static final long INF = Long.MAX_VALUE / 4;

    static class Edge {
        int to;
        long weight;
        Edge(int to, long weight) { this.to = to; this.weight = weight; }
    }

    static class State implements Comparable<State> {
        int vertex, usedCoupon;
        long distance;
        State(int vertex, int usedCoupon, long distance) {
            this.vertex = vertex; this.usedCoupon = usedCoupon; this.distance = distance;
        }
        public int compareTo(State other) {
            return Long.compare(distance, other.distance);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt(), m = fs.nextInt();
        int s = fs.nextInt(), t = fs.nextInt();

        @SuppressWarnings("unchecked")
        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt(), v = fs.nextInt();
            long w = fs.nextLong();
            graph[u].add(new Edge(v, w));
        }

        long[][] dist = new long[n + 1][2];
        for (long[] row : dist) Arrays.fill(row, INF);
        dist[s][0] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(s, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.distance != dist[cur.vertex][cur.usedCoupon]) continue;

            for (Edge e : graph[cur.vertex]) {
                long normal = cur.distance + e.weight;
                if (normal < dist[e.to][cur.usedCoupon]) {
                    dist[e.to][cur.usedCoupon] = normal;
                    pq.add(new State(e.to, cur.usedCoupon, normal));
                }

                if (cur.usedCoupon == 0) {
                    long discounted = cur.distance + e.weight / 2;
                    if (discounted < dist[e.to][1]) {
                        dist[e.to][1] = discounted;
                        pq.add(new State(e.to, 1, discounted));
                    }
                }
            }
        }

        long answer = Math.min(dist[t][0], dist[t][1]);
        System.out.println(answer == INF ? -1 : answer);
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr, len;
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer); ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c;
            do { c = read(); } while (c <= ' ' && c != -1);
            long sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long value = 0;
            while (c > ' ') { value = value * 10 + c - '0'; c = read(); }
            return value * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}
