package org.aksw.queryexpansion.benchmark.answergeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.aksw.openqa.qald.QALDBenchmark;
import org.aksw.openqa.qald.schema.Dataset;
import org.aksw.openqa.qald.schema.Question;

public class ProfilesToBenchmark {
	public static void main(String[] args) throws Exception {
		URL profileDirURL = ProfilesToBenchmark.class.getResource("/org/aksw/queryexpansion/benchmark/profiles");
		List<String> diffWords = new ArrayList<String>();
		List<String> diffOrder = new ArrayList<String>();
		List<String> diffWithTypos = new ArrayList<String>();
		List<String> diffInflec = new ArrayList<String>();
		List<String> all = new ArrayList<String>();
		
		File profileDir = new File(profileDirURL.toURI());
		for(File profile : profileDir.listFiles()) {
			List<String> sentences;
			try {
				sentences = getSentences(profile);
				diffWords.add(sentences.get(0));
				diffOrder.add(sentences.get(1));
				diffWithTypos.add(sentences.get(2));
				diffInflec.add(sentences.get(3));
				all.add(sentences.get(4));
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		File diffWordsBenchFile = new File("qald-4_multilingual_test_questions_diffWords.xml");
		serialize(diffWordsBenchFile, diffWords);
		File diffOrderBenchFile = new File("qald-4_multilingual_test_questions_diffOrder.xml");
		serialize(diffOrderBenchFile, diffOrder);
		File withTyposBenchFile = new File("qald-4_multilingual_test_questions_withTypos.xml");
		serialize(withTyposBenchFile, diffWithTypos);
		File withInflecBenchFile = new File("qald-4_multilingual_test_questions_withInflec.xml");
		serialize(withInflecBenchFile, diffInflec);
		File allBenchFile = new File("qald-4_multilingual_test_questions_all.xml");
		serialize(allBenchFile, all);
	}

	private static void serialize(File benchFile,
			List<String> diffWords) throws Exception {
		URL QALDFileURL = ProfilesToBenchmark.class
				.getResource("/org/aksw/queryexpansion/benchmark/qald/qald-4_multilingual_test_questions.xml");
		Dataset dataset = QALDBenchmark.deserialize(new File(QALDFileURL
				.toURI()));
		for(Question q : dataset.getQuestion()) {
			int index = dataset.getQuestion().indexOf(q);
			q.getKeywords().clear();
			q.getString().clear();
			org.aksw.openqa.qald.schema.String questionString = new org.aksw.openqa.qald.schema.String();
			questionString.setLang("en");
			questionString.setValue(diffWords.get(index));
			q.getString().add(questionString);
		}
		
		QALDBenchmark.serialize(dataset, benchFile);
	}

	private static List<String> getSentences(File profile) throws IOException {
		List<String> entities = new ArrayList<String>();
		FileReader fr = new FileReader(profile);
		BufferedReader br = new BufferedReader(fr);
		
		next(br);next(br);next(br);
		entities.add(br.readLine());
		next(br);
		entities.add(br.readLine());
		next(br);
		entities.add(br.readLine());
		next(br);
		entities.add(br.readLine());
		next(br);
		entities.add(br.readLine());
				
		br.close();		
		return entities;
	}
	
	private static void next(BufferedReader br) throws IOException {
		String line = null;
		while ((line = br.readLine()) != null && !line.contains("///////////")) {}
	}
}
