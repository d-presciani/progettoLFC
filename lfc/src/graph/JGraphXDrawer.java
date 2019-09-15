package graph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import solver.Transizione;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class JGraphXDrawer extends JApplet{
	private static final long serialVersionUID = -6245711043382793078L;

	private static final Dimension DEFAULT_SIZE = new Dimension(960, 540);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    
    public RisImmagine init(LinkedList<String> nodi, LinkedList<Transizione> transizioni)
    { 
    	RisImmagine risultato = new RisImmagine();
        // Creazione di un grafico JGraphT
        ListenableGraph<String, DefaultEdge> g =
            new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        // Creazione di un nuovo visualizzatore usando JGraph tramite un adattatore
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

        jgxAdapter.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#000000");
        jgxAdapter.getModel().beginUpdate();
        try {
        	jgxAdapter.clearSelection(); 
        	jgxAdapter.selectAll();
            Object[] cells = jgxAdapter.getSelectionCells(); //here you have all cells

            // Iterate into graph to change cells
            for (Object c : cells) {
                mxCell cell = (mxCell) c; //cast
                if (cell.isVertex()) {
                	cell.setStyle(mxConstants.STYLE_FONTCOLOR+"=black");
                	if(cell.getValue().toString().contains("ERRORE LR1")) { //isVertex
                        cell.setStyle(/*mxConstants.STYLE_STROKECOLOR+"=red;"+*/mxConstants.STYLE_FILLCOLOR+"=red;"+mxConstants.STYLE_FONTCOLOR+"=black");
                    }
                	
                }
                
            }
        }
        finally {
        	jgxAdapter.getModel().endUpdate();
        	jgxAdapter.clearSelection(); 
        	jgxAdapter.refresh();
		}

        
        // Set del layout
        mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
        layout.setOrientation(SwingConstants.WEST);
        layout.setIntraCellSpacing(25);
        layout.setFineTuning(true);
        
        // Modifica dello stile degli archi (stile stondato)
        mxStylesheet edgeStyle = new mxStylesheet();
        Map<String, Object> edge = new HashMap<String, Object>();
        edge.put(mxConstants.STYLE_ROUNDED, true);
        edge.put(mxConstants.STYLE_ORTHOGONAL, false);
        edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        edge.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        edge.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        edgeStyle.setDefaultEdgeStyle(edge);
       
        jgxAdapter.setStylesheet(edgeStyle);
        
        layout.execute(jgxAdapter.getDefaultParent());
        final FileDialog dialog = new FileDialog((Frame)null, "Scegliere dove salvare il file");
		dialog.setMode(FileDialog.SAVE);
		dialog.setFile("*.png;*.jpg;*.jpeg");
		dialog.setVisible(true);
		final String file = dialog.getDirectory() + dialog.getFile();
		risultato.path = file;
		if(file.endsWith(".png") || file.endsWith(".jpg") || file.endsWith(".jpeg")) {
			BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 1, Color.WHITE, true, null);
	        try {
				ImageIO.write(image, "PNG", new File(file));
				risultato.esito="done";
			} catch (IOException e) {
				
			}
		} else {
			risultato.esito = "Formato non riconosciuto";
		}
		
		return risultato;
    }
}
