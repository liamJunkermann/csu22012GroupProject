Algorithms and Data Structures group project design document
Feature 1: Bus route and associated cost.
The path finding algorithm that we chose to use for this feature was Sedgewick and Wayne’s Dijkstra algorithm. We decided to go with Dijkstra because A* would be less efficient for our task of finding the shortest path to a given point. We also came to the conclusion that A* would be more difficult to implement given the requirements of this program. Given that A* is more memory inefficient than Dijkstra in tandem with the fact that it requires more operations per node than Dijkstra does led us to the decision to go with Dijkstra as our Algorithm. Dijkstra’s Runtime is N squared and  In order to store our data for the algorithm we used an edge weighted graph as our data had to be stored in pairs and we deduced that this would be the best way to do so. Given the runtime for initialising the edge weighted graph is N squared and the memory usage is N+E. We found this to be best for our needs and as we only needed to run this data structure on initialising as opposed to our pathfinding algorithm which had to be run every time we performed a calculation as it’s a variable Algorithm.

Feature 2: 
We didn’t have to make any design decisions for our second feature as our project Spec outlined that we were to use a ternary search tree

Feature 3:
We decided to re-use the edge weighted graph for this feature as we found it to be the most efficient for similar reasons as discussed in feature 1. A regular expression was developed by the team in order to complete this feature.

Feature 4:
We decided to use A command line interface as it would allow for the functionality that we required and we wouldn't have to worry about visualising data.

Development of the project was carried out on a team basis with much of the work on the features being done while on a zoom call together. We found collaborative programming to work best for this project as we have worked together before and know each other’s strengths and weaknesses.
