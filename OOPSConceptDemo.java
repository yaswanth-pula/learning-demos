package ConceptsDemo;

/**
 * This File Demonstrate Oops Concept
 * We Consider Computer as a Real World Entity
 * Concepts implemented
 * -> Classes and Objects
 * -> Encapsulation
 * -> Polymorphism
 * -> Abstraction
 *      |-> With Abstract class and methods
 *      |-> with Interfaces
 * -> Inheritance
 *      |-> Single level inheritance
 *      |-> Multi-level inheritance
 *      |-> Multiple inheritance (with help of Interfaces)
 *
 * */

// Abstraction with Interfaces
interface Processor{
    double getProcessorSpeedInGhz();
}

interface SSD{
    int getSSDSize();
}
interface Ram{
    String getRamType();
}

// In Interfaces, new Versions of java supports default and static methods also.
interface KeyBoard{
    default void printKeyboardStatus(){
        System.out.println("Keyboard is ALL OKAY For Handling keystrokes\n");
    }
}
interface Mouse{
    // interface can only have final and static values by default
    static final String mouseType = "Laser";
    static void printMouseStatus(){
        System.out.println(mouseType+" Mouse is ALL OKAY\n");
    }
}



// abstracted interfaces methods are implemented
// Since CpuSetup implements above interfaces
// we can also observe multiple inheritance with help of interfaces.
class CpuSetup implements Processor,SSD,Ram{

    @Override
    public double getProcessorSpeedInGhz() {
        return 4.12;
    }

    @Override
    public int getSSDSize() {
        return 512;
    }

    @Override
    public String getRamType() {
        return "DDR4";
    }
    // this class can also have its own variables and methods.
}


// Abstraction with Abstract class and abstract methods.
// we can observe single level inheritance here.
abstract class HardwareSetup extends CpuSetup implements KeyBoard,Mouse{

    // abstract method
    public abstract String handleUsbPort();

    // non abstract methods and values;
    // polymorphism - method Overriding
    static void printMouseStatus(){
        System.out.println("Normal Mouse is ALL OKAY\n");
    }

    public void printInputDeviceStatus(){
        printKeyboardStatus();
        // interface method is referenced.
        Mouse.printMouseStatus();
        // own class method is referenced.
        printMouseStatus();
    }

    final static int MODEL_NUMBER = 324921;

    protected static void getHardwareModel(){
           System.out.println("Hardware Model is "+MODEL_NUMBER+"\n");
    }


}

// Inheritance with extends
// we can also observe multilevel inheritance
// cpuSetup <- hardwareSetup <- Computer
class Computer extends HardwareSetup{
   public String handleUsbPort() {
        final String usbStatus = "USB Port is All Ready for Input";
        return usbStatus;
    }
    public void getComputerDesc(){
        System.out.println(
                "CPU SPEED: "+ getProcessorSpeedInGhz()+" Ghz\n"+
                "SSD SIZE: "+ getSSDSize()+" GB\n"+
                "RAM Type: "+ getRamType()+" \n"
        );
        printInputDeviceStatus();
        getHardwareModel();
    }

}

// Encapsulation
class UserManagement {
    private final int authToken = 21442321;
    private static String SysPassword = "XsOqkL";

    public int validateUser(String userPassword){
        if(SysPassword.equals(userPassword))
            return authToken;
        else
            return -1;
    }

    protected void updateUserPassword(String newPassword){
        SysPassword = newPassword;
        System.out.println("Password Updated Successfully\n");
    }

    // Polymorphism - Method Overloading
    public void greetUser(){
        System.out.println("Hello Guest!");
    }
    public void greetUser(String userName){
        System.out.println("Hello "+userName+" Welcome !");
    }

}

// Class And Objects
public class OOPSConceptDemo {
    public static void main(String[] args){

        Computer newUserComputer = new Computer();
        newUserComputer.getComputerDesc();

        UserManagement user = new UserManagement();

        int authToken = user.validateUser("XsOqkL");
         if (authToken>0) {
             user.greetUser("Admin");
             System.out.println("User Authenticated");
         }
         else {
             user.greetUser();
             System.out.println("Access Denied");
         }
        user.updateUserPassword("admin123");

    }
}
