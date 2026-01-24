package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.AprilTagDetectionController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.BasicDriveController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequence;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous(name="Autonomous Blue", group="Live")
public class AutonomousLiveBlue extends LinearOpMode {
    private enum RobotState { Start, Preloaded, MoveOffWall, MoveOffLaunch, Idle }

    private RobotMapping _robotMapping;
    private FireSequence _fireSequence;
    private AprilTagDetectionController _aprilTagDetection;
    private RobotState _currentAutoState = RobotState.Start;
    private BasicDriveController _driveController;

    @Override
    public void runOpMode() {

        _robotMapping = new RobotMapping(hardwareMap);
        _fireSequence = new FireSequence(telemetry, _robotMapping);
        _driveController = new BasicDriveController(_robotMapping);
        _aprilTagDetection = new AprilTagDetectionController(_robotMapping, telemetry);

        telemetry.addData("OpMode", "Autonomous Blue");
        telemetry.addData("Robot State: ", _currentAutoState);
        telemetry.update();

        waitForStart();
        AprilTagDetection id20 = _aprilTagDetection.getTagByID(20);
        _aprilTagDetection.Update();
        _aprilTagDetection.displayDetectionTelemetry(id20);

        while (opModeIsActive()){
        }
    }
}
