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
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class Register {

    private List<Class<?>> effects;
    private List<Class<?>> expressions;
    private List<Class<?>> conditions;

    private List<Class> syntax = new ArrayList<>();

    public Register() {
        syntax.clear();

        effects = getClasses("com.efnilite.skematic.elements.effects");
        expressions = getClasses("com.efnilite.skematic.elements.expressions");
        conditions = getClasses("com.efnilite.skematic.elements.conditions");

        syntax.addAll(effects);
        syntax.addAll(expressions);
        syntax.addAll(conditions);

        registerSyntaxes();
    }

    private void registerSyntaxes() {
        if (syntax == null) {
            throw new IllegalStateException("Syntaxes not yet initialized");
        }
        for (Class clazz : syntax) {
            if (Effect.class.isAssignableFrom(clazz)) {
                System.out.println(clazz.getName());
                Skript.registerEffect(clazz, ((Patterns) clazz.getAnnotation(Patterns.class)).value());
            } else if (SimpleExpression.class.isAssignableFrom(clazz)) {
                Skript.registerExpression(clazz, ((Return) clazz.getAnnotation(Return.class)).value(), ExpressionType.PROPERTY, ((Patterns) clazz.getAnnotation(Patterns.class)).value());
            } else if (SimplePropertyExpression.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(PropertyExpression.class)) {
                String property = ((Patterns) clazz.getAnnotation(Patterns.class)).value()[0];
                String fromType = ((Patterns) clazz.getAnnotation(Patterns.class)).value()[1];
                Skript.registerExpression(clazz, ((Return) clazz.getAnnotation(Return.class)).value(), ExpressionType.PROPERTY, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
            } else if (Condition.class.isAssignableFrom(clazz)) {
                Skript.registerCondition(clazz, ((Patterns) clazz.getAnnotation(Patterns.class)).value());
            } else {
                throw new IllegalStateException("Class of unknown type");
            }
        }
    }

    private List<Class<?>> getClasses(String pack) {
        try {
            return ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive(pack)
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}