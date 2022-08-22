package i4s.scalacv.core.constants

object DecompositionMethods extends Enumeration {
  protected case class TypeVal(flag: Int) extends super.Val

  type DecompositionMethod = Value

  import scala.language.implicitConversions

  implicit def valueToSolveMethod(v: Value): TypeVal = v.asInstanceOf[TypeVal]

  /** Gaussian elimination with the optimal pivot element chosen. */
  val Lu: TypeVal = TypeVal(0)

  /** singular value decomposition (SVD) method; the system can be over-defined and/or the matrix
   * src1 can be singular */
  val SVD: TypeVal = TypeVal(1)

  /** eigenvalue decomposition; the matrix src1 must be symmetrical */
  val Eigen: TypeVal = TypeVal(2)

  /** Cholesky <pre>LL&#94;T</pre> factorization; the matrix src1 must be symmetrical and positively
   * defined */
  val Cholesky: TypeVal = TypeVal(3)

  /** QR factorization; the system can be over-defined and/or the matrix src1 can be singular */
  val QR: TypeVal = TypeVal(4)

  /** while all the previous flags are mutually exclusive, this flag can be used together with
   * any of the previous; it means that the normal equations
   * <pre>\texttt{src1^T\cdot\texttt{src1}\cdot\texttt{dst}=\texttt{src1}^T\texttt{src2}</pre> are
   * solved instead of the original system
   * <pre>\texttt{src1\cdot\texttt{dst}=\texttt{src2}</pre>
   */
  val Normal: TypeVal = TypeVal(16)

}
