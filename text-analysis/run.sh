#!/bin/sh
# Compile, test, benchmark, plot. Requires JDK 17+ and Python 3 with matplotlib (plots only).
set -e
mkdir -p out results plots
javac -d out $(find src test -name '*.java')
java -cp out textanalysis.AllTests
java -cp out textanalysis.bench.Benchmark
python3 plot.py
