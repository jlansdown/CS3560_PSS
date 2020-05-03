



import java.io.FileNotFoundException; 
import java.io.PrintWriter; 
import java.util.LinkedHashMap; 
import java.util.Map; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PSS implements PSSInterface
{
    
    private String fileName;
    
    private JSONArray subjects;
    
    private FileWriter outFile;
    
    private Iterator<JSONObject> iterator;
   
    private int size;           //length or size of the total JSON file. Useful for making arrays
    
    private Model pssModel;
    
    private ArrayList<Task> taskList;
    
    private String[] list_format;
    
    /*
    Need to verify whether the user has a file or not. If not, user can create a file. 
    */
    /*
    public PSSController()
    {
        fileName = "";
        pssModel = new PSSModel();
        readFromFile(fileName);
    }
    */
    
    public PSS(String fileName)
    {
        this.fileName = fileName;

        pssModel = new Model(this);
        
        readFromFile(fileName);
        
        taskList = pssModel.getTaskList();
        
    }
    
    //Edit this later. Not important yet.
    @Override
    public void viewSchedule()
    {
        
    }
    
    @Override
    public void printSchedule() // Initial stage development. Utilize Schedule for info storage.
    {
        for(int i = 0; i < taskList.size(); i++)
        {
            taskList.get(i).viewTask();
        }
    }
    
    @Override
    public void storeToFile(String fileName)
    {
        ArrayList<String> taskComponents;
        
        JSONArray jsonArray = new JSONArray();
        
        JSONObject jsonObject;
        

        for (int k = 0; k < taskList.size(); k++) 
        {
            
            jsonObject = new JSONObject();
            taskComponents = taskList.get(k).getAllComponents();
            
            for (int i = 0; i < list_format.length; i++) 
            {
                jsonObject.put(list_format[i], taskComponents.get(i));
            }
            
            
            jsonArray.add(k, jsonObject);
        }
        
        try 
        {
            outFile = new FileWriter(fileName);
            jsonArray.writeJSONString(outFile);

        } catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                outFile.flush();
                outFile.close();
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    public Model getPSSModel()
    {
        return pssModel;
    }
    
    public JSONArray getJSONFileArray()
    {
        return subjects;
    }
    
    /*
        Reading from file will also create tasks during run time.
    */
    
    @Override
    public void readFromFile(String fileName)
    {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        
        try 
        {
            Object obj = parser.parse(new FileReader(fileName));

            subjects = (JSONArray) obj;

            size = subjects.size();

            iterator = subjects.iterator();

        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        
        String[] list_format = 
        {
            "Name",
            "Type",
            "Date",
            "StartDate",
            "StartTime",
            "Duration",
            "EndDate",
            "Frequency"
        };
        
        this.list_format = list_format;
        
        //Loop through JSON file and store it in runtime and RAM. 
        
        while(iterator.hasNext())
        {
            jsonObject = iterator.next();
            pssModel.createTask(list_format, jsonObject);
        }
        
    }
    
}
