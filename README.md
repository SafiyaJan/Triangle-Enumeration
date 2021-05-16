# Triangle-Enumeration

The triangle enumeration problem is a problem wherein, given a graph - be it sparse or dense - one aims to count the different triangles in the graph. In the problem, a triangle is defined as a set of 3 vertices that are mutually adjecent in the input graph. There are several real world application for counting triangles such as in analyzing social networks.

I approach this problem by utilizing a distributed computing solution, using a client-server architecture. In such a system, the client provides the server a list of all the edges in the graph. The server then processes the graph and returns a list of all the triangles in the graph. I also used this project as a means to explore socket programming Java.


## Problem Approach
When the client sends the server a list of all the edges in the graph, the server begin immediately processing the graph. It does this by generating an adjacency-list representation of the graph. While the adjacency list is being generated, the server simultanously looks for triangles in the graph as it is processing the edges. This results in a slight improvement in the efficiency of the algorithm. The triangles in the graph are then outputted by the server to the client as a list of several 3-vertex triples that represent the different triangles in the graph.




