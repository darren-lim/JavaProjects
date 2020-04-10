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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PostIt {

	String PATH = "C:/";
	String DIRNAME = PATH.concat("PostItNotes/");
	String NAME = "note1.txt";

	public static PostIt stickyNote;

	public JFrame f;

	public BorderLayout layout;

	public JPanel buttonPanel;

	public JPanel textFieldPanel;

	public class Colors {
		Color yellow = new Color(255, 250, 205);
		Color blue = new Color(135, 206, 250);
		Color green = new Color(144, 238, 144);
		Color grey = new Color(211, 211, 211);
	}

	public PostIt() {
		f = new JFrame("TestFrame");
		Colors color = new Colors();
		layout = new BorderLayout(0, 20);
		f.setLayout(layout);
		f.setSize(300, 400);// 400 width and 500 height
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setBackground(color.yellow);

		textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new CardLayout(20, 0));

		String readText = "";
		// later on check multiple files in directory
		try {
			if (checkFileExists(NAME)) {
				readText = readFile(NAME);
			} else {
				readText = "New Note";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			readText = "New Note";
		}

		JTextArea area = new JTextArea(readText);
		area.setBackground(color.yellow);
		area.setFont(new Font("Arial", Font.PLAIN, 18));
		area.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				writeFile(NAME, area.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				writeFile(NAME, area.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				writeFile(NAME, area.getText());
			}
		});
		// area.setBounds(20, 100, 300, 300);
		textFieldPanel.setBackground(color.yellow);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		addColorButtons(color, area);
		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());

		textFieldPanel.add(area);
		textFieldPanel.setPreferredSize(textFieldPanel.getPreferredSize());

		f.setPreferredSize(f.getPreferredSize());
		f.add(buttonPanel, BorderLayout.NORTH);
		f.add(textFieldPanel, BorderLayout.CENTER);

		f.setVisible(true);// making the frame visible
	}

	public void addColorButtons(Colors color, JTextArea area) {
		GridBagConstraints c = new GridBagConstraints();

		JButton yellowB = new JButton("");
		yellowB.setBorder(BorderFactory.createEmptyBorder());
		yellowB.setPreferredSize(new Dimension(10, 30));
		yellowB.setBackground(color.yellow);
		yellowB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.getContentPane().setBackground(color.yellow);
				textFieldPanel.setBackground(color.yellow);
				area.setBackground(color.yellow);
			}
		});

		JButton blueB = new JButton("");// creating instance of JButton
		blueB.setBorder(BorderFactory.createEmptyBorder());
		blueB.setPreferredSize(new Dimension(10, 30));
		blueB.setBackground(color.blue);
		blueB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.getContentPane().setBackground(color.blue);
				textFieldPanel.setBackground(color.blue);
				area.setBackground(color.blue);
			}
		});

		JButton greenB = new JButton("");
		greenB.setBorder(BorderFactory.createEmptyBorder());
		greenB.setPreferredSize(new Dimension(10, 30));
		greenB.setBackground(color.green);
		greenB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.getContentPane().setBackground(color.green);
				textFieldPanel.setBackground(color.green);
				area.setBackground(color.green);
			}
		});

		JButton greyB = new JButton("");
		greyB.setBorder(BorderFactory.createEmptyBorder());
		greyB.setPreferredSize(new Dimension(10, 30));
		greyB.setBackground(color.grey);
		greyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.getContentPane().setBackground(color.grey);
				textFieldPanel.setBackground(color.grey);
				area.setBackground(color.grey);
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

	public boolean checkFileExists(String fileName) throws IOException {
		File directory = new File(DIRNAME);
		File file = new File(DIRNAME + "/" + fileName);
		if (!directory.exists()) {
			directory.mkdir();
			return false;
		} else if (!file.isFile()) {
			// create file
			file.createNewFile();
			return false;
		}
		return true;
	}

	public void writeFile(String title, String value) {

		String path = DIRNAME + "/" + title;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(value);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public String readFile(String title) {
		String path = DIRNAME + "/" + title;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			String wholeString = "";
			while (line != null) {
				wholeString += line + "\n";
				line = br.readLine();
			}
			br.close();
			if (wholeString == "") {
				return "";
			}
			return wholeString.substring(0, wholeString.length() - 1);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return "";
	}

	// public static void main(String[] args) {
	// stickyNote = new PostIt();
	// }

}
