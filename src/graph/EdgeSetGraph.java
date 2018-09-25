package graph;

import static support.graph.Constants.MAX_VERTICES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphEdge;
import support.graph.GraphVertex;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;

/**
 *
 * ###################################################### EXTRA CREDIT ONLY
 * ######################################################
 *
 * 
 * This class defines a Graph that tracks its edges through the use of an edge
 * set. Please review the lecture slides and the book before attempting to write
 * this program.
 */
public class EdgeSetGraph<V> implements Graph<V> {

	// The underlying data structure of your graph: the 'edge set'.
	// See the handout for more information on why this is a map, and not a set.
	private Map<CS16Edge<V>, CS16Edge<V>> _edges;

	// Set to store the vertices of your graph
	private Set<CS16Vertex<V>> _vertices;
	private ArrayList<Integer> _max;

	/**
	 * Constructor for your Graph, where you will most likely want to instantiate
	 * your edges map and vertices set.
	 *
	 * This must run in O(1) time.
	 */
	public EdgeSetGraph() {
		_edges = new HashMap<CS16Edge<V>, CS16Edge<V>>();
		_vertices = new HashSet<CS16Vertex<V>>();
		_max = new ArrayList<Integer>();
		for (int i = 1; i <= MAX_VERTICES; i++) {
			_max.add(i);
		}
	}

	/**
	 * Returns an iterator holding all the Vertices of the graph.
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 * * Note that the visualizer uses this method to display the graph's
	 * vertices, so you should implement it first.
	 *
	 * @return an Iterator containing the vertices of the Graph.
	 */
	@Override
	public Iterator<CS16Vertex<V>> vertices() {
		return _vertices.iterator();
	}

	/**
	 * Returns an iterator holding all the edges of the graph.
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 *
	 * Note that the visualizer uses this method to display the graph's edges, so
	 * you should implement it first.
	 *
	 * @return an Iterator containing the edges of the Graph.
	 */
	@Override
	public Iterator<CS16Edge<V>> edges() {
		return _edges.values().iterator();
	}

	/**
	 * Inserts a new Vertex into your Graph.
	 * 
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 * 
	 * @param vertElement
	 *          the element to be added to the graph as a vertex
	 */
	@Override
	public CS16Vertex<V> insertVertex(V vertElement) {
		CS16Vertex<V> v = new GraphVertex<V>(vertElement);
		v.setVertexNumber(_max.remove(0));
		_vertices.add(v);
		return v;
	}

	/**
	 * Inserts a new Edge into your Graph. The Edge needs to be added to the edge
	 * set.
	 * 
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 * 
	 * @param v1
	 *          The first vertex of the edge connection.
	 * @param v2
	 *          The second vertex of the edge connection.
	 * @param edgeElement
	 *          The element of the newly inserted edge.
	 * @return Returns the newly inserted Edge.
	 * @throws InvalidVertexException
	 *           Thrown when either Vertex is null.
	 */
	@Override
	public CS16Edge<V> insertEdge(CS16Vertex<V> v1, CS16Vertex<V> v2,
			Integer edgeElement) throws InvalidVertexException {
		if (v1 == null || v2 == null) {
			throw new InvalidVertexException("Vertex cannot be null");
		}
		int fake = (int) (Math.pow(v1.getVertexNumber(), 3) + Math.pow(
				v2.getVertexNumber(), 3));
		CS16Edge<V> fakeEdge = new GraphEdge<V>(fake);
		CS16Edge<V> realEdge = new GraphEdge<V>(edgeElement);
		realEdge.setFromVertex(v1);
		realEdge.setToVertex(v2);
		return _edges.put(fakeEdge, realEdge);
	}

	/**
	 * Removes a Vertex from your graph. You will need to look through all of the
	 * edges, and remove it if it is adjacent to the vertex to be removed. In
	 * addition, you'll need to update your set of vertices.
	 *
	 * <p>
	 * This must run in O(|E|) time.
	 * </p>
	 *
	 * @param vert
	 *          The Vertex to remove.
	 * @return The element of the removed Vertex.
	 * @throws InvalidVertexException
	 *           Thrown when the Vertex is null.
	 */
	@Override
	public V removeVertex(CS16Vertex<V> vert) throws InvalidVertexException {
		if (vert == null) {
			throw new InvalidVertexException("input vertex can't be null");
		}
		int vnum = vert.getVertexNumber();
		for (int i = 1; i <= MAX_VERTICES; i++) {
			_edges.remove(Math.pow(vnum, 3) + Math.pow(i, 3));
		}
		_max.add(vnum);
		_vertices.remove(vert);
		return vert.element();
	}

	/**
	 * Removes an Edge from your Graph.
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 *
	 * @param edge
	 *          The Edge to remove.
	 * @return The element of the removed Edge.
	 * @throws InvalidEdgeException
	 *           Thrown when the Edge is null.
	 */
	@Override
	public Integer removeEdge(CS16Edge<V> edge) throws InvalidEdgeException {
		if (edge == null) {
			throw new InvalidEdgeException("edge cannot be null");
		}
		_edges.remove(edge);
		int efrom = edge.getFromVertex().getVertexNumber();
		int eto = edge.getToVertex().getVertexNumber();
		int fakeEdge = (int) (Math.pow(efrom, 3) + Math.pow(eto, 3));
		return _edges.remove(fakeEdge).element();
	}

	/**
	 * Returns the edge that connects the two vertices. You will need to query the
	 * set of Edges using an imposter edge to check for the existence of an edge
	 * with endpoints v1 and v2. See the handout for more information on this!
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 *
	 * @param v1
	 *          The first vertex that may be connected.
	 * @param v2
	 *          The second vertex that may be connected.
	 * @return The edge that connects the first and second vertices.
	 * @throws InvalidVertexException
	 *           Thrown when either vertex is null.
	 * @throws NoSuchEdgeException
	 *           Thrown when no edge connects the vertices.
	 */
	@Override
	public CS16Edge<V> connectingEdge(CS16Vertex<V> v1, CS16Vertex<V> v2)
			throws InvalidVertexException, NoSuchEdgeException {
		if (v1 == null || v2 == null) {
			throw new InvalidVertexException("input vertices cannot be null");
		}
		int onenum = v1.getVertexNumber();
		int twonum = v2.getVertexNumber();
		int fakeEdge = (int) (Math.pow(onenum, 3) + Math.pow(twonum, 3));
		CS16Edge<V> realEdge = _edges.get(fakeEdge);
		if (realEdge == null) {
			throw new NoSuchEdgeException("there is no edge connecting the vertices");
		}
		return realEdge;
	}

	/**
	 * Returns an Iterator over all the Edges that are connected to this Vertex.
	 * Be careful to not add duplicates to the returned collection of edges!
	 * <p>
	 * This must run in O(|E|) time;
	 * </p>
	 * 
	 * @param vert
	 *          The vertex to find the incident edges on.
	 * @return Returns an Iterator holding the incident edges on v.
	 * @throws InvalidVertexException
	 *           Thrown when the Vertex is null.
	 */
	@Override
	public Iterator<CS16Edge<V>> incidentEdges(CS16Vertex<V> vert)
			throws InvalidVertexException {
		if (vert == null) {
			throw new InvalidVertexException("input vertex cannot be null");
		}
		int vnum = vert.getVertexNumber();
		ArrayList<CS16Edge<V>> arraylist = new ArrayList<CS16Edge<V>>();
		for (int i = 1; i <= MAX_VERTICES; i++) {
			int fakeEdge = (int) (Math.pow(vnum, 3) + Math.pow(i, 3));
			if (_edges.get(fakeEdge) != null) {
				arraylist.add(_edges.get(fakeEdge));
			}
		}
		return arraylist.iterator();
	}

	/**
	 * Returns the Vertex that is on the other side of Edge e opposite of Vertex
	 * v. If the edge is not incident on v, then throw a NoSuchVertexException.
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 *
	 * @param vert
	 *          The first vertex on Edge e.
	 * @param edge
	 *          The edge connecting Vertex v and the unknown opposite Vertex.
	 * @return The opposite Vertex of v across Edge e.
	 * @throws InvalidVertexException
	 *           Thrown when the Vertex is not valid.
	 * @throws InvalidEdgeException
	 *           Thrown when the Edge is not valid.
	 * @throws NoSuchVertexException
	 *           Thrown when Edge e is not incident on v.
	 */
	@Override
	public CS16Vertex<V> opposite(CS16Vertex<V> vert, CS16Edge<V> edge)
			throws InvalidVertexException, InvalidEdgeException,
			NoSuchVertexException {
		if (vert == null) {
			throw new InvalidVertexException("input vertex cannot be null");
		}
		if (edge == null) {
			throw new InvalidEdgeException("input edge cannot be null");
		}
		CS16Vertex<V> vertfrom = edge.getFromVertex();
		CS16Vertex<V> vertto = edge.getToVertex();
		if (vert != vertfrom && vert != vertto) {
			throw new NoSuchVertexException("there is no edge that is incident on v");
		}
		if (vert == vertfrom) {
			return vertto;
		} else {
			return vertfrom;
		}
	}

	/**
	 * Returns the two Vertex's that the Edge e is incident upon.
	 * 
	 * Checking the adjacency matrix may be too costly for this method.
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 * 
	 * Note that the visualizer uses this method to display the graph's edges.
	 *
	 * @param e
	 *          The edge to find the connecting Vertex's on.
	 * @return a list of Vertex's holding the two connecting vertices.
	 * @throws InvalidEdgeException
	 *           Thrown when the Edge e is null.
	 */
	@Override
	public List<CS16Vertex<V>> endVertices(CS16Edge<V> e)
			throws InvalidEdgeException {
		if (e == null) {
			throw new InvalidEdgeException("input edge cannot be null");
		}
		CS16Vertex<V> vertfrom = e.getFromVertex();
		CS16Vertex<V> vertto = e.getToVertex();
		ArrayList<CS16Vertex<V>> list = new ArrayList<CS16Vertex<V>>();
		if (vertfrom != null) {
			list.add(vertfrom);
		}
		if (vertto != null) {
			list.add(vertto);
		}
		return list;
	}

	/**
	 * Returns true if there exists an Edge that connects Vertex v1 and Vertex v2.
	 * 
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 * 
	 * @param v1
	 *          The first Vertex to test adjacency.
	 * @param v2
	 *          The second Vertex to test adjacency.
	 * @return Returns true if the vertices are adjacent.
	 * @throws InvalidVertexException
	 *           Thrown if either vertex is null.
	 */
	@Override
	public boolean areAdjacent(CS16Vertex<V> v1, CS16Vertex<V> v2)
			throws InvalidVertexException {
		if (v1 == null || v2 == null) {
			throw new InvalidVertexException("input vertices cannot be null");
		}
		int onenum = v1.getVertexNumber();
		int twonum = v2.getVertexNumber();
		int fakeEdge = (int) (Math.pow(onenum, 3) + Math.pow(twonum, 3));
		if (_edges.get(fakeEdge) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Clears all the vertices and edges from the graph. Remember the power of
	 * Java's garbage collection mechanism. If you re-instantiate something, the
	 * instance of what that Object used to be disappears.
	 *
	 * <p>
	 * This must run in O(1) time.
	 * </p>
	 */
	@Override
	public void clear() {
		_edges = new HashMap<CS16Edge<V>, CS16Edge<V>>();
		_vertices = new HashSet<CS16Vertex<V>>();
		_max = new ArrayList<Integer>();
		for (int i = 1; i <= MAX_VERTICES; i++) {
			_max.add(i);
		}
	}
}
