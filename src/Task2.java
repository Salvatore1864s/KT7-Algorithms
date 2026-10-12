import java.io.*;
import java.util.*;

public class Task2 {
    static final long INF = Long.MAX_VALUE / 4;

    static class Edge {
        int from, to;
        long weight;
        Edge(int from, int to, long weight) {
            this.from = from; this.to = to; this.weight = weight;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        int s = fs.nextInt();

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            edges[i] = new Edge(fs.nextInt(), fs.nextInt(), fs.nextLong());
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[s] = 0;

        for (int iteration = 1; iteration < n; iteration++) {
            boolean changed = false;
            for (Edge e : edges) {
                if (dist[e.from] == INF) continue;
                long candidate = dist[e.from] + e.weight;
                if (candidate < dist[e.to]) {
                    dist[e.to] = candidate;
                    changed = true;
                }
            }
            if (!changed) break;
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
