import java.io.*;
import java.util.*;

public class Task6 {
    static final long INF = Long.MAX_VALUE / 4;
    static final int MOD = 1_000_000_007;

    static class Edge {
        int from, to;
        long weight;
        Edge(int from, int to, long weight) {
            this.from = from; this.to = to; this.weight = weight;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt(), m = fs.nextInt();
        int s = fs.nextInt(), t = fs.nextInt(), k = fs.nextInt();

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            edges[i] = new Edge(fs.nextInt(), fs.nextInt(), fs.nextLong());
        }

        long[] previousDist = new long[n + 1];
        Arrays.fill(previousDist, INF);
        int[] previousWays = new int[n + 1];
        previousDist[s] = 0;
        previousWays[s] = 1;

        long best = INF;
        int ways = 0;

        for (int length = 0; length <= k; length++) {
            if (previousDist[t] < best) {
                best = previousDist[t];
                ways = previousWays[t];
            } else if (previousDist[t] == best && best != INF) {
                ways = (int) (((long) ways + previousWays[t]) % MOD);
            }

            if (length == k) break;

            long[] nextDist = new long[n + 1];
            Arrays.fill(nextDist, INF);
            int[] nextWays = new int[n + 1];

            for (Edge e : edges) {
                if (previousDist[e.from] == INF) continue;
                long candidate = previousDist[e.from] + e.weight;

                if (candidate < nextDist[e.to]) {
                    nextDist[e.to] = candidate;
                    nextWays[e.to] = previousWays[e.from];
                } else if (candidate == nextDist[e.to]) {
                    nextWays[e.to] = (int) (((long) nextWays[e.to] + previousWays[e.from]) % MOD);
                }
            }

            previousDist = nextDist;
            previousWays = nextWays;
        }

        if (best == INF) System.out.println("IMPOSSIBLE");
        else System.out.println(best + " " + ways);
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
