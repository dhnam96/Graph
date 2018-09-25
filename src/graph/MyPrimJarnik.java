package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;

import net.datastructures.Entry;
import support.graph.CS16AdaptableHeapPriorityQueue;
import support.graph.CS16Edge;
import support.graph.CS16GraphVisualizer;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

/**
 * In this class you will implement a slightly modified version of the
 * Prim-Jarnik algorithm for generating Minimum Spanning trees. The original
 * version of this algorithm will only generate the minimum spanning tree of the
 * connected vertices in a graph, given a starting vertex. Like Kruskal's, this
 * algorithm can be modified to produce a minimum spanning forest with very
 * little effort.
 *
 * See the handout for details on Prim-Jarnik's algorithm. Like Kruskal's
 * algorithm this algorithm makes extensive use of the decorator pattern, so
 * make sure you know it.
 */

public class MyPrimJarnik<V> implements MinSpanForest<V> {

	private CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>> _pq;
	private MyDecorator<CS16Vertex<V>, Integer> _cost;
	private MyDecorator<CS16Vertex<V>, CS16Vertex<V>> _prev;
	private HashMap<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>> _map;
	private ArrayList<CS16Vertex<V>> _visited;

	/**
	 * This method implements Prim-Jarnik's algorithm and extends it slightly to
	 * account for disconnected graphs. You must return the collection of edges of
	 * the Minimum Spanning Forest (MSF) for the given graph, g.
	 * 
	 * This algorithm must run in O((|E| + |V|)log(|V|)) time
	 * 
	 * @param g
	 *          Your graph
	 * @param v
	 *          Only used if you implement the optional animation.
	 * @return returns a data structure that contains the edges of your MSF that
	 *         implements java.util.Collection
	 */
	@Override
	public Collection<CS16Edge<V>> genMinSpanForest(Graph<V> g,
			CS16GraphVisualizer<V> visualizer) {
		ArrayList<CS16Edge<V>> forest = new ArrayList<CS16Edge<V>>();
		this.initialize(g);
		while (_pq.isEmpty() == false) {
			CS16Vertex<V> v = _pq.removeMin().getValue();
			CS16Vertex<V> pv = _prev.getDecoration(v);
			Iterator<CS16Edge<V>> edges = g.incidentEdges(v);
			_visited.add(v);
			if (pv != null) {
				forest.add(g.connectingEdge(v, pv));
			}
			while (edges.hasNext()) {
				CS16Edge<V> e = edges.next();
				CS16Vertex<V> ov = g.opposite(v, e);
				if (_visited.contains(ov) == false && (pv != ov)) {
					if ((_cost.getDecoration(ov) > e.element())) {
						_cost.setDecoration(ov, e.element());
						_prev.setDecoration(ov, v);
						_pq.replaceKey(_map.get(ov), _cost.getDecoration(ov));
					}
				}
			}
		}
		return forest;
	}

	public void initialize(Graph<V> g) {
		_pq = new CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>>();
		_cost = new MyDecorator<CS16Vertex<V>, Integer>();
		_prev = new MyDecorator<CS16Vertex<V>, CS16Vertex<V>>();
		_map = new HashMap<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>>();
		_visited = new ArrayList<CS16Vertex<V>>();
		Iterator<CS16Vertex<V>> list = g.vertices();
		CS16Vertex<V> src = list.next();
		_cost.setDecoration(src, 0);
		_prev.setDecoration(src, null);
		_pq.insert(_cost.getDecoration(src), src);
		while (list.hasNext()) {
			CS16Vertex<V> vert = list.next();
			_cost.setDecoration(vert, (int) (java.lang.Double.POSITIVE_INFINITY));
			_prev.setDecoration(vert, null);
			_map.put(vert, _pq.insert(_cost.getDecoration(vert), vert));
		}
	}
}