import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.lang.*;

public class Text_Based_Search {
	HashedDictionary<String, String, Double, String, String> Hd1;  
	HashedDictionary<String, String, Double, String, String> Hd2;
	HashedDictionary<String, String, Double, String, String> Hd3;
	HashedDictionary<String, String, Double, String, String> Hd4;
	HashedDictionary<String, String, Double, String, String> Hd5;  
	HashedDictionary<String, String, Double, String, String> Hd6;
	HashedDictionary<String, String, Double, String, String> Hd7;
	HashedDictionary<String, String, Double, String, String> Hd8;
	File mainFile;
	File[] list;
	
	Text_Based_Search(){
		Hd1 = new HashedDictionary<String, String, Double, String, String>();
		Hd2 = new HashedDictionary<String, String, Double, String, String>();
		Hd3 = new HashedDictionary<String, String, Double, String, String>();
		Hd4 = new HashedDictionary<String, String, Double, String, String>();
		Hd5 = new HashedDictionary<String, String, Double, String, String>();
		Hd6 = new HashedDictionary<String, String, Double, String, String>();
		Hd7 = new HashedDictionary<String, String, Double, String, String>();
		Hd8 = new HashedDictionary<String, String, Double, String, String>();
		
		fillingTable();
	}	
	
	private void fillingTable() {
		mainFile = new File("sport");
		list = mainFile.listFiles(); 
		for ( int i = 0; i < 100; i++) {
			String[] words = readFile(list[i].toString(), "add");
			for ( int j = 0; j < words.length; j++) {
				if(words[j] != null && words[j] != " ") {
					Hd1.add_LP(words[j], 0.5, "SSF", i + 1);
					Hd2.add_LP(words[j], 0.8, "SSF", i + 1);
					Hd3.add_LP(words[j], 0.5, "PAF", i + 1);
					Hd4.add_LP(words[j], 0.8, "PAF", i + 1);
					Hd5.add_DH(words[j], 0.5, "SSF", i + 1);
					Hd6.add_DH(words[j], 0.8, "SSF", i + 1);
					Hd7.add_DH(words[j], 0.5, "PAF", i + 1);
					Hd8.add_DH(words[j], 0.8, "PAF", i + 1);
				}
			}
		}
		
		String[] words = readFile("search.txt", "search");
		int collision1 = search_LP(Hd1, words, "SSF");
		int collision2 = search_LP(Hd2, words, "SSF");
		int collision3 = search_LP(Hd3, words, "PAF");
		int collision4 = search_LP(Hd4, words, "PAF");
		int collision5 = search_DH(Hd5, words, "SSF");
		int collision6 = search_DH(Hd6, words, "SSF");
		int collision7 = search_DH(Hd7, words, "PAF");
		int collision8 = search_DH(Hd8, words, "PAF");
		
		System.out.println("0,5  SSF   LP "+collision1); 
		System.out.println("0,8  SSF   LP "+collision2); 
		System.out.println("0,5  PAF   LP "+collision3); 
		System.out.println("0,8  PAF   LP "+collision4); 
		System.out.println("0,5  SSF   DH "+collision5); 
		System.out.println("0,8  SSF   DH "+collision6); 
		System.out.println("0,5  PAF   DH "+collision7); 
		System.out.println("0,8  PAF   DH "+collision8); 
		 
	    
	}
	
	public  String[] readFile(String fileName, String operation) {
		String[] words = new String[10000];
		try {
			File file = new File(fileName);
			Scanner in = new Scanner(file);
			int counter = 0;
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] tempWords = new String[10000];
				if (line != null) {
					line = line.replaceAll("\\p{Punct}", " ").toLowerCase(Locale.ENGLISH);  //deleting punctuations and
					line = line.replaceAll("[0-9]","");                                     //lowers capital letters
					tempWords = line.split(" ");  //line splits into words                 
					for(int i = 0; i < tempWords.length; i++) {
						words[counter] = tempWords[i];  //put words into an array
						counter++;
					}
				}
			}
			in.close();
			if(operation.equalsIgnoreCase("add"))
			words = deleteStopWords(words); //deletes stop words
			
			return words;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public  String[] deleteStopWords(String[] words) throws FileNotFoundException {
		File file =new File("stop_words_en.txt");  //read stop words file
		Scanner in = new Scanner(file);
		String[] tempWords = new String[2000];
		int counter = 0;
		while(in.hasNextLine()) {
			String line = in.nextLine();
			if (line != null) {
				line = line.replaceAll("\\p{Punct}", " ").toLowerCase(Locale.ENGLISH);  //deleting punctuations and 
				tempWords[counter] = line;                                            //lowers capital letters
				counter++;
			}
		}
		for(int i = 0; i < words.length;i++) {
			for(int j = 0; j <= counter; j++) {
				if(words[i] != null) {
					if(words[i].equals(tempWords[j])) {  //find stop words and delete
						words[i] = " ";
					}
				}
			}
		}
		in.close();
		return words;
	}
	
	private int search_LP(HashedDictionary<String, String, Double, String, String> Hd, String[] words, String calculation) {
		int collision = 0;
		for ( int i = 0; i < words.length; i++) {
			if(words[i] != null) {
				Result result = Hd.contains_LP(words[i], calculation);
				collision += result.getCollision();
			}
		}
		return collision;
	}
	
	private int search_DH(HashedDictionary<String, String, Double, String, String> Hd, String[] words, String calculation) {
		int collision = 0;
		for ( int i = 0; i < words.length; i++) {
			if(words[i] != null) {
				Result result = Hd.contains_DH(words[i], calculation);
				collision += result.getCollision();
			}
		}
		return collision;
	}
	
}

