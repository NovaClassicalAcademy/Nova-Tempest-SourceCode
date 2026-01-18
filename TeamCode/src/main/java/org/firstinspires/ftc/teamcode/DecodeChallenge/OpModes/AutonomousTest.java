package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.BasicDriveController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequence;

@Autonomous(name="Autonomous Comp Test", group="Test")
public class AutonomousTest extends LinearOpMode {
    private enum RobotState { Start, Preloaded, MoveOffWall, MoveOffLaunch, Idle }

    private RobotMapping _robotMapping;
    private FireSequence _fireSequence;
    private RobotState _currentAutoState = RobotState.Start;
    private BasicDriveController _driveController;

    @Override
    public void runOpMode() {

        _robotMapping = new RobotMapping(hardwareMap);
        _fireSequence = new FireSequence(telemetry, _robotMapping);
        _driveController = new BasicDriveController(_robotMapping);

        telemetry.addData("OpMode", "Autonomous Comp Test 11");
        telemetry.addData("Robot State: ", _currentAutoState);
        telemetry.update();

        waitForStart();

        // NOTE: initialize launch variable and spin up the launcher.
        FireSequence.LaunchState launchState;

        while (opModeIsActive()) {

            switch (_currentAutoState){

                case Start:
                    _driveController.SetTarget(130);
                    _currentAutoState = RobotState.MoveOffWall;
                    break;

                case MoveOffWall:
                    if (_driveController.IsDoneMoving()){
                        _fireSequence.InitFireMode();
                        _currentAutoState = RobotState.Preloaded;
                    }
                    break;

                case Preloaded:

                    _fireSequence.GetStatus();

                    if (_fireSequence.IsReadyToFire()){

                        _fireSequence.Fire();
                    }

                    if (_fireSequence.IsFireComplete()){
                        _driveController.SetTarget(125);
                        _currentAutoState = RobotState.MoveOffLaunch;
                    }
                    break;

                case MoveOffLaunch:
                    if (_driveController.IsDoneMoving()){
                        _currentAutoState = RobotState.Idle;
                    }
                    break;

                case Idle:
                    // Do nothing
                    break;
            }

            telemetry.addData("Auto State: ", _currentAutoState);
            _driveController.DebugOutput(telemetry);
            telemetry.update();
            sleep(1000);
        }
    }
}