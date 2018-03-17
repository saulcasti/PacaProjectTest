package com.paca.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.paca.tests.pageobjects.PO_HomeView;
import com.paca.tests.pageobjects.PO_Properties;
import com.paca.tests.pageobjects.PO_RegisterView;
import com.paca.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PacaTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox = "E:\\USER\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
	// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
	// automáticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	@Test
	/**
	 * Acceder a la p�gina principal
	 */
	public void PR01() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// PR02. Opci�n de navegaci�n. Pinchar en el enlace Registro en la p�gina home
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	// PR03. OPción de navegación. Pinchar en el enlace Identificate en la p�gina
	// home
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	// PR04. Prueba del formulario de registro. registro con datos correctos
	@Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "laLoles@gmail.com", "Loles", "Fuentes", "123456", "123456");
		// Comprobamos que entramos en la secci�n privada
		PO_View.checkElement(driver, "text", "PACA");
	}

	/**
	 * PR06: Prueba del formulario de registro
	 * Email repetido o demasiado corto
	 * Nombre y apellidos demasiado cortos
	 */
	@Test
	public void PR06() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// COmprobamos el error de email repetido.
		PO_RegisterView.fillForm(driver, "laPaca@gmail.com", "Josefo", "Perez", "77777", "77777");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		
		// COmprobamos el error de email corto .
		PO_RegisterView.fillForm(driver, "hola", "Jose", "Perez", "77777", "77777");
		PO_RegisterView.checkKey(driver, "Error.signup.email.length", PO_Properties.getSPANISH());
		
		// Comprobamos el error de email no valido
		PO_RegisterView.fillForm(driver, "laLorena", "Lorena", "Reinols", "123456", "123456");
		PO_RegisterView.checkKey(driver, "Error.signup.email.format", PO_Properties.getSPANISH());
		
		// Comprobamos el error de nombre corto
		PO_RegisterView.fillForm(driver, "laLore@yahoo.com", "L", "Reinols", "77777", "77777");
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		
		// Comprobamos el error de apellido corto
		PO_RegisterView.fillForm(driver, "laLore@yahoo.com", "Lorena", "R", "77777", "77777");
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		
		// Comprobamos el error de contrase�a corta
		PO_RegisterView.fillForm(driver, "laLore@yahoo.com", "Lorena", "Reinols", "123", "123");
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		
		// Comprobamos el error de contrase�as no coincidentes
		PO_RegisterView.fillForm(driver, "laLore@yahoo.com", "Lorena", "Reinols", "123456", "654321");
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
				
	}
}
