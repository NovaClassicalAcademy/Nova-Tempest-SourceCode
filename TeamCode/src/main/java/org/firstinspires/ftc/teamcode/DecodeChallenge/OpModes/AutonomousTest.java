package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.DecodePathing;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequence;

@Autonomous(name="Autonomous Pedro Test", group="Test")
public class AutonomousTest extends LinearOpMode {
    private enum RobotState { Preloaded, Firing, Move, Idle }

    private RobotMapping _robotMapping;
    private FireSequence _fireSequence;
    private Follower _follower;
    private DecodePathing _pathing;
    private ElapsedTime _stateTimer = new ElapsedTime();
    private RobotState _currentAutoState = RobotState.Preloaded;

    @Override
    public void runOpMode() {

        _robotMapping = new RobotMapping(hardwareMap);
        _follower = Constants.createFollower(hardwareMap);
        _pathing = new DecodePathing(_follower);
        _fireSequence = new FireSequence(telemetry, _robotMapping);

        telemetry.addData("OpMode", "Autonomous PEDRO TEST");
        telemetry.update();

        waitForStart();

        RobotState robotState = RobotState.Firing;
        _fireSequence.InitFireMode();

        while (opModeIsActive()) {

            _follower.update();

            FireSequence.LaunchState launchState = _fireSequence.GetStatus();

            switch (_currentAutoState){
                case Preloaded:
                    if (launchState == FireSequence.LaunchState.ReadyToFire){
                        _fireSequence.Fire();
                    }

                    if (launchState == FireSequence.LaunchState.Off){
                        _currentAutoState = RobotState.Move;
                    }
                    break;

                case Move:
                    if (!_follower.isBusy()){
                        _currentAutoState = RobotState.Idle;
                    }
                    break;

                case Idle:
                    // Do nothing
                    break;
            }

            telemetry.addData("Auto State", _currentAutoState);
            telemetry.addData("Fire State", launchState);
            telemetry.addData("Pos X", _follower.getPose().getX());
            telemetry.addData("Pos Y", _follower.getPose().getY());
            telemetry.update();
        }
    }
}