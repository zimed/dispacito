package mehdi.learning.projects.dispacito;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.print.DocFlavor.URL;

import DispacitoExtraction.Dispacito;
import mehdi.learning.projects.dispacito.Excelreader.*;
import mehdi.learning.projects.dispacito.selenium.BasePage;

import java.util.ArrayList;
/**
 * Hello world!
 *
 */
public class App 
{

	

    public static void main( String[] args )
    {
    	ProductReader produits;
    	String quantityXpath;
    	String priceXpath;
    	String quantityValue;
    	String priceValue;
    	Object[][] produitsInf = null;
    	
		String path=System.getProperty("user.dir");
    	String filePath=(path+"/src/products.xlsx");
    	System.out.println(filePath);
        produits = new ProductReader(filePath);   
        produitsInf = produits.ProductInfosBeta();
        for(int i = 0; i < produitsInf.length; i++) {
			System.out.println("new row");
            for(int j = 0; j < produitsInf[i].length; j++) {
            	System.out.println("yo : " + produitsInf[i][j]);
            }
        }
        produits.writeValueToCell(1,3,"yooo");
        

        
    }

}
