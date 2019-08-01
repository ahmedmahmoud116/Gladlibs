package pkj;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class MethodsHelper {
	
	private ArrayList<String> adjectiveList;
	private ArrayList<String> animalList;
	private ArrayList<String> nounList;
	private ArrayList<String> colorList;
	private ArrayList<String> countryList;
	private ArrayList<String> fruitList;
	private ArrayList<String> nameList;
	private ArrayList<String> verbList;
	private ArrayList<String> timeframeList;
	private ArrayList<String> trackList;
	
	private String directorysource = "C:\\Users\\lenovo\\Documents\\Downloads\\Compressed\\Java2\\Week1\\Gladlibs\\datalong";
	private Random rm;
	private int counter;
	
	public MethodsHelper() {
		try {
		initializeFromSource(directorysource);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		rm = new Random();
	}
	
	public MethodsHelper(String source) {
		try {
			initializeFromSource(source);
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		rm = new Random();
	}
	
	private void initializeFromSource(String source) throws IOException {
		adjectiveList = readIt(source + "//adjective.txt");
		animalList 	  = readIt(source + "//animal.txt");
		colorList 	  = readIt(source + "//color.txt");
		countryList   = readIt(source + "//country.txt");
		fruitList 	  = readIt(source + "//fruit.txt");
		nameList 	  = readIt(source + "//name.txt");
		nounList 	  = readIt(source + "//noun.txt");
		timeframeList = readIt(source + "//timeframe.txt");
		verbList 	  = readIt(source + "//verb.txt");
		trackList = new ArrayList<String>();
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
		String story = fromTemplate("madtemplate2.txt");
		printOut(story, 60);
	}
	
	private String randomFrom(ArrayList<String> source) {
		int random = rm.nextInt(source.size());
		return source.get(random);
	}
	
	private String getSubstitute(String label) {
		if(label.equalsIgnoreCase("country")) {
			String random =  randomFrom(countryList);
			return random;
		}
		if(label.equalsIgnoreCase("adjective")) {
			String random =  randomFrom(adjectiveList);
			return random;
		}
		if(label.equalsIgnoreCase("animal")) {
			String random =  randomFrom(animalList);
			return random;
		}
		if(label.equalsIgnoreCase("color")) {
			String random =  randomFrom(colorList);
			return random;
		}
		if(label.equalsIgnoreCase("fruit")) {
			String random =  randomFrom(fruitList);
			return random;
		}
		if(label.equalsIgnoreCase("name")) {
			String random =  randomFrom(nameList);
			return random;
		}
		if(label.equalsIgnoreCase("noun")) {
			String random =  randomFrom(nounList);
			return random;
		}
		if(label.equalsIgnoreCase("timeframe")) {
			String random =  randomFrom(timeframeList);
			return random;
		}
		if(label.equalsIgnoreCase("verb")) {
			String random =  randomFrom(verbList);
			return random;
		}
		if(label.equalsIgnoreCase("number")) {
			int random = rm.nextInt(151);
			String randoms = "" + random;
			return randoms;
		}
		return "**UNKNOWN**";
	}
	
	private String processWord(String w) {
		int first = w.indexOf("<");
		int last = w.indexOf(">" , first);
		if( first == -1 || last == -1) {
			return w;
		}
		counter++;
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
}
