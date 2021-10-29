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


case class Person(
    var fname: String,
    var lname: String,
    var username: String,
    var password: String,
    var balance: Float
)

case class Prompt(
    promptTitle: String,
    promptText: String,
    options: List[String]
)


////This is a repository test 11:42AM 10/28/2021
object AccountsManager {

  
  var allAccounts = ListBuffer(
    Person("Shaun", "Hogan", "user", "pw", 5f),
    Person("Jake", "Rake", "JakeTheRake", "FallColors", 5f),
    Person("Jane", "Rain", "JaneTheRain", "SpringShowers", 100f),
    Person("Same", "Pain", "SameThePain", "WinterBoots", 1000f),
    Person("Jahuluba", "Aah", "JahulubaTheAah", "SummerHeat", 780f)
  )

  // mongod --dbpath="C:\data\db"
  //mongo

  var running = true;
  var user = Person("", "", "", "", 0);
  var currentUserIndex = -1;
//("","","","",0);

  val allPrompts = Seq(
    Prompt(
      "Begin",
      "Welcome to TDROTC National Bank!",
      List[String](
        "Login",
        "Create",
        "Quit",
        "Print Accounts",
        "Replace With Fake Data",
        "Load Accounts from DB",
        "Save to DB",
        "Get Average Balance"
      )
    ),
    
    Prompt("Login", "Login", null),
    Prompt("Success", "", List[String]("Menu")),
    Prompt("Fail", "Invalid Login", List[String]("Begin")),
    Prompt("Log Out", "Logging Out.", List[String]("Begin")),
    Prompt("Print Accounts", "Printing all Accounts", List[String]("Begin")),
    // Prompt("Save Accounts", "Saving all accounts to database", List[String]("Begin")),
    Prompt("Replace With Fake Data", "", List[String]("Begin")),
    Prompt("Quit", "Thanks for banking with TDROTC National Bank!", null),
    Prompt(
      "Menu",
      "Please choose from the following options:",
      List[String]("Deposit", "Withdraw", "View Balance", "Log Out", "Quit")
    ),
    Prompt(
      "Deposit",
      "Please choose the amount to deposit:\n",
      List[String](
        "Deposit Custom Amount",
        "Deposit $10",
        "Deposit $20",
        "Deposit $50",
        "Deposit $100",
        "Deposit $500",
        "Menu",
        "Quit"
      )
    ),
    Prompt(
      "Withdraw",
      "Please choose the amount to withdraw:\n",
      List[String](
        "Withdraw Custom Amount",
        "Withdraw $10",
        "Withdraw $20",
        "Withdraw $50",
        "Withdraw $100",
        "Withdraw $500",
        "Menu",
        "Quit"
      )
    ),
    Prompt("Deposit Custom Amount", "", List[String]("View Balance")),
    Prompt("Withdraw Custom Amount", "", List[String]("View Balance")),
    Prompt("D_Success", "Deposit Succesful.", null),
    Prompt("W_Success", "Withdrawal Succesful.", null),
    Prompt("D_Fail", "Deposit Not Succesful.", null),
    Prompt("W_Fail", "Withdrawal Not Succesful.", null),
    Prompt("View Balance", "", List[String]("Menu")),
    Prompt("Create", "Create Account", null),
    Prompt("Deposit $10", "", List[String]("View Balance")),
    Prompt("Deposit $20", "", List[String]("View Balance")),
    Prompt("Deposit $50", "", List[String]("View Balance")),
    Prompt("Deposit $100", "", List[String]("View Balance")),
    Prompt("Deposit $500", "", List[String]("View Balance")),
    Prompt("Withdraw $10", "", List[String]("View Balance")),
    Prompt("Withdraw $20", "", List[String]("View Balance")),
    Prompt("Withdraw $50", "", List[String]("View Balance")),
    Prompt("Withdraw $100", "", List[String]("View Balance")),
    Prompt("Withdraw $500", "", List[String]("View Balance")),
    Prompt("Load Accounts from DB", "", List[String]("Begin")),
    Prompt("Save to DB", "SaveAccounts", List[String]("Begin")),

    Prompt("Get Average Balance", "", List[String]("Begin"))

    
  )

  def GetPrompt(promptTitle: String): Prompt = {

    for (a <- 0 to allPrompts.length - 1) {
      if (allPrompts(a).promptTitle == promptTitle) {

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
        user.balance = allAccounts(a).balance;
        user.username = allAccounts(a).username;
        user.password = allAccounts(a).password;
        currentUserIndex = a;

        println("Successful Login!");
        SaveAccount();
        return true;
      }
    }
    println("Failed Login!");
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

        println("Account already exists!");
        return false;
      }
    }

    val newPerson = Person(firstname, lastname, username, password, 0);

    allAccounts += newPerson;
    println("Account created.");
    SaveAccounts();
    return true;
  }


  def PrintAccounts() = {
    for (a <- 0 to allAccounts.length - 1) {

      println(allAccounts(a));

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

      val newPerson = Person(s(0), s(1), username, password, balance);

      allAccounts += newPerson;
//print("Adding to accounts");
    }
//SaveAccounts();
  }

  def SaveAccounts() = {
    

    for (i <- 0 to allAccounts.length - 1) {
      val person = allAccounts(i);
     
    

    }
  }
  def SaveAccount() = {

    
    val person = allAccounts(currentUserIndex);
    


  }



  def GetNextInt(max: Int): Int = {

    var returnVal = -1;

    while (returnVal < 0 || returnVal > max) {
      try {
        returnVal = readLine().toInt;

      } catch {
        case _: Throwable => println("Invalid Input")
      }

    }
    return returnVal;
  }

  def TakeAction(promptTitle: String): Prompt = {
if (promptTitle == "Get Average Balance") {
      var total = 0.0;
      var numberOfAccounts = allAccounts.length
    for (i <- 0 to allAccounts.length - 1) {
      total = total + allAccounts(i).balance
    }
    println("\n\nAverage balance for all accounts = " + (total/numberOfAccounts))
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
      println("Please enter your username:")
      val username = readLine();
      println("Please enter your password:")
      val password = readLine();
      if (TryLogin(username, password) == true) {

        return GetPrompt("Success");
      } else {
        println("Invalid username or password.");
        Thread.sleep(800)
        //user = null;
        // if(loggedIn) return GetPrompt(("Menu"));

        return GetPrompt("Fail");
      }
    }
    if (promptTitle == "Create") {
      println("Please enter your first name:")
      val firstname = readLine();
      println("Please enter your last name:")
      val lastname = readLine();
      println("Please enter your new username:")
      val username = readLine();
      println("Please enter your password:")
      val password = readLine();
      if (TryCreate(firstname, lastname, username, password) == true) {
    SaveAccounts();

        return GetPrompt("Success");
      } else {
        Thread.sleep(800)

        return GetPrompt("Fail");
      }
    }
    if (promptTitle == "View Balance") {
      println("...");
      Thread.sleep(2000);

    }
    if (promptTitle == "Quit") {
      running = false;
      return null;
    }

    if (promptTitle == "Deposit Custom Amount") {
      println("Please enter the amount:")
      val amount = GetNextInt(9999999);
      val money = amount.toInt;
      user.balance = user.balance + money;
           allAccounts(currentUserIndex).balance = user.balance;

          SaveAccounts();
return GetPrompt("Menu")
    }
    if (promptTitle == "Withdraw Custom Amount") {
      println("Please enter the amount:")
      val amount = GetNextInt(9999999);
      val money = amount.toInt;
      if (user.balance >= money) {
        user.balance = user.balance - money;
              allAccounts(currentUserIndex).balance = user.balance;

            SaveAccounts();

      } else {
        println("Can't withdraw - Not enough funds.");
        return GetPrompt("Withdraw");
      }
    }
    if (promptTitle.contains("Deposit $")) {
      return GetPrompt(("View Balance"));
    }
    if (promptTitle.contains("Withdraw $")) {
      return GetPrompt(("View Balance"));
    }
    return GetPrompt(promptTitle);
  }

  println("\n")

 
  var currentPrompt = GetPrompt("Begin");
  LoadAccounts()
  while (running) {
    println(currentPrompt.promptText);
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
          println(a + " " + currentPrompt.options(a - 1).toString());
        }
      }
    }

    var input = 1;

    if (optionsCount > 1) {
      input = GetNextInt(optionsCount);
    }
    var inputIndex = input.toInt - 1;
    if (optionsCount == 1) inputIndex = 0;
    if (optionsCount > 0) {
      val currentOption = currentPrompt.options(inputIndex);
      val selectedPrompt = GetPrompt(currentOption);
      // if(selectedPrompt != null){
      currentPrompt = selectedPrompt;
      //}
      if (currentPrompt.promptTitle == "View Balance") {
        println("Balance = " + user.balance);
      }

        currentPrompt = TakeAction(currentPrompt.promptTitle);
      }
    

    println("\n")

  }
  
}

