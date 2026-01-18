package org.firstinspires.ftc.teamcode;

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
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

@TeleOp(name = "TempestTeleop", group = "TeleOp")

public class TempestTeleop extends OpMode {


    ColorSensor sight = new ColorSensor();

    ColorSensor.DetectedColor detectedColor;

    private ElapsedTime timer = new ElapsedTime();


    DcMotorEx FR;
    DcMotorEx FL;
    DcMotorEx BR;
    DcMotorEx BL;

    DcMotorEx outtake;

    DcMotorEx YLeftEncoder;
    DcMotorEx YRightEncoder;
    DcMotorEx XEncoder;


    CRServo leftBlack;
    CRServo rightBlack;
    CRServo leftGreen;
    CRServo rightGreen;

    Servo lever;
/*
    private DcMotorEx launcher = null;
    private ElapsedTime timer = new ElapsedTime();

    // --- CONFIGURE THESE for your hardware ---
    private static final String LAUNCHER_NAME = "Goat";
    private static final int TICKS_PER_REV = 1;            // TODO replace with your encoder ticks per motor rev
    private static final double GEAR_RATIO = 1.0;           // outputRev = motorRev * (1/gearRatio) depending on definition
    // If output is direct: GEAR_RATIO = 1.0
    // If motor turns faster than wheel, adjust accordingly

    // Example target RPM for launcher wheel
    private static final double TARGET_RPM = 5500;

    // Safety/timeouts
    private static final double SPINUP_TIMEOUT = 4.0; // seconds to wait for spin-up
*/

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


        lever = hardwareMap.get(Servo.class, "scooper");





        sight.init(hardwareMap);

/*
        launcher = hardwareMap.get(DcMotorEx.class, LAUNCHER_NAME);
        // motor direction depending on how you wired it
        launcher.setDirection(DcMotor.Direction.REVERSE);

        // Use RUN_USING_ENCODER so velocity control uses encoder feedback
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

*/



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


        //Color sensor values
        detectedColor = sight.getDetectedColor(telemetry);
        telemetry.addData("ColorDetected", detectedColor);





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

        //lever
        if(gamepad2.right_bumper){
            lever.setPosition(0.25);
        }
        else{
            lever.setPosition(0);
        }

        if(gamepad2.right_bumper){
            timer.reset();
        }


        //Shoot
        if(gamepad2.right_trigger > 0.2){
            outtake.setPower(-10);

        }
        else if(gamepad2.left_trigger > 0.2){
            outtake.setPower(-0.9);
        }
        else if(gamepad2.dpad_up){
            outtake.setPower(1);
        }
        else{
            outtake.setPower(0);
        }

/*

        double rpmAdjust = -gamepad1.left_stick_y * 200.0; // adjust +/- 200 RPM per full stick
        double targetRPM = TARGET_RPM + rpmAdjust;

        if (gamepad2.right_trigger > 0.2) {
            // Convert target RPM to ticks per second
            double ticksPerSecond = rpmToTicksPerSecond(targetRPM, TICKS_PER_REV, GEAR_RATIO);
            launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // ensure using encoder
            launcher.setVelocity(ticksPerSecond); // DcMotorEx uses ticks/sec
            telemetry.addData("Current Velo", "%.1f",  launcher.getVelocity());
            telemetry.update();
            //timer.reset();
            // Wait up to SPINUP_TIMEOUT for RPM to stabilize (optional)
            /*while (timer.seconds() < SPINUP_TIMEOUT && gamepad2.right_trigger > 0.2) {
                telemetry.addData("Launcher", "Spinning to %.0f RPM (%.0f t/s)", targetRPM, ticksPerSecond);
                telemetry.addData("Motor velocity (t/s)", launcher.getVelocity());
                telemetry.update();

            }

        }
        else if (gamepad2.a) {
            // quick manual full power (not recommended for precise RPMs)
            launcher.setPower(1.0);
        }
        else {
            launcher.setPower(0);
        }

        telemetry.addData("Target RPM", "%.1f", targetRPM);
        double currentTps = launcher.getVelocity();
        double currentRPM = ticksPerSecondToRpm(currentTps, TICKS_PER_REV, GEAR_RATIO);
        telemetry.addData("Current Velo", "%.1f",  launcher.getVelocity());
        telemetry.update();


        telemetry.addData("FL power", FL_power);
        telemetry.addData("FR power", FR_power);
        telemetry.addData("BL power", BL_power);
        telemetry.addData("BR power", BR_power);
        telemetry.update();
*/
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

   private double clamp (double val, double min, double max){
            if (val < min) {
                val = min;
            }
            else if (val > max){
                val = max;
            }
            return val;
        }


    private double rpmToTicksPerSecond(double rpm, int ticksPerRev, double gearRatio) {
        // rpm -> ticks/sec
        return (rpm / 60.0) * ticksPerRev * gearRatio;
    }

    private double ticksPerSecondToRpm(double ticksPerSecond, int ticksPerRev, double gearRatio) {
        return (ticksPerSecond / (ticksPerRev * gearRatio)) * 60.0;
    }





}




