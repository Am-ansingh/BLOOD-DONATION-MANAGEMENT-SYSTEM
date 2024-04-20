public class BloodAppQuries {

    static String insert = "INSERT INTO donar_details VALUES(?,?,?,?,?,?,?)";
    static String lastDonated = "SELECT donation_date from donation_history WHERE donar_id = ? ORDER BY donation_date DESC LIMIT 1";
    static String isActive = "SELECT is_active from donar_details WHERE donar_id = ?";
    static String isValid = "SELECT donar_id from donar_details WHERE donar_id = ?";
    static String addDonation = "INSERT INTO donation_history VALUES(?,?)";
    static String checkBloodGroup = "SELECT blood_group from donar_details WHERE donar_id = ?";
    static String addBloodBalance = "UPDATE blood_balance SET available_bottles = available_bottles + 1 WHERE blood_group = ?";
    static String bloodForONeg = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ?";
    static String donorForONeg = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ?";
    static String bloodForOPos = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ? OR blood_group = ?";
    static String donorForOPos = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ? OR blood_group = ?";
    static String bloodForANeg = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ? OR blood_group = ?";
    static String donorForANeg = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ? OR blood_group = ?";
    static String bloodForAPos = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ? OR blood_group = ? OR blood_group = ? OR blood_group = ?";
    static String donorForAPos = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ? OR blood_group = ? OR blood_group = ? OR blood_group = ?";
    static String bloodForBNeg = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ? OR blood_group = ?";
    static String donorForBNeg = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ? OR blood_group = ?";
    static String bloodForBPos = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ? OR blood_group = ? OR blood_group = ? OR blood_group = ?";
    static String donorForBPos = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ? OR blood_group = ? OR blood_group = ? OR blood_group = ?";
    static String bloodForABNeg = "SELECT blood_group, available_bottles from blood_balance WHERE blood_group = ? OR blood_group = ? OR blood_group = ? OR blood_group = ?";
    static String donorForABNeg = "SELECT name, age, blood_group, mobile from donar_details WHERE blood_group = ? OR blood_group = ? OR blood_group = ? OR blood_group = ?";
    static String bloodForABPos = "SELECT blood_group, available_bottles from blood_balance";
    static String donorForABPos = "SELECT name, age, blood_group, mobile from donar_details";
    static String addAppointment = "INSERT INTO appointments VALUES(?,?,?)";
    static String checkAppointment = "SELECT appointment_date from appointments WHERE donor_id = ? ORDER BY appointment_date DESC LIMIT 1";
    static String cancelAppointment = "DELETE FROM appointments WHERE donor_id = ? AND appointment_date = ?";
}
