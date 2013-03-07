package edu.bit.dlde.tools.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import edu.bit.dlde.utils.DLDELogger;

/**
 * 
 * 根据螺旋线的思想，做的仿微博关键词 词云 将各像素使用与否做了索引{@code BitMap}，提高搜寻效率
 * 
 */
public class WordsThumb {
	public static final int MIN_WIDTH = 320;
	public static final int MIN_HEIGHT = 240;
	public static final int MAX_WIDTH = 2560;
	public static final int MAX_HEIGHT = 1920;
	public static final int BLOCK = 4;
	public static final String DEF_FONT = "微软雅黑";
	public static final int MIN_FONT_SIZE = 12;
	private static final int[] COLORS = { 11682842, 14439168, 13081114,
			10300417, 14450176, 13052357, 11665801, 1739698, 101319, 84146,
			15580865, 1722823, 6664705, 11257601, 7582257, 11665680, 14425600,
			10768192, 14360836, 15515347, 111266, 49372, 15436032, 15436032 };

	public BufferedImage createWordsThumb(Map<String, Integer> words,
			int width, int height, String fontName, Integer maxFontSize,
			Integer minFontSize) {
		if ((words == null) || (words.size() < 1) || (width < 320)
				|| (width > 2560) || (height < 240) || (height > 1920))
			return null;
		fontName = validateFontName(fontName, "微软雅黑");
		List sortedWords = sortWordsMap(words);

		BufferedImage bi = createBufferedImage(width, height);
		if (bi == null) {
			return null;
		}
		if (maxFontSize == null) {
			maxFontSize = Integer.valueOf(Math.min(width, height) / 5);
		}
		if (minFontSize == null) {
			minFontSize = Integer.valueOf(Math.min(width, height) / 40);
		}
		if (minFontSize.intValue() < 12)
			minFontSize = Integer.valueOf(12);
		if (maxFontSize.intValue() < minFontSize.intValue()) {
			maxFontSize = minFontSize;
		}
		if (paintWords(bi, sortedWords, fontName, maxFontSize.intValue(),
				minFontSize.intValue()) < 1) {
			return null;
		}
		return bi;
	}

	private String validateFontName(String fontName, String defFont) {
		if (fontName != null) {
			Font font = new Font(fontName, 0, 16);
			if (font.getName().equals(fontName))
				return fontName;
		}
		return defFont;
	}

	@SuppressWarnings("unchecked")
	private List<Map.Entry<String, Integer>> sortWordsMap(
			Map<String, Integer> words) {
		if (words == null)
			return null;
		ArrayList wordsList = new ArrayList(words.entrySet());
		Collections.sort(wordsList, new Comparator() {
			// public int compare(Map.Entry<String, Integer> o1,
			// Map.Entry<String, Integer> o2)
			// {
			// return o2 == null ? -1 : o1 == null ? 1 :
			// ((Integer)o2
			// .getValue()).intValue() - ((Integer)o1.getValue()).intValue();
			// }

			public int compare(Object o1, Object o2) {
				Map.Entry<String, Integer> oe1 = (Map.Entry<String, Integer>) o1;
				Map.Entry<String, Integer> oe2 = (Map.Entry<String, Integer>) o2;

				return oe2 == null ? -1 : oe1 == null ? 1 : ((Integer) oe2
						.getValue()).intValue()
						- ((Integer) oe1.getValue()).intValue();
			}

		});
		return wordsList;
	}

	private BufferedImage createBufferedImage(int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, 2);
		Graphics2D g2 = bi.createGraphics();
		bi = g2.getDeviceConfiguration()
				.createCompatibleImage(width, height, 3);
		g2.dispose();
		return bi;
	}

	private int paintWords(BufferedImage bi,
			List<Map.Entry<String, Integer>> words, String fontName,
			int maxFontSize, int minFontSize) {
		BitMap bitMap = initBitMap(bi);
		Graphics2D g2 = bi.createGraphics();
		int wordCount = 0;
		int fontSizeAdjust = 0;
		int maxFrequency = ((Integer) ((Map.Entry) words.get(0)).getValue())
				.intValue();
		int minFrequency = ((Integer) ((Map.Entry) words.get(words.size() - 1))
				.getValue()).intValue();
		for (Map.Entry entry : words) {
			while (true) {
				Font font = initFont(g2, fontName,
						Integer.valueOf(maxFontSize),
						Integer.valueOf(minFontSize),
						(Integer) entry.getValue(),
						Integer.valueOf(maxFrequency),
						Integer.valueOf(minFrequency),
						Integer.valueOf(fontSizeAdjust));
				if (font.getSize() < minFontSize)
					return wordCount;
				Rectangle2D bounds = getStringBounds(g2, font,
						(String) entry.getKey());
				int direction = (int) (Math.random() * 10000.0D % 4.0D % 3.0D);
				Rectangle2D rect = bounds.getBounds();
				if (direction != 0) {
					rect.setRect(rect.getX(), rect.getY(), rect.getHeight(),
							rect.getWidth());
				}

				rect = findSpace(bitMap, rect);
				if (rect == null) {
					rect = bounds.getBounds();
					if (direction == 0) {
						direction = (int) (Math.random() * 10000.0D % 2.0D + 1.0D);
						rect.setRect(rect.getX(), rect.getY(),
								rect.getHeight(), rect.getWidth());
					} else {
						direction = 0;
					}
					rect = findSpace(bitMap, rect);
				}

				if (rect != null) {
					paintOneWord(bi, g2, (String) entry.getKey(), direction,
							rect, bounds);
					updateBitMap(bitMap, bi, rect);
					wordCount++;
					break;
				}

				fontSizeAdjust--;
			}
		}

		return wordCount;
	}

	private BitMap initBitMap(BufferedImage bi) {
		int w = (int) Math.ceil(bi.getWidth() / 4.0D);
		int h = (int) Math.ceil(bi.getHeight() / 4.0D);
		return new BitMap(w, h);
	}

	private Font initFont(Graphics2D g2, String fontName, Integer maxFontSize,
			Integer minFontSize, Integer frequency, Integer maxFrequency,
			Integer minFrequency, Integer fontSizeAdjust) {
		int fs = 24;
		if (maxFrequency.intValue() > minFrequency.intValue()) {
			fs = (int) ((frequency.intValue() - minFrequency.intValue())
					* (maxFontSize.intValue() - minFontSize.intValue())
					/ (maxFrequency.intValue() - minFrequency.intValue()) + minFontSize
					.intValue());
		}
		if (fontSizeAdjust != null) {
			fs += fontSizeAdjust.intValue();
		}
		Font font = new Font(fontName, 1, fs);
		g2.setFont(font);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		return font;
	}

	private Rectangle2D getStringBounds(Graphics2D g2, Font font, String key) {
		FontRenderContext frc = g2.getFontRenderContext();

		Rectangle2D rc = font.getStringBounds(key, frc);

		return rc;
	}

	private Rectangle2D findSpace(BitMap bitMap, Rectangle2D rect) {
		int w = pixel2bitMap((int) rect.getWidth());
		int h = pixel2bitMap((int) rect.getHeight());
		int boundW = bitMap.getWidth() - w;
		int boundH = bitMap.getHeight() - h;
		int start_x = boundW / 2;
		int start_y = boundH / 2;

		int maxBound = Math.max(boundW, boundH);
		double wRatio = boundW / maxBound;
		double hRatio = boundH / maxBound;

		double max_r = Math.sin(0.7853981633974483D) * maxBound;
		double r = 1.0D;
		double a = 0.0D;
		double a_tmp = 0.0D;
		double s = 1.0D;
		double step = 1.0D;
		int x = start_x;
		int y = start_y;
		while (r < max_r) {
			if (isClean(bitMap, x, y, w, h)) {
				int targetX = bit2pixel(x);
				int targetY = bit2pixel(y);
				return new Rectangle(targetX, targetY, (int) rect.getWidth(),
						(int) rect.getHeight());
			}
			a_tmp = s / r > 0.7853981633974483D ? 0.7853981633974483D : s / r;
			a += a_tmp;
			r += step * (a_tmp / 6.283185307179586D);
			x = (int) (Math.sin(a) * r * wRatio + start_x);
			y = (int) (Math.cos(a) * r * hRatio + start_y);
		}
		return null;
	}

	private Rectangle2D findSpace2(BitMap bitMap, Rectangle2D rect) {
		int w = pixel2bitMap((int) rect.getWidth());
		int h = pixel2bitMap((int) rect.getHeight());
		int[] bounds = { bitMap.getWidth() - w, bitMap.getHeight() - h };

		int[] step_len = new int[2];
		int marchDir = 0;
		int x = 0;
		int y = 0;
		if (bounds[0] > bounds[1]) {
			y = bounds[1] / 2;
			x = y;
			bounds[0] -= bounds[1];
			step_len[1] = 1;
		} else {
			marchDir = 3;
			x = bounds[0] / 2;
			y = x;
			step_len[0] = 1;
			bounds[1] -= bounds[0];
			if (step_len[1] == 0) {
				step_len[1] = 1;
			}
		}

		int[] step = new int[4];
		while (!isClean(bitMap, x, y, w, h)) {
			step[marchDir] += 1;
			if (step[marchDir] > step_len[(marchDir % 2)]) {
				step[marchDir] = 0;
				step_len[(marchDir % 2)] += 1;
				marchDir++;
				marchDir %= 4;
				if (step_len[(marchDir % 2)] > bounds[(marchDir % 2)]) {
					return null;
				}
			}
			switch (marchDir) {
			case 0:
				x++;
				break;
			case 1:
				y--;
				break;
			case 2:
				x--;
				break;
			case 3:
				y++;
			}

		}

		int targetX = bit2pixel(x);
		int targetY = bit2pixel(y);
		return new Rectangle(targetX, targetY, (int) rect.getWidth(),
				(int) rect.getHeight());
	}

	private int pixel2bitMap(int v) {
		return (int) Math.ceil(v / 4.0D);
	}

	private boolean isClean(BitMap bitMap, int x, int y, int w, int h) {
		if ((x < 0) || (x + w >= bitMap.getWidth()) || (y < 0)
				|| (y + h >= bitMap.getHeight()))
			return false;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				if (bitMap.isUsed(i, j))
					return false;
			}
		}
		return true;
	}

	private int bit2pixel(int v) {
		return v * 4;
	}

	private void paintOneWord(BufferedImage bi, Graphics2D g2, String word,
			int direction, Rectangle2D rect, Rectangle2D orgBounds) {
		g2.setPaint(randomColor());

		if (direction == 1) {
			g2.rotate(1.570796326794897D, rect.getX(), rect.getY());
			g2.drawString(word, (int) rect.getX(),
					(int) (rect.getY() - rect.getWidth() - orgBounds.getY()));
			g2.rotate(-1.570796326794897D, rect.getX(), rect.getY());
		} else if (direction == 2) {
			g2.rotate(-1.570796326794897D, rect.getX(), rect.getY());
			g2.drawString(word, (int) (rect.getX() - rect.getHeight()),
					(int) (rect.getY() - orgBounds.getY()));
			g2.rotate(1.570796326794897D, rect.getX(), rect.getY());
		} else {
			g2.drawString(word, (int) rect.getX(),
					(int) (rect.getY() - orgBounds.getY()));
		}

		bi.flush();
	}

	private Paint randomColor() {
		int rgb = COLORS[((int) (Math.random() * 10000.0D) % COLORS.length)];
		return new Color(rgb);
	}

	private void updateBitMap(BitMap bitMap, BufferedImage bi, Rectangle2D rect) {
		int l = (int) rect.getX();
		int t = (int) rect.getY();
		int r = l + (int) rect.getWidth();
		int b = t + (int) rect.getHeight();
		if (l < 0)
			l = 0;
		if (t < 0)
			t = 0;
		if (r > bi.getWidth())
			r = bi.getWidth();
		if (b > bi.getHeight())
			b = bi.getHeight();
		if ((r <= l) || (b <= t)) {
			return;
		}
		for (int y = t; y < b + 4 - 1; y += 4)
			for (int x = l; x < r + 4 - 1; x += 4)
				for (int i = 0; i < 16; i++) {
					int x2 = x + i % 4;
					int y2 = y + i / 4;
					if ((x2 >= bi.getWidth()) || (y2 >= bi.getHeight()))
						continue;
					if (bi.getRGB(x + i % 4, y + i / 4) != 0) {
						bitMap.setUsed(pixel2bitMap(x), pixel2bitMap(y), true);
						break;
					}
				}
	}

	public BufferedImage mixImages(BufferedImage[] images, Float[] alphas,
			int width, int height) {
		return mixImages(images, alphas, createBufferedImage(width, height));
	}

	public BufferedImage mixImages(BufferedImage[] images, Float[] alphas,
			BufferedImage targetImage) {
		if ((images == null) || (images.length < 1))
			return targetImage;
		if (targetImage == null) {
			for (int i = 0; i < images.length; i++) {
				if (images[i] != null) {
					targetImage = createBufferedImage(images[0].getWidth(),
							images[0].getHeight());
					break;
				}
			}
			if (targetImage == null)
				return null;
		}
		Graphics2D g2 = targetImage.createGraphics();
		float alpha = 1.0F;
		for (int i = 0; i < images.length; i++) {
			if (images[i] == null)
				continue;
			if ((alphas != null) && (i < alphas.length) && (alphas[i] != null))
				alpha = alphas[i].floatValue();
			else {
				alpha = 1.0F;
			}
			g2.setComposite(AlphaComposite.getInstance(3, alpha));
			g2.drawImage(images[i], 0, 0, targetImage.getWidth(),
					targetImage.getHeight(), 0, 0, images[i].getWidth(),
					images[i].getHeight(), null);
		}
		g2.dispose();
		targetImage.flush();
		return targetImage;
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./wordsthumb.png";
		String bgPic = null;
		String waterMarkPic = null;
		int width = 640;
		int height = 480;
		String fontName = "微软雅黑";
		Map words = new HashMap();
		try {
			int i = 0;
			for (i = 0; i < args.length; i++) {
				if ("-o".equals(args[i])) {
					i++;
					fileName = args[i];
				} else if ("-b".equals(args[i])) {
					i++;
					bgPic = args[i];
				} else if ("-m".equals(args[i])) {
					i++;
					waterMarkPic = args[i];
				} else if ("-w".equals(args[i])) {
					i++;
					width = Integer.parseInt(args[i]);
				} else if ("-h".equals(args[i])) {
					i++;
					height = Integer.parseInt(args[i]);
				} else {
					if (!"-f".equals(args[i]))
						break;
					i++;
					fontName = args[i];
				}

			}

			for (; i < args.length - 1; i += 2)
				words.put(args[i],
						Integer.valueOf(Integer.parseInt(args[(i + 1)])));
		} catch (RuntimeException e) {
			System.out.println("Usage: " + WordsThumb.class.getName()
					+ " [options] <word frequency>...");
			System.out.println("\t-o:\tOutput image file name");
			System.out.println("\t-b:\tBackground image file name");
			System.out.println("\t-m:\tWater mark image file name");
			System.out.println("\t-w:\tWidth of target image");
			System.out.println("\t-h:\tHeight of target image");
			System.out.println("\t-f:\tFont name");
			System.exit(1);
		}

		if (words.size() < 1) {
			readWords(words);
		}

		if (words.size() < 1) {
			return;
		}

		WordsThumb wt = new WordsThumb();
		BufferedImage bi = wt.createWordsThumb(words, width, height, fontName,
				null, null);
		BufferedImage bg = null;
		BufferedImage wm = null;
		Float fgAlpha = Float.valueOf(0.8F);
		Float wmAlpha = Float.valueOf(1.0F);
		if (bgPic != null)
			bg = ImageIO.read(new FileInputStream(bgPic));
		if (waterMarkPic != null) {
			wm = ImageIO.read(new FileInputStream(waterMarkPic));
		}
		bi = wt.mixImages(new BufferedImage[] { bg, bi, wm }, new Float[] {
				Float.valueOf(1.0F), fgAlpha, wmAlpha }, bi.getWidth(),
				bi.getHeight());

		File file = new File(fileName);
		ImageIO.write(bi, "png", file);
	}
private static DLDELogger logger=new DLDELogger();
	/**
	 * @param words 词:词重
	 * @param fileName 输出文件名
	 * @param bgPic 背景图片名
	 * @param waterMarkPic 水印图片名
	 * @return
	 * Mar 12, 2012
	 */
	public static File getWordCloud(Map<String, Integer> words, String fileName,String bgPic,String waterMarkPic) {
		File file = null;
		int width = 640;
		int height = 480;
		String fontName = "微软雅黑";
		if (words.size() < 1) {
			try {
				readWords(words);
			} catch (IOException e) {
				logger.error("生成词云的输入词有错误");
			}
		}
		if (words.size() < 1) {
			return null;
		}
		WordsThumb wt = new WordsThumb();
		BufferedImage bi = wt.createWordsThumb(words, width, height, fontName,
				null, null);
		BufferedImage bg = null;//背景图
		BufferedImage wm = null;//水印
		Float fgAlpha = Float.valueOf(0.8F);
		Float wmAlpha = Float.valueOf(1.0F);
		try {
			if (bgPic != null)
				bg = ImageIO.read(new FileInputStream(bgPic));
			if (waterMarkPic != null) {
				wm = ImageIO.read(new FileInputStream(waterMarkPic));
			}
			bi = wt.mixImages(new BufferedImage[] { bg, bi, wm }, new Float[] {
					Float.valueOf(1.0F), fgAlpha, wmAlpha }, bi.getWidth(),
					bi.getHeight());

			file = new File(fileName);
			ImageIO.write(bi, "png", file);
		} catch (FileNotFoundException e) {
			logger.error("生成词云--图片不存在，检查空白图是否存在");
		} catch (IOException e) {
			logger.error("生成词云--读片读取错误");
		} finally {
			return file;
		}
	}

	private static void readWords(Map<String, Integer> words)
			throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入词汇和词频，格式为每行一个词、一个空格、一个词频数字，以空行结束输入。");
		String line = null;
		while ((line = rd.readLine()) != null) {
			line = line.trim();
			if ("".equals(line)) {
				System.out.println("好，马上处理！");
				break;
			}
			String[] parts = line.split("\\s+");
			if (parts.length != 2)
				continue;
			try {
				words.put(parts[0], Integer.valueOf(Integer.parseInt(parts[1])));
			} catch (NumberFormatException e) {
				System.out.println("无效的输入。");
			}
		}
	}
}