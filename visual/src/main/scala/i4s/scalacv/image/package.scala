package i4s.scalacv

import org.bytedeco.opencv.opencv_core.UMat

package object image {
  type Image = UMat

  object Ops extends Colors with Drawing with Filters with Shapes with Transforms

}
