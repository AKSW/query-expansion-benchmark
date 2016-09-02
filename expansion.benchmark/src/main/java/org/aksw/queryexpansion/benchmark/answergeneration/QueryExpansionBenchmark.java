package org.aksw.queryexpansion.benchmark.answergeneration;

import it.unimi.di.big.mg4j.query.nodes.Query;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.aksw.kbox.KBox;
import org.aksw.kbox.ZipInstall;
import org.aksw.openqa.qald.QALDBenchmark;
import org.aksw.openqa.qald.QALDBenchmarkResult;
import org.aksw.openqa.qald.QuestionResult;
import org.aksw.openqa.qald.schema.Answer;
import org.aksw.openqa.qald.schema.Answers;
import org.aksw.openqa.qald.schema.Dataset;
import org.aksw.openqa.qald.schema.Question;
import org.aksw.queryexpansion.benchmark.answergeneration.glimmer.Querier;
import org.aksw.queryexpansion.benchmark.answergeneration.glimmer.RDFIndex;

import com.yahoo.glimmer.query.Context;
import com.yahoo.glimmer.web.QueryResult;
import com.yahoo.glimmer.web.QueryResultItem;

public class QueryExpansionBenchmark {	

	/**
	 * Retrieve a list of resources in the target dataset using Glimmer 
	 * with a given query.
	 * 
	 * @param query the query that is going to be used for search.
	 * @return a list of resources retrieved by the given query.
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static List<String> query(String query) throws Exception {
		List<String> result = null;		
		RDFIndex index = getIndex();
		result = query(index, query);		
		return result;
	}
	
	/**
	 * Return an RDFIndex.
	 * 
	 * @return an RDFIndex.
	 * 
	 * @throws Exception if there is an exception while reading the index. 
	 */
	public static RDFIndex getIndex() throws Exception {
		URL idx = new URL("http://openqa.aksw.org/download/glimmer/dbpedia/39/kb.idx");
		File kb = null;
		try {
			kb = KBox.getResource(idx, new ZipInstall(), true);
		} catch (Exception e) {
			throw new Exception("The index could not be retrieved by KBox, check your disk space or internet connection.", e);
		}
		Context context;
		context = new Context("exampleContext.properties");
		context.setKbRootPath(kb);
		RDFIndex index = new RDFIndex(context.getMultiIndexDirPrefix(),
				context);		
		return index;
	}
	
	/**
	 * 
	 * @param qaldFile
	 * @return
	 * @throws Exception
	 */
	public static Dataset generateAnswers(File qaldFile) throws Exception {
		RDFIndex index = QueryExpansionBenchmark.getIndex();
		Dataset dataset = QALDBenchmark.deserialize(qaldFile);
		List<Question> questions = dataset.getQuestion();		
		Dataset generatedDataset = new Dataset();
		generatedDataset.setId(dataset.getId());
		for (Question question : questions) {
			for (org.aksw.openqa.qald.schema.String string : question
					.getString()) {
				if (string.getLang().equals("en")) {
					Question q = new Question();
					q.setAggregation(question.getAggregation());
					q.setAnswertype(question.getAnswertype());
					q.setOnlydbo(question.getOnlydbo());
					q.setId(question.getId());
					q.setQuery(question.getQuery());					
					String query = string.getValue();
					Answers answers = new Answers();
					List<String> result = QueryExpansionBenchmark.query(index, query);
					for(String resourceURI : result) {
						Answer a = new Answer();
						a.setUri(resourceURI);
						answers.getAnswer().add(a);
					}
					q.setAnswers(answers);
					generatedDataset.getQuestion().add(q);
				}
			}
		}
		return generatedDataset;
	}
	
	/**
	 * Perform the given query using the given index.
	 * 
	 * @param index the RDFIndex that will be queried.
	 * @param query the query.
	 * @return a list of resource returned by the given index. 
	 * @throws Exception if some exception occurs while accessing the index.
	 */
	public static List<String> query(RDFIndex index, String query) throws Exception {
		List<String> result = new ArrayList<String>();
		query = query.replace("$", "");
		query = query.replace(".", "");
		query = query.replace("?", "");
		query = query.replace("'s", "");
		query = query.replace("'", "");
		query = query.replace("-", " ");
		query = query.replace(",", "");
		query = query.replace(" ", " | ");
		
		Query parsedQuery = index.getParser().parse(query);
		Querier querier = new Querier();
		QueryResult queryResult = querier.doQuery(index,
				parsedQuery,
				0,
				100,
				false,
				0);
		
		List<QueryResultItem> resultItems = queryResult.getResultItems();
		for (QueryResultItem queryResultItem : resultItems) {
			result.add(queryResultItem.getSubject());
		}
		return result;
	}
	
	/**
	 * Evaluates a given Dataset.
	 *   
	 * @param dataset the Dataset that is going to be evaluated.
	 * 
	 * @return a QALDBenchmarkResult for the given Dataset.
	 * @throws Exception if any error occurs during the evaluation.
	 */
	public static QALDBenchmarkResult benchmark(Dataset dataset) throws Exception {
		URL QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/groundtruth/qald-4_multilingual_test_questions_glimmer_answer.xml");
		Dataset glimmerDataset = QALDBenchmark.deserialize(new File(QALDFileURL.toURI()));
		return QALDBenchmark.evaluate(dataset, glimmerDataset);
	}
	
	/**
	 * Evaluates a given question limiting to the given top 
	 * resources of the question in the benchmark
	 *   
	 * @param questionID the id of the Question that is going to be evaluated.
	 * @param resources the resources returned as answer for the given question.
	 * 
	 * @return a QuestionResult as result of the benchmark
	 * @throws Exception if the Question id is null or can not be found 
	 * in the Dataset.
	 */
	public static QuestionResult benchmark(String questionID, 
			List<String> resources) throws Exception {		
		return benchmark(questionID, resources, 100);
	}
	
	/**
	 * Evaluates a given question limiting to the given top 
	 * resources of the question in the benchmark
	 *   
	 * @param questionID the id of the question that is going to be evaluated.
	 * @param resources the resources returned as answer for the given question  
	 * @param top the number of top resources from the question benchmark that is 
	 * going to be compared.
	 * 
	 * @return a QuestionResult as result of the benchmark
	 * @throws Exception if the Question id is null or can not be found 
	 * in the Dataset.
	 */
	public static QuestionResult benchmark(String questionID, 
			List<String> resources, 
			int top) throws Exception {
		Question q = new Question();
		q.setId(questionID);
		for(String resource : resources) {
			Answer a = new Answer();
			a.setUri(resource);
			q.getAnswers().getAnswer().add(a);
		}
		return benchmark(q, top);
	}
	
	/**
	 * Evaluates a given question limiting to the given top 
	 * resources of the question in the benchmark
	 *   
	 * @param question the question that is going to be evaluated.
	 * @param top the number of top resources from the question benchmark that is 
	 * going to be compared.
	 * @return a QuestionResult as result of the benchmark
	 * @throws Exception if the Question id is null or can not be found 
	 * in the Dataset.
	 */
	public static QuestionResult benchmark(Question question, int top) throws Exception {
		if(question.getId() == null) {
			throw new Exception("Question id can not be null.");
		}
		if(top > 100) {
			throw new Exception("Top can not be bigger than 100.");
		}
		URL QALDFileURL = BM25FQALDEvaluation.class
				.getResource("/org/aksw/queryexpansion/benchmark/qald/qald-4_multilingual_BM25F_answer.xml");
		Dataset glimmerDataset = QALDBenchmark.deserialize(new File(QALDFileURL.toURI()));
		Question expected = getQuestion(glimmerDataset, question.getId());
		if(expected == null) {
			throw new Exception("No question in the dataset match the given id: " + question.getId());
		}
		List<Answer> answerList = expected.getAnswers().getAnswer();
		answerList = answerList.subList(0, top);
		expected.getAnswers().getAnswer().clear();
		expected.getAnswers().getAnswer().addAll(answerList);
		return QALDBenchmark.evaluate(question, expected);
	}
	
	/**
	 * Get a Question with the given id.
	 * 
	 * @param dataset the dataset containing the Question.
	 * @param id the id of the Question that is going to be search in the given dataset.
	 * @return a Question with the given id.
	 */
	public static Question getQuestion(Dataset dataset, String id) {
		for(Question q : dataset.getQuestion()) {
			if(q.getId().equals(id)) {
				return q;
			}
		}
		return null;
	}

}
