package com.efnilite.skematic;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Register {

    private Class[] effects;
    private Class[] expressions;
    private Class[] conditions;

    public Register() {
        effects = getClasses(Skematic.getInstance(), "com.efnilite.skematic.elements.effects").toArray(new Class[0]);
        expressions = getClasses(Skematic.getInstance(), "com.efnilite.skematic.elements.expressions").toArray(new Class[0]);
        conditions = getClasses(Skematic.getInstance(), "com.efnilite.skematic.elements.conditions").toArray(new Class[0]);

        registerEffects();
        registerExpressions();
        registerConditions();
    }

    private Set<Class<?>> getClasses(JarFile jar, String... packages){
        Set<Class<?>> classes = new HashSet<>();
        try {
            for (Enumeration<JarEntry> jarEntry = jar.entries(); jarEntry.hasMoreElements();) {
                String name = jarEntry.nextElement().getName().replace("/", ".");
                String className = name.substring(0, name.length() - 6);
                className = className.replace('/', '.');
                for (String packageName : packages) {
                    if (name.startsWith(packageName) && name.endsWith(".class")) {
                        classes.add(Class.forName(className));
                    }
                }
            }
            jar.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return classes;
    }

    private Set<Class<?>> getClasses(JavaPlugin instance, String... packages) {
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);
            File file = (File) method.invoke(instance);
            JarFile jar = new JarFile(file);
            return getClasses(jar, packages);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void registerEffects() {

    }

    private void registerExpressions() {

    }

    private void registerConditions() {

    }
}
