import com.karthikeyan.modules.Properties;
import demopojo.DemoSrc;
import demopojo.DemoSubClass;
import demopojo.DemoTarget;

/**
 * @author Karthikeyan on 26-06-2021
 */

public class TestApplication {
    public static void main(String[] args) throws IllegalAccessException {
        DemoSrc demoSrc = new DemoSrc();
        DemoSubClass demoSubClass = new DemoSubClass();
        demoSrc.setA("A");
        demoSubClass.setAa("aa");
        demoSrc.setB(demoSubClass);

        DemoTarget demoTarget = new DemoTarget();

        DemoTarget targetedObj = Properties.cloner(demoSrc, demoTarget);
        System.out.println("Target Object json = " + Properties.toJson(targetedObj));
    }
}
