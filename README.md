# Wikipedia-Path-Finder
Finds path from wiki page to another wiki page using multi-threading and jsoup ->

How it works:
The user may enter a soruce wikipedia page and a target wikipedia page. The first step of the process is initializing the threads to crawl through
wikipedia. From the initial source wikipedia page there is a small pre-processing step in which we get all of the pages found from the source page and we
assign a random sampling of at most 15 of the different pages and use them as their own independent threads. Next, the theads use the crawl method in parallel
and search for a path using breadth first search. The path is cache in a visitied hashmap and finally when a path is found we calculate the time it took and
go through our reconstruct method, which backtracks through the map of cached direct edges to print the final output.

Note: 
Sometimes the output may not result in the shortest-path, which is why I titled the project a path-finder, not a shortest-path finder. I personally have not run into
a time where the chosen path has not been a shortest-path, but by the nature of how the algorithm works it is possible that the answer is not a shortest path.

My idea behind the project was initially to build a shortest-path finder. However, I did notice a lot of people have already created a shortest-path finder and I did not want my project to be the same thing everyone else has done. I decided on this idea of finding a path using multi-threading to instead center my project around speed in how fast I can find a path. And after running my algorithm, it is clear that it is much faster in finding a path. To find an optimal shortest-path, you have to breadth first search from the starting position with only the main thread and that process is extremely slow and there is not much you can do about that. It can take over 40 seconds to find 4-hop paths doing it that way. The algorithm I have created finds the shortest path most of the time and can find a lot of 4+ hop paths in under 10 seconds. I will show a few examples below. 

I hope you like the project!
Also if you would like to run the code yourself make sure you have the jsoup.jar file in your classpath

![image](https://user-images.githubusercontent.com/108239710/184469181-91c0fdfe-3da9-40ac-b877-9c1eaffea209.png)

![image](https://user-images.githubusercontent.com/108239710/184469204-38603689-b9f4-40f0-943f-536837fb43c5.png)

![image](https://user-images.githubusercontent.com/108239710/184469217-8c688782-6695-4dc0-bce2-366bbaf7ef76.png)

![image](https://user-images.githubusercontent.com/108239710/184469236-d59a7a62-9fb1-473a-ac28-28fb392235ab.png)

![image](https://user-images.githubusercontent.com/108239710/184469264-1c530337-ea18-4180-9a90-d60800934236.png)


