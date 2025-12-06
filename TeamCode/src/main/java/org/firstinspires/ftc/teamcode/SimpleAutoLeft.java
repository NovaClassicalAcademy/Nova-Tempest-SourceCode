package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Autonomous(name = "SimpleAutoLeft", group = "Auto")
public class SimpleAutoLeft extends LinearOpMode {


    @Override
    public void runOpMode() {
        HardwareClass _hardware = new HardwareClass();
        _hardware.Init(hardwareMap);


        waitForStart();


        while(opModeIsActive()){
            _hardware.BackLeftDrive.setPower(-0.2);
            _hardware.FrontLeftDrive.setPower(0.2);
            _hardware.BackRightDrive.setPower(0.2);
            _hardware.FrontRightDrive.setPower(-0.2);


            if (_hardware.XEncoder.getCurrentPosition() < 2000){
                stop();
            }
        }
    }


}
