import java.io.*;
import java.util.*;

public class Task3 {
    static final long INF = Long.MAX_VALUE / 4;

    static class Edge {
        int to;
        long weight;
        Edge(int to, long weight) { this.to = to; this.weight = weight; }
    }

    static class State implements Comparable<State> {
        int vertex, source;
        long distance;
        State(int vertex, int source, long distance) {
            this.vertex = vertex; this.source = source; this.distance = distance;
        }
        public int compareTo(State other) {
            int byDistance = Long.compare(distance, other.distance);
            if (byDistance != 0) return byDistance;
            return Integer.compare(source, other.source);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        int k = fs.nextInt();

        boolean[] isSource = new boolean[n + 1];
        for (int i = 0; i < k; i++) isSource[fs.nextInt()] = true;

        @SuppressWarnings("unchecked")
        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt(), v = fs.nextInt();
            long w = fs.nextLong();
            graph[u].add(new Edge(v, w));
            graph[v].add(new Edge(u, w));
        }

        long[] dist = new long[n + 1];
        int[] source = new int[n + 1];
        Arrays.fill(dist, INF);
        Arrays.fill(source, -1);

        PriorityQueue<State> pq = new PriorityQueue<>();
        for (int v = 1; v <= n; v++) {
            if (isSource[v]) {
                dist[v] = 0;
                source[v] = v;
                pq.add(new State(v, v, 0));
            }
        }

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.distance != dist[cur.vertex] || cur.source != source[cur.vertex]) continue;

            for (Edge e : graph[cur.vertex]) {
                long nd = cur.distance + e.weight;
                if (nd < dist[e.to] || (nd == dist[e.to] && cur.source < source[e.to])) {
                    dist[e.to] = nd;
                    source[e.to] = cur.source;
                    pq.add(new State(e.to, cur.source, nd));
                }
            }
        }

        StringBuilder out = new StringBuilder();
        for (int v = 1; v <= n; v++) {
            if (source[v] == -1) out.append("-1 -1\n");
            else out.append(source[v]).append(' ').append(dist[v]).append('\n');
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
