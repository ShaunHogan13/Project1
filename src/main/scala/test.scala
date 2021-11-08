//package example
//package twitter4j


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.PrintWriter;
//import example.AccountsManager

object HdfsDemo {
  
  val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/"
 
  def main(args: Array[String]) {

    

    val accounts = AccountsManager
    accounts.SetUp()
  }

  
}