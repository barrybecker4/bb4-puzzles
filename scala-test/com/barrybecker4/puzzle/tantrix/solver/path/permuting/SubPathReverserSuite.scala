// Copyright by Barry G. Becker, 2017. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.tantrix.solver.path.permuting

import com.barrybecker4.common.geometry.ByteLocation
import com.barrybecker4.puzzle.tantrix.TantrixTstUtil._
import com.barrybecker4.puzzle.tantrix.model.HexTiles.TILES
import com.barrybecker4.puzzle.tantrix.model.PathColor.PathColor
import com.barrybecker4.puzzle.tantrix.model.RotationEnum._
import com.barrybecker4.puzzle.tantrix.model.TilePlacement
import com.barrybecker4.puzzle.tantrix.solver.path.TantrixPath
import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class SubPathReverserSuite extends FunSuite with SubPathMutatorBase {

  protected def createMutator(primaryColor: PathColor) = new SubPathReverser(primaryColor)

  protected def verifyMutated1TilePath(resultPath: TantrixPath) {
    assertResult(1) { resultPath.size }
    val first = TilePlacement(TILES.getTile(2), loc(2, 1), ANGLE_60)
    val expList = Seq(first)
    assertResult(expList) { resultPath.getTilePlacements }
  }

  protected def verifyMutated2TilePath(resultPath: TantrixPath) {
    assertResult(2) { resultPath.size }
    val first = TilePlacement(TILES.getTile(3), loc(2, 1), ANGLE_180)
    val second = TilePlacement(TILES.getTile(2), loc(1, 2), ANGLE_180)
    val expList = Seq(first, second)
    assertResult(expList) { resultPath.getTilePlacements }
  }

  protected def verifyMutated3TilePath(resultPath: TantrixPath) {
    assertResult(3) { resultPath.size }
    val first = TilePlacement(TILES.getTile(2), new ByteLocation(19, 19), ANGLE_60)
    val second = TilePlacement(TILES.getTile(5), new ByteLocation(19, 20), ANGLE_180)
    val third = TilePlacement(TILES.getTile(1), new ByteLocation(19, 21), ANGLE_60)
    val expList = Seq(first, second, third)
    assertResult(expList) { resultPath.getTilePlacements }
  }

  protected def verifyMutated3aTilePath(resultPath: TantrixPath) {
    assertResult(3) { resultPath.size }
    val first = TilePlacement(TILES.getTile(4), new ByteLocation(23, 21), ANGLE_300)
    val second = TilePlacement(TILES.getTile(1), new ByteLocation(23, 20), ANGLE_240)
    val third = TilePlacement(TILES.getTile(2), new ByteLocation(24, 19), ANGLE_120)
    val expList = Seq(first, second, third)
    assertResult(expList) { resultPath.getTilePlacements }
  }
}