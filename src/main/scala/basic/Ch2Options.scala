package basic

import java.util.Date

object Ch2Options {

  /**
   * Option is a data structure that enable us to represent
   * nullable value in type-safe way
   *
   * Option is an abstract class that has 2 concrete classes
   * each to represent not-null and null condition
   * Some is subtype of Option that enable us to indicate not-null
   * None is subtype of Option that enable us to indicate null
   * To be precise, Some is a container of value, and None is container of nothing
   * */

  val example0: Option[Int] = Option(1)
  val example1: Option[Int] = Some(1)
  val example3: Option[Int] = None
  val example4: Option[Int] = Option.empty[Int] /** This technically returns a None
                                                * but will typecheck as Option[Int]
                                                */

  /**
   * We can check the value wrapped by Option by using several methods
   * */
  val example5 = Some("Hai")
  val value5 = example5.get
  val example6 = None
  val value6 = example6.get /**
                            * BEWARE!
                            * Invoking .get on empty Option a.k.a None will blow your
                            * code with `NoSuchElementException("None.get")` exception
                            * Unless you are really really sure that the Option you are
                            * invoking its .get method is not empty, use other more type-safe
                            * method provided by Option
                            */

  val example7 = Option.empty[String]
  val value7 = example7.getOrElse("This is default value") /** Will return `This is default value` */
  val example8 = Option.empty[String]
  val value8 = example8
    .fold("This is default value")(value => value + ".csv") /**
                                                    * .fold enable us to.. fold, an Option according
                                                    * to its initial state:
                                                    * a. if it is non-empty, the second parameter group
                                                    *    will be invoked `value => value + ".csv"`
                                                    * b. if it is empty, the first parameter group will be
                                                    *     returned `This is default value`
                                                    */
  /**
   * Given previous examples above, use .getOrElse if you want to get the initial value without any modification
   * and use .fold to turn an Option[A] into non-wrapped value B, where B is a type supposedly different than A
   * */

  /**
   * We can create new Option from existing Option by modifying its contained value
   * using .map or .flatMap
   * */
  val example9 = Option(List("a", "b", "c"))
  val value9a = example9.map(_.size) /** Here we transform Option[List[String]] into Option[Int] */
  val value9b = example9.flatMap { list =>
    list.headOption
  } /** Here we transform Option[List[String]] into Option[Int] */

  /**
   * although .map and .flatMap enable us create new Option from existing Option,
   * there is a notable distinction:
   * 1. .map transform Option[A] into Option[B], where B is any valid Scala type
   *    and retrieved directly from the return value of anonymous function provided as .map argument
   * 2. .flatMap transform Option[A] into Option[B], where B is retrieved from
   *    the Option returned by the anonymous function provided as .flatMap argument
   * */

  val example10 = Option(List(1,2,3))
  val value10a = example10
    .map(list => list.headOption)
    .flatten /** if you use Intellij, this line will be highlighted */
  val value10b = example10.flatMap(list => list.headOption)

  /**
   * We can say that .flatMap is shorter version of Option[A].map[B](f: A => B).flatten
   * if only if B is an Option
   * */

  /**
   * Somehow we encounter nested flatMap. To make our code readable,
   * we can use for-yield block instead. Scala compiler can infer and transform
   * the for-yield block into valid flatMap-map chain if and if only we provide
   * typchecked code
   * */
  {
    case class User(id: Int, address: Option[String], birthday: Option[Date])

    /** Pretend that this function is valid user validation */
    val parseUserArgsToId = (idStr: String) => {
      if(idStr == "10") Some(10)
      else None
    }
    /** Pretend that this function is eligibly read user by its id from db */
    val getUserById = (id: Int) => {
      if(id == 10) Some(User(
        id,
        Some("Here"),
        None
      ))
      else None
    }

    val fetchAddressFromUserId1: String => Option[String] = (idStr: String) => {
      parseUserArgsToId(idStr)
        .flatMap { id =>            /** flatMap */
          getUserById(id)           //
            .flatMap { user =>      /** another flatMap */
              user.address          //
            }
        }
    }

    val fetchAddressFromUserId2: String => Option[String] = (idStr: String) => {
      for {
        id <- parseUserArgsToId(idStr) /** this is actually flatMap */
        user <- getUserById(id) /** this is another flatMap */
        address <- user.address /** another flatMap */
      } yield address /** yield block defines what will be returned from this for {} block */
    }

    val value11a: Option[String] = fetchAddressFromUserId1("10")
    val value11b: Option[String] = fetchAddressFromUserId2("10")
  }

  /** Option also provides us with several convenient method to create new Option from
   * existing value or extract a value from non-empty Option
   * */

  /** .filter */
  val example12 = Option(1 -> 2)
  val value12a = example12
    .filter(item => item._2 > 10) /** if `item._2 > 0` is true,
                                  * value12 will be Some[Int],
                                  * else it will be None
                                  * Note that if the initial Option is empty a.k.a None,
                                  * .filter will always return None
                                  */

  /** .foldLeft */
  val example13 = Option(List("a", "b", "c"))
  val value13 = example13.foldLeft("") { (memo, value) =>
    memo + " " + value.mkString(" ")
  } /** .foldLeft enable us to fold an Option[A] into other type B
    * with the initial value of B is specified at the first parameter group and
    * the accumulator function is specified at the second parameter group.
    * If Option[A] is actually empty, .foldLeft will return the initial value
    * Option[A]
    *   .foldLeft[B](initialValue: B)(accumulator: (B, A) => B)
    * */


}
