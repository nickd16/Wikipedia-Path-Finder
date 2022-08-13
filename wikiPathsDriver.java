import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import course3.Job;

public class wikiPathsDriver {

	public static void main(String[] args) {
		
		//Code to read in starting page and target page
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter starting Wikipedia page: ");
		String page = input.next();
		System.out.println("Enter target Wikipedia page: ");
		String target = input.next();
		target = target.substring(0,1).toUpperCase() + target.substring(1,target.length());
		target = "https://en.wikipedia.org/wiki/"+target;
		String start = "https://en.wikipedia.org/wiki/"+page;
		List<String> layerone = wikiPaths.firstLayer(start, target);
		List<wikiPaths> bots = new ArrayList<>();
		
		//create 15 randomized threads starting from first layer of links from the original wikipedia link
		
		int increasefactor = layerone.size()/15; 
		bots.add(new wikiPaths(start, target, start));
		for(int i = 0; i < layerone.size(); i = i+increasefactor) {
			bots.add(new wikiPaths(layerone.get(i), target, start));
		}
		
		for(wikiPaths w : bots) {
			try {
				w.getThread().join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
		


	
	
}
