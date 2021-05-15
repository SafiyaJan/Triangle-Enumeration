# Triangle-Enumeration

The triangle enumeration problem is a problem wherein, given a graph - be it sparse or dense - one aims to count the different triangles in the graph. In the problem, a triangle is defined as a set of 3 vertices that are mutually adjecent in the input graph. There are several real world application for counting triangles such as in analyzing social networks.

I approach this problem by utilizing a distributed computing solution, where we a have a client server architecture. In such a system, the client provides the server a list of all the edges in the graph. THe server then processes the graph an returns a list of all the triangles in the graph.

