import annotations.ApplicationParams;
import annotations.TypedCommand;
import org.testng.TestNG;
import org.testng.collections.Lists;
import testng.TestNgListener;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main  {
    private static boolean IS_RUNNING = true;
    //provide absolute path
    private static final String pathFixedNativeAndroid="C:\\SOME_DIRECTORY\\androidNativeSuite.xml";
    private static final String pathFixedWeb="C:\\SOME_DIRECTORY\\webAppSuite.xml";
    private static final String pathFixedIos="C:\\SOME_DIRECTORY\\iosNativeSuite.xml";

    //for iOS provide additional params
    @ApplicationParams(
            pathToApp = "com.example.view.activity.MainActivity",
            appPackage = "com.example.android"
    )
    @TypedCommand()
    public static void main(String... args) {
        controlFunction();
    }



    private static void controlFunction() {
            Scanner input = new Scanner(System.in).useDelimiter("\n");
            String shPrompt = "input> ";
            System.out.print(shPrompt);

            while(IS_RUNNING){
             if(input.hasNext()) {


                 String command = input.next();


                 if (command.contains("run")) {

                     String subCommand = command.split(" ")[1];
                     if (subCommand.equalsIgnoreCase("android"))
                         executeSuite(pathFixedNativeAndroid);
                     else if (subCommand.equalsIgnoreCase("web"))
                         executeSuite(pathFixedWeb);
                     else if (subCommand.equalsIgnoreCase("ios"))
                         executeSuite(pathFixedIos);
                     else if (command.contains("path"))
                         executeSuite(command.substring(command.lastIndexOf(" ")+1));
                     else{
                            input.close();
                             controlFunction();
                             return;
                         }
                     }


                 else if(command.equalsIgnoreCase("exit")) {
                     IS_RUNNING = false;
                     input.close();
                     return;
                 }
                 else
                     executeSuite(command);
                 System.out.println("Suite Path abs: " + command);
             }
        }

        }
        private static void executeSuite(String ... paths){
        if(paths.length>0) {
            TestNG testng = new TestNG();
            List<String> suites = Lists.newArrayList();
            try {

                suites.addAll(Arrays.asList(paths));
                testng.addListener(new TestNgListener());
                testng.setTestSuites(suites);
                testng.run();
            }catch (Exception e){
                System.out.println(e.toString());
                System.exit(1);

            }
        }
        }

        private static void executeTypedSuite(String typedCommand){

        }


}
