import java.util.*;
public class BloodApp {

    public static String bloodGroupSelection(Scanner sc){
        System.out.println("Please enter your blood group from the options below: ");
        System.out.println("1. A+");
        System.out.println("2. O+");
        System.out.println("3. B+");
        System.out.println("4. AB+");
        System.out.println("5. A-");
        System.out.println("6. O-");
        System.out.println("7. B-");
        System.out.println("8. AB-");
        System.out.println("Write your blood group here: ");
        String group = sc.nextLine();
        return group;
    }

    public static boolean tellYourHistory(Scanner sc){
        System.out.print("You had any major health issue or operation or disease in history? (y/n): ");
        char option = sc.nextLine().charAt(0);
        if(option=='y'||option=='Y'){
            System.out.println();
            System.out.println("#############################################################################################");
            System.out.println("You'll need to connect to one of our doctors to get a checkup");
            System.out.println("Contact us on 9015181701");
            System.out.println("We will create your entry but you'll not be able to donate blood until you have a checkup");
            System.out.println("#############################################################################################");
            System.out.println();
            return true;
        }
        else{
            return false;
        }
    }
    public static void main(String[] args) throws Exception {
        BloodOperations operations = new BloodOperations();
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        int age;
        String name;
        String bloodGroup;
        boolean diseaseHistory;
        String mobile;
        String donarId;
        char firstTimeDonar;
        
        while(loop){
            System.out.println();
            System.out.println();
            System.out.println("-----------------------Welcome to the Blood Bank-----------------------");
            System.out.println("You may use numerical keys to select an option below to continue: \n");
            System.out.println("1. Register youself as a Donor");
            System.out.println("2. Donate Blood! Save Lives!");
            System.out.println("3. Check your appointment");
            System.out.println("4. Check for blood availability and donors");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int selection = sc.nextInt();
            switch (selection) {
                case 1:
                sc.nextLine();
                System.out.print("Enter your name: ");
                name = sc.nextLine();
                System.out.print("Enter your age: ");
                age = sc .nextInt();
                sc.nextLine();
                bloodGroup = bloodGroupSelection(sc);
                System.out.println("Enter your mobile number: ");
                mobile = sc.nextLine();
                diseaseHistory = tellYourHistory(sc);    
                operations.createDoner(name,age,bloodGroup,mobile,diseaseHistory);
                break;

                case 2:
                sc.nextLine();
                System.out.println("Enter your donar id: ");
                donarId = sc.nextLine();
                System.out.println("Donating for the first time?(y/n)");
                firstTimeDonar = sc.nextLine().charAt(0);
                if(firstTimeDonar == 'y'||firstTimeDonar=='Y'){
                    operations.firstTimeDonate(donarId);
                }else{
                    operations.donate(donarId);
                }
                break;

                case 3:
                sc.nextLine();
                System.out.println("Enter your donar id: ");
                donarId = sc.nextLine();
                operations.checkAppointment(donarId);
                break;

                case 4:
                sc.nextLine();
                bloodGroup = bloodGroupSelection(sc);
                operations.checkBloodAvailability(bloodGroup);
                break;

                case 5:
                System.out.println("Thank you for using our blood bank services!");
                loop=false;
                break;
            
                default:
                System.out.println("Wrong Input! Try Again!!");
                break;
            }
        }

    }
}
