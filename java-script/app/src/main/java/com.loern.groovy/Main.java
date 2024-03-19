package com.loern.groovy;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main{
    public static void main(String[] args){
//        doByGroovyShell();
//        doByGroovyClassLoader();
        doByGroovyScriptEng();
    }


    public static void doByGroovyScriptEng(){
        try {
            GroovyScriptEngine scriptEngine = new GroovyScriptEngine("/Users/loern/Documents/github/groovy-demo/java-script/app/build/resources/main/");
            Binding binding = new Binding();
            binding.setVariable("args",new String[]{"Hello from GroovyScriptEngine"});
            Script script = scriptEngine.createScript("build.groovy", binding);
            Object run = script.run();
            System.out.println(run);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static void doByGroovyClassLoader(){
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("UTF-8");

        URL resource = Main.class.getResource("/build.groovy");
        try (GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(),
                compilerConfiguration)) {
            assert resource != null;
            Class<?> groovyClass = loader.parseClass(new File(resource.getPath()));
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            System.out.println(groovyObject.getMetaClass());
            System.out.println(groovyObject.getClass());
            Object name = groovyObject.invokeMethod("printlnNames", new Object[]{"load from script"});
            System.out.println(name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void doByGroovyShell(){
        // 创建GroovyShell实例
        GroovyShell shell = new GroovyShell();

        // 定义Groovy脚本
        String script = "println 'Hello from Groovy'";

        // 执行Groovy脚本
        shell.evaluate(script);
    }
}