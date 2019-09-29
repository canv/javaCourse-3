package lesson15.framework.core;

import lesson15.framework.annotations.Component;
import lesson15.framework.annotations.Inject;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public final class IocDiFramework {
    private static Map<Class<?>,Object> iocContainer = new HashMap<>();
    private IocDiFramework() {

    }

    public static void init(String path) {
        try {

            Reflections reflections = new Reflections(path);
            Set<Class<?>> annotatedTypes = reflections.getTypesAnnotatedWith(Component.class);
            for (Class<?> aClass : annotatedTypes) {
                Constructor<?> constructor = aClass.getConstructor(null);
                Object bean = constructor.newInstance();
                Class<?>[] interfaces = aClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    iocContainer.put(anInterface, bean);
                }
            }

            injectAnnotationBeanProcessor();

        }catch (Throwable e){
            e.printStackTrace();
            throw new Error("(Framework error)\nInitiation failed!");
        }
    }

    private static void injectAnnotationBeanProcessor()
            throws IllegalAccessException {

        Collection<Object> beans = iocContainer.values();
        for (Object bean : beans) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if(Objects.nonNull(field.getAnnotation(Inject.class))){
                    field.setAccessible(true);
                    Class<?> interfaceOfImplementation = field.getType();
                    Object dependency = getByInterface(interfaceOfImplementation);
                    field.set(bean, dependency);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getByInterface(Class<T> someInterface) {
        T implementation = (T)iocContainer.get(someInterface);
        if(Objects.isNull(implementation))
            throw new Error("(Framework error)\n" + someInterface.getName() + "'s implementation not found");
        return implementation;
    }
}