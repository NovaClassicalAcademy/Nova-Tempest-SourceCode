package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Launcher - Velocity Control")
public class GoatVelocity extends LinearOpMode {

    Servo lever;
    private DcMotorEx launcher = null;
    private ElapsedTime timer = new ElapsedTime();

    // --- CONFIGURE THESE for your hardware ---
    private static final String LAUNCHER_NAME = "Goat";
    private static final int TICKS_PER_REV = 28;            // TODO replace with your encoder ticks per motor rev
    private static final double GEAR_RATIO = 1.0;           // outputRev = motorRev * (1/gearRatio) depending on definition
    // If output is direct: GEAR_RATIO = 1.0
    // If motor turns faster than wheel, adjust accordingly

    // Example target RPM for launcher wheel
    private static final double TARGET_RPM = 6000;

    // Safety/timeouts
    private static final double SPINUP_TIMEOUT = 4.0; // seconds to wait for spin-up

    @Override
    public void runOpMode() throws InterruptedException {
        launcher = hardwareMap.get(DcMotorEx.class, LAUNCHER_NAME);
        // motor direction depending on how you wired it
        launcher.setDirection(DcMotor.Direction.REVERSE);

        HardwareClass _hardware = new HardwareClass();
        _hardware.Init(hardwareMap);

        // Use RUN_USING_ENCODER so velocity control uses encoder feedback
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lever = hardwareMap.get(Servo.class, "scooper");


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        timer.reset();
        while (opModeIsActive()) {
            // Example controls:
            // - Press gamepad1.a to spin up and hold at TARGET_RPM
            // - Press gamepad1.b to stop
            // - gamepad1.left_stick_y to adjust target RPM interactively

            double rpmAdjust = -gamepad1.left_stick_y * 200.0; // adjust +/- 200 RPM per full stick
            double targetRPM = TARGET_RPM + rpmAdjust;
            double Velocity = launcher.getVelocity();

            if (gamepad1.a) {
                // Convert target RPM to ticks per second
                double ticksPerSecond = rpmToTicksPerSecond(targetRPM, TICKS_PER_REV, GEAR_RATIO);
                launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // ensure using encoder
                launcher.setVelocity(ticksPerSecond); // DcMotorEx uses ticks/sec
                timer.reset();
                // Wait up to SPINUP_TIMEOUT for RPM to stabilize (optional)
                while (opModeIsActive() && timer.seconds() < SPINUP_TIMEOUT && gamepad1.a) {
                    telemetry.addData("Launcher", "Spinning to %.0f RPM (%.0f t/s)", targetRPM, ticksPerSecond);
                    telemetry.addData("Motor velocity (t/s)", launcher.getVelocity());
                    telemetry.update();
                    idle();
                }
            } else if (gamepad1.b) {
                launcher.setPower(0);
            } else if (gamepad1.x) {
                // quick manual full power (not recommended for precise RPMs)
                launcher.setPower(1.0);
            } else {
                // if no buttons, optionally hold velocity or do nothing
                launcher.setPower(0); // uncomment to stop when no command
            }

            //lever
            if(gamepad1.right_bumper){
                lever.setPosition(0.25);
            }
            else{
                lever.setPosition(0);
            }

            telemetry.addData("Target RPM", "%.1f", targetRPM);
            double currentTps = launcher.getVelocity();
            double currentRPM = ticksPerSecondToRpm(currentTps, TICKS_PER_REV, GEAR_RATIO);
            telemetry.addData("Current RPM", "%.1f", currentRPM);
            telemetry.addData("Velocity",Velocity);
            telemetry.addData("ticksPerRev",launcher.getCurrentPosition());
            telemetry.update();

            idle();
        }
    }

    private double rpmToTicksPerSecond(double rpm, int ticksPerRev, double gearRatio) {
        // rpm -> ticks/sec
        return (rpm / 60.0) * ticksPerRev * gearRatio;
    }

    private double ticksPerSecondToRpm(double ticksPerSecond, int ticksPerRev, double gearRatio) {
        return (ticksPerSecond / (ticksPerRev * gearRatio)) * 60.0;
    }
}