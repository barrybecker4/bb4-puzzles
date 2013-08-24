/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.puzzle.slidingpuzzle;

import com.barrybecker4.common.app.AppContext;
import com.barrybecker4.puzzle.common.AlgorithmEnum;
import com.barrybecker4.puzzle.common.PuzzleController;
import com.barrybecker4.puzzle.common.Refreshable;
import com.barrybecker4.puzzle.common.solver.AStarPuzzleSolver;
import com.barrybecker4.puzzle.common.solver.ConcurrentPuzzleSolver;
import com.barrybecker4.puzzle.common.solver.PuzzleSolver;
import com.barrybecker4.puzzle.common.solver.SequentialPuzzleSolver;
import com.barrybecker4.puzzle.slidingpuzzle.model.SlideMove;
import com.barrybecker4.puzzle.slidingpuzzle.model.Slider;

/**
 * Type of solver to use.
 *
 * @author Barry Becker
 */
public enum Algorithm implements AlgorithmEnum<Slider, SlideMove> {

    A_STAR_SEQUENTIAL,
    SIMPLE_SEQUENTIAL,
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
    public PuzzleSolver<Slider, SlideMove> createSolver(
            PuzzleController<Slider, SlideMove> controller, Refreshable<Slider, SlideMove> ui) {

        switch (this) {
            case A_STAR_SEQUENTIAL :
                return new AStarPuzzleSolver<Slider, SlideMove>(controller, ui);
            case SIMPLE_SEQUENTIAL :
                // this will find a solution, but not necessary the shortest path
                return new SequentialPuzzleSolver<Slider, SlideMove>(controller, ui);
            case CONCURRENT_BREADTH :
                // this will find the shortest path to a solution if one exists, but takes longer
                return new ConcurrentPuzzleSolver<Slider, SlideMove>(controller, 1.0f, ui);
            case CONCURRENT_DEPTH :
                return new ConcurrentPuzzleSolver<Slider, SlideMove>(controller, 0.12f, ui);
            case CONCURRENT_OPTIMUM :
                return new ConcurrentPuzzleSolver<Slider, SlideMove>(controller, 0.3f, ui);
        }
        return null;
    }

}
