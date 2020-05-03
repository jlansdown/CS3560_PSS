




import java.io.FileNotFoundException; 
import java.io.PrintWriter; 
import java.util.LinkedHashMap; 
import java.util.Map; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;



public class PSSMain 
{
    public static PSSUser pss_user;
    public static PSSController pss;
    public static String fileName;
    
    public static String[] list_format
            = {
                "Name",
                "Type",
                "Date",
                "StartDate",
                "StartTime",
                "Duration",
                "EndDate",
                "Frequency"
            };

    public static void main(String[] args) throws Exception
    {
        //PLEASE USE YOUR OWN DIRECTORY TO TEST THE JSON FILE! EVERYONE IS DIFFERENT!!! 
        
        fileName = "Set1_a.json"; // Directory 
        
        pss = new PSSController(fileName);
        
        //pss.printSchedule();
        
        StartUp();

    }
    public static void StartUp()
    {
        Scanner userInput = new Scanner(System.in);
        
        String name;
        
        System.out.println("-------------[Welcome to PSS]----------------");
        System.out.println("To get started, please enter your name!\n");
        
        System.out.print("Name: _> ");
        
        name = userInput.nextLine();
        
        pss_user = new PSSUser(name);
        
        System.out.println("\nHello "+name+"! "+"Thank you for choosing PSS!"+"\n");
        
        Menu();
    }
    public static void Menu()
    {

        int input = -1;
        do 
        {
            
            System.out.println("---------------[Main Menu]------------------");
            Scanner userInput = new Scanner(System.in);

            System.out.println("[1] View Schedule\n"
                             + "[2] Create Task\n" // Use format from PSSSChedule
                             + "[3] Edit Task\n"   
                             + "[4] Delete Task\n"
                             + "[5] File Options\n" // allow user to access the file options to decided on create, store, load a file.
                             + "[0] Quit\n");
            
            System.out.print("Choice: _> ");

            input = userInput.nextInt();
            
            switch(input)
            {
                case 0 : input = 0; 
                    break;
                case 1 : pss.printSchedule(); System.out.println();
                    break;
                case 2 : createTask(); System.out.println();
                    break;
                case 3 : editTask(); System.out.println();
                    break;
                case 4 : deleteTask(); System.out.println();
                    break;
                case 5:  FileOption(); System.out.println();
                    break;
                default : System.out.println("Invalid Input.");;
            }

            
            
        } while (input != 0);
    }
    
    public static void createTask()
    {  
        String[] input = new String[list_format.length];
        
        Scanner userInput = new Scanner(System.in);
        
        for (int i = 0; i < list_format.length; i++) 
        {
            System.out.print("Please enter " + list_format[i] + ": _>");
            input[i] = userInput.nextLine();
            System.out.println();
        }
        
        pss.getPSSModel().createTask(list_format, input);
        
        return;
    }
    
    public static void editTask()
    {
       
        JSONObject jsonObject = null;
        
        String[] input = new String[list_format.length];
        
        do
        {
            Scanner userInput = new Scanner(System.in);

            System.out.println("----------[Edit Task]-------------");
            System.out.println("Please enter task Name and Type.");
            System.out.println("Please enter menu on Name to return to menu.");
            System.out.println("[Note] Assume it's case sensitive.");

            System.out.print("Name: _> ");
            String name = userInput.nextLine();
            
            if (name.equals("menu")) 
            {
                return;
            }
            
            System.out.println();

            System.out.print("Type: _> ");
            String type = userInput.nextLine();
            
            System.out.println("____________________________________");
            
            int index = pss.getPSSModel().findTask(name, type); // Returns the index if it finds it. 
            
            System.out.println("index: "+ index);
            
            //if found. Then edit
            for(int i = 0; i < list_format.length; i++)
            {
                System.out.print("Please enter "+list_format[i]+": _>");
                input[i] = userInput.nextLine();
                System.out.println();
            }
            
            pss.getPSSModel().editTask(list_format, input, index);
            
            System.out.println("____________________________________");
            
 
        
        }while(jsonObject == null);
        
    }
    
    public static void deleteTask()
    {
        do {
            Scanner userInput = new Scanner(System.in);

            System.out.println("----------[Delete Task]-------------");
            System.out.println("Please enter task Name and Type.");
            System.out.println("Please enter menu on Name to return to menu.");
            System.out.println("[Note] Assume it's case sensitive.");
            System.out.println("[Example] Name: CS3560-Tu");
            System.out.println("          Type: Class");

            System.out.print("Name: _> ");

            String name = userInput.nextLine();

            if (name.equals("menu")) 
            {
                return;
            }

            System.out.println();

            System.out.print("Type: _> ");

            String type = userInput.nextLine();

            pss.getPSSModel().deleteTask(name, type);

        } while(true);
       
    }
    
    public static void FileOption()
    {
        int input = -1;
        do
        {
            Scanner userInput = new Scanner(System.in);

            System.out.println("----------[File Options]-------------");
            System.out.println("[0] Return to Menu");
            System.out.println("[1] Save Current File"); // NOTE! IT WILL MODIFY CURRENT JSON FILE AND will mess up the orignal formatting. However, computer will read it correctly.
            System.out.println("[2] Save a New File");
            System.out.println("[3] Load a File"); // Prompt user for the file name and load that file up.
            
            System.out.print("Choice: _> ");

            input = userInput.nextInt();
            
    
            
            switch (input) 
            {
                case 0:
                {
                    input = 0; 
                }
                    break;
                    
                case 1:
                {
                    pss.storeToFile(fileName);
                }
                    break;
                    
                case 2:
                {
                    
                    System.out.print("Enter New File Name: _> ");
                    
                    fileName = userInput.next();
                    
                    System.out.println("File: "+fileName+" has been saved.");
                    
                    pss.storeToFile(fileName+".json");
                }
                    break;
                    
                case 3:  
                {
                    System.out.print("Enter File Name: _> ");
                    
                    fileName = userInput.next();
                    
                    pss = new PSSController(fileName);
                    
                    System.out.println("File: "+fileName+" has been loaded.");
                }
                    break;
                    
                default:
                    System.out.println("Invalid Input.");
                    ;
            }
            
            
        }while(input != 0);
    }
}
