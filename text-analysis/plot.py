import csv, collections
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

rows = list(csv.DictReader(open("results/results.csv")))

def series(exp, case=None):
    d = collections.defaultdict(list)
    for r in rows:
        if r["experiment"] == exp and (case is None or r["case"] == case):
            d[r["algorithm"]].append((int(r["size"]), float(r["avg_ms"])))
    return d

def plot(d, title, xlabel, fname, logy=False):
    plt.figure(figsize=(6, 4))
    for k, v in d.items():
        plt.plot([x for x, _ in v], [y for _, y in v], marker="o", label=k)
    plt.title(title); plt.xlabel(xlabel); plt.ylabel("avg time, ms (5 runs)")
    if logy: plt.yscale("log")
    plt.grid(alpha=.3); plt.legend(); plt.tight_layout(); plt.savefig(fname, dpi=150); plt.close()

plot(series("dp"), "LCS vs Edit Distance", "string length", "plots/dp_time.png")
plot(series("match", "present"), "String matching, pattern present", "text length", "plots/match_present.png")
plot(series("match", "absent"), "String matching, pattern absent", "text length", "plots/match_absent.png")
plot(series("match_worst"), "Adversarial input a^n / a^(m-1)b (log scale)", "text length", "plots/match_worst.png", True)
plot(series("kasai"), "Kasai's algorithm", "string length", "plots/kasai_time.png")

# markdown tables
with open("results/tables.md", "w") as f:
    def table(title, rs, cols):
        f.write(f"**{title}**\n\n| " + " | ".join(cols) + " |\n|" + "---|" * len(cols) + "\n")
        for r in rs: f.write("| " + " | ".join(r) + " |\n")
        f.write("\n")
    for exp, title in [("dp", "LCS / Edit Distance"), ("kasai", "Kasai")]:
        table(title, [[r["algorithm"], r["size"], f'{float(r["avg_ms"]):.3f}'] for r in rows if r["experiment"] == exp],
              ["Algorithm", "Length", "Avg time (ms)"])
    for exp, title in [("match", "String matching (random text, m=100)"), ("match_worst", "String matching, adversarial (a^n, m=100)")]:
        table(title, [[r["algorithm"], r["size"], r["case"], f'{float(r["avg_ms"]):.3f}', r["comparisons"]] for r in rows if r["experiment"] == exp],
              ["Algorithm", "Text length", "Pattern", "Avg time (ms)", "Char comparisons"])
print("plots + tables written")
