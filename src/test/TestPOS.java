package test;

import java.util.Iterator;
import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

public class TestPOS {

	/**
	 * @param args
	 * @throws JWNLException 
	 */
	public static void main(String[] args) throws JWNLException {
//		for (POS c : POS.values()){
//			System.out.println(c);
//		}
//		System.out.println(POS.valueOf("NOUN"));
		
		
		Dictionary dic = Dictionary.getInstance(TestPOS.class.getResourceAsStream("properties.xml"));
//		Synset hyponym = new Synset(dic, POS.valueOf("NOUN"),1);
		Iterator<Synset> nouns = dic.getSynsetIterator(POS.NOUN);
		while(nouns.hasNext()){
			List<Word> words = nouns.next().getWords();
			
			for (Word word : words) {
				System.out.println(word.getLemma());
			}
		}
	
	}

}
