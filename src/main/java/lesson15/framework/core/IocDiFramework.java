package lesson15.framework.core;

import lesson15.framework.annotations.Component;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class IocDiFramework {
    private static Map<Class<?>,Object> iocContainer = new HashMap<>();
    private IocDiFramework() {

    }

    public static void init(String path) {
        try {

            Reflections reflections = new Reflections(path);
            Set<Class<?>> annotatedTypes = reflections.getTypesAnnotatedWith(Component.class);
            for (Class<?> annotatedClass : annotatedTypes) {
                Constructor<?> constructor = annotatedClass.getConstructor(null);
                Object classInstance = constructor.newInstance();
                Class<?>[] interfaces = annotatedClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    iocContainer.put(anInterface, classInstance);
                }
            }

        }catch (Throwable e){
            e.printStackTrace();
            throw new Error("init error");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getByInterface(Class<T> someInterface) {
        Object implementation = iocContainer.get(someInterface);
        if(Objects.isNull(implementation))
            throw new Error(someInterface.getName() + "'s implementation not found");
        return (T)implementation;
    }
}
