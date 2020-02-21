import org.testng.TestNG;
import org.testng.collections.Lists;
import testng.TestNgListener;

import java.util.List;

public class Main {

    public static void main(String[] args){

        System.out.println("Main");
        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("X:\\Egyebek\\idea_stuff\\androidNativeSuite.xml");
        testng.addListener(new TestNgListener());
        testng.setTestSuites(suites);
        testng.run();
    }
}
