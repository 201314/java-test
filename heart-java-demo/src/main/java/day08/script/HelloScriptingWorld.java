package day08.script;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class HelloScriptingWorld {
	public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {

		ScriptEngineManager factory = new ScriptEngineManager();
		// 打印出当前JDK所能支持的所有脚本引擎
		for (ScriptEngineFactory available : factory.getEngineFactories()) {
			System.out.println(available.getEngineVersion());
			System.out.println(available.getEngineName());
			System.out.println(available.getLanguageVersion());
			System.out.println(available.getLanguageName());
		}

		// ScriptEngineManager 可以使用以下三种方式返回脚本引擎：
		// JS的名称首字母必须大写
		// 通过引擎或语言的名称，比如说 清单 1 请求 JavaScript 引擎。
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		// 通过该语言脚本共同使用的文件扩展名，比如说 Ruby 脚本的 .rb。
		// engine = factory.getEngineByExtension("js");
		// 通过脚本引擎声明的、知道如何处理的 MIME 类型。
		// engine =
		// factory.getEngineByMimeType("application/javascript");
		// 建立上下文变量
		Bindings bindings = engine.createBindings();
		// 绑定上下文，作用域是当前引擎范围
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		InputStream path = ClassLoader.getSystemResourceAsStream("sayHello.js");
		engine.eval(new InputStreamReader(path));

		System.out.println("\nCalling defineScript...");
		defineScript(engine);

		System.out.println("\nCalling invokeScriptFromJava...");
		invokeScriptFromJava(engine);

		System.out.println("\nCalling invokeJavaFromScript...");
		invokeJavaFromScript(engine);
	}

	/**
	 * 用script引擎定义js函数
	 * 
	 * @param engine
	 * @throws ScriptException
	 */
	private static void defineScript(ScriptEngine engine) throws ScriptException {
		engine.eval("function sayHello(name) { console.log('defineScript-->' + name)}");
		engine.eval("sayHello('Hellow World!')");
	}

	/**
	 * 使用java接口调用js函数
	 * 
	 * @param engine
	 * @throws ScriptException
	 * @throws NoSuchMethodException
	 */
	private static void invokeScriptFromJava(ScriptEngine engine) throws ScriptException, NoSuchMethodException {
		if (engine instanceof Invocable) {
			Invocable invocableEngine = (Invocable) engine;
			invocableEngine.invokeFunction("sayHello", "sayHello JS方法的参数值");
		}
	}

	/**
	 * JS调用java方法
	 * 
	 * @param engine
	 * @throws ScriptException
	 */
	private static void invokeJavaFromScript(ScriptEngine engine) throws ScriptException {
		engine.put("helloScriptingWorld", new HelloScriptingWorld());
		engine.eval("var msg = helloScriptingWorld.getHelloReply('JavaScript');");
		engine.eval("console.log('Java returned:' + msg);");
	}

	/**
	 * 使用importPackage或importClass导入包或类
	 * 
	 * @param engine
	 * @throws ScriptException
	 */
	private static void invokeImport(ScriptEngine engine) throws ScriptException {
		// 导入一个包
		engine.eval("importPackage(Packages.com.linzl.cn.script);");
		// 导入一个类
		// engine.eval("importClass(com.linzl.cn.script.HelloScriptingWorld);");

		engine.eval("var helloScriptingWorld2=new HelloScriptingWorld();");
		engine.eval("var msg2 = helloScriptingWorld2.getHelloReply('invokeJavaFromScript2-->JavaScript');");
		engine.eval("console.log('invokeJavaFromScript2--Java returned:' + msg2);");
	}

	/**
	 * 使用JavaImporter导入包或类
	 * 
	 * @param engine
	 */
	private static void invokeJavaImporter(ScriptEngine engine) throws ScriptException {
		engine.eval("var myImport = JavaImporter(Packages.com.linzl.cn.script);");
		// engine.eval("var myImport =
		// JavaImporter(com.linzl.cn.script.HelloScriptingWorld);");
		engine.eval("with(myImport){" + "var scriptTest=new HelloScriptingWorld();"
				+ "var msg2 = scriptTest.getHelloReply('invokeJavaFromScript2-->JavaScript');"
				+ "println('invokeJavaFromScript2--Java returned:' + msg2);}");
	}

	/** Method invoked from the above script to return a string. */
	public String getHelloReply(String name) {
		return "Java method getHelloReply says, 'Hello, " + name + "'";
	}
}