package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptAprilTag;
import org.firstinspires.ftc.teamcode.DecodeChallenge.AllianceColor;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.AprilTagDetectionController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.DecodeDriveSystemStateMachine;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequenceSystemStateMachine;

@Autonomous(name="Autonomous Comp Test", group="Test")
public class AutonomousTest extends LinearOpMode {
    private enum RobotState {Start, Preloaded, MoveOffWall, MoveOffLaunch, Idle}

    private RobotState _currentAutoState = RobotState.Start;
    private RobotMapping _robotMapping;
    private DecodeDriveSystemStateMachine _pathing;
    private FireSequenceSystemStateMachine _fireSequence;
    private IntakeController _intake;
    private Follower _follower;
    private Constants _constants;
    private ElapsedTime _stateTimer;
    private AllianceColor _allianceColor = AllianceColor.Blue;

    @Override
    public void runOpMode() {

        _robotMapping = new RobotMapping(hardwareMap);
        _follower = Constants.createFollower(hardwareMap);
        _pathing = new DecodeDriveSystemStateMachine(telemetry, _follower, _robotMapping, _allianceColor);
        _pathing.Init();

        _stateTimer = new ElapsedTime();
        _intake = new IntakeController(_robotMapping.UpperLeftIntake, _robotMapping.UpperRightIntake, _robotMapping.LowerLeftIntake, _robotMapping.LowerRightIntake);

//        _fireSequence = new FireSequenceSystemStateMachine(telemetry, _robotMapping);

        telemetry.addData("OpMode", "Autonomous Comp Test 14");
        telemetry.addData("Robot State: ", _currentAutoState);
        telemetry.update();

        waitForStart();

        // NOTE: initialize launch variable and spin up the launcher.
//        FireSequenceSystemStateMachine.LaunchState launchState;

        int step = 0;
        _stateTimer.startTime();

        while (opModeIsActive()) {
            _follower.update();

            switch (_currentAutoState) {
                case Start:
                    if (step == 0) {
                        _pathing.AlignToFirstRow(21);
                        step = 1;
                        _stateTimer.reset();

                        telemetry.addData("In Step", "1");
                    }

                    if (!_follower.isBusy() && step == 1 && _stateTimer.milliseconds() > 500){
                        telemetry.addData("Status", "Step 1 Complete");
                        _currentAutoState = RobotState.Idle;
                    }
                    break;

                case Idle:
                    telemetry.addData("In Step", "Finish");
                    _follower.holdPoint(_follower.getPose());
                    telemetry.addData("Status", "Holding Position");
                    break;
            }

            telemetry.update();

        }
    }
}