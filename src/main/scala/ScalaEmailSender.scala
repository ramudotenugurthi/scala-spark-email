import org.apache.spark.sql.SparkSession
import org.apache.commons.mail.{DefaultAuthenticator, Email, EmailException, SimpleEmail}

object ScalaEmailSender {

  def main(args: Array[String]): Unit = {
    // Initialize Spark Session
    val spark = SparkSession.builder()
      .appName("ScalaMail")
      .master("local[*]")  // or your Spark cluster URL
      .getOrCreate()

    // Example: Process some data (optional)
    val data = Seq(("John", 1000), ("Doe", 2000))
    val df = spark.createDataFrame(data).toDF("Name", "Amount")
    df.show()

    // Prepare email parameters
    val recipient = "ramuenugurthi@gmail.com"
    val subject = "Test Email from Scala"
    val body = "This is a test email sent from a Scala application using Spark and Apache Commons Email."

    // Send email
    sendEmail(recipient, subject, body)

    // Stop Spark Session
    spark.stop()
  }

  def sendEmail(recipient: String, subject: String, body: String): Unit = {
    try {
      // Create the email message

      val fromEmail="from@email.com"
      val smtpUserName="smtp.username"
      val smtpPassowrd="smtp.password"
      val smtpHostName="smtp.host"
      val smtpPort=25

      val email: Email = new SimpleEmail()
      email.setHostName(smtpHostName)
      email.setSmtpPort(smtpPort)
      email.setAuthentication(smtpUserName,smtpPassowrd)
      email.setSSLOnConnect(false)
      email.setFrom(fromEmail)
      email.setSubject(subject)
      email.setMsg(body)
      email.addTo(recipient)

      // Send the email
      email.send()
      println("Email sent successfully.")
    } catch {
      case e: EmailException =>
        e.printStackTrace()
    }
  }
}
