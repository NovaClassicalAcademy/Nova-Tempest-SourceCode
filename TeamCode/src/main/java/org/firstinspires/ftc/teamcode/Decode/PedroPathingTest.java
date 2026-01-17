package org.firstinspires.ftc.teamcode.Decode;

import org.firstinspires.ftc.teamcode.Config.PIDController;

//Handles auto path. Uses PID controllers to follow paths and reach target position.
//Needs HardwareClass.
public class PedroPathingTest {
    private final HardwareClass robot;
    private final PIDController xController; //forward/backward
    private final PIDController yController; //left/right strafe
    private final PIDController headingController; //rotation

    private double currentX = 0;
    private double currentY = 0;
    private double currentHeading = 0;
    /// TODO: adjust values.

    private double positionTolerance = 10; //how close it is to target. in ticks or in.   ///TODO: adjust values.
    private double headingTolerance = 2;

    /// TODO: adjust values.

    public PedroPathingTest(HardwareClass robot) { //constructor : create instances of class.
        this.robot = robot;

        xController = new PIDController(0.05, 0, 0.002); ///TODO: adjust values
        yController = new PIDController(0.05, 0, 0.002); ///TODO: adjust values
        headingController = new PIDController(0.05, 0, 0.002); ///TODO: adjust values
    }

    //Updates current position
    public void updatePosition(double x, double y, double heading) {
        this.currentX = x;
        this.currentY = y;
        this.currentHeading = heading;
    }

    public boolean followPathToPoint(double targetX, double targetY, double targetHeadng) {
        double xError = targetX - currentX;
        double yError = targetY - currentY;
        double headingError = normalizeAngle(targetHeadng - currentHeading);

        double xPower = xController.calculateError(targetX, currentX);
        double yPower = yController.calculateError(targetY, currentY);
        double turnPower = headingController.calculateError(targetHeadng, currentHeading);

        double robotX = xPower * Math.cos(-Math.toRadians(currentHeading)) - yPower * Math.sin(-Math.toRadians(currentHeading));
        double robotY = yPower * Math.sin(-Math.toRadians(currentHeading)) + yPower * Math.cos(-Math.toRadians(currentHeading));

        setDrivePowers(robotX, robotY, turnPower);

        boolean atPosition = Math.abs(xError) < positionTolerance && Math.abs(yError) < positionTolerance;
        boolean atHeading = Math.abs(headingError) < headingTolerance;
        return atPosition && atHeading;
    }

    public boolean driveToPoint(double targetX, double targetY) {
        return followPathToPoint(targetX, targetY, currentHeading);
    }

    public boolean turnToHeading(double targetHeading) {
        double headingError = normalizeAngle(targetHeading - currentHeading);
        double turnPower = headingController.calculateError(targetHeading, currentHeading);

        setDrivePowers(0, 0, turnPower);
        return Math.abs(headingError) < headingTolerance;
    }

    public void followPath(double[] xPoints, double[] yPoints, double[] finalHeadings) {
        for (int i = 0; i < xPoints.length; i++) {

            double targetX = xPoints[i];
            double targetY = yPoints[i];
            double targetHeading = finalHeadings[i];

            while (!driveToPoint(targetX, targetY)) {
                //update position in main loop before calling this
            }

            while (!turnToHeading(targetHeading)) {
                //do nothing till we get to target turn.
            }

            updatePosition(targetX, targetY, targetHeading);
        }
    }

    //setting motor powers using drive kinematics.
    private void setDrivePowers(double forward, double strafe, double turn) {
        double leftFrontPower = forward + strafe + turn;
        double rightFrontPower = forward - strafe - turn;
        double leftBackPower = forward - strafe + turn;
        double rightBackPower = forward + strafe - turn;

        double maxPower = Math.max(
                Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower)),
                Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower))
        );

        if (maxPower > 1.0) {
            leftFrontPower /= maxPower;
            rightFrontPower /= maxPower;
            leftBackPower /= maxPower;
            rightBackPower /= maxPower;
        }

        robot.FrontLeftDrive.setPower(leftFrontPower);
        robot.FrontRightDrive.setPower(rightFrontPower);
        robot.BackLeftDrive.setPower(leftBackPower);
        robot.BackRightDrive.setPower(rightBackPower);
    }

    public void stop() {
        setDrivePowers(0, 0, 0);
    }

    public void resetControllers() {
        xController.reset();
        yController.reset();
        headingController.reset();
    }

    public void setPIDCoefficients(double kP, double kI, double kD) {
        xController.setPID(kP, kI, kD);
        yController.setPID(kP, kI, kD);
    }

    public void setHeadingPID(double kP, double kI, double kD) {
        headingController.setPID(kP, kI, kD);
    }

    public void setTolerances(double positionTol, double headingTol) {
        this.positionTolerance = positionTol;
        this.headingTolerance = headingTol;
    }

    private double normalizeAngle(double angle) { //if we go over 360, subtract # by 360 and it'll be # degrees.
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}

