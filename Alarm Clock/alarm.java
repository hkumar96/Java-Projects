import java.util.Calendar;
import java.io.*;
import sun.audio.*;
import java.util.Scanner;
class alarmClock {
	Calendar current, end;
	long sleepTime;
	alarmClock(){
		current = Calendar.getInstance();
		System.out.printf("\nCurrent time is %s\n",current.getTime());
	}
	void showElapsedTime(){
	}
	void ringAlarm(){
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
	public void setAlarm(int hh,int mm,int ss){
		end = Calendar.getInstance();
		Calendar temp = Calendar.getInstance();
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
		System.out.printf("\nAlarm set for %s\n",end.getTime());
		current = Calendar.getInstance();
		sleepTime = end.getTimeInMillis() - current.getTimeInMillis();
		System.out.printf("\nTime remaining %d seconds!\n",(sleepTime/1000));
		try{
			Thread.sleep(sleepTime);
			ringAlarm();
		}catch (InterruptedException ex){
			Thread.currentThread().interrupt();
		}
	}

	
}
public class alarm{
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter alarm time in format(HH:MM:SS)");
		String timeStr = s.nextLine();
		int hh=0,mm=0,ss=0,tmp;
		int temp = 0;
		int count=1;
		for(int i = 0; i<timeStr.length(); i++){
			if (timeStr.charAt(i) == ':'){
				if (count == 1)
					hh = Integer.parseInt(timeStr.substring(temp,i));
				else if (count == 2)
					mm = Integer.parseInt(timeStr.substring(temp,i));
				count++;
				temp = i + 1;
			}
		}
		if (count == 3) {
					ss = Integer.parseInt(timeStr.substring(temp,timeStr.length()));
				}
		alarmClock clock1 = new alarmClock();
		clock1.setAlarm(hh,mm,ss);
	}
}