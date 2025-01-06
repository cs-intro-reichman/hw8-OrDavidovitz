/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i=0; i<userCount;i++)
        {
            if (users[i].getName().equals(name))
            {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (userCount==users.length){
            return false;
        }
        for (int i=0; i<userCount;i++)
        {
            if (users[i].getName().equals(name))
            {
                return false;
            }
        }
        users[userCount]= new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (getUser(name1)==null||getUser(name2)==null)
        return false;
        return getUser(name1).addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int maxmutal=0;
        String thename="no one";
        for (int i=0;i<userCount;i++)
        {
            if (!name.equals(users[i].getName()))
            {
                  if (getUser(name).countMutual(users[i])>maxmutal)
                  {
                    maxmutal=getUser(name).countMutual(users[i]);
                    thename=users[i].getName();
                  }
            }
        }
        return thename;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int maxFollows = 0;
        String theName = "";
        // Iterate over all users to check who follows whom
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {  // Check if the current user is not null
                int follows = 0;  // Initialize follows count for the current user
                // Check each other user in the array
                for (int j = 0; j < users.length; j++) {
                    if (users[j] != null && users[j].follows(users[i].getName())) {  // Ensure both users[j] is not null and safely call follows
                        follows++;
                    }
                }
                // Update the most popular user if the current one has more followers
                if (follows > maxFollows) {
                    maxFollows = follows;
                    theName = users[i].getName();
                }
            }
        }
        return theName;
    }
    
    

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        for (int i=0; i<users.length;i++)
        {
            if(users[i].follows(name))
            {
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "";
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {  // Only process non-null users
                ans += users[i].toString();  // Concatenate the user's description
            }
            // No else part needed because we don't want to append anything if the user is null
        }
        return ans;
    }
    
    
}
