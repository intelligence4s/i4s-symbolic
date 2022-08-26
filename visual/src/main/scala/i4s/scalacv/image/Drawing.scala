package i4s.scalacv.image

import i4s.scalacv.image.constants.HersheyFonts.HersheyFont
import i4s.scalacv.image.constants.{LineTypes, MarkerTypes}
import i4s.scalacv.image.constants.LineTypes.LineType
import i4s.scalacv.image.constants.MarkerTypes.MarkerType
import org.bytedeco.javacpp.Pointer
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core._

object Drawing extends Drawing

trait Drawing {
  import i4s.scalacv.TypeConverters._

  /** Calculates the width and height of a text string.
   * <p>
   * The function cv::getTextSize calculates and returns the size of a box that contains the specified text.
   * That is, the following code renders some text, the tight box surrounding it, and the baseline: :
   *
   * <pre>* String text &#61; "Funny text inside the box";
   * int fontFace &#61; FONT_HERSHEY_SCRIPT_SIMPLEX;
   * double fontScale &#61; 2;
   * int thickness &#61; 3;
   *
   * UMat img(600, 800, CV_8UC3, Scalar::all(0));
   *
   * int baseline&#61;0;
   * Size textSize &#61; getTextSize(text, fontFace,
   * fontScale, thickness, &baseline);
   * baseline +&#61; thickness;
   *
   * // center the text
   * Point textOrg((img.cols - textSize.width)/2,
   * (img.rows + textSize.height)/2);
   *
   * // draw the box
   * rectangle(img, textOrg + Point(0, baseline),
   * textOrg + Point(textSize.width, -textSize.height),
   * Scalar(0,0,255));
   * // ... and the baseline first
   * line(img, textOrg + Point(0, thickness),
   * textOrg + Point(textSize.width, thickness),
   * Scalar(0, 0, 255));
   *
   * // then put the text itself
   * putText(img, text, textOrg, fontFace, fontScale,
   * Scalar::all(255), thickness, 8);
   * }</pre>
   * <p>
   * */

  def getTextSize(text: String, fontFace: HersheyFont, italic: Boolean, fontScale: Double, thickness: Int, baseLine: Int): Size =
    opencv_imgproc.getTextSize(text, fontFace.face(italic), fontScale, thickness, baseLine.asPointer)

  def getTextSize(text: String, fontFace: HersheyFont, italic: Boolean, fontScale: Double, thickness: Int, baseLine: Array[Int]): Size =
    opencv_imgproc.getTextSize(text, fontFace.face(italic), fontScale, thickness, baseLine)

  /** Calculates the font-specific size to use to achieve a given height in pixels.
   * <p>
   *
   * @param fontFace    Font to use, see cv::HersheyFonts.
   * @param pixelHeight Pixel height to compute the fontScale for
   * @param thickness   Thickness of lines used to render the text.See putText for details.
   * @return The fontSize to use for cv::putText
   *         <p>
   * @see cv::putText
   */
  def getFontScaleFromHeight(fontFace: HersheyFont, italic: Boolean, pixelHeight: Int, thickness: Int): Double =
    opencv_imgproc.getFontScaleFromHeight(fontFace.face(italic), pixelHeight, thickness)

  def getFontScaleFromHeight(fontFace: HersheyFont, italic: Boolean, pixelHeight: Int): Double =
    getFontScaleFromHeight(fontFace, italic, pixelHeight, thickness = 1)

  /** \brief Clips the line against the image rectangle.
   * <p>
   * The function cv::clipLine calculates a part of the line segment that is entirely within the specified
   * rectangle. It returns false if the line segment is completely outside the rectangle. Otherwise,
   * it returns true .
   *
   * @param imgSize Image size. The image rectangle is Rect(0, 0, imgSize.width, imgSize.height) .
   * @param pt1     First line point.
   * @param pt2     Second line point.
   */
  def clipLine(imgSize: Size, pt1: Point, pt2: Point): Boolean =
    opencv_imgproc.clipLine(imgSize, pt1, pt2)

  /** \overload
   *
   * @param imgSize Image size. The image rectangle is Rect(0, 0, imgSize.width, imgSize.height) .
   * @param pt1     First line point.
   * @param pt2     Second line point.
   */

  /** \overload
   *
   * @param imgRect Image rectangle.
   * @param pt1     First line point.
   * @param pt2     Second line point.
   */
  def clipLine(imgRect: Rect, pt1: Point, pt2: Point): Boolean =
    opencv_imgproc.clipLine(imgRect, pt1, pt2)

  /** \brief Approximates an elliptic arc with a polyline.
   * <p>
   * The function ellipse2Poly computes the vertices of a polyline that approximates the specified
   * elliptic arc. It is used by #ellipse. If {@code arcStart} is greater than {@code arcEnd}, they are swapped.
   * <p>
   *
   * @param center   Center of the arc.
   * @param axes     Half of the size of the ellipse main axes. See #ellipse for details.
   * @param angle    Rotation angle of the ellipse in degrees. See #ellipse for details.
   * @param arcStart Starting angle of the elliptic arc in degrees.
   * @param arcEnd   Ending angle of the elliptic arc in degrees.
   * @param delta    Angle between the subsequent polyline vertices. It defines the approximation
   *                 accuracy.
   * @param pts      Output vector of polyline vertices.
   */
  def ellipse2Poly(center: Point, axes: Size, angle: Int, arcStart: Int, arcEnd: Int, delta: Int): PointVector = {
    val pointVector = new PointVector()
    opencv_imgproc.ellipse2Poly(center, axes, angle, arcStart, arcEnd, delta, pointVector)
    pointVector
  }

  /** \overload
   *
   * @param center   Center of the arc.
   * @param axes     Half of the size of the ellipse main axes. See #ellipse for details.
   * @param angle    Rotation angle of the ellipse in degrees. See #ellipse for details.
   * @param arcStart Starting angle of the elliptic arc in degrees.
   * @param arcEnd   Ending angle of the elliptic arc in degrees.
   * @param delta    Angle between the subsequent polyline vertices. It defines the approximation accuracy.
   * @param pts      Output vector of polyline vertices.
   */
  def ellipse2Poly(center: Point2d, axes: Size2d, angle: Int, arcStart: Int, arcEnd: Int, delta: Int): Point2dVector = {
    val pt2dVector = new Point2dVector()
    opencv_imgproc.ellipse2Poly(center, axes, angle, arcStart, arcEnd, delta, pt2dVector)
    pt2dVector
  }


  implicit class ImageDrawing(image: Image) {
    /** Draws a circle.
     * <p>
     * The function cv::circle draws a simple or filled circle with a given center and radius.
     *
     * @param center    Center of the circle.
     * @param radius    Radius of the circle.
     * @param color     Circle color.
     * @param thickness Thickness of the circle outline, if positive. Negative values, like #FILLED,
     *                  mean that a filled circle is to be drawn.
     * @param lineType  Type of the circle boundary. See #LineTypes
     * @param shift     Number of fractional bits in the coordinates of the center and in the radius value.
     */
    def circle(center: Point, radius: Int, color: Scalar, thickness: Int, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.circle(image, center, radius, color, thickness, lineType.id, shift)

    def circle(center: Point, radius: Int, color: Scalar): Unit =
      circle(center, radius, color, thickness = 1, LineTypes.Line8, shift = 0)

    /** Draws a simple or thick elliptic arc or fills an ellipse sector.
     * <p>
     * The function cv::ellipse with more parameters draws an ellipse outline, a filled ellipse, an elliptic
     * arc, or a filled ellipse sector. The drawing code uses general parametric form.
     * A piecewise-linear curve is used to approximate the elliptic arc
     * boundary. If you need more control of the ellipse rendering, you can retrieve the curve using
     * #ellipse2Poly and then render it with #polylines or fill it with #fillPoly. If you use the first
     * variant of the function and want to draw the whole ellipse, not an arc, pass {@code startAngle=0} and
     * {@code endAngle=360}. If {@code startAngle} is greater than {@code endAngle}, they are swapped. The figure below explains
     * the meaning of the parameters to draw the blue arc.
     * <p>
     * ![Parameters of Elliptic Arc](pics/ellipse.svg)
     * <p>
     *
     * @param center     Center of the ellipse.
     * @param axes       Half of the size of the ellipse main axes.
     * @param angle      Ellipse rotation angle in degrees.
     * @param startAngle Starting angle of the elliptic arc in degrees.
     * @param endAngle   Ending angle of the elliptic arc in degrees.
     * @param color      Ellipse color.
     * @param thickness  Thickness of the ellipse arc outline, if positive. Otherwise, this indicates that
     *                   a filled ellipse sector is to be drawn.
     * @param lineType   Type of the ellipse boundary. See #LineTypes
     * @param shift      Number of fractional bits in the coordinates of the center and values of axes.
     */
    def ellipse(center: Point, axes: Size, angle: Double, startAngle: Double, endAngle: Double, color: Scalar, thickness: Int, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.ellipse(image,center,axes,angle,startAngle,endAngle,color,thickness,lineType.id,shift)

    def ellipse(center: Point, axes: Size, angle: Double, startAngle: Double, endAngle: Double, color: Scalar): Unit =
      ellipse(center,axes,angle,startAngle,endAngle,color,thickness = 1,LineTypes.Line8,shift = 0)

    /**
     * @param box       Alternative ellipse representation via RotatedRect. This means that the function draws
     *                  an ellipse inscribed in the rotated rectangle.
     * @param color     Ellipse color.
     * @param thickness Thickness of the ellipse arc outline, if positive. Otherwise, this indicates that
     *                  a filled ellipse sector is to be drawn.
     * @param lineType  Type of the ellipse boundary. See #LineTypes
     */
    def ellipse(box: RotatedRect, color: Scalar, thickness: Int, lineType: LineType): Unit =
      opencv_imgproc.ellipse(image,box,color,thickness,lineType.id)

    def ellipse(box: RotatedRect, color: Scalar): Unit =
      ellipse(box,color,thickness = 1,LineTypes.Line8)


    /** Draws a simple, thick, or filled up-right rectangle.
     * <p>
     * The function cv::rectangle draws a rectangle outline or a filled rectangle
     * <p>
     *
     * @param point1    Vertex of the rectangle.
     * @param point2    Vertex of the rectangle opposite to pt1 .
     * @param color     Rectangle color or brightness (grayscale image).
     * @param thickness Thickness of lines that make up the rectangle. Negative values, like #FILLED,
     *                  mean that the function has to draw a filled rectangle.
     * @param lineType  Type of the line. See #LineTypes
     * @param shift     Number of fractional bits in the point coordinates.
     */
    def rectangle(point1: Point, point2: Point, color: Scalar, lineType: LineType, thickness: Int, shift: Int): Unit =
      opencv_imgproc.rectangle(image, point1, point2, color, thickness, lineType.id, shift)

    def rectangle(point1: Point, point2: Point, color: Scalar): Unit =
      rectangle(point1, point2, color, LineTypes.Line8, thickness = 1, shift = 0)

    /** Draws a simple, thick, or filled up-right rectangle.
     * <p>
     * The function cv::rectangle draws a rectangle outline or a filled rectangle whose two opposite corners
     * are pt1 and pt2.
     * <p>
     *
     * @param rect      Bounding rectangle
     * @param color     Rectangle color or brightness (grayscale image).
     * @param thickness Thickness of lines that make up the rectangle. Negative values, like #FILLED,
     *                  mean that the function has to draw a filled rectangle.
     * @param lineType  Type of the line. See #LineTypes
     * @param shift     Number of fractional bits in the point coordinates.
     */
    def rectangle(rect: Rect, color: Scalar, lineType: LineType, thickness: Int, shift: Int): Unit =
      opencv_imgproc.rectangle(image, rect, color, thickness, lineType.id, shift)

    def rectangle(rect: Rect, color: Scalar): Unit =
      rectangle(rect,color,LineTypes.Line8,thickness = 1,shift = 0)

    /** Draws a marker on a predefined position in an image.
     * <p>
     * The function cv::drawMarker draws a marker on a given position in the image. For the moment several
     * marker types are supported, see #MarkerTypes for more information.
     * <p>
     * @param position   The point where the crosshair is positioned.
     * @param color      Line color.
     * @param markerType The specific type of marker you want to use, see #MarkerTypes
     * @param thickness  Line thickness.
     * @param lineType   Type of the line, See #LineTypes
     * @param markerSize The length of the marker axis [default = 20 pixels]
     */
    def drawMarker(position: Point, color: Scalar, markerType: MarkerType, markerSize: Int, thickness: Int, lineType: LineType): Unit =
      opencv_imgproc.drawMarker(image,position,color,markerType.id,markerSize,thickness,lineType.id)

    def drawMarker(position: Point, color: Scalar): Unit =
      drawMarker(position,color,MarkerTypes.Cross,markerSize = 20,thickness = 1,LineTypes.Line8)

    /** Draws a text string.
     * <p>
     * The function cv::putText renders the specified text string in the image. Symbols that cannot be rendered
     * using the specified font are replaced by question marks. See #getTextSize for a text rendering code
     * example.
     * <p>
     * @param text             Text string to be drawn.
     * @param org              Bottom-left corner of the text string in the image.
     * @param fontFace         Font type, see #HersheyFonts.
     * @param fontScale        Font scale factor that is multiplied by the font-specific base size.
     * @param color            Text color.
     * @param thickness        Thickness of the lines used to draw a text.
     * @param lineType         Line type. See #LineTypes
     * @param bottomLeftOrigin When true, the image data origin is at the bottom-left corner. Otherwise,
     *                         it is at the top-left corner.
     */
    def putText(text: String, org: Point, fontFace: HersheyFont, fontScale: Double, italic: Boolean, color: Scalar, thickness: Int, lineType: LineType, bottomLeftOrigin: Boolean): Unit =
      opencv_imgproc.putText(image,text,org,fontFace.face(italic),fontScale,color,thickness,lineType.id,bottomLeftOrigin)

    def putText(text: String, org: Point, fontFace: HersheyFont, fontScale: Double, italic: Boolean, color: Scalar): Unit =
      putText(text,org,fontFace,fontScale,italic,color,1,LineTypes.Line8,bottomLeftOrigin = false)

    /** Fills a convex polygon.
     * <p>
     * The function cv::fillConvexPoly draws a filled convex polygon. This function is much faster than the
     * function #fillPoly . It can fill not only convex polygons but any monotonic polygon without
     * self-intersections, that is, a polygon whose contour intersects every horizontal line (scan line)
     * twice at the most (though, its top-most and/or the bottom edge could be horizontal).
     * <p>
     * @param img      Image.
     * @param points   Polygon vertices.
     * @param color    Polygon color.
     * @param lineType Type of the polygon boundaries. See #LineTypes
     * @param shift    Number of fractional bits in the vertex coordinates.
     */
    def fillConvexPoly(points: UMat, color: Scalar, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.fillConvexPoly(image,points,color,lineType.id,shift)

    def fillConvexPoly(points: UMat, color: Scalar): Unit =
      fillConvexPoly(points,color,LineTypes.Line8,shift = 0)

    def fillConvexPoly(pts: Point, npts: Int, color: Scalar, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.fillConvexPoly(image,pts,npts,color,lineType.id,shift)

    def fillConvexPoly(pts: Point, npts: Int, color: Scalar): Unit =
      fillConvexPoly(pts,npts,color,LineTypes.Line8,shift = 0)

    /** \example samples/cpp/tutorial_code/ImgProc/basic_drawing/Drawing_1.cpp
     * An example using drawing functions
     * Check \ref tutorial_random_generator_and_text "the corresponding tutorial" for more details
     */

    /** \brief Fills the area bounded by one or more polygons.
     * <p>
     * The function cv::fillPoly fills an area bounded by several polygonal contours. The function can fill
     * complex areas, for example, areas with holes, contours with self-intersections (some of their
     * parts), and so forth.
     * <p>
     * @param img      Image.
     * @param pts      Array of polygons where each polygon is represented as an array of points.
     * @param color    Polygon color.
     * @param lineType Type of the polygon boundaries. See #LineTypes
     * @param shift    Number of fractional bits in the vertex coordinates.
     * @param offset   Optional offset of all points of the contours.
     */
    def fillPoly(pts: MatVector, color: Scalar, lineType: LineType, shift: Int, offset: Point): Unit =
      opencv_imgproc.fillPoly(image,pts,color,lineType.id,shift,offset)

    def fillPoly(pts: MatVector, color: Scalar): Unit =
      fillPoly(pts, color, LineTypes.Line8, shift = 0, offset = new Point())

    def fillPoly(pts: Array[_ <: Pointer], npts: Int, ncontours: Int, color: Scalar, lineType: LineType, shift: Int, offset: Point): Unit = {
      opencv_imgproc.fillPoly(image.getMat(0),pts.asPointer,npts.asPointer,ncontours,color,lineType.id,shift,offset)
    }

    def fillPoly(pts: Point, npts: Int, ncontours: Int, color: Scalar, lineType: LineType, shift: Int, offset: Point): Unit =
      opencv_imgproc.fillPoly(image,pts,npts.asPointer,ncontours,color,lineType.id,shift,offset)

    def fillPoly(pts: Point, npts: Int, ncontours: Int, color: Scalar): Unit =
      fillPoly(pts, npts, ncontours, color, LineTypes.Line8, shift = 0, offset = new Point())

    def fillPoly(pts: Point, npts: Array[Int], ncontours: Int, color: Scalar, lineType: LineType, shift: Int, offset: Point): Unit =
      opencv_imgproc.fillPoly(image, pts, npts, ncontours, color, lineType.id, shift, offset)

    def fillPoly(pts: Point, npts: Array[Int], ncontours: Int, color: Scalar): Unit =
      fillPoly(pts, npts, ncontours, color, LineTypes.Line8, shift = 0, offset = new Point())

    /** \brief Draws several polygonal curves.
     * <p>
     * @param img       Image.
     * @param pts       Array of polygonal curves.
     * @param isClosed  Flag indicating whether the drawn polylines are closed or not. If they are closed,
     *                  the function draws a line from the last vertex of each curve to its first vertex.
     * @param color     Polyline color.
     * @param thickness Thickness of the polyline edges.
     * @param lineType  Type of the line segments. See #LineTypes
     * @param shift     Number of fractional bits in the vertex coordinates.
     *                  <p>
     *                  The function cv::polylines draws one or more polygonal curves.
     */
    def polylines(pts: MatVector, isClosed: Boolean, color: Scalar, thickness: Int, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.polylines(image,pts,isClosed,color,thickness,lineType.id,shift)

    def polylines(pts: MatVector, isClosed: Boolean, color: Scalar): Unit =
      polylines(pts,isClosed,color,thickness = 1,LineTypes.Line8,shift = 0)

    def polylines(pts: Array[_ <: Pointer], npts: Int, ncontours: Int, isClosed: Boolean, color: Scalar, thickness: Int, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.polylines(image.getMat(0),pts.asPointer,npts.asPointer,ncontours,isClosed,color,thickness,lineType.id,shift)

    def polylines(pts: Point, npts: Int, ncontours: Int, isClosed: Boolean, color: Scalar, thickness: Int, lineType: LineType, shift: Int): Unit =
      opencv_imgproc.polylines(image,pts,npts.asPointer,ncontours,isClosed,color,thickness,lineType.id,shift)

    def polylines(pts: Point, npts: Int, ncontours: Int, isClosed: Boolean, color: Scalar): Unit =
      polylines(pts, npts, ncontours, isClosed, color, thickness = 1, LineTypes.Line8, shift = 1)

    def polylines(pts: Point, npts: Array[Int], ncontours: Int, isClosed: Boolean, color: Scalar, thickness: Int, lineType: LineTypes.LineType, shift: Int): Unit =
      opencv_imgproc.polylines(image,pts,npts,ncontours,isClosed,color,thickness,lineType.id,shift)

    def polylines(pts: Point, npts: Array[Int], ncontours: Int, isClosed: Boolean, color: Scalar): Unit =
      polylines(pts,npts, ncontours, isClosed, color, thickness = 1, lineType = LineTypes.Line8, shift = 0)

    /** \example samples/cpp/contours2.cpp
     * An example program illustrates the use of cv::findContours and cv::drawContours
     * \image html WindowsQtContoursOutput.png "Screenshot of the program"
     */

    /** \example samples/cpp/segment_objects.cpp
     * An example using drawContours to clean up a background segmentation result
     */

    /** \brief Draws contours outlines or filled contours.
     * <p>
     * The function draws contour outlines in the image if <pre>\texttt{thickness</pre> \ge 0} or fills the area
     * bounded by the contours if <pre>\texttt{thickness</pre><0} . The example below shows how to retrieve
     * connected components from the binary image and label them: :
     * \include snippets/imgproc_drawContours.cpp
     * <p>
     * @param image      Destination image.
     * @param contours   All the input contours. Each contour is stored as a point vector.
     * @param contourIdx Parameter indicating a contour to draw. If it is negative, all the contours are drawn.
     * @param color      Color of the contours.
     * @param thickness  Thickness of lines the contours are drawn with. If it is negative (for example,
     *                   thickness=#FILLED ), the contour interiors are drawn.
     * @param lineType   Line connectivity. See #LineTypes
     * @param hierarchy  Optional information about hierarchy. It is only needed if you want to draw only
     *                   some of the contours (see maxLevel ).
     * @param maxLevel   Maximal level for drawn contours. If it is 0, only the specified contour is drawn.
     *                   If it is 1, the function draws the contour(s) and all the nested contours. If it is 2, the function
     *                   draws the contours, all the nested contours, all the nested-to-nested contours, and so on. This
     *                   parameter is only taken into account when there is hierarchy available.
     * @param offset Optional contour shift parameter. Shift all the drawn contours by the specified
     *               <pre>\texttt{offset</pre>=(dx,dy)} .
     *               \note When thickness=#FILLED, the function is designed to handle connected components with holes correctly
     *               even when no hierarchy data is provided. This is done by analyzing all the outlines together
     *               using even-odd rule. This may give incorrect results if you have a joint collection of separately retrieved
     *               contours. In order to solve this problem, you need to call #drawContours separately for each sub-group
     *               of contours, or iterate over the collection using contourIdx parameter.
     */
    def drawContours(contours: MatVector, contourIdx: Int, color: Scalar, thickness: Int, lineType: LineType, hierarchy: UMat, maxLevel: Int, offset: Point): Unit =
      opencv_imgproc.drawContours(image,contours,contourIdx,color,thickness,lineType.id,hierarchy,maxLevel,offset)

    def drawContours(contours: MatVector, contourIdx: Int, color: Scalar): Unit =
      drawContours(contours,contourIdx,color,thickness = 1,LineTypes.Line8,hierarchy = new UMat(),maxLevel = Int.MaxValue,offset = new Point())

  }
}
