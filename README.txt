README Graph:
The graph uses MyDecorator class to keep track of the hashmap's Key and Value. The
methods in this class will be used in the future to store and access the hashmap's
key and values. The adjacency matrix graph class keeps track of the vertices and
edges locations regarding each other through the use of a 2D matrix. The 2D matrix,
when plugged in the vertex numbers of two vertices will give the edge connecting
the two. Furthermore, the edges and vertices lists are given using an iterator,
which allows it to be visualized. Whenever a vertex is inserted, a corresponding
vertex number is added along to keep track within the adjacency matrix. This vertex
number needs to be tracked using a data structure. I used an arraylist to hold the
numbers of maximum list of vertices. This way each vertex can be traced to a 2D
matrix point. A stack and queue can also be used in a similar manner for this
purpose. Whenever an edge is inserted, similarly, using the (vertex from) and
(vertex to) data stored in the edge, the 2D matrix is updated. When a vertex is
being removed, it removes any and all connecting edges using the 2D matrix. When
an edge is being removed, the (vertex from) and (vertex to) is used to set the
corresponding 2D matrix to null and then removed from the hash set. The connecting
edges are found by using the vertex number to locate the 2D matrix. The incident
edges are found by taking one vertex number and adding all elements in the corresponding
row or column of the matrix to an arraylist and is iterated back. The opposite and
end vertices are found by using the (vertex from) and (vertex to) data. The
areAdjacent method uses the 2D matrix to see if there exists a value in the given
location of the 2D matrix.

PRIM-JARNIK:
The prim jarnik algorithm is rather simple. It implements a cost, previous, map,
priority queue, and an arraylist to find the minimum spanning forest. The MSF
itself is an arraylist iterated through. Initially, the algorithm takes in a graph
and sets all vertices of the graph's costs and previous to infinity and null
respectively. However, the source node's cost is set to 0. Then, for each vertex,
the entry of cost, which determines the priority, and the vertex is stored into
a map to be accessed when needed. So while the priority queue is not empty, we dequeue
the entry with the lowest cost (first priority) and decorate its connecting vertices
with a previous and cost. Then the connecting edges are added to the forest. The
dequeued vertices are then added to the visited array list so as to keep track of
which vertices have been visited.

KRUSKAL:
Kruskal's algorithm uses a cloud, a decorator, and a priority queue. Initially, a
cloud is made so that all of the vertices has its own cloud pointing to itself and
is decorated with 0. Then, all of the edges of the graph is added to the priority
queue following the cost of the edge. The spanning forest similarly uses an
arraylist and is iterated back. Again, while the priority queue is not empty, it
takes the highest priority (minimum cost) edge and finds its two connecting vertices.
Then, it uses the find method to find the root of the tree, or the very most parent.
The vertices' clouds are then decorated with the root's cloud. If the two vertices'
clouds are not same, meaning the two vertices are in different clouds, then the
two clouds are merged together using the union method. The union method takes the
rank of the roots of the different clouds and merges them. Furthermore, the minimum edge
is added to the minimum spanning forest.

TESTING:
For testing, I've tested every method in the Adjacency Matrix class. Each method
has a line of comment which explains what method the test checks. Furthermore, I've
checked if all the exceptions to be thrown have been thrown in the correct occasions.
The MSFTest checks both the Prim-Jarnik and Kruskal algorithm by passing in a graph
and running the algorithm to find the minimum spanning forest. This has a simple
test, two forest test, one vertex test, and a circular forest test.

EXTRA CREDIT:
I've attempted the extra credit by using a hashmap that connects two vertices to
an edge by taking the cube of each vertex number and adding them together. I chose
cube instead of square to make sure the edges are assigned to different areas.
The fake edges are then mapped to the real edge using the hashmap data structure.
