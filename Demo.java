package fileManipulation;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class Demo {
	
	static TreeMap<String, Integer> wordsCounter = new TreeMap<String, Integer>();
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		File vIM1 = new File("Voina_i_mir1.txt");
		File vIM2 = new File("Voina_i_mir2.txt");
		
		try {
			vIM1.createNewFile();
			vIM2.createNewFile();
		} 
		catch (IOException e) {
		}
		
		try {
			counter(vIM1).join();
			counter(vIM2).join();
		} catch (InterruptedException e) {
			System.out.println("Process was interrupted");
		}
		
		mostCommonWord();
		System.out.println("War has " + wordsCounter.get("война") + " encounters in the book.");
		System.out.println("Peace has " + wordsCounter.get("мир") + " encounters in the book.");
		
		System.out.println("App took " + ((System.currentTimeMillis()-start)/1000) + " seconds to run.");

	}
	
	private static Thread counter(File file){
		Thread thr = new Thread(){
			public void run() {
				
				
				try(FileReader fr = new FileReader(file); Scanner sc = new Scanner(fr)) {
					int count = 0;
					while(sc.hasNext()){
						count++;
						String temp = sc.next().toLowerCase();
						temp = removeExtras(temp);
						if(wordsCounter.containsKey(temp)){
							int tmp = wordsCounter.get(temp);
							tmp++;
							wordsCounter.put(temp, tmp);
						}
						else{
							wordsCounter.put(temp, 1);
						}
					}
					
					System.out.println("Number of words " + count);
				}
				catch (IOException e) {
					System.out.println("Error with file...");
				}
			}
		};
		
		thr.start();
		return thr;
	}
	
	private static String removeExtras(String word){
		char[] array = word.toCharArray();
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i<array.length; i++){
			if(Character.isAlphabetic(array[i])){
				sb.append(array[i]);
			}
			
		}
		return sb.toString();
	}

	private static void mostCommonWord(){
		Iterator<Entry<String, Integer>> it = wordsCounter.entrySet().iterator();
		String mostCommon = "";
		int count = 0;
		while(it.hasNext()){
			String tempWord = it.next().getKey();
			int tempInt = wordsCounter.get(tempWord);
			if(tempInt>count){
				count = tempInt;
				mostCommon = tempWord;
			}
		}
		System.out.println("The most common word is " + mostCommon + " = " + count + " times.");
	}

}
