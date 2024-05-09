package utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;

public class XImage {
	public static Image getAppIcon() {
		URL url = XImage.class.getResource("/icon/fpt.png");
		return new ImageIcon(url).getImage();
	}

	public static boolean save(File EduSys) {
		File dstFile = new File("resources\\icon", EduSys.getName());
		if (!dstFile.getParentFile().exists()) {
			dstFile.getParentFile().mkdirs();
		}
		try {
			Path from = Paths.get(EduSys.getAbsolutePath());
			Path to = Paths.get(dstFile.getAbsolutePath());
			Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static ImageIcon read(String fileName) {
		File path = new File("resources\\\\icon", fileName);
		return new ImageIcon(path.getAbsolutePath());
	}
}
