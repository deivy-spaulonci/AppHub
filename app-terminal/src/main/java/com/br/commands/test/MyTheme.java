package com.br.commands.test;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.gui2.AbstractTextGUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

class MyTheme extends PropertyTheme {
    MyTheme() {
        super(definitionAsProperty(), false);
    }

    private static Properties definitionAsProperty() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = AbstractTextGUI.class.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream(" + resourceFileName");
            if(resourceAsStream == null) {
                resourceAsStream = new FileInputStream("src/main/resources/my-theme.properties");
            }
            properties.load(resourceAsStream);
            resourceAsStream.close();

//            properties.load(new StringReader(definition));
            return properties;
        }
        catch(IOException e) {
            // We should never get here!
            throw new RuntimeException("Unexpected I/O error", e);
        }
    }
}
