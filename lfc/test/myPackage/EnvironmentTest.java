package myPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class EnvironmentTest {

	@Test
	void costruttore() {
		Environment env = new Environment();
		assertEquals(new ArrayList<String>(), env.errorList);
		assertEquals(new ArrayList<String>(), env.warningList);
		assertEquals("", env.translation);
	}
	
	@Test
	void aggiuntaErrore() {
		Environment env = new Environment();
		String err = "Errore di prova";
		assertEquals(0, env.errorList.size());
		env.addError(err);
		assertEquals(1, env.errorList.size());
		assertEquals("Errore di prova", env.errorList.get(0));
	}

}
