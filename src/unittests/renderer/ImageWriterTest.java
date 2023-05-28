package unittests.renderer;

import renderer.ImageWriter;
import primitives.Color;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for renderer.ImageWriter class
 * 
 * @author Maayan &amp; Renana
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	void testWriteToImage() {
		final int width = 800;
		final int height = 500;
		final int step = 50;
		final Color white = new Color(255, 255, 255);
		final Color bluish = new Color(51, 204, 255);
		ImageWriter image = new ImageWriter("firstImage", width, height);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				image.writePixel(i, j, i % step == 0 || j % step == 0 ? white : bluish);
		image.writeToImage();
	}

}
