/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import renderer.ImageWriter;
import primitives.Color;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for renderer.ImageWriter class
 * 
 * @author Maayan & Renana
 *
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */

	@Test
	void testWriteToImage() {
		ImageWriter image = new ImageWriter("firstImage", 800, 500);

		for (int i = 0; i < 800; i++) {
			for (int j = 0; j < 500; j++) {
				if (i % 50 == 0 || j % 50 == 0)
					image.writePixel(i, j, new Color(255, 255, 255));
				else
					image.writePixel(i, j, new Color(51, 204, 255));
			}
		}
		image.writeToImage();

	}

}
