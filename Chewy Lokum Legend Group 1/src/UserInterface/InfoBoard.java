package UserInterface;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Domain.CreateLevels;
import Domain.GameGUI;
import Domain.SaveFileGenerator;
import Domain.main;

import java.awt.event.ActionEvent;


/**
 * This class creates a the information board which is at the right side of the
 * main screen of the game. It contains Jlabels for displaying information and 
 * JButtons for interaction with certain elements of the game. 
 */

public class InfoBoard extends JPanel implements ActionListener{

	public static ImageLibrary imageLibrary = new ImageLibrary();
	private JLabel textTargetScore = new JLabel(" Target Score:");
	private JLabel targetScore  ;
	private JLabel textCurrentScore = new JLabel(" Current Score:");
	private JLabel score  ;
	private JLabel textLevel = new JLabel(" Level:");
	private JLabel level ;
	private JLabel textMoveLeft = new JLabel(" Move Left:");
	private JLabel moveLeft;
	private JButton saveButton = new JButton("Save");
	private JButton exit = new JButton("Exit");
	public SaveFileGenerator sfg;
	int privateScoreWithNumber;
	int targetScoreWithNumber;
	int moveLeftWithNumber;
	int levelNumber;
	int boardRowNumber;
	int boardColNumber;
	String boardState;



	/**
	 * Shows player's remaining number of moves and current score.
	 * 
	 * @requires 	game must be started.
	 * @modifies 	shows updated info board.
	 */
	public InfoBoard(int levelNo){
		levelNumber = levelNo;
		privateScoreWithNumber = 0;
		targetScoreWithNumber = CreateLevels.setOfLevels[levelNo].getTarget();
		moveLeftWithNumber = CreateLevels.setOfLevels[levelNo].getMoveLeft();

		setLayout(new GridLayout(5,2));

		add(textLevel);
		level=new JLabel(Integer.toString(levelNo+1),JLabel.CENTER);
		add(level);

		add(textTargetScore);
		targetScore= new JLabel(Integer.toString(targetScoreWithNumber),JLabel.CENTER);
		add(targetScore);

		add(textCurrentScore);
		score= new JLabel(Integer.toString(privateScoreWithNumber),JLabel.CENTER);
		add(score);

		add(textMoveLeft);
		moveLeft = new JLabel(Integer.toString(moveLeftWithNumber),JLabel.CENTER);
		add(moveLeft);

		add(saveButton);
		add(exit);
		saveButton.addActionListener(this);
		exit.addActionListener(this);

	}

	/**
	 * Set the score displayed on the panel.
	 * 
	 * @requires 	game must be started.
	 * @modifies 	shows updated score value at info board.
	 */
	public void setCurrentScore(int score){
		privateScoreWithNumber = score;
		this.score.setText(Integer.toString(privateScoreWithNumber));
	}


	public int getCurrentScore(){
		return privateScoreWithNumber;
	}

	public int getMoveLeft(){
		return moveLeftWithNumber;
	}

	public int getTargetScore(){
		return targetScoreWithNumber;
	}

	/**
	 * Set the level displayed on the panel.
	 * 
	 * @requires 	game must be started.
	 * @modifies 	shows updated level value at info board.
	 */
	public void setLevel(int level){
		this.level.setText(Integer.toString(level));
	}

	public void setboardRowNumber(int rn){
		boardRowNumber = rn;
		System.out.println(boardRowNumber);
	}

	public void setboardColNumber(int rn){
		boardColNumber = rn;
		System.out.println(boardColNumber);
	}
	public String toString() {
		String s = "Move Left: " + Integer.toString(moveLeftWithNumber)+ "Current Score: " + Integer.toString(privateScoreWithNumber) ;
		return s;
	}

	/**
	 * Set the move left displayed on the panel.
	 * 
	 * @requires 	game must be started.
	 * @modifies 	shows updated move left value at info board.
	 */
	public void setMoveLeft(int moveLeft){
		moveLeftWithNumber = moveLeft;
		this.moveLeft.setText(Integer.toString(moveLeft));
	}
	public void loseScreen(){

		JFrame frame = new JFrame ("You Lose");
		String[] s=new String[8];
		ImageIcon imageIcon = new ImageIcon(imageLibrary.getImage(66));
		Object[] options = {"New Game","Exit Game"};
		int n = JOptionPane.showOptionDialog(frame,
		"No More Lokums to Eat. Try Some Donught Instead..."+ "","You Lose",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		imageIcon,
		options,
		options[1]);
		new CreateLevels();
		if (n == JOptionPane.NO_OPTION) {
		System.exit(0);
		main.dis();
		}else if (n == JOptionPane.CLOSED_OPTION) {
			main.dis();
			System.exit(0);
		} else if (n == JOptionPane.YES_OPTION) {
		main.dis();
		main.dis();
		main.main(s); 
			
		}
	}
	public void winScreen(){
		JFrame frame = new JFrame ("You Win");
		String[] s=new String[8];
		ImageIcon imageIcon = new ImageIcon(imageLibrary.getImage(65));
		Object[] options = {"Play Next Level","Exit"};
		int n = JOptionPane.showOptionDialog(frame,
				"You Win. Even Donughts are Happy Now..."+ "","Win Screen",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				imageIcon,
				options,
				options[1]);
		if (n == JOptionPane.NO_OPTION) {
			main.dis();
			System.exit(0);
		} else if (n == JOptionPane.YES_OPTION) {
			int levelNum = Integer.parseInt(level.getText());
			System.out.println("Level NUM:" + levelNum);
			main.dis();
			main.dis();
			if (levelNum < 5) {
		    GameGUI gui = new GameGUI(CreateLevels.setOfLevels[levelNum],Integer.parseInt(level.getText()));}
		} else if (n == JOptionPane.CLOSED_OPTION) {
			main.dis();
			System.exit(0);
		}
	}
	public void setBoardString(String s){
		boardState = s;
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource().equals(saveButton)){
			String playerName = JOptionPane.showInputDialog(null,"Please Enter Your Name:","Save Game",2);
			
			System.out.println(playerName);
			
			if ((playerName != null) && (playerName.length() > 0)) {
				sfg = new SaveFileGenerator();
				int[] levelArray = sfg.levelArray;
				levelArray[levelNumber] = 1;
				sfg.generateSaveFile(privateScoreWithNumber, levelNumber ,moveLeftWithNumber, boardState, boardRowNumber, boardColNumber, levelArray, playerName);
			}}
		if (e.getSource().equals(exit))
			System.exit(0);
	}

}