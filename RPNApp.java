
import java.util.*;

/**
 * This class solves mathmatical equations presented to it
 * as input from the keyboard,
 * in the form of reverse polish notation.
 * @author David Black, Boston Hart, Jessie Rongen
 */
public class RPNApp{

    /**
     * An array list of type String which contains the numbers
     * that have been added to the stack.
     */
    private static ArrayList<String> stack = new ArrayList<String>();

    /**
     * firstNum is an integer data field which represents the first element
     * on the stack.
     */
    private static int firstNum;

    /**
     * secondNum is an integer data field which represents the second element
     * on the stack.
     */
    private static int secondNum;

    /**
     * previousNum is a character  data field which represents
     * the previous element of the input string.
     */
    private static String previousNum;

    /**
     * c is a String data field which is used when adding elements that have
     * been operated on to the stack.
     */
    private static String stackAdd;

    /**
     * j is the index of the element on the top of the stack.
     */
    private static int j;

    /**
     * A String which contians the invalid input types to the eval method
     * (do not have a case within the switch statement).    
     */
    private static String save = "";

    /**
     *The string which contains the input that casued an error.
     *This varable acts as a boolean value which toggles
     * the print line statement for the stack at the end.
     */
    private static String errorToken = "";

    /**
     * Final variable which helps the brackets method
     * removing the operators inside of the brackets.
     */
    public static final int CONSTANT = 3;

    /**
     * Main method for the RPNApp.
     * @param args is a string array
     */
    public static void main(String[] args){
   
        Scanner scan = new Scanner(System.in);
   
        String keyboardIn;
   
        while(scan.hasNextLine()){
            errorToken = "";
            keyboardIn = scan.nextLine();
            eval(keyboardIn);
            if(errorToken.isEmpty()){
                System.out.println(stack.toString());
            }
            stack.clear();
         
            if(keyboardIn.isEmpty()){
                break;
            }        
        }
    }

    /**
     * eval is a method which processes the input stream.
     * @param input is the input in RPN form.
     */
    public static void eval(String input){
        for (int i = 0; i < input.length(); i++){    
            switch(input.charAt(i)){
                case'(':
                    if(stack.size() < 2){
                        System.out.println("Error: too few operands");
                        errorToken+= 'E';
                        break;
                    }else{
                        String inputFirstHalf = input.substring(0,i - CONSTANT);
                        String inputEquation = input.substring
                            (i +1,input.length() - 1);
                        int equationEnd = i + brackets(inputEquation).length();
                        String inputRest = input.substring
                            (equationEnd +CONSTANT ,input.length());
                        j = stack.size() - 1;              
                        for(int u = 0; u < Integer.parseInt(stack.get(j)); u++){
                            inputFirstHalf += brackets(inputEquation);
                        }
                        input = inputFirstHalf + inputRest;
                        stack.remove(j);
                        i-= CONSTANT;
                        break;
                    }
                case ')':                  
                    break;
                case '+':            
                    add();
                    break;                  
                case '-':          
                    subtract();
                    break;
                case '*':      
                    mult();
                    break;
                case '/':
                    divide();
                    break;
                case 'c':
                    copy();
                    break;
                case '%':
                    mod();
                    break;
                case '!':
                    if(stack.size() < 2){
                        System.out.println("ERROR: too few opearands");
                        break;          
                    }
                    j = stack.size() - 1;
                    previousNum = String.valueOf(input.charAt(i - 1));
                    for(int u = 0; u < j;u++){
                        input += previousNum;
                    }
                    break;
                case 'r':
                    roll();
                    break;
                case 'd':
                    dup();
                    break;
                case 'o':
                    output();
                    break;
                case ' ':
                    break;
                default:
                    if(Character.isDigit(input.charAt(i))){
                        if(i > 0){
                            if(input.charAt(i-1) != ' '){
                                j = stack.size() - 1;
                                stack.remove(j);
                                save = Character.toString(input.charAt(i-1));
                                save += Character.toString(input.charAt(i));
                                stack.add(save);                    
                                break;
                            }
                        }          
                        stack.add(Character.toString(input.charAt(i)));      
                    }else{
                        for (int h = i; h < input.length(); h++){
                            if(input.charAt(h) != ' '){
                                errorToken += input.charAt(h);
                            }                      
                        }
                        System.out.println("Error: bad token" +
                                           "'" + errorToken + "'");
                        i+= errorToken.length();
                        break;
                    }
                    break;
            }
        }
    }
    /**
     * Public method to do the brackets operation.
     * @param counter is a string
     * @return A string which contains the operators inside
     * of the brackets.
     */
    public static String brackets(String counter){
        int open = 1;
        for(int i = 0; i < counter.length();i++){
            if (counter.charAt(i) == '('){
                open++;
            }
            if (counter.charAt(i) == ')'){
                open--;
            }
            if(open == 0){
                return counter.substring(0,i-1);
            }
        }
        return counter.substring(0,counter.length() -1);
    }

    /**
     * Public void method which is used to evaluate
     * the '+' (addition) operator.
     */
    public static void add(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken+= 'E';
         
        }else{
            j = stack.size() - 1;        
            firstNum = Integer.parseInt(stack.get(j));
            secondNum = Integer.parseInt(stack.get(j-1));
            stackAdd= Integer.toString(firstNum+secondNum);
            stack.remove(j);
            stack.remove(j-1);
            stack.add(stackAdd);
        }
    }

    /**
     *  Public void method which is used to
     *  evaluate the '-' (subtract) operator.
     */
    public static void subtract(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken += "E";        
         
        }else{
            j = stack.size() - 1;
            firstNum = Integer.parseInt(stack.get(j));
            secondNum = Integer.parseInt(stack.get(j-1));
            stackAdd= Integer.toString(secondNum-firstNum);
            stack.remove(j);
            stack.remove(j-1);
            stack.add(stackAdd);
        }
    }

    /**
     *  Public void method which is used to
     *  evaluate the '*' (multiplication) operator.
     */
    public static void mult(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken+= 'E';                      
        }
        j = stack.size() - 1;
        firstNum = Integer.parseInt(stack.get(j));
        secondNum = Integer.parseInt(stack.get(j-1));
        stackAdd= Integer.toString(firstNum*secondNum);
        stack.remove(j);
        stack.remove(j-1);
        stack.add(stackAdd);      
    }

    /**
     *  Public void method which is used
     *  to evaluate the '/' (divison) operator.
     */
    public static void divide(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken+= "E";      
        }else{
            j = stack.size() - 1;
            firstNum = Integer.parseInt(stack.get(j));
            secondNum = Integer.parseInt(stack.get(j-1));
            if(firstNum < 1){
                System.out.println("Error: division by 0");
                errorToken+= "E";
            }else{
                stackAdd= Integer.toString(secondNum/firstNum);
                stack.remove(j);
                stack.remove(j-1);
                stack.add(stackAdd);
            }
        }
    }

    /**
     *  Public void method which is used
     *  to evaluate the 'c' (copy) operator.
     */
    public static void copy(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken+= 'E';        
         
        }else{
            j = stack.size() - 1;
            firstNum = Integer.parseInt(stack.get(j));
            secondNum = Integer.parseInt(stack.get(j-1));
            stack.remove(j);
            stack.remove(j-1);
         
            for(int u = 0; u < firstNum; u++){
                stack.add(Integer.toString(secondNum));
            }
        }
    }

    /**
     *  Public void method which is used
     *  to evaluate the '%' (modulus) operator.
     */
    public static void mod(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");            
        }
        j = stack.size() - 1;
        firstNum = Integer.parseInt(stack.get(j));
        secondNum = Integer.parseInt(stack.get(j-1));
        stackAdd= Integer.toString(firstNum%secondNum);
        stack.remove(j);
        stack.remove(j-1);
        stack.add(stackAdd);
    }
 
    /**
     *  Public void method which is used
     *  to evaluate the 'r' (roll) operator.
     */
    public static void roll(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken+= 'E';          
         
        }else{
            j = stack.size() - 1;
            firstNum = Integer.parseInt(stack.get(j));
            secondNum = Integer.parseInt(stack.get(j-1));
            stack.remove(j);
            stack.remove(j-1);
            if(stack.size() - (firstNum - 1) < 0 ){
                System.out.println("ERROR");                  
            }
            stack.add(stack.size() - (firstNum - 1),
                      Integer.toString(secondNum));
        }
    }

    /**
     *  Public void method which is used to
     *  evaluate the 'd' (duplicate) operator.
     */
    public static void dup(){
        if(stack.size() < 1){
            System.out.println("Error: too few operands");
            errorToken+= 'E';          
         
        }else{
            j = stack.size() - 1;
            firstNum = Integer.parseInt(stack.get(j));
            stackAdd= Integer.toString(firstNum);
            stack.add(stackAdd);
        }
    }

    /**
     *  Public void method which is used to
     *  evaluate the 'o' (output) operator.
     */
    public static void output(){
        if(stack.size() < 2){
            System.out.println("Error: too few operands");
            errorToken+= 'E';          
         
        }else{
            j = stack.size() - 1;
            firstNum = Integer.parseInt(stack.get(j));
            System.out.print(firstNum +" " );
        }
    }
}