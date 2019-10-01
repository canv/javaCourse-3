package lesson15.framework.core;

import lesson15.framework.annotations.*;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.*;

public final class IocDiFramework {
    private static Map<Class<?>, Object> iocContainer = new HashMap<>();

    private IocDiFramework() {
    }

    public static void init(String path) {
        try {

            Reflections reflections = new Reflections(path);
            Set<Class<?>> aTypes = reflections.getTypesAnnotatedWith(Component.class);
            for (Class<?> aClass : aTypes) {
              //  testConstructorInit(aClass);

                Constructor[] constructors = aClass.getDeclaredConstructors();
                for (Constructor constructor : constructors) {
                    if(constructor.isAnnotationPresent(Inject.class))
                        injectedConstructorInit(constructor, aClass);
                    else defaultConstructorInit(aClass);
                }
            }

            injectAnnotationBeanProcessor();

        } catch (Throwable e) {
            e.printStackTrace();
            throw new Error("\n\n(Framework error)\nInitiation failed!");
        }
    }

    private static void injectedConstructorInit(Constructor constructor, Class<?> aClass) throws Throwable {
//        Constructor[] c = aClass.getDeclaredConstructors();
//        for (Constructor constructor : c) {
//            Class[] c1 = constructor.getParameterTypes();
////            for (Class aClass1 : c1) {
////                System.out.println(aClass1.toString());
////            }
//            if (c1[2] == null) {
//            }
//            //  System.out.println(Arrays.toString(constructor.getParameterTypes()));
//        }

        System.out.println("injectedConstructorInit");

        Class[] typeParameters = constructor.getParameterTypes();
        Constructor<?> constructorInit = aClass.getDeclaredConstructor(typeParameters);
        constructorInit.setAccessible(true);


        Object dependency = getByInterface(typeParameters[0]);
//        Field[] declaredFields = aClass.getDeclaredFields();
//        for (Field field : declaredFields) {
//            if (Objects.nonNull(field.getAnnotation(Inject.class))) {
//                field.setAccessible(true);
//                Class<?> interfaceOfImplementation = field.getType();
//                dependency = getByInterface(typeParameters[0]);
//               // field.set(bean, dependency);
//            }
//        }

        //continue here
        Object[] injectArgs = {dependency};



        Object realisation = constructorInit.newInstance(injectArgs);
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> aInterface : interfaces) {
            iocContainer.put(aInterface, realisation);
        }

//        System.out.println("YESS");
//        Constructor<?> constructor = aClass.getConstructor();//params @Inject
//        Object realisation = constructor.newInstance();
//        Class<?>[] interfaces = aClass.getInterfaces();
//        for (Class<?> aInterface : interfaces) {
////                    System.out.println(aInterface.toString());
//          //  System.out.println(realisation.toString());
//            iocContainer.put(aInterface, realisation);
//        }
    }

    private static void defaultConstructorInit(Class<?> aClass) throws Throwable {
//        TypeVariable[] typeParameters = aClass.getConstructor().getTypeParameters();
//        System.out.println(Arrays.toString(typeParameters));
        System.out.println("defaultConstructorInit");
//        Constructor[] c = aClass.getDeclaredConstructors();
//        int y = 0;
//        for (Constructor constructor : c) {
//            int x = 0;
//            if(constructor.isAnnotationPresent(Inject.class)) System.out.println("HERE!!");
//            Class[] c1 = constructor.getParameterTypes();
//            for (Class aClass1 : c1) {
//                System.out.println("$ "+aClass1.toString());
//                x++;
//                y++;
//            }
//            if (Objects.nonNull(constructor))
//                System.out.println("~~~ " + x + "^" + y + " ~~~" + Arrays.toString(constructor.getParameterTypes()));
//        }
       // constructor = constructor.getConstructor();//params null
        Object realisation = aClass.getConstructor().newInstance();//params null
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> aInterface : interfaces) {
//                    System.out.println(aInterface.toString());
            //           System.out.println(realisation.toString());
            iocContainer.put(aInterface, realisation);
        }
    }

    private static void testConstructorInit(Class<?> aClass) throws Throwable {
        Constructor[] c = aClass.getDeclaredConstructors();
        int y = 0;
        for (Constructor constructor : c) {
            int x = 0;
            if(constructor.isAnnotationPresent(Inject.class)) System.out.println("HERE!!");
            Class[] c1 = constructor.getParameterTypes();
            for (Class aClass1 : c1) {
                System.out.println("$ "+aClass1.toString());
                x++;
                y++;
            }
            if (Objects.nonNull(constructor))
                System.out.println("~ " + x + "^" + y + " ~" + Arrays.toString(constructor.getParameterTypes()));
        }

        Constructor<?> constructor = aClass.getConstructor();//params null
        Object realisation = constructor.newInstance();
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> aInterface : interfaces) {
            iocContainer.put(aInterface, realisation);
        }
    }

    private static void injectAnnotationBeanProcessor()
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


    private static void injectField(Object bean) throws IllegalAccessException {

    }
    private static void injectSetter() {
    }
    private static void injeptConstructor() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getByInterface(Class<T> someInterface) {
        T implementation = (T)iocContainer.get(someInterface);
        if(Objects.isNull(implementation))
            throw new Error("(Framework error)\n" + someInterface.getName() + "'s implementation not found");
        return implementation;
    }
}