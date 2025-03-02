package bakos.life_pm;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",  // Location of .feature files
        glue = "bakos.life_pm.cucumber",      // Location of step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumberTest {

}