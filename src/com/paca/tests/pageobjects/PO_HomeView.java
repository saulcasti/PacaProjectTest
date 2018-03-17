package com.paca.tests.pageobjects;

import org.openqa.selenium.WebDriver;

import com.paca.tests.util.SeleniumUtils;
import com.paca.tests.pageobjects.PO_NavView;

public class PO_HomeView extends PO_NavView{

	static public void checkWelcome(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", "PACA", getTimeout());
	}
	
	
}
