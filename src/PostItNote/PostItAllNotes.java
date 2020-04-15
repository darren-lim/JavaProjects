package PostItNote;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PostItAllNotes implements ActionListener {

	public static PostItAllNotes allNotes;

	private JFrame frame;
	private JPanel listPanel;

	private BorderLayout layout;

	private HashMap<String, PostIt> buttonMap = new HashMap<>();

	public PostItAllNotes() {
		// System.out.println("Create all notes");
		frame = new JFrame("All Notes");

		layout = new BorderLayout();
		frame.setSize(300, 400);
		frame.setLayout(layout);
		frame.setPreferredSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				allNotes = null;
			}
		});

		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setBorder(BorderFactory.createEmptyBorder());
		listPanel.setPreferredSize(listPanel.getPreferredSize());

		addButtons();
		// listPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// frame.add(listPanel, BorderLayout.CENTER);
		frame.add(listPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String title = e.getActionCommand();
		PostIt post = buttonMap.get(title);
		post.bringBack();
	}

	public void bringBack() {
		frame.setVisible(true);
		frame.toFront();
	}

	public void repaint() {
		frame.getContentPane().removeAll();
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setBorder(BorderFactory.createEmptyBorder());
		listPanel.setPreferredSize(listPanel.getPreferredSize());

		addButtons();

		frame.add(listPanel, BorderLayout.CENTER);
		frame.setVisible(false);
		frame.setVisible(true);
	}

	public void addButtons() {
		for (int i = 0; i < PostItMain.PostItArr.size(); i++) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			String title = PostItMain.PostItArr.get(i).gettitle();
			if (buttonMap.get(title) != null) {
				title = title + "(" + Integer.toString(i) + ")";
			}
			JButton button = new JButton(title);
			buttonMap.put(title, PostItMain.PostItArr.get(i));
			button.addActionListener(this);
			panel.add(button);
			listPanel.add(panel);
		}
		System.out.println("add");
	}

}
