package com.practice.servicepractice.config;

import org.apache.commons.text.StringSubstitutor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestConfigReader {
    private static final Map<String, String> CONFIG = new HashMap<>();
    private static final Map<String, String> DEFAULT_VALUES = Map.of(
            "http://localhost:", "8080"
    );

    static {
        loadYamlConfig();
    }

    private static void loadYamlConfig() {
        Yaml yaml = new Yaml();
        try (InputStream input = TestConfigReader.class
                .getResourceAsStream("/application-test.yml")) {

            if (input != null) {
                Map<String, Object> config = yaml.load(input);
                flattenMap("", config, CONFIG);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test config", e);
        }
    }

    private static void flattenMap(String prefix, Map<String, Object> source, Map<String, String> target) {
        source.forEach((key, value) -> {
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;
            if (value instanceof Map) {
                flattenMap(fullKey, (Map<String, Object>) value, target);
            } else {
                target.put(fullKey, value.toString());
            }
        });
    }

    public static String getProperty(String key) {
        String value = CONFIG.getOrDefault(key, DEFAULT_VALUES.get(key));

        if (value == null) {
            return null;
        }

        StringSubstitutor substitutor = new StringSubstitutor(
                varName -> {
                    String systemProp = System.getProperty(varName);
                    if (systemProp != null) return systemProp;

                    String envVar = System.getenv(varName);
                    if (envVar != null) return envVar;

                    return CONFIG.getOrDefault(varName, DEFAULT_VALUES.get(varName));
                }
        );
        substitutor.setEnableSubstitutionInVariables(true);

        return substitutor.replace(value);
    }
}