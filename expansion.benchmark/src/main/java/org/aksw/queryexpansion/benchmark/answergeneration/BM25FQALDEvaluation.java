package org.aksw.queryexpansion.benchmark.answergeneration;

import java.io.File;
import java.net.URL;

import org.aksw.openqa.qald.QALDBenchmark;
import org.aksw.openqa.qald.schema.Dataset;

public class BM25FQALDEvaluation {

	public static void main(String[] args) throws Exception {
		URL QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffWords.xml");				
		
		File answerFile = new File("qald-4_multilingual_test_questions_diffWords_BM25F_answer.xml");
		Dataset answeredDataset = null; 
				
//				n QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
//				.toURI()));
//		QALDBenchmark.serialize(answeredDataset, answerFile);
		
		QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffOrder.xml");				
		
		answerFile = new File("qald-4_multilingual_test_questions_diffOrder_BM25F_answer.xml");
		answeredDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
				.toURI()));
		QALDBenchmark.serialize(answeredDataset, answerFile);
		
		QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_WithInflec.xml");				
		
		answerFile = new File("qald-4_multilingual_test_questions_WithInflec_BM25F_answer.xml");
		answeredDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
				.toURI()));
		QALDBenchmark.serialize(answeredDataset, answerFile);
		
		QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_WithTypos.xml");				
		
		answerFile = new File("qald-4_multilingual_test_questions_WithTypos_BM25F_answer.xml");
		answeredDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
				.toURI()));
		QALDBenchmark.serialize(answeredDataset, answerFile);
		
		QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/all.xml");				
		
		answerFile = new File("qald-4_multilingual_test_questions_all_BM25F_answer.xml");
		answeredDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
				.toURI()));
		QALDBenchmark.serialize(answeredDataset, answerFile);
	}
}
