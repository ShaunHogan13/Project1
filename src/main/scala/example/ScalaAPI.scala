
//package twitter4j
//package example

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import net.liftweb.json._
//import org.github.fedeoasi.newsapi._
import util.control.Breaks._

import scala.io.Source
import scala.util.parsing.json.JSONObject
import java.util.Date

class ScalaAPI {



    def getRestContent(url: String): String = {
      val httpClient = new DefaultHttpClient()
      val httpResponse = httpClient.execute(new HttpGet(url))
      val entity = httpResponse.getEntity()
      var content = ""
      if (entity != null) {
        val inputStream = entity.getContent()
        content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
        inputStream.close
      }
      httpClient.getConnectionManager().shutdown()
      return content
    }




def queryAPI(input : String, startDate : Date, endDate : Date) : Int = {
   //7:49 PM - got some data!
  //12.39 AM - holy cow im tired

  val filename = "C:/Users/shaun/ScalaDemo/Thursday/thursdaytestproject/src/main/scala/example/QueryLocal.json"

var jsonString = Source.fromFile(filename).mkString 
   val count =  getTotalTweets(jsonString, input, startDate, endDate) 
    
   return count
   
}


// 22300011

  
def getTotalTweets(jString: String, key: String, startDate: Date, endDate: Date): Int = {

 implicit val formats = DefaultFormats
val actualKey = key.toLowerCase 


    val json = parse(jString)
    val elements = (json \\ "date").children
        val tags = (json \\ "Tags").children
    var counter = 0
var index = 0
    for (acct <- elements) {
      
      breakable{
          var m = acct.toString().replace("JField(date,JString(", "")
          m = m.replace("))", "")
          val tweetYear = m.substring(0, 4).toInt
          val tweetMonth = m.substring(5, 7).toInt
          val tweetDay = m.substring(8, 10).toInt

          val tweetDate = new Date(tweetYear, tweetMonth, tweetDay)
          if(tweetDate.before(startDate)) break()
          if(tweetDate.after(endDate)) break()
          if(tags(index).toString.toLowerCase.contains(actualKey) == false) break()
         counter = counter + 1

        
      
      }
      index = index + 1
  }
        return counter

}
}
case class Tweet(
    date: String,
    tags: List[String]
)

case class Test(data: net.liftweb.json.JArray, meta: net.liftweb.json.JValue)
case class Test1(totalTweetCount: Int)