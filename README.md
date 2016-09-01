# Query Expansion Benchmark

This repository contains a benchmark for query-expansion based on questions of QALD-4, multilingual chalenge.

- The profiles were obtained with Amazon Mechanical Turk;
- The Benchmark made use of openQA for manipulating and evaluating QALD questions;

The benchmark contain 50 questions manually expanded by users.
The groundtruth was obtained using the real questions--the not expanded questions--with a common algorithm (BMF25F) over DBpedia.

The chalenge consist in define an algorithm that, by using the expanded queries, returns the most similar result to the original query.
