package graph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import solver.Transizione;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class JGraphXDrawer extends JApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6245711043382793078L;

	private static final Dimension DEFAULT_SIZE = new Dimension(960, 540);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;


    public void draw(LinkedList<String> nodi, LinkedList<Transizione> transizioni)
    {
        JGraphXDrawer applet = new JGraphXDrawer();
        applet.init(nodi, transizioni);

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void init(LinkedList<String> nodi, LinkedList<Transizione> transizioni)
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
            new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        for(String nodo : nodi) {
        	g.addVertex(nodo);
        }
        
        for(Transizione transizione: transizioni) {
        	g.addEdge(nodi.get(transizione.nodoOrigine-1), nodi.get(transizione.nodoTarghet-1),new RelationshipEdge(transizione.messaggio));
        }

        mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
        mxStylesheet edgeStyle = new mxStylesheet();
        Map<String, Object> edge = new HashMap<String, Object>();
        edge.put(mxConstants.STYLE_ROUNDED, true);
        edge.put(mxConstants.STYLE_ORTHOGONAL, true);
        edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        edge.put(mxConstants.STYLE_STROKECOLOR, "#6482B9"); // default is #6482B9
        edge.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        edgeStyle.setDefaultEdgeStyle(edge);
        jgxAdapter.setStylesheet(edgeStyle);
        layout.setOrientation(SwingConstants.WEST);
        layout.setIntraCellSpacing(25);
        layout.setFineTuning(true);
        layout.execute(jgxAdapter.getDefaultParent());

        // that's all there is to it!...
    }
}
