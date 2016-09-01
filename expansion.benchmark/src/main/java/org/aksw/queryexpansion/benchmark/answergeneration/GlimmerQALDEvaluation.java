package org.aksw.queryexpansion.benchmark.answergeneration;

import java.io.File;
import java.net.URL;

import org.aksw.openqa.qald.QALDBenchmark;
import org.aksw.openqa.qald.schema.Dataset;

public class GlimmerQALDEvaluation {

	public static void main(String[] args) throws Exception {
		URL QALDFileURL = GlimmerQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffWords.xml");				
		try {
			File answerFile = new File("qald-4_multilingual_test_questions_diffWords_glimmer_answer.xml");
			Dataset answeredDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
					.toURI()));
			QALDBenchmark.serialize(answeredDataset, answerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
