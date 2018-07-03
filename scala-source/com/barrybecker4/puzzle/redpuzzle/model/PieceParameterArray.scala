// Copyright by Barry G. Becker, 2017. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.redpuzzle.model

import com.barrybecker4.common.math.MathUtil
import com.barrybecker4.optimization.parameter.{ParameterArray, PermutedParameterArray}

import scala.util.Random

/**
  * The parameter array to use when searching (using optimization) to find a red puzzle solution.
  * It has some unique properties.
  * For example, when finding a random neighbor, we consider rotations of
  * non-fitting pieces rather than just offsetting the number by some random amount.
  * @author Barry Becker
  */
object PieceParameterArray {
  private val SAMPLE_POPULATION_SIZE = 400

  /** The larger this number, the less we care about how many fits there are when finding the swap probability */
  private val PROB_SOFTENER = 0.8

  /** @param pieces piece list to find probabilities for.
    * @return probability used to determine if we do a piece swap.
    *         Pieces that already fit have a low probability of being swapped.
    */
  private def findSwapProbabilities(pieces: PieceList): IndexedSeq[Double] =
    for (i <- 0 until pieces.numTotal) yield 1.0 / (PROB_SOFTENER + pieces.getNumFits(i))
}

class PieceParameterArray(var pieces: PieceList, val rnd: Random = MathUtil.RANDOM)
  extends PermutedParameterArray {

  override def copy: PieceParameterArray = {
    val copy: PieceParameterArray = new PieceParameterArray(pieces, rnd)
    copy.setFitness(this.getFitness)
    copy
  }

  override def getSamplePopulationSize: Int = PieceParameterArray.SAMPLE_POPULATION_SIZE

  /** We want to find a potential solution close to the one that we have,
    * with minimal disturbance of the pieces that are already fit.
    * @param radius proportional to the number of pieces that you want to vary.
    * @return the random nbr (potential solution).
    */
  override def getRandomNeighbor(radius: Double): PermutedParameterArray = {

    var pieceList: PieceList = new PieceList(pieces)
    val numSwaps: Int = Math.max(1.0,  radius * 2.0).toInt
    //println(s"numSwaps = $numSwaps rad= $radius")

    for (i <- 0 until numSwaps)
      pieceList = doPieceSwap(pieceList)

    assert (pieceList.size == pieceList.numTotal)

    // Make a pass over all the pieces. If rotating a piece leads to more fits, then do it.
    for (k <- 0 until pieceList.size) {
      var numFits: Int = pieceList.getNumFits(k)
      var bestNumFits: Int = numFits
      var bestRot: Int = 1
      for (i <- 1 to 3) {
        val plist = pieceList.rotate(k, i)
        numFits = plist.getNumFits(k)
        if (numFits > bestNumFits) {
          bestNumFits = numFits
          bestRot = 2 + i
        }
      }
      // rotate the piece to position of best fit.
      pieceList = pieceList.rotate(k, bestRot)
    }
    new PieceParameterArray(pieceList)
  }

  /** Exchange 2 pieces, even if it means the fitness gets worse.
    * Skew away from selecting pieces that have fits.
    * The probability of selecting pieces that already have fits is sharply reduced.
    * The denominator is 1 + the number of fits that the piece has.
    */
  def doPieceSwap(pieces: PieceList): PieceList = {
    val swapProbabilities: IndexedSeq[Double] = PieceParameterArray.findSwapProbabilities(pieces)
    var totalProb: Double = 0

    for (i <- 0 until pieces.numTotal) totalProb += swapProbabilities(i)

    val p1: Int = getPieceFromProb(totalProb * rnd.nextDouble, swapProbabilities)
    var p2: Int = 0

    do {
      p2 = getPieceFromProb(totalProb * rnd.nextDouble, swapProbabilities)
    } while (p2 == p1)

    pieces.doSwap(p1, p2)
  }

  /** @param p some value between 0 and the totalProbability (i.e. 100%).
    * @return the index of the piece that was selected given the probability.
    */
  def getPieceFromProb(p: Double, probabilities: IndexedSeq[Double]): Int = {
    var total: Double = 0
    var i: Int = 0
    while (total < p && i < pieces.numTotal) {
      total += probabilities(i)
      i += 1
    }
    i - 1
  }

  /** @return get a completely random solution in the parameter space.*/
  override def getRandomSample: ParameterArray = {
    val pl: PieceList = new PieceList(pieces)
    val shuffledPieces: PieceList = pl.shuffle
    new PieceParameterArray (shuffledPieces)
  }

  override def setPermutation(indices: List[Integer]): Unit = {
    var newParams: PieceList = pieces
    indices.foreach(p => {
      newParams = newParams.add(pieces.get(p))
    })
    pieces = newParams
  }

  /** @return the piece list corresponding to the encoded parameter array */
  def getPieceList: PieceList = pieces

  override def canEqual(other: Any): Boolean = other.isInstanceOf[PieceParameterArray]

  override def equals(other: Any): Boolean = other match {
    case that: PieceParameterArray =>
      (that canEqual this) && (pieces == that.pieces) && getFitness == that.getFitness
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(pieces, getFitness)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  /** @return the number of parameters in the array.*/
  override def size: Int = pieces.size

  override def toString: String = pieces.toString

  /** @return the parameters in a string of Comma Separated Values. */
  override def toCSVString: String = toString
}