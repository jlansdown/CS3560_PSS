
import java.util.ArrayList;
import java.util.Iterator;
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
        
        Task pssTask = new Task(list_format, jsonObject);
        
        taskList.add(pssTask);
        
        return pssTask;
    }
    
    //All of the user inputs are in a String array type of format that's realtive to the List_format. 
    public void createTask(String[] list_format, String[] input)
    {
        this.list_format = list_format;
        
        taskList.add(new Task(list_format, input));
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
            System.out.println("Task Name: "+name +" and Type: "+type+" has been deleted!");
        }
        else
        {
            System.out.println("Task Name: "+name +" and Type: "+type+" cannot be found!");
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
    public boolean checkForOverlap()
    {
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
    
    
    //Testing Ideas
    
    //-------------------------Initial Ideas
    //Idea: A list Same tasks Name and Types will be found and will be show to allow users to edit on. 
    /*
    public ArrayList<Integer> findTask(String name, String type)
    {
        ArrayList<Integer> indexList = new ArrayList<Integer>(); 
        
        for(int i = 0 ; i < taskList.size() ; i++)
        {
            if(taskList.get(i).getName().equals(name) && taskList.get(i).getType().equals(type))
            {
                indexList.add(i);
            }
            
        }
        return indexList;
    }*/
    
    
    
    /* // Initial Thought
    // Case and type sensitive.
    // Example: JSONObject jsonObject = pssModel.findTask("CS3560-Tu", "Class");
    // This will find the appropraite task and return JSON object. 
    
    public JSONObject findTask(String name, String type) // O(n) time complexity
    {
        JSONArray jsonSubject = pssController.getJSONFileArray();
        
        JSONObject tempJSONObject;
        JSONObject jsonObject = null;
        
        Iterator<JSONObject> iterator = jsonSubject.iterator();
        
        while(iterator.hasNext())
        {
            tempJSONObject = iterator.next();
            if(name.equals(tempJSONObject.get("Name").toString()) && type.equals(tempJSONObject.get("Type").toString()))
            {
                jsonObject = tempJSONObject;
            }
        }
        if(jsonObject == null)
        {
            System.out.println("Sorry, cannot find such task!"); // Must throw an exception.
            
        }
        return jsonObject;
    }*/
    
    //sendError();
}
