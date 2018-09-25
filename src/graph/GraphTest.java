package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static support.graph.Constants.MAX_VERTICES;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphVertex;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;

/**
 * This class tests the functionality of a Graph based on a 'String' type
 * parameter for the vertices. Edge elements are Integers. The general framework
 * of a test case is: - Name the test method descriptively, mentioning what is
 * being tested (it is ok to have slightly verbose method names here) - Set-up
 * the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the program is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset
 * to a new instance of the a Graph&lt;String&gt; that you can simply use 'as
 * is' via the methods under the 'DO NOT MODIFY ANYTHING BELOW THIS LINE' line.
 * Of course, please do not modify anything below that, or the above assumptions
 * may be broken.
 */
@RunWith(Parameterized.class)
public class GraphTest {

	private Graph<String> _graph;
	private String _graphClassName;

	/**
	 * A simple test for insertVertex, that adds 3 vertices and then checks to
	 * make sure they were added by accessing them through the vertices iterator.
	 */
	@Test(timeout = 10000)
	public void testInsertVertex() {
		// insert vertices
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		// use the vertex iterator to get a list of the vertices in the actual
		// graph
		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}

		// assert that the graph state is consistent with what you expect
		assertThat(actualVertices.size(), is(3));
		assertThat(actualVertices.contains(A), is(true));
		assertThat(actualVertices.contains(B), is(true));
		assertThat(actualVertices.contains(C), is(true));
	}

	/**
	 * A simple test for insertEdges that adds 3 vertices, adds two edges to the
	 * graph and then asserts that both edges were in fact added using the edge
	 * iterator as well as checks to make sure their from and to vertices were set
	 * correctly.
	 */

	// this tests the insertEdges method
	@Test(timeout = 10000)
	public void testInsertEdges() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		// use the edge iterator to get a list of the edges in the actual graph.
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 1);
		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _graph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.size(), is(2));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(bc), is(true));
	}

	// this tests the exceptions thrown by the insertEdge method
	@Test(expected = InvalidVertexException.class)
	public void testInsertEdgeException() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.insertEdge(A, null, null);
		_graph.insertEdge(null, B, null);
		_graph.insertEdge(null, null, 1);
		_graph.insertEdge(A, null, 1);
		_graph.insertEdge(null, B, 1);
		_graph.insertEdge(A, B, null);
		_graph.insertEdge(null, null, null);
	}

	// this tests the removeVertices method
	@Test(timeout = 10000)
	public void testRemoveVertices() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		_graph.removeVertex(C);
		Iterator<CS16Vertex<String>> it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		ArrayList<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> edge = _graph.edges();
		while (edge.hasNext()) {
			actualEdges.add(edge.next());
		}
		assertThat(actualVertices.size(), is(2));
		assertThat(actualVertices.contains(A), is(true));
		assertThat(actualVertices.contains(B), is(true));
		assertThat(actualVertices.contains(C), is(false));
		assertThat(actualEdges.size(), is(1));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(bc), is(false));
	}

	// this tests the exceptions thrown by the removeVertices method
	@Test(expected = InvalidVertexException.class)
	public void testRemoveVerticesException() {
		_graph.removeVertex(null);
	}

	// this tests the removeEdges method
	@Test(timeout = 10000)
	public void testRemoveEdges() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		_graph.removeEdge(ab);
		Iterator<CS16Edge<String>> it = _graph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.size(), is(1));
		assertThat(actualEdges.contains(bc), is(true));
		assertThat(actualEdges.contains(ab), is(false));
	}

	// this tests the exceptions thrown by the removeEdge method
	@Test(expected = InvalidEdgeException.class)
	public void testRemoveEdgeException() {
		_graph.removeEdge(null);
	}

	// this tests the connectingEdge method
	@Test(timeout = 10000)
	public void testConnectingEdge() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
		CS16Edge<String> connectab = _graph.connectingEdge(A, B);
		CS16Edge<String> connectbc = _graph.connectingEdge(C, B);
		assertThat(connectab == ab, is(true));
		assertThat(connectbc == bc, is(true));
	}

	// the following two tests the exceptions thrown by connectingEdge method
	@Test(expected = InvalidVertexException.class)
	public void testConnectingEdgeException1() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.connectingEdge(A, null);
		_graph.connectingEdge(null, B);
		_graph.connectingEdge(null, null);
	}

	@Test(expected = NoSuchEdgeException.class)
	public void testConnectingEdgeException2() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.connectingEdge(A, B);
	}

	// this tests the incidentEdge method
	@Test(timeout = 10000)
	public void testIncidentEdge() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 2);

		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _graph.incidentEdges(A);
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.size(), is(2));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(ac), is(true));
	}

	// this tests the exceptions thrown by incidentEdge method
	@Test(expected = InvalidVertexException.class)
	public void testIncidentEdgeException() {
		_graph.incidentEdges(null);
	}

	// this tests the opposite method
	@Test(timeout = 10000)
	public void testOpposite() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
		CS16Vertex<String> oppA = _graph.opposite(A, ab);
		CS16Vertex<String> oppC = _graph.opposite(C, bc);
		assertThat(oppA == B, is(true));
		assertThat(oppC == B, is(true));
	}

	// the following three tests the exceptions thrown by opposite method
	@Test(expected = InvalidVertexException.class)
	public void testOppositeException1() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		_graph.opposite(null, ab);
	}

	@Test(expected = NoSuchVertexException.class)
	public void testOppositeException2() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		_graph.removeVertex(C);
		_graph.opposite(C, ab);
	}

	@Test(expected = InvalidEdgeException.class)
	public void testOppositeException3() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.opposite(A, null);
	}

	// this tests the endVertices method
	@Test(timeout = 10000)
	public void testEndVertices() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		List<CS16Vertex<String>> list = _graph.endVertices(ab);
		assertThat(list.size(), is(2));
		assertThat(list.contains(A), is(true));
		assertThat(list.contains(B), is(true));
	}

	// this tests the exceptions thrown by endVertices method
	@Test(expected = InvalidEdgeException.class)
	public void testEndVerticesException() {
		_graph.endVertices(null);
	}

	// this tests the areAdjacent method
	@Test(timeout = 10000)
	public void testAreAdjacent() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("A");
		CS16Vertex<String> D = _graph.insertVertex("B");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 1);
		CS16Edge<String> ad = _graph.insertEdge(A, D, 1);
		boolean abAdj = _graph.areAdjacent(A, B);
		boolean acAdj = _graph.areAdjacent(A, C);
		boolean adAdj = _graph.areAdjacent(A, D);
		boolean bcAdj = _graph.areAdjacent(B, C);
		boolean bdAdj = _graph.areAdjacent(B, D);
		boolean cdAdj = _graph.areAdjacent(C, D);
		assertThat(abAdj, is(true));
		assertThat(acAdj, is(false));
		assertThat(adAdj, is(true));
		assertThat(bcAdj, is(true));
		assertThat(bdAdj, is(false));
		assertThat(cdAdj, is(false));
	}

	// this tests the exception thrown by areAdjacent method
	@Test(expected = InvalidVertexException.class)
	public void testAreAdjacentException() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.areAdjacent(A, null);
		_graph.areAdjacent(null, A);
		_graph.areAdjacent(null, null);
	}

	// this tests the clear method in adjacency matrix
	@Test(timeout = 10000)
	public void testClear() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("A");
		CS16Vertex<String> D = _graph.insertVertex("B");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 1);
		CS16Edge<String> ad = _graph.insertEdge(A, D, 1);
		_graph.clear();
		Iterator<CS16Edge<String>> edges = _graph.edges();
		Iterator<CS16Vertex<String>> vertices = _graph.vertices();
		assertThat(edges.hasNext(), is(false));
		assertThat(vertices.hasNext(), is(false));
	}

	/*
	 * EXTRA CREDIT: Test your EdgeSetGraph by modifying this method per the
	 * instructions below!
	 */
	@Parameters(name = "with graph: {0}")
	public static Collection<String> graphs() {
		List<String> names = new ArrayList<>();
		names.add("graph.AdjacencyMatrixGraph");
		// un-comment the line below to test your EdgeSetGraph on all of the
		// graph testing methods that you've already written above!

		// Alternatively, if you want to test only the methods on your
		// EdgeSetGraph but not on the AdjacencyMatrixGraph, both
		// 1) uncomment the line below and 2) comment out the line above!

		// names.add("graph.EdgeSetGraph");

		return names;
	}

	/*
	 * ####################################################
	 * 
	 * DO NOT MODIFY ANYTHING BELOW THIS LINE
	 * 
	 * ####################################################
	 */

	@SuppressWarnings("unchecked")
	@Before
	public void makeGraph() {
		Class<?> graphClass = null;
		try {
			graphClass = Class.forName(_graphClassName);
			Constructor<?> constructor = graphClass.getConstructors()[0];
			_graph = (Graph<String>) constructor.newInstance();
		} catch (ClassNotFoundException | InvocationTargetException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException e) {
			System.err
					.println("Exception while instantiating Graph class in GraphTest.");
			e.printStackTrace();
		}
	}

	public GraphTest(String graphClassName) {
		this._graphClassName = graphClassName;
	}
}
