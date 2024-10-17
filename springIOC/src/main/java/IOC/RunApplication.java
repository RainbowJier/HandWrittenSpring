package IOC;

import IOC.dao.IUserDao;
import IOC.reflection.ApplicationContext;
import IOC.service.IUserService;
import javafx.application.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/17 10:26
 * @Version: 1.0
 */

public class RunApplication {
    public static final String BANNER = "\n" +
            "   _____   _    _    _____    _____   ______    _____    _____ \n" +
            "  / ____| | |  | |  / ____|  / ____| |  ____|  / ____|  / ____|\n" +
            " | (___   | |  | | | |      | |      | |__    | (___   | (___  \n" +
            "  \\___ \\  | |  | | | |      | |      |  __|    \\___ \\   \\___ \\ \n" +
            "  ____) | | |__| | | |____  | |____  | |____   ____) |  ____) |\n" +
            " |_____/   \\____/   \\_____|  \\_____| |______| |_____/  |_____/ \n" +
            "                                                               \n" +
            "                                                               \n";

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = new ApplicationContext();
        //applicationContext.initContext();
        applicationContext.initContextByAnnotation();
        
        // Get all beans.
        HashMap<Class<?>, Object> allBeans = applicationContext.getAllBeans();
        Set<Class<?>> classes = allBeans.keySet();
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getName() + " -> " + allBeans.get(clazz));
        }

        // Get bean object by class type.
        IUserService bean = (IUserService) applicationContext.getBean(IUserService.class);
        // call login method.
        bean.login();

        System.out.println(BANNER);
    }
}
