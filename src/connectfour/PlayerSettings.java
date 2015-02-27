package connectfour;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.SpringLayout;

public class PlayerSettings extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 303599265726881278L;
	private JLabel name;
	private SpringLayout layout;
	private JComboBox<String> players;
	private JPanel aiOptions, depth;
	private JFormattedTextField depthTextField;
	private HumanPlayer human;
	
	public PlayerSettings(String name, HumanPlayer human) {
		this.name = new JLabel(name);
		this.human = human;
		
		layout = new SpringLayout();
        setLayout(layout);
        
        /** title label **/
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.name, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, this.name, 10, SpringLayout.NORTH, this);
        add(this.name);
        
        /** ai options **/
        aiOptions = new JPanel();
        aiOptions.setLayout(new BoxLayout(aiOptions, BoxLayout.PAGE_AXIS));
        
        depth = new JPanel();
        depthTextField = new JFormattedTextField(new Integer(6));
        depthTextField.setColumns(2);
        depth.add(new JLabel("Depth:"));
        depth.add(depthTextField);
        
        aiOptions.add(depth);
        aiOptions.add(new JButton("Coefficients"));
        
        aiOptions.setVisible(false);
        
        /** player type dropdown **/
        players = new JComboBox<String>(new String[] {"Human", "AI"});
        
        players.addActionListener(this);
        
        layout.putConstraint(SpringLayout.NORTH, players, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, players, 25, SpringLayout.WEST, this);
        add(players);
        
        layout.putConstraint(SpringLayout.NORTH, aiOptions, 35, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, aiOptions, -30, SpringLayout.EAST, players);
        add(aiOptions);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == players) {
			aiOptions.setVisible(players.getSelectedItem().toString().equals("AI"));
		}
	}
	
	public Player getPlayer() {
		if (players.getSelectedItem().toString().equals("Human")) {
			return human;
		}
		else {
			return new AIPlayer(Integer.parseInt(depthTextField.getText()));
		}
	}
	
	
}
