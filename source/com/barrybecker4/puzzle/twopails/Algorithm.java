/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.puzzle.twopails;

import com.barrybecker4.common.app.AppContext;
import com.barrybecker4.puzzle.common.AlgorithmEnum;
import com.barrybecker4.puzzle.common.PuzzleController;
import com.barrybecker4.puzzle.common.Refreshable;
import com.barrybecker4.puzzle.common.solver.AStarPuzzleSolver;
import com.barrybecker4.puzzle.common.solver.ConcurrentPuzzleSolver;
import com.barrybecker4.puzzle.common.solver.PuzzleSolver;
import com.barrybecker4.puzzle.common.solver.SequentialPuzzleSolver;
import com.barrybecker4.puzzle.twopails.model.Pails;
import com.barrybecker4.puzzle.twopails.model.PourOperation;

/**
 * Type of solver to use.
 *
 * @author Barry Becker
 */
public enum Algorithm implements AlgorithmEnum<Pails, PourOperation> {

    SIMPLE_SEQUENTIAL,
    A_STAR_SEQUENTIAL,
    CONCURRENT_BREADTH,
    CONCURRENT_DEPTH,
    CONCURRENT_OPTIMUM;

    private String label;

    /**
     * Private constructor
     */
    Algorithm() {
        this.label = AppContext.getLabel(this.name());
    }

    @Override
    public String getLabel() {
        return label;
    }

    /**
     * Create an instance of the algorithm given the controller and a refreshable.
     */
    @Override
    public PuzzleSolver<Pails, PourOperation> createSolver(
            PuzzleController<Pails, PourOperation> controller, Refreshable<Pails, PourOperation> ui) {

        switch (this) {
            case SIMPLE_SEQUENTIAL :
                // this will find a solution, but not necessary the shortest path
                return new SequentialPuzzleSolver<Pails, PourOperation>(controller, ui);
            case A_STAR_SEQUENTIAL :
                return new AStarPuzzleSolver<Pails, PourOperation>(controller, ui);
            case CONCURRENT_BREADTH :
                // this will find the shortest path to a solution if one exists, but takes longer
                return new ConcurrentPuzzleSolver<Pails, PourOperation>(controller, 1.0f, ui);
            case CONCURRENT_DEPTH :
                return new ConcurrentPuzzleSolver<Pails, PourOperation>(controller, 0.12f, ui);
            case CONCURRENT_OPTIMUM :
                return new ConcurrentPuzzleSolver<Pails, PourOperation>(controller, 0.3f, ui);
        }
        return null;
    }

}
