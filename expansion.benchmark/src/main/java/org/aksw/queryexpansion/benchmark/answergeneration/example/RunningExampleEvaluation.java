package org.aksw.queryexpansion.benchmark.answergeneration.example;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.aksw.openqa.qald.QuestionResult;
import org.aksw.openqa.qald.schema.Dataset;
import org.aksw.openqa.qald.schema.Question;
import org.aksw.queryexpansion.benchmark.answergeneration.BM25FQALDEvaluation;
import org.aksw.queryexpansion.benchmark.answergeneration.QueryExpansionBenchmark;
import org.aksw.queryexpansion.benchmark.answergeneration.glimmer.RDFIndex;

public class RunningExampleEvaluation {
	public static void main(String[] args) throws Exception {
		// in this example we are going to evaluate expanded queries with different words
		
		URL QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffWords.xml");
		Dataset questionDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
				.toURI()));
		List<Question> questions = questionDataset.getQuestion();		
		RDFIndex index = QueryExpansionBenchmark.getIndex();
		
		for(Question q : questions) {
			org.aksw.openqa.qald.schema.String expandedQueryString = q.getString().get(0); // all questions must have at least one schema.String
			String expandedQueryValue = expandedQueryString.getValue(); // here you get the value
			
			// Do the expansion here....
			
			String myNewQuery = ""; // put your new query here
			List<String> result = QueryExpansionBenchmark.query(index, myNewQuery); 
			
			// now you can compare
			QuestionResult questionResult = QueryExpansionBenchmark.benchmark(q.getId(), result);
			
			System.out.println(questionResult.toString()); // here you get the precision, f-measure and recall
		}		
	}
}
