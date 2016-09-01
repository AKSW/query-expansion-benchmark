# Query Expansion Benchmark

This repository contains a benchmark for query-expansion based on questions of [QALD-4](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/qald/qald-4_multilingual_test_questions.xml), multilingual chalenge.

- The [profiles](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/profiles) were obtained with Amazon Mechanical Turk;
- The Benchmark made use of [openQA](http://openqa.aksw.org) for manipulating and evaluating QALD questions;

The benchmark contain 50 questions manually expanded by users.
The [groundtruth](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/groundtruth) was obtained using the real questions--the not expanded questions--with a common algorithm (BMF25F) over [DBpedia](http://dbpedia.org).

The chalenge consist in define an algorithm that, by using the expanded queries, returns the most similar result to the original query.
