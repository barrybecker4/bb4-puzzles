// Copyright by Barry G. Becker, 2017. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.tantrix.model.analysis.verfication

import com.barrybecker4.puzzle.tantrix.model.{HexTile, TantrixBoard, TilePlacement}

/**
  * Used to determine whether or not a given tantrix is a loop.
  *
  * @param board the tantrix state to test for solution.
  * @author Barry Becker
  */
class LoopDetector(var board: TantrixBoard) {
  /**
    * True if loop of the primary color.
    * Does not check the consistency of secondary paths.
    *
    * @return true if solved.
    */
  def hasLoop: Boolean = {
    if (board.unplacedTiles.nonEmpty) return false
    var numVisited = 0
    val lastTilePlaced = board.getLastTile
    var currentTile: Option[TilePlacement] = Some(lastTilePlaced)
    var previousTile: Option[TilePlacement] = None
    var nextTile: Option[TilePlacement] = None
    do {
      nextTile = findNeighborTile(currentTile.get, previousTile)
      previousTile = currentTile
      currentTile = nextTile
      numVisited += 1
    } while (currentTile.isDefined && currentTile.get != lastTilePlaced)
    numVisited == board.numTiles && currentTile.contains(lastTilePlaced)
  }

  /**
    * Loop through the edges until we find the primary color.
    * If it does not direct us back to where we came from then go that way.
    *
    * @param currentPlacement where we are now
    * @param previousTile     where we were
    * @return the next tile in the path if there is one. Otherwise null.
    */
  private def findNeighborTile(currentPlacement: TilePlacement,
                               previousTile: Option[TilePlacement]): Option[TilePlacement] = {
    for (i <- 0 until HexTile.NUM_SIDES) {
        val color = currentPlacement.getPathColor(i)
        if (color == board.primaryColor) {
          val nbr = board.getNeighbor(currentPlacement, i)
          if (nbr.isDefined && nbr.get != previousTile.get && (nbr.get.getPathColor(i + 3) == color)) return nbr
        }
    }
    None
  }
}
