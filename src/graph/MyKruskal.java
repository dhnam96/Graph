package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import support.graph.CS16AdaptableHeapPriorityQueue;
import support.graph.Graph;
import support.graph.CS16Edge;
import support.graph.CS16GraphVisualizer;
import support.graph.CS16Vertex;
import support.graph.MinSpanForest;

/**
 * This class is based on Kruskal's minimum spanning tree algorithm. It has been
 * extended to calculate the MST of each disconnected graph at the same time.
 * The trick is to take advantage of the fact that Kruskal's algorithm combines
 * "clouds" when it builds its trees. Thus we can connect the clouds of these
 * disconnect graphs using the standard algorithm. The only modification to the
 * original algorithm is specifying the termination case. Since the graphs can
 * be disconnected, we can not stop the algorithm once we have n-1 edges in our
 * MST.
 *
 * See the handout for an explanation of Kruskal's algorithm. This also makes
 * heavy use of the decorator pattern, so make sure you know it cold.
 *
 */
public class MyKruskal<V> implements MinSpanForest<V> {

	private CS16AdaptableHeapPriorityQueue<Integer, CS16Edge<V>> _pq;
	private MyDecorator<CS16Vertex<V>, Integer> _decor;
	private MyDecorator<CS16Vertex<V>, CS16Vertex<V>> _cloud;

	/**
	 * This method implements Kruskal's algorithm and extends it slightly to
	 * account for disconnected graphs. You must return the collection of edges of
	 * the Minimum Spanning Forest (MSF) for the given graph, g.
	 * 
	 * This must run in O(|E|log|V|) time.
	 * 
	 * @param g
	 *          Your graph
	 * @param visualizer
	 *          Only used if you implement the optional animation.
	 * @return returns a data structure that contains the edges of your MSF that
	 *         implements java.util.Collection
	 */
	@Override
	public Collection<CS16Edge<V>> genMinSpanForest(Graph<V> g,
			CS16GraphVisualizer<V> visualizer) {
		_decor = new MyDecorator<CS16Vertex<V>, Integer>();
		_cloud = new MyDecorator<CS16Vertex<V>, CS16Vertex<V>>();
		_pq = new CS16AdaptableHeapPriorityQueue<Integer, CS16Edge<V>>();
		this.makeCloud(g);
		this.pqEdge(g);
		Collection<CS16Edge<V>> forest = new ArrayList<CS16Edge<V>>();
		while (_pq.isEmpty() == false) {
			CS16Edge<V> minEdge = _pq.removeMin().getValue();
			CS16Vertex<V> vfrom = minEdge.getFromVertex();
			CS16Vertex<V> vto = minEdge.getToVertex();
			this.find(vfrom);
			this.find(vto);
			// check if the from vertex and to vertex are in the same cloud
			if (_cloud.getDecoration(vfrom) != _cloud.getDecoration(vto)) {
				this.union(vfrom, vto);
				forest.add(minEdge);
			}
		}
		return forest;
	}

	public void makeCloud(Graph<V> g) {
		Iterator<CS16Vertex<V>> itv = g.vertices();
		while (itv.hasNext()) {
			CS16Vertex<V> v = itv.next();
			// cloud ranks should all be 0
			_decor.setDecoration(v, 0);
			// points to itself
			_cloud.setDecoration(v, v);
		}
	}

	public void pqEdge(Graph<V> g) {
		Iterator<CS16Edge<V>> ite = g.edges();
		while (ite.hasNext()) {
			CS16Edge<V> e = ite.next();
			_pq.insert(e.element(), e);
		}
	}

	public void union(CS16Vertex<V> r1, CS16Vertex<V> r2) {
		if (_decor.getDecoration(_cloud.getDecoration(r1)) > _decor
				.getDecoration(_cloud.getDecoration(r2))) {
			CS16Vertex<V> parent = _cloud.getDecoration(r2);
			_cloud.setDecoration(parent, _cloud.getDecoration(r1));
			_cloud.setDecoration(r2, _cloud.getDecoration(r1));
		} else if (_decor.getDecoration(_cloud.getDecoration(r2)) > _decor
				.getDecoration(_cloud.getDecoration(r1))) {
			CS16Vertex<V> parent = _cloud.getDecoration(r1);
			_cloud.setDecoration(parent, _cloud.getDecoration(r2));
			_cloud.setDecoration(r1, _cloud.getDecoration(r2));
		} else {
			CS16Vertex<V> parent = _cloud.getDecoration(r2);
			_cloud.setDecoration(parent, _cloud.getDecoration(r1));
			_cloud.setDecoration(r2, _cloud.getDecoration(r1));
			_decor.setDecoration(r1, _decor.removeDecoration(r1) + 1);
		}
	}

	public CS16Vertex<V> find(CS16Vertex<V> x) {
		CS16Vertex<V> par = _cloud.getDecoration(x);
		if (x != par) {
			_cloud.setDecoration(x, this.find(par));
		}
		return _cloud.getDecoration(x);
	}
}
