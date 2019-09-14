package basic

object Ch1Functions {

  /**
   * Function is a construct that enable us to encapsulate a logic within
   * an isolated container that can receive arbitrary amount of inputs (parameters/arguments)
   * and an output (return value)
   * Because function is first-class citizen (construct that can be initiated, manipulated,
   * and assigned), function has type. The type of a function is represented by:
   * 1. The type of its parameters
   * 2. The type of its output
   * and depicted as (P1, P2, P3, ...) => R where P1, P2, P3, ..., and R is valid Scala type
   * for function with 1-arity, instead of using (P) => R we can write is shorter into P => R
   * In Scala, there is no such this as 0-arity function because the form of () => R is actually
   * a 1-arity function with Unit/() as its sole parameter
   * */
  val example0: Int => Int = (num: Int) => num + 1
  val example1: Function1[Int, Int] = (num: Int) => num + 1

  /**
   * Several kinds of function syntax
   * From fun0 to fun2, we construct a function that receive an Int and return an Int using
   * syntactic sugar of A => B
   * fun3 depict the more verbose syntax of defining a function. We rarely use this in our codebase
   * except when really needed
   * */

  val fun0 = (num: Int) => num + 1
  val ret0 = fun0(1) /** ret0: Int = 2 */
  //--
  val fun1 = (num: Int) => {
    num + 1
  }
  val ret1 = fun1(1) /** ret1: Int = 2 */
  //--
  val fun2 = { num: Int =>
    num + 1
  }
  val ret2 = fun2(1) /** ret2: Int = is 2 */
  //--
  val fun3 = new Function1[Int, Int] {
    override def apply(v1: Int) = v1 + 1
  }
  val ret3 = fun3(1)  /** ret3: Int = 2 */
  val ret3a = fun3.apply(1) /** <- this is another way of calling a function
                            * When we create a function using syntactic sugar, compiler
                            * will translate our function into FunctionN[A, A1,...,R] form
                            * such as this. Because of this, we can call a function by calling
                            * .apply() method instead of calling it directly
                            */
                            /** ret3a: Int = 2 */

  /**
   * Function can has 0 to arbitrary amount of input
   * fun4 is function that has 2 parameters
   * fun5 is a function that has no parameter but return an Int
   * fun6 through fun8 is a function that has a parameter and _return_ an Unit
   * Why Unit? Because *almost* everything in Scala is expression and function is no exception,
   * so to represent a type that resembles nothingness, we have Unit and its only value is (),
   * depicted in fun8. Empty expression _always_ return a Unit
   * */

  val fun4 = (v1: Int, v2: Int) => v1 + v2
  val ret4 = fun4(1, 2) /** ret4: Int = 3 */
  //--
  val fun4a = { (v1: Int, v2: Int) =>
    v1 + v2
  }
  val ret4a = fun4(1, 2) /** ret4a: Int = 3 */
  //--
  val fun5 = () => 1
  val ret5 = fun5() /** ret5: Int = 1 */
  //--
  val fun6 = (num: Int) => {}
  val ret6 = fun6(1) /** ret6: Unit = () */
  val fun7 = (_: Int) => {} /** because the parameter of fun7 will never be used,
                            * we can use _ as wildcard placeholder
                            * for its input variable
                            */
  val ret7 = fun7(1) /** ret7: Unit = () */
  val fun8 = (num: Int) => ()
  val ret8 = fun8(1) /** ret8: Unit = () */

  /**
   * Because function is actually a valid Scala type, we can use function is parameter
   * or return value
   * */

  val fun9 = (num: Int, f: Int => Int) => { num + f(num) }
  val ret9 = fun9(1, num => num * 2) /** ret9: Int = 3 */
  val ret9a = fun9(1, _ * 2) /** ret9a: Int = 3 */
  val ret9b = fun9(1, { num =>
    num * 2
  }) /** ret9b: Int = 3 */
  //--
  val fun10 = (prefix: String, suffix: String) => {
    (other: String) => prefix + " " + other + " " + suffix
  }
  val ret10 = fun10("hello", "!") /** ret10: String => String = <function>  */
  val ret10a = ret10("Bob") /** ret10a: String = "hello Bob !" */

  val fun11 = (prefix: String) =>
    (suffix: String) =>
      (other: String) =>
        prefix + " " + other + " " + suffix
  val ret11: String = fun11("hello")("!")("Bob") /** ret11: String = "hello Bob !" */
  val ret11a: String => String => String  = fun11("hello") /** ret11a: String => String => String = <function> */
  val ret11b: String => String = ret11a("!") /** ret11b: String => String = <function> */
  val ret11c: String = ret11b("Bob") /** ret11c: String = "hello Bob !" */
}
