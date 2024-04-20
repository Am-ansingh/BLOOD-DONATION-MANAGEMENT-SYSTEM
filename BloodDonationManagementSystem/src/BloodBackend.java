import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BloodBackend {
    BloodDB db = new BloodDB();
    public void addDonar(String userId, String name, int age, String bloodGroup, String mobile, boolean diseaseHistory, boolean ifActive) {
        try {
            String query = BloodAppQuries.insert;
            Connection conn = db.connect();
            PreparedStatement ptm = conn.prepareStatement(query);
            ptm.setString(1, userId);
            ptm.setString(2, name);
            ptm.setInt(3, age);
            ptm.setString(4, bloodGroup);
            ptm.setString(5, mobile);
            ptm.setBoolean(6, diseaseHistory);
            ptm.setBoolean(7, ifActive);
            ptm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDonationAppointment(String donarId, Date date) {
        String bloodGroup = checkBloodGroup(donarId);
        try{
            String query = BloodAppQuries.addAppointment;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            pstm.setString(2, bloodGroup);
            pstm.setDate(3, date);
            pstm.execute();
            System.out.println("Sucessfully registered your appointment for "+date);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Date checkLastDonation(String donarId) {
        Date lastDonated = null;
        try{
            String query = BloodAppQuries.lastDonated;
            Connection conn = db.connect();
            PreparedStatement ptm = conn.prepareStatement(query);
            ptm.setString(1, donarId);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                lastDonated = rs.getDate(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lastDonated;
    }
    public boolean isDonarActive(String donarId) {
        boolean activeStatus = false;
        try{
            String query = BloodAppQuries.isActive;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                activeStatus = rs.getBoolean(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return activeStatus;
    }
    public boolean isDonarRegistered(String donarId) {
        boolean isValid = false;
        try{
            String query = BloodAppQuries.isValid;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                isValid = true;
            }else{
                isValid=false;
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
        return isValid;
    }
    public void addDonation(String donarId, Date currDate) {
        try{
            String query = BloodAppQuries.addDonation;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            pstm.setDate(2, currDate);
            pstm.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addBloodBalance(String donarId) {
        String bloodGroup = checkBloodGroup(donarId);
        try{
            String query = BloodAppQuries.addBloodBalance;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, bloodGroup);
            pstm.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private String checkBloodGroup(String donarId) {
        String bloodGroup = "";
        try{
            String query = BloodAppQuries.checkBloodGroup;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                bloodGroup = rs.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return bloodGroup;
    }
    public void checkAvailability(String bloodGroup) {
        try{
            Connection conn = db.connect();
            if(bloodGroup.equals("O-")){
                String query = BloodAppQuries.bloodForONeg;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "O-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForONeg;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "O-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("O+")){
                String query = BloodAppQuries.bloodForOPos;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "O+");
                pstm.setString(2, "O-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForOPos;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "O+");
                pstm2.setString(2, "O-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("A-")){
                String query = BloodAppQuries.bloodForANeg;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "A-");
                pstm.setString(2, "O-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForANeg;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "A-");
                pstm2.setString(2, "O-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("A+")){
                String query = BloodAppQuries.bloodForAPos;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "A+");
                pstm.setString(2, "A-");
                pstm.setString(3, "O+");
                pstm.setString(4, "O-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForAPos;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "A+");
                pstm2.setString(2, "A-");
                pstm2.setString(3, "O+");
                pstm2.setString(4, "O-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("B-")){
                String query = BloodAppQuries.bloodForBNeg;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "B-");
                pstm.setString(2, "O-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForBNeg;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "B-");
                pstm2.setString(2, "O-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("B+")){
                String query = BloodAppQuries.bloodForBPos;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "B+");
                pstm.setString(2, "B-");
                pstm.setString(3, "O+");
                pstm.setString(4, "O-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForBPos;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "B+");
                pstm2.setString(2, "B-");
                pstm2.setString(3, "O+");
                pstm2.setString(4, "O-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("AB-")){
                String query = BloodAppQuries.bloodForABNeg;
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1, "O-");
                pstm.setString(2, "B-");
                pstm.setString(3, "A+");
                pstm.setString(4, "AB-");
                ResultSet rs = pstm.executeQuery();
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForABNeg;
                PreparedStatement pstm2 = conn.prepareStatement(query2);
                pstm2.setString(1, "O-");
                pstm2.setString(2, "B-");
                pstm2.setString(3, "A+");
                pstm2.setString(4, "AB-");
                ResultSet rs2 = pstm2.executeQuery();
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else if(bloodGroup.equals("AB+")){
                String query = BloodAppQuries.bloodForABPos;
                Statement stm = conn.prepareStatement(query);
                ResultSet rs = stm.executeQuery(query);
                System.out.println("\nAvailables Bottles in blood bank: ");
                while(rs.next()){
                    System.out.print(rs.getString(1)+" : ");
                    System.out.println(rs.getInt(2));
                }

                String query2 = BloodAppQuries.donorForABPos;
                Statement stm2 = conn.prepareStatement(query2);
                ResultSet rs2 = stm2.executeQuery(query2);
                if (!rs2.isBeforeFirst() ) {    
                    System.out.println("No donor available"); 
                } else{
                    while(rs2.next()){
                        System.out.println("\n----------Available Donors----------");
                        System.out.println("Donor Name: "+rs2.getString(1));
                        System.out.println("Donor Age: "+ rs2.getInt(2));
                        System.out.println("Donor Blood Group: "+rs2.getString(3));
                        System.out.println("Donor Mobile: "+rs2.getString(4));
                    }
                }
            }else{
                System.out.println("Wrong Input!!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Date checkAppointmentDate(String donarId) {
        Date appointmentDate = null;
        try{
            String query = BloodAppQuries.checkAppointment;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                appointmentDate = rs.getDate(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return appointmentDate;
    }
    public void cancelAppointment(String donarId, Date currDate) {
        try{
            String query = BloodAppQuries.cancelAppointment;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            pstm.setDate(2, currDate);
            pstm.execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void dropAppointment(String donarId) {
        Date date = checkAppointmentDate(donarId);
        try{
            String query = BloodAppQuries.cancelAppointment;
            Connection conn = db.connect();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, donarId);
            pstm.setDate(2, date);
            pstm.execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    
}
