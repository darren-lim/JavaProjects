package PostItNote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostItMain {

	public static List<PostIt> PostItArr = new ArrayList<PostIt>();

	public static FileCheckers fc = new FileCheckers();

	public static String DEFAULTCOLOR = "Yellow";
	public static String DEFAULTTITLE = "Title";
	public static String DEFAULTTEXTAREA = "New Note";

	public class FilePaths {
		public String PATH = "C:/";
		public String DIRNAME = PATH + "PostItNotes/";
		public String NAME = "note";
		public String EXTENSION = ".txt";

		public String SETTINGSNAME = "settings.txt";
	}

	public static void main(String[] args) {
		PostItMain.FilePaths names = new PostItMain().new FilePaths();
		// later on check multiple files in directory
		try {
			// if dir exists
			if (fc.checkDirExists(names.DIRNAME)) {
				File[] files = fc.getFileList(names.DIRNAME);
				// if no files in dir
				if (files.length == 0) {
					newPostMain(names.NAME, names.EXTENSION, names.DIRNAME, names.SETTINGSNAME);
					// else if there are files in dir
				} else {
					String[] lineArr = {};
					String colorName = DEFAULTCOLOR;
					String titleText = DEFAULTTITLE;
					String fileContent = DEFAULTTEXTAREA;
					if (fc.checkFileExists(names.DIRNAME, names.SETTINGSNAME)) {
						String colors = fc.readFile(names.DIRNAME, names.SETTINGSNAME);
						lineArr = colors.split("\n");
					}
					for (File file : files) {
						String fileName = file.getName();
						if (fileName.equals(names.SETTINGSNAME)) {
							continue;
						}
						for (String colorOfPost : lineArr) {
							String[] splitLine = colorOfPost.split(" ");
							if (splitLine[0].equals(fileName)) {
								colorName = splitLine[1];
								titleText = splitLine[2];
								break;
							}
						}
						fileContent = fc.readFile(names.DIRNAME, fileName);
						createNewPostIt(fileName, names.DIRNAME, fileContent, colorName, titleText, true);
						// PostIt stickyNote = new PostIt(fileName, names.DIRNAME, fileContent, count);
					}
				}
				// else dir does not exist, create a new file
			} else {
				newPostMain(names.NAME, names.EXTENSION, names.DIRNAME, names.SETTINGSNAME);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void newPostMain(String name, String ext, String dir, String settingName) {
		String newName = name + "1" + ext;
		try {
			fc.checkFileExists(dir, newName);
			fc.checkFileExists(dir, settingName);
			createNewPostIt(newName, dir, DEFAULTTEXTAREA, DEFAULTCOLOR, DEFAULTTITLE, true);
			fc.writeFile(dir, settingName, newName + " " + DEFAULTCOLOR + " " + DEFAULTTITLE + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createNewPostIt(String newName, String dirName, String content, String colorName, String title,
			boolean start) {
		// -1 is on start of program
		if (start) {
			PostIt stickyNote = new PostIt(newName, dirName, content, colorName, title);
			PostItArr.add(stickyNote);
		} else {
			// created by new note function
			int lastNum = getLastNoteNum(dirName);
			String noteName = newName + "_" + Integer.toString(lastNum + 1) + ".txt";
			try {
				fc.checkFileExists(dirName, noteName);
				PostIt stickyNote = new PostIt(noteName, dirName, content, colorName, title);
				PostItArr.add(stickyNote);
				String settingsStr = fc.readFile(dirName, "settings.txt");
				fc.writeFile(dirName, "settings.txt", settingsStr + noteName + " " + colorName + " " + title + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getLastNoteNum(String dir) {
		File[] files = fc.getFileList(dir);
		// the last file is settings, the last note file is the 2nd to last
		File lastFile = files[files.length - 2];
		String fileName = lastFile.getName();
		String line = fc.readFile(dir, fileName);
		String[] splitLine = line.split(" ");
		String[] splitName = splitLine[0].split("_");
		int num = Integer.parseInt(splitName[1]);
		return num;
	}

	public static void closePost(PostIt post) {
		PostItArr.remove(post);
	}
}

/**
 * Title -> note_1.txt yellow "Title" deleting: delete note with the title ex.
 * note1: groceries, note2: games delete groceries note2: games create new note:
 * takes most recent note and keeps going higher ex. note1, note2, note 4,
 * note10 -> will make note 11
 * 
 * 
 */
