package IOC.reflection;

import IOC.annotation.Autowired;
import IOC.annotation.Bean;
import javafx.application.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/17 10:05
 * @Version: 1.0
 */

public class ApplicationContext {

    // Store bean objects in a Hashmap, equivalent to ioc container.
    private HashMap<Class<?>, Object> beanFactory = new HashMap<>();

    /**
     * init ApplicationContext, and put bean objects into IOC container.
     */
    public void initContext() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Load application.properties file and Get information of bean object.
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/application.properties"));
        Set<Object> keys = properties.keySet();
        for (Object key : keys) {
            Class<?> keyClass = Class.forName(key.toString());

            String value = properties.getProperty(key.toString());
            Class<?> valueClass = Class.forName(value);
            Object instance = valueClass.newInstance();

            // put bean object into IOC container.
            beanFactory.put(keyClass, instance);
        }
    }


    // root path of project.
    private String filePath;

    /**
     * Load all bean objects with annotation @Bean from the project path.
     */
    public void initContextByAnnotation() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Get the project path.
        filePath = Objects.requireNonNull(ApplicationContext.class.getClassLoader().getResource("")).getFile();

        // Load all bean objects into IOC container.
        loadOne(new File(filePath));

        // initiate fields of object.
        assembleObject();
    }

    /**
     * Load all class file with @Bean.
     */
    public void loadOne(File fileParent) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(!fileParent.isDirectory()){
            return;
        }

        File[] childrenFiles = fileParent.listFiles();
        if (childrenFiles == null) {
            return;
        }
        for (File child : childrenFiles) {
            if (child.isDirectory()) {
                loadOne(child);
            } else {
                String pathWithClass = child.getAbsolutePath().substring(filePath.length() - 1);

                // Get file name like UserServiceImpl.class
                if (pathWithClass.contains(".class")) {
                    String fullName = pathWithClass
                            .replaceAll("\\\\", ".")
                            .replace(".class", "");

                    Class<?> clazz = Class.forName(fullName);
                    if (!clazz.isInterface()) {
                        Bean annotation = clazz.getAnnotation(Bean.class);
                        if (annotation != null) {
                            Object instance = clazz.newInstance();

                            Class<?>[] interfacesList = clazz.getInterfaces();
                            // interface as key, if object has no interface.
                            if (interfacesList.length > 0) {
                                Class<?> interfaceClass = interfacesList[0];
                                beanFactory.put(interfaceClass, instance);
                            }
                            // clazz as key, if object has interface.
                            else {
                                beanFactory.put(clazz, instance);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @Autowired annotation to initiate fields of bean objects.
     */
    public void assembleObject() throws IllegalAccessException {
        Set<Class<?>> keys = beanFactory.keySet();
        for (Class<?> key : keys) {
            Object value = beanFactory.get(key);
            Class<?> clazz = value.getClass();

            // Set value of field by @Autowired.
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field filed : declaredFields) {
                Autowired annotation = filed.getAnnotation(Autowired.class);
                if (annotation != null) {
                    filed.setAccessible(true);

                    // Get bean object by type from IOC container.
                    Object object = beanFactory.get(filed.getType());
                    filed.set(value, object);
                }
            }
        }
    }

    /**
     * get bean object by class from IOC container.
     */
    public Object getBean(Class<?> clazz) {
        return beanFactory.get(clazz);
    }

    /**
     * return all bean objects in IOC container.
     */
    public HashMap<Class<?>, Object> getAllBeans() {
        return beanFactory;
    }
}
