package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Red", group="Live")
public class AutonomousLiveRed extends LinearOpMode {


    @Override
    public void runOpMode() {

        telemetry.addData("OpMode", "Autonomous RED side!");
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
    }
}
