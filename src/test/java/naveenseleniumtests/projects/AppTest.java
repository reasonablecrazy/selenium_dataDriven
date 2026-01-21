package naveenseleniumtests.projects;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import naveenseleniumtest.pageobjectmodel.CheckOutPage;
import naveenseleniumtest.pageobjectmodel.ConfirmationPage;
import naveenseleniumtest.pageobjectmodel.LandingPage;
import naveenseleniumtest.pageobjectmodel.ProductCartPage;
import naveenseleniumtests.testComponents.BaseTests;


public class AppTest extends BaseTests {

    
    @Test
    public void ValidateConfPage(Method method) throws IOException {
    	
    	ArrayList<ArrayList<String>> testData = new ArrayList<>();
    	testData = getTheTestDataForThisTest(method.getName());
    	System.out.println(testData);
    	LandingPage landingPage = new LandingPage(driver);
    	landingPage.login(testData.get(0).get(1), testData.get(0).get(2));
    	
		ProductCartPage prodCartPage = new ProductCartPage(driver);
		prodCartPage.getProductList().addProductsToCart(testData.get(0).get(3)).navigateToCart().
			validateProductsOnCartPage().submitCart();
		
		CheckOutPage checkoutPage = new CheckOutPage(driver);
		checkoutPage.selectCountry(testData.get(0).get(4)).submitCheckoutPage();
		
		ConfirmationPage confPage = new ConfirmationPage(driver);
		confPage.validateProductOnFinalPage(testData.get(0).get(3));
		
		System.out.println("Successfully concluded the test");
    }

	@Test
    public void ValidateE2Eflow(Method method) throws IOException {
    	ArrayList<ArrayList<String>> testData = new ArrayList();
    	testData = getTheTestDataForThisTest(method.getName());
    	System.out.println(testData);  	
    	
    	LandingPage landingPage = new LandingPage(driver);
    	landingPage.login(testData.get(0).get(1), testData.get(0).get(2));
    	
		ProductCartPage prodCartPage = new ProductCartPage(driver);
		prodCartPage.getProductList().addProductsToCart(testData.get(0).get(3)).navigateToCart().
			validateProductsOnCartPage().submitCart();
		
		CheckOutPage checkoutPage = new CheckOutPage(driver);
		checkoutPage.selectCountry(testData.get(0).get(4)).submitCheckoutPage();
		
		ConfirmationPage confPage = new ConfirmationPage(driver);
		confPage.validateProductOnFinalPage(testData.get(0).get(3));
		
		System.out.println("Successfully concluded the test");
    }
	
	@DataProvider(name = "testdataforthetest")
	public Object[][] getTheTestDataDataDriven(Method method) throws IOException {
		ArrayList<ArrayList<String>> testData = new ArrayList();
    	testData = getTheTestDataForThisTest(method.getName());
    	if (testData.isEmpty()) {
            throw new RuntimeException("No test data found for method: " + method.getName());
        }
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i); // each row as ONE parameter
        }
        return data;
	}
	
	@Test (dataProvider="testdataforthetest")
    public void ValidateE2Eflow_withDataProvider(ArrayList<String> arr) throws IOException {

    	System.out.println(arr);  		
    	LandingPage landingPage = new LandingPage(driver);
    	landingPage.login(arr.get(1).toString(),arr.get(2).toString());
    	
		ProductCartPage prodCartPage = new ProductCartPage(driver);
		prodCartPage.getProductList().addProductsToCart(arr.get(3).toString()).navigateToCart().
			validateProductsOnCartPage().submitCart();
		
		CheckOutPage checkoutPage = new CheckOutPage(driver);
		checkoutPage.selectCountry(arr.get(4).toString()).submitCheckoutPage();
		
		ConfirmationPage confPage = new ConfirmationPage(driver);
		confPage.validateProductOnFinalPage(arr.get(3).toString());
		
		System.out.println("Successfully concluded the test");
    }
	
}
