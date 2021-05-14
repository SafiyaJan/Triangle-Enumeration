import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

class Server {

	static class Graph {

		public HashMap<Integer, ArrayList<Integer>> adj_list = new HashMap<Integer, ArrayList<Integer>>();
		public ArrayList<String> all_triangles = new ArrayList<String>();

		// add edge between two vertices
		public void add_edge(int src, int dest) {

			if (!adj_list.containsKey(src)) {
				adj_list.put(src, new ArrayList<Integer>());
			}

			if (!adj_list.containsKey(dest)) {
				adj_list.put(dest, new ArrayList<Integer>());
			}

			ArrayList<Integer> src_neighbors = adj_list.get(src);
			ArrayList<Integer> dest_neighbors = adj_list.get(dest);

			src_neighbors.add(dest);
			dest_neighbors.add(src);

		}

		// check if triangle
		public boolean is_triangle(int u, int w) {

			// check if u and w have edge between each other
			ArrayList<Integer> u_adj_list = adj_list.get(u);
			ArrayList<Integer> w_adj_list = adj_list.get(w);

			if (u_adj_list.contains(w) && w_adj_list.contains(u)) {
				return true;
			}
			return false;

		}

		// create string representing triangle
		public static String triangle_string(int u, int v, int w) {

			int[] arr = { u, v, w };
			Arrays.sort(arr);
			String triangle = Integer.toString(arr[0]) + " " + Integer.toString(arr[1]) + " "
					+ Integer.toString(arr[2]);
			return triangle;

		}

		// generate graph and while doing, simultaneously find triangles
		public Graph(String input_edges) {

			String[] edges = input_edges.split("\n");

			for (int k = 0; k < edges.length; k++) {

				String[] vertices = edges[k].split("\\s+");

				// add edge from src <-> dest
				int src = Integer.parseInt(vertices[0]);
				int dest = Integer.parseInt(vertices[1]);
				add_edge(src, dest);

				// get src and dest adj lists
				ArrayList<Integer> src_neighbors = adj_list.get(src);
				int src_size = src_neighbors.size();

				ArrayList<Integer> dest_neighbors = adj_list.get(dest);
				int dest_size = dest_neighbors.size();

				// check if adj list of src and dest are the >=2
				if (src_size >= 2 && dest_size >= 2) {

					for (int i = 0; i < src_size; i++) {

						/*
						 * check if both lists contains any matching vertices -> if yes, the matching
						 * vertex creates the triangle
						 */
						int elem = src_neighbors.get(i);
						if (dest_neighbors.contains(elem)) {

							String triangle = triangle_string(src, dest, elem);

							// if triangle not already added to list of triangles, then add
							if (!all_triangles.contains(triangle)) {
								all_triangles.add(triangle);
							}

						}

					}

				}

			}

		}

	}

	public static void main(final String args[]) throws Exception {
		if (args.length != 1) {
			System.out.println("usage: java CCServer port");
			System.exit(-1);
		}
		final int port = Integer.parseInt(args[0]);

		final ServerSocket ssock = new ServerSocket(port);
		System.out.println("listening on port " + port);
		while (true) {
			try {

				/*
				 * YOUR CODE GOES HERE - accept a connection from the server socket - add an
				 * inner loop to read requests from this connection repeatedly (client may reuse
				 * the connection for multiple requests) - for each request, compute an output
				 * and send a response - each message has a 4-byte header followed by a payload
				 * - the header is the length of the payload (signed, two's complement,
				 * big-endian) - the payload is a string (UTF-8) - the inner loop ends when the
				 * client closes the connection
				 */

				// accept a connection from the server socket
				final Socket serviceSocket = ssock.accept();

				// define input and output streams
				final DataInputStream din = new DataInputStream(serviceSocket.getInputStream());
				final DataOutputStream dout = new DataOutputStream(serviceSocket.getOutputStream());

				boolean connected = serviceSocket.isConnected();
				try {
					while (connected) {

						final int respDataLen = din.readInt(); // read the size of the graph

						byte[] bytes = new byte[respDataLen];
						din.readFully(bytes); // read the graph itself

						// convert bytes into a string
						String graph = new String(bytes, StandardCharsets.UTF_8);

						// create graph + find triangles
						Graph acc_graph = new Graph(graph);

						// format output
						String all_triangles = String.join("\n", acc_graph.all_triangles);

						// send response to client
						bytes = all_triangles.getBytes("UTF-8");
						dout.writeInt(bytes.length);
						dout.write(bytes);
						dout.flush();

					}

				} catch (final Exception e) {
					connected = false;
				}

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
}
