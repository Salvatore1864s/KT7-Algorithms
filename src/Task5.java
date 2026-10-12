import java.io.*;
import java.util.*;

public class Task5 {
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
        int n = fs.nextInt(), m = fs.nextInt(), s = fs.nextInt();

        Edge[] edges = new Edge[m];
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt(), v = fs.nextInt();
            long w = fs.nextLong();
            edges[i] = new Edge(u, v, w);
            graph[u].add(v);
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[s] = 0;

        for (int i = 1; i < n; i++) {
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

        boolean[] affected = new boolean[n + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (Edge e : edges) {
            if (dist[e.from] != INF && dist[e.from] + e.weight < dist[e.to]) {
                if (!affected[e.to]) {
                    affected[e.to] = true;
                    queue.add(e.to);
                }
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int next : graph[v]) {
                if (!affected[next]) {
                    affected[next] = true;
                    queue.add(next);
                }
            }
        }

        StringBuilder out = new StringBuilder();
        for (int v = 1; v <= n; v++) {
            if (affected[v]) out.append("-\n");
            else if (dist[v] == INF) out.append("*\n");
            else out.append(dist[v]).append('\n');
        }
        System.out.print(out);
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
