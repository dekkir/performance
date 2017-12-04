package steps;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:src/cucumber.json"},
        features = "src/test/java/features",
        format = {"pretty", "html:src/site/cucumber-pretty", "json:src/cucumber.json"},
        //glue = "src/test/java/steps/StepsDefinitions.java",
        tags = {"~@ignore"}
)

public class CucumberRunnerTest
{
}
