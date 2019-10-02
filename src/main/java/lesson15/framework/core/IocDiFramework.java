package lesson15.framework.core;

import lesson15.app.exceptions.*;
import lesson15.framework.annotations.*;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.*;

public final class IocDiFramework {
    private static Map<Class<?>, Object> iocContainer = new HashMap<>();
    private IocDiFramework() {}

    public static void run(Class<?> mainClass) {
        MyFrameworkBootStart annotation = mainClass.getAnnotation(MyFrameworkBootStart.class);
        if(Objects.isNull(annotation))
            throw new BootStartAnnotationNotFoundException("\n\n(Framework error)\nMain class path not found!");
        init(annotation.value());
    }
    private static void init(String path) {
        try {

            Reflections reflections = new Reflections(path);
            Set<Class<?>> Classes = reflections.getTypesAnnotatedWith(Component.class);
            List<Class<?>> aClasses = new ArrayList<>(Classes);
            aClasses.sort(Comparator.comparingInt(m -> m.getAnnotation(Component.class).value()));
            for (Class<?> aClass : aClasses) {
                Constructor[] constructors = aClass.getDeclaredConstructors();
                for (Constructor constructor : constructors) {
                    if (constructor.isAnnotationPresent(Inject.class))
                        injectedConstructorInit(constructor, aClass);
                    else defaultConstructorInit(aClass);

                }
            }
            injectedFieldsProcessor();
            injectedMethodsProcessor();

        } catch (Throwable e) {
            e.printStackTrace();
            throw new Error("\n\n(Framework error)\nInitiation failed!");
        }
    }

    private static void injectedConstructorInit(Constructor constructor, Class<?> aClass) throws Throwable {
        Class<?>[] typeParameters = constructor.getParameterTypes();
        Constructor<?> constructorInit = aClass.getDeclaredConstructor(typeParameters);

        constructorInit.setAccessible(true);
        Object[] injectArgs = {getByInterface(typeParameters[0])};

        Object realisation = constructorInit.newInstance(injectArgs);
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> aInterface : interfaces) {
            iocContainer.put(aInterface, realisation);
        }
    }
    private static void defaultConstructorInit(Class<?> aClass) throws Throwable {
        Object realisation = aClass.getConstructor().newInstance();//params null
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> aInterface : interfaces) {
            iocContainer.put(aInterface, realisation);

        }
    }

    private static void injectedFieldsProcessor()
            throws IllegalAccessException {
        Collection<Object> beans = iocContainer.values();
        for (Object bean : beans) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (Objects.nonNull(field.getAnnotation(Inject.class))) {
                    field.setAccessible(true);
                    Class<?> interfaceOfImplementation = field.getType();
                    Object dependency = getByInterface(interfaceOfImplementation);
                    field.set(bean, dependency);
                }
            }
        }
    }
    private static void injectedMethodsProcessor()
            throws IllegalAccessException, InvocationTargetException {
        Collection<Object> beans = iocContainer.values();
        for (Object bean : beans) {
            Method[] declaredMethods = bean.getClass().getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (Objects.nonNull(method.getAnnotation(Inject.class))) {
                    method.setAccessible(true);
                    Class<?>[] interfaceOfImplementation = method.getParameterTypes();
                    Object dependency = getByInterface(interfaceOfImplementation[0]);
                    method.invoke(bean, dependency);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getByInterface(Class<T> someInterface) {
        T implementation = (T)iocContainer.get(someInterface);
        if(Objects.isNull(implementation))
            throw new Error("\n\n(Framework error)\n" + someInterface.getName() + "'s implementation not found!");
        return implementation;
    }
}
