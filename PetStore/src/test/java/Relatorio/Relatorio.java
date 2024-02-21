package Relatorio;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class Relatorio {

    public static ExtentReports EXTENT_REPORT = null;
    public static ExtentHtmlReporter HTML_REPORTER = null;
    public static ExtentTest TEST;
    public static String reportPath = "target/reports/";
    public static String fileName = "PetStoreRestAssured.html";

    @BeforeSuite
    public void beforSuite() {
        EXTENT_REPORT = new ExtentReports();
        HTML_REPORTER = new ExtentHtmlReporter(reportPath+"/"+fileName);
        EXTENT_REPORT.attachReporter(HTML_REPORTER);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        TEST = EXTENT_REPORT.createTest(method.getName());
    }

    @AfterMethod
    public void afterTest(ITestResult result) {
        switch (result.getStatus()){
            case ITestResult.FAILURE:
                TEST.log(Status.FAIL, result.getThrowable().toString());
                break;
            case ITestResult.SKIP:
                TEST.log(Status.SKIP, result.getThrowable().toString());
                break;
            default:
                TEST.log(Status.PASS, "Sucesso");
                break;
        }
    }

    @AfterSuite
    public void afterSuite(){
        EXTENT_REPORT.flush();
    }
}
