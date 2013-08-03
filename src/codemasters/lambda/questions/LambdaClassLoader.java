package codemasters.lambda.questions;

import java.security.*;


public class LambdaClassLoader extends SecureClassLoader {
	
	public LambdaClassLoader() {
		super();
	}
	
	public LambdaClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		System.out.println(name);
		return super.loadClass(name);
	}
	
	
}


