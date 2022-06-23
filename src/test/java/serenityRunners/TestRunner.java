package serenityRunners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty", "html:target/site/serenity/cucumber.html", "json:target/site/serenity/cucumber-report.json"},
        glue = {"gherkinDefinitions"}
)
public class TestRunner {
}
