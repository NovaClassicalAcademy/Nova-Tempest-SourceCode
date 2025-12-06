package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "AutoDriveForward", group = "Auto")
public class AutoDriveForward extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        HardwareClass _hardware = new HardwareClass();
        _hardware.Init(hardwareMap);

        _hardware.BackLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        _hardware.FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        _hardware.BackRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        _hardware.FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();


        while(opModeIsActive()){

            if (runtime.time() > 0.5) {

                _hardware.BackLeftDrive.setPower(0.4);
                _hardware.FrontLeftDrive.setPower(0.4);
                _hardware.BackRightDrive.setPower(0.4);
                _hardware.FrontRightDrive.setPower(0.4);

            }




            if (_hardware.XEncoder.getCurrentPosition() < -250){
                _hardware.BackLeftDrive.setPower(0);
                _hardware.FrontLeftDrive.setPower(0);
                _hardware.BackRightDrive.setPower(0);
                _hardware.FrontRightDrive.setPower(0);
            }
            telemetry.addData("Encoder POS", _hardware.XEncoder.getCurrentPosition());
            telemetry.update();
        }
    }


}
