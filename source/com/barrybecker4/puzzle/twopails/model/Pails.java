/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.puzzle.twopails.model;

/**
 * Immutable representation of the two Pails and the amount of liquid they each contain at the moment.
 * @author Barry Becker
 */
public class Pails {

    private PailParams params;
    private byte fill1;
    private byte fill2;

    /**
     * Constructor to create two empty pails.
     */
    public Pails(PailParams params) {
        this.params = params;
        fill1 = 0;
        fill2 = 0;
    }

    /**
     * Copy constructor.
     */
    public Pails(Pails pails) {
        params = pails.params;
        fill1 = pails.fill1;
        fill2 = pails.fill2;
    }

    public byte getFill1() {
        return fill1;
    }
    public byte getFill2() {
        return fill2;
    }

    boolean pail1HasRoom() {
        return fill1 < params.getPail1Size();
    }
    boolean pail2HasRoom() {
        return fill1 < params.getPail2Size();
    }

    public PailParams getParams() {
        return params;
    }

    /**
     * creates a new Pails by applying a move to another Pails.
     * Does not violate immutability.
     */
    public Pails doMove(PourOperation move) {
        return new Pails(this, move, false);
    }

    /**
     *
     * @param pos current state
     * @param move transition to apply to it
     * @param undo if true, then undoes the transition rather than applying it.
     */
    public Pails(Pails pos, PourOperation move, boolean undo) {
        this(pos);
        applyMove(move);
    }

    private void applyMove(PourOperation move) {
        switch (move.getAction()) {
            case FILL :
        }
    }

    /**
     * @return true if either container has exactly the target measure.
     */
    public boolean isSolved() {

        byte target = params.getTargetMeasureSize();
        return fill1 == target || fill2 == target;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Pails:");
        builder.append('[').append(fill1).append(" ").append(fill2).append(']');
        return builder.toString();
    }
}
