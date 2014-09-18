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
import net.sf.extjwnl.dictionary.Dictionary;

public class CheckNP {
	
	/**
	 * @param args
	 * @throws JWNLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws JWNLException, FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Dictionary dic = Dictionary.getInstance(CheckNP.class.getResourceAsStream("properties.xml"));
		List<String> npList = loadWholeList();
		String intersectIndexFile = "/home/bigstone/workspace/WordNetUse/intersectIndex.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(intersectIndexFile)));
		
		for (String np : npList) {
			IndexWord tmp = dic.lookupIndexWord(POS.NOUN, np);
			
			if(tmp != null){
				writer.write(np + "\n");
			}
			
		}
		writer.flush();
        writer.close();
	}
//	public static List<String> loadWholeList() throws FileNotFoundException, IOException {
//		String allNounPhraseFilePath = "/home/bigstone/workspace/WordNetUse/myNP.txt";
//        InputStream fis;
//        BufferedReader br;
//        String line;
//        List<String> npList = new ArrayList<String>();
//        fis = new FileInputStream(allNounPhraseFilePath);
//        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
//        int count = 0;
//        while ((line = br.readLine()) != null) {
//            if (count++ % 1000 == 0) {
//                System.out.println(count / 1000);
//            }
//
//            line = line.trim();
//            npList.add(line);
//        }
//
//        br.close();
//        br = null;
//        fis = null;
//        
//        return npList;
//    }
	public static List<String> loadWholeList() throws FileNotFoundException, IOException {
		String allNounPhraseFilePath = "/home/bigstone/workspace/WordNetUse/index_np.txt";
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
            String[] tmp = line.split("\t");
            npList.add(tmp[1]);
        }

        br.close();
        br = null;
        fis = null;
        return npList;
    }
}
