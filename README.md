# Query Expansion Benchmark

This repository contains a benchmark for query-expansion based on questions of [QALD-4](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/qald/qald-4_multilingual_test_questions.xml), multilingual chalenge.

- The [profiles](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/profiles) were obtained with Amazon Mechanical Turk;
- The Benchmark made use of [openQA](http://openqa.aksw.org) for manipulating and evaluating QALD questions;

The benchmark contain 50 questions manually expanded by users.
The [groundtruth](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/groundtruth) was obtained using the real questions--the not expanded questions--with a common algorithm (BMF25F) over [DBpedia](http://dbpedia.org).

The chalenge consist in define an algorithm that, by using the expanded queries, returns the most similar result to the original query.

You can use the running example at [RunningExampleEvaluation](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/java/org/aksw/queryexpansion/benchmark/answergeneration/example) to know how to evaluate your system:

```
public class RunningExampleEvaluation {
	public static void main(String[] args) throws Exception {
		// in this example we are going to evaluate expanded queries with different words
		
		URL QALDFileURL = GlimmerQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffWords.xml");
		Dataset questionDataset = QueryExpansionBenchmark.generateAnswers(new File(QALDFileURL
				.toURI()));
		List<Question> questions = questionDataset.getQuestion();
		
		for(Question q : questions) {
			org.aksw.openqa.qald.schema.String expandedQueryString = q.getString().get(0); // all questions must have at least one schema.String
			String expandedQueryValue = expandedQueryString.getValue(); // here you get the value
			
			// Do the expansion here....
			
			String myNewQuery = ""; // put your new query here
			List<String> result = QueryExpansionBenchmark.query(myNewQuery); 
			
			// now you can compare
			QuestionResult questionResult = QueryExpansionBenchmark.benchmark(q.getId(), result);
			
			System.out.println(questionResult.toString()); // here you get the precision, recall and f-measure...
		}		
	}
}
```

I forget to mention, Glimmer get the following results using expanded queries:

Different Words: Recall:0.24799999999999994 Precision:0.24799999999999994 F-measure:0.24799999999999994
