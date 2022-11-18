package Task3_ClassesVersion;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Scanner input =new Scanner(System.in);
        char control;
//        Room r0= new Room(0,"e",null,0);
//        Room r1= new Room(1,"e",null,0);
//        Room r2= new Room(2,"e",null,0);
//        Room r3= new Room(3,"e",null,0);
//        Room r4= new Room(4,"e",null,0);
//        Room r5= new Room(5,"e",null,0);
//        Room r6= new Room(6,"e",null,0);
//        Room r7= new Room(7,"e",null,0);
//
//        Room[] rooms={r0,r1,r2,r3,r4,r5,r6,r7};
          Room[] rooms = initialise();




        while(true) {
            System.out.println("\n--boarding system for Hotel --\n\n");
            System.out.println("\n--Main Menu--\n");
            System.out.print("""

                    V: Views All Rooms
                    E: Display Empty Rooms
                    A: Add a customer to a Room
                    D: Delete customer from Room
                    F: Find Room from customer name
                    S: Store Program Data into file
                    L: Load program data from file
                    O: View Guests Ordered alphabetically by name
                    >\s""");
            control=input.next().charAt(0);
            switch (control) {
                case 'A' -> addCustomers(rooms);
                case 'E' -> viewEmptyRooms(rooms);
                case 'D' -> deleteCustomer(rooms);
                case 'F' -> searchByName(rooms);
                case 'L' -> loadData(rooms);
                case 'O' -> orderCustomers(rooms);
                case 'S' -> storeData(rooms);
                case 'V' -> viewAllRooms(rooms);
                default -> System.out.println("Invalid Output");
            }
            Thread.sleep(2000);
        }
    }



    private static void addCustomers( Room[] list) {
        int roomNum;
        int noGuests = 0;
        String payeeSName = "";
        String payeeFName="";
        long payeeCard=0;

        String roomName="";
        Scanner input = new Scanner(System.in);
        System.out.println("Enter room number (0-7) or  8 to stop:");
        roomNum = input.nextInt();
        while (roomNum < 8) {
            System.out.println("Enter name for room " + roomNum + " :");
            roomName = input.next();
            System.out.println("Enter no of guests for the room");
            noGuests = input.nextInt();
            System.out.println("Enter payee  Surname");
            payeeSName = input.next();
            System.out.println("Enter payee First Name");
            payeeFName= input.next();
            System.out.println("Enter payee Card no");
            payeeCard = input.nextLong();
            System.out.println("Room booked successfully");
            break;
        }
        var p = new Person(payeeFName,payeeSName,payeeCard);
        Room r= new Room(roomNum,roomName,p,noGuests);
        list[roomNum]=r;

    }

    private static void viewEmptyRooms(Room[] r){
        for(int i=0;i<r.length;i++){
            if(r[i].getRoomName().equals("e")){
                System.out.println("Room no " + i + "is Empty");
            }
        }
    }

    private static Room[] initialise(){
        Room[]  r= new Room[8];
        r[0]= new Room(0,"e",null,0);
        r[1]= new Room(1,"e",null,0);
        r[2]= new Room(2,"e",null,0);
        r[3]= new Room(3,"e",null,0);
        r[4]= new Room(4,"e",null,0);
        r[5]= new Room(5,"e",null,0);
        r[6]= new Room(6,"e",null,0);
        r[7]= new Room(7,"e",null,0);


        return r;
    }


    private static void orderCustomers(Room[] r){
        String[] arr = new String[r.length];
        for(int i=0;i<r.length;i++){
            arr[i]= r[i].getRoomName();
        }
        Arrays.sort(arr,String.CASE_INSENSITIVE_ORDER);
        System.out.println("\n \n Names are sorted in alphabetic order\n \n");
        for (String s : arr) {
            if (!s.equals("e")) System.out.println(s);
        }
    }

    private static void viewAllRooms(Room[] r){
        for(int x=0;x<r.length;x++){
            if(r[x].getRoomName().equals("e")) System.out.println("room " + x + " is occupied by None ");
            else System.out.println("room " + x + " is occupied by " +  r[x].getRoomName());
        }


    }

    private static void deleteCustomer(Room[] r){
        viewAllRooms(r);
        int roomNum;
        Scanner input = new Scanner(System.in);


        while(true){
            System.out.println("Enter room number (0-7) to delete or  8 to stop:");
            roomNum=input.nextInt();
            if( roomNum>0 &&roomNum<8){
                if(r[roomNum].getRoomName().equals("e"))  return;
                r[roomNum].setRoomName("e");
                r[roomNum].setNoOfGuests(0);
                r[roomNum].setRoomNo(roomNum);
                r[roomNum].setPayee(null);

            }else if(roomNum==8) break;
            else System.out.println("Invalid room number ");
        }


    }
    private static void searchByName(Room[] r){
        System.out.println("Enter the name to Search");
        Scanner input = new Scanner(System.in);
        String searchName= input.next();
        for(int i=0;i<r.length;i++){
            if(r[i].getRoomName().equalsIgnoreCase(searchName)){
                System.out.println("Room No :" +i);
                break;
            }


        }
    }


    private static void storeData(Room[] r) {
        try {
            File file = new File("details.txt");
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i <r.length; i++) {
                writer.write("Room No" + "-" + i + "-" + r[i].getRoomName()+"paying by"+ r[i].payee.getSurName()+"-"+ r[i].payee.getFirstName()+r[i].payee.getCardNo()+" no of guests" +r[i].getNoOfGuests() + "\n");
            }
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    private static   void loadData(Room[] r){

        try{

            File file= new File("details.txt");
            Scanner rdr = new Scanner(file);
            while(rdr.hasNextLine()){
                String data= rdr.nextLine();
                String[] values= data.split("-");
                r[Integer.parseInt(values[1])].setRoomName(values[2]);
                r[Integer.parseInt(values[1])].setRoomNo(Integer.parseInt(values[1]));
                r[Integer.parseInt(values[1])].payee.setSurName(values[4]);
                r[Integer.parseInt(values[1])].payee.setFirstName(values[5]);
                r[Integer.parseInt(values[1])].payee.setFirstName(values[5]);
                r[Integer.parseInt(values[1])].payee.setCardNo(Integer.parseInt(values[6]));
                r[Integer.parseInt(values[1])].payee.setFirstName(values[5]);
                r[Integer.parseInt(values[1])].setNoOfGuests(Integer.parseInt(values[8]));
            }
            System.out.println("Load the data successfully!");


        }catch(Exception ex){
            ex.getStackTrace();
        }

    }



}
