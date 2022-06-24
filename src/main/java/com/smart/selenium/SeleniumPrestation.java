package com.smart.selenium;

import java.awt.AWTException;
import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumPrestation {

	private static WebDriver driver;
	private static Actions actions;

	public static void main(String[] args) throws Exception {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://prestation.eserviceslab.com");
		driver.manage().window().maximize();
		actions = new Actions(driver);

		System.out.println("Starting chrome windows");

		Thread.sleep(20000);

		// Login As cnss user
		login("cnssuser", "1234");

		Thread.sleep(30000);
		goUpDown();
		Thread.sleep(30000);

		// Add demande
		WebElement addDemandBtn = findElementByXpath("//*[@id=\"sidebar\"]/app-sidebar/ul/li[2]");
		clickElement(addDemandBtn);
		Thread.sleep(5000);
		addDemand();

		viewNotifications();
		logout();
		return;

	}

	private static void addDemand() throws Exception {
		WebElement selectPrestation = findElementByXpath("//*[@id=\"step-1\"]/form/div[1]/div[2]/div/select");
		WebElement selectPrestationCategory = findElementByXpath("//*[@id=\"step-1\"]/form/div[1]/div[3]/div/select");
		WebElement LettreInput = findElementByXpath("//*[@id=\"dmd\"]/div/div/div");

		Thread.sleep(3000);
		if (selectPrestation.isDisplayed() && selectPrestationCategory.isDisplayed() && LettreInput.isDisplayed()) {

			Select selectedPrestation = new Select(selectPrestation);
			selectedPrestation.selectByIndex(1);
			
			Select selectedPrestationCategory = new Select(selectPrestationCategory);
			selectedPrestationCategory.selectByIndex(2);
			
			Thread.sleep(3000);
			humanTyping(LettreInput, "Madame, Monsieur,\r\n" + "\r\n"
					+ "J’ai reçu un courrier par lequel vous indiquiez votre refus de modification de mon relevé de situation.\r\n");

//			humanTyping(LettreInput, "Madame, Monsieur,\r\n" + "\r\n"
//					+ "J’ai reçu un courrier par lequel vous indiquiez votre refus de modification de mon relevé de situation.\r\n"
//					+ "\r\n"
//					+ "Je souhaite vous faire savoir que le nombre de points que vous comptabilisez au titre des assurances complémentaires ne mentionne pas les cotisations que j'ai versées durant les périodes d’activités comprises entre (indiquer les années concernées par l’erreur de calcul).\r\n"
//					+ "\r\n"
//					+ "Comme le confirment les pièces justificatives fournies dans ce courrier, je satisfais aux conditions de liquidation des droits (apporter les informations étayant votre point de vue).\r\n");
//			

			Thread.sleep(5000);

			WebElement fileUploadBtn = findElementByXpath("//*[@id=\"1\"]");

			goDown();
			fileUploadBtn.sendKeys("C:\\Users\\SMSDT-DEV-005\\Desktop\\file-sample_150kB.pdf");
			System.out.println("Uploaded a file");

			Thread.sleep(15000);
			WebElement createDemandBtn = findElementByXpath("//*[@id=\"step-1\"]/div/a[2]");
			Thread.sleep(3000);
			clickElement(createDemandBtn);

			Thread.sleep(30000);
			WebElement submitDemandBtn = findElementByXpath("//*[@id=\"step-2\"]/div/a[2]");
			System.out.println("Demand Created successfully");

			clickElement(submitDemandBtn);

			System.out.println("Demand Submitted successfully");
			Thread.sleep(2000);
			goUp();

			Thread.sleep(30000);
			demandListAndDetails();

		}
	}

	private static void clickElement(WebElement element) {
		if (element == null)
			return;

		int stop = 0;
		while (true) {
			try {
				System.out.println("Element " + element + " is being clicked");
				Thread.sleep(1000);
				if (element.isDisplayed() && element.isEnabled()) {
					actions.moveToElement(element).click().perform();
					break;
				}
			} catch (Exception e) {
				System.out.println("Element  = " + element.getText() + " Exception = " + e.getMessage());
			}

			if (stop == 3) {
				System.out.println(element + "not found");
				break;
			}
			stop++;
		}
	}

	private static void demandListAndDetails() throws Exception {

		Thread.sleep(3000);
		WebElement demandListBtn = findElementByXpath("//*[@id=\"sidebar\"]/app-sidebar/ul/li[3]/a");

		clickElement(demandListBtn);

		goUpDown();

		// Demand details
		WebElement DemandDetailsBtn = findElementByXpath(
				"//*[@id=\"index-page\"]/body/app-root/div[1]/body/section[1]/div/div/div[2]/app-listedemadecitoyen/div[2]/div[1]/table/tbody/tr[1]/td[5]/span/a");

		clickElement(DemandDetailsBtn);

		Thread.sleep(10000);
		// Download Demand PJ
		WebElement pjDownloadBtn = findElementByXpath(
				"//*[@id=\"step-3\"]/app-demande-dynamique/div[1]/div[6]/div/div/div/div/div/div[2]/a");
		clickElement(pjDownloadBtn);

		Thread.sleep(2000);
		goUp();

		Thread.sleep(30000);
		// Back to demands list
		clickElement(demandListBtn);

		// Cancel demand
//		Thread.sleep(30000);
//
//		WebElement annullerDemandeBtn = findElementByXpath("//*[@id=\"step-2\"]/div/a[2]");
//		clickElement(annullerDemandeBtn);

		goUpDown();

	}

	public static void viewNotifications() throws Exception {
		WebElement notificationsBtn = findElementByXpath(
				"//*[@id=\"index-page\"]/body/app-root/div[1]/body/header/app-header/header/div/div/div/div/a[2]");
		clickElement(notificationsBtn);
		Thread.sleep(2000);
		goUpDown();
		Thread.sleep(2000);
	}

	public static void goUpDown() throws Exception {
		goUp();
		Thread.sleep(2000);
		goDown();
		Thread.sleep(2000);
		goUp();
	}

	public static void goUp() throws Exception {
		WebElement topElement = findElementByXpath("//*[@id=\"index-page\"]/body/app-root/div[1]/body/header/app-header/header");
		actions.moveToElement(topElement);
		Thread.sleep(2000);
	}

	public static void goDown() throws Exception {
		WebElement buttomElement = findElementByXpath("//*[@id=\"index-page\"]/body/app-root/div[1]/body/app-footer/footer");
		actions.moveToElement(buttomElement);
		Thread.sleep(2000);
	}

	private static WebElement findElementByXpath(String xPATH) {
		int stop = 1;
		while (true) {
			try {
				System.out.println("Element" + xPATH + " is being located yet ...");
				Thread.sleep(1000);
				WebElement element = driver.findElement(By.xpath(xPATH));
				actions.moveToElement(element);
				System.out.println("Element" + xPATH + " Located successfully");
				return element;
			} catch (Exception e) {
				System.out.println("Element" + xPATH + " cannot be located yet ..., Exception " + e.getMessage());
			}

			if (stop == 3) {
				System.out.println("Element" + xPATH + " cannot be located");
				break;
			}

			stop++;
		}
		return null;
	}

	private static void login(String usernameVal, String passwordVal) throws InterruptedException {
		Thread.sleep(3000);
		WebElement username = findElementByXpath("//*[@id=\"username\"]");
		WebElement password = findElementByXpath("//*[@id=\"password\"]");
		WebElement submitBtn = findElementByXpath("/html/body/div/div/div[1]/main/form/div[4]/button");

		if (username.isDisplayed() && password.isDisplayed()) {
			if (username.isEnabled() && password.isEnabled()) {
				humanTyping(username, usernameVal);
				Thread.sleep(3000);
				humanTyping(password, passwordVal);
				String enteredText = username.getAttribute("value");
				System.out.println("Username = " + enteredText);
				Thread.sleep(3000);
				submitBtn.click();
			} else
				System.err.println("username textbox is not enabled");
		} else
			System.err.println("username textbox is not displayed");
		System.out.println("Login successfully");
	}

	private static void logout() {
		WebElement logoutBtn = findElementByXpath(
				"//*[@id=\"index-page\"]/body/app-root/div[1]/body/header/app-header/header/div/div/div/div/a[1]");

		clickElement(logoutBtn);
	}

	private static void humanTyping(WebElement element, String text) throws InterruptedException {
		for (char lettre : text.toCharArray()) {
			element.sendKeys("" + lettre);
			Thread.sleep(1);
		}
	}
}
