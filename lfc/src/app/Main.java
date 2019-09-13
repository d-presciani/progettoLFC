package app;
	
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileReader;
import java.util.Scanner;
import org.antlr.runtime.*;

import lr1package.*;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import graph.JGraphXDrawer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public final class Main extends Application {
	
	//Variabili per il parser
	private static PrototipoLR1Parser parser;

	
	@Override
	public void start(Stage primaryStage) {
		try {
			/*
		    // Creo una linea verticale (o almeno ci proviamo)
		    Line linea = new Line();
		    linea.setStartX(480);
		    linea.setStartY(0);
		    linea.setEndX(480);
		    linea.setEndY(540);
		    */
		    
		    //Creo un testo (non casella)
		    Text testo = new Text();
		    testo.setFont(new Font(22));
		    testo.setText("Selezionare un file di testo con la grammatica da identificare");

		    
		    // Creo i due bottoni
		    Button btnCarica = new Button("Carica file");
		    Button btnSalvaGrafico = new Button("Salva Grafo");
		    btnSalvaGrafico.setDisable(true);
		    
		    // Applet grafico
		    // Link a come fare ad usare l'applet
		    //https://stackoverflow.com/questions/8566818/is-it-possible-to-make-javafx-web-applet
		    JApplet zonaGrafo = new JGraphXDrawer();
		    SwingNode nodoGrafo = new SwingNode();
		    SwingUtilities.invokeLater(()-> nodoGrafo.setContent(zonaGrafo.getRootPane()));
		    
		    // Event dei bottoni		    
		    // bottone carica file
		    btnCarica.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					final Scanner inputUtente = new Scanner(System.in);
					// Richiesta del file di input all'utente
					final FileDialog dialog = new FileDialog((Frame)null, "Scegliere il file da aprire");
					dialog.setMode(FileDialog.LOAD);
					dialog.setFile("*.txt");
					dialog.setVisible(true);
					final String file = dialog.getDirectory() + dialog.getFile();
					String fileName = dialog.getFile(); //Salvataggio nomeFile per print successivo nel grafico
					dialog.dispose();
					if(dialog.getFile() == null) {
						testo.setText("Nessun file selezionato, riselezionare un file");
						inputUtente.hasNextLine();
						inputUtente.close();
					} else {
								
						CommonTokenStream tokens;
					
					  	boolean errore = false;
					  	try {
								testo.setText("IDENTIFICAZIONE GRAMMATICHE LR(1)\n"); 
								PrototipoLR1Lexer lexer = new PrototipoLR1Lexer(new ANTLRReaderStream(new FileReader(file)));
								tokens = new CommonTokenStream(lexer);
						    parser = new PrototipoLR1Parser(tokens);
					
						    parser.lr1();
						    
						    if(parser.getErrorList().size()>0) {
						    	errore = true;
						    }
						    	String err = "";
							    for (int i=0;i<parser.getErrorList().size();i++) {
							    	err += (i+1) + ".\t" + parser.getErrorList().get(i);
							    }
							    testo.setText(err);
							} catch (Exception e) {
								errore = true;
								testo.setText(testo.getText() + "\nParsing con ANTLR abortito\n\n");
							}
					  	if(!errore) {
					  		//TODO: questo andrebbe spostato in modo da avere qui tutta la roba da stampare ecc...
					  		parser.solve(fileName);
					  	}
					  	inputUtente.nextLine();
					  	inputUtente.close();
						btnSalvaGrafico.setDisable(false);
					}
				}
			});
		    
		    btnSalvaGrafico.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    //Gridpane per organizzare gli elementi
		    GridPane griglia = new GridPane();
		    griglia.setPrefSize(960, 540);
		    ColumnConstraints column = new ColumnConstraints();
		    RowConstraints row = new RowConstraints();
		    row.setPercentHeight(10);
		    column.setPercentWidth(15);
		    griglia.getRowConstraints().add(row);
		    griglia.getColumnConstraints().add(column);
		    
		    //Prova modifica testo auto a capo
		    griglia.add(btnCarica, 0, 0, 1, 1);
		    griglia.add(btnSalvaGrafico, 1, 0, 1, 1);
		    griglia.add(testo, 0, 1, 2, 9);
		    griglia.add(nodoGrafo, 2, 0, 4, 10);
		    GridPane.setHalignment(btnCarica, HPos.CENTER);
		    GridPane.setHalignment(btnSalvaGrafico, HPos.CENTER);
		    GridPane.setValignment(testo, VPos.TOP);
		    GridPane.setHalignment(testo, HPos.CENTER);
		    testo.setWrappingWidth(960/3);
		    /*
		    // Creo un gruppo con una linea al suo interno
		    Group gruppo = new Group(linea);
		    // Rimedio l'elenco degli oggetti nel gruppo
		    ObservableList<Node> lista = gruppo.getChildren();
		    // Aggiungo al gruppo il testo creato
		    lista.add(testo);
		    Scene scene = new Scene(gruppo,960,540); // Dimensione della schermata
		    */
		    
		    Group gruppo = new Group(griglia);
		    Scene scene = new Scene(gruppo,960,540); // Dimensione della schermata
		    scene.setFill(Color.AQUA); // Setto colore BG
		    primaryStage.setScene(scene);
			primaryStage.setTitle("LR1 Solver"); // Titolo della schermata
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
