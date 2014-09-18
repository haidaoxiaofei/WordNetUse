package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.relationship.RelationshipFinder;
import net.sf.extjwnl.data.relationship.RelationshipList;
import net.sf.extjwnl.dictionary.Dictionary;

/**
 * Tests RelationshipList.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class Test {

    private static Dictionary dic;

    public Test() throws FileNotFoundException, JWNLException {
        dic = Dictionary.getInstance(Test.class.getResourceAsStream("properties.xml"));
    }


    public void testGetShallowest() throws JWNLException, CloneNotSupportedException, FileNotFoundException, IOException {
//        Word sW = dic.getWordBySenseKey("dog%1:05:00::");
//        IndexWord water = dic.lookupIndexWord(POS.NOUN, "water");
//        IndexWord watermelon = dic.lookupIndexWord(POS.NOUN, "watermelon");
//        List<Synset> waterSynset = water.getSenses();
//        List<Synset> watermelonSynset = watermelon.getSenses();
//        
//        
//        int index = RelationshipFinder.getImmediateRelationship(water, watermelon);
//        Word tW = dic.getWordBySenseKey("man%1:05:00::");
//       
//        RelationshipList result = RelationshipFinder.findRelationships(sW.getSynset(), tW.getSynset(), PointerType.HYPERNYM);
//        System.out.print("Deepest!");
//        System.out.print(result.getDeepest());
//        System.out.print("Shallowest!");
//        System.out.print(result.getShallowest());
        
        
//        List<String> nounPhrase = new ArrayList<String>();
//        nounPhrase.add("Mac");
//        nounPhrase.add("BI");
//        nounPhrase.add("bandwidth");
//        nounPhrase.add("priority");
//        nounPhrase.add("Pareto-optimal");
//        nounPhrase.add("e-government");
//        nounPhrase.add("electron");
        List<String> nounPhrase = loadWholeList();
        WordRelationshipFinder wordRelationshipFinder = WordRelationshipFinder.getInstance();
//        int count = wordRelationshipFinder.calculateRelationshipCount(nounPhrase);
//        System.out.println(count);
        List<String> pairs = wordRelationshipFinder.findAllRelationship(nounPhrase);
//        System.out.println(pairs);
        
        String intersectIndexFile = "/home/bigstone/workspace/WordNetUse/pairs.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(intersectIndexFile)));
		
		for (String np : pairs) {
			writer.write(np + "\n");
		}
		writer.flush();
        writer.close();
    }
    public static List<String> loadWholeList() throws FileNotFoundException, IOException {
		String allNounPhraseFilePath = "/home/bigstone/workspace/WordNetUse/intersectIndex.txt";
        InputStream fis;
        BufferedReader br;
        String line;
        List<String> npList = new ArrayList<String>();
        fis = new FileInputStream(allNounPhraseFilePath);
        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count++ % 1000 == 0) {
                System.out.println(count / 1000);
            }

            line = line.trim();
            npList.add(line);
        }

        br.close();
        br = null;
        fis = null;
        
        return npList;
    }
    public static void main(String args[]) throws JWNLException, CloneNotSupportedException, IOException{
    	Test t = new Test();
    	t.testGetShallowest();
    }
}