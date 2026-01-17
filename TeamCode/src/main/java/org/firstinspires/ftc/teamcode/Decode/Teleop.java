
package org.firstinspires.ftc.teamcode.Decode;


import static androidx.core.math.MathUtils.clamp;


import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


import java.util.List;


@TeleOp(name = "TempestTeleop", group = "TeleOp")
class TempestTeleop extends OpMode {

//
//    ColorSensor sight = new ColorSensor();
//
//
//    ColorSensor.DetectedColor detectedColor;


    DcMotorEx FR;
    DcMotorEx FL;
    DcMotorEx BR;
    DcMotorEx BL;


    DcMotorEx outtake;


    DcMotorEx YLeftEncoder;
    DcMotorEx YRightEncoder;
    DcMotorEx XEncoder;


    CRServo roulette;
    CRServo leftBlack;
    CRServo rightBlack;
    CRServo leftGreen;
    CRServo rightGreen;


    CRServo EncoderRoulette;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {


        FR = hardwareMap.get(DcMotorEx.class, "FrontRight");
        FL = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        BR = hardwareMap.get(DcMotorEx.class, "BackRight");
        BL = hardwareMap.get(DcMotorEx.class, "BackLeft");


        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);


        outtake = hardwareMap.get(DcMotorEx.class, "Goat");
        outtake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        leftBlack = hardwareMap.get(CRServo.class, "IntakeLowerLeft");
        rightBlack = hardwareMap.get(CRServo.class, "IntakeLowerRight");
        leftGreen = hardwareMap.get(CRServo.class, "IntakeUpperLeft");
        rightGreen = hardwareMap.get(CRServo.class, "IntakeUpperRight");


        YLeftEncoder = hardwareMap.get(DcMotorEx.class, "YLeftEncoder");// YLeftEncoder
        YRightEncoder = hardwareMap.get(DcMotorEx.class, "YRightEncoder");//YRight
        XEncoder = hardwareMap.get(DcMotorEx.class, "XEncoder");//XEncoder


        roulette = hardwareMap.get(CRServo.class, "RouletteServo");


        //EncoderRoulette = hardwareMap.get( CRServo.class, "RouletteEncoder");


        roulette.setPower(0);

//
//        sight.init(hardwareMap);


        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);


        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        telemetry.addData("Status", "Initialized");


    }


    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {


    }


    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {


        //panning motion
        double FL_power = (gamepad1.left_stick_y - gamepad1.left_stick_x) / 2;
        double BL_power = (gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
        double FR_power = (gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
        double BR_power = (gamepad1.left_stick_y - gamepad1.left_stick_x) / 2;


        //turning motion
        FL_power -= gamepad1.right_stick_x / 2;
        BL_power -= gamepad1.right_stick_x / 2;
        FR_power += gamepad1.right_stick_x / 2;
        BR_power += gamepad1.right_stick_x / 2;


        FR.setPower(clamp(FR_power, -1, 1));
        FL.setPower(clamp(FL_power, -1, 1));
        BR.setPower(clamp(BR_power, -1, 1));
        BL.setPower(clamp(BL_power, -1, 1));
//
//
//        //Color sensor values
//        detectedColor = sight.getDetectedColor(telemetry);
//        telemetry.addData("ColorDetected", detectedColor);


        //intake
        if (gamepad2.x) {
            leftBlack.setPower(5);
            rightBlack.setPower(-5);
            leftGreen.setPower(-5);
            rightGreen.setPower(-5);
        }
        //outtake
        else if (gamepad2.y) {
            leftBlack.setPower(-5);
            rightBlack.setPower(5);
            leftGreen.setPower(6.5);
            rightGreen.setPower(6.5);
        }
        //passive off
        else {
            leftBlack.setPower(0);
            rightBlack.setPower(0);
            leftGreen.setPower(0);
            rightGreen.setPower(0);
        }


       /*
      if (gamepad2.left_bumper && (!DetectedColor.Green || !DetectedColor.PURPLE)) {
           roulette.setPower(-1);


       }
       else if(detectedColor.GREEN || DetectedColor.PURPLE || DetectedColor.UNKNOWN){
           roulette.setPower(0);
       }


       */


        //rotate roulette for now
        if (gamepad2.left_bumper) {
            roulette.setPower(0.5);
        } else if (gamepad2.right_bumper) {
            roulette.setPower(-1);
        } else {
            roulette.setPower(0);
        }


        //Shoot
        if (gamepad2.right_trigger > 0.2) {
            outtake.setPower(-10);
        } else if (gamepad2.left_trigger > 0.2) {
            outtake.setPower(10);
        } else {
            outtake.setPower(0);
        }


        telemetry.update();


    }


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {


        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);


    }


    private double clamp(double val, double min, double max) {
        if (val < min) {
            val = min;
        } else if (val > max) {
            val = max;
        }
        return val;
    }
}


