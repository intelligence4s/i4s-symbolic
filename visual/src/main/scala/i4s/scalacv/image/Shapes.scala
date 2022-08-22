package i4s.scalacv.image

import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.Type
import i4s.scalacv.image.constants.ConnectedComponentsAlgorithmsTypes.ConnectedComponentsAlgorithmsType
import i4s.scalacv.image.constants.ContourApproximationMethods.ContourApproximationMethod
import i4s.scalacv.image.constants.DistanceTypes.DistanceType
import i4s.scalacv.image.constants.RectanglesIntersectTypes
import i4s.scalacv.image.constants.RectanglesIntersectTypes.RectanglesIntersectType
import i4s.scalacv.image.constants.RetrievalModes.RetrievalMode
import i4s.scalacv.image.constants.ShapeMatchModes.ShapeMatchMode
import org.bytedeco.javacpp.{DoublePointer, FloatPointer}
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core.{GpuMat, GpuMatVector, Mat, MatVector, Moments, Point, Point2f, Rect, RotatedRect, UMat, UMatVector}
import org.bytedeco.opencv.opencv_imgproc.{GeneralizedHoughBallard, GeneralizedHoughGuil}

import java.nio.{DoubleBuffer, FloatBuffer}

object Shapes extends Shapes

trait Shapes {

  /** Calculates all of the moments up to the third order of a polygon or rasterized shape.
   * <p>
   * The function computes moments, up to the 3rd order, of a vector shape or a rasterized shape. The
   * results are returned in the structure cv::Moments.
   * <p>
   *
   * @param array       Raster image (single-channel, 8-bit or floating-point 2D array) or an array (
   *                    {@code 1 \times N} or {@code N \times 1} ) of 2D points (Point or Point2f ).
   * @param binaryImage If it is true, all non-zero image pixels are treated as 1's. The parameter is
   *                    used for images only.
   * @return moments.
   *         <p>
   *         \note Only applicable to contour moments calculations from Python bindings: Note that the numpy
   *         type for the input array should be either np.int32 or np.float32.
   *         <p>
   * @see contourArea, arcLength
   */

  def moments(array: Mat, binaryImage: Boolean /*=false*/): Moments =
    opencv_imgproc.moments(array,binaryImage)

  def moments(array: Mat): Moments = moments(array, binaryImage = false)

  /** \brief Calculates seven Hu invariants.
   * <p>
   * The function calculates seven Hu invariants (introduced in \cite Hu62; see also
   * <http://en.wikipedia.org/wiki/Image_moment>) defined as:
   * <p>
   * <pre>{@code \[\begin{array}{l} hu[0]= \eta _{20}+ \eta _{02} \\ hu[1]=( \eta _{20}- \eta _{02})^{2}+4 \eta _{11}^{2} \\ hu[2]=( \eta _{30}-3 \eta _{12})^{2}+ (3 \eta _{21}- \eta _{03})^{2} \\ hu[3]=( \eta _{30}+ \eta _{12})^{2}+ ( \eta _{21}+ \eta _{03})^{2} \\ hu[4]=( \eta _{30}-3 \eta _{12})( \eta _{30}+ \eta _{12})[( \eta _{30}+ \eta _{12})^{2}-3( \eta _{21}+ \eta _{03})^{2}]+(3 \eta _{21}- \eta _{03})( \eta _{21}+ \eta _{03})[3( \eta _{30}+ \eta _{12})^{2}-( \eta _{21}+ \eta _{03})^{2}] \\ hu[5]=( \eta _{20}- \eta _{02})[( \eta _{30}+ \eta _{12})^{2}- ( \eta _{21}+ \eta _{03})^{2}]+4 \eta _{11}( \eta _{30}+ \eta _{12})( \eta _{21}+ \eta _{03}) \\ hu[6]=(3 \eta _{21}- \eta _{03})( \eta _{21}+ \eta _{03})[3( \eta _{30}+ \eta _{12})^{2}-( \eta _{21}+ \eta _{03})^{2}]-( \eta _{30}-3 \eta _{12})( \eta _{21}+ \eta _{03})[3( \eta _{30}+ \eta _{12})^{2}-( \eta _{21}+ \eta _{03})^{2}] \\ \end{array}\]}</pre>
   * <p>
   * where {@code \eta_{ji}} stands for {@code \texttt{Moments::nu}_{ji}} .
   * <p>
   * These values are proved to be invariants to the image scale, rotation, and reflection except the
   * seventh one, whose sign is changed by reflection. This invariance is proved with the assumption of
   * infinite image resolution. In case of raster images, the computed Hu invariants for the original and
   * transformed images are a bit different.
   * <p>
   * @param moments Input moments computed with moments .
   * @param hu      Output Hu invariants.
   *                <p>
   * @see matchShapes
   */
  def huMoments(moments: Moments, hu: DoublePointer): Unit =
    opencv_imgproc.HuMoments(moments,hu)

  def huMoments(moments: Moments, hu: DoubleBuffer): Unit =
    opencv_imgproc.HuMoments(moments,hu)

  def huMoments(moments: Moments, hu: Array[Double]): Unit =
    opencv_imgproc.HuMoments(moments,hu)

  def huMoments(moments: Moments, hu: Mat): Unit =
    opencv_imgproc.HuMoments(moments,hu)

  implicit class ImageShapes(image: Mat) {


    /** \brief computes the connected components labeled image of boolean image
     * <p>
     * image with 4 or 8 way connectivity - returns N, the total number of labels [0, N-1] where 0
     * represents the background label. ltype specifies the output label image type, an important
     * consideration based on the total number of labels or alternatively the total number of pixels in
     * the source image. ccltype specifies the connected components labeling algorithm to use, currently
     * Bolelli (Spaghetti) \cite Bolelli2019, Grana (BBDT) \cite Grana2010 and Wu's (SAUF) \cite Wu2009 algorithms
     * are supported, see the #ConnectedComponentsAlgorithmsTypes for details. Note that SAUF algorithm forces
     * a row major ordering of labels while Spaghetti and BBDT do not.
     * This function uses parallel version of the algorithms if at least one allowed
     * parallel framework is enabled and if the rows of the image are at least twice the number returned by #getNumberOfCPUs.
     * <p>
     * @param image        the 8-bit single-channel image to be labeled
     * @param labels       destination labeled image
     * @param connectivity 8 or 4 for 8-way or 4-way connectivity respectively
     * @param ltype        output image label type. Currently CV_32S and CV_16U are supported.
     * @param ccltype      connected components algorithm type (see the #ConnectedComponentsAlgorithmsTypes).
     */
    def connectedComponentsWithAlgorithm(labels: Mat, connectivity: Int, ltype: Type, ccltype: ConnectedComponentsAlgorithmsType): Int =
      opencv_imgproc.connectedComponentsWithAlgorithm(image,labels,connectivity,ltype.flag,ccltype.flag)

    /** \overload
     * <p>
     * @param image        the 8-bit single-channel image to be labeled
     * @param labels       destination labeled image
     * @param connectivity 8 or 4 for 8-way or 4-way connectivity respectively
     * @param ltype        output image label type. Currently CV_32S and CV_16U are supported.
     */
    def connectedComponents(connectivity: Int /*=8*/ , ltype: Type /*=CV_32S*/): (Mat,Int) = {
      val labels = new Mat()
      val result = opencv_imgproc.connectedComponents(image,labels,connectivity,ltype.flag)
      (labels,result)
    }

    def connectedComponents(labels: Mat): (Mat,Int) =
      connectedComponents(connectivity = 8,ltype = Types.Cv32S)


    /** \brief computes the connected components labeled image of boolean image and also produces a statistics output for each label
     * <p>
     * image with 4 or 8 way connectivity - returns N, the total number of labels [0, N-1] where 0
     * represents the background label. ltype specifies the output label image type, an important
     * consideration based on the total number of labels or alternatively the total number of pixels in
     * the source image. ccltype specifies the connected components labeling algorithm to use, currently
     * Bolelli (Spaghetti) \cite Bolelli2019, Grana (BBDT) \cite Grana2010 and Wu's (SAUF) \cite Wu2009 algorithms
     * are supported, see the #ConnectedComponentsAlgorithmsTypes for details. Note that SAUF algorithm forces
     * a row major ordering of labels while Spaghetti and BBDT do not.
     * This function uses parallel version of the algorithms (statistics included) if at least one allowed
     * parallel framework is enabled and if the rows of the image are at least twice the number returned by #getNumberOfCPUs.
     * <p>
     * @param image        the 8-bit single-channel image to be labeled
     * @param labels       destination labeled image
     * @param stats        statistics output for each label, including the background label.
     *                     Statistics are accessed via stats(label, COLUMN) where COLUMN is one of
     *                     #ConnectedComponentsTypes, selecting the statistic. The data type is CV_32S.
     * @param centroids    centroid output for each label, including the background label. Centroids are
     *                     accessed via centroids(label, 0) for x and centroids(label, 1) for y. The data type CV_64F.
     * @param connectivity 8 or 4 for 8-way or 4-way connectivity respectively
     * @param ltype        output image label type. Currently CV_32S and CV_16U are supported.
     * @param ccltype      connected components algorithm type (see #ConnectedComponentsAlgorithmsTypes).
     */
    def connectedComponentsWithStatsWithAlgorithm(connectivity: Int, ltype: Type, ccltype: ConnectedComponentsAlgorithmsType): (Mat,Mat,Mat,Int) = {
      val labels = new Mat()
      val stats = new Mat()
      val centroids = new Mat()
      val result = opencv_imgproc.connectedComponentsWithStatsWithAlgorithm(image,labels,stats,centroids,connectivity,ltype.flag,ccltype.flag)
      (labels,stats,centroids,result)
    }

    /** \overload
     * @param image        the 8-bit single-channel image to be labeled
     * @param labels       destination labeled image
     * @param stats        statistics output for each label, including the background label.
     *                     Statistics are accessed via stats(label, COLUMN) where COLUMN is one of
     *                     #ConnectedComponentsTypes, selecting the statistic. The data type is CV_32S.
     * @param centroids    centroid output for each label, including the background label. Centroids are
     *                     accessed via centroids(label, 0) for x and centroids(label, 1) for y. The data type CV_64F.
     * @param connectivity 8 or 4 for 8-way or 4-way connectivity respectively
     * @param ltype        output image label type. Currently CV_32S and CV_16U are supported.
     */
    def connectedComponentsWithStats(connectivity: Int, ltype: Type): (Mat,Mat,Mat,Int) = {
      val labels = new Mat()
      val stats = new Mat()
      val centroids = new Mat()
      val result = opencv_imgproc.connectedComponentsWithStats(image,labels,stats,centroids,connectivity,ltype.flag)
      (labels,stats,centroids,result)
    }

    def connectedComponentsWithStats(): (Mat,Mat,Mat,Int) =
      connectedComponentsWithStats(connectivity = 8,ltype = Types.Cv32S)

    /** \brief Finds contours in a binary image.
     * <p>
     * The function retrieves contours from the binary image using the algorithm \cite Suzuki85 . The contours
     * are a useful tool for shape analysis and object detection and recognition. See squares.cpp in the
     * OpenCV sample directory.
     * \note Since opencv 3.2 source image is not modified by this function.
     * <p>
     *
     * @param image     Source, an 8-bit single-channel image. Non-zero pixels are treated as 1's. Zero
     *                  pixels remain 0's, so the image is treated as binary . You can use #compare, #inRange, #threshold ,
     *                  #adaptiveThreshold, #Canny, and others to create a binary image out of a grayscale or color one.
     *                  If mode equals to #RETR_CCOMP or #RETR_FLOODFILL, the input can also be a 32-bit integer image of labels (CV_32SC1).
     * @param contours  Detected contours. Each contour is stored as a vector of points (e.g.
     *                  std::vector<std::vector<cv::Point> >).
     * @param hierarchy Optional output vector (e.g. std::vector<cv::Vec4i>), containing information about the image topology. It has
     *                  as many elements as the number of contours. For each i-th contour contours[i], the elements
     *                  hierarchy[i][0] , hierarchy[i][1] , hierarchy[i][2] , and hierarchy[i][3] are set to 0-based indices
     *                  in contours of the next and previous contours at the same hierarchical level, the first child
     *                  contour and the parent contour, respectively. If for the contour i there are no next, previous,
     *                  parent, or nested contours, the corresponding elements of hierarchy[i] will be negative.
     *                  \note In Python, hierarchy is nested inside a top level array. Use hierarchy[0][i] to access hierarchical elements of i-th contour.
     * @param mode      Contour retrieval mode, see #RetrievalModes
     * @param method    Contour approximation method, see #ContourApproximationModes
     * @param offset    Optional offset by which every contour point is shifted. This is useful if the
     *                  contours are extracted from the image ROI and then they should be analyzed in the whole image
     *                  context.
     */
    def findContours(hierarchy: Mat, mode: RetrievalMode, method: ContourApproximationMethod, offset: Point): MatVector = {
      val contours = new MatVector()
      opencv_imgproc.findContours(image, contours, hierarchy, mode.flag, method.flag, offset)
      contours
    }

    def findContours(hierarchy: Mat, mode: RetrievalMode, method: ContourApproximationMethod): MatVector =
      findContours(hierarchy,mode,method,offset = new Point())

    /** \overload */
    def findContours(mode: RetrievalMode, method: ContourApproximationMethod, offset: Point): MatVector = {
      val contours = new MatVector()
      opencv_imgproc.findContours(image,contours,mode.flag,method.flag,offset)
      contours
    }

    def findContours(mode: RetrievalMode, method: ContourApproximationMethod): MatVector =
      findContours(mode,method,offset = new Point())

    /** \example samples/cpp/squares.cpp
     * A program using pyramid scaling, Canny, contours and contour simplification to find
     * squares in a list of images (pic1-6.png). Returns sequence of squares detected on the image.
     */

    /** \example samples/tapi/squares.cpp
     * A program using pyramid scaling, Canny, contours and contour simplification to find
     * squares in the input image.
     */

  }

  /** \brief Approximates a polygonal curve(s) with the specified precision.
   * <p>
   * The function cv::approxPolyDP approximates a curve or a polygon with another curve/polygon with less
   * vertices so that the distance between them is less or equal to the specified precision. It uses the
   * Douglas-Peucker algorithm <http://en.wikipedia.org/wiki/Ramer-Douglas-Peucker_algorithm>
   * <p>
   *
   * @param curve       Input vector of a 2D point stored in std::vector or Mat
   * @param approxCurve Result of the approximation. The type should match the type of the input curve.
   * @param epsilon     Parameter specifying the approximation accuracy. This is the maximum distance
   *                    between the original curve and its approximation.
   * @param closed      If true, the approximated curve is closed (its first and last vertices are
   *                    connected). Otherwise, it is not closed.
   */
  def approxPolyDP(curve: Mat, approxCurve: Mat, epsilon: Double, closed: Boolean): Unit =
    opencv_imgproc.approxPolyDP(curve, approxCurve, epsilon, closed)

  /** \brief Calculates a contour perimeter or a curve length.
   * <p>
   * The function computes a curve length or a closed contour perimeter.
   * <p>
   *
   * @param curve  Input vector of 2D points, stored in std::vector or Mat.
   * @param closed Flag indicating whether the curve is closed or not.
   */
  def arcLength(curve: Mat, closed: Boolean): Double =
    opencv_imgproc.arcLength(curve, closed)

  /** \brief Calculates the up-right bounding rectangle of a point set or non-zero pixels of gray-scale image.
   * <p>
   * The function calculates and returns the minimal up-right bounding rectangle for the specified point set or
   * non-zero pixels of gray-scale image.
   * <p>
   *
   * @param array Input gray-scale image or 2D point set, stored in std::vector or Mat.
   */
  def boundingRect(array: Mat): Rect =
    opencv_imgproc.boundingRect(array)

  /** \brief Calculates a contour area.
   * <p>
   * The function computes a contour area. Similarly to moments , the area is computed using the Green
   * formula. Thus, the returned area and the number of non-zero pixels, if you draw the contour using
   * #drawContours or #fillPoly , can be different. Also, the function will most certainly give a wrong
   * results for contours with self-intersections.
   * <p>
   * Example:
   * <pre>
   * vector<Point> contour;
   * contour.push_back(Point2f(0, 0));
   * contour.push_back(Point2f(10, 0));
   * contour.push_back(Point2f(10, 10));
   * contour.push_back(Point2f(5, 4));
   *
   * double area0 = contourArea(contour);
   * vector<Point> approx;
   * approxPolyDP(contour, approx, 5, true);
   * double area1 = contourArea(approx);
   *
   * cout << "area0 =" << area0 << endl <<
   * "area1 =" << area1 << endl <<
   * "approx poly vertices" << approx.size() << endl;
   * </pre>
   *
   * @param contour  Input vector of 2D points (contour vertices), stored in std::vector or Mat.
   * @param oriented Oriented area flag. If it is true, the function returns a signed area value,
   *                 depending on the contour orientation (clockwise or counter-clockwise). Using this feature you can
   *                 determine orientation of a contour by taking the sign of an area. By default, the parameter is
   *                 false, which means that the absolute value is returned.
   */
  def contourArea(contour: Mat, oriented: Boolean): Double =
    opencv_imgproc.contourArea(contour, oriented)

  def contourArea(contour: Mat): Double = contourArea(contour, oriented = false)

  /** \brief Finds a rotated rectangle of the minimum area enclosing the input 2D point set.
   * <p>
   * The function calculates and returns the minimum-area bounding rectangle (possibly rotated) for a
   * specified point set. Developer should keep in mind that the returned RotatedRect can contain negative
   * indices when data is close to the containing Mat element boundary.
   * <p>
   *
   * @param points Input vector of 2D points, stored in std::vector\<\> or Mat
   */
  def minAreaRect(points: Mat): RotatedRect =
    opencv_imgproc.minAreaRect(points)

  /** \brief Finds the four vertices of a rotated rect. Useful to draw the rotated rectangle.
   * <p>
   * The function finds the four vertices of a rotated rectangle. This function is useful to draw the
   * rectangle. In C++, instead of using this function, you can directly use RotatedRect::points method. Please
   * visit the \ref tutorial_bounding_rotated_ellipses "tutorial on Creating Bounding rotated boxes and ellipses for contours" for more information.
   * <p>
   *
   * @param box The input rotated rectangle. It may be the output of
   * @return The output array of four vertices of rectangles.
   */
  def boxPoints(box: RotatedRect): Mat = {
    val points = new Mat()
    opencv_imgproc.boxPoints(box, points)
    points
  }

  /** \brief Finds a circle of the minimum area enclosing a 2D point set.
   * <p>
   * The function finds the minimal enclosing circle of a 2D point set using an iterative algorithm.
   * <p>
   *
   * @param points Input vector of 2D points, stored in std::vector\<\> or Mat
   * @param center Output center of the circle.
   * @param radius Output radius of the circle.
   */
  def minEnclosingCircle(points: Mat): (Point2f, Float) = {
    val center = new Point2f()
    val radius = new FloatPointer()
    opencv_imgproc.minEnclosingCircle(points, center, radius)
    (center, radius.get)
  }

  /** \example samples/cpp/minarea.cpp
   */

  /** \brief Finds a triangle of minimum area enclosing a 2D point set and returns its area.
   * <p>
   * The function finds a triangle of minimum area enclosing the given set of 2D points and returns its
   * area. The output for a given 2D point set is shown in the image below. 2D points are depicted in
   * red* and the enclosing triangle in *yellow*.
   * <p>
   * ![Sample output of the minimum enclosing triangle function](pics/minenclosingtriangle.png)
   * <p>
   * The implementation of the algorithm is based on O'Rourke's \cite ORourke86 and Klee and Laskowski's
   * \cite KleeLaskowski85 papers. O'Rourke provides a {@code \theta(n)} algorithm for finding the minimal
   * enclosing triangle of a 2D convex polygon with n vertices. Since the #minEnclosingTriangle function
   * takes a 2D point set as input an additional preprocessing step of computing the convex hull of the
   * 2D point set is required. The complexity of the #convexHull function is {@code O(n log(n))} which is higher
   * than {@code \theta(n)}. Thus the overall complexity of the function is {@code O(n log(n))}.
   * <p>
   *
   * @param points   Input vector of 2D points with depth CV_32S or CV_32F, stored in std::vector\<\> or Mat
   * @param triangle Output vector of three 2D points defining the vertices of the triangle. The depth
   *                 of the OutputArray must be CV_32F.
   */
  def minEnclosingTriangle(points: Mat): (Mat, Double) = {
    val triangle = new Mat()
    val area = opencv_imgproc.minEnclosingTriangle(points, triangle)
    (triangle, area)
  }

  /** \brief Compares two shapes.
   * <p>
   * The function compares two shapes. All three implemented methods use the Hu invariants (see #HuMoments)
   * <p>
   *
   * @param contour1  First contour or grayscale image.
   * @param contour2  Second contour or grayscale image.
   * @param method    Comparison method, see #ShapeMatchModes
   * @param parameter Method-specific parameter (not supported now).
   */
  def matchShapes(contour1: Mat, contour2: Mat, method: ShapeMatchMode, parameter: Double): Double =
    opencv_imgproc.matchShapes(contour1, contour2, method.flag, parameter)

  /** \example samples/cpp/convexhull.cpp
   * An example using the convexHull functionality
   */

  /** \brief Finds the convex hull of a point set.
   * <p>
   * The function cv::convexHull finds the convex hull of a 2D point set using the Sklansky's algorithm \cite Sklansky82
   * that has *O(N logN)* complexity in the current implementation.
   * <p>
   *
   * @param points       Input 2D point set, stored in std::vector or Mat.
   * @param hull         Output convex hull. It is either an integer vector of indices or vector of points. In
   *                     the first case, the hull elements are 0-based indices of the convex hull points in the original
   *                     array (since the set of convex hull points is a subset of the original point set). In the second
   *                     case, hull elements are the convex hull points themselves.
   * @param clockwise    Orientation flag. If it is true, the output convex hull is oriented clockwise.
   *                     Otherwise, it is oriented counter-clockwise. The assumed coordinate system has its X axis pointing
   *                     to the right, and its Y axis pointing upwards.
   * @param returnPoints Operation flag. In case of a matrix, when the flag is true, the function
   *                     returns convex hull points. Otherwise, it returns indices of the convex hull points. When the
   *                     output array is std::vector, the flag is ignored, and the output depends on the type of the
   *                     vector: std::vector\<int\> implies returnPoints=false, std::vector\<Point\> implies
   *                     returnPoints=true.
   *                     <p>
   *                     \note {@code points} and {@code hull} should be different arrays, inplace processing isn't supported.
   *                     <p>
   *                     Check \ref tutorial_hull "the corresponding tutorial" for more details.
   *                     <p>
   *                     useful links:
   *                     <p>
   *                     https://www.learnopencv.com/convex-hull-using-opencv-in-python-and-c/
   */
  def convexHull(points: Mat, clockwise: Boolean, returnPoints: Boolean): Mat = {
    val hull = new Mat()
    opencv_imgproc.convexHull(points, hull, clockwise, returnPoints)
    hull
  }

  def convexHull(points: Mat): Mat =
    convexHull(points, clockwise = false, returnPoints = true)

  /** \brief Finds the convexity defects of a contour.
   * <p>
   * The figure below displays convexity defects of a hand contour:
   * <p>
   * ![image](pics/defects.png)
   * <p>
   *
   * @param contour    Input contour.
   * @param convexhull Convex hull obtained using convexHull that should contain indices of the contour
   *                   points that make the hull.
   * @return The output vector of convexity defects. In C++ and the new Python/Java
   *         interface each convexity defect is represented as 4-element integer vector (a.k.a. #Vec4i):
   *         (start_index, end_index, farthest_pt_index, fixpt_depth), where indices are 0-based indices
   *         in the original contour of the convexity defect beginning, end and the farthest point, and
   *         fixpt_depth is fixed-point approximation (with 8 fractional bits) of the distance between the
   *         farthest contour point and the hull. That is, to get the floating-point value of the depth will be
   *         fixpt_depth/256.0.
   */
  def convexityDefects(contour: Mat, convexhull: Mat): Mat = {
    val defects = new Mat
    opencv_imgproc.convexityDefects(contour, convexhull, defects)
    defects
  }

  /** \brief Tests a contour convexity.
   * <p>
   * The function tests whether the input contour is convex or not. The contour must be simple, that is,
   * without self-intersections. Otherwise, the function output is undefined.
   * <p>
   *
   * @param contour Input vector of 2D points, stored in std::vector\<\> or Mat
   */
  def isContourConvex(contour: Mat): Boolean =
    opencv_imgproc.isContourConvex(contour)

  /** \example samples/cpp/intersectExample.cpp
   * Examples of how intersectConvexConvex works
   */

  /** \brief Finds intersection of two convex polygons
   * <p>
   *
   * @param p1           First polygon
   * @param p2           Second polygon
   * @param p12          Output polygon describing the intersecting area
   * @param handleNested When true, an intersection is found if one of the polygons is fully enclosed in the other.
   *                     When false, no intersection is found. If the polygons share a side or the vertex of one polygon lies on an edge
   *                     of the other, they are not considered nested and an intersection will be found regardless of the value of handleNested.
   *                     <p>
   * @return Absolute value of area of intersecting polygon
   *         <p>
   *         \note intersectConvexConvex doesn't confirm that both polygons are convex and will return invalid results if they aren't.
   */
  def intersectConvexConvex(p1: Mat, p2: Mat, handleNested: Boolean): (Mat, Float) = {
    val p12 = new Mat()
    val area = opencv_imgproc.intersectConvexConvex(p1, p1, p12, handleNested)
    (p12, area)
  }

  def intersectConvexConvex(p1: Mat, p2: Mat): (Mat, Float) =
    intersectConvexConvex(p1, p2, handleNested = true)

  /** \example samples/cpp/fitellipse.cpp
   * An example using the fitEllipse technique
   */

  /** \brief Fits an ellipse around a set of 2D points.
   * <p>
   * The function calculates the ellipse that fits (in a least-squares sense) a set of 2D points best of
   * all. It returns the rotated rectangle in which the ellipse is inscribed. The first algorithm described by \cite Fitzgibbon95
   * is used. Developer should keep in mind that it is possible that the returned
   * ellipse/rotatedRect data contains negative indices, due to the data points being close to the
   * border of the containing Mat element.
   * <p>
   *
   * @param points Input 2D point set, stored in std::vector\<\> or Mat
   */
  def fitEllipse(points: Mat): RotatedRect =
    opencv_imgproc.fitEllipse(points)

  /** \brief Fits an ellipse around a set of 2D points.
   * <p>
   * The function calculates the ellipse that fits a set of 2D points.
   * It returns the rotated rectangle in which the ellipse is inscribed.
   * The Approximate Mean Square (AMS) proposed by \cite Taubin1991 is used.
   * <p>
   * For an ellipse, this basis set is {@code \chi= \left(x^2, x y, y^2, x, y, 1\right) },
   * which is a set of six free coefficients {@code A^T=\left\{A_{\text{xx}},A_{\text{xy}},A_{\text{yy}},A_x,A_y,A_0\right\} }.
   * However, to specify an ellipse, all that is needed is five numbers; the major and minor axes lengths {@code (a,b) },
   * the position {@code (x_0,y_0) }, and the orientation {@code \theta }. This is because the basis set includes lines,
   * quadratics, parabolic and hyperbolic functions as well as elliptical functions as possible fits.
   * If the fit is found to be a parabolic or hyperbolic function then the standard #fitEllipse method is used.
   * The AMS method restricts the fit to parabolic, hyperbolic and elliptical curves
   * by imposing the condition that {@code A^T ( D_x^T D_x  +   D_y^T D_y) A = 1 } where
   * the matrices {@code Dx } and {@code Dy } are the partial derivatives of the design matrix {@code D } with
   * respect to x and y. The matrices are formed row by row applying the following to
   * each of the points in the set:
   * <pre>{@code \begin{align*}
   * D(i,:)&=\left\{x_i^2, x_i y_i, y_i^2, x_i, y_i, 1\right\} &
   * D_x(i,:)&=\left\{2 x_i,y_i,0,1,0,0\right\} &
   * D_y(i,:)&=\left\{0,x_i,2 y_i,0,1,0\right\}
   * \end{align*}}</pre>
   * The AMS method minimizes the cost function
   * <pre>{@code \begin{equation*}
   * \epsilon ^2=\frac{ A^T D^T D A }{ A^T (D_x^T D_x +  D_y^T D_y) A^T }
   * \end{equation*}}</pre>
   * <p>
   * The minimum cost is found by solving the generalized eigenvalue problem.
   * <p>
   * <pre>{@code \begin{equation*}
   * D^T D A = \lambda  \left( D_x^T D_x +  D_y^T D_y\right) A
   * \end{equation*}}</pre>
   * <p>
   *
   * @param points Input 2D point set, stored in std::vector\<\> or Mat
   */
  def fitEllipseAMS(points: Mat): RotatedRect =
    opencv_imgproc.fitEllipseAMS(points)

  /** \brief Fits an ellipse around a set of 2D points.
   * <p>
   * The function calculates the ellipse that fits a set of 2D points.
   * It returns the rotated rectangle in which the ellipse is inscribed.
   * The Direct least square (Direct) method by \cite Fitzgibbon1999 is used.
   * <p>
   * For an ellipse, this basis set is {@code \chi= \left(x^2, x y, y^2, x, y, 1\right) },
   * which is a set of six free coefficients {@code A^T=\left\{A_{\text{xx}},A_{\text{xy}},A_{\text{yy}},A_x,A_y,A_0\right\} }.
   * However, to specify an ellipse, all that is needed is five numbers; the major and minor axes lengths {@code (a,b) },
   * the position {@code (x_0,y_0) }, and the orientation {@code \theta }. This is because the basis set includes lines,
   * quadratics, parabolic and hyperbolic functions as well as elliptical functions as possible fits.
   * The Direct method confines the fit to ellipses by ensuring that {@code 4 A_{xx} A_{yy}- A_{xy}^2 > 0 }.
   * The condition imposed is that {@code 4 A_{xx} A_{yy}- A_{xy}^2=1 } which satisfies the inequality
   * and as the coefficients can be arbitrarily scaled is not overly restrictive.
   * <p>
   * <pre>
   * \epsilon ^2= A^T D^T D A \quad \text{with} \quad A^T C A =1 \quad \text{and} \quad C=\left(\begin{matrix}
   * 0 & 0  & 2  & 0  & 0  &  0  \\
   * 0 & -1  & 0  & 0  & 0  &  0 \\
   * 2 & 0  & 0  & 0  & 0  &  0 \\
   * 0 & 0  & 0  & 0  & 0  &  0 \\
   * 0 & 0  & 0  & 0  & 0  &  0 \\
   * 0 & 0  & 0  & 0  & 0  &  0
   * \end{matrix} \right)
   * \end{equation*}}</pre>
   * <p>
   * The minimum cost is found by solving the generalized eigenvalue problem.
   * <p>
   * <pre><pre>\begin{equation*</pre>
   * D^T D A = \lambda  \left( C\right) A
   * \end{equation*}}</pre>
   * <p>
   * The system produces only one positive eigenvalue <pre>\lambda</pre> which is chosen as the solution
   * with its eigenvector <pre>\mathbf{u</pre>}. These are used to find the coefficients
   * <p>
   * <pre>
   * A = \sqrt{\frac{1}{\mathbf{u}^T C \mathbf{u}}}  \mathbf{u}
   * \end{equation*}</pre>
   * The scaling factor guarantees that  <pre>A&#94;T C A &#61;1</pre>.
   * <p>
   *
   * @param points Input 2D point set, stored in std::vector\<\> or Mat
   */
  def fitEllipseDirect(points: Mat): RotatedRect =
    opencv_imgproc.fitEllipseDirect(points)

  /** \brief Fits a line to a 2D or 3D point set.
   * <p>
   * The function fitLine fits a line to a 2D or 3D point set by minimizing <pre>\sum_i \rho(r_i)</pre> where
   * <pre>r_i</pre> is a distance between the <pre>i&#94;{th</pre>} point, the line and <pre>\rho(r)</pre> is a distance function, one
   * of the following:
   * -  DIST_L2
   * <pre>\[\rho (r) = r^2/2  \quad \text{(the simplest and the fastest least-squares method)}\]</pre>
   * - DIST_L1
   * <pre>\[\rho (r) = r\]</pre>
   * - DIST_L12
   * <pre>\[\rho (r) = 2  \cdot ( \sqrt{1 + \frac{r^2}{2}} - 1)\]</pre>
   * - DIST_FAIR
   * <pre>\[\rho \left (r \right ) = C^2  \cdot \left (  \frac{r}{C} -  \log{\left(1 + \frac{r}{C}\right)} \right )  \quad \text{where} \quad C=1.3998\]</pre>
   * - DIST_WELSCH
   * <pre>\[\rho \left (r \right ) =  \frac{C^2}{2} \cdot \left ( 1 -  \exp{\left(-\left(\frac{r}{C}\right)^2\right)} \right )  \quad \text{where} \quad C=2.9846\]</pre>
   * - DIST_HUBER
   * <pre>\[\rho (r) =  \fork{r^2/2}{if \(r < C\)}{C \cdot (r-C/2)}{otherwise} \quad \text{where} \quad C=1.345\]</pre>
   * <p>
   * The algorithm is based on the M-estimator ( <http://en.wikipedia.org/wiki/M-estimator> ) technique
   * that iteratively fits the line using the weighted least-squares algorithm. After each iteration the
   * weights {@code w_i} are adjusted to be inversely proportional to {@code \rho(r_i)} .
   * <p>
   * @param points   Input vector of 2D or 3D points, stored in std::vector\<\> or Mat.
   * @param line     Output line parameters. In case of 2D fitting, it should be a vector of 4 elements
   *                 (like Vec4f) - (vx, vy, x0, y0), where (vx, vy) is a normalized vector collinear to the line and
   *                 (x0, y0) is a point on the line. In case of 3D fitting, it should be a vector of 6 elements (like
   *                 Vec6f) - (vx, vy, vz, x0, y0, z0), where (vx, vy, vz) is a normalized vector collinear to the line
   *                 and (x0, y0, z0) is a point on the line.
   * @param distType Distance used by the M-estimator, see #DistanceTypes
   * @param param    Numerical parameter ( C ) for some types of distances. If it is 0, an optimal value
   *                 is chosen.
   * @param reps     Sufficient accuracy for the radius (distance between the coordinate origin and the line).
   * @param aeps     Sufficient accuracy for the angle. 0.01 would be a good default value for reps and aeps.
   */
  def fitLine(points: Mat, distType: DistanceType, param: Double, reps: Double, aeps: Double): Mat = {
    val line = new Mat()
    opencv_imgproc.fitLine(points, line, distType.flag, param, reps, aeps)
    line
  }

  /** \brief Performs a point-in-contour test.
   * <p>
   * The function determines whether the point is inside a contour, outside, or lies on an edge (or
   * coincides with a vertex). It returns positive (inside), negative (outside), or zero (on an edge)
   * value, correspondingly. When measureDist=false , the return value is +1, -1, and 0, respectively.
   * Otherwise, the return value is a signed distance between the point and the nearest contour edge.
   * <p>
   * See below a sample output of the function where each image pixel is tested against the contour:
   * <p>
   * ![sample output](pics/pointpolygon.png)
   * <p>
   *
   * @param contour     Input contour.
   * @param pt          Point tested against the contour.
   * @param measureDist If true, the function estimates the signed distance from the point to the
   *                    nearest contour edge. Otherwise, the function only checks if the point is inside a contour or not.
   */
  def pointPolygonTest(contour: Mat, pt: Point2f, measureDist: Boolean): Double =
    opencv_imgproc.pointPolygonTest(contour, pt, measureDist)

  /** \brief Finds out if there is any intersection between two rotated rectangles.
   * <p>
   * If there is then the vertices of the intersecting region are returned as well.
   * <p>
   * Below are some examples of intersection configurations. The hatched pattern indicates the
   * intersecting region and the red vertices are returned by the function.
   * <p>
   * ![intersection examples](pics/intersection.png)
   * <p>
   *
   * @param rect1              First rectangle
   * @param rect2              Second rectangle
   * @return intersectingRegion The output array of the vertices of the intersecting region. It returns
   *                           at most 8 vertices. Stored as std::vector\<cv::Point2f\> or cv::Mat as Mx1 of type CV_32FC2.
   * @return One of #RectanglesIntersectTypes
   */
  def rotatedRectangleIntersection(rect1: RotatedRect, rect2: RotatedRect): (RectanglesIntersectType, Mat) = {
    val intersectingRegion = new Mat()
    val itypeFlag = opencv_imgproc.rotatedRectangleIntersection(rect1, rect2, intersectingRegion)
    val itype: RectanglesIntersectType = RectanglesIntersectTypes.values.find(_.flag == itypeFlag).get
    (itype, intersectingRegion)
  }

  /** \brief Creates a smart pointer to a cv::GeneralizedHoughBallard class and initializes it.
   */
  def createGeneralizedHoughBallard: GeneralizedHoughBallard =
    opencv_imgproc.createGeneralizedHoughBallard()

  /** \brief Creates a smart pointer to a cv::GeneralizedHoughGuil class and initializes it.
   */
  def createGeneralizedHoughGuil: GeneralizedHoughGuil =
    opencv_imgproc.createGeneralizedHoughGuil()


}
