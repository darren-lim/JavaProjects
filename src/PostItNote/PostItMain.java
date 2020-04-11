package PostItNote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostItMain {

	public static List<PostIt> PostItArr = new ArrayList<PostIt>();

	public static FileCheckers fc = new FileCheckers();

	public class FilePaths {
		public String PATH = "C:/";
		public String DIRNAME = PATH + "PostItNotes/";
		public String NAME = "note_";
		public String EXTENSION = ".txt";

		public String SETTINGSNAME = "settings.txt";
	}

	public static void main(String[] args) {
		PostItMain.FilePaths names = new PostItMain().new FilePaths();
		// later on check multiple files in directory
		try {
			if (fc.checkDirExists(names.DIRNAME)) {
				File[] files = fc.getFileList(names.DIRNAME);
				if (files.length == 0) {
					fc.checkFileExists(names.DIRNAME, names.SETTINGSNAME);

					String newName = names.NAME + "1" + names.EXTENSION;
					fc.checkFileExists(names.DIRNAME, newName);
					createNewPostIt(newName, names.DIRNAME, "New Note", "Yellow", true);
					// PostIt stickyNote = new PostIt(newName, names.DIRNAME, "New Note", 1);

					fc.writeFile(names.DIRNAME, names.SETTINGSNAME, newName + " Yellow\n");

				} else {
					String[] colorArr = {};
					if (fc.checkFileExists(names.DIRNAME, names.SETTINGSNAME)) {
						String colors = fc.readFile(names.DIRNAME, names.SETTINGSNAME);
						colorArr = colors.split("\n");
					}
					for (File file : files) {
						String fileName = file.getName();
						if (fileName.equals(names.SETTINGSNAME)) {
							continue;
						}
						String colorName = "Yellow";
						for (String colorOfPost : colorArr) {
							String[] nameAndColArr = colorOfPost.split(" ");
							if (nameAndColArr[0].equals(fileName)) {
								colorName = nameAndColArr[1];
							}
						}
						String fileContent = fc.readFile(names.DIRNAME, fileName);
						createNewPostIt(fileName, names.DIRNAME, fileContent, colorName, true);
						// PostIt stickyNote = new PostIt(fileName, names.DIRNAME, fileContent, count);
					}
				}
			} else {
				String newName = names.NAME + "1" + names.EXTENSION;
				fc.checkFileExists(names.DIRNAME, newName);
				fc.checkFileExists(names.DIRNAME, names.SETTINGSNAME);
				createNewPostIt(newName, names.DIRNAME, "New Note", "Yellow", true);
				fc.writeFile(names.DIRNAME, names.SETTINGSNAME, newName + " Yellow\n");
				// PostIt stickyNote = new PostIt(newName, names.DIRNAME, "New Note", 1);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void createNewPostIt(String newName, String dirName, String content, String colorName,
			boolean start) {
		// -1 is on start of program
		if (start) {
			PostIt stickyNote = new PostIt(newName, dirName, content, colorName);
			PostItArr.add(stickyNote);
		} else {
			// created by new note function
			String noteName = newName + "_" + Integer.toString(PostItArr.size() + 1) + ".txt";
			try {
				fc.checkFileExists(dirName, noteName);
				PostIt stickyNote = new PostIt(noteName, dirName, content, "Yellow");
				PostItArr.add(stickyNote);
				String settingsStr = fc.readFile(dirName, "settings.txt");
				fc.writeFile(dirName, "settings.txt", settingsStr + noteName + " Yellow\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closePost(PostIt post) {
		PostItArr.remove(post);
	}
}
