package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
    private AllianceColor _allianceColor = AllianceColor.Blue;

    @Override
    public void runOpMode() {

        _robotMapping = new RobotMapping(hardwareMap);
        _follower = Constants.createFollower(hardwareMap);
        _pathing = new DecodeDriveSystemStateMachine(telemetry, _follower, _robotMapping, _allianceColor);
        _pathing.Init();

        _intake = new IntakeController(_robotMapping.UpperLeftIntake, _robotMapping.UpperRightIntake, _robotMapping.LowerLeftIntake, _robotMapping.LowerRightIntake);

//        _fireSequence = new FireSequenceSystemStateMachine(telemetry, _robotMapping);

        telemetry.addData("OpMode", "Autonomous Comp Test 12");
        telemetry.addData("Robot State: ", _currentAutoState);
        telemetry.update();

        waitForStart();

        // NOTE: initialize launch variable and spin up the launcher.
//        FireSequenceSystemStateMachine.LaunchState launchState;

        int step = 0;

        while (opModeIsActive()) {
//            switch (_currentAutoState) {
//                case Start:
//            }
            _follower.update();

            switch (_currentAutoState) {
                case Start:
                    if (!_follower.isBusy() && step == 0) {
                        _pathing.AlignToFirstRow(21);
                        step = 1;
                        telemetry.addData("In Step", "1");
                        sleep(100);
                    }
                    if (!_follower.isBusy() && step == 1) {
                        _intake.Activate();
                        _pathing.IntakeFirstRow();
                        step++;
                        telemetry.addData("In Step", "Step 2");
                        sleep(100);
                    }
                    if (!_follower.isBusy() && step == 2) {
                        _pathing.ReturnFirstRowToLaunch();
                        step++;
                        telemetry.addData("In Step", "Step 3");
                        sleep(100);
                    }

                    break;
            }
        }
    }
}