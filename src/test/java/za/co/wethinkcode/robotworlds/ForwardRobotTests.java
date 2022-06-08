package za.co.wethinkcode.robotworlds;


import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Commands.Command;
import za.co.wethinkcode.robotworlds.maze.EmptyMaze;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.Robot;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ForwardRobotTests {
    @Test
    void TestForward() {
        IWorld maze = new TextWorld(new EmptyMaze());
        Robot robot = new Robot("Hall", maze);
        Command forward10 = Command.create("forward 10");
        assertTrue(forward10.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 10);
        assertEquals(expectedPosition, robot.getWorld().getPosition());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }
    @Test
    void TestForward20(){
        IWorld bott = new TextWorld(new EmptyMaze());
        Robot robot = new Robot("Jagua", bott);
        Command forwardK = Command.create("forward 20");
        assertTrue(forwardK.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 20);
        assertEquals(expectedPosition, robot.getWorld().getPosition());
        assertEquals("Moved forward by 20 steps.", robot.getStatus());
    }
    @Test
    void TestForward120(){
        IWorld bott = new TextWorld(new EmptyMaze());
        Robot robot = new Robot("Jagua", bott);
        Command forwardK = Command.create("forward 120");
        assertTrue(forwardK.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY());
        assertEquals(expectedPosition, robot.getWorld().getPosition());
        assertEquals("Sorry i can not go outside my safe zone", robot.getStatus());
    }
}
