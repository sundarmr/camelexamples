package com.camel.sql.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class CreateSampleFile {
	public static void main(String[] args) {
		try {
			File file = new File("/Users/smunirat/apps/myfile/orders.xml");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<orders>");
			
			for (int i = 0; i < 1000000; i++) {
				bw.write("<order><orderid>ord" + UUID.randomUUID().toString()
						+ "</orderid><productid>prd" + UUID.randomUUID().toString()
						+ "</productid><productName>" + RandomStringUtils.randomAlphabetic(10)
						+ "</productName><productDescription>" + RandomStringUtils.randomAlphabetic(99)
						+ "</productDescription><customerId>" + RandomStringUtils.randomAlphabetic(13)
						+ "</customerId><firstName>" + RandomStringUtils.randomAlphabetic(10)
						+ "</firstName><lastName>" + RandomStringUtils.randomAlphabetic(9) + "</lastName></order>");
			}
			bw.write("</orders>");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
