package test;

import java.util.ArrayList;
import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.relationship.RelationshipFinder;
import net.sf.extjwnl.data.relationship.RelationshipList;
import net.sf.extjwnl.dictionary.Dictionary;

public class WordRelationshipFinder {
	public Dictionary dic;
	private static int MAX_DEPTH = 5;
	private static WordRelationshipFinder instance = null;
	private WordRelationshipFinder() throws JWNLException{
		dic = Dictionary.getInstance(Test.class.getResourceAsStream("properties.xml"));
	}
	
	public static WordRelationshipFinder getInstance() throws JWNLException{
		if (instance == null) {
			instance = new WordRelationshipFinder();
		}
		
		return instance;
	}
	public int calculateRelationshipCount(List<String> nounPhrase) throws JWNLException, CloneNotSupportedException{
		int relationshipCount = 0;
		for (int i = 0; i < nounPhrase.size(); i++){
			IndexWord target = dic.lookupIndexWord(POS.NOUN, nounPhrase.get(i));
			List<Synset> targetSynset = target.getSenses();
			for (int j = i+1; j < nounPhrase.size(); j++){
				boolean flag = false;

		        IndexWord item = dic.lookupIndexWord(POS.NOUN, nounPhrase.get(j));    
		        List<Synset> itemSynset = item.getSenses();
		        
		        if (relatedIsA(targetSynset, itemSynset)) {
					relationshipCount++;
				}
			}
		}
		return relationshipCount;
	}
	
	public List<String> findAllRelationship(List<String> nounPhrase) throws JWNLException, CloneNotSupportedException{
		List<String> npRelationship = new ArrayList<String>();
		for (int i = 0; i < nounPhrase.size(); i++){
			IndexWord target = dic.lookupIndexWord(POS.NOUN, nounPhrase.get(i));
			List<Synset> targetSynset = target.getSenses();
			for (int j = i+1; j < nounPhrase.size(); j++){
				boolean flag = false;

		        IndexWord item = dic.lookupIndexWord(POS.NOUN, nounPhrase.get(j));    
		        List<Synset> itemSynset = item.getSenses();
		        
		        if (relatedIsA(targetSynset, itemSynset)) {
		        	npRelationship.add(nounPhrase.get(i) + "#" + nounPhrase.get(j));
				}
			}
		}
		return npRelationship;
	}
	
	public boolean relatedIsA(List<Synset> targetSynset, List<Synset> itemSynset) throws CloneNotSupportedException, JWNLException{
		for (int m = 0; m < targetSynset.size(); m++) {
        	for (int n = 0; n < itemSynset.size(); n++) {
        		RelationshipList result = RelationshipFinder.findRelationships(targetSynset.get(m), itemSynset.get(n), PointerType.HYPERNYM);
        		if (result.getShallowest() != null && result.getShallowest().getDepth() <= MAX_DEPTH) {
					return true;
				}
        		result = RelationshipFinder.findRelationships(targetSynset.get(m), itemSynset.get(n), PointerType.HYPONYM);
        		if (result.getShallowest() != null && result.getShallowest().getDepth() <= MAX_DEPTH) {
        			return true;
				}
        	}
        }
		return false;
	}
	
}
