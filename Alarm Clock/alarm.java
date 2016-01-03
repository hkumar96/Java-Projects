import java.util.*;
import java.io.*;
import sun.audio.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
class ActionAlarmSet implements ActionListener{
	JFrame myWin;
	AlarmClock temp;
	public ActionAlarmSet(JFrame win, AlarmClock tmp){
		this.myWin = win;
		this.temp = tmp;
	}
	public void actionPerformed(ActionEvent e){
		String timeStr;
		timeStr = JOptionPane.showInputDialog(myWin,"Enter Time(HH:MM:SS)");
		temp.setAlarm(timeStr);

	}
}
class AlarmClock {
	GregorianCalendar current, end;
	long sleepTime;
	JFrame win;
	JLabel time;
	public AlarmClock(){
		current = new GregorianCalendar();
		this.initializeFrame();
	}
	public void initializeFrame(){
		final int X_FRAME = 100 ,Y_FRAME = 100 ,LEN_FRAME = 300 ,WID_FRAME = 400;
		win = new JFrame("Alarm Clock!");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(WID_FRAME,LEN_FRAME);
		win.setLocation(X_FRAME,Y_FRAME);

		JPanel pane = new JPanel();
		pane.setLocation(0,100);
		pane.setSize(WID_FRAME-100,LEN_FRAME-100);
		pane.setBackground(Color.BLUE);

		time = new JLabel(current.getTime().toString());
		pane.add(time);

		JButton alarmSet = new JButton("Set Alarm!");
		alarmSet.addActionListener(new ActionAlarmSet(win,this));
		pane.add(alarmSet);

		win.add(pane);
		win.setVisible(true);
		showElapsedTime();

	}

	void showElapsedTime(){
		showElapsedTime(-1);
	}
	void showElapsedTime(long endTime){
		long prev,next;
		long startTime = System.currentTimeMillis();
		prev = System.currentTimeMillis();
		for(;endTime == -1 || System.currentTimeMillis() - startTime < endTime;){
			next = System.currentTimeMillis();
			if(next - prev >= 1000){
				current = new GregorianCalendar();
				time.setText(current.getTime().toString());
				prev = next;
			}
		}
	}
	public void ringAlarm(){
		System.out.println("wakeup!");
		try{
			String gongFile = "tone.wav";
	    	InputStream in = new FileInputStream(gongFile);
	    	try{
	    		// create an audiostream from the inputstream
			    AudioStream audioStream = new AudioStream(in);
			 
			    // play the audio clip with the audioplayer class
			    AudioPlayer.player.start(audioStream);
			    setAlarm(end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE),end.get(Calendar.SECOND));
	    	}catch (IOException ex){
	    		System.out.println("Unable to open file");
	    	}
		}catch (FileNotFoundException ex){
			System.out.println("File not found");
		}
	}
	public void setAlarm(String timeStr){
		int tempLoc = 0,hh,mm,ss;
		int count=1;
		hh = mm = ss = 0;
		for(int i = 0; i<timeStr.length(); i++){
			if (timeStr.charAt(i) == ':'){
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
		setAlarm(hh,mm,ss);
	}
	public void setAlarm(int hh,int mm,int ss){		
		end = new GregorianCalendar();
		GregorianCalendar temp = new GregorianCalendar();
		temp.set(Calendar.HOUR_OF_DAY,hh);
		temp.set(Calendar.MINUTE,mm);
		temp.set(Calendar.SECOND,ss);
		// System.out.println(end.getTime() + "\n" + temp.getTime());
		if (end.compareTo(temp)>=0){
			end.set(Calendar.DAY_OF_MONTH,end.get(Calendar.DAY_OF_MONTH)+1);
		}
		end.set(Calendar.HOUR_OF_DAY,hh);
		end.set(Calendar.MINUTE,mm);
		end.set(Calendar.SECOND,ss);
		// System.out.printf("\nAlarm set for %s\n",end.getTime());
		current =  new GregorianCalendar();
		sleepTime = end.getTimeInMillis() - current.getTimeInMillis();
		// System.out.printf("\nTime remaining %d seconds!\n",(sleepTime/1000));
		// try{
		// 	Thread.sleep(sleepTime);
		// 	ringAlarm();
		// }catch (InterruptedException ex){
		// 	Thread.currentThread().interrupt();
		// }
		showElapsedTime(sleepTime);
		ringAlarm();
	}

	
}
public class alarm{
	public static void main(String[] args) {
		// Scanner s = new Scanner(System.in);
		// System.out.println("Enter alarm time in format(HH:MM:SS)");
		// String timeStr = s.nextLine();
		// int hh=0,mm=0,ss=0,tmp;
		// int temp = 0;
		// int count=1;
		// for(int i = 0; i<timeStr.length(); i++){
		// 	if (timeStr.charAt(i) == ':'){
		// 		if (count == 1)
		// 			hh = Integer.parseInt(timeStr.substring(temp,i));
		// 		else if (count == 2)
		// 			mm = Integer.parseInt(timeStr.substring(temp,i));
		// 		count++;
		// 		temp = i + 1;
		// 	}
		// }
		// if (count == 3) {
		// 			ss = Integer.parseInt(timeStr.substring(temp,timeStr.length()));
		// 		}
		AlarmClock clock1 = new AlarmClock();
		// clock1.setAlarm(hh,mm,ss);
	}
}