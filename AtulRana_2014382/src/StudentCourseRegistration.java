import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextPane;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentCourseRegistration {
	private Connection connection;
	
	private JFrame frame;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtContact;
	private JTextField txtCourse;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentCourseRegistration window = new StudentCourseRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public void refreshTable() {
		try {
			String query = "select * from StudentInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			txtId.setText(null);
			txtName.setText(null);
			txtContact.setText(null);
			txtCourse.setText(null);
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	public StudentCourseRegistration() {
		initialize();
		connection = SqlLiteConnection.dbConnector();
		refreshTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
try {
					
					int row = table.getSelectedRow();
					String ID = table.getModel().getValueAt(row, 0).toString();
					String query = "select * from StudentInfo where ID ='" + ID + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs =  pst.executeQuery();
					
					while(rs.next()) {
						
						txtId.setText(rs.getString("ID"));
						txtName.setText(rs.getString("Name"));
						txtContact.setText(rs.getString("Contact"));
						txtCourse.setText(rs.getString("Course"));
					}
					
					pst.close();
					rs.close();
					
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
					
				}
				
				
				
				
				
			}
		});
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().setBackground(Color.CYAN);
		frame.getContentPane().setLayout(null);
		
		txtId = new JTextField();
		txtId.setBounds(157, 145, 96, 20);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(157, 176, 96, 20);
		frame.getContentPane().add(txtName);
		
		txtContact = new JTextField();
		txtContact.setColumns(10);
		txtContact.setBounds(157, 207, 96, 20);
		frame.getContentPane().add(txtContact);
		
		txtCourse = new JTextField();
		txtCourse.setColumns(10);
		txtCourse.setBounds(157, 238, 96, 20);
		frame.getContentPane().add(txtCourse);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(31, 156, 49, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(31, 181, 84, 17);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Contact");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(31, 209, 84, 17);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Course");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(31, 237, 73, 21);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton add = new JButton("ADD");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query = "insert into StudentInfo (ID, Name, Contact, Course) values (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, Integer.parseInt(txtId.getText()));
					pst.setString(2, txtName.getText());
					pst.setString(3, txtContact.getText());
					pst.setString(4, txtCourse.getText());
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Saved Successfully");
					pst.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				refreshTable();
				
			}
		});
		add.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add.setBackground(Color.WHITE);
		add.setBounds(44, 348, 89, 23);
		frame.getContentPane().add(add);
		
		JButton update = new JButton("UPDATE");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "update StudentInfo set ID='" + txtId.getText()+ "', Name='" + txtName.getText() + 
							"', Contact='" + txtContact.getText() + "', Course='" +txtCourse.getText() + "' where ID='" + txtCourse.getText() + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Updated Successfully");
					pst.close();
			
			
			} catch (Exception e2) {
				// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);
			}
			
			refreshTable();
			}
		});
		update.setFont(new Font("Tahoma", Font.PLAIN, 14));
		update.setBounds(164, 348, 89, 23);
		frame.getContentPane().add(update);
		
		JButton delete = new JButton("DELETE");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int action = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Delete", JOptionPane.YES_NO_OPTION);
				if(action == 0) {
					
					try {
						
							String query = "delete from StudentInfo where ID='" + txtId.getText() + "'";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.execute();
							JOptionPane.showMessageDialog(null, "Removed Successfully");
							pst.close();
				
				
					} catch (Exception e3) {
						// TODO: handle exception
							JOptionPane.showMessageDialog(null, e3);
					}
					
					refreshTable();
			}
			}
		});
		delete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		delete.setBounds(44, 395, 89, 23);
		frame.getContentPane().add(delete);
		
		JButton clear = new JButton("CLEAR");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshTable();
			}
		});
		clear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clear.setBounds(157, 395, 89, 23);
		frame.getContentPane().add(clear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(382, 145, 372, 386);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\ACER\\Downloads\\new_LaSalle_college.png"));
		lblNewLabel_5.setBounds(34, 11, 394, 82);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_3 = new JLabel("Search By Name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(368, 82, 176, 33);
		frame.getContentPane().add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (textField.getText().isEmpty()) {
						refreshTable();
						return;
					}
					String query = "select * from StudentInfo where Name = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		textField.setBounds(559, 82, 163, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		frame.setBounds(100, 100, 811, 579);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
