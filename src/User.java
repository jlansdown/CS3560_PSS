

public class User implements UserInterface
{
    String name = "";
    
    public User(String name)
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
