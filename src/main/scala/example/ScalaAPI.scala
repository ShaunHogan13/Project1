
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

import scala.io.Source

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




def queryAPI(input : String){
   //7:49 PM - got some data!
var jsonString = ""
  val filename = "C:/Users/shaun/ScalaDemo/Thursday/thursdaytestproject/src/main/scala/example/QueryLocal.json";

for (line <- Source.fromFile(filename).getLines) {
    jsonString = jsonString + line
}
   
   println("TOTAL TWEETS: " + cleanJson2(jsonString) )
   
}


// 22300011

  
def cleanJson2(jString: String): Int = {
        implicit val formats = net.liftweb.json.DefaultFormats
        val jValue = parse(jString)
        var resultDoc = jValue.extract[Test]
        resultDoc.data
        resultDoc.meta
        var r2 = resultDoc.meta.extract[Test1]

        r2.totalTweetCount
    }
}


case class Test(data: net.liftweb.json.JArray, meta: net.liftweb.json.JValue)
case class Test1(totalTweetCount: Int)