package models;

public class UserID {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public UserID(){
        username = "";
        password = "";
        firstName = "";
        lastName = "";
    }

    public UserID(String username, String password, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Set<UserID> users;

    static{
        users = new HashSet<>();
        users.add(new UserID(jdoe, pass, Jane, Doe));
        users.add(new UserID(jschmoe, pass, Joe, Schmoe));
    }

    public static UserID findById(String username){
        for (UserID user: users){
            if (username.equals(user.username)){
                return user;
            }
        }

        return null;
    }

    public static void create(UserID user){
        users.add(user);
    }
}