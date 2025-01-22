package com.java.admin;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.java.project.Provider;

public class Post7 {
	// Java program to check valid
	// Aadhaar number using regex.
	
	
	   
	public static void main(String args[])  
	{  
	//obtains an instance of LocalDate from a year, month and date 
	
	LocalDate dob = LocalDate.of(1988, 12, 13);  
	//obtains the current date from the system clock  
	LocalDate curDate = LocalDate.now();  
	//calculates the difference betwween two dates  
	Period period = Period.between(dob, curDate);  
	//prints the differnce in years, months, and days  
	System.out.printf("Your age is %d years %d months and %d days.", period.getYears(), period.getMonths(), period.getDays());  
	}  
	
	

		// Function to validate Aadhaar number.
	/*	public static boolean
		isValidAadhaarNumber(String str)
		{
			// Regex to check valid Aadhaar number.
			String regex
				= "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";

			// Compile the ReGex
			Pattern p = Pattern.compile(regex);

			// If the string is empty
			// return false
			if (str == null) {
				return false;
			}

			// Pattern class contains matcher() method
			// to find matching between given string
			// and regular expression.
			Matcher m = p.matcher(str);

			// Return if the string
			// matched the ReGex
		return m.matches();
		}

		// Driver Code.
		public static void main(String args[])
		{
			System.out.println(org.hibernate.Version.getVersionString());

			// Test Case 1:
			String str1 = "3675 9834 6015";
			System.out.println(isValidAadhaarNumber(str1));

			// Test Case 2:
			String str2 = "4675 9834 6012 8";
			System.out.println(isValidAadhaarNumber(str2));

			// Test Case 3:
			String str3 = "0175 9834 6012";
			System.out.println(isValidAadhaarNumber(str3));

			// Test Case 4:
			String str4 = "3675 98AF 60#2";
			System.out.println(isValidAadhaarNumber(str4));

			// Test Case 5:
			String str5 = "417598346012";
			System.out.println(isValidAadhaarNumber(str5));
		}
	*/

 /*   public static void main(String[] args) {
        String regex = "\\d+";
        Scanner sc = new Scanner(System.in);
        System.out.println("please input a valid 4 digit pin");
        while(true) {
            String ln = sc.nextLine();
            if(ln.length() == 4 && ln.matches(regex)) {
                System.out.println("valid input "+ln);
                break;
            }else {
                System.out.println("please input a valid 4 digit pin");
            }
        }
        sc.close();

    }*/

}