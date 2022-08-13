import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.*;
public class wikiPaths implements Runnable {

	private HashMap<String, String> visited = new HashMap<>();
	private Thread thread;
	private String first_link;
	private static String original, target;
	private static int count;
	private static double start,finish;
	private static boolean keepCrawling = true;
	
	//Constructor to take in starting link, target link, and original 
	//Initializes global variables and start running bfs from a new thread
	
	public wikiPaths(String link, String target, String original) {
		start = System.nanoTime();
		first_link = link;
		thread = new Thread(this);
		thread.start();
		this.target = target;
		this.original = original;
	}
	
	//From java's runnable interface to instruct each thread to enter crawl function 
	
	public void run() {
		while(keepCrawling) {
			crawl(first_link, visited);
		}
	}
	
	/*Function to get all links from the original page
	  A random sampling of these links are taken and serve as the route of the 15 threads that crawl the rest
	Of the wiki */
	
	public static List<String> firstLayer(String url, String target) {
		start = System.nanoTime();
		List<String> layerOne = new ArrayList<>();
		Document doc = request(url);
		for(Element link : doc.select("a[href~=\\/wiki\\/([a-zA-Z0-9_/&?]+)$]")) {
			String next_link = link.absUrl("href");
			if(next_link.equals(target)) {
				System.out.println(url.substring(30, url.length()) + " -> "+target.substring(30, target.length()));
				double finish = System.nanoTime();
				System.out.println((finish-start)/1000000000 + " seconds");
				System.exit(0);
			}
			layerOne.add(next_link);
		}
		return layerOne;
	}
	
	// BFS algorithm to crawl the web using jsoup
	
	private static HashMap<String, String> crawl(String url, HashMap<String, String> visited) {
		Queue<String> links = new LinkedList<>();
		Queue<String> temp = new LinkedList<>();
		links.offer(url);
		if(visited.get(url) == null) 
			visited.put(url, original);
		while(!links.isEmpty() && keepCrawling) {
			String current_link = links.poll();
			Document doc = request(current_link);
			if(doc != null) {
				for(Element link : doc.select("a[href~=\\/wiki\\/([a-zA-Z0-9_/&?]+)$]")) {
					String next_link = link.absUrl("href");
					if(visited.get(next_link) == null && !next_link.equals(current_link) && 
							(visited.get(current_link) != null && !visited.get(current_link).equals(next_link)) 
							&& next_link.substring(8,10).equals("en")) {
						visited.put(next_link, current_link);
						temp.offer(next_link);
						count++;
						if(count % 100000 == 0) 
							System.out.println("Pages crawled: " +count);
						if(next_link.equals(target)) {
							keepCrawling = false;
							double finish = System.nanoTime();
							String time = String.valueOf((finish-start)/1000000000);
							visited.put("time", time);
							HashMap<String, String> copy = new HashMap<>(visited);
							reconstructPath(copy, target);
						}
					}
				}
			}
			if(links.isEmpty()) {
				links.addAll(temp);
				temp.clear();
			}
		}
		return null;
	}
	
	//Helper function to note if a given url can be accessed
	
	private static Document request(String url) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if(con.response().statusCode() == 200) {
				return doc;
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	/* Function to reconstruct the path found by the algorithm by going backwards through our visited hashmap.
	 * Because our visited hashmap links a new link to the link it came from we can simply move backwards
	 * and create our final path. */
	
	public static void reconstructPath(HashMap<String, String> visited, String found) {
		System.out.println("Found path in: " +visited.get("time") + " seconds");
		Stack<String> path = new Stack<>();
		String next = visited.get(found);
		path.push(next.substring(30, next.length()));
		while(visited.get(next) != null) {
			next = visited.get(next);
			path.push(next.substring(30, next.length()));
			if(next.equals(original))
				break;
		}
		while(!path.isEmpty()) {
			System.out.print(path.pop() + " -> ");
		}
		System.out.println(found.substring(30, found.length()));
		System.exit(1);
	}
	

}