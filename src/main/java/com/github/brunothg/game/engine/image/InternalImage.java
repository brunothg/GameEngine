package com.github.brunothg.game.engine.image;

import java.awt.Image;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

/**
 * Class for loading images right out the jar file
 * 
 * @author Marvin Bruns
 *
 */
public class InternalImage
{

	static Map<String, Image> loadedImages;
	static String root = "";

	static
	{
		loadedImages = new ConcurrentHashMap<String, Image>();
	}

	/**
	 * Load an image with specific path. Ignores the root folder.
	 * 
	 * @param path Path to the folder the image is located in
	 * @param name Name of the image
	 * @see #load(String)
	 * @return The loaded image or null, if any error occurred
	 */
	public static Image loadFromPath(String path, String name)
	{

		try
		{
			return fullLoadFromPath(path, name);
		}
		catch (IOException e)
		{
		}

		return null;
	}

	/**
	 * Load an image with specific path. Ignores the root folder.
	 * 
	 * @param path Path to the folder the image is located in
	 * @param name Name of the image
	 * @return The loaded image
	 * @throws IOException
	 */
	public static Image fullLoadFromPath(String path, String name) throws IOException
	{

		if (path == null)
		{
			path = "";
		}

		if (!path.isEmpty() && !path.endsWith("/"))
		{
			path = path + "/";
		}
		path = path + name;

		Image ret;
		ret = loadedImages.get(path);

		if (ret != null)
		{
			return ret;
		}

		ret = ImageIO.read(InternalImage.class.getResource(path));
		loadedImages.put(path, ret);

		return ret;
	}

	/**
	 * Load image from root folder
	 * 
	 * @see #loadFromPath(String, String)
	 * @param name Name of the image
	 * @return Image or null if any Exception is thrown
	 */
	public static Image load(String name)
	{
		return loadFromPath(getRootFolder(), name);
	}

	/**
	 * Reloads the image using root folder
	 * 
	 * @param name Name of the image
	 * @return The image
	 * @throws IOException if an error occurs during reading
	 */
	public static Image reloadFull(String name) throws IOException
	{

		return reloadFullFromPath(getRootFolder(), name);
	}

	/**
	 * Reloads the image
	 * 
	 * @param name Name of the image
	 * @param path Path to the folder the image is located in
	 * @return The image
	 * @throws IOException if an error occurs during reading
	 */
	public static Image reloadFullFromPath(String path, String name) throws IOException
	{

		if (path == null)
		{
			path = "";
		}

		if (!path.isEmpty() && !path.endsWith("/"))
		{
			path = path + "/";
		}
		path = path + name;

		Image ret = ImageIO.read(InternalImage.class.getResource(path));
		loadedImages.put(path, ret);

		return ret;

	}

	public static String getRootFolder()
	{
		return root;
	}

	public static void setRootFolder(String s)
	{
		if (s == null)
		{
			root = "";
			return;
		}

		root = s;

		if (!root.isEmpty() && !root.endsWith("/"))
		{
			root += "/";
		}
	}

	/**
	 * Load an image from given path relative to root folder. If it has been loaded before a
	 * reference is returned.
	 * 
	 * @param name image name
	 * @return The image
	 * @throws IOException if an error occurs during reading
	 */
	public static Image fullLoad(String name) throws IOException
	{

		return fullLoadFromPath(getRootFolder(), name);
	}

	/**
	 * All loaded Images will be released. The next call to load a image will read it from file not
	 * memory.
	 */
	public static void clearMemory()
	{
		loadedImages.clear();
	}

	/**
	 * Release a specific image if it exists.
	 * 
	 * @param path Path to image's folder
	 * @param name image's name
	 */
	public static void freeImage(String path, String name)
	{
		loadedImages.remove(path + name);
	}

	/**
	 * Release a specific image if it exists.
	 * 
	 * @param name image's name
	 */
	public static void freeImage(String name)
	{
		loadedImages.remove(getRootFolder() + name);
	}

	/**
	 * Manually insert a image
	 * 
	 * @param path path of the image (Identifier)
	 * @param img the image that was stored previously at this position
	 * @return the image that was previously at this position
	 */
	public static Image storeImage(String path, String name, Image img)
	{

		return loadedImages.put(path + name, img);
	}
}
