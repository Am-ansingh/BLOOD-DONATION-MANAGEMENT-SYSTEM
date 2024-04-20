import java.util.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class BloodOperations {
    Scanner sc = new Scanner(System.in);
    BloodBackend backend = new BloodBackend();

    public void createDoner(String name, int age, String bloodGroup, String mobile, boolean diseaseHistory) {
        String userId = createUserId(name,age,mobile);
        if(diseaseHistory){
            backend.addDonar(userId,name,age,bloodGroup,mobile,diseaseHistory,false);
        }else{
            backend.addDonar(userId,name,age,bloodGroup,mobile,diseaseHistory,true);
        }
        System.out.println("Succesfully Saved your information as a donor");
    }

    private String createUserId(String name, int age, String mobile) {
        return name.toUpperCase().charAt(0) +""+ age +""+ mobile;

    }

    public void donate(String donarId) {
        if(validateDonar(donarId)){
            if(isDonarActive(donarId)){
                if(isDonarEligible(donarId)){
                    System.out.println("Ready to donate now? or you can book and appointment of a later date(y/n): ");
                    char readyNow = sc.nextLine().charAt(0);
                    if(readyNow=='y'||readyNow=='Y'){
                        long millis=System.currentTimeMillis();  
                        Date currDate = new Date(millis);
                        backend.addDonation(donarId, currDate);
                        backend.addBloodBalance(donarId);
                        System.out.println("Suceessfully added your donation in records!!!!");
                    }else{
                        System.out.println("Type in a date of your preference to shedule donation (20xx-00-00): ");
                        String inputDate = sc.nextLine();
                        Date date = Date.valueOf(inputDate);
                        backend.addDonationAppointment(donarId,date);
                    }
                }else{
                    System.out.println("You had recently donated blood. You can't donate blood now!");
                }
            }else{
                System.out.println("You had a disease in your medical history you first need to consult one of our doctors");
            }
        }else{
            System.out.println("You aren't registed as a donar with us. Select 1 in the menu to first register yourself!");
        }
    }

    private boolean isDonarEligible(String donarId) {
        long millis=System.currentTimeMillis();  
        Date currDate = new Date(millis);
        Date lastDonation = backend.checkLastDonation(donarId);
        System.out.println(currDate);
        System.out.println(lastDonation);
        if(lastDonation==null){
            return true;
        }
        LocalDate localCurrDate = currDate.toLocalDate();
        LocalDate localLastDonation = lastDonation.toLocalDate();
        long differenceInDays = ChronoUnit.DAYS.between(localLastDonation, localCurrDate);
        System.out.println(differenceInDays);
        if(differenceInDays>=90){
            return true;
        }else{
            return false;
        }
    }

    private boolean isDonarActive(String donarId) {
        boolean activeStatus = backend.isDonarActive(donarId);
        return activeStatus;
    }

    private boolean validateDonar(String donarId) {
        boolean validUser = backend.isDonarRegistered(donarId);
        return validUser;
    }

    public void firstTimeDonate(String donarId) {
        if(validateDonar(donarId)){
            if(isDonarActive(donarId)){
                System.out.println("Ready to donate now? or you can book and appointment of a later date(y/n): ");
                char readyNow = sc.nextLine().charAt(0);
                if(readyNow=='y'||readyNow=='Y'){
                    long millis=System.currentTimeMillis();  
                    Date currDate = new Date(millis);
                    backend.addDonation(donarId, currDate);
                    backend.addBloodBalance(donarId);
                    System.out.println("Suceessfully added your donation in records!!!!");
                }else{
                    System.out.println("Type in a date of your preference to shedule donation (20xx-00-00): ");
                    String inputDate = sc.nextLine();
                    Date date = Date.valueOf(inputDate);
                    backend.addDonationAppointment(donarId,date);
                }
            }else{
                System.out.println("You had a disease in your medical history you first need to consult one of our doctors");
            }
        }else{
            System.out.println("You aren't registed as a donar with us. Select 1 in the menu to first register yourself!");
        }
    }

    public void checkBloodAvailability(String bloodGroup) {
        backend.checkAvailability(bloodGroup);
    }

    public void checkAppointment( String donarId) {
        Date appointment = backend.checkAppointmentDate(donarId);
        if(appointment==null){
            System.out.println("You have no appointments. Add one to donate!");
            System.out.println("Select 2 from menu to donate or add appointments");
        }
        long millis=System.currentTimeMillis();  
        Date currDate = new Date(millis);
        LocalDate localCurrDate = currDate.toLocalDate();
        LocalDate localAppointment = appointment.toLocalDate();
        long differenceInDays = ChronoUnit.DAYS.between(localCurrDate,localAppointment);
        if(differenceInDays>0){
            System.out.println("You have some days left for your appointment. Days left = "+differenceInDays);
        }
        else if(differenceInDays==0){
            System.out.println("Your appointment is scheduled for today!!");
            System.out.println("Have you already donated blood?(y/n): ");
            char donated = sc.nextLine().charAt(0);
            if(donated=='y'||donated=='Y'){
                backend.addDonation(donarId, currDate);
                backend.addBloodBalance(donarId);
                System.out.println("Succefully updated your donation in our systems!!");
            }else{
                System.out.println("You can rush to our nearest center and donate blood today or you can cancel this appointment to add another");
                System.out.println("To cancel this appointment enter 1: ");
                int selection = sc.nextInt();
                if(selection==1){
                    System.out.println("Successfully canceled your appointments!!");
                    backend.cancelAppointment(donarId,currDate);
                }

            }
        }else if(differenceInDays<0){
            System.out.println("You have missed your appointment you can add new appointment by going to option 2 from main menu");
            backend.dropAppointment(donarId);
        }
    }
    
}
