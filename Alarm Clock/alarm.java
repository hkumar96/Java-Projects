import java.util.*;
import java.io.*;
import sun.audio.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class AlarmClock {
	GregorianCalendar currTime,alarmTime;
	boolean isSet;

	public AlarmClock(){
		currTime = new GregorianCalendar();
		isSet = false;
	}

	public String getCurTime(){
		currTime = new GregorianCalendar();
		return (currTime.getTime().toString());
	}

	public String getAlarmTime(){
		if (!isSet){
			JOptionPane.showMessageDialog(null,"ERROR: Alarm not set!");
			return "--not set--";
		}

		return (alarmTime.getTime().toString());
	}

	public void setAlarm(String timeStr) {
		int tempLoc = 0,hh,mm,ss;
		int count=1;
		hh = mm = ss = 0;

		for(int i = 0; i<timeStr.length(); i++) {
			if (timeStr.charAt(i) == ':') {
				if (count == 1)
					hh = Integer.parseInt(timeStr.substring(tempLoc,i));
				else if (count == 2)
					mm = Integer.parseInt(timeStr.substring(tempLoc,i));
				count++;
				tempLoc = i + 1;
			}
		}

		if (count == 3) {
					ss = Integer.parseInt(timeStr.substring(tempLoc,timeStr.length()));
		}

		this.setAlarm(hh,mm,ss);
	}
	private void  setAlarm(int hh, int mm ,int ss) {
		GregorianCalendar temp = new GregorianCalendar();
		temp.set(Calendar.HOUR_OF_DAY,hh);
		temp.set(Calendar.MINUTE,mm);
		temp.set(Calendar.SECOND,ss);
		alarmTime = temp;

		currTime = new GregorianCalendar();

		if (alarmTime.compareTo(currTime) <= 0) {
			alarmTime.set(Calendar.DAY_OF_MONTH, alarmTime.get(Calendar.DAY_OF_MONTH) + 1);
		}

		this.setIsSet(true);
	}
	public boolean getIsSet(){
		return isSet;
	}

	private void setIsSet(boolean value) {
		isSet = value;
	}

	public void ringAlarm(){

		JOptionPane.showMessageDialog(null,"Alarm ringing!");

		try{
			String gongFile = "tone.wav";
	    	InputStream in = new FileInputStream(gongFile);
	    	try{

	    		// create an audiostream from the inputstream

			    AudioStream audioStream = new AudioStream(in);
			 
			    // play the audio clip with the audioplayer class

			    AudioPlayer.player.start(audioStream);

			    setAlarm(alarmTime.get(Calendar.HOUR_OF_DAY),alarmTime.get(Calendar.MINUTE),alarmTime.get(Calendar.SECOND));
	    	}catch (IOException ex) {
	    		JOptionPane.showMessageDialog(null,"Unable to open file!");
	    	}
		}catch (FileNotFoundException ex){
			JOptionPane.showMessageDialog(null,"File not found");
		}
	}
}

public class alarm extends JFrame implements ActionListener {
	
	private JLabel curTimeLbl;
	private JLabel alarmTimeLbl;
	private JButton setAlarmBtn;
	private AlarmClock clock1;

	public static void main(String[] args) {
		alarm alarmWin = new alarm();
		alarmWin.setVisible(true);
		alarmWin.startAlarm();
	}

	public alarm(){
		this.setTitle("Alarm Clock");
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		clock1 = new AlarmClock();

		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);

		curTimeLbl = new JLabel(clock1.getCurTime());
		curTimeLbl.setBounds(100,20,200,22);
		curTimeLbl.setBorder(BorderFactory.createLineBorder(Color.white));
		contentPane.add(curTimeLbl);

		alarmTimeLbl = new JLabel("--not set--");
		alarmTimeLbl.setBounds(100,42,200,22);
		alarmTimeLbl.setBorder(BorderFactory.createLineBorder(Color.white));
		contentPane.add(alarmTimeLbl);

		setAlarmBtn = new JButton("Set Alarm");
		setAlarmBtn.setBounds(200,64,100,30);
		setAlarmBtn.setBorder(BorderFactory.createLineBorder(Color.white));
		setAlarmBtn.addActionListener(this);
		contentPane.add(setAlarmBtn);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			String inputTime = JOptionPane.showInputDialog(this,"Enter time in HH:MM:SS");
			if (inputTime.equals("")) {
				JOptionPane.showMessageDialog(this,"Time not entered. Alarm not set!");
			}else {
				clock1.setAlarm(inputTime);
			}
		}
	}

	public void startAlarm() {
		for(;;){
			if (!(curTimeLbl.getText().equals(clock1.getCurTime()))){
				curTimeLbl.setText(clock1.getCurTime());
			}
			if (clock1.getIsSet()) {
				alarmTimeLbl.setText(clock1.getAlarmTime());
				if (alarmTimeLbl.getText().equals(curTimeLbl.getText())){
					clock1.ringAlarm();
				}
			}
		}
	}
}