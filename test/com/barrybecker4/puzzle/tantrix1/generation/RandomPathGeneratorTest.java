// Copyright by Barry G. Becker, 2012. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.tantrix1.generation;

import com.barrybecker4.common.geometry.ByteLocation;
import com.barrybecker4.common.math.MathUtil;
import com.barrybecker4.puzzle.tantrix1.model.PathColor;
import com.barrybecker4.puzzle.tantrix1.model.Rotation;
import com.barrybecker4.puzzle.tantrix1.model.TilePlacement;
import com.barrybecker4.puzzle.tantrix1.model.TilePlacementList;
import com.barrybecker4.puzzle.tantrix1.solver.path.TantrixPath;
import org.junit.Before;
import org.junit.Test;

import static com.barrybecker4.puzzle.tantrix1.TantrixTstUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Barry Becker
 */
public class RandomPathGeneratorTest {

    /** instance under test */
    private RandomPathGenerator pathGenerator;

    @Before
    public void setUp() {
        MathUtil.RANDOM.setSeed(0);
    }

    @Test
    public void test3TilesPathGen() {
        pathGenerator = new RandomPathGenerator(place3UnsolvedTiles());
        TantrixPath rPath = pathGenerator.generateRandomPath();

        assertEquals("Unexpected length for randomly generated path.", 3, rPath.size());

        TilePlacementList tiles = new TilePlacementList(
                new TilePlacement(TILES.getTile(2), new ByteLocation(22, 20), Rotation.ANGLE_0),
                new TilePlacement(TILES.getTile(1), new ByteLocation(21, 21), Rotation.ANGLE_0),
                new TilePlacement(TILES.getTile(3), new ByteLocation(22, 21), Rotation.ANGLE_180));
        TantrixPath expectedPath = new TantrixPath(tiles, PathColor.YELLOW);

        assertEquals("Unexpected path.", expectedPath, rPath);
    }

    @Test
    public void test6TilesPathGen() {
        pathGenerator = new RandomPathGenerator(place3of6UnsolvedTiles());
        TantrixPath rPath = pathGenerator.generateRandomPath();

        assertEquals("Unexpected length for randomly generated path.", 6, rPath.size());

        /* This is how it was.
        TilePlacementList tiles = new TilePlacementList(
                new TilePlacement(TILES.getTile(2), new ByteLocation(20, 21), Rotation.ANGLE_60),
                new TilePlacement(TILES.getTile(3), new ByteLocation(19, 22), Rotation.ANGLE_180),
                new TilePlacement(TILES.getTile(6), new ByteLocation(19, 21), Rotation.ANGLE_120),
                new TilePlacement(TILES.getTile(4), new ByteLocation(20, 20), Rotation.ANGLE_240),
                new TilePlacement(TILES.getTile(5), new ByteLocation(21, 20), Rotation.ANGLE_300),
                new TilePlacement(TILES.getTile(1), new ByteLocation(21, 21), Rotation.ANGLE_0)
        );

        TilePlacementList tiles = new TilePlacementList(
                new TilePlacement(TILES.getTile(1), new ByteLocation(21, 21), Rotation.ANGLE_0),
                new TilePlacement(TILES.getTile(2), new ByteLocation(20, 21), Rotation.ANGLE_60),
                new TilePlacement(TILES.getTile(3), new ByteLocation(19, 22), Rotation.ANGLE_180),
                new TilePlacement(TILES.getTile(5), new ByteLocation(19, 21), Rotation.ANGLE_300),
                new TilePlacement(TILES.getTile(6), new ByteLocation(18, 21), Rotation.ANGLE_0),
                new TilePlacement(TILES.getTile(4), new ByteLocation(17, 21), Rotation.ANGLE_120)
        );*/
        TilePlacementList tiles = new TilePlacementList(
                new TilePlacement(TILES.getTile(5), new ByteLocation(21, 20), Rotation.ANGLE_300),
                new TilePlacement(TILES.getTile(1), new ByteLocation(21, 21), Rotation.ANGLE_0),
                new TilePlacement(TILES.getTile(2), new ByteLocation(20, 21), Rotation.ANGLE_60),
                new TilePlacement(TILES.getTile(3), new ByteLocation(19, 22), Rotation.ANGLE_180),
                new TilePlacement(TILES.getTile(6), new ByteLocation(19, 21), Rotation.ANGLE_120),
                new TilePlacement(TILES.getTile(4), new ByteLocation(20, 20), Rotation.ANGLE_240)
        );
        TantrixPath expectedPath = new TantrixPath(tiles, PathColor.BLUE);

        assertEquals("Unexpected path.", expectedPath, rPath);

        // make sure we get a different random path on the second call.
        TantrixPath rPath2 = pathGenerator.generateRandomPath();
        assertNotEquals("Unexpected path.", expectedPath, rPath2);
    }

    @Test
    public void test5TilesPathGenNeverNull() {
        pathGenerator = new RandomPathGenerator(place1of5UnsolvedTiles());

        for (int i=0; i<100; i++) {
            TantrixPath rPath = pathGenerator.generateRandomPath();
            assertEquals("Unexpected length for randomly generated path.", 5, rPath.size());
        }
    }
}