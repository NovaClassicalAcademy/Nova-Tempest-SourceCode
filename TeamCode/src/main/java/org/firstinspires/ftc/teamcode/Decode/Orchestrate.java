package org.firstinspires.ftc.teamcode.Decode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.internal.ftdi.eeprom.FT_EEPROM_232H;
import org.firstinspires.ftc.teamcode.Config.AprilTagController;
import org.firstinspires.ftc.teamcode.Config.AprilTagOptimizer;
import org.firstinspires.ftc.teamcode.Config.ArtifactColor;
import org.firstinspires.ftc.teamcode.Config.ColorSensorController;
import org.firstinspires.ftc.teamcode.Config.IntakeServo;
import org.firstinspires.ftc.teamcode.Config.ShooterMotor;
import org.firstinspires.ftc.teamcode.Config.RouletteServoController;

import java.util.Timer;

@TeleOp(name = "Orchestrate Decode Test", group = "Decode")
public class Orchestrate extends LinearOpMode {
    private HardwareClass _hardware;
    private RouletteServoController _rouletteController;
    private ColorSensorController _colorController;
    private IntakeServo _intakeServo;
    private ShooterMotor _shootMotor;
    private AprilTagController _aprilTagController;
    private AprilTagOptimizer _aprilTagOptimizer;
    private PedroPathingTest _pedroPathingTest;

    @Override
    public void runOpMode() {
        _hardware = new HardwareClass();
        _hardware.Init(hardwareMap);

        _pedroPathingTest = new PedroPathingTest(_hardware);

        _colorController = new ColorSensorController(_hardware.NormalizedColorSensor);
        _rouletteController = new RouletteServoController(_hardware.RouletteServo, _colorController);
        _intakeServo = new IntakeServo(_hardware.IntakeUpperLeft, _hardware.IntakeUpperRight, _hardware.IntakeLowerLeft, _hardware.IntakeLowerRight);
        _shootMotor = new ShooterMotor(_hardware.ShooterMotor);
        _aprilTagOptimizer = new AprilTagOptimizer(_hardware.WebCam);
        _aprilTagController = new AprilTagController(_hardware.WebCam);

        telemetry.addLine("Initialized. Press START.");
        telemetry.update();
        waitForStart();

        //_rouletteController.InitializeRoulette();

        //_pedroPathingTest.followPathToPoint(1,1,0);

//        _intakeServo.startIntake();

//        int pattern = _aprilTagController.getBallPattern(); // NOTE: this is just for testing using hard value.
//        int targetPosition = -1;

        int wheelNum = 0;
        while (opModeIsActive()) {
//
//            switch (pattern) {
//                case 21:
//                    targetPosition = 0;
//                    break;
//                case 22:
//                    targetPosition = 1;
//                    break;
//                case 23:
//                    targetPosition = 2;
//                    break;
//            }

//            _rouletteController.MoveToPosition(targetPosition);
//
//            ArtifactColor color = _colorController.GetColor(telemetry);
//
//            _shootMotor.startOutput();
//
//            telemetry.addData("Detect Color", color.toString());
//            telemetry.addData("Ball Pattern", pattern);
            if (wheelNum == 0) {
                _hardware.FrontRightDrive.setPower(0.2);
                sleep(3000);
                _hardware.FrontRightDrive.setPower(0);
                wheelNum++;
            } else if (wheelNum == 1) {
                _hardware.FrontLeftDrive.setPower(0.2);
                sleep(3000);
                _hardware.FrontLeftDrive.setPower(0);
                wheelNum++;
            } else if (wheelNum == 2) {
                _hardware.BackRightDrive.setPower(0.2);
                sleep(3000);
                _hardware.BackRightDrive.setPower(0);
                wheelNum++;
            } else if (wheelNum == 3){
                _hardware.BackLeftDrive.setPower(0.2);
                sleep(3000);
                _hardware.BackLeftDrive.setPower(0);
                wheelNum++;
            } else if (wheelNum > 3)
            { wheelNum = 0; }

            double yrPos = _hardware.YRightEncoder.getCurrentPosition();
            double ylPos = _hardware.YLeftEncoder.getCurrentPosition();
            double xPos = _hardware.XEncoder.getCurrentPosition();

            telemetry.addData("YLeftEncoder", ylPos);
            telemetry.addData("YRightEncoder", yrPos);
            telemetry.addData("XEncoder", xPos);
            telemetry.update();
        }
    }
}
