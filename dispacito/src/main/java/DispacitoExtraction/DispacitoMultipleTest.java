package DispacitoExtraction;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Beans.Product;
import mehdi.learning.projects.dispacito.Excelreader.ProductReader;
import mehdi.learning.projects.dispacito.selenium.BasePage;
import mehdi.learning.projects.dispacito.selenium.BaseTest;


public class DispacitoMultipleTest extends BaseTest{
	BasePage action;
	ProductReader produits;
	
	String closePopUp;
	String quantityXpath;
	String priceXpath;
	
	String quantityValue;
	String priceValue;
	
	ArrayList<ArrayList<String>> produitsInf;
	
	public DispacitoMultipleTest()  {}
	
	@BeforeClass
	public void getActualInfos() {
		String path=System.getProperty("user.dir");
    	String filePath=(path+"/src/products.xlsx");
    	System.out.println(filePath);
		//action = new BasePage(super.app);
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
	
	@Test(dataProvider = "productData")
	public void getDataForDispacito(Product produit) throws Exception{
			produits.readProductInfos(produitsInf);
//			System.out.println("exec    :  " + produit.getProductName());

//			app.get(produit.getProductUrl());
//			action.clickOnElementIfIsExist(By.xpath(closePopUp));
//		    priceValue =  app.findElement(By.xpath(priceXpath)).getText();
//		    quantityValue =  app.findElement(By.xpath(quantityXpath)).getText();
//		    produits.writeValueToCell(produit.indexProductInFile, 3, priceValue);
//		    produits.writeValueToCell(produit.indexProductInFile, 5, quantityValue);
//			System.out.println(priceValue + "  " + quantityValue);
		
	}
	
	@AfterTest
	public void closeExcel() {
		produits.closeWorkbook();
	}
	
	@DataProvider(name="productData")
	public Object[][] getData(){
		produits.readProductInfos(produitsInf);
		int productLength = produitsInf.size();
		Object[][] products = new Object[productLength][];
		String name;
		String url;
		String price;
		String quantity;

		for(int indexProduct = 1; indexProduct < productLength; indexProduct++) {
			name = produitsInf.get(indexProduct).get(0);
			url = produitsInf.get(indexProduct).get(1);
			price = produitsInf.get(indexProduct).get(2);
			quantity = produitsInf.get(indexProduct).get(4);
			products[indexProduct] = new Object[1];
			products[indexProduct][0] = new Product(name, url, price, quantity);
		}
		return products;
	}
	
	
	


	
	
	

}
