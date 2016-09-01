package org.aksw.queryexpansion.benchmark.answergeneration;

import java.io.File;
import java.net.URL;

import org.aksw.openqa.qald.QALDBenchmark;
import org.aksw.openqa.qald.QALDBenchmarkResult;
import org.aksw.openqa.qald.schema.Dataset;

public class CompareSystemAnswers {
	public static void main(String[] args) throws Exception {
			URL answerFileURL = CompareSystemAnswers.class.getResource("/org/aksw/queryexpansion/benchmark/systems/answers/qald-4_multilingual_test_questions_diffWords_glimmer_answer.xml");
			Dataset answeredDataset = QALDBenchmark.deserialize(new File(answerFileURL
					.toURI()));
			QALDBenchmarkResult benchmarkResult = QueryExpansionBenchmark.benchmark(answeredDataset);
			System.out.println(benchmarkResult.toString());
	}
}
