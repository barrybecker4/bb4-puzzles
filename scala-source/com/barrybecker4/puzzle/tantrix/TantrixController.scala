// Copyright by Barry G. Becker, 2017. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.tantrix

import com.barrybecker4.common.search.Refreshable
import com.barrybecker4.puzzle.common.ui.AbstractPuzzleController
import com.barrybecker4.puzzle.tantrix.generation.MoveGenerator
import com.barrybecker4.puzzle.tantrix.model.{HexTiles, TantrixBoard, TilePlacement}
import com.barrybecker4.puzzle.tantrix.solver.SIMPLE_SEQUENTIAL
import com.barrybecker4.puzzle.tantrix.solver.path.{PathEvaluator, TantrixPath}

import scala.collection.Seq

/**
  * The controller allows the solver to do its thing by providing the PuzzleController api.
  *
  * The generic solvers (sequential and concurrent) expect the first class param
  * to represent the state of a board, and the TilePlacement (second param)
  * to represent a move. The way a move is applied is simply to add the piece to the
  * end of the current list.
  *
  * @author Barry Becker
  */
object TantrixController {
  private val MIN_NUM_TILES = 3
}

class TantrixController(val ui: Refreshable[TantrixBoard, TilePlacement])

/**
  * Creates a new instance of the Controller
  */
  extends AbstractPuzzleController[TantrixBoard, TilePlacement](ui) {
  algorithm_ = SIMPLE_SEQUENTIAL
  private var numTiles = TantrixController.MIN_NUM_TILES
  private val evaluator = new PathEvaluator

  def setNumTiles(numTiles: Int) {
    this.numTiles = numTiles
  }

  def initialState: TantrixBoard = {
    //MathUtil.RANDOM.setSeed(1);
    new TantrixBoard(new HexTiles().createRandomList(numTiles))
  }

  /**
    * @return true if there is a loop of the primary color and all the
    *         secondary color path connections match.
    */
  def isGoal(position: TantrixBoard): Boolean = position.isSolved

  def legalTransitions(position: TantrixBoard): Seq[TilePlacement] = new MoveGenerator(position).generateMoves

  def transition(position: TantrixBoard, move: TilePlacement): TantrixBoard = position.placeTile(move)

  /**
    * @return estimate of the cost to reach the goal from the specified position
    */
  override def distanceFromGoal(position: TantrixBoard): Int = {
    val path = new TantrixPath(position.tantrix, position.primaryColor)
    val fitness = evaluator.evaluateFitness(path)
    (10.0 * Math.max(0, PathEvaluator.SOLVED_THRESH - fitness)).toInt
  }
}