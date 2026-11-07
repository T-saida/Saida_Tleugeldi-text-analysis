**LCS / Edit Distance**

| Algorithm | Length | Avg time (ms) |
|---|---|---|
| LCS | 100 | 0.009 |
| EditDistance | 100 | 0.047 |
| LCS | 500 | 0.205 |
| EditDistance | 500 | 1.137 |
| LCS | 1000 | 0.869 |
| EditDistance | 1000 | 4.493 |

**Kasai**

| Algorithm | Length | Avg time (ms) |
|---|---|---|
| Kasai | 1000 | 0.010 |
| Kasai | 5000 | 0.061 |
| Kasai | 10000 | 0.110 |

**String matching (random text, m=100)**

| Algorithm | Text length | Pattern | Avg time (ms) | Char comparisons |
|---|---|---|---|---|
| Naive | 10000 | present | 0.039 | 13318 |
| KMP | 10000 | present | 0.042 | 12425 |
| Rabin-Karp | 10000 | present | 0.083 | 100 |
| Naive | 10000 | absent | 0.048 | 13177 |
| KMP | 10000 | absent | 0.037 | 12537 |
| Rabin-Karp | 10000 | absent | 0.078 | 0 |
| Naive | 50000 | present | 0.310 | 66443 |
| KMP | 50000 | present | 0.301 | 61620 |
| Rabin-Karp | 50000 | present | 0.394 | 100 |
| Naive | 50000 | absent | 0.347 | 66380 |
| KMP | 50000 | absent | 0.273 | 62349 |
| Rabin-Karp | 50000 | absent | 0.394 | 0 |
| Naive | 100000 | present | 0.653 | 132851 |
| KMP | 100000 | present | 0.530 | 124338 |
| Rabin-Karp | 100000 | present | 0.807 | 100 |
| Naive | 100000 | absent | 0.678 | 133230 |
| KMP | 100000 | absent | 0.554 | 125019 |
| Rabin-Karp | 100000 | absent | 0.787 | 0 |

**String matching, adversarial (a^n, m=100)**

| Algorithm | Text length | Pattern | Avg time (ms) | Char comparisons |
|---|---|---|---|---|
| Naive | 10000 | absent | 1.844 | 990100 |
| KMP | 10000 | absent | 0.030 | 19901 |
| Rabin-Karp | 10000 | absent | 0.079 | 0 |
| Naive | 50000 | absent | 9.360 | 4990100 |
| KMP | 50000 | absent | 0.151 | 99901 |
| Rabin-Karp | 50000 | absent | 0.393 | 0 |
| Naive | 100000 | absent | 18.935 | 9990100 |
| KMP | 100000 | absent | 0.299 | 199901 |
| Rabin-Karp | 100000 | absent | 0.790 | 0 |

