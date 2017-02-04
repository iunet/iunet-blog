package iunet.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class CaptchaEngineUtil extends ListImageCaptchaEngine {
	
	protected void buildInitialFactories() {
		int minWordLength = 6;
		int maxWordLength = 6;
		int fontSize = 50;
		int imageWidth = 250;
		int imageHeight = 100;

		WordGenerator wordGenerator = new RandomWordGenerator("0123456789abcdefghijklmnopqrstuvwxyz");

		RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(new int[] { 0, 150 }, new int[] { 0, 150 },
				new int[] { 0, 150 });
		LineTextDecorator lineTextDecorator = new LineTextDecorator(1, cgen);// 曲线干扰
		BaffleTextDecorator baffleTextDecorator = new BaffleTextDecorator(2, cgen);// 气泡干扰
		TextDecorator[] textDecorators = new TextDecorator[2];
		textDecorators[0] = lineTextDecorator;
		textDecorators[1] = baffleTextDecorator;
		
		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength,
				new RandomListColorGenerator(
						new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }),
				textDecorators);
		BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
		FontGenerator font = new RandomFontGenerator(fontSize, fontSize,
				new Font[] { new Font("nyala", Font.BOLD, fontSize), new Font("Bell MT", Font.PLAIN, fontSize),
						new Font("Credit valley", Font.BOLD, fontSize) });
		
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});

		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef,
				postDef);
		addFactory(new GimpyFactory(wordGenerator, word2image));
	}
}