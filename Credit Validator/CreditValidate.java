import java.util.Scanner;
public class CreditValidate{
	static boolean validate(long num){
		int checkDigit = -1;
		int temp,sum = 0,flag=1;
		while (num>0) {
			temp = (int)(num%10);
			num = num/10;
			if (checkDigit == -1){
				checkDigit = temp;
				continue;
			}
			//System.out.print(temp);
			if (flag > 0){
				temp = temp*2;
			}
			flag *= -1;
			//System.out.printf(" %d,",temp);
			if (temp > 10){
				sum += temp%10;
				temp = temp/10;
			}
			sum += temp;
			//System.out.println(sum);
		}
		
		if ((sum*9)%10 == checkDigit)
			return true;
		else
			return false;
	}
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String tmp;
		System.out.println("Enter the credit card number?");
		tmp = s.nextLine();
		long num = 0;
		try{
			num = Long.parseLong(tmp,10);
			if (validate(num)) {
				System.out.println("The number you entered is a valid Credit Card number!\n\n");				
			}
			else{
				System.out.println("The number you entered is not a valid Credit Card number!\n\n");
			}
		}catch (NumberFormatException e){
			System.out.println("Cannot parse string to number");
			System.out.println(e.getMessage());
		}
	}
}