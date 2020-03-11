package utils;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ReflectionHelper {

    private HashMap<Method,String> methods;
    private static ReflectionHelper INSTANCE = null;

    private ReflectionHelper(){
        methods = new HashMap<>();
    }

    public static synchronized ReflectionHelper getInstance(){
        if(INSTANCE==null)
            INSTANCE = new ReflectionHelper();
        return INSTANCE;
    }

    public void disposeMethods(){
        methods.clear();
    }

    public void reflectiveListInitializer(Object o, String functionName, @Nullable String params) {
        Method method;

        try {

            method = o.getClass().getMethod(functionName,String.class);

            methods.put(method,params);
            System.out.println("Successfully Reflected: "+method.getName());
          //  invokeMethodList(m,o,params);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }




    }

    private void invokeMethodList(Object object) throws InvocationTargetException, IllegalAccessException {

        methods.forEach((key, value) -> {
            try {
                key.invoke(object,value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println("invoked " + key.getName());
        });

    }
}
