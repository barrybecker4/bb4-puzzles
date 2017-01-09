// Copyright by Barry G. Becker, 2017. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.bridge.ui

import java.awt.Choice
import com.barrybecker4.puzzle.bridge.model.InitialConfiguration

/**
  * A combo box that allows the user to select the sort of people that need to cross
  *
  * @author Barry Becker
  */
object InitialConfigurationSelector {
  private val MENU_ITEMS = InitialConfiguration.CONFIGURATIONS.map(_.label)
}

final class InitialConfigurationSelector extends Choice {

  InitialConfigurationSelector.MENU_ITEMS.foreach{ add }
  select(0)

  /** @return the puzzle size for what was selected. */
  def getSelectedConfiguration: Array[Int] = InitialConfiguration.CONFIGURATIONS(getSelectedIndex).peopleSpeeds
}