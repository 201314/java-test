package com.gitee.linzl.cipher;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws Exception {
		Provider[] providers = Security.getProviders();
		if (null != providers) {
			for (Provider provider : providers) {
				Set<Provider.Service> services = provider.getServices();
				for (Provider.Service service : services) {
					if ("Cipher".equals(service.getType())) {
						System.out.println(String.format("provider:%s,type:%s,algorithm:%s", service.getProvider(),
								service.getType(), service.getAlgorithm()));
					}
				}
			}
		}
	}
}