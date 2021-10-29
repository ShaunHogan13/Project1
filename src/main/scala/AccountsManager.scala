package example
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import java.{util => ju}
import scala.collection.mutable.ListBuffer

import scala.util.Random
import scala.io.Source


import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import java.io.FileWriter

import java.io.PrintWriter
import java.util.Calendar;


case class Account(
    var fname: String,
    var lname: String,
    var username: String,
    var password: String,
    var id: Int,
    var admin: Boolean
)

case class Prompt(
    promptTitle: String,
    promptText: String,
    options: List[PromptOption]
)

case class PromptOption(
    displayedText: String,
    methodName: String,
    argument: String

)


////This is a repository test 11:42AM 10/28/2021
object AccountsManager {

    def SetUp(){
        


        println("V S A - Vanquish Sports Analysis")
        TakeAction(GetPrompt("Begin"), -1)
        
    }
  
  var allAccounts = ListBuffer(
    Account("Shaun", "Hogan", "user", "pw", 0, true),
    Account("Stan", "Man", "user2", "pw", 1, false),
    Account("Brad", "Mad", "user3", "pw", 2, false),
    Account("Emma", "Dilemma", "user4", "pw", 3, false)

    
  )

  // mongod --dbpath="C:\data\db"
  //mongo

  var running = true;
  var user = Account("", "", "", "", 0, false);
  var currentUserIndex = -1;
//("","","","",0);

  val allPrompts = Seq(
    Prompt(
      "Begin",
      "Menu",
      List[PromptOption](
        PromptOption("Login", "Login", ""),
        PromptOption("Create an account", "Create", ""),
        PromptOption("Quit", "Quit", "")
      )
    ),
    
    Prompt("Login", "Login", null),
    Prompt("Success", "",  List[PromptOption](
        PromptOption("Menu", "Menu", ""))),
    Prompt("Fail", "Invalid Login", List[PromptOption](
        PromptOption("Begin","Begin", ""))),
    Prompt("Log Out", "Logging Out.", List[PromptOption](
        PromptOption("Begin","Begin", ""))),
    Prompt("Print Accounts", "Printing all Accounts", List[PromptOption](
        PromptOption("Begin","Begin", ""))),
    Prompt("Replace With Fake Data", "", List[PromptOption](
        PromptOption("Begin","Begin", ""))),
    Prompt("Quit", "Thanks for banking with TDROTC National Bank!", null),
    Prompt(
      "Menu",
      "Please choose from the following options:",
      List[PromptOption](
        PromptOption("Query", "StartQuery", ""),
        PromptOption("Log Out", "Log Out", ""),
        PromptOption("Quit", "Quit", ""),
        PromptOption("Manage Account", "ManageAccount", ""))
    ),
    Prompt(
      "StartQuery",
      "Please choose what sport you want to query:",
      List[PromptOption](
        PromptOption("MLB", "SelectSport", "MLB"),
        PromptOption("NFL", "SelectSport", "NFL"),
        PromptOption("NBA", "SelectSport", "NBA"),
        PromptOption("All Leagues", "SelectSport", "All"),

        PromptOption("Menu", "Menu", ""),
        PromptOption("Quit", "Quit", ""))),
    Prompt(
      "Get Duration",
      "Please choose the duration:",
          List[PromptOption](
        PromptOption("24 Hours", "SelectDuration", "24"),
        PromptOption("48 Hours", "SelectDuration",  "48"),
        PromptOption("One Week", "SelectDuration",  "" + (24 * 7)),
        PromptOption("Two Weeks", "SelectDuration",  "" + (24 * 14)),
        PromptOption("One Month", "SelectDuration",  "" + (24 * 30)),
        PromptOption("Two Months", "SelectDuration",  "" + (24 * 60)),
        PromptOption("Six Months", "SelectDuration",  "" + (24 * 120)),
        PromptOption("One Year", "SelectDuration",  "" + (24 * 365)),
        PromptOption("Two Years", "SelectDuration",  "" + (24 * 365 * 2)),
        PromptOption("Up To Today", "SelectDuration",  "" + (100000)),

        PromptOption("Menu", "Menu", ""),
        PromptOption("Quit", "Quit", ""))),
   Prompt(
      "Get Start Date",
      "Please choose the starting date:",
          List[PromptOption](
        PromptOption("24 Hours Ago", "SelectStartTime", "24"),
        PromptOption("48 Hours Ago", "SelectStartTime",  "48"),
        PromptOption("One Week Ago", "SelectStartTime",  "" + (24 * 7)),
        PromptOption("Two Weeks Ago", "SelectStartTime",  "" + (24 * 14)),
        PromptOption("One Month Ago", "SelectStartTime",  "" + (24 * 30)),
        PromptOption("Two Months Ago", "SelectStartTime",  "" + (24 * 60)),
        PromptOption("Six Months Ago", "SelectStartTime",  "" + (24 * 120)),
        PromptOption("One Year Ago", "SelectStartTime",  "" + (24 * 365)),
        PromptOption("Two Years Ago", "SelectStartTime",  "" + (24 * 365 * 2)),
        PromptOption("Five Years Ago", "SelectStartTime",  "" + (24 * 365 * 5)),
        PromptOption("Ten Years Ago", "SelectStartTime",  "" + (24 * 365 * 10)),

        PromptOption("Menu", "Menu", ""),
        PromptOption("Quit", "Quit", ""))),

    Prompt("Create", "Create Account", null),
  
    Prompt("Load Accounts from DB", "", null),
    Prompt("Save to DB", "SaveAccounts", null),

    Prompt("Get Average Balance", "", null)

    
  )

  def GetPrompt(prompt: PromptOption): Prompt = {

    for (a <- 0 to allPrompts.length - 1) {
      if (allPrompts(a).promptTitle == prompt.methodName) {

        return allPrompts(a);
      }
    }
     for (a <- 0 to allPrompts.length - 1) {
      if (allPrompts(a).promptTitle == prompt.displayedText) {

        return allPrompts(a);
      }
    }
    return null;
  }
 def GetPrompt(prompt: String): Prompt = {

    for (a <- 0 to allPrompts.length - 1) {
      if (allPrompts(a).promptTitle == prompt) {

        return allPrompts(a);
      }
    }

    return null;
  }
  

  def TryLogin(username: String, password: String): Boolean = {
    for (a <- 0 to allAccounts.length - 1) {

      if (
        allAccounts(a).username == username && allAccounts(a).password == password
      ) {
        //user = allAccounts(a);
        user.fname = allAccounts(a).fname;
        user.lname = allAccounts(a).lname;
        user.id = allAccounts(a).id;
        user.username = allAccounts(a).username;
        user.password = allAccounts(a).password;
        currentUserIndex = a;

        printWithTab("Successful Login!");
        SaveAccount();
        return true;
      }
    }
    printWithTab("Failed Login!");
    return false;
  }
  def TryCreate(
      firstname: String,
      lastname: String,
      username: String,
      password: String
  ): Boolean = {
    if (username.length() < 2) return false;
    if (password.length() < 2) return false;

    for (a <- 0 to allAccounts.length - 1) {
      if (allAccounts(a).username == username) {

        printWithTab("Account already exists!");
        return false;
      }
    }

    val newAccount = Account(firstname, lastname, username, password, 0, false);

    allAccounts += newAccount;
    printWithTab("Account created.");
    SaveAccounts();
    return true;
  }


  def PrintAccounts() = {
    for (a <- 0 to allAccounts.length - 1) {

      printWithTab(allAccounts(a).toString());

    }

  }
  def InsertFakeData() = {
    LoadAccountsLocally();
   SaveAccounts()

   
  }

  def LoadAccounts() = {

   allAccounts.clear();
    
  }


  def LoadAccountsLocally() = {
    allAccounts.clear();
    val filename =
      "C:/Users/shaun/ScalaDemo/Thursday/thursdaytestproject/src/main/scala/example/Accounts.json";

    for (line <- Source.fromFile(filename).getLines) {
      val s: Array[String] = line.split(",");
      s(0) = s(0).split(":")(1);
      s(1) = s(1).split(":")(1);
      // s(2) = s(2).split(":")(1);
      val preUsername = s(2).replace("\"", "");
      val username = preUsername.split(":")(1);
      //s(3) = s(3).split(":")(1);
      val password = s(3).split(":")(1).replace("\"", "");

      val preBalance = s(4).split(":")(1).replace("}", "");
      val balance = preBalance.replace("]", "").toInt;

      val newAccount = Account(s(0), s(1), username, password, balance, false);

      allAccounts += newAccount;
//print("Adding to accounts");
    }
//SaveAccounts();
  }

  def SaveAccounts() = {
    

    for (i <- 0 to allAccounts.length - 1) {
      val Account = allAccounts(i);
     
    

    }
  }
  def SaveAccount() = {

    
    val Account = allAccounts(currentUserIndex);
    


  }



  def GetNextInt(max: Int): Int = {

    var returnVal = -1;

    while (returnVal < 0 || returnVal > max) {
      try {
        returnVal = readLine().toInt;

      } catch {
        case _: Throwable => printWithTab("Invalid Input")
      }

    }
    return returnVal;
  }

  def TakeAction(prompt: Prompt, optionIndex: Int): Prompt = {
            

     val promptTitle = prompt.promptTitle
if (promptTitle == "Get Average Balance") {
      var total = 0.0;
      var numberOfAccounts = allAccounts.length
    for (i <- 0 to allAccounts.length - 1) {
     // total = total + allAccounts(i).balance
    }
    printWithTab("\n\nAverage balance for all accounts = " + (total/numberOfAccounts))
    }

   if(promptTitle == "SelectSport"){
       SelectSport(prompt.options(optionIndex))
      // return GetPrompt(promptTitle)
   }
  
   if(promptTitle == "SelectStartTime"){
       
       SelectStartTime(prompt.options(optionIndex))
       //return GetPrompt(promptTitle)
   }
   if(promptTitle == "StartQuery"){
       
       //return GetPrompt("StartQuery")
   }
 if (promptTitle == "Save to DB") {
      SaveAccounts();

    }
    if (promptTitle == "Save to DB") {
      SaveAccounts();

    }
    if (promptTitle == "Save Accounts") {
      SaveAccounts();

    }
     if (promptTitle == "Load Accounts from DB") {
      LoadAccounts();

    }
    if (promptTitle == "Replace With Fake Data") {
      InsertFakeData();

    }
    if (promptTitle == "Print Accounts") {
      PrintAccounts();

    }
    if (promptTitle == "Login") {
      printWithTab("Please enter your username:")
      val username = readLine();
      printWithTab("Please enter your password:")
      val password = readLine();
      if (TryLogin(username, password) == true) {

        return GetPrompt("Success");
      } else {
        printWithTab("Invalid username or password.");
       

        return GetPrompt("Fail");
      }
    }
    if (promptTitle == "Create") {
      printWithTab("Please enter your first name:")
      val firstname = readLine();
      printWithTab("Please enter your last name:")
      val lastname = readLine();
      printWithTab("Please enter your new username:")
      val username = readLine();
      printWithTab("Please enter your password:")
      val password = readLine();
      if (TryCreate(firstname, lastname, username, password) == true) {
    SaveAccounts();

        return GetPrompt("Success");
      } else {

        return GetPrompt("Fail");
      }
    }
    if (promptTitle == "View Balance") {
      printWithTab("...");
      Thread.sleep(300);

    }
    if (promptTitle == "Quit") {
      running = false;
      return null;
    }

    return GetPrompt(promptTitle);
  }


 
  var currentPrompt = GetPrompt("Begin");

  while (running) {
     printWithTab("\n")
    printWithTab(currentPrompt.promptText);
  //  printWithTab("\n")
   //  printWithTab("\n")
    if (currentPrompt.promptTitle == "Success") {
      currentPrompt = GetPrompt("Menu")
    }
    if (currentPrompt.promptTitle == "Fail") {
      currentPrompt = GetPrompt("Begin")
    }
    var optionsCount = 0;

    if (currentPrompt.options != null) {

      optionsCount = currentPrompt.options.length;

      if (currentPrompt.options.length > 1) {
        for (a <- 1 to currentPrompt.options.length) {
            printWithTab(a + " " + currentPrompt.options(a - 1).displayedText.toString());
        }
      }
    }
    printWithTab("\n")

    var input = 1;

    if (optionsCount > 1) {
      input = GetNextInt(optionsCount);
    }
    var inputIndex = input.toInt - 1;
    if (optionsCount == 1) inputIndex = 0;
    if (optionsCount > 0) {
      val currentOption = currentPrompt.options(inputIndex);
      print("current option = " + currentOption.displayedText)
      val selectedPrompt = GetPrompt(currentOption);
     //  if(selectedPrompt != null){
      currentPrompt = selectedPrompt;
     // }
      //if(currentPrompt == null) println("ERRRRRROR " + currentPrompt.promptText + "," + currentPrompt.promptTitle)

        currentPrompt = TakeAction(currentPrompt, inputIndex);
      }
    
  //  printWithTab("Exiting the program.")


  }

  var querySport = "none"
  var queryStartTime = -1
  var queryDuration = 0

  def SelectSport(option : PromptOption){

    querySport = option.argument
    println("Sport = " + querySport)
  }
   def SelectStartTime(option : PromptOption){
queryStartTime = option.argument.toInt

  }
   def SelectDuration(option : PromptOption){
queryDuration = option.argument.toInt

  }

  def StartQuery(){
      val calendar = Calendar.getInstance()
      var dayOfYear = calendar.get(Calendar.DAY_OF_YEAR) 
      var date = calendar.get(Calendar.DATE) 

      println("Querying : \nLeague = " + querySport + "\nStartDate = " + date + "\n")
  }

    def printWithTab(output : String){
        if(output == "") return
        if(output.endsWith("\n")){
            var baseContent = ""
             for (a <- 0 to 81) {
            baseContent = baseContent + "-"
        }
            println(baseContent)
            return
        }
        val length = output.length()
        var addition = ""
        for (a <- 0 to (50 - length)) {
            addition = addition + "-"
        }
      print("--------------  " + output + "   ------------" + addition + "\n")
  }
   
}

