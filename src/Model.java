

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Model implements ModelInterface
{
    private PSS pssController;
    
    private ArrayList<Task> taskList = new ArrayList<>();
    
    private ArrayList<Schedule> scheduleList = new ArrayList<>();
    
    private Task pssTask;
    
    String[] list_format;
            
    public Model(PSS pssController)
    {
        this.pssController = pssController;
        
        pssTask = new Task();
        
    }
    
    
    @Override
    public Task createTask(String[] list_format, JSONObject jsonObject)
    {
        this.list_format = list_format;
        
        pssTask = new Task(list_format, jsonObject);
        
        taskList.add(pssTask);
        
        return pssTask;
    }
    
    //All of the user inputs are in a String array type of format that's realtive to the List_format. 
    public void createTask(String[] list_format, String[] input)
    {
        this.list_format = list_format;
        Task task = new Task(list_format, input);
        taskList.add(task);
    }
    
    public void createTransientTask(String[] list_format, String[] input) throws ParseException
    {
        this.list_format = list_format;
        Task task = new Task(list_format, input);
        
        //add a task if there's no overlap.
        if (!checkTimeOverLap(task.getName(), task.getType())) 
        {
            taskList.add(task);
        }
        
    }
    
    public ArrayList<Task> getTaskList()
    {
        return taskList;
    }
    
    @Override
    public void deleteTask(String name, String type)
    {
        int index = findTask(name, type);
        
        if(index >= 0)
        {
            taskList.remove(index);
            System.out.println("Task Name: "+ name +" and Type: "+ type +" has been deleted!");
        }
        else
        {
            System.out.println("Task Name: "+ name +" and Type: "+ type +" cannot be found!");
            System.out.println("Please try again!");
        }
    }
    
    @Override
    public void editTask(String[] list_format, String[] input, int index)
    {
        if(index >= 0)
        {
            taskList.get(index).setTask(list_format, input);
        }
        else
        {
            System.out.println("Task cannot be found!");
            System.out.println("Please try again!");
        }
    }
    
    @Override
    public boolean checkForOverlap(String name, String type) 
    {
        //This is just to see if that task exsist or not. 
        //We'll get the index of that task.
        
        int index = findTask(name, type); 
        
        //If such task found! 
        if(index >= 0)
        {
            Task task = taskList.get(index); // found task
            
            double[] time_list = task.getTimeList();
            
            // Check for overlap in terms of weeks of the day
            try 
            {
                // Return true if the current Start Date and Start Date of a same task is the same.
                //time_list[0] = Date and time_list[1] = StartDate
                
                if(time_list[1] > 0 || time_list[0] > 0)
                {
                    String searchedDay = task.dayOfWeek("StartDate",time_list[1]);
                    
                    double[] this_time_list = this.pssTask.getTimeList();
                    
                    String thisTaskDay = this.pssTask.dayOfWeek("StartDate",this_time_list[1]);
                    
                    if(thisTaskDay.equals(searchedDay))
                    {
                        return true;
                    }
                }
                
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else
        {
            return false;
        }
        return false;
    }
    
    
    //Check for time overlap in terms of duration. 
    public boolean checkTimeOverLap(String name, String type) throws ParseException
    {
        int index = findTask(name, type); 
        
        if(index >= 0)
        {
            Task task = taskList.get(index);
            
            double[] time_list = task.getTimeList();
            double[] this_time_list = this.pssTask.getTimeList();
            
            String searchedDay = task.dayOfWeek("StartDate", time_list[1]);
            String thisTaskDay = this.pssTask.dayOfWeek("Date", this_time_list[0]);
                    
            double foundStartTime = task.getStartTime();
            double foundDuration = task.getDuration();
            
            double currentStartTime = this.pssTask.getStartTime();
            double currentDuration = this.pssTask.getDuration();

            double foundTimeElapsed = foundStartTime + foundDuration;
            double currentTimeElapsed = currentStartTime + currentDuration;
            
            // Account for 1 hour of time to compute the overlaps. 
            double lowerBound = foundTimeElapsed - 1; // 19.25
            double upperBound = foundTimeElapsed + 1; // 21.25
            
            
            if (searchedDay.equals(thisTaskDay)) 
            {
                if (lowerBound <= currentTimeElapsed && currentTimeElapsed < upperBound) 
                {
                    System.out.println("Error! Time Conflict!");
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    
    
    // Can be upgraded with a HashMap 
    
    
    public int findTask(String name, String type)
    {
        int index = -1;
        
        for(int i = 0 ; i < taskList.size() ; i++)
        {
            if(taskList.get(i).getName().equals(name) && taskList.get(i).getType().equals(type))
            {
                index = i;
            }
        }
        return index;
    }
    
}
