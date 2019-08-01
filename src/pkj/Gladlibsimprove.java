package pkj;

import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.HashMap;

public class Gladlibsimprove {
	

	private HashMap<String,ArrayList<String>> mymap;
	private ArrayList<String> trackList;
	private ArrayList<String> categoryList;
	
	private String directorysource = "C:\\Users\\lenovo\\Documents\\Downloads\\Compressed\\Java2\\Week1\\Gladlibs\\data";
	private Random rm;
	private int counter;
	
	public Gladlibsimprove() {
		try {
		initializeFromSource(directorysource);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		rm = new Random();
	}
	
	public Gladlibsimprove(String source) {
		try {
			initializeFromSource(source);
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		rm = new Random();
	}
	
	private void initializeFromSource(String source) throws IOException {
		mymap = new HashMap<String,ArrayList<String>>();
		String[] categories = new String[]{"adjective","animal","color","country","fruit","name","noun","timeframe","verb"};
		for(String s:categories) {
			ArrayList<String> categ = readIt(source + "//" + s + ".txt");
			mymap.put(s,categ);
		}
		trackList = new ArrayList<String>();
		categoryList = new ArrayList<String>();
		counter = 0;
	}
	
	private ArrayList<String> readIt(String source) throws IOException{
		ArrayList<String> al = new ArrayList<String>();
		Scanner sc = null;
		if(source.startsWith("http")) {
			URL Url = new URL(source);
			 sc = new Scanner(new InputStreamReader(Url.openStream()));
		}
		else {
		File f = new File(source);
			sc = new Scanner(f);
		}
		while(sc.hasNext()) {
			al.add(sc.next());
		}
		sc.close();
		return al;
	}
	
	public void makeStory() throws IOException {
		trackList.clear();
		String story = fromTemplate("madtemplate3.txt");
		printOut(story, 60);
		
		System.out.println("total number of words in all files: " + totalWordsInMap());
		System.out.println("total number of words in the categories used: " + totalWordsConsidered());
		
	}
	
	private String randomFrom(ArrayList<String> source) {
		int random = rm.nextInt(source.size());
		return source.get(random);
	}
	
	private String getSubstitute(String label) {
		if(label.equalsIgnoreCase("number")) {
			int random = rm.nextInt(151);
			String randoms = "" + random;
			return randoms;
		}
		if(mymap.containsKey(label)) {
			return randomFrom(mymap.get(label));
		}
		return "**UNKNOWN**";
	}
	
	private String processWord(String w) {
		int first = w.indexOf("<");
		int last = w.indexOf(">" , first);
		if( first == -1 || last == -1) {
			return w;
		}
		
		String category = w.substring(first+1 , last);
		counter++;
		if(!categoryList.contains(category))
		categoryList.add(category);
		
		String sub = getSubstitute(w.substring(first+1 , last));
		while(trackList.indexOf(sub) != -1)
			 sub = getSubstitute(w.substring(first+1 , last));
		trackList.add(sub);
		return sub;
		
	}
	
	private String fromTemplate(String source) throws IOException {
		StringBuilder sb = new StringBuilder();
		Scanner sc = null;
		if(source.startsWith("http")) {
			URL Url = new URL(source);
			 sc = new Scanner(new InputStreamReader(Url.openStream()));
		}
		else {
		File f = new File(source);
			sc = new Scanner(f);
		}
		while(sc.hasNext()) {
			sb.append(processWord(sc.next()) + " ");
		}
		sc.close();
		return sb.toString();
	}
	
	private void printOut(String s,int lineWidth) {
		int charswritten = 0;
		for(String w: s.split("\\s+")) {
			if(charswritten + w.length() > lineWidth) {
				System.out.println();
				charswritten = 0;
			}
			System.out.print(w + " ");
			charswritten = charswritten + w.length() + 1;
		}
		System.out.println();
		System.out.println("Number of words replaced is: " + counter);
	}
	
	private int totalWordsInMap(){
		
		ArrayList<String> allwords = new ArrayList<String>();
		for(String s: mymap.keySet()) {
			ArrayList<String> al = mymap.get(s);
			for(String w : al) {
				allwords.add(w);
			}
		}
		return allwords.size();
	}
	
	private int totalWordsConsidered(){
		int counter=0;
		for(String s: mymap.keySet()) {
			if(categoryList.contains(s)) {
				ArrayList<String> al = mymap.get(s);
				counter = counter + al.size();
			}
		}
		return counter;
	}
}
