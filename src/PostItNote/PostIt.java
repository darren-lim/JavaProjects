package PostItNote;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PostIt {

	public String SETTINGSNAME = "settings.txt";

	public PostIt stickyNote;

	public String postName;
	public String postPath;
	public String postContent;
	public String postFileName;
	public String cName;
	public String postTitle;

	public FileCheckers fileChecker;

	public JMenuBar mb;
	public JMenu menu;
	public JMenuItem m1, m2, m3;

	public JTextArea area;
	public JTextField titleText;

	public JFrame f;

	public BorderLayout layout;

	public JPanel buttonPanel;
	public JPanel textFieldPanel;
	public JPanel titlePanel;

	public class Colors {
		Color yellow = new Color(255, 250, 205);
		Color blue = new Color(135, 206, 250);
		Color green = new Color(144, 238, 144);
		Color grey = new Color(211, 211, 211);
	}

	public PostIt(String name, String path, String content, String colorName, String title) {
		// Create New Frame
		f = new JFrame("Post-It Notes");
		postName = name;
		postPath = path;
		postContent = content;
		cName = colorName;
		postTitle = title;
		fileChecker = new FileCheckers();

		Colors color = new Colors();

		// Set Frame Layout
		layout = new BorderLayout(0, 10);
		f.setLayout(layout);

		// set frame size and position
		f.setSize(300, 400);// 300 width and 400 height
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onClose();
				if (PostItMain.PostItArr.size() == 0) {
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
		});

		// Create a new Text Field
		textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new CardLayout(0, 10));

		// Set initial text field to the text from file
		area = new JTextArea(content);
		fileChecker.writeFile(postPath, postName, area.getText());
		area.setFont(new Font("Arial", Font.PLAIN, 18));

		// Add listeners to document updates, write to file
		area.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				postContent = area.getText();
				fileChecker.writeFile(postPath, postName, postContent);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				postContent = area.getText();
				fileChecker.writeFile(postPath, postName, postContent);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				postContent = area.getText();
				fileChecker.writeFile(postPath, postName, postContent);
			}
		});

		// Add a scroller to the text field
		JScrollPane scrollPane = new JScrollPane(area);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		textFieldPanel.add(scrollPane);
		textFieldPanel.setPreferredSize(textFieldPanel.getPreferredSize());

		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout(10, 0));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 2, 15));

		titleText = new JTextField(title);
		titleText.setBorder(BorderFactory.createEmptyBorder());
		titleText.setFont(new Font("Arial", Font.BOLD, 24));
		titleText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				postTitle = titleText.getText();
				writeToSetting(postPath, cName, postTitle);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				postTitle = titleText.getText();
				writeToSetting(postPath, cName, postTitle);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				postTitle = titleText.getText();
				writeToSetting(postPath, cName, postTitle);
			}
		});

		titlePanel.add(titleText, BorderLayout.NORTH);
		titlePanel.add(textFieldPanel, BorderLayout.CENTER);
		titlePanel.setPreferredSize(textFieldPanel.getPreferredSize());

		// Set Background Colors
		if (colorName.equals("Blue")) {
			setBackgroundColor(color.blue);
		} else if (colorName.equals("Green")) {
			setBackgroundColor(color.green);
		} else if (colorName.equals("Grey")) {
			setBackgroundColor(color.grey);
		} else {
			setBackgroundColor(color.yellow);
		}

		// Create Button Panel For Colors
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		addColorButtons(color, area);
		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());
		buttonPanel.setVisible(false);

		// Create Menu Bar
		mb = new JMenuBar();
		menu = new JMenu("Menu");
		m1 = new JMenuItem("New Note");
		m2 = new JMenuItem("Colors");
		// m3 = new JMenuItem("All Notes");

		m1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PostItMain.createNewPostIt("note", path, PostItMain.DEFAULTTEXTAREA, PostItMain.DEFAULTCOLOR,
						PostItMain.DEFAULTTITLE, false);
			}
		});

		m2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonPanel.isVisible()) {
					buttonPanel.setVisible(false);
				} else {
					buttonPanel.setVisible(true);
				}
			}
		});

		menu.add(m1);
		menu.add(m2);
		// menu.add(m3);

		mb.add(menu);

		f.setJMenuBar(mb);

		// Add all of the panels to the JFrame

		f.setPreferredSize(f.getPreferredSize());
		f.add(buttonPanel, BorderLayout.NORTH);
		f.add(titlePanel, BorderLayout.CENTER);

		// making the frame visible
		f.setVisible(true);
	}

	public void addColorButtons(Colors color, JTextArea area) {
		GridBagConstraints c = new GridBagConstraints();

		JButton yellowB = new JButton("");
		yellowB.setBorder(BorderFactory.createEmptyBorder());
		yellowB.setPreferredSize(new Dimension(10, 30));
		yellowB.setBackground(color.yellow);
		yellowB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBackgroundColor(color.yellow);
				cName = "Yellow";
				writeToSetting(postPath, cName, postTitle);
			}
		});

		JButton blueB = new JButton("");// creating instance of JButton
		blueB.setBorder(BorderFactory.createEmptyBorder());
		blueB.setPreferredSize(new Dimension(10, 30));
		blueB.setBackground(color.blue);
		blueB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBackgroundColor(color.blue);
				cName = "Blue";
				writeToSetting(postPath, cName, postTitle);
			}
		});

		JButton greenB = new JButton("");
		greenB.setBorder(BorderFactory.createEmptyBorder());
		greenB.setPreferredSize(new Dimension(10, 30));
		greenB.setBackground(color.green);
		greenB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBackgroundColor(color.green);
				cName = "Green";
				writeToSetting(postPath, cName, postTitle);
			}
		});

		JButton greyB = new JButton("");
		greyB.setBorder(BorderFactory.createEmptyBorder());
		greyB.setPreferredSize(new Dimension(10, 30));
		greyB.setBackground(color.grey);
		greyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBackgroundColor(color.grey);
				cName = "Grey";
				writeToSetting(postPath, cName, postTitle);
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(yellowB, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 0;
		buttonPanel.add(blueB, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 2;
		c.gridy = 0;
		buttonPanel.add(greenB, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 3;
		c.gridy = 0;
		buttonPanel.add(greyB, c);
	}

	public void writeToSetting(String dir, String color, String title) {
		String settingsContent = fileChecker.readFile(dir, SETTINGSNAME);
		String[] lineArr = settingsContent.split("\n");
		String newContent = "";
		for (int i = 0; i < lineArr.length; i++) {
			String[] lineSplit = lineArr[i].split(" ");
			if (lineSplit[0].equals(postName)) {
				newContent = lineSplit[0] + " " + color + " " + title;
				lineArr[i] = newContent;
				System.out.println("YES");
				break;
			}
		}
		String changedText = String.join("\n", lineArr);
		System.out.println(changedText);
		fileChecker.writeFile(dir, SETTINGSNAME, changedText);
	}

	public void onClose() {
		PostItMain.PostItArr.remove(this);
	}

	public void setBackgroundColor(Color bgColor) {
		f.getContentPane().setBackground(bgColor);
		textFieldPanel.setBackground(bgColor);
		area.setBackground(bgColor);
		titlePanel.setBackground(bgColor);
		titleText.setBackground(bgColor);
	}

	// public static void main(String[] args) {
	// stickyNote = new PostIt();
	// }

}
