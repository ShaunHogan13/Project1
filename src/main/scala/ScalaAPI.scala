
//package twitter4j
package example

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import net.liftweb.json._

//import twitter4j.auth.AccessToken

class ScalaAPI {
//     def QueryAPI(input : String){

//     val url = "https://api.twitter.com/2/tweets/search/recent?query=" + input   
//     val twitter = new TwitterFactory().getInstance()
//   // Authorising with your Twitter Application credentials
//     twitter.setOAuthConsumer("consumer_key",
//     "consumer_secret")
//       twitter.setOAuthAccessToken(new AccessToken(
//     "access_key",
//     "access_token"))
//    // val result = getRestContent(url)
//    var query = new twitter4j.Query()
//    val result = twitter.search(query)
//  println("Result = \n\n" + result.getCount + "\n\nDONE\n\n")
  //}


def getRestContent(url: String): String = {

    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(url))
    httpResponse.addHeader("Authorization: ", "Bearer AAAAAAAAAAAAAAAAAAAAAGtFVAEAAAAAEUPuvTHgRW6KAWX6xX7bduxn3Nc%3DG2Ll5XpWVG3BPtxb0m0An6gBbBu8SVCDKrhcnwkZWrMZnquFyZ")
    val entity = httpResponse.getEntity()
    var content = ""
    if(entity != null) {
    val inputStream = entity.getContent()
    content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
    inputStream.close

    }
    httpClient.getConnectionManager().shutdown()
    return content
}
//     2EYZSoNhWucyoR6JAgw9hGkzZ

// API Key Secret
// uCrWc4gtx3M6OqX9Gjd0OuRqL8mMJfyZQDwC68K8bcYaKe3GpQ

// Bearer Token
// AAAAAAAAAAAAAAAAAAAAAGtFVAEAAAAAEUPuvTHgRW6KAWX6xX7bduxn3Nc%3DG2Ll5XpWVG3BPtxb0m0An6gBbBu8SVCDKrhcnwkZWrMZnquFyZ

// APPID
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