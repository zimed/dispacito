package mehdi.learning.projects.dispacito.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.WebDriver;


public class BasePage{
	WebDriver app;
	public BasePage(WebDriver wb) {
		this.app = wb;
	}
	int TIMOUT = 2000;
	// ********************************************************************//
	// SAISIR CHAMPS
	// ********************************************************************//

	protected void saisirChampAvecClear(By champ, String text) throws Exception {
		this.waitForElement(champ);
		app.findElement(champ).click();
		app.findElement(champ).clear();
		app.findElement(champ).sendKeys(text);
	}

	protected void saisirChampSansClear(By champ, String text) throws Exception {
		this.waitForElement(champ);
		app.findElement(champ).click();
		app.findElement(champ).sendKeys(text);
	}

	// ********************************************************************//
	// WAIT FOR ELEMENT
	// ********************************************************************//

	protected void waitForElement(By champ) throws Exception {
		WebDriverWait wait = new WebDriverWait(app, 10);
		Thread.sleep(3000);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(champ));
		} catch (Exception e) {
			throw new Exception("Erreur sur la page ====>" + this.getClass().getSimpleName() + "Element non trouve : "
					+ champ.toString());
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(champ));
		} catch (Exception e) {
			throw new Exception("Erreur sur la page ====>" + this.getClass().getSimpleName()
					+ "Element present mais non visible : " + champ.toString());
		}
	}

	// ********************************************************************//
	// WAIT TO BE CLICKABLE
	// ********************************************************************//

	protected void waitToBeClickable(By locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(app, 10);
		Thread.sleep(3000);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			throw new Exception("Erreur sur la page ====> " + this.getClass().getSimpleName() + " Element non trouve : "
					+ locator.toString());
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			throw new Exception("Erreur sur la page ====>" + this.getClass().getSimpleName()
					+ "Element present mais non visible : " + locator.toString());
		}

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			throw new Exception("Erreur sur la page ====>" + this.getClass().getSimpleName()
					+ "Element present mais non clickable : " + locator.toString());
		}

	}

	// ********************************************************************//
	// VERIFICATION DES CHAMPS
	// ********************************************************************//

	protected void verifierText(By champ, String texteAverifier) throws Exception {
		this.waitForElement(champ);
		WebElement elementAcontroler = app.findElement(champ);
		Assert.assertEquals(elementAcontroler.getText(), texteAverifier);
	}

	protected void verifierChampVide(By champ) throws Exception {
		this.waitForElement(champ);
		WebElement elementAcontroler = app.findElement(champ);
		Assert.assertEquals(elementAcontroler.getText(), "");

	}

	// ********************************************************************//
	// VERIFICATION ELEMENT PRESENT
	// ********************************************************************//

//	protected Boolean estPresent(By element)throws Exception{
//		waitForElement(element);
//		List<WebElement> presence = (List<WebElement>) app.findElement(element);
//		return (presence.size() > 0);
//	}

	// ********************************************************************//
	// CLICK SUR ELEMENT
	// ********************************************************************//

	public void cliquer(By locator) throws Exception {
		this.waitToBeClickable(locator);
		app.findElement(locator).click();
	}

	// ********************************************************************//
	// FORCE CLICK
	// ********************************************************************//
	protected void forceClick(WebElement el) throws Exception {
		int retry = 0;
		Boolean done = false;
		if (el != null) {
			while (!done && (retry < 5)) {
				try {
					el.click();
					done = true;
				} catch (Exception e) {
				}
				Thread.sleep(3000);
				retry++;

			}
		}

		else
			throw new Exception("Element non trouver");
		if (!done && (retry >= 5))
			throw new Exception("Clique non realiser");
	}

	protected WebElement forceFindElement(By locator) throws Exception {
		WebElement el = null;
		int retry = 0;
		while ((el == null) && (retry < 5)) {
			try {
				el = app.findElement(locator);
			} catch (Exception e) {
			}
			Thread.sleep(2000);
			retry++;
		}

		if (el != null) {
			try {

				Actions act = new Actions(app);
				act.moveToElement(el);
				act.perform();
			} catch (Exception e) {
			}
		}
		return el;
	}

	protected void forceClicker(By locator) throws Exception {
		WebElement el = this.forceFindElement(locator);
		this.forceClick(el);
	}
	
	public void clickOnElementIfIsExist(By locator) throws Exception {
		if(app.findElements(locator).size() > 0 && app.findElement(locator).isDisplayed()) {
			app.findElement(locator).click();
		}
	}

	// ********************************************************************//
	// RADIO BUTTON ET VERIFICATION DE LA SELECTION
	// ********************************************************************//
	protected void cliquerRadioButton(By locator) throws Exception {
		this.waitToBeClickable(locator);
		long start = System.currentTimeMillis();
		while (!app.findElement(locator).isSelected() && (System.currentTimeMillis() - start) < TIMOUT) {
			app.findElement(locator).click();

		}
	}

	// ********************************************************************//
	// SELECTIONNER DANS UNE LISTE PAR TEXTE
	// ********************************************************************//
	protected void selectionnerOptionParTexte(By locator, String text) throws Exception {
		this.waitToBeClickable(locator);
		Select select = new Select(app.findElement(locator));
		select.selectByVisibleText(text);
	}

	// ********************************************************************//
	// EFFACER UN CHAMP
	// ********************************************************************//
	protected void clearChamp(By locator) throws Exception {
		app.findElement(locator).clear();
	}

	// ********************************************************************//
	// RECUPERER TEXTE
	// ********************************************************************//

	protected String recupererText(By locator) throws Exception {
		this.waitForElement(locator);
		return app.findElement(locator).getText();
	}

	// ********************************************************************//
	// SCROLL TO ELEMENT
	// ********************************************************************//

	public void scrollToElement(By locator) throws Exception {
		// WebElement ele = app.findElement(locator);
		// Actions act = new Actions(app);
		// act.moveToElement(ele);
		// act.perform();
		Thread.sleep(5000);
		JavascriptExecutor je = (JavascriptExecutor) app;
		WebElement ele = app.findElement(locator);		
		je.executeScript("arguments[0].scrollIntoView(true);", ele);

	}
	
	// ********************************************************************//
	// GESTION DATE
	// ********************************************************************//
	
	public static String date_aller() throws Exception{
		Calendar calendar = Calendar.getInstance();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		calendar.add(Calendar.DATE, 5);
		String dateDebut = (sdf.format(calendar.getTime())).toString();
		return dateDebut;
	}
	
	public static String date_retour() throws Exception{
		Calendar calendar = Calendar.getInstance();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		calendar.add(Calendar.DATE, 10);
		String dateRetour = (sdf.format(calendar.getTime())).toString();
		return dateRetour;
	}
}
