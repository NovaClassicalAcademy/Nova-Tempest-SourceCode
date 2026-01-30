package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptAprilTag;
import org.firstinspires.ftc.teamcode.DecodeChallenge.AllianceColor;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.AprilTagDetectionController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.DecodeDriveSystemStateMachine;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequenceSystemStateMachine;

@Autonomous(name="Autonomous Comp Test", group="Test")
public class AutonomousTest extends LinearOpMode {
    private enum RobotState { Start, Preloaded, MoveOffWall, MoveOffLaunch, Idle }
    private RobotState _currentAutoState = RobotState.Start;
    private RobotMapping _robotMapping;
    private DecodeDriveSystemStateMachine _pathing;
    private FireSequenceSystemStateMachine _fireSequence;
    private AprilTagDetectionController _camera;
    private Follower _follower;
    private Constants _constants;
    private AllianceColor _allianceColor;

    @Override
    public void runOpMode() {

        _robotMapping = new RobotMapping(hardwareMap);
        _follower = Constants.createFollower(hardwareMap);
        _pathing = new DecodeDriveSystemStateMachine(telemetry, _follower, _robotMapping, _allianceColor);

        _pathing.Init();

        _fireSequence = new FireSequenceSystemStateMachine(telemetry, _robotMapping);
        _camera = new AprilTagDetectionController();

        telemetry.addData("OpMode", "Autonomous Comp Test 11");
        telemetry.addData("Robot State: ", _currentAutoState);
        telemetry.update();

        waitForStart();

        _pathing.Test();

        // NOTE: initialize launch variable and spin up the launcher.
//        FireSequenceSystemStateMachine.LaunchState launchState;

        while (opModeIsActive()) {
//            switch (_currentAutoState) {
//                case Start:
//            }
            _follower.update();

            if (!_follower.isBusy()){
                stop();
            }
        }
    }
}