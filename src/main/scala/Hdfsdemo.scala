package example

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.PrintWriter;

class Hdfsdemo {

  val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/"
 

  def createFile(resultString : String, nameString : String): Unit = {
    val filename = path + nameString
   // println(s"Creating file $filename ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    
    // Check if file exists. If yes, delete it.
    println("Checking if it already exists...")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
      println("Yes it does exist. Deleting it...")
      fs.delete(filepath, false)
    }

    val output = fs.create(new Path(filename))
    
    val writer = new PrintWriter(output)
    writer.write(resultString)
    writer.close()
    
    println(s"Done creating file $filename ...")
  }
}