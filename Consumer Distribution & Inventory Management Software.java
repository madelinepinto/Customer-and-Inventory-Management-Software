/*@Author: Madeline Pinto         @Date: Nov 9 2020
 * 
 * ICS 4U CPT 2020
 * Consumers Distributing Point of Sale and Inventory Management Software
 * 
 * Table of contents:
 * 
 * Line 49: main() 
 * Line 51: imports data from Catalogue
 * Line 70: imports data from Customer List
 * Line 109: Menu -- Switch
 * Line 116: Start of POS - in main()
 *        user enters ID
 *        user enters product code
 *        user enters quantity
 *        adds items to PickDisplay
 *        asks user to purchase more items        
 * Line 259: Start of Iventory Management
 *       +startInv(ArrayList<CatalogueData> catItem): void 
 *       -addCat(ArrayList<CatalogueData> catItem): void
 *       -removeCat(ArrayList<CatalogueData> catItem): void
 *       -modInv(ArrayList<CatalogueData> catItem): void
 *       +checkCode(int code, ArrayList<CatalogueData> catItem, int x): int
 *       -displayCat(ArrayList<CatalogueData> catItem): void
 *       +checkInv(ArrayList<CatalogueData> catItem, int index, int qty): int
 *       +itemSold(ArrayList<CatalogueData> catItem, int index, int qty): void
 *       +itemCheck(ArrayList<Integer> items, int code): int
 * Line 679: Start of Customer Management
 *       +startCust(ArrayList<CustomerData> custList): void
 *       +addCust(ArrayList<CustomerData> custList, int x): String
 *       -removeCust(ArrayList<CustomerData> custList): void
 *       -displayCust(ArrayList<CustomerData> custList): void
 *       +checkCust(int id, ArrayList<CustomerData> custList): String
 *       +checkIndex(int id, ArrayList<CustomerData> custList): int  
 * Line 948: Start of Pick Display
 *       +shoppingList(int code, int qty, int stock): void
 *       +customerInfo(String x): void
 *       +checkout(): void
 * Line 988: CatalogueData 
 * Line 1009: CustomerData
 * */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;

class CPTMadeline{
  public static void main (String[]arg){
    //import Catalogue and Customer List from file
     ArrayList<CatalogueData> catItem = new ArrayList<CatalogueData>();
      try {
         File catFile = new File ("Catalogue.txt"); //points to txt file
         Scanner diskScanner = new Scanner (catFile); //populates the scanner
        
         while (diskScanner.hasNextLine()){//checks to see if the scanner has a next line
           
         //Populates the Array with the information from the file.
            catItem.add(new CatalogueData (diskScanner.nextInt(),diskScanner.nextInt(),diskScanner.nextDouble(),diskScanner.nextLine()));
         }//end while       
                 
             
         diskScanner.close(); // close scanner
         
      }catch (FileNotFoundException e){
         System.err.print(e);
      }
   
      
      ArrayList<CustomerData> custList = new ArrayList<CustomerData>();
   
      try {
         File custFile = new File ("Customers.txt"); //points to txt file
         Scanner diskScanner = new Scanner (custFile); //populates the scanner
        
         while (diskScanner.hasNextLine()){//checks to see if the scanner has a next line
           
         //Populates the Array with the information from the file.
            custList.add(new CustomerData (diskScanner.nextInt(),diskScanner.next(),diskScanner.nextLine()));
         }//end while       
                 
             
         diskScanner.close(); // close scanner
         
      }catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
    int x, id, code, qty, index;
    Scanner in = new Scanner(System.in);
    //Menu to start program
    System.out.println("MENU-------Please select an option--------------------------------------------\n"
                         +"1 - Store management - Inventory Management\n"
                         +"2 - Store management - Customer Management\n"
                         +"3 - Point of Sales");
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    x = in.nextInt();
    while(x<1||x>3){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      x = in.nextInt();
    }
    //creates an object of Inventory Management 
    InventoryManagement obj1 = new InventoryManagement();
    //creates an object of Customer Management
    CustomerManagement obj2 = new CustomerManagement();
    switch(x) {
  case 1:    
    obj1.startInv(catItem); //takes user to Inventory Management Menu
    break;    
  case 2:    
    obj2.startCust(custList); //takes user to Customer Management Menu
    break;  
//  Start of POS
  case 3:
    //creates an object of Pick Display
    PickDisplay obj3 = new PickDisplay();
    String name;
    int y = 1;
    ArrayList<Integer> items = new  ArrayList<Integer>();
    //POS Menu
    System.out.println("Welcome to the Point of Sales Menu----------------------------------------------\n"
                         +"Please enter your Customer ID.");
    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    id = in.nextInt();
    //checkCust()-- Returns "no" if invalid. Returns Customer's name if valid. If invalid -- adds a new customer.
    if(obj2.checkCust(id, custList) != "no"){
      name = obj2.checkCust(id, custList);
    }
    else{
      System.out.println("Hello, the Customer ID you are entering does not exist.\n"
                           +"Adding a new customer...");
      name = obj2.addCust(custList, 0);
    }
    //Adds data to Pick Display class
     obj3.customerInfo(name);
      
    do{
      //clears catItem arrayList and reads from file
       catItem.clear();
      try {
         File catFile = new File ("Catalogue.txt"); //points to txt file
         Scanner diskScanner = new Scanner (catFile); //populates the scanner
        
         while (diskScanner.hasNextLine()){//checks to see if the scanner has a next line
           
         //Populates the Array with the information from the file.
            catItem.add(new CatalogueData (diskScanner.nextInt(),diskScanner.nextInt(),diskScanner.nextDouble(),diskScanner.nextLine()));
         }//end while       
                 
             
         diskScanner.close(); // close scanner
         
      }catch (FileNotFoundException e){
         System.err.print(e);
      }
    
    System.out.println("Please enter the item code.");
    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    code = in.nextInt();
    //itemCheck(ArrayList<Integer> items, int code) -- Returns -1 if the user has already purchased the item
    
   
    int buy = obj1.itemCheck(items, code);
    if(buy==-1){
       System.out.println("You have already purchased this item. To buy more, please start a second transaction.");
       continue;
    }
    //checkCode() -- Returns -1 if invalid. Returns index if valid.
    index = obj1.checkCode(code, catItem, 0);
    if(index==-1){
      System.out.println("We do not sell a product with the code "+code+".");
    }
    if(index!=-1&&buy == 0){
    System.out.println("Please enter the desired quantity.");
    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    qty = in.nextInt();
    
    //checkInv() -- Returns stock if the quantity>stock. Returns -1 if quantity<=stock.
    if(obj1.checkInv(catItem, index, qty)!=-1){
     System.out.println("Unfortunately, we do carry enough items to meet your desired quantity.\n"
                          +"We only have "+obj1.checkInv(catItem, index, qty)+" item(s) in stock. Sorry.");
    }
    else{
     
     int stock = catItem.get(index).quantity;
     double price = catItem.get(index).price;
     //Asks user to purchase more items
    System.out.println("Enter 0 to delete purchase.\nEnter 1 to confirm purchase.");
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    int z = in.nextInt();
    while(z<0||z>1){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      z = in.nextInt();
    }
    if (z==1){
     System.out.println("Adding the desired item(s) to your shopping list.");
     //Adds data to Pick Display class
     obj3.customerInfo(name);
     obj3.shoppingList(code, qty, stock, price);
     //Updates the catalogue
     obj1.itemSold(catItem, index, qty, stock);
     //Sends item code to ArrayList
     items.add(code);
     
    }
    }
    }
    //Asks user to purchase more items
    System.out.println("Enter 0 to Exit.\nEnter 1 to purchase more items.");
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    y = in.nextInt();
    while(y<0||y>1){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      y = in.nextInt();
    }     
    }
    while(y==1);
    //Directs user to checkout when they are done shopping
    obj3.checkout();
    break;    
  default:
    System.out.println("You entered an invaild value - Goodbye.");
    break;

       }
    
  }
}

/****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This class manages the Inventory.
   * 
   ****************************************************************/

class InventoryManagement{
  Scanner in = new Scanner(System.in);
  
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method is the Inventory Management Menu 
   * @return: void              @Param: ArrayList<CatalogueData>
   ****************************************************************/
  public void startInv(ArrayList<CatalogueData> catItem){        
    int x; 
    //Menu for Inventory Management
    System.out.println("Welcome to the Inventory Management Menu-------Please select an option--------\n"
                      +"1 - Add Catalogue product\n"
                      +"2 - Remove Catalogue product\n"
                      +"3 - Modify Inventory\n"
                      +"4 - Display Catalogue");
    
    
   while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    x = in.nextInt();
    while(x<1||x>4){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      x = in.nextInt();
    }
    switch(x) {
  case 1:
    addCat(catItem); //user enters code, quantity, price, and description into one line of the file
    break;    
  case 2:
    removeCat(catItem); //removes a line from the file
    break;    
  case 3:
    modInv(catItem); //changes the quantity left in stock
    break;   
  case 4:
     displayCat(catItem); //displays the file, 5 items at a time.
     break;     
  default:
     System.out.println("You entered an invaild value - Goodbye.");
     break; 
    }
  }
  
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method adds an item to the catalogue.
   * @return: void                @Param: ArrayList<CatalogueData>
   ****************************************************************/  
  private void addCat(ArrayList<CatalogueData> catItem){
      Scanner in1 = new Scanner (System.in);
      String name, desc, prod, name1;
      double price;
      int qty, code;
      
    //Asks user for product name
    System.out.println("Please enter the Product name you wish to add to the Inventory.");
    name1 = in1.nextLine();
    
    //Asks user for product description
    System.out.println("Please enter the Product description you wish to add to the Inventory.");
    desc = in1.nextLine();
    
    //Combines
    name = name1 + " - " + desc;
    
    //Asks user for product price
    System.out.println("Please enter the price of the product you wish to add to the Inventory.");  
    while(!in1.hasNextDouble()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in1.next();
    }
    price = in1.nextDouble();
    //Asks user for product quantity
    System.out.println("Please enter the quantity of the product you wish to add to the Inventory.");  
    while(!in1.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in1.next();
    }
    qty = in1.nextInt();
    //Asks user for product code
    System.out.println("Please enter the 6-digit item code of the product you wish to add to the Inventory.");
    while(!in1.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in1.next();
    }                               
    code = in1.nextInt();
    while(code<100000||code>999999){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      code = in1.nextInt();
    }
    int i;
    //checks to see if code exists
    for(i=0; i<catItem.size();i++){
      if(catItem.get(i).proNum==code){
      System.out.println("The code entered is already registered. Goodbye.");
      break;
      }
    }
    //adds new item to text file
    try{
      File fileName = new File ("Catalogue.txt");
      Scanner diskScanner = new Scanner (fileName);
      PrintWriter diskWriter = new PrintWriter("CatalogueTmp.txt"); 
      File fileNameNew = new File ("CatalogueTmp.txt");     
      String oldFileContents=new String();
      
      //copies old file contents to temp file
      while (diskScanner.hasNextLine()) {
         oldFileContents= oldFileContents + diskScanner.nextLine()+"\n";
      }//end while
      diskWriter.print(oldFileContents);
      //adds new item to temp file
      diskWriter.print(code+" "+qty+" "+price+" "+name);
      
      //closes scanner and writer
      diskScanner.close();
      diskWriter.close();
      
      fileName.delete(); // deletes the file called Data.txt
     
     if (fileNameNew.renameTo(fileName)){ //renameTo returns a boolean value
     System.out.println("The Catalogue was updated successfully.");
     
     }else{
     System.out.println("The Catalogue was not updated.");
     }
    }
    catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
    
    }
    
    /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method removes an item from the catalogue.
   * @return: void                @Param: ArrayList<CatalogueData>
   ****************************************************************/  
  private void removeCat(ArrayList<CatalogueData> catItem){
    
      int code, index;
      System.out.println("Please enter the item code of the Product you wish to remove.");    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    code = in.nextInt();
    //checks to see if product exists
    index = checkCode(code, catItem, 1);
    if(index==-1){
      System.out.println("We do not sell a product with the code "+code+". Goodbye.");
      }
    else{          
    try{
      File fileName = new File ("Catalogue.txt");
      Scanner diskScanner = new Scanner (fileName);
      PrintWriter diskWriter = new PrintWriter("CatalogueTmp.txt"); 
      File fileNameNew = new File ("CatalogueTmp.txt");     
      String oldFileContents=new String();
         
      //copies old file contents to temp file
      while (diskScanner.hasNextLine()) {
        for(int i = 0; i<catItem.size(); i++){
          if (i == index){ //assigns line to skip to a blank String
            String toRemove =  diskScanner.nextLine();
            continue;
          }
        oldFileContents= oldFileContents + diskScanner.nextLine()+"\n";
           
        }
      }//end while
      diskWriter.print(oldFileContents);     
      //closes scanner and writer 
      diskScanner.close();
      diskWriter.close();
      
      fileName.delete(); // deletes the file called Data.txt
     
     if (fileNameNew.renameTo(fileName)){ //renameTo returns a boolean value
     System.out.println("The Catalogue was updated successfully.");
     
     }else{
     System.out.println("The Catalogue was not updated.");
     }
    }
    catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
      
    }}
    
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method modifies an item's quantity.
   * @return: void                @Param: ArrayList<CatalogueData>
   ****************************************************************/   
  private void modInv(ArrayList<CatalogueData> catItem){
      int code, index, qty;
      System.out.println("Please enter the item code of the Product quantity you wish to modify.");    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    code = in.nextInt();
    //checks to see if product exists
    index = checkCode(code, catItem, 1);
    if(index==-1){
      System.out.println("We do not sell a product with the code "+code+". Goodbye.");
      }
    else{
      //asks user for new quantity
      System.out.println("Please enter the desired quantity.");
      while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    qty = in.nextInt();
    
    try{
      File fileName = new File ("Catalogue.txt");
      Scanner diskScanner = new Scanner (fileName);
      PrintWriter diskWriter = new PrintWriter("CatalogueTmp.txt"); 
      File fileNameNew = new File ("CatalogueTmp.txt");     
      String oldFileContents=new String();
      
      
      //copies old file contents to temp file
      while (diskScanner.hasNextLine()) {        
        for(int i = 0; i<catItem.size(); i++){
          if (i == index){ //assigns line to skip to a blank String
            String toRemove =  diskScanner.nextLine();
            continue;
          }
        oldFileContents= oldFileContents + diskScanner.nextLine()+"\n";
           
        }
      }//end while
      diskWriter.print(oldFileContents);
      diskWriter.print(catItem.get(index).proNum+" "+qty+" "+catItem.get(index).price+catItem.get(index).name);
   
      //closes scanner and writer 
      diskScanner.close();
      diskWriter.close();
      
      fileName.delete(); // deletes the file called Data.txt
     
     if (fileNameNew.renameTo(fileName)){ //renameTo returns a boolean value
     System.out.println("The Catalogue was updated successfully.");
     
     }else{
     System.out.println("The Catalogue was not updated.");
     }
    }
    catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
      
    }
    }
    
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method checks if a product exists. It returns the index of
   * the item if it does exist.
   * @return: int                 @Param: ArrayList<CatalogueData>, int
   ****************************************************************/   
  public int checkCode(int code, ArrayList<CatalogueData> catItem, int x){
      int index = -1;      
      //returns -1 if product doesn't exist. Returns index if exists.
      for (int i=0; i <catItem.size(); i++){
        if (code == catItem.get(i).proNum){
          index = i;
          break;
        }
      }
        
    return index;
    }
    
    /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method displays the catalogue.
   * @return: void                @Param: ArrayList<CatalogueData>
   ****************************************************************/   
  private void displayCat(ArrayList<CatalogueData> catItem){      
      int x, i;
      exit:  //labelled break
        for (i=0; i < catItem.size(); i+=2){
        //outprints 2 lines at a time
         System.out.print("Product Code: "+catItem.get(i).proNum+"\n");
         System.out.print("Quantity in Stock: "+catItem.get(i).quantity+"\n");
         System.out.print("Price: "+catItem.get(i).price+"\n");
         System.out.print("Product Name: "+catItem.get(i).name+"\n");
         
         System.out.println("----------------------------------------------------------------");
         
         System.out.print("Product Code: "+catItem.get(i+1).proNum+"\n");
         System.out.print("Quantity in Stock: "+catItem.get(i+1).quantity+"\n");
         System.out.print("Price: "+catItem.get(i+1).price+"\n");
         System.out.println("Product Name: "+catItem.get(i+1).name);
         
         
         System.out.println("----------------------------------------------------------------");
                   
        
        System.out.println("Enter 0 to Exit\nEnter 1 to display more items");
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    x = in.nextInt();
    while(x<0||x>1){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      x = in.nextInt();
    } 
    
    switch(x) {
     case 0:
     System.out.println("Goodbye.");
     break exit;
    }
    if(x>1||x<0){//ERROR CHECKING
     System.out.println("You entered an invaild value - Try again.");    
    }
  
    
    }
     //outprints when the catalogue is empty
     if (i>=catItem.size()){
     System.out.println("There are no more items in the Catalogue. Goodbye."); 
    }
    }
    
   /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method checks the stock of an item.
   * @return: int             @Param: ArrayList<CatalogueData>, int, int
   ****************************************************************/    
  public int checkInv(ArrayList<CatalogueData> catItem, int index, int qty){
     //returns stock if qty>stock. else, returns -1
    int stock = catItem.get(index).quantity;
    if (stock >=qty){
      return -1;
    }
    else return stock;
    }
    
    /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method updates the txt file after an item is sold.
   * @return: void           @Param: ArrayList<CatalogueData>, int, int, int
   ****************************************************************/  
  public void itemSold(ArrayList<CatalogueData> catItem, int index, int qty1, int stock){
    try{
      File fileName = new File ("Catalogue.txt");
      Scanner diskScanner = new Scanner (fileName);
      PrintWriter diskWriter = new PrintWriter("CatalogueTmp.txt"); 
      File fileNameNew = new File ("CatalogueTmp.txt");     
      String oldFileContents=new String();
      int qty = stock - qty1;
      
      //copies old file contents to temp file
      while (diskScanner.hasNextLine()) {        
        for(int i = 0; i<catItem.size(); i++){
          if (i == index){ //assigns line to skip to a blank String
            String toRemove =  diskScanner.nextLine();
            continue;
          }
        oldFileContents= oldFileContents + diskScanner.nextLine()+"\n";
           
        }
      }//end while
      diskWriter.print(oldFileContents);
      diskWriter.print(catItem.get(index).proNum+" "+qty+" "+catItem.get(index).price+catItem.get(index).name);
   
      //closes scanner and writer 
      diskScanner.close();
      diskWriter.close();
      
      fileName.delete(); // deletes the file called Data.txt
     
     if (fileNameNew.renameTo(fileName)){ //renameTo returns a boolean value
     System.out.println("The Catalogue was updated successfully.");
     
     }else{
     System.out.println("The Catalogue was not updated.");
     }
    }
    catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
    
    
    }
  
   /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method checks if the user has already purchased the item.
   * @return: int             @Param: ArrayList<Integer>, int
   ****************************************************************/  
  public int itemCheck(ArrayList<Integer> items, int code){
    int check = 0;
    for (int i=0; i<items.size(); i++){
      if (code == items.get(i)){
      check = -1;      
      }
    }
    return check;
    }
}

/****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This class manages the Customer database
   ****************************************************************/  
class CustomerManagement{
  Scanner in = new Scanner(System.in);
  
 /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method is the Customer management menu.
   * @return: void                @Param: ArrayList<CustomerData>
   ****************************************************************/  
  public void startCust(ArrayList<CustomerData> custList){
    int x;    
    //customer management menu
    System.out.println("Welcome to the Customer Management Menu-------Please select an option---------\n"
                         +"1 - Add a Customer\n"
                         +"2 - Remove a Customer\n"
                         +"3 - Display Customer List");
    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    x = in.nextInt();
    while(x<1||x>3){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      x = in.nextInt();
    }
    
    switch(x) {
  case 1:
    addCust(custList, 1); //method to add a customer
    break;    
  case 2:
    removeCust(custList); //method to remove a customer
    break;    
  case 3:
    displayCust(custList); //method to display customer list
    break;     
  default:
     System.out.println("You entered an invaild value - Goodbye.");
     break; 
    }
  }
  
   /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method adds a customer to the database.
   * @return: String           @Param: ArrayList<CustomerData>, int
   ****************************************************************/  
  public String addCust(ArrayList<CustomerData> custList, int x){
      Scanner in1 = new Scanner(System.in);
      String fName, lName, name;
      String phone;
      int id;
    //asks user to enter  the customer's first name
    System.out.println("Please enter the First name of the Customer you wish to add to the Database.");
    fName = in1.nextLine();
    //asks user to enter  the customer's last name
    System.out.println("Please enter the Last name of the Customer you wish to add to the Database.");
    lName = in1.nextLine();
    
    //combines both names
    name = fName+" "+lName;
    
    //asks user to enter  the customer's phone number
    System.out.println("Please enter the phone number of the Customer you wish to add to the Database.");  
    phone = in1.next();
    if(phone.length()!=10){ //ERROR CHECK
      System.out.println("The phone number entered is INVALID. Please try again.");
      addCust(custList, x);
    }                           
    
    //generates a customer ID
    int max;
    max = 0;
    int i;
    for(i=0; i<custList.size();i++)
    {if(custList.get(i).id>max)
      {max = custList.get(i).id;}}
    max = max;
    id=max+1;
    System.out.printf("The Customer ID for %s %s is %d.\n", fName,lName,id);
    
    //adds new customer to database
    try{
      File fileName = new File ("Customers.txt");
      Scanner diskScanner = new Scanner (fileName);
      PrintWriter diskWriter = new PrintWriter("CustomersTmp.txt"); 
      File fileNameNew = new File ("CustomersTmp.txt");     
      String oldFileContents=new String();
    //copies old file contents
      while (diskScanner.hasNextLine()) {
         oldFileContents= oldFileContents + diskScanner.nextLine()+"\n";
      }//end while
      diskWriter.print(oldFileContents);
      diskWriter.print(id+" "+phone+" "+fName+" "+lName);
    //closes scanner and diskwriter
      diskScanner.close();
      diskWriter.close();
      
      fileName.delete(); // deletes the file called Data.txt
     
     if (fileNameNew.renameTo(fileName)){ //renameTo returns a boolean value
     System.out.println("The Customer Database was updated successfully.");
     
     }else{
     System.out.println("The Customer Database was not updated.");
     }
    }
    catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
    if (x==0)
     return name;
    else
      return "no";
    }
    
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method removes a customer from the database.
   * @return: void          @Param: ArrayList<CustomerData>
   ****************************************************************/    
  private void removeCust(ArrayList<CustomerData> custList){
    //method to remove customer
      int id, index;
      System.out.println("Please enter the Customer ID of the Customer you wish to remove.");    
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    id = in.nextInt();
    //checks to see if id exists
    index = checkIndex(id, custList);
    if(index==-1){
      System.out.println("There is no Customer with the ID "+id+" in our Database. Goodbye.");
      }
    else{
    //updates database      
    try{
      File fileName = new File ("Customers.txt");
      Scanner diskScanner = new Scanner (fileName);
      PrintWriter diskWriter = new PrintWriter("CustomersTmp.txt"); 
      File fileNameNew = new File ("CustomersTmp.txt");     
      String oldFileContents=new String();
    //copies data from org array to temp array
     while (diskScanner.hasNextLine()) {
        for(int i = 0; i<custList.size(); i++){
          if (i == index){ //assigns line to skip to a blank String
            String toRemove =  diskScanner.nextLine();
            continue;
          }
        oldFileContents= oldFileContents + diskScanner.nextLine()+"\n";
           
        }
      }//end while
      diskWriter.print(oldFileContents);     
      //closes scanner and writer
      diskScanner.close();
      diskWriter.close();
      
      fileName.delete(); // deletes the file called Data.txt
     
     if (fileNameNew.renameTo(fileName)){ //renameTo returns a boolean value
     System.out.println("The Database was updated successfully.");
     
     }else{
     System.out.println("The Database was not updated.");
     }
    }
    catch (FileNotFoundException e){ //ERROR CHECK
         System.err.print(e);
      }
      
    }}
    
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method displays the customer database.
   * @return: void          @Param: ArrayList<CustomerData>
   ****************************************************************/    
  private void displayCust(ArrayList<CustomerData> custList){      
      int x, i;
      exit:  //labelled break
        //displays customer list, 2 at a time
        for (i=0; i < custList.size(); i+=2){
         System.out.print("Customer Name:"+custList.get(i).name+"\n");
         System.out.print("Customer ID: "+custList.get(i).id+"\n");
         System.out.print("Phone Number: "+custList.get(i).phoneNum+"\n");         
                  
         System.out.println("----------------------------------------------------------------");
         
         System.out.print("Customer Name:"+custList.get(i+1).name+"\n");
         System.out.print("Customer ID: "+custList.get(i+1).id+"\n");
         System.out.print("Phone Number: "+custList.get(i+1).phoneNum+"\n");         
                  
         System.out.println("----------------------------------------------------------------");          
        
         System.out.println("Enter 0 to Exit\nEnter 1 to display more customers");
    while(!in.hasNextInt()){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      in.next();
    }
    
    x = in.nextInt();
    while(x<0||x>1){//ERROR CHECKING
      System.out.println("You entered an invaild value - Try again.");
      x = in.nextInt();
    } 
    
    switch(x) {
     case 0:
     System.out.println("Goodbye.");
     break exit;
    }
    if(x>1||x<0){//ERROR CHECK
     System.out.println("You entered an invaild value - Try again.");    
    }
      
    }
     //tells the user there are no more items
     if (i>=custList.size()){
     System.out.println("There are no more customers in the Database. Goodbye."); 
    }
    }
    
    /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method checks to see if the id exists. Returns name if exists.
   * Returns "no" if does not exist.
   * @return: String           @Param: int, ArrayList<CustomerData>
   ****************************************************************/  
  public String checkCust(int id, ArrayList<CustomerData> custList){
      //checks to see if the id exists. returns name if exists. returns "no" if does not exist. 
      String check = "no";      
      for (int i=0; i < custList.size(); i++){
        if (id == custList.get(i).id){
          check = custList.get(i).name;
          break;
        }
      }
          
    return check;}
    

    
  /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method checks to see what index the id is at.
   * returns -1 if it does not exist. returns index if exists
   * @return: int          @Param: int, ArrayList<CustomerData>
   ****************************************************************/  
  public int checkIndex(int id, ArrayList<CustomerData> custList){
     //checks to see what index the id is at. returns -1 if it does not exist. returns index if exists.
      int index = -1;      
      for (int i=0; i <custList.size(); i++){
        if (id == custList.get(i).id){
          index = i;
          break;
        }
      }
          
    return index;
    }
  }

 /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * Start of Pick Display.
   ****************************************************************/  
class PickDisplay{
  String productList = "";
  String name;
  double cost;
   /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method adds an item to the pick display. It prints the list
   * for the picker
   * @return: void          @Param: int, int, int, double
   ****************************************************************/  
  public void shoppingList(int code, int qty, int stock, double price){
    productList = productList+"Product Code: \t" + code + "\nQuantity Purchased: \t" + qty + "\nStock Remaining: \t" + (stock-qty) + "\n----------------------------------\n";
    System.out.println(productList);
    cost = cost + (price*qty);
    System.out.printf("Total Cost: $%.2f\n",cost);
  }
   /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method stores the customer's name.
   * @return: void          @Param: String
   ****************************************************************/  
  public void customerInfo(String x){
    name = x;
    System.out.println("Customer Name:" +name);
  }
   /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This method is the checkout.
   * @return: void         @Param: n/a
   ****************************************************************/  
  public void checkout(){
    System.out.print("Thank you for shopping. Here is your final recipt:\n"+"Customer Name:" +name+"\n"+productList+"Total Cost: $");
    System.out.printf("%.2f\nGoodbye.",cost);
  }
}

 /****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This class is used to populate the Catalogue Data.
   ****************************************************************/  
class CatalogueData {
//class variables
   int proNum;
   int quantity;
   double price;
   String blank;
   String name;
//constructor
   CatalogueData (int pNum, int qty, double cost, String nm){
      proNum = pNum;
      quantity = qty;
      price = cost;
      name = nm;
   }

}

/****************************************************************
   * @Author: Madeline Pinto      @Date: Nov 9 2020
   * This class is used to populate the Customer Data.
   ****************************************************************/  
class CustomerData {
//class variables
   int id;
   String phoneNum;
   String blank;
   String name;
//constructor
   CustomerData (int custId, String custPhone, String custNm){
      id = custId;
      phoneNum = custPhone;           
      name = custNm;
   }

}