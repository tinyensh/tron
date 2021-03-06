package tronPackage;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class newAccountPage extends JPanel {
	
	JPanel parentPanel;
	PrintWriter pw;
	
	JLabel newUsernameLabel;
	JLabel newPasswordLabel;
	JLabel confirmPasswordLabel;
	
	JTextField newUsernameField;
	JPasswordField newPasswordField;
	JPasswordField confirmPasswordField;
	
	JButton createAccountButton;
	JButton cancelCreateAccountButton;
	TronPlayer tp;
	
	public newAccountPage(PrintWriter pw, JPanel parent, TronPlayer tp) {
		super();
		this.setSize(800,800);
		this.pw = pw;
		this.parentPanel = parent;
		this.tp = tp;
		
		
		newUsernameLabel = new JLabel("Username");
		newPasswordLabel = new JLabel("Password");
		confirmPasswordLabel = new JLabel("Confirm Password");
		
		newUsernameField = new JTextField(10);
		newPasswordField = new JPasswordField(10);
		confirmPasswordField = new JPasswordField(10);
		
		createAccountButton = new JButton("Create");
		cancelCreateAccountButton = new JButton("Cancel");
				
		createAccountButton.addActionListener(new newAccountPageButton(tp, parentPanel, newUsernameField, newPasswordField, confirmPasswordField));
		
		cancelCreateAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newUsernameField.setText("");
				newPasswordField.setText("");
				confirmPasswordField.setText("");
				CardLayout c1 = (CardLayout) parentPanel.getLayout();
				c1.show(parentPanel, "loginPage");
			}
			
		});
		
		this.addComponents();

		
		JLabel image = new JLabel(new ImageIcon("../loginPageBackground.jpg"));
		this.add(image);
	}
	
	void addComponents(){
		this.add(newUsernameLabel);
		this.add(newUsernameField);
		this.add(newPasswordLabel);
		this.add(newPasswordField);
		this.add(confirmPasswordLabel);
		this.add(confirmPasswordField);
		this.add(createAccountButton);
		this.add(cancelCreateAccountButton);
	}

	static boolean checkIfNameExists(String name){
		try{
			FileReader fr = new FileReader("./log.txt");
			BufferedReader br = new BufferedReader(fr);
			boolean nameFound = false;
			String line = br.readLine();
			String array[] = null;

			while(line != null){
				array = line.split(":");
				if(array[0].equals(name)){
					nameFound = true;
					break;
				}
				line = br.readLine();
			}

			br.close();
			fr.close();
			return nameFound;
		}catch(IOException ioe){

		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}




class newAccountPageButton implements ActionListener {

	TronPlayer tp;
	JPanel parent;
	JTextField usernameField;
	JPasswordField password;
	JPasswordField confirmPassword;

	newAccountPageButton(TronPlayer tp, JPanel parent, JTextField username, JPasswordField password, JPasswordField confirmPasswordField){
		super();
		this.tp = tp;
		this.parent = parent;
		this.usernameField = username;
		this.password = password;
		this.confirmPassword = confirmPasswordField;
	}

	public void actionPerformed(ActionEvent ae){
		if(usernameField.getText().equals("")){
			return;
		}
		if(!(new String(password.getPassword()).equals(new String(confirmPassword.getPassword())))){
			return;
		}
		if(!newAccountPage.checkIfNameExists(usernameField.getText())){
			try{
				FileWriter fw = new FileWriter("./log.txt", true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(usernameField.getText() + ":" + new String(password.getPassword()) + ":0:0");
				pw.close();
				fw.close();
			}catch(IOException ioe){

			}
			CardLayout c1 = (CardLayout) parent.getLayout();
			tp.setPlayerName(usernameField.getText());
			c1.show(parent, "homePage");
		}

		//left over from last implemented button. didn't want to delete incase we wanted it later

		/*//TODO: Check database for existing user with username
				boolean usernameExists = false;
				if(usernameExists) {
					
				} else if(Arrays.equals(newPasswordField.getPassword(), confirmPasswordField.getPassword()) && newPasswordField.getPassword().length > 8) {
					String username = newUsernameField.getText();
					char[] password = newPasswordField.getPassword();
					//TODO: create new User in database
					
					newUsernameField.setText("");
					newPasswordField.setText("");
					confirmPasswordField.setText("");
					CardLayout c1 = (CardLayout) parentPanel.getLayout();
					c1.show(parentPanel, "loginPage");
				} else {
					System.out.println("INVALID PASSWORD");
				}*/
	}
}
