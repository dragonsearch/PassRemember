import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
public class PassRememberer {
	
         /**
        * Really simple class pair.
        */
        static class Pair implements Serializable, Comparable{
            String name;
            String password;
            /**
             * Constructor for class pair.
             * @param Name
             * @param Password 
             */
            public Pair(String Name, String Password){
                name = Name;
                password = Password;
            }
            /**
             * Set the name of this pair.
             * @param Name 
             */
            public void setName(String Name){
                name = Name;
            }
            /**
             * Set the password of this pair.
             * @param Password 
             */
            public void setPassword(String Password){
                password = Password;
            }
            /**
             * Get the name for this pair.
             * @return String
             */
            public String getName(){
                return name;
            }
            /**
             * Get the password for this pair.
             * @return String
             */
            public String getPassword(){
                return password;
            }
            @Override
            public int compareTo(Object t) {
                if(t == null){
                    throw new NullPointerException();
                }
                if(!(t instanceof Pair))
                    throw new ClassCastException();
                Pair pair = (Pair) t;

                    return name.compareTo(pair.getName());
                
            }
        }
        /**
         *  Main method.
         * @param args
         * @throws java.io.FileNotFoundException
         * @throws java.io.IOException
         */
	public static void main(String[] args) throws FileNotFoundException, IOException{
            launch();
            
        }
        /**
         * Launching method.
         */
        public static void launch(){
            Scanner sc = new Scanner(System.in).useLocale(Locale.US);
                HashMap<String, ArrayList<Pair>> dicc;
                try {
                   FileInputStream fileIn = new FileInputStream("hash.ser");
                   ObjectInputStream in = new ObjectInputStream(fileIn);
                   dicc = (HashMap) in.readObject();
                   in.close();
                   fileIn.close();
                } catch (IOException i) {
                   System.out.println("Not found files with existing passwords, generating one empty");
                   dicc  = new HashMap<>();
                } catch (ClassNotFoundException c) {
                   System.out.println("Class not found, exiting.");
                   return;
                }
		//File passwordtxt = new File("password.txt");
//		HashMap<String, ArrayList<String>> anexo  = new HashMap<>();
		System.out.println("Password:");
		boolean exit = true;
		while(true && exit) {
			if(sc.nextLine().equals("27")) {
				exit = false;
			}
		}
		
		exit = true;
                ArrayList<String> keywords = new ArrayList<>(Arrays.asList("write","help","exit","list","remove","removeall","modify"));
		while(true && exit) {
                    System.out.println("Write a service to display accounts from. For example, twitter, microsoft... for more information write help. To leave write exit");
                    String nextinput = sc.next().toLowerCase();
                    String service;
                    String username;
                    String password;
			switch(nextinput) {
                            	case "list":
                                        System.out.println("There is a total of" + dicc.keySet().size() + "services:");
					dicc.keySet().forEach((c)->{
                                            System.out.println(c);
                                        });
					break;
				case "exit":
					exit = false;
					break;
				case "help":
					System.out.println("Special commands /reserved words/ :");
                                        System.out.println("Write: Adds a new entry of name, password and service.");
                                        System.out.println("Modify: Edits a existing entry. (username and password, not service)");
                                        System.out.println("Remove: deletes an entry");
                                        System.out.println("List: shows all the services");
                                        System.out.println("Exit: closes the program. You can always do exit.");
                                        System.out.println("Removeservice: removes every single entry in the service.");
                                        System.out.println("Removeall: removes every single entry.");
                                        System.out.println("Help: shows this page.");
					break;
                                case "write":
                                        System.out.println("Service:");
                                        service = sc.next();
                                        
                                        while(keywords.contains(service)){
                                            System.out.println("Not a valid service, if you are using a reserved keyword, try with something like # before");
                                            service =  sc.next();
                                        }
                                        boolean containedd = true;
                                        ArrayList<Pair> listed = dicc.get(service);
                                        if(listed == null){
                                            listed = new ArrayList<>();
                                        }
                                        username = "";
                                        while(containedd && !username.equals("exit")){
                                            System.out.println("Write a non-existing username:");
                                            username = sc.next();
                                            containedd = false;
                                            Iterator it = listed.iterator();
                                                while(it.hasNext() && !containedd){
                                                    if(((Pair)it.next()).getName().equals(username)){
                                                        containedd = true;
                                                        
                                                    }
                                                }
                                        }
                                        
                                        System.out.println("Password:");
                                        password = sc.next();
                                        ArrayList<Pair> tmp =  new ArrayList<>();
                                        tmp.add(new Pair(username,password));
                                        dicc.put(service.toLowerCase(),tmp);
                                        System.out.println("Done!");
                                        break;
                                case "modify":
                                        System.out.println("Service:");
                                        service = sc.next().toLowerCase();
                                        if(dicc.isEmpty()){
                                            System.out.println("Nothing to modify");
                                            break;
                                        }
                                        while(!dicc.containsKey(service) && !service.equals("exit")){
                                            System.out.println("Write an existing service:");
                                            System.out.println("There is a total of " + dicc.keySet().size() + " services:");
                                            dicc.keySet().forEach((c)->{
                                                System.out.println(c);
                                            });
                                            service = sc.next();
                                        }
                                        ArrayList<Pair> list = dicc.get(service);
                                        if(list == null){
                                            list = new ArrayList<>();
                                        }
                                        boolean contained = false;
                                        username = "";
                                        while(!contained && !username.equals("exit")){
                                            System.out.println("Write an existing username:");
                                            username = sc.next();
                                            Iterator it = list.iterator();
                                                while(it.hasNext() && !contained){
                                                    if(((Pair)it.next()).getName().equals(username)){
                                                        contained = true;
                                                        it.remove();
                                                        if(it.hasNext()){
                                                            it.next();
                                                        }
                                                    }
                                                }
                                        }
                                        System.out.println("-MODIFYING-(DON'T CLOSE)");
                                        System.out.println("New username:");
                                        username = sc.next();
                                        while(username.equals("exit")){
                                            System.out.println("Cant use this reserved word. New username:");
                                            username = sc.next();
                                        }
                                        System.out.println("New password:");
                                        password = sc.next();
                                        list.add(new Pair(username,password));                                     
                                        System.out.println("Done!");
                                        break;
                                case "remove":
                                    System.out.println("Service:");
                                        service = sc.next().toLowerCase();
                                        if(dicc.isEmpty()){
                                            System.out.println("Nothing to remove");
                                            break;
                                        }
                                        while(!dicc.containsKey(service) && !service.equals("exit")){
                                            System.out.println("Write an existing service:");
                                            System.out.println("There is a total of " + dicc.keySet().size() + " services:");
                                            dicc.keySet().forEach((c)->{
                                                System.out.println(c);
                                            });
                                            service = sc.next();
                                        }
                                        ArrayList<Pair> lists = dicc.get(service);
                                        if(lists == null){
                                            lists = new ArrayList<>();
                                        }
                                        boolean containeds = false;
                                        username = "";
                                        while(!containeds && !username.equals("exit")){
                                            System.out.println("Write an existing username:");
                                            username = sc.next();
                                            Iterator it = lists.iterator();
                                                while(it.hasNext() && !containeds){
                                                    if(((Pair)it.next()).getName().equals(username)){
                                                        containeds = true;
                                                        it.remove();
                                                        if(it.hasNext()){
                                                            it.next();
                                                        }
                                                    }
                                                }
                                        }
                                        if(dicc.get(service).isEmpty()){
                                            dicc.remove(service);
                                        }
                                        System.out.println("Done!");
                                        break;
                                case "removeall":
                                    
                                    System.out.println("Are you sure? y/n");
                                    if(sc.next().equals("y")){
                                        dicc = new HashMap<>();
                                        System.out.println("Done!");
                                        break;
                                    }
                                    System.out.println("Aborted!");
                                    break;
                                case "removeservice":
                                
                                    System.out.println("Service:");
                                    service = sc.next();
                                    ArrayList<Pair> tmpo = dicc.get(service);
                                    if(tmpo == null){
                                        System.out.println("Nothing done, there is no service named like that");
                                        break;
                                    }
                                    dicc.remove(service);
                                    System.out.println("Done!");
                                    break;
                                default:
                                    ArrayList<Pair> array = dicc.get(nextinput);
                                    if(array != null){
                                         array.forEach((c)->{
                                            System.out.println("Username: " + c.getName());
                                            System.out.println("Password: " + c.getPassword());
                                            System.out.println("____________________________________");
                                        });     
                                    }else{
                                        System.out.println("404: Service Not Found!");
                                    }
                                               
                        }
                }
        try{
        FileOutputStream fileOut = new FileOutputStream("hash.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(dicc);
         out.close();
         fileOut.close();
        }catch(IOException e){
            System.out.println("A SERIOUS PROBLEM HAS APPEARED. YOUR PROGRESS MAY BE NOT SAVED");
        }
        }
}
        
      