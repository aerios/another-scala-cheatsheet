package basic

import scala.io.Source
import scala.util._

object Ch3Tries {

  /**
   * Try is a data structure that enable us to turn exception into value
   * This data structure in my opinion is another huge mind-shifting paradigm
   * from the usual exception throwing. Why is that? By turning exception into value,
   * we can make sure that a function is `total` -- for every input given, the function
   * always return a value or output. Exception thrown in a function will make that function
   * not total or loosely translated as `partial`. The benefit of total function is that
   * every function will behave exactly as its signature, and in return it will make our program
   * easier to reason and will be more likely to be correct when the program typecheck (1).
   *
   * Like Option, Try is an abstract class that has 2 concrete classes
   * each to represent 2 state: success and failure. Success state is
   * represented by Success[A](value: A) and failure by Failure(exc: Throwable)
   *
   */

  val example0: Try[Int] = Try(1)
  val example1: Try[Int] = Failure(new RuntimeException("Here be exception"))
  val example2: Try[Int] = Success(1)

  /**
   * In reality, we seldom call Failure or Success directly. Usually we enclose a block
   * of code that we suspect will throw Exception with Try { /*here be blocks of code */ }
   * */

  val checkForPasscode = (passcode: String) => {
    if(passcode == "herebedragon") "OK"
    else throw new IllegalArgumentException("Passcode is not recognized!")
  }
  val example3 = (passcode: String) => Try { checkForPasscode(passcode) }
  val ret3 = example3("herebedragon") /** ret4: Try[String] = Success("OK") */
  val ret3b = example3("oopsie")      /** ret4b: Try[String] = Failure(IllegalArgumentException)*/

  /**
   * Below is a working example of reading a content from a file.
   * There are at least 2 failure cases and exactly 1 success case:
   * 1. Failure: File not found: java.io.FileNotFoundException
   * 2. Failure: Mismatched charset: java.nio.charset.java.nio.charset.MalformedInputException
   * 3. Success: The content of the file, if the file is exists and can encoded using UTF-8
   * */

  val example4 = (filePath: String) => Try {
    val buffer = Source.fromFile(filePath, "UTF-8")
    val content = buffer.getLines().toList.mkString("\n")
    buffer.close()
    content
  }
  val ret4: Try[String] = example4("/path/to/non_existent/file.txt")    /** ret4: Try[String] = Failure(FileNotFoundException) */
  val ret4b: Try[String] = example4("/path/to/non_utf8/file.xlsx")      /** ret4: Try[String] = Failure(MalformedInputException) */
  val ret4c: Try[String] = example4("/path/to/existing_utf_8/file.txt") /** ret4: Try[String] = Success("content of file here..") */

  /**
   *
   *
   * */


  /**
   * Index
   * (1): https://adamdrake.com/are-your-functions-total.html
   * */
}
