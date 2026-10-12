import java.io.*;
import java.util.*;

public class Task1 {
    static final long INF = Long.MAX_VALUE / 4;

    static class Edge {
        int to;
        long weight;
        Edge(int to, long weight) { this.to = to; this.weight = weight; }
    }

    static class State implements Comparable<State> {
        int vertex;
        long distance;
        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
        public int compareTo(State other) {
            return Long.compare(distance, other.distance);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        int s = fs.nextInt();

        @SuppressWarnings("unchecked")
        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            long w = fs.nextLong();
            graph[u].add(new Edge(v, w));
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[s] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(s, 0));

        while (!pq.isEmpty()) {
            State state = pq.poll();
            if (state.distance != dist[state.vertex]) continue;

            for (Edge edge : graph[state.vertex]) {
                long candidate = state.distance + edge.weight;
                if (candidate < dist[edge.to]) {
                    dist[edge.to] = candidate;
                    pq.add(new State(edge.to, candidate));
                }
            }
        }

        StringBuilder out = new StringBuilder();
        for (int v = 1; v <= n; v++) {
            if (v > 1) out.append(' ');
            out.append(dist[v] == INF ? "INF" : Long.toString(dist[v]));
        }
        System.out.println(out);
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
