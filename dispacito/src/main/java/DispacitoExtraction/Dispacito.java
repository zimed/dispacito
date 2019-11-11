package DispacitoExtraction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import mehdi.learning.projects.dispacito.Excelreader.ProductReader;
import mehdi.learning.projects.dispacito.selenium.BasePage;
import mehdi.learning.projects.dispacito.selenium.BaseTest;

public class Dispacito extends BaseTest{
	BasePage action;
	ProductReader produits;
	
	String closePopUp;
	String quantityXpath;
	String priceXpath;
	
	String quantityValue;
	String priceValue;
	
	ArrayList<ArrayList<String>> produitsInf;
	
	public Dispacito()  {}
	
	@BeforeClass
	public void getActualInfos() {
		String path=System.getProperty("user.dir");
    	String filePath=(path+"/src/products.xlsx");
    	System.out.println(filePath);
		action = new BasePage(super.app);
        produits = new ProductReader(filePath);   
        produitsInf = produits.ProductInfos();
	}
	
	
	@BeforeTest
	public void initiateData() throws FileNotFoundException, IOException{
    	String path = System.getProperty("user.dir");
    	System.out.println(path);
    	String filePath=(path+"/src/main/java/DispacitoExtraction/dispacitoXpath.properties");
		Properties appProps = new Properties();
		appProps.load(new FileInputStream(filePath));
		priceXpath = appProps.getProperty("price");
		quantityXpath = appProps.getProperty("quantity");
		closePopUp = appProps.getProperty("buttonPopUp");
	}
	
	@Test
	public void getDataForDispacito() throws Exception{
		produits.readProductInfos(produitsInf);
		int columnUrlLength = produitsInf.size();
		System.out.println("columnUrlLength : " + columnUrlLength);
		for (int indexUrl = 1; indexUrl<columnUrlLength ; indexUrl++) {
			System.out.println("index URL : " + indexUrl);
			System.out.println("hola " + produitsInf.get(indexUrl).get(1));
			app.get(produitsInf.get(indexUrl).get(1));
			//action.clickOnElementIfIsExist(By.xpath(closePopUp));
			action.clickOnElementIfIsExist(By.xpath(closePopUp));
		    priceValue =  app.findElement(By.xpath(priceXpath)).getText();
		    quantityValue =  app.findElement(By.xpath(quantityXpath)).getText();
		    produits.writeValueToCell(indexUrl, 3, priceValue);
		    produits.writeValueToCell(indexUrl, 5, quantityValue);
			System.out.println(priceValue + "  " + quantityValue);
		}	
	}
	
	@AfterTest
	public void closeExcel() {
		produits.closeWorkbook();
	}
	


	
	
	

}
