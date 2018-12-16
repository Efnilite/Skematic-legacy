package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Register {

    private Class[] effects;
    private Class[] expressions;
    private Class[] conditions;

    private List<Class> syntax;

    public Register() {
        effects = getClasses(Skematic.getInstance(), "com.efnilite.skematic.elements.effects").toArray(new Class[0]);
        expressions = getClasses(Skematic.getInstance(), "com.efnilite.skematic.elements.expressions").toArray(new Class[0]);
        conditions = getClasses(Skematic.getInstance(), "com.efnilite.skematic.elements.conditions").toArray(new Class[0]);

        syntax.addAll(Arrays.asList(effects));
        syntax.addAll(Arrays.asList(expressions));
        syntax.addAll(Arrays.asList(conditions));

        registerSyntaxes();
    }

    @SuppressWarnings("unchecked")
    private void registerSyntaxes() {
        if (syntax == null) {
            throw new IllegalStateException("Syntaxes not yet initialized");
        }
        for (Class clazz : syntax) {
            if (Effect.class.isAssignableFrom(clazz)) {
                Skript.registerEffect(clazz, clazz.getClass().getAnnotation(Patterns.class).value());
            } else if (SimpleExpression.class.isAssignableFrom(clazz)) {
                Skript.registerExpression(clazz, clazz.getClass().getAnnotation(Return.class).value(), ExpressionType.PROPERTY, clazz.getClass().getAnnotation(Patterns.class).value());
            } else if (SimplePropertyExpression.class.isAssignableFrom(clazz) && clazz.getClass().isAnnotationPresent(PropertyExpression.class)) {
                String property = clazz.getClass().getAnnotation(Patterns.class).value()[0];
                String fromType = clazz.getClass().getAnnotation(Patterns.class).value()[1];
                Skript.registerExpression(clazz, clazz.getClass().getAnnotation(Return.class).value(), ExpressionType.PROPERTY, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
            } else if (Condition.class.isAssignableFrom(clazz)) {
                Skript.registerCondition(clazz, clazz.getClass().getAnnotation(Patterns.class).value());
            } else {
                throw new IllegalStateException("Class of unknown type");
            }
        }
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
}