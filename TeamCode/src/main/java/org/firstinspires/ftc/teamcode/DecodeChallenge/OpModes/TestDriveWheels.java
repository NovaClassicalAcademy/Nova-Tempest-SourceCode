package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;

@Autonomous (name= "TestDriveWheels", group = "Test")
public class TestDriveWheels extends LinearOpMode {

    private RobotMapping _rb;
    private ElapsedTime _timer;

    public void runOpMode() {
        _rb = new RobotMapping(hardwareMap);
        _timer = new ElapsedTime();

        waitForStart();

        _timer.reset();

        while (opModeIsActive()) {

//            _rb.FrontLeftDrive.setPower(1);
//            sleep(1000);
//            _rb.FrontLeftDrive.setPower(0);
//
//            _rb.FrontRightDrive.setPower(1);
//            sleep(1000);
//            _rb.FrontRightDrive.setPower(0);
//
//            _rb.BackLeftDrive.setPower(1);
//            sleep(1000);
//            _rb.BackLeftDrive.setPower(0);
//
//            _rb.BackRightDrive.setPower(1);
//            sleep(1000);
//            _rb.BackRightDrive.setPower(0);

            telemetry.addData("X: ", _rb.XEncoder.getCurrentPosition());
            telemetry.addData("Y Left: ", _rb.YLeftEncoder.getCurrentPosition());
            telemetry.addData("Y Right: ", _rb.YRightEncoder.getCurrentPosition());

            telemetry.update();
        }
    }
}
