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

//Ordenamos las pruebas por el nombre del m茅todo
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PacaTests {

	// En Windows (Debe ser la versi贸n 46.0 y desactivar las actualizacioens
	// autom谩ticas)):
	static String PathFirefox = "E:\\USER\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
	// En MACOSX (Debe ser la versi贸n 46.0 y desactivar las actualizaciones
	// autom谩ticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Com煤n a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versi贸n 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaci贸nn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Despu茅s de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la 煤ltima prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	@Test
	/**
	 * Acceder a la p醙ina principal
	 */
	public void PR01() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// PR02. Opci髇 de navegaci髇. Pinchar en el enlace Registro en la p醙ina home
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	// PR03. OPci贸n de navegaci贸n. Pinchar en el enlace Identificate en la p醙ina
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
		// Comprobamos que entramos en la secci髇 privada
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
		
		// Comprobamos el error de contrase馻 corta
		PO_RegisterView.fillForm(driver, "laLore@yahoo.com", "Lorena", "Reinols", "123", "123");
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		
		// Comprobamos el error de contrase馻s no coincidentes
		PO_RegisterView.fillForm(driver, "laLore@yahoo.com", "Lorena", "Reinols", "123456", "654321");
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
				
	}
}
