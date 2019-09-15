package app;
	
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Scanner;

import org.antlr.runtime.*;

import graph.JGraphXDrawer;
import graph.RisImmagine;
import lr1package.*;
import solver.Risultati;



import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class Main extends Application{
	
	//Variabili
	private static PrototipoLR1Parser parser;
	Risultati ris;
	Image immagine;
	String fileName;
	String retMex;
	
	@Override
	public void start(Stage primaryStage) {
		try {
		    // Creo un testo (non casella)
		    Text testo = new Text();
		    testo.setFont(new Font(22));
		    testo.setText("Selezionare un file di testo con la grammatica da identificare");		    
		    
		    // Creo i due bottoni
		    Button btnCarica = new Button("Carica file");
		    Button btnSalvaGrafico = new Button("Genera e mostra grafo");
		    btnSalvaGrafico.setDisable(true);
		    		    
		    //Immagine 
		    ImageView imW = new ImageView(immagine);
		    imW.resize(960/6*4, 540);
		    ScrollPane scrollPane = new ScrollPane();
	        scrollPane.setPrefSize(960/6*3.85, 540);
	        scrollPane.setPrefViewportWidth(960/6*4);
	        scrollPane.setPrefViewportHeight(540);
	        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	        scrollPane.setContent(imW);
	        scrollPane.setLayoutX(690/6*3);
	        scrollPane.setLayoutY(5);
	        
	        
		    // Event dei bottoni		    
		    // bottone carica file (Copiato codice da Riconoscitore (main_package))
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
					fileName = dialog.getFile(); //Salvataggio nomeFile per print successivo nel grafico
					dialog.dispose();
					if(dialog.getFile() == null) {
						testo.setText("Nessun file selezionato, selezionare un file");
					} else {
						CommonTokenStream tokens;
						
					  	boolean errore = false;
					  	try {
								testo.setText("IDENTIFICAZIONE GRAMMATICHE LR(1)\n"); 
								PrototipoLR1Lexer lexer = new PrototipoLR1Lexer(new ANTLRReaderStream(new FileReader(file)));
								tokens = new CommonTokenStream(lexer);
						    parser = new PrototipoLR1Parser(tokens);
						    retMex = parser.lr1();
						    if(parser.getErrorList().size()>0) {
						    	errore = true;
						    }
						    	String err = "";
							    for (int i=0;i<parser.getErrorList().size();i++) {
							    	err += (i+1) + ".\t" + parser.getErrorList().get(i);
							    }
							    testo.setText(err);
							} catch (Exception e) {
								System.out.println("eccezione catturata");
								errore = true;
								testo.setText(testo.getText() + "\nParsing con ANTLR abortito\n\n");
							}
					  	if(!errore) {
					  		ris = null;
					  		ris = parser.solve();
					  		primaryStage.setTitle("LR(1) Solver - " + fileName); // Titolo della schermata
					  		testo.setText(retMex + "\n" + ris.getMessaggi());
					  		inputUtente.close();
					  	}
					  	/*
					  	inputUtente.nextLine();
					  	inputUtente.close();*/
					  	imW.setImage(null);
						btnSalvaGrafico.setDisable(false);
					}
				}
			});
		    
		    btnSalvaGrafico.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					JGraphXDrawer drawer = new JGraphXDrawer();
					RisImmagine risultato = drawer.init(ris.getNodi(), ris.getListaTransizioni());
					if(risultato.getEsito().compareTo("done")==0) {
						immagine = new Image("file:" + Paths.get(System.getProperty("user.dir")).relativize(Paths.get(risultato.getPath())));
						imW.setImage(immagine);
						scrollPane.setContent(imW);
					} else {
						testo.setText(risultato.getEsito());
					}
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

		    griglia.add(btnCarica, 0, 0, 1, 1);
		    griglia.add(btnSalvaGrafico, 1, 0, 1, 1);
		    griglia.add(testo, 0, 1, 2, 9);

		    GridPane.setHalignment(btnCarica, HPos.CENTER);
		    GridPane.setHalignment(btnSalvaGrafico, HPos.CENTER);
		    GridPane.setValignment(testo, VPos.TOP);
		    GridPane.setHalignment(testo, HPos.CENTER);
		    testo.setWrappingWidth(960/3); //Auto a capo del testo
		    
		    Group gruppo = new Group(griglia);
		    ObservableList<Node> lista = gruppo.getChildren();
		    // Aggiungo al gruppo la scrollpane (nella griglia da problemi con la posizione)
		    lista.add(scrollPane);
		    Scene scene = new Scene(gruppo,960,540); // Dimensione della schermata
		    scene.setFill(Color.AQUA); // Setto colore BG
		    primaryStage.setScene(scene);
			primaryStage.setTitle("LR(1) Solver"); // Titolo della schermata
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
