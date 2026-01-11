package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.DecodePathing;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequence;
import org.firstinspires.ftc.teamcode.Utilities.StopWatch;

@Autonomous(name="Autonomous Pedro Test", group="Test")
public class AutonomousTest extends LinearOpMode {
    private enum RobotState { Idle, Firing }

    private final RobotMapping _robotMapping;
    private final FireSequence _fireSequence;
    private final Follower _follower;
    private final DecodePathing _pathing;
    private StopWatch _stopWatch;

    public AutonomousTest() {
        _robotMapping = new RobotMapping(hardwareMap);
        _follower = Constants.createFollower(hardwareMap);
        _pathing = new DecodePathing(_follower);

        _fireSequence = new FireSequence(_robotMapping);
    }

    @Override
    public void runOpMode() {

        telemetry.addData("OpMode", "Autonomous PEDRO TEST");
        telemetry.update();

        waitForStart();

        RobotState robotState = RobotState.Firing;

        while (opModeIsActive()) {

            // NOTE: firing loop.
            if (robotState == RobotState.Firing) {
                FireCannons(robotState);
            }

            if (robotState == RobotState.Idle){
                MoveToPosition(robotState);
            }
        }
    }

    private void FireCannons(RobotState robotState){

        _fireSequence.InitializeCountDown();

        _stopWatch.StartTimer();
        while (_stopWatch.GetElapseTime() < 1000) {
            telemetry.addData("Launch Sequence", "Countdown Initiated");
            telemetry.update();
            _stopWatch.ResetTimer();
        }

        FireSequence.LaunchState launchState = _fireSequence.GetStatus();

        while (opModeIsActive() && launchState != FireSequence.LaunchState.Off) {

            telemetry.addData("OpMode", "Autonomous PEDRO TEST");

            if (_fireSequence.IsLoaded()) {
                telemetry.addData("Launch Sequence", "Countdown Initiated");
                _fireSequence.FireAway();
            }

            telemetry.update();
            launchState = _fireSequence.GetStatus();

            if (launchState == FireSequence.LaunchState.Off) {
                robotState = RobotState.Idle;
            }
        }
    }

    private void MoveToPosition(RobotState robotState){

        while(opModeIsActive()){

            telemetry.addData("OpMode", "Autonomous PEDRO TEST");

            // TODO: Perform the movements.

            DecodePathing.PathState pathState = _pathing.Get

            switch (robotState){

                case Idle:
                    // Todo: move to next position
                    break;
            }

        }
    }
}