/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.utils;

import java.io.IOException;

/**
 * This Class has all the methods to stylize your console output.
 * @author tsepe
 */
public class Style {
    
    
    
    
    /**
    *  The addLine() method prints as many empty lines as is the "times" argument
    * @param times declares how many lines we want to print
    */
    public static void addLines(int times){
        for(int i=1; i <= times; i++){        
            System.out.println("");
        }
    }
    
    /**
     * We use createFormat() method to format an output.<p>
     * For example we want to print a name and surname inside a String of 15 chars for name and 20 chars for surname.<p>
     * We create a formatOptions[],and a String[]<p>
     * we give in formatOptions[] the number of string's characters  formatOptions[15,20]<p>
     * we give in arguments[] the name and the surname, arguments[name,surname]
     * and then we call the method to return our formated string createFormat(formatOptions,arguments)<p>
     * 
     * @param formatOptions is an array with integers. Each integer represent how many 
     * fields we want for each argument's String<p>
     * @param arguments is an array with Strings we want to print after we formated first
     * @return formatted String
     */
    public static String createFormat(int formatOptions[],String[] arguments){
        
        String format;
        String result = "";
        switch(arguments.length){
            case 1:
                format = "%1$-"+formatOptions[0]+"s";
                result = String.format(format,arguments[0]);
                return result;
                
            case 2:
                format = "%1$-"+formatOptions[0]+"s %2$-"+formatOptions[1]+"s";
                result = String.format(format,arguments[0],arguments[1]);
                return result;
            case 3:
                format = "%1$-"+formatOptions[0]+"s %2$-"+formatOptions[1]+"s %3$-"+formatOptions[2]+"s";
                result = String.format(format,arguments[0],arguments[1],arguments[2]);
                return result;
            case 4:
                format = "%1$-"+formatOptions[0]+"s %2$-"+formatOptions[1]+"s %3$-"+formatOptions[2]+"s %4$-"+formatOptions[3]+"s";
                result = String.format(format,arguments[0],arguments[1],arguments[2],arguments[3]);
                return result;
            case 5:
                format = "%1$-"+formatOptions[0]+"s %2$-"+formatOptions[1]+"s %3$-"+formatOptions[2]+"s %4$-"+formatOptions[3]+"s %5$-"+formatOptions[4]+"s";
                result = String.format(format, arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
                return result;
            case 6:
                format = "%1$-"+formatOptions[0]+"s %2$-"+formatOptions[1]+"s %3$-"+formatOptions[2]+"s %4$-"+formatOptions[3]+"s %5$-"+formatOptions[4]+"s %6$-"+formatOptions[5]+"s";
                result = String.format(format,arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
                return result;
                
            case 7:
                format = "%1$-"+formatOptions[0]+"s %2$-"+formatOptions[1]+"s %3$-"+formatOptions[2]+"s %4$-"+formatOptions[3]+"s %5$-"+formatOptions[4]+"s %6$-"+formatOptions[5]+"s %7$-"+formatOptions[6]+"s";
                result = String.format(format,arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5],arguments[6]);
                return result;
        
        }
        return result;
    }
    
    
     /**
     * We use printFormatted() method to format an output.<p>
     * For example we want to print a name and surname inside a String of 15 chars for name and 20 chars for surname.<p>
     * We create a formatOptions[],and a String[]<p>
     * we give in formatOptions[] the number of string's characters  formatOptions[15,20]<p>
     * we give in arguments[] the name and the surname, arguments[name,surname]
     * and then we call the method to print our formated string printFormatted(formatOptions,arguments)<p>
     * 
     * @param formatOptions is an array with integers. Each integer represent how many 
     * fields we want for each argument's String<p>
     * @param arguments is an array with Strings we want to print after we formated first
     */
    public static void printFormatted(int formatOptions[], String arguments[]){
        System.out.println(createFormat(formatOptions,arguments));
    }
    
    
    
    /**
    *  The addUnderline() method prints a string of "-"
    * We use it to print the same size underline every time.
    */
    public static void addUnderline(){
        System.out.println("-----------------------------------------");
    }
    
    
    
    // <--------------   Here we declare methods about Stylize a Text  --------------------------->
    
    
    
    /**
     * Takes a String, and returns it red
     * It puts the red string code before the string, and reset code after it.
     * 
     * @param text
     * @return String
     */
    public static String red(String text){
        String red = "\033[0;31m";
        String reset = "\033[0m";
        return red+text+reset;
    }
    
    
    /**
     * Takes a String, and returns it green
     * It puts the green string code before the string, and reset code after it.
     * 
     * @param text
     * @return String
     */
    public static String green(String text){
        String green = "\033[0;32m";
        String reset = "\033[0m";
        return green+text+reset;
    }
    
    
    /**
     * Takes a String, and returns it yellow
     * It puts the yellow string code before the string, and reset code after it.
     * 
     * @param text
     * @return String
     */
    public static String yellow(String text){
        String yellow = "\033[0;33m";
        String reset = "\033[0m";
        return yellow+text+reset;
    }
    
    
    /**
     * Takes a String, and returns it magenta
     * It puts the magenta string code before the string, and reset code after it.
     * 
     * @param text
     * @return String
     */
    public static String magenta(String text){
        String magenta = "\033[0;35m";
        String reset = "\033[0m";
        return magenta+text+reset;
    }
    
    
    /**
     * Takes a String, and returns it blue
     * It puts the blue string code before the string, and reset code after it.
     * 
     * @param text
     * @return String
     */
    public static String blue(String text){
        String blue = "\033[0;34m";
        String reset = "\033[0m";
        return blue+text+reset;
    }
    
    
    
}
