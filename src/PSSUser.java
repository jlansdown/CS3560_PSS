

public class PSSUser implements User
{
    String name = "";
    
    public PSSUser(String name)
    {
        this.name = name;
    }
    
    public String getUserName()
    {
        return name;
    }
    
    public void setUserName(String name)
    {
        this.name = name;
    }
}
