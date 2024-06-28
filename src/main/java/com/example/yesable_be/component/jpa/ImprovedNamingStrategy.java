package com.example.yesable_be.component.jpa;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class ImprovedNamingStrategy implements PhysicalNamingStrategy {
    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (jdbcEnvironment.getCurrentCatalog().toString().equals("HELLO_COCONE")) {
            return Identifier.toIdentifier(camelToSnake(name).getText().toUpperCase());
        }
        return Identifier.toIdentifier(name.toString());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return camelToSnake(name);
    }

    private Identifier camelToSnake(Identifier name) {
        String regex = "([a-z])([A-Z|0-9]+)";
        String replacement = "$1_$2";
        String convertName = name.getText().replaceAll(regex, replacement).toLowerCase();

        return Identifier.toIdentifier(convertName);
    }
}

