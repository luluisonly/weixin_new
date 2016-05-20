package com.bokesoft.thirdparty.weixin.common;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

public class Kaptcha {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		long old = System.currentTimeMillis();
//		 for (int i = 0; i < 100; i++) {
		writeImage2OutputStream(generate(randomCode(4), 200, 50, 40), new FileOutputStream(
				new File("aaa.jpg")));
//		 }
		System.out.println(System.currentTimeMillis() - old);
	}

	private static String[] words = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2",
			"3", "4", "5", "6", "7", "8", "9" };

	public static String randomCode(int wordLength) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < wordLength; i++) {
			int temp = random.nextInt(36);
			String rand = words[temp];
			sRand += rand;
		}
		return sRand;
	}

	public static void writeImage2OutputStream(BufferedImage image, OutputStream outputStream)
			throws IOException {
		ImageIO.write(image, "JPEG", outputStream);
	}

	private static class MyConfig extends Config {

		private Font[] getFonts(String paramName, String paramValue, int fontSize,
				Font[] defaultFonts) {
			Font[] fonts;
			if ("".equals(paramValue) || paramValue == null) {
				fonts = defaultFonts;
			} else {
				String[] fontNames = paramValue.split(",");
				fonts = new Font[fontNames.length];
				for (int i = 0; i < fontNames.length; i++) {
					fonts[i] = new Font(fontNames[i],Font.BOLD, fontSize);
				}
			}
			return fonts;
		}

		public MyConfig(Properties properties) {
			super(properties);
		}

		public Font[] getTextProducerFonts(int fontSize) {
			String paramName = Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;
			String paramValue = this.getProperties().getProperty(paramName);
			return getFonts(paramName, paramValue, fontSize, new Font[]{
					new Font("Arial", Font.BOLD, fontSize), new Font("Courier", Font.BOLD, fontSize)
			});
			
//			return getFonts(paramName, paramValue, fontSize, new Font[]{
//					new Font("Arial", Font.PLAIN, fontSize), new Font("Courier", Font.PLAIN, fontSize)
//			});
		}

	}

	public static BufferedImage generate(String text, int width, int height, int fontSize)
			throws IOException {
		Properties properties = new Properties();
		properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, String.valueOf(width));
		properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, String.valueOf(height));
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, String.valueOf(fontSize));
//		properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
//		properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.FishEyeGimpy");
		
		
		MyConfig config = new MyConfig(properties);
		Producer producer = config.getProducerImpl();
		BufferedImage bufferedImage = producer.createImage(text);
		return bufferedImage;
	}
}