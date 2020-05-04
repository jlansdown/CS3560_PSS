
import java.util.ArrayList;
import org.json.simple.JSONObject;


public class Task implements TaskInterface
{
    private String name;
    private String type;

    
    private double[] time_list; // assume they're in this order [date, startDate, Time, duration, endDate, frequency]
    
    private String[] list_format; // This array sets the standard of the formatting. Can be useful for other methods.
    
    
    public Task() // Only for debug. This part allows dummy PSSTask constructor. 
    {
        this.name = "";
        this.type = "";
    }
    
    //Required for each task. 
    public Task(String[] list_format, JSONObject jsonObject)
    {
        this.list_format = list_format;
        this.name = jsonObject.get(list_format[0]).toString();  // index 0 is always name
        this.type = jsonObject.get(list_format[1]).toString();  // index 1 is always type 
        
        // These are placed in relation to the list format of a string name, type, date. etc.

        time_list = new double[list_format.length - 2];

        for(int i = 0; i < time_list.length; i++)
        {
            if(jsonObject.get(list_format[i+2]) != null)
            {
                time_list[i] = Double.parseDouble(jsonObject.get(list_format[i+2]).toString());
            }
            else
            {
                time_list[i] = -1; // If that part of a task don't exist. 
            }
        }
       
    }
    
    public Task(String[] list_format, String[] input)
    {
        name = input[0];
        type = input[1];
        
        time_list = new double[input.length - 2];
        this.list_format = list_format;
        
        for (int i = 0; i < time_list.length; i++) 
        {
            time_list[i] = Double.parseDouble(input[i+2]);
        }
    }
    
    
    // This part will read users input and store in a task time_list array. 
    //Assume time_list in this order [date, startDate, Time, duration, endDate, frequency]
    
    public void setTask(String[] list_format, String[] input)
    {
        name = input[0];
        type = input[1];
        
        for(int i = 2; i < time_list.length; i++)
        {
            time_list[i] = Double.parseDouble(input[i]);
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public ArrayList<String> getAllComponents()
    {
        ArrayList<String> comp = new ArrayList<String>();
        
        comp.add(name);
        comp.add(type);
        
        for(int i = 0; i < time_list.length; i++)
        {
            comp.add(Double.toString(time_list[i]));
        }
        
        return comp;
    }
    
    public void viewTask()
    {
        System.out.println("____________________________________");
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        
        //System.out.println(time_list.length);
        
        //System.out.println(list_format[4]+": "+ formatTime("StartTime", time_list[4]));
        
        
        //System.out.println("____________________________________");
        
        
        for(int i = 0; i < time_list.length; i++)
        {
            System.out.println(list_format[i + 2] +": "+formatTime(list_format[i + 2],time_list[i])); // List_Format[name, type, date, startDate, Time, duration, endDate, frequency]
        }
    }
    
    
    //Output the a readable time for users.
    
    public String formatTime(String type, double time)
    {
        String formatted_time = "";
        
        if (time >= 0) 
        {
            if (type.equals("Date") || type.equals("StartDate") || type.equals("EndDate")) 
            {
                time = time / 10000;
                formatted_time = Double.toString(time);
            }

            if (type.equals("StartTime")) 
            {
                formatted_time = ((int) time % 12 + ":" + "00" + " " + (((int) time >= 12) ? "PM" : "AM"));
            }
            if(type.equals("Duration"))
            {
                formatted_time = (time + " Hours");
            }
            if(type.equals("Frequency"))
            {
                formatted_time = (time + " Times");
            }
        }
        else
        {
            formatted_time = "Not Available";
        }
        
        return formatted_time;
    }
}
