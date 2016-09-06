# Query Expansion Benchmark

This repository contains a benchmark for query-expansion based on questions of [QALD-4](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/qald/qald-4_multilingual_test_questions.xml), multilingual chalenge.

- The [profiles](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/profiles) were obtained using Amazon Mechanical Turk;
- The Benchmark made use of [openQA](http://openqa.aksw.org) and [KBox](http://github.com/aksw/KBox) for manipulating and evaluating QALD questions;

The benchmark contain 50 questions manually expanded by users.
The [groundtruth](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/groundtruth) was obtained using the real questions--the not expanded questions--with a common algorithm (BM25F) over [DBpedia](http://dbpedia.org).

The chalenge consist in define an algorithm that, by using the expanded queries, returns the most similar result to the original query.

### Challenges

We compile the chalenges in five categories:

- [Different Words](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffWords.xml): When user describe the aiming resource using different words;
- [Different Order](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffOrder.xml): The query is expanded with words in different order (e.g. Bill Gates as Gates Bill);
- [Inflections](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_withInflec.xml): The query is expanded with words using different inflections (e.g. orange as oranges);
- [Typos](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_withTypos.xml): The query is expanded with typos (e.g. words to wors);
- [All](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_all.xml): All the above chalenges together.

### Running Example

You can use the running example at [RunningExampleEvaluation](https://github.com/AKSW/query-expansion-benchmark/tree/master/expansion.benchmark/src/main/java/org/aksw/queryexpansion/benchmark/answergeneration/example) to learn how to evaluate your system:

```
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
			
			System.out.println(questionResult.toString()); // here we go...
		}		
	}
}
```

### Reported systems result

I forgot to mention, BM25F got the following results using expanded queries without treatment:

- [Different Words](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffWords.xml): Recall:0.24799999999999994 Precision:0.24799999999999994 F-measure:0.24799999999999994
- [Inflections](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_withInflec.xml): Recall:0.45519999999999994 Precision:0.45519999999999994 F-measure:0.45519999999999994
- [Different Order](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/resources/org/aksw/queryexpansion/benchmark/chalenges/qald-4_multilingual_test_questions_diffOrder.xml): Recall:0.5811999999999999 Precision:0.5811999999999999 F-measure:0.5811999999999999

You can check it yourself executing [BM25FQALDEvaluation](https://github.com/AKSW/query-expansion-benchmark/blob/master/expansion.benchmark/src/main/java/org/aksw/queryexpansion/benchmark/answergeneration/BM25FQALDEvaluation.java).

### Acknowledgement & License

- If you plan to use the benchmark or any part of the code for benchmark your query expansion approach, you might have to proper reference BM25F, [KBox](http://github.com/aksw/KBox), [openQA](http://openqa.aksw.org) and [QALD](http://qald.sebastianwalter.org/index.php?x=home&q=4).

- Any use of the benchmark or any part of the code for exclusive propouse is not authorized.
In order to do that, you have to contact the author.

### Contact

[http://emarx.org](http://emarx.org)
