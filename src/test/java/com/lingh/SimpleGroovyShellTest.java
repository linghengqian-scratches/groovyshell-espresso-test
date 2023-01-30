package com.lingh;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleGroovyShellTest {
    @Test
    @DisabledInNativeImage
    void testGroovyShell() {
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse("3*5");
        assert (Integer) script.run() == 15;
    }

    @Test
    void testJavaInEspresso() {
        try (Context polyglot = Context.newBuilder().allowNativeAccess(true).build()) {
            Value java_lang_Math = polyglot.getBindings("java").getMember("java.lang.Math");
            double sqrt2 = java_lang_Math.invokeMember("sqrt", 2).asDouble();
            double pi = java_lang_Math.getMember("PI").asDouble();
            assert sqrt2 == Math.sqrt(2);
            assert pi == Math.PI;
        }
    }

    @Test
    void testGroovyShellInEspresso() {
        try (Context polyglot = Context.newBuilder().allowNativeAccess(true)
                .option("java.Classpath", "./lib/groovyshell-espresso-test-1.0-SNAPSHOT.jar" +
                        ":" +
                        "/home/linghengqian/.gradle/caches/modules-2/files-2.1/org.apache.groovy/groovy/4.0.8/e83922d915a939a463d6d208143ac37691945dae/groovy-4.0.8.jar" +
                        ":" +
                        "/home/linghengqian/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/31.1-jre/60458f877d055d0c9114d9e1a2efb737b4bc282c/guava-31.1-jre.jar")
                .build()) {
            Value inlineExpressionParser = polyglot.getBindings("java").getMember("com.lingh.InlineExpressionParser");
            assert inlineExpressionParser != null;
            assertThat(inlineExpressionParser.invokeMember("handlePlaceHolder", "t_$->{[\"new$->{1+2}\"]}").as(String.class),
                    is("t_${[\"new${1+2}\"]}"));
            assertThat(inlineExpressionParser.invokeMember("handlePlaceHolder", "t_${[\"new$->{1+2}\"]}").as(String.class),
                    is("t_${[\"new${1+2}\"]}"));
        }
    }
}
